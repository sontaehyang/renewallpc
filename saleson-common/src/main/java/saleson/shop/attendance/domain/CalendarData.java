package saleson.shop.attendance.domain;

import com.onlinepowers.framework.util.StringUtils;

public class CalendarData {
	private int year;
	private int month;
	private int day;
	private int dayOfWeek;
	private boolean checked = false;
	private boolean disabled = false;
	private String checkedDate;

	public CalendarData(int year, int month, int day, int dayOfWeek) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.dayOfWeek = dayOfWeek;
	}

	public CalendarData(int year, int month, int day, int dayOfWeek, boolean disabled) {
		this(year, month, day, dayOfWeek);
		this.disabled = disabled;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(String checkedDate) {
		this.checkedDate = checkedDate;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * 날짜를 yyyyMMdd 형식으로 리턴함.
	 * @return
	 */
	public String getDate() {
		return new StringBuilder()
				.append(year)
				.append(StringUtils.lPad("" + month, 2, '0'))
				.append(StringUtils.lPad("" + day, 2, '0'))
				.toString();
	}

	@Override
	public String toString() {
		return "CalendarData{" +
				"year=" + year +
				", month=" + month +
				", day=" + day +
				", dayOfWeek=" + dayOfWeek +
				", checked=" + checked +
				", disabled=" + disabled +
				", checkedDate='" + checkedDate + '\'' +
				'}';
	}
}
