package com.oilplatform.modules.qa.service.impl;

import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.qa.entity.Question;
import com.oilplatform.modules.qa.entity.Answer;
import com.oilplatform.modules.qa.mapper.AnswerMapper;
import com.oilplatform.modules.qa.mapper.QAMapper;
import com.oilplatform.modules.qa.service.QAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QAServiceImpl implements QAService {

    @Autowired
    private QAMapper qaMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Result<List<Question>> getQuestionsByCourse(Long courseId) {
        List<Question> questions = qaMapper.selectQuestionsByCourseId(courseId);
        return Result.success(questions);
    }

    @Override
    public Result<Question> getQuestionDetail(Long questionId) {
        Question question = qaMapper.selectById(questionId);
        if (question == null) {
            throw new BusinessException("问题不存在");
        }
        return Result.success(question);
    }

    @Override
    public Result<List<Answer>> getAnswers(Long questionId) {
        List<Answer> answers = answerMapper.selectByQuestionId(questionId);
        return Result.success(answers);
    }

    @Override
    @Transactional
    public Result<?> createQuestion(Question question) {
        question.setStatus(1);
        question.setIsTop(0);
        qaMapper.insert(question);
        return Result.success("提问成功");
    }

    @Override
    @Transactional
    public Result<?> createAnswer(Answer answer) {
        Question question = qaMapper.selectById(answer.getQuestionId());
        if (question == null) {
            throw new BusinessException("问题不存在");
        }
        answer.setIsAccepted(0);
        answerMapper.insert(answer);
        return Result.success("回复成功");
    }

    @Override
    @Transactional
    public Result<?> acceptAnswer(Long answerId) {
        Answer answer = answerMapper.selectById(answerId);
        if (answer == null) {
            throw new BusinessException("回答不存在");
        }

        List<Answer> answers = answerMapper.selectByQuestionId(answer.getQuestionId());
        for (Answer a : answers) {
            a.setIsAccepted(0);
            answerMapper.updateById(a);
        }

        answer.setIsAccepted(1);
        answerMapper.updateById(answer);

        return Result.success("已采纳该回答");
    }

    @Override
    @Transactional
    public Result<?> topQuestion(Long questionId) {
        Question question = qaMapper.selectById(questionId);
        if (question == null) {
            throw new BusinessException("问题不存在");
        }
        question.setIsTop(1);
        qaMapper.updateById(question);
        return Result.success("置顶成功");
    }

    @Override
    @Transactional
    public Result<?> deleteQuestion(Long questionId) {
        Question question = qaMapper.selectById(questionId);
        if (question == null) {
            throw new BusinessException("问题不存在");
        }
        qaMapper.deleteById(questionId);
        return Result.success("删除成功");
    }

    @Override
    public Result<List<Question>> getMyQuestions(Long userId) {
        List<Question> questions = qaMapper.selectQuestionsByUserId(userId);
        return Result.success(questions);
    }
}