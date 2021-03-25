package saleson.shop.point.domain;

import java.util.ArrayList;
import java.util.List;

import com.onlinepowers.framework.util.DateUtils;

public class ExpirationPoint {
	
	private String pointType;
	private int amount;
	private List<Point> expirationPoints;
	private String lastDate;
	
	public ExpirationPoint(List<Point> list, String pointType, boolean isViewPage) {
		
		if (list == null) {
			return;
		}
		
		setPointType(pointType);
		setLastDate(DateUtils.getLastDateOfMonth(DateUtils.getToday()));
		
		// 만료처리 대상 포인트 초기화
		expirationPoints = new ArrayList<>();
		
		for(Point point : list) {
			if (point.getPoint() > 0) {
				
				// 오늘날짜 기준 이후 만료일 이면? <만료 예정>
				if (DateUtils.getDaysDiff(point.getExpirationDate()) < 0) {
					amount += point.getPoint();
					
				} else {
					expirationPoints.add(point);
					
					// 뷰페이지에 보여줄때는 전달 포인트 만료내역도있으면 보여준다.
					if (isViewPage == true) {
						amount += point.getPoint();
					}
				}
			}
		}
	
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public List<Point> getExpirationPoints() {
		return expirationPoints;
	}

	public void setExpirationPoints(List<Point> expirationPoints) {
		this.expirationPoints = expirationPoints;
	}

	public String getLastDate() {
		return lastDate;
	}
	
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	
}
