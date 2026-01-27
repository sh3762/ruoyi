package com.ruoyi.zbh.domain;

import java.io.Serializable;

public class SystemStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bigDataIndex; // 大数据指标
    private String collectorOffline; // 采集器离线
    private String networkWarning; // 大网管报警
    private String dataLakeStatus; // 数据湖状态
    private String errorMessages; // 错误信息
    
    private String updateTime; // 服务端最近软件更新
    private String checkTime; // 检测时间
    private Integer pcCount; // 计算机数量
    private Integer softwareCount; // 软件记录数量
    
    // Extra field for data lake time diff to handle color in frontend
    private Integer dataLakeDiff;

    // Getters and Setters
    public String getBigDataIndex() { return bigDataIndex; }
    public void setBigDataIndex(String bigDataIndex) { this.bigDataIndex = bigDataIndex; }

    public String getCollectorOffline() { return collectorOffline; }
    public void setCollectorOffline(String collectorOffline) { this.collectorOffline = collectorOffline; }

    public String getNetworkWarning() { return networkWarning; }
    public void setNetworkWarning(String networkWarning) { this.networkWarning = networkWarning; }

    public String getDataLakeStatus() { return dataLakeStatus; }
    public void setDataLakeStatus(String dataLakeStatus) { this.dataLakeStatus = dataLakeStatus; }

    public String getErrorMessages() { return errorMessages; }
    public void setErrorMessages(String errorMessages) { this.errorMessages = errorMessages; }

    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public String getCheckTime() { return checkTime; }
    public void setCheckTime(String checkTime) { this.checkTime = checkTime; }

    public Integer getPcCount() { return pcCount; }
    public void setPcCount(Integer pcCount) { this.pcCount = pcCount; }

    public Integer getSoftwareCount() { return softwareCount; }
    public void setSoftwareCount(Integer softwareCount) { this.softwareCount = softwareCount; }
    
    public Integer getDataLakeDiff() { return dataLakeDiff; }
    public void setDataLakeDiff(Integer dataLakeDiff) { this.dataLakeDiff = dataLakeDiff; }
}
