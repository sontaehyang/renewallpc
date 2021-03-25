package saleson.shop.calendar.domain;


public class Calendar {

    private int calendarId;
    private String calendarYear;
    private String calendarMonth;
    private String calendarDay;
    private String subject;
    private String hday;

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int i) {
        this.calendarId = i;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHday() {
        return hday;
    }

    public void setHday(String hday) {
        this.hday = hday;
    }

    public String getCalendarYear() {
        return calendarYear;
    }

    public void setCalendarYear(String calendarYear) {
        this.calendarYear = calendarYear;
    }

    public String getCalendarMonth() {
        return calendarMonth;
    }

    public void setCalendarMonth(String calendarMonth) {
        this.calendarMonth = calendarMonth;
    }

    public String getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(String calendarDay) {
        this.calendarDay = calendarDay;
    }


}
