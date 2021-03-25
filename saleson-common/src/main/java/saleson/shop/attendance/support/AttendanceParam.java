package saleson.shop.attendance.support;

import com.onlinepowers.framework.web.domain.SearchParam;


public class AttendanceParam extends SearchParam {

    private Long attendanceId;

	private Long []Ids;
	private String year;
	private String month;
	private String contentTop;
	private String contentBottom;

	// AttendanceCheck
    private Long attendanceCheckId;
    private Long userId;
    private String checkedDate;
    private String checkedTime;

    // AttendanceConfig
    private Long attendanceConfigId;
    private String eventCode;
    private String continueYn;
    private int days;

    // AttendanceEvent
    private Long attendanceEventId;
    private int checkedDays;
    private String successYn;

	private String updatedBy;
	private String updatedDate;
	private String createdBy;
	private String createdDate;

    private String startYear;
    private String startMonth;
    private String endYear;
    private String endMonth;

	private String yearMonth;

	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

    public Long[] getIds() {
        return Ids;
    }

    public void setIds(Long[] ids) {
        Ids = ids;
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

	public Long getAttendanceCheckId() {
		return attendanceCheckId;
	}

	public void setAttendanceCheckId(Long attendanceCheckId) {
		this.attendanceCheckId = attendanceCheckId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(String checkedDate) {
		this.checkedDate = checkedDate;
	}

	public String getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(String checkedTime) {
		this.checkedTime = checkedTime;
	}

	public Long getAttendanceConfigId() {
		return attendanceConfigId;
	}

	public void setAttendanceConfigId(Long attendanceConfigId) {
		this.attendanceConfigId = attendanceConfigId;
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

	public Long getAttendanceEventId() {
		return attendanceEventId;
	}

	public void setAttendanceEventId(Long attendanceEventId) {
		this.attendanceEventId = attendanceEventId;
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

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
}

