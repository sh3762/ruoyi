package com.ruoyi.kq.mapper;

import com.ruoyi.kq.domain.FaceHkLog;
import com.ruoyi.kq.domain.IceClock;
import com.ruoyi.kq.domain.SisTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

/**
 * 考勤模块Mapper接口
 * 
 * @author ruoyi
 */
@Mapper
public interface KqMapper 
{
    /**
     * 查询ICE签到列表
     * 
     * @return 结果
     */
    @Select("SELECT name, srq, sc1, sc2, tm FROM ice_clock ORDER BY sc1")
    public List<IceClock> selectIceClockList();

    /**
     * 查询海康刷脸全部记录
     * 
     * @return 结果
     */
    @Select("SELECT name, event_time, created_at FROM face_hk_log ORDER BY event_time DESC")
    public List<FaceHkLog> selectFaceHkLogList();

    /**
     * 查询海康刷脸记录（自定义SQL支持复杂逻辑）
     * 
     * @param sql 动态SQL
     * @return 结果
     */
    @Select("${sql}")
    public List<Map<String, Object>> selectFaceHkLogBySql(String sql);

    /**
     * 查询SIS测点列表
     * 
     * @return 结果
     */
    @Select("SELECT id, tagname, `describe`, round(`value`,2) as value, tm FROM sis_tags ORDER by idx")
    public List<SisTag> selectSisTagList();
}
