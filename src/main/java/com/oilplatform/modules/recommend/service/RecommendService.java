package com.oilplatform.modules.recommend.service;

import com.oilplatform.common.result.Result;
import java.util.List;

public interface RecommendService {

    Result<List<Long>> getRecommendedCourses(Long userId);

    Result<List<Long>> getSimilarCourses(Long courseId);
}