package saleson.shop.stats.domain;

public class DayStats implements Comparable<DayStats> {
	private String visitDay;
	private String visitCount = "0";
	private String totalCount = "0";
	private String percent = "0";
	
	public DayStats() {}
	
	public DayStats(String visitDay) {
		this.visitDay = visitDay;
	}
	
	public String getVisitDay() {
		return visitDay;
	}
	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}
	public String getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(String visitCount) {
		this.visitCount = visitCount;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	
	public String getDisplayDay() {
		return Integer.toString(Integer.parseInt(visitDay));
	}
	
	@Override
	public String toString() {
		return visitDay;
	}

	@Override
	public int compareTo(DayStats o) {
		return visitDay.compareTo(o.getVisitDay());
	}
	
}
