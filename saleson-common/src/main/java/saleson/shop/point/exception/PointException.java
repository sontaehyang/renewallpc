package saleson.shop.point.exception;

import com.onlinepowers.framework.util.MessageUtils;

@SuppressWarnings("serial")
public class PointException extends RuntimeException {
	
	private int overflowUsePoint = 0;
	public PointException() {
		super(MessageUtils.getMessage("M00246") + " 처리가 잘못되었습니다.");
	}
	
	public PointException(String message) {
		super(message);
	}
	
	public PointException(String message, int overflowUsePoint) {
		super(message);
		setOverflowUsePoint(overflowUsePoint);
	}

	public int getOverflowUsePoint() {
		return overflowUsePoint;
	}

	public void setOverflowUsePoint(int overflowUsePoint) {
		this.overflowUsePoint = overflowUsePoint;
	}
	
}
