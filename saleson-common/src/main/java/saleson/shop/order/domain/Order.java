package saleson.shop.order.domain;

import java.util.ArrayList;
import java.util.List;

import saleson.model.OrderGiftItem;
import saleson.shop.order.claimapply.domain.OrderCancelShipping;
import saleson.shop.order.claimapply.domain.OrderReturnApply;

import com.onlinepowers.framework.util.StringUtils;

public class Order {
	
	private List<OrderReturnApply> returnApplys;
	public List<OrderReturnApply> getReturnApplys() {
		return returnApplys;
	}
	public void setReturnApplys(List<OrderReturnApply> returnApplys) {
		this.returnApplys = returnApplys;
	}

	private List<OrderCancelShipping> cancelShippingGroups;
	public List<OrderCancelShipping> getCancelShippingGroups() {
		return cancelShippingGroups;
	}
	public void setCancelShippingGroups(
			List<OrderCancelShipping> cancelShippingGroups) {
		this.cancelShippingGroups = cancelShippingGroups;
	}

	private String orderCode;
	private int orderSequence;
	private long userId;
	private String loginId;
	private String buyerName;
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	private String userName;
	private String phone;
	private String mobile;
	private String email;
	
	private String zipcode;
	private String newZipcode;
	private String companyName;
	private String sido;
	private String sigungu;
	private String eupmyeondong;
	private String address;
	private String addressDetail;
	
	private int itemTotalAmount;
	private int shippingTotalAmount;
	private int orderTotalAmount;
	private int payAmount;
	private String ip;
	private String orderAdminMemo;
	private String messageTargetDeliveryCompanyName; 	// 배송 메시지 발송시 발송 택배사
	private String messageTargetDeliveryNumber; 		// 배송 메시지 발송시 발송 타겟 송장번호
	private String[] messageTargetItemSequences;		// 배송 메시지 발송 타겟 아이템 순번
	
	private String returnBankName;
	private String returnBankInName;
	private String returnVirtualNo;
	private List<BuyItem> additionItemList;

	// 퀵배송 여부 (Y:퀵배송, N:일반택배)
	private String quickDeliveryFlag;

	public String getQuickDeliveryFlag() {
		return quickDeliveryFlag;
	}
	public void setQuickDeliveryFlag(String quickDeliveryFlag) {
		this.quickDeliveryFlag = quickDeliveryFlag;
	}
	public String getReturnBankName() {
		return returnBankName;
	}
	public void setReturnBankName(String returnBankName) {
		this.returnBankName = returnBankName;
	}
	public String getReturnBankInName() {
		return returnBankInName;
	}
	public void setReturnBankInName(String returnBankInName) {
		this.returnBankInName = returnBankInName;
	}
	public String getReturnVirtualNo() {
		return returnVirtualNo;
	}
	public void setReturnVirtualNo(String returnVirtualNo) {
		this.returnVirtualNo = returnVirtualNo;
	}
	public String getMessageTargetDeliveryNumber() {
		return messageTargetDeliveryNumber;
	}

	public void setMessageTargetDeliveryNumber(String messageTargetDeliveryNumber) {
		this.messageTargetDeliveryNumber = messageTargetDeliveryNumber;
	}

	public String[] getMessageTargetItemSequences() {
		return messageTargetItemSequences;
	}

	public void setMessageTargetItemSequences(String[] messageTargetItemSequences) {
		this.messageTargetItemSequences = messageTargetItemSequences;
	}

