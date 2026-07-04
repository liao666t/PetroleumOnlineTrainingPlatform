package com.oilplatform.modules.qa.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.qa.entity.Question;
import com.oilplatform.modules.qa.entity.Answer;
import com.oilplatform.modules.qa.service.QAService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qa")
public class QAController {

    @Autowired
    private QAService qaService;

    @GetMapping("/questions/{courseId}")
    public Result<List<Question>> getQuestionsByCourse(@PathVariable Long courseId) {
        return qaService.getQuestionsByCourse(courseId);
    }

    @GetMapping("/question/{questionId}")
    public Result<Question> getQuestionDetail(@PathVariable Long questionId) {
        return qaService.getQuestionDetail(questionId);
    }

    @GetMapping("/answers/{questionId}")
    public Result<List<Answer>> getAnswers(@PathVariable Long questionId) {
        return qaService.getAnswers(questionId);
    }

    @PostMapping("/question/create")
    public Result<?> createQuestion(@RequestBody Question question, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        question.setUserId(userId);
        return qaService.createQuestion(question);
    }

    @PostMapping("/answer/create")
    public Result<?> createAnswer(@RequestBody Answer answer, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        answer.setUserId(userId);
        return qaService.createAnswer(answer);
    }

    @PutMapping("/answer/accept/{answerId}")
    public Result<?> acceptAnswer(@PathVariable Long answerId) {
        return qaService.acceptAnswer(answerId);
    }

    @PutMapping("/question/top/{questionId}")
    public Result<?> topQuestion(@PathVariable Long questionId) {
        return qaService.topQuestion(questionId);
    }

    @DeleteMapping("/question/delete/{questionId}")
    public Result<?> deleteQuestion(@PathVariable Long questionId) {
        return qaService.deleteQuestion(questionId);
    }

    @GetMapping("/my-questions")
    public Result<List<Question>> getMyQuestions(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return qaService.getMyQuestions(userId);
    }
}