package com.ruoyi.zbh.domain;

import java.io.Serializable;

public class YunLeiServer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String hostName;
    private String serverIp;
    private String serverScore;
    private String lastUpdateTime;
    private String tm;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getServerIp() { return serverIp; }
    public void setServerIp(String serverIp) { this.serverIp = serverIp; }

    public String getServerScore() { return serverScore; }
    public void setServerScore(String serverScore) { this.serverScore = serverScore; }

    public String getLastUpdateTime() { return lastUpdateTime; }
    public void setLastUpdateTime(String lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }

    public String getTm() { return tm; }
    public void setTm(String tm) { this.tm = tm; }
}