	public String getOrderAdminMemo() {
		return orderAdminMemo;
	}
	public void setOrderAdminMemo(String orderAdminMemo) {
		this.orderAdminMemo = orderAdminMemo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getItemTotalAmount() {
		return itemTotalAmount;
	}
	public void setItemTotalAmount(int itemTotalAmount) {
		this.itemTotalAmount = itemTotalAmount;
	}
	public int getShippingTotalAmount() {
		return shippingTotalAmount;
	}
	public void setShippingTotalAmount(int shippingTotalAmount) {
		this.shippingTotalAmount = shippingTotalAmount;
	}
	public int getOrderTotalAmount() {
		return orderTotalAmount;
	}
	public void setOrderTotalAmount(int orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}
	public int getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	private String dataStatusCode;
	private String createdDate;

	private List<OrderPayment> orderPayments;
	
	private List<OrderShippingInfo> orderShippingInfos;
	public String getNewZipcode() {
		return newZipcode;
	}
	public void setNewZipcode(String newZipcode) {
		this.newZipcode = newZipcode;
	}
	
	private String zipcode1;
	private String zipcode2;
	public String getFullZipcode() {
		
		if (StringUtils.isEmpty(this.zipcode2)) {
			return this.zipcode;
		}
		
		return this.zipcode1 + "-" + this.zipcode2;
	}
	
	public String getZipcode1() {
		return zipcode1;
	}
	public void setZipcode1(String zipcode1) {
		this.zipcode1 = zipcode1;
	}
	public String getZipcode2() {
		return zipcode2;
	}
	public void setZipcode2(String zipcode2) {
		this.zipcode2 = zipcode2;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		
		String[] temp = StringUtils.delimitedListToStringArray(zipcode, "-");
		if (temp.length == 2) {
			this.zipcode1 = temp[0];
			this.zipcode2 = temp[1];
		}
		
		this.zipcode = zipcode;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getEupmyeondong() {
		return eupmyeondong;
	}
	public void setEupmyeondong(String eupmyeondong) {
		this.eupmyeondong = eupmyeondong;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	public List<OrderShippingInfo> getOrderShippingInfos() {
		return orderShippingInfos;
	}

	public void setOrderShippingInfos(List<OrderShippingInfo> orderShippingInfos) {
		this.orderShippingInfos = orderShippingInfos;
	}

	/**
	 * 환불, 취소요청시 은행계좌 정보를 입력해야 되는가?
	 * @return
	 */
	public boolean isBankInfoWrite() {
		
		if (this.orderPayments == null) {
			return false;
		}
		
		for(OrderPayment orderPayment : orderPayments) {
			if ("bank".equals(orderPayment.getApprovalType()) || "vbank".equals(orderPayment.getApprovalType())) {
				//if (orderPayment.getRemainingAmount() > 0) {
					return true;
				//}
			}
		}
		
		return false;
	}

	public int getOrderSequence() {
		return orderSequence;
	}

	public void setOrderSequence(int orderSequence) {
		this.orderSequence = orderSequence;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataStatusCode() {
		return dataStatusCode;
	}

	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public List<OrderPayment> getOrderPayments() {
		return orderPayments;
	}

	public void setOrderPayments(List<OrderPayment> orderPayments) {
		this.orderPayments = orderPayments;
	}
	
	/**
	 * 취소금액
	 * @return
	 */
	public int getCancelAmount() {
		
		if (this.orderPayments == null) {
			return 0;
		}
		
		int cancelAmount = 0;
		for (OrderPayment orderPayment : orderPayments) {
			if ("2".equals(orderPayment.getPaymentType())) {
				cancelAmount += orderPayment.getCancelAmount();
			}
		}
		return cancelAmount;
	}
	
	public boolean isAllItemsCanceled() {
		if (this.orderPayments == null) {
			return false;
		}

		int canceledCount = 0;
		int orderItemCount = 0;
		for (OrderShippingInfo orderShippingInfo : getOrderShippingInfos()) {
			if (orderShippingInfo.getOrderItems() == null) {
				return false;
			}
			
			for (OrderItem orderItem : orderShippingInfo.getOrderItems()) {
				if ("71".equals(orderItem.getOrderStatus())) {
					canceledCount++;
				}
				orderItemCount++;
			}
		}
		
		return orderItemCount > 0 && canceledCount == orderItemCount ? true : false;
		
	}
	
	/**
	 * 상품의 총 겟수
	 * @return
	 */
	public int getItemCount() {
		
		int count = 0;
		for(OrderShippingInfo info : getOrderShippingInfos()) {
			for(OrderItem item : info.getOrderItems()) {
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * 결제 예정금액
	 * @return
	 */
	public int getPostPayAmount() {

        return this.orderTotalAmount - this.payAmount - getCancelAmount();
	}


	/**
	 * 해당 주문에 에스크로 결제 유무 확인
	 *
	 * 에스크로 정보가 ORDER_ITEM에 저장 되서 ORDER_ITEM 정보에서 확인 후
	 * 결과를 리턴함.
	 *
	 * @author skc@onlinepowers.com
	 * @date 2017-08-23
	 *
	 * @return Y: 에스크로 결제 , N: 일반 결제.
	 */
	public String getEscrowStatus() {
		if (this.orderShippingInfos == null || this.orderShippingInfos.isEmpty()) {
			return "N";
		}
		for (OrderShippingInfo orderShippingInfo : this.orderShippingInfos) {
			List<OrderItem> orderItems = orderShippingInfo.getOrderItems();

			if (orderItems == null || orderItems.isEmpty()) {
				return "N";
			}

			for (OrderItem orderItem : orderItems) {
				if ("Y".equals(orderItem.getEscrowStatus())) {
					return "Y";
				} else {
					return "N";
				}

			}
		}
		return "N";
	}

	/**
	 * 주문 - 총 배송비
	 * 네이밍 변경.
	 * @return
	 */
	public int getTotalShippingAmount() {
		return getShippingTotalAmount();
	}

	/**
	 * 주문 - 총 주문금액
	 * 네이밍 변경.
	 * @return
	 */
	public int getTotalOrderAmount() {
		return getOrderTotalAmount();
	}

	/**
	 * 주문 - 총 상품금액
	 * @return
	 */
	public int getTotalItemAmount() {
		return getTotal(OrderTotal.ITEM_AMOUNT);
	}

	/**
	 * 주문 - 총 할인금액 (전체)
	 * @return
	 */
	public int getTotalDiscountAmount() {
		return getTotal(OrderTotal.DISCOUNT_AMOUNT);
	}

	/**
	 * 주문 - 총 상품할인금액 (즉시 + 스팟)
	 * @return
	 */
	public int getTotalItemDiscountAmount() {
		return getTotal(OrderTotal.ITEM_DISCOUNT_AMOUNT);
	}

	/**
	 * 주문 - 총 할인금액 (쿠폰)
	 * @return
	 */
	public int getTotalCouponDiscountAmount() {
		return getTotal(OrderTotal.COUPON_DISCOUNT_AMOUNT);
	}

	/**
	 * 주문 - 총 할인금액 (회원등급)
	 * @return
	 */
	public int getTotalUserLevelDiscountAmount() {
		return getTotal(OrderTotal.USER_LEVEL_DISCOUNT_AMOUNT);
	}

	/**
	 * 주문 - 총 구입금액 (상품금액 - 할인금액)
	 * 할인 적용금액, 실제 결제 금액 (상품 기준)
	 * @return
	 */
	public int getTotalSaleAmount() {
		return getTotal(OrderTotal.SALE_AMOUNT);
	}


	/**
	 * 주문 항목별 Total 금액
	 * @param orderTotal
	 * @return
	 */
	private int getTotal(OrderTotal orderTotal) {
		if (this.orderShippingInfos == null || this.orderShippingInfos.isEmpty()) {
			return 0;
		}

		int totalAmount = 0;
		for (OrderShippingInfo orderShippingInfo : this.orderShippingInfos) {
			List<OrderItem> orderItems = orderShippingInfo.getOrderItems();

			if (orderItems == null || orderItems.isEmpty()) {
				continue;
			}

			for (OrderItem orderItem : orderItems) {
				int amount = 0;

				switch (orderTotal) {
					case ITEM_AMOUNT:
						amount = orderItem.getItemAmount();
						break;
					case DISCOUNT_AMOUNT:
						amount = orderItem.getDiscountAmount();
						break;
					case ITEM_DISCOUNT_AMOUNT:
						amount = orderItem.getItemDiscountAmount();
						break;
					case COUPON_DISCOUNT_AMOUNT:
						amount = orderItem.getCouponDiscountAmount();
						break;
					case USER_LEVEL_DISCOUNT_AMOUNT:
						amount = orderItem.getUserLevelDiscountAmount();
						break;
					case SALE_AMOUNT:
						amount = orderItem.getSaleAmount();
						break;


				}


				// 추가구성상품
				if (orderItem.getAdditionItemList() != null) {
					for (OrderItem additionOrderItem : orderItem.getAdditionItemList()) {

						switch (orderTotal) {
							case ITEM_AMOUNT:
								amount += additionOrderItem.getItemAmount();
								break;
							case DISCOUNT_AMOUNT:
								amount += additionOrderItem.getDiscountAmount();
								break;
							case ITEM_DISCOUNT_AMOUNT:
								amount += additionOrderItem.getItemDiscountAmount();
								break;
							case COUPON_DISCOUNT_AMOUNT:
								amount += additionOrderItem.getCouponDiscountAmount();
								break;
							case USER_LEVEL_DISCOUNT_AMOUNT:
								amount += additionOrderItem.getUserLevelDiscountAmount();
								break;
							case SALE_AMOUNT:
								amount += additionOrderItem.getSaleAmount();
								break;
						}
					}
				}


				totalAmount += amount;
			}
		}

		return totalAmount;
	}


	public String getMessageTargetDeliveryCompanyName() {
		return messageTargetDeliveryCompanyName;
	}

	public void setMessageTargetDeliveryCompanyName(String messageTargetDeliveryCompanyName) {
		this.messageTargetDeliveryCompanyName = messageTargetDeliveryCompanyName;
	}

	public List<String> getOrderItemUserCodes() {

		List<String> list = new ArrayList<>();

		List<OrderShippingInfo> shippingInfos = getOrderShippingInfos();

		if (shippingInfos != null && !shippingInfos.isEmpty()) {

			for (OrderShippingInfo shippingInfo : shippingInfos) {
				List<OrderItem> orderItems = shippingInfo.getOrderItems();

				if (orderItems != null && !orderItems.isEmpty()) {
					for (OrderItem orderItem: orderItems) {
						list.add(orderItem.getItemUserCode());
					}
				}
			}
		}

		return list;
	}

	public List<BuyItem> getAdditionItemList() {
		return additionItemList;
	}

	public void setAdditionItemList(List<BuyItem> additionItemList) {
		this.additionItemList = additionItemList;
	}
}

/**
 * 주문 항목별 TOTAL
 */
enum OrderTotal {
	ITEM_AMOUNT,				// 상품금액
	DISCOUNT_AMOUNT,			// 젙체 할인금액
	ITEM_DISCOUNT_AMOUNT,		// 상품할인금액
	COUPON_DISCOUNT_AMOUNT,		// 쿠폰사용금액
	USER_LEVEL_DISCOUNT_AMOUNT,		// 등급할인금액
	SALE_AMOUNT					// 구매금액 (할인적용금액)
}
