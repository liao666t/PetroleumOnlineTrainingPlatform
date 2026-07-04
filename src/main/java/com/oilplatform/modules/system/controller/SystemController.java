package com.oilplatform.modules.system.controller;

import com.oilplatform.common.result.Result;
import com.oilplatform.modules.system.entity.OperLog;
import com.oilplatform.modules.system.service.SystemService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping("/logs")
    public Result<Page<OperLog>> getOperLogs(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String operType,
            @RequestParam(required = false) String keyword) {
        return systemService.getOperLogs(page, size, operType, keyword);
    }

    @DeleteMapping("/logs/clear/{days}")
    public Result<?> clearLogs(@PathVariable Integer days) {
        return systemService.clearLogs(days);
    }

    @PostMapping("/backup")
    public Result<?> backupDatabase() {
        return systemService.backupDatabase();
    }
}