package com.ruoyi.kq.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.kq.service.IKqService;

/**
 * 考勤模块 Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/kq")
public class KqController extends BaseController
{
    @Autowired
    private IKqService kqService;

    /**
     * 查询海康刷脸数据
     */
    @PreAuthorize("@ss.hasPermi('kq:view')")
    @GetMapping("/face/data")
    public AjaxResult getFaceData()
    {
        Map<String, Object> data = kqService.selectFaceHkLogData();
        return AjaxResult.success(data);
    }
}
