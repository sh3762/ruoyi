package com.ruoyi.zbh.domain;

import java.io.Serializable;

public class NonGenuineSoftware implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ip;
    private String softName;
    private String version;
    private String isUpdate;
    private String installTime;
    private String corp;

    // Getters and Setters
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getSoftName() { return softName; }
    public void setSoftName(String softName) { this.softName = softName; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getIsUpdate() { return isUpdate; }
    public void setIsUpdate(String isUpdate) { this.isUpdate = isUpdate; }

    public String getInstallTime() { return installTime; }
    public void setInstallTime(String installTime) { this.installTime = installTime; }

    public String getCorp() { return corp; }
    public void setCorp(String corp) { this.corp = corp; }
}
