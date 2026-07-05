package com.oilplatform.modules.ai.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.ai.entity.AIChat;
import com.oilplatform.modules.ai.mapper.AIMapper;
import com.oilplatform.modules.ai.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ai.api.key:demo-key}")
    private String apiKey;

    @Value("${ai.api.url:https://api.deepseek.com/v1/chat/completions}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 石油专业知识库（本地备用）
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
            String knowledgePoint;

            // 1. 优先调用远程 DeepSeek API
            Map<String, Object> remoteResult = callRemoteAPI(question);
            if (remoteResult != null) {
                aiAnswer = (String) remoteResult.get("answer");
                knowledgePoint = (String) remoteResult.getOrDefault("knowledgePoint", "综合知识");
            } else {
                // 2. 远程失败，回退到本地知识库
                aiAnswer = matchKnowledgeBase(question);
                if (aiAnswer == null) {
                    // 本地也未命中，给出兜底回答
                    aiAnswer = "抱歉，没有找到与您问题直接相关的本地知识。建议尝试更换关键词或咨询专业教师。";
                }
                knowledgePoint = extractKnowledgePoint(question);
            }

            // 3. 保存对话记录
            AIChat chat = new AIChat();
            chat.setUserId(userId);
            chat.setUserQuestion(question);
            chat.setAiAnswer(aiAnswer);
            chat.setKnowledgePoint(knowledgePoint);
            chat.setCreateTime(LocalDateTime.now());
            aiMapper.insert(chat);

            // 4. 返回结果
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

    /**
     * 调用远程 DeepSeek API，成功返回回答 Map，失败返回 null
     */
    private Map<String, Object> callRemoteAPI(String question) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            List<Map<String, String>> messages = new ArrayList<>();
            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", "你是一个石油专业领域的智能助教，专注于钻井、油气储运、地质勘探和石油化工等领域的知识解答。" +
                    "请用专业、准确、易懂的方式回答问题。如果问题超出石油领域，请礼貌拒绝并引导用户回到专业学习。");
            messages.add(systemMessage);
            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", question);
            messages.add(userMessage);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "deepseek-chat");
            requestBody.put("messages", messages);
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.3);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode choices = root.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    String content = choices.get(0).path("message").path("content").asText();
                    Map<String, Object> result = new HashMap<>();
                    result.put("answer", content);
                    result.put("knowledgePoint", extractKnowledgePoint(question));
                    return result;
                }
            }
        } catch (Exception e) {
            // 远程调用失败，记录日志后可返回 null 触发本地回退
            System.err.println("远程API调用失败，回退到本地知识库: " + e.getMessage());
        }
        return null;
    }

    /**
     * 匹配本地石油知识库
     */
    private String matchKnowledgeBase(String question) {
        for (Map.Entry<String, String> entry : PETROLEUM_KNOWLEDGE.entrySet()) {
            if (question.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 简单知识点提取
     */
    private String extractKnowledgePoint(String question) {
        if (question == null) return "综合知识";
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

    // ==================== 其余方法保持不变 ====================
    @Override
    public Result<?> getChatHistory(Long userId) {
        List<AIChat> chatHistory = aiMapper.selectByUserId(userId);
        return Result.success(chatHistory);
    }

    @Override
    public Result<?> analyzeWeakPoints(Long userId) {
        List<String> knowledgePoints = aiMapper.selectKnowledgePoints(userId);
        Map<String, Long> frequency = knowledgePoints.stream()
                .filter(Objects::nonNull)
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
        suggestion.append("2. 结合闯关答题结果，查漏补缺薄弱环节。\n");
        suggestion.append("3. 建议每周至少完成一次实训报告，巩固理论知识。\n");
        suggestion.append("4. 积极参与课程问答，与师生交流讨论。\n");
        return suggestion.toString();
    }

    @Override
    public Result<?> generateStudyPlan(Long userId) {
        Map<String, Object> studyPlan = new HashMap<>();
        studyPlan.put("title", "个性化学习方案");
        studyPlan.put("period", "4周");
        List<Map<String, String>> weeklyPlans = new ArrayList<>();
        Map<String, String> week1 = new HashMap<>(); week1.put("week","第1周"); week1.put("content","基础知识回顾：复习四大专业方向基础知识"); week1.put("goal","掌握石油地质基础"); weeklyPlans.add(week1);
        Map<String, String> week2 = new HashMap<>(); week2.put("week","第2周"); week2.put("content","专业深化：学习油气储运和石油化工核心课程"); week2.put("goal","理解油气集输流程"); weeklyPlans.add(week2);
        Map<String, String> week3 = new HashMap<>(); week3.put("week","第3周"); week3.put("content","实训操作：完成实训报告，参与问答"); week3.put("goal","提交2份实训报告"); weeklyPlans.add(week3);
        Map<String, String> week4 = new HashMap<>(); week4.put("week","第4周"); week4.put("content","综合提升：攻克薄弱知识点，参加积分排行"); week4.put("goal","冲刺积分榜前50"); weeklyPlans.add(week4);
        studyPlan.put("weeklyPlans", weeklyPlans);
        studyPlan.put("tips", "每天学习1-2小时，坚持闯关答题");
        return Result.success(studyPlan);
    }

    @Override
    public Result<?> generateReportFramework(String topic) {
        Map<String, Object> framework = new HashMap<>();
        framework.put("title", topic + " 实训报告");
        List<Map<String, String>> sections = new ArrayList<>();
        Map<String, String> sec1 = new HashMap<>(); sec1.put("section","一、实训目的"); sec1.put("content","1. 掌握"+topic+"的基本原理\n2. 熟悉相关设备操作"); sections.add(sec1);
        Map<String, String> sec2 = new HashMap<>(); sec2.put("section","二、实训原理"); sec2.put("content","简述"+topic+"涉及的专业理论知识"); sections.add(sec2);
        Map<String, String> sec3 = new HashMap<>(); sec3.put("section","三、实训设备与材料"); sec3.put("content","列出使用的仪器设备和材料"); sections.add(sec3);
        Map<String, String> sec4 = new HashMap<>(); sec4.put("section","四、实训步骤"); sec4.put("content","按操作顺序描述"); sections.add(sec4);
        Map<String, String> sec5 = new HashMap<>(); sec5.put("section","五、数据记录与分析"); sec5.put("content","记录数据并进行分析"); sections.add(sec5);
        Map<String, String> sec6 = new HashMap<>(); sec6.put("section","六、实训结论"); sec6.put("content","总结实训成果"); sections.add(sec6);
        framework.put("sections", sections);
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