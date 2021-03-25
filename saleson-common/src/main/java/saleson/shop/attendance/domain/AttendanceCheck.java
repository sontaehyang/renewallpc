package saleson.shop.attendance.domain;

public class AttendanceCheck {
    private long attendanceCheckId;
    private long attendanceId;
    private long userId;
    private String checkedDate;
    private String checkedTime;

    public long getAttendanceCheckId() {
        return attendanceCheckId;
    }

    public void setAttendanceCheckId(long attendanceCheckId) {
        this.attendanceCheckId = attendanceCheckId;
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
}
