package com.oilplatform.modules.recommend.service.impl;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.recommend.algorithm.CollaborativeFilter;
import com.oilplatform.modules.recommend.service.RecommendService;
import com.oilplatform.modules.comment.entity.Comment;
import com.oilplatform.modules.comment.mapper.CommentMapper;
import com.oilplatform.modules.study.entity.UserCourse;
import com.oilplatform.modules.study.mapper.StudyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private CollaborativeFilter collaborativeFilter;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private StudyMapper studyMapper;

    @Override
    public Result<List<Long>> getRecommendedCourses(Long userId) {
        Map<Long, Map<Long, Double>> userItemMatrix = buildUserItemMatrix();
        List<Long> recommendedCourses = collaborativeFilter.recommend(userItemMatrix, userId, 5);

        if (recommendedCourses.size() < 5) {
            List<Long> popularCourses = getPopularCourses(5 - recommendedCourses.size());
            for (Long courseId : popularCourses) {
                if (!recommendedCourses.contains(courseId)) {
                    recommendedCourses.add(courseId);
                }
            }
        }
        return Result.success(recommendedCourses);
    }

    @Override
    public Result<List<Long>> getSimilarCourses(Long courseId) {
        List<Long> similarCourses = findSimilarByCategory(courseId, 5);
        return Result.success(similarCourses);
    }

    private Map<Long, Map<Long, Double>> buildUserItemMatrix() {
        Map<Long, Map<Long, Double>> matrix = new HashMap<>();
        List<Comment> comments = commentMapper.selectList(null);
        for (Comment comment : comments) {
            Long userId = comment.getUserId();
            Long courseId = comment.getCourseId();
            Double score = comment.getScore().doubleValue();
            matrix.computeIfAbsent(userId, k -> new HashMap<>()).put(courseId, score);
        }
        List<UserCourse> userCourses = studyMapper.selectList(null);
        for (UserCourse uc : userCourses) {
            Long userId = uc.getUserId();
            Long courseId = uc.getCourseId();
            if (!matrix.containsKey(userId) || !matrix.get(userId).containsKey(courseId)) {
                matrix.computeIfAbsent(userId, k -> new HashMap<>()).put(courseId, 3.0);
            }
        }
        return matrix;
    }

    private List<Long> getPopularCourses(int limit) {
        List<UserCourse> userCourses = studyMapper.selectList(null);
        Map<Long, Long> courseCount = userCourses.stream()
                .collect(Collectors.groupingBy(UserCourse::getCourseId, Collectors.counting()));
        return courseCount.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Long> findSimilarByCategory(Long courseId, int limit) {
        return new ArrayList<>();
    }
}