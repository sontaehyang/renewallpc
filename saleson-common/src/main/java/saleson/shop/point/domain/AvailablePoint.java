package saleson.shop.point.domain;

import java.util.List;

public class AvailablePoint {
	
	public AvailablePoint(List<Point> list) {
		this.pointList = list;
		this.availablePoint = 0;
		if (list != null) {
			int p = this.availablePoint;
			for(Point point : list) {
				p += point.getPoint();
			}
			
			this.availablePoint = p;
		}
		
	}
	
	private int availablePoint;
	private List<Point> pointList;
	
	public int getAvailablePoint() {
		return availablePoint;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}
	
}
