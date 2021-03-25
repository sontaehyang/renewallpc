package saleson.shop.log.domain;

public class ChangeLog {

    private int changeLogId;
    private long userId;
    private long managerId;
    private String parameter;
    private String createdDate;
    private String remoteAddr;

    public ChangeLog() {
    }

    public ChangeLog(long userId, long managerId, String parameter, String remoteAddr) {
        this.userId = userId;
        this.managerId = managerId;
        this.parameter = parameter;
        this.remoteAddr = remoteAddr;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public int getChangeLogId() {
        return changeLogId;
    }

    public void setChangeLogId(int changeLogId) {
        this.changeLogId = changeLogId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

}
