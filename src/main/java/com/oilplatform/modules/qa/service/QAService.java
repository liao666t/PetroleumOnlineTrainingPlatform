package com.oilplatform.modules.qa.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.qa.entity.Question;
import com.oilplatform.modules.qa.entity.Answer;
import java.util.List;

public interface QAService {

    Result<List<Question>> getQuestionsByCourse(Long courseId);

    Result<Question> getQuestionDetail(Long questionId);

    Result<List<Answer>> getAnswers(Long questionId);

    Result<?> createQuestion(Question question);

    Result<?> createAnswer(Answer answer);

    Result<?> acceptAnswer(Long answerId);

    Result<?> topQuestion(Long questionId);

    Result<?> deleteQuestion(Long questionId);

    Result<List<Question>> getMyQuestions(Long userId);
}