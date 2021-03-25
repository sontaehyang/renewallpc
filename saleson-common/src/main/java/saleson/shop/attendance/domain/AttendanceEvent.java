package saleson.shop.attendance.domain;

public class AttendanceEvent {

    private long attendanceEventId;
    private long attendanceId;
    private long userId;
    private String eventCode;
    private String continueYn;
    private int days;
    private int checkedDays;
    private String successYn;
    private String updatedDate;

	public AttendanceEvent() {
	}

	public AttendanceEvent(AttendanceConfig config) {
		this.attendanceId = config.getAttendanceId();
		this.eventCode = config.getEventCode();
		this.continueYn = config.getContinueYn();
		this.days = config.getDays();
		this.successYn = "N";
	}

	public long getAttendanceEventId() {
        return attendanceEventId;
    }

    public void setAttendanceEventId(long attendanceEventId) {
        this.attendanceEventId = attendanceEventId;
    }

    public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public int getCheckedDays() {
        return checkedDays;
    }

    public void setCheckedDays(int checkedDays) {
        this.checkedDays = checkedDays;
    }

    public String getSuccessYn() {
        return successYn;
    }

    public void setSuccessYn(String successYn) {
        this.successYn = successYn;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
