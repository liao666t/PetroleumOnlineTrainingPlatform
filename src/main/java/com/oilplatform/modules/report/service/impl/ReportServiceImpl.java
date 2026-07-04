package com.oilplatform.modules.report.service.impl;

import com.oilplatform.common.exception.BusinessException;
import com.oilplatform.common.result.Result;
import com.oilplatform.modules.report.entity.TrainingReport;
import com.oilplatform.modules.report.mapper.ReportMapper;
import com.oilplatform.modules.report.service.ReportService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    @Transactional
    public Result<?> submitReport(TrainingReport report) {
        report.setReviewStatus(0);
        report.setCreateTime(LocalDateTime.now());
        reportMapper.insert(report);
        return Result.success("实训报告提交成功");
    }

    @Override
    @Transactional
    public Result<?> updateReport(TrainingReport report) {
        TrainingReport existReport = reportMapper.selectById(report.getReportId());
        if (existReport == null) {
            throw new BusinessException("实训报告不存在");
        }
        if (existReport.getReviewStatus() == 1) {
            throw new BusinessException("已批阅的报告不可修改");
        }
        reportMapper.updateById(report);
        return Result.success("实训报告更新成功");
    }

    @Override
    public Result<List<TrainingReport>> getMyReports(Long userId) {
        List<TrainingReport> reports = reportMapper.selectByUserId(userId);
        return Result.success(reports);
    }

    @Override
    public Result<TrainingReport> getReportDetail(Long reportId) {
        TrainingReport report = reportMapper.selectById(reportId);
        if (report == null) {
            throw new BusinessException("实训报告不存在");
        }
        return Result.success(report);
    }

    @Override
    public Result<Page<TrainingReport>> getUnreviewedReports(Integer page, Integer size, Long courseId) {
        Page<TrainingReport> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<TrainingReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TrainingReport::getReviewStatus, 0);
        if (courseId != null) {
            wrapper.eq(TrainingReport::getCourseId, courseId);
        }
        wrapper.orderByDesc(TrainingReport::getCreateTime);

        Page<TrainingReport> reportPage = reportMapper.selectPage(pageParam, wrapper);
        return Result.success(reportPage);
    }

    @Override
    @Transactional
    public Result<?> reviewReport(Long reportId, Double score, String comment, Long reviewerId) {
        TrainingReport report = reportMapper.selectById(reportId);
        if (report == null) {
            throw new BusinessException("实训报告不存在");
        }
        if (report.getReviewStatus() == 1) {
            throw new BusinessException("该报告已批阅");
        }

        report.setScore(new BigDecimal(score));
        report.setReviewComment(comment);
        report.setReviewerId(reviewerId);
        report.setReviewStatus(1);
        report.setReviewTime(LocalDateTime.now());

        reportMapper.updateById(report);
        return Result.success("批阅成功");
    }

    @Override
    public Result<List<TrainingReport>> getReviewedReports(Long reviewerId) {
        List<TrainingReport> reports = reportMapper.selectByReviewerId(reviewerId);
        return Result.success(reports);
    }
}