package saleson.shop.attendance.domain;

public class AttendanceConfig {

    private long attendanceConfigId;
    private long attendanceId;
    private String eventCode;
    private String continueYn;					// 연속 출석 여부
    private int days;
    private String updatedBy;
    private String updatedDate;
    private String createdBy;
    private String createdDate;

    public long getAttendanceConfigId() {
        return attendanceConfigId;
    }

    public void setAttendanceConfigId(long attendanceConfigId) {
        this.attendanceConfigId = attendanceConfigId;
    }

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getContinueYn() {
        return continueYn;
    }

    public void setContinueYn(String continueYn) {
        this.continueYn = continueYn;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
