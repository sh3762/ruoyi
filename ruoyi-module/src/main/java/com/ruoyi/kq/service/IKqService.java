package com.ruoyi.kq.service;

import com.ruoyi.kq.domain.FaceHkLog;
import com.ruoyi.kq.domain.IceClock;
import com.ruoyi.kq.domain.SisTag;
import java.util.List;
import java.util.Map;

/**
 * 考勤模块Service接口
 * 
 * @author ruoyi
 */
public interface IKqService 
{
    /**
     * 查询ICE签到列表
     * 
     * @return 结果
     */
    public List<IceClock> selectIceClockList();

    /**
     * 查询海康刷脸列表 (按类型: 1=上班, 2=下班, 3=全部)
     * 
     * @param type 类型
     * @return 结果
     */
    public Map<String, Object> selectFaceHkLogData();

    /**
     * 查询SIS测点列表
     * 
     * @return 结果
     */
    public List<SisTag> selectSisTagList();
}
