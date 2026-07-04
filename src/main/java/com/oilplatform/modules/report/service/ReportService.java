package com.oilplatform.modules.report.service;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.report.entity.TrainingReport;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

public interface ReportService {

    Result<?> submitReport(TrainingReport report);

    Result<?> updateReport(TrainingReport report);

    Result<List<TrainingReport>> getMyReports(Long userId);

    Result<TrainingReport> getReportDetail(Long reportId);

    Result<Page<TrainingReport>> getUnreviewedReports(Integer page, Integer size, Long courseId);

    Result<?> reviewReport(Long reportId, Double score, String comment, Long reviewerId);

    Result<List<TrainingReport>> getReviewedReports(Long reviewerId);
}