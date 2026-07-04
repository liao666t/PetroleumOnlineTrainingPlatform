package com.oilplatform.modules.study.service;

import com.oilplatform.common.result.Result;
import java.util.Map;

public interface StudyService {
    // 原有接口保留（空）

    /**
     * 获取我的选课列表（分页，包含学习进度）
     * @param userId 用户ID
     * @param page 当前页
     * @param size 每页大小
     * @return 分页数据
     */
    Result<?> getMyEnrolledCourses(Long userId, Integer page, Integer size);

    /**
     * 获取最近学习记录（按最近学习时间排序的前N条）
     * @param userId 用户ID
     * @param limit 记录数
     * @return 学习记录列表
     */
    Result<?> getRecentStudyRecords(Long userId, Integer limit);

    /**
     * 获取个人学习统计概览
     * @param userId 用户ID
     * @return 统计数据
     */
    Result<Map<String, Object>> getStudyOverview(Long userId);

    /**
     * 收藏或取消收藏课程
     * @param userId 用户ID
     * @param courseId 课程ID
     * @param isFavorite 是否收藏（true-收藏，false-取消）
     * @return 操作结果
     */
    Result<?> toggleFavorite(Long userId, Long courseId, boolean isFavorite);

    /**
     * 获取我的收藏课程列表
     * @param userId 用户ID
     * @param page 当前页
     * @param size 每页大小
     * @return 分页数据
     */
    Result<?> getFavoriteCourses(Long userId, Integer page, Integer size);
}