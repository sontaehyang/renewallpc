package saleson.shop.attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class Attendance {
	private long attendanceId;
	private String year;
	private String month;
	private String contentTop;
	private String contentBottom;
	private String updatedBy;
	private String updatedDate;
	private String createdBy;
	private String createdDate;

	private List<AttendanceConfig> attendanceConfigs = new ArrayList<>();

    private String [] eventCode;
    private int [] days;
    private String [] continueYn;

    public String[] getEventCode() {
        return eventCode;
    }

	public List<AttendanceConfig> getAttendanceConfigs() {
		return attendanceConfigs;
	}
    public void setEventCode(String[] eventCode) {
        this.eventCode = eventCode;
    }

	public void setAttendanceConfigs(List<AttendanceConfig> attendanceConfigs) {
		this.attendanceConfigs = attendanceConfigs;
	}
    public int[] getDays() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public String[] getContinueYn() {
        return continueYn;
    }

    public void setContinueYn(String[] continueYn) {
        this.continueYn = continueYn;
    }

	public long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getContentTop() {
        return contentTop;
    }

    public void setContentTop(String contentTop) {
        this.contentTop = contentTop;
    }

    public String getContentBottom() {
        return contentBottom;
    }

    public void setContentBottom(String contentBottom) {
        this.contentBottom = contentBottom;
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
