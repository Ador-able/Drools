package com.zyc.fact.model;

public class SysHead {
    private String glbSeqno;
    private String sysSeqno;
    private String sourceSysid;
    private String serverId;
    private String version;
    private String ruleNo;
    private String retCode;
    private String retMsg;

    public String getGlbSeqno() {
        return glbSeqno;
    }

    public void setGlbSeqno(String glbSeqno) {
        this.glbSeqno = glbSeqno;
    }

    public String getSysSeqno() {
        return sysSeqno;
    }

    public void setSysSeqno(String sysSeqno) {
        this.sysSeqno = sysSeqno;
    }

    public String getSourceSysid() {
        return sourceSysid;
    }

    public void setSourceSysid(String sourceSysid) {
        this.sourceSysid = sourceSysid;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRuleNo() {
        return ruleNo;
    }

    public void setRuleNo(String ruleNo) {
        this.ruleNo = ruleNo;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }



    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    @Override
    public String toString() {
        return "SysHead{" +
                "glbSeqno='" + glbSeqno + '\'' +
                ", sysSeqno='" + sysSeqno + '\'' +
                ", sourceSysid='" + sourceSysid + '\'' +
                ", serverId='" + serverId + '\'' +
                ", version='" + version + '\'' +
                ", ruleNo='" + ruleNo + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                '}';
    }

}
