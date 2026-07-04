package com.oilplatform.modules.system.service;

import com.oilplatform.common.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oilplatform.modules.system.entity.OperLog;

public interface SystemService {

    Result<Page<OperLog>> getOperLogs(Integer page, Integer size, String operType, String keyword);

    Result<?> clearLogs(Integer days);

    Result<?> backupDatabase();
}