package saleson.shop.stats.domain;

public class MonthStats {
	private String yearMonth;
	private String title;
	private String monthCount;
	private String percent;
	private String kbLoginCount;
	private String custLoginCount;
	
	
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMonthCount() {
		return monthCount;
	}
	public void setMonthCount(String monthCount) {
		this.monthCount = monthCount;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getKbLoginCount() {
		return kbLoginCount;
	}
	public void setKbLoginCount(String kbLoginCount) {
		this.kbLoginCount = kbLoginCount;
	}
	public String getCustLoginCount() {
		return custLoginCount;
	}
	public void setCustLoginCount(String custLoginCount) {
		this.custLoginCount = custLoginCount;
	}
}
