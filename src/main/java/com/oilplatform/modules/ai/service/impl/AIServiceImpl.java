package com.oilplatform.modules.ai.service.impl;

import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.ai.entity.AIChat;
import com.oilplatform.modules.ai.mapper.AIMapper;
import com.oilplatform.modules.ai.service.AIService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIServiceImpl implements AIService {

    @Autowired
    private AIMapper aiMapper;

    @Value("${ai.api.key:demo-key}")
    private String apiKey;

    @Value("${ai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Map<String, String> PETROLEUM_KNOWLEDGE = new HashMap<>();

    static {
        PETROLEUM_KNOWLEDGE.put("钻井", "钻井工程是利用钻井设备在地层中钻出孔眼的过程，主要包括：\n" +
                "1. 钻井工艺：旋转钻井、冲击钻井、定向钻井\n" +
                "2. 钻井液：水基钻井液、油基钻井液、气体钻井液\n" +
                "3. 固井技术：套管固井、尾管固井\n" +
                "4. 井控技术：一级井控、二级井控、三级井控");

        PETROLEUM_KNOWLEDGE.put("油气储运", "油气储运是指石油和天然气的储存与运输，主要包括：\n" +
                "1. 管道输送：长输管道、集输管道、城市管网\n" +
                "2. 储罐类型：拱顶罐、浮顶罐、球罐\n" +
                "3. 液化天然气：LNG接收站、LNG运输船\n" +
                "4. 油库管理：油品收发、计量、安全防护");

        PETROLEUM_KNOWLEDGE.put("地质勘探", "石油地质勘探是寻找油气藏的过程，主要包括：\n" +
                "1. 地质调查：野外地质、遥感地质\n" +
                "2. 地球物理勘探：地震勘探、重力勘探、磁法勘探\n" +
                "3. 地球化学勘探：烃类检测、微生物勘探\n" +
                "4. 钻井勘探：探井、评价井、开发井");

        PETROLEUM_KNOWLEDGE.put("石油化工", "石油化工是以石油和天然气为原料的化学工业，主要包括：\n" +
                "1. 炼油工艺：常减压蒸馏、催化裂化、加氢裂化\n" +
                "2. 乙烯生产：管式炉裂解、蒸汽裂解\n" +
                "3. 芳烃生产：催化重整、芳烃抽提\n" +
                "4. 合成树脂：聚乙烯、聚丙烯、聚氯乙烯");
    }

    @Override
    @Transactional
    public Result<Map<String, Object>> chat(Long userId, String question) {
        try {
            String aiAnswer;
            String knowledgePoint = null;

            aiAnswer = matchKnowledgeBase(question);

            if (aiAnswer == null) {
                aiAnswer = callAIAPI(question);
            }

            knowledgePoint = extractKnowledgePoint(question);

            AIChat chat = new AIChat();
            chat.setUserId(userId);
            chat.setUserQuestion(question);
            chat.setAiAnswer(aiAnswer);
            chat.setKnowledgePoint(knowledgePoint);
            chat.setCreateTime(LocalDateTime.now());

            aiMapper.insert(chat);

            Map<String, Object> result = new HashMap<>();
            result.put("question", question);
            result.put("answer", aiAnswer);
            result.put("knowledgePoint", knowledgePoint);
            result.put("timestamp", LocalDateTime.now());

            return Result.success(result);
        } catch (Exception e) {
            throw new BusinessException("AI服务调用失败：" + e.getMessage());
        }
    }

    private String matchKnowledgeBase(String question) {
        for (Map.Entry<String, String> entry : PETROLEUM_KNOWLEDGE.entrySet()) {
            if (question.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private String callAIAPI(String question) {
        try {
            return generateMockAIResponse(question);
        } catch (Exception e) {
            return "抱歉，AI服务暂时不可用。关于\"" + question + "\"的问题，建议您查看相关课程资料或咨询专业教师。";
        }
    }

    private String generateMockAIResponse(String question) {
        if (question.contains("钻井液") || question.contains("泥浆")) {
            return "钻井液（又称泥浆）是钻井过程中不可或缺的循环流体...";
        } else if (question.contains("地震勘探") || question.contains("地震")) {
            return "地震勘探是石油勘探中最主要的地球物理方法...";
        } else if (question.contains("实训报告") || question.contains("实验报告")) {
            return "实训报告撰写框架：...";
        } else {
            return "关于您提出的问题，建议从以下几个方面学习：...";
        }
    }

    private String extractKnowledgePoint(String question) {
        if (question.contains("钻井") || question.contains("钻井液") || question.contains("固井")) {
            return "钻井工程";
        } else if (question.contains("储运") || question.contains("管道") || question.contains("储罐")) {
            return "油气储运";
        } else if (question.contains("地质") || question.contains("勘探") || question.contains("地震")) {
            return "地质勘探";
        } else if (question.contains("化工") || question.contains("炼油") || question.contains("裂化")) {
            return "石油化工";
        }
        return "综合知识";
    }

    @Override
    public Result<?> getChatHistory(Long userId) {
        List<AIChat> chatHistory = aiMapper.selectByUserId(userId);
        return Result.success(chatHistory);
    }

    @Override
    public Result<?> analyzeWeakPoints(Long userId) {
        List<String> knowledgePoints = aiMapper.selectKnowledgePoints(userId);
        Map<String, Long> frequency = knowledgePoints.stream()
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));

        Map<String, Object> analysis = new HashMap<>();
        analysis.put("totalQuestions", knowledgePoints.size());
        analysis.put("knowledgeDistribution", frequency);
        analysis.put("suggestion", generateSuggestion(frequency));
        return Result.success(analysis);
    }

    private String generateSuggestion(Map<String, Long> frequency) {
        StringBuilder suggestion = new StringBuilder("根据您的提问记录，学习建议如下：\n\n");
        String mostAsked = frequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
        suggestion.append("1. 您最常咨询的知识点是：").append(mostAsked).append("，建议重点复习该方向相关课程。\n");
        return suggestion.toString();
    }

    @Override
    public Result<?> generateStudyPlan(Long userId) {
        Map<String, Object> studyPlan = new HashMap<>();
        studyPlan.put("title", "个性化学习方案");
        studyPlan.put("period", "4周");
        return Result.success(studyPlan);
    }

    @Override
    public Result<?> generateReportFramework(String topic) {
        Map<String, Object> framework = new HashMap<>();
        framework.put("title", topic + " 实训报告");
        return Result.success(framework);
    }

    @Override
    @Transactional
    public Result<?> clearHistory(Long userId) {
        List<AIChat> chats = aiMapper.selectByUserId(userId);
        for (AIChat chat : chats) {
            aiMapper.deleteById(chat.getChatId());
        }
        return Result.success("对话历史已清空");
    }
}