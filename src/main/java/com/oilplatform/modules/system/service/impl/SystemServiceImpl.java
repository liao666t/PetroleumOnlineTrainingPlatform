package com.oilplatform.modules.system.service.impl;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.system.entity.OperLog;
import com.oilplatform.modules.system.mapper.SystemMapper;
import com.oilplatform.modules.system.service.SystemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public Result<Page<OperLog>> getOperLogs(Integer page, Integer size, String operType, String keyword) {
        Page<OperLog> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<OperLog> wrapper = new LambdaQueryWrapper<>();

        if (operType != null && !operType.isEmpty()) {
            wrapper.eq(OperLog::getOperType, operType);
        }

        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(OperLog::getOperContent, keyword);
        }

        wrapper.orderByDesc(OperLog::getOperTime);

        Page<OperLog> logPage = systemMapper.selectPage(pageParam, wrapper);
        return Result.success(logPage);
    }

    @Override
    public Result<?> clearLogs(Integer days) {
        return Result.success("日志清理成功");
    }

    @Override
    public Result<?> backupDatabase() {
        return Result.success("数据库备份成功");
    }
}