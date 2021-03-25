package saleson.common.utils;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import org.apache.commons.lang.ArrayUtils;
import saleson.common.Const;
import saleson.common.enumeration.PointType;
import saleson.common.point.SalesonPoint;
import saleson.shop.config.domain.Config;
import saleson.shop.point.domain.Point;
import saleson.shop.point.domain.PointUsed;
import saleson.shop.point.exception.PointException;

import java.util.ArrayList;
import java.util.List;

public class PointUtils {

	/*public static String DEFAULT_POINT_CODE = "point";
	public static String SHIPPING_COUPON_CODE=  "shipping";
	public static String EMONEY_CODE = "emoney";*/
	public static final String DEFAULT_POINT_CODE = PointType.POINT.getTitle();
	public static final String SHIPPING_COUPON_CODE=  PointType.SHIPPING.getTitle();
	public static final String EMONEY_CODE = PointType.EMONEY.getTitle();

	private PointUtils() {
	}

	private static SalesonPoint[] pointList = new SalesonPoint[] {
			new SalesonPoint("point", MessageUtils.getMessage("M00246"), false),				// 포인트 코드, 포인트명, 현금영수증 발행대상 여부 (TRUE, FALSE)
			new SalesonPoint("emoney", "이머니", true)
	};


	/**
	 * 쇼핑몰에서 사용 설정된 포인트(코드) 목록을 가져옴.
	 * PointUtils.POINT_LIST ==> PointUtils.getPointTypes();
	 * @return
	 */
	public static String[] getPointTypes() {
		List<String> pointTypes = new ArrayList<>();
		for (SalesonPoint point : pointList) {
			pointTypes.add(point.getPointType());
		}
		String[] pointList = new String[pointTypes.size()];
		pointList = pointTypes.toArray(pointList);
		return pointList;
	}

	/**
	 * 포인트명을 가져옴.
	 * @param pointType
	 * @return
	 */
	public static String getPointName(String pointType) {
		for (SalesonPoint point : pointList) {
			if (point.getPointType().equals(pointType)) {
				return point.getPointName();
			}
		}
		return "";
	}

	/**
	 * 포인트 결제 타입인가?
	 * ArrayUtils.contains(PointUtils.POINT_LIST, approvalType)
	 * 		==> PointUtils.isPointType(approvalType);
	 * @param approvalType
	 * @return
	 */
	public static boolean isPointType(String approvalType) {
		return ArrayUtils.contains(PointUtils.getPointTypes(), approvalType);
	}


	/**
	 * 현금영수증 발행이 가능한 포인트 인가?
	 * @param pointType
	 * @return
	 */
	public static boolean isPossibleToIssueReceipt(String pointType) {
		for (SalesonPoint point : pointList) {
			if (point.getPointType().equals(pointType)) {
				return point.isPossibleIssueReceipt();
			}
		}
		return false;
	}
	
	/**
	 * 포인트 처리
	 * @param earnPoints
	 * @param point
	 * @return
	 */
	public static void pointProcessing(List<Point> earnPoints, Point point) {
		
		if (earnPoints == null) {
			earnPoints = new ArrayList<>();
		}
		
		int pointAmount = point.getPoint();
		boolean isMinusPoint = pointAmount < 0 ? true : false;
		
		if (!earnPoints.isEmpty()) {
			for(Point earnPoint : earnPoints) {
				int price = earnPoint.getPoint();

				// 마이너스 포인트로 들어왔는데 나중에 적립으로 바뀌었다??
				if (isMinusPoint == true) {
					if (pointAmount > 0) { 
						throw new PointException();
					}
				} else {
					if (pointAmount < 0) { 
						throw new PointException();
					}
				}
				
				if (pointAmount < 0) {
					
					// 마이너스 적립 포인트 처리
					if (price > 0) {
						int usePoint = price + pointAmount > 0 ? -pointAmount : price;
						pointAmount = pointAmount + usePoint;
						
						earnPoint.setPoint(price - usePoint);
						
						// 새로 등록되는 데이터를 다시 업데이트 할 필요가 있나?
						if (earnPoint.isInsert() == false) {
							earnPoint.setUpdate(true);
						}
					}
					
				} else if (pointAmount > 0) { 
					
					// 적립 포인트 처리
					if (price < 0) {
						int usePoint = price + pointAmount > 0 ? -price : pointAmount;
						pointAmount = pointAmount - usePoint;
						
						earnPoint.setPoint(price + usePoint);
						
						if (earnPoint.isInsert() == false) {
							earnPoint.setUpdate(true);
						}
					}
					
				}
				
				if (pointAmount == 0) {
					break;
				}
			}
		}
		
		point.setPoint(pointAmount);
		earnPoints.add(point);
	}
	
	/**
	 * 과다 사용 포인트로 인한 새로운 포인트 내역 생성용
	 * @param pointType
	 * @param overflowUsePoint
	 * @return
	 */
	public static Point newPointForOverflowUsePoint(String pointType, int overflowUsePoint) {
		
		Point point = new Point();
		point.setInsert(true);
		point.setPointType(pointType);
		point.setSavedType("2");
		point.setSavedYear(DateUtils.getToday("yyyy"));
		point.setSavedMonth(DateUtils.getToday("MM"));
		point.setSavedPoint(-overflowUsePoint);
		point.setPoint(-overflowUsePoint);
		point.setReason("MIGRATION");
		point.setOrderCode("");
		point.setCreatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
		return point;
	}

	/**
	 * 포인트 과다 사용으로 인한 사용내역 생성용
	 * @param pointId
	 * @param groupId
	 * @param overflowUsePoint
	 * @return
	 */
	public static PointUsed newPointUsedForOverflowUsePoint(int pointId, int groupId, int overflowUsePoint) {
		
		PointUsed pointUsed = new PointUsed();

		pointUsed.setPointUsedGroupId(groupId);
		pointUsed.setUsedType("1");
		pointUsed.setPointId(pointId);
		pointUsed.setPoint(overflowUsePoint);
		
		return pointUsed;
	}
	
	/**
	 * 포인트를 사용처리한후 잔여 포인트보다 사용 내역이 많으면 에러를 리턴..
	 * @param earnPoints
	 * @param pointUsed
	 * @return
	 */
	public static void pointUsedProcessing(List<Point> earnPoints, List<PointUsed> newPointUsedList, PointUsed pointUsed) {
		int pointAmount = pointUsed.getPoint();
		
		for(Point point : earnPoints) {
			
			if (pointAmount == 0) {
				break;
			}
			
			int price = point.getPoint();
			if (price > 0) {
				int usePoint = pointAmount > price ? price : pointAmount;
				pointAmount = pointAmount - usePoint;
				
				PointUsed newPointUsed = new PointUsed();

				newPointUsed.setPointUsedGroupId(pointUsed.getPointUsedGroupId());
				newPointUsed.setUsedType("1");
				newPointUsed.setPointId(point.getPointId());
				newPointUsed.setPoint(usePoint);
				newPointUsed.setDetails(pointUsed.getDetails());
                newPointUsed.setOrderCode(pointUsed.getOrderCode());
				newPointUsed.setCreatedDate(pointUsed.getCreatedDate());
				newPointUsed.setManagerUserId(pointUsed.getManagerUserId());
				newPointUsed.setRemainingPoint(usePoint);
				
				newPointUsedList.add(newPointUsed);
				
				point.setUpdate(true);
				point.setPoint(price - usePoint);
			}
		}
		
		// 포인트 사용이 더 많은경우 알아서 처리..
		if (pointAmount > 0) {
			throw new PointException(MessageUtils.getMessage("M00246") + " 사용처리도중 사용처리되지 않은 " + MessageUtils.getMessage("M00246") + "가 발생함", pointAmount);
		}
	}
	
	/**
	 * 포인트 만료일 설정을 포인트 구분별로 조회
	 * @param pointType
	 * @param shopConfig
	 * @return
	 */
	public static int getExpirationMonth(String pointType, Config shopConfig) {
		
		if (shopConfig == null) {
			return -1;
		}
		
		if (PointUtils.DEFAULT_POINT_CODE.equals(pointType)) { // MILEAGE
			return shopConfig.getPointExpirationMonth();
		} else if (PointUtils.SHIPPING_COUPON_CODE.equals(pointType)) {
			return shopConfig.getShippingCouponExpirationMonth();
		}
		
		return -1;
	}
	
}
