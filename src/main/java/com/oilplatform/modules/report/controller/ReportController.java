package com.oilplatform.modules.report.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.report.entity.TrainingReport;
import com.oilplatform.modules.report.service.ReportService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/submit")
    public Result<?> submitReport(@RequestBody TrainingReport report, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        report.setUserId(userId);
        return reportService.submitReport(report);
    }

    @PutMapping("/update")
    public Result<?> updateReport(@RequestBody TrainingReport report) {
        return reportService.updateReport(report);
    }

    @GetMapping("/my")
    public Result<List<TrainingReport>> getMyReports(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return reportService.getMyReports(userId);
    }

    @GetMapping("/detail/{reportId}")
    public Result<TrainingReport> getReportDetail(@PathVariable Long reportId) {
        return reportService.getReportDetail(reportId);
    }

    @GetMapping("/unreviewed")
    public Result<Page<TrainingReport>> getUnreviewedReports(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long courseId) {
        return reportService.getUnreviewedReports(page, size, courseId);
    }

    @PostMapping("/review/{reportId}")
    public Result<?> reviewReport(@PathVariable Long reportId,
                                  @RequestBody Map<String, Object> reviewData,
                                  HttpServletRequest request) {
        Long reviewerId = (Long) request.getAttribute("userId");
        Double score = Double.valueOf(reviewData.get("score").toString());
        String comment = (String) reviewData.get("comment");
        return reportService.reviewReport(reportId, score, comment, reviewerId);
    }

    @GetMapping("/reviewed")
    public Result<List<TrainingReport>> getReviewedReports(HttpServletRequest request) {
        Long reviewerId = (Long) request.getAttribute("userId");
        return reportService.getReviewedReports(reviewerId);
    }
}