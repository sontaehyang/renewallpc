package saleson.shop.mailconfig.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.mailconfig.domain.MailConfig;
import saleson.shop.order.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderMail extends MailTemplate {
	private String orderName;
	private String orderCode;
	private String orderDate;
	
	private String usePoint;
	private String orderPrice;
	private String deliveryPrice;
	private String orderTotalPrice;
	
	private String approvalType;
	private String bankDate;
	
	// 주문상품 + 배송지
	private String orderDetailList;

	// 결제내역
	private String orderItemPayment; 
	
	private String siteName;
	private String siteUrl;
	
	private String bankVirtualNo;
	private String bankAmount;
	private String bankInName;
	
	// 주문자
	private String email;
	private String mobile;
	
	
	public HashMap<String, String> getMap() {
		HashMap<String, String> map = new HashMap<>();

		map.put("orderName", "주문자명");
		map.put("orderCode", "주문코드");
		map.put("orderDate", "주문일자");
		
		map.put("mobile", "휴대폰번호");
		map.put("email", "이메일");
		
		map.put("orderDetailList", "주문상품정보");

		map.put("orderPrice", "주문금액");
		map.put("usePoint", "할인금액");
		map.put("deliveryPrice", "배송비");
		map.put("orderTotalPrice", "총 결제금액");
		
		map.put("approvalType", "결제방법");
		map.put("bankVirtualNo", "입금은행정보");
		map.put("bankInName", "입금자명");
		map.put("bankDate", "입금기한");
		
		map.put("orderItemPayment", "결제내역");
		
		map.put("siteName", "상점명");
		map.put("siteUrl", "상점URL");

		map.put("bankAmount", "입금요청액");
		// map.put("bankVirtualNo", "가상계좌번호");
		
		return map;
	}
	
	public OrderMail() {
		this.setMap(this.getMap());
	}

	/**
	 * OrderDelivery mail
	 * @param order
	 * @param userId
	 * @param mailConfig
	 * @param config
	 */
	public OrderMail(Order order, long userId, MailConfig mailConfig, Config config) {
		super(mailConfig);
		if (order== null) {
			return;
		}
		// 주문정보
		this.orderCode = order.getOrderCode();
		this.orderDate = DateUtils.date(order.getCreatedDate());
		this.orderName = order.getUserName();
		
		this.mobile = order.getMobile();
		this.email = order.getEmail();
		
		// 결제정보
		this.deliveryPrice = StringUtils.numberFormat(order.getShippingTotalAmount());
		this.orderPrice = StringUtils.numberFormat(order.getPayAmount() - order.getShippingTotalAmount());
		this.usePoint = "0";

		// 주문상품 + 배송지
		this.orderDetailList = getOrderDetailList(order.getOrderShippingInfos(), mailConfig);

		// 결제내역
		// this.orderItemPayment = orderItemPaymentByOrderPayment(order.getOrderPayments());
		
		//총결제금액
		this.orderTotalPrice = StringUtils.numberFormat(order.getPayAmount() - order.getShippingTotalAmount() - Integer.parseInt(this.usePoint) + order.getShippingTotalAmount());
		for(OrderPayment orderPayment : order.getOrderPayments()) {
			if ("bank".equals(orderPayment.getApprovalType())) {
				this.orderTotalPrice = StringUtils.numberFormat(orderPayment.getAmount());
				break;
			} 
		}
		
		// 배송중 쿠폰할인액 추가 2017-03-03_seungil.lee
		int couponDiscountAmount = 0;
		for (OrderShippingInfo info : order.getOrderShippingInfos()) {
			for (OrderItem item : info.getOrderItems()) {
				couponDiscountAmount += item.getCouponDiscountAmount();
			}
		}
		
		this.usePoint = StringUtils.numberFormat(Integer.parseInt(usePoint) + couponDiscountAmount);
		this.siteName = config.getShopName();
		this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
		
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	// 무통장 입금 대기, 결제 완료 안내 메일
	public OrderMail(Buy buy, long userId, MailConfig mailConfig, Config config) {
		super(mailConfig);
		if (buy== null) {
			return;
		}
		
		Buyer buyer = buy.getBuyer();
		OrderPrice orderPrice = buy.getOrderPrice();
		
		// 주문정보
		this.orderCode = buy.getOrderCode();
		this.orderDate = DateUtils.getToday("yyyy-MM-dd");
		this.orderName = buyer.getUserName();
		
		// 주문금액 정보
		this.orderPrice = StringUtils.numberFormat(orderPrice.getTotalItemAmountBeforeDiscounts());
		
		// 포인트할인 + 전체할인 + 쿠폰할인 2017-03-03_seungil.lee		
		this.usePoint = StringUtils.numberFormat(orderPrice.getTotalPointDiscountAmount()+orderPrice.getTotalDiscountAmount()+buy.getOrderPrice().getTotalCouponDiscountAmount());
		
		this.deliveryPrice = StringUtils.numberFormat(orderPrice.getTotalShippingAmount());
		this.orderTotalPrice = StringUtils.numberFormat(orderPrice.getOrderPayAmount());
		
		// 주문내역
//		this.orderDetailList = orderInfo(buy.getItems());

		// 주문상품 + 배송지
		this.orderDetailList = getOrderDetailListByReceiver(buy.getReceivers());
		
		this.siteName = config.getShopName();

		// 이미지 노출을 위한 URL 변경 - VirtualUrl
//		this.siteUrl = SalesonProperty.getSalesonUrlShoppingmall();
		this.siteUrl = SalesonProperty.getSalesonUrlFrontend();

		//결제방식 추가[2017-01-26]minae.yun
		for(BuyPayment orderPayment : buy.getPayments()) {
			if ("realtimebank".equals(orderPayment.getApprovalType())) {
				this.approvalType = "실시간계좌이체";
			}
			/*else if ("point".equals(orderPayment.getApprovalType())) {
				this.usePoint = StringUtils.numberFormat(String.valueOf(orderPayment.getAmount()));
			}*/
			else  if ("card".equals(orderPayment.getApprovalType())) {
				this.approvalType = "카드";
			}
			else  if ("vbank".equals(orderPayment.getApprovalType())) {
				this.approvalType = "가상계좌";
				this.bankInName = orderPayment.getBankInName();
				this.bankDate = orderPayment.getBankExpirationDate();
				this.bankAmount = StringUtils.numberFormat(orderPayment.getAmount());
				this.bankVirtualNo = orderPayment.getBankVirtualNo();
				this.orderTotalPrice = StringUtils.numberFormat(orderPayment.getAmount());
			}
			else if  ("bank".equals(orderPayment.getApprovalType())) {
				this.approvalType = "무통장입금";
				this.bankInName = orderPayment.getBankInName();
				this.bankDate = orderPayment.getBankExpirationDate();
				this.bankAmount = StringUtils.numberFormat(orderPayment.getAmount());
				this.bankVirtualNo = orderPayment.getBankVirtualNo();
				this.orderTotalPrice = StringUtils.numberFormat(orderPayment.getAmount());
			}
		}

		// 결제내역
		this.orderItemPayment = buyItemPayment(buy.getPayments());
		
		this.mobile = buyer.getMobile();
		this.email = buyer.getEmail();
		
		HashMap<String, String> map = this.setMailInfo();
		this.setMap(map);
	}
	
	private HashMap<String, String> setMailInfo(){
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("orderCode", this.orderCode);
		map.put("orderName", this.orderName);
		
		// 주문자 정보
		map.put("mobile", this.mobile);
		map.put("email", this.email);
		
		// 주문 + 배송지 정보
		map.put("orderDetailList", this.orderDetailList);
		
		// 주문일자
		map.put("orderDate", this.orderDate);
		map.put("bankAmount", this.bankAmount);
		
		// 주문가격 정보 
		map.put("orderPrice", this.orderPrice);
		map.put("usePoint", this.usePoint);
		map.put("deliveryPrice", this.deliveryPrice);
		map.put("orderTotalPrice", this.orderTotalPrice);
		
		// 결제 타입
		map.put("approvalType", this.approvalType);
		
		// 결제 내역
		map.put("orderItemPayment", this.orderItemPayment);
		
		// 은행 정보
		map.put("bankVirtualNo", this.bankVirtualNo);
		map.put("bankDate", this.bankDate);
		map.put("bankInName", this.bankInName);
		
		// 사이트 정보
		map.put("siteName", this.siteName);
		map.put("siteUrl", this.siteUrl);
		
		return map;
	}
	
	/**
	 * 배송지 + 상품 정보(Buy)
	 * @param receivers
	 * @return
	 */
	private String getOrderDetailListByReceiver(List<Receiver> receivers) {
		
		StringBuffer sb = new StringBuffer();
		for (Receiver receiver : receivers) {
			String receiverInfo = "";
			
			String address = "(" + receiver.getReceiveNewZipcode() + ")";
			
			if (StringUtils.isEmpty(receiver.getReceiveNewZipcode())) {
				address = "(" + receiver.getReceiveZipcode() + ") ";
			}
			
			address += receiver.getReceiveAddress() + " " + receiver.getReceiveAddressDetail();
			
			receiverInfo += "";
			receiverInfo += "<table style='width:100%; margin-top:10px; border-top:1px solid #666; border-bottom:1px solid #ccc; border-collapse:collapse; color:#333'>";
			receiverInfo += "	<colgroup>";
			receiverInfo += "		<col style='width:130px;'>";
			receiverInfo += "		<col style='width:240px;'>";
			receiverInfo += "		<col style='width:85px;'>";
			receiverInfo += "		<col style='width:55px;'>";
			receiverInfo += "		<col style='width:85px;'>";
			receiverInfo += "		<col style='width:83px;'>";
			receiverInfo += "	</colgroup>";
			receiverInfo += "	<thead>";
			receiverInfo += "		<tr>";
			receiverInfo += "			<th scope='col' colspan='2' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>상품명/옵션정보</th>";
			receiverInfo += "			<th scope='col' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>상품금액</th>";
			receiverInfo += "			<th scope='col' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>수량</th>";
			receiverInfo += "			<th scope='col' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>소계</th>";
			receiverInfo += "			<th scope='col' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>배송비</th>";
			receiverInfo += "		</tr>";
			receiverInfo += "	</thead>";
			receiverInfo += "	<tbody>";

			//묶음배송일 경우, 입금대기 상태일 경우 고려 이상우 [2017-02-27 추가]
			List<String> shipmentGroupCodes = new ArrayList<>();

			// 추가구성상품 리스트
			List<BuyItem> additionItemList = new ArrayList<>();
			for (Shipping shipping : receiver.getItemGroups()) {
				for (BuyItem item : shipping.getBuyItems()) {

					if ("Y".equals(item.getAdditionItemFlag())) {
						additionItemList.add(item);
					}
				}
			}

			// itemGroups에서 getBuyItems를 가져오는 것으로 구조 변경 2017-03-13_seungil.lee
			for (Shipping shipping : receiver.getItemGroups()) {
				int buyItemIndex = 0;
				for (BuyItem item : shipping.getBuyItems()) {

					int shippingAmount = (buyItemIndex == 0) ? shipping.getPayShipping() : 0;

					// 묶음배송일 때
					if (!shipmentGroupCodes.isEmpty() && shipmentGroupCodes.contains(item.getBuyShipping().getShipmentGroupCode())) {
						shippingAmount = 0;
					}

					// 본상품에 추가구성상품 데이터 세팅
					List<BuyItem> additionItems = new ArrayList<>();
					for (BuyItem additionItem : additionItemList) {

						// 부모의 ITEM_SEQUENCE, ITEM_ID, OPTIONS 가 일치하는 경우
						if (item.getItemSequence() == additionItem.getParentItemSequence()
								&& additionItem.getParentItemId() == item.getItemId()
								&& item.getOptions().equals(additionItem.getParentItemOptions())) {

							additionItems.add(additionItem);
						}
					}

					if (additionItems.size() > 0) {
						item.setAdditionItemList(additionItems);
					}

					// 상품, 배송지 테이블 구성
					receiverInfo = OrderItemAndReceiverInfoTable(item, receiverInfo, shippingAmount);
					// 같은 출고지인지 확인하기위해 list에 추가
					if (StringUtils.isNotEmpty(item.getBuyShipping().getShipmentGroupCode())) {
						shipmentGroupCodes.add(item.getBuyShipping().getShipmentGroupCode());
					}

					buyItemIndex++;
				}
			}

			receiverInfo += "		<tr>";
			receiverInfo += "			<th scope='row' style='padding:37px 20px; background:#f7f7f7; font-weight:bold; text-align:left'>받는사람</th>";
			receiverInfo += "			<td colspan='5' style='padding:16px 20px; line-height:18px'>";
			receiverInfo += "				<p style='margin:0; font-weight:bold;'>" + receiver.getReceiveName() + "</p>";
			receiverInfo += "				<p style='margin:0;'>" + address + "</p>";



			if ( !receiver.getFullReceivePhone().isEmpty() && receiver.getFullReceivePhone().length() > 6 ) {
				receiverInfo += "				<p style='margin:0;'>" + receiver.getFullReceivePhone() + " / " + receiver.getFullReceiveMobile() + "</p>";
			} else {
				receiverInfo += "				<p style='margin:0;'>" + receiver.getFullReceiveMobile() + "</p>";
			}
			receiverInfo += "			</td>";
			receiverInfo += "		</tr>";

			if (!StringUtils.isEmpty(receiver.getContent())) {
				receiverInfo += "		<tr>";
				receiverInfo += "			<th scope='row' style='padding:37px 20px; border-top:1px solid #e1e1e1; background:#f7f7f7; font-weight:bold; text-align:left'>배송시 요청사항</th>";
				receiverInfo += "			<td colspan='5' style='padding:16px 20px; border-top:1px solid #e1e1e1; line-height:18px'>";
				receiverInfo += "				<p style='margin:0;'>" + receiver.getContent() + "</p>";
				receiverInfo += "			</td>";
				receiverInfo += "		</tr>";
			}
			receiverInfo += "	</tbody>";
			receiverInfo += "</table>";
			
			sb.append(receiverInfo);
		}
		
		return sb.toString(); 
	}
	
	/**
	 * 상품 + 배송지 테이블 생성
	 * @param shippingInfoList
	 * @return
	 */
	private String getOrderDetailList(List<OrderShippingInfo> shippingInfoList, MailConfig mailConfig) {
		
		StringBuffer sb = new StringBuffer();
		int itemsPay = 0;
		int deliveryPay = 0;
		
		for (OrderShippingInfo shippingInfo : shippingInfoList) {
			String deliverInfo = "";
			
			String address = "(" + shippingInfo.getReceiveNewZipcode() + ") ";
			
			if (StringUtils.isEmpty(shippingInfo.getReceiveNewZipcode())) {
				address = "(" + shippingInfo.getReceiveZipcode() + ") ";
			}
			
			address += shippingInfo.getReceiveAddress() + " " + shippingInfo.getReceiveAddressDetail();

			deliverInfo += "";
			deliverInfo += "<table style='width:100%; margin-top:10px; border-top:1px solid #666; border-bottom:1px solid #ccc; border-collapse:collapse; color:#333'>";
			deliverInfo += "	<colgroup>";
			deliverInfo += "		<col style='width:130px;'>";
			deliverInfo += "		<col style='width:240px;'>";
			deliverInfo += "		<col style='width:85px;'>";
			deliverInfo += "	</colgroup>";
			deliverInfo += "	<thead>";
			deliverInfo += "		<tr>";
			deliverInfo += "			<th scope='col' colspan='2' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>상품명/옵션정보</th>";
			deliverInfo += "			<th scope='col' style='padding:15px 0; background:#f7f7f7; border-bottom:1px solid #d9d9d9; font-weight:bold; text-align:center'>수량</th>";
			deliverInfo += "		</tr>";
			deliverInfo += "	</thead>";
			deliverInfo += "	<tbody>";

			//묶음배송일 경우, 입금대기 상태일 경우 고려 이상우 [2017-02-27 추가]
			List<String> shipmentGroupCodes = new ArrayList<>();

			// 추가구성상품 리스트
			List<OrderItem> additionItemList = new ArrayList<>();
			for (OrderItem item : shippingInfo.getOrderItems()) {
				if ("Y".equals(item.getAdditionItemFlag())) {
					additionItemList.add(item);
				}
			}

			for (OrderItem item : shippingInfo.getOrderItems()) {
				String optionText = "";
				String giftItemText = "";
				String additionItemText = "";

				if (!StringUtils.isEmpty(item.getOptions())) {
					optionText = ShopUtils.viewOptionText(item.getOptions());
				}

				if (item.getOrderGiftItemList() != null && item.getOrderGiftItemList().size() > 0) {
					giftItemText = ShopUtils.makeOrderGiftItemText(item.getOrderGiftItemList());
				}

				// 본상품에 추가구성상품 데이터 세팅
				List<OrderItem> additionItems = new ArrayList<>();
				for (OrderItem additionItem : additionItemList) {

					// 부모의 ITEM_SEQUENCE, ITEM_ID, OPTIONS 가 일치하는 경우
					if (item.getItemSequence() == additionItem.getParentItemSequence()
							&& additionItem.getParentItemId() == item.getItemId()
							&& item.getOptions().equals(additionItem.getParentItemOptions())) {

						additionItems.add(additionItem);
					}
				}

				if (additionItems.size() > 0) {
					item.setAdditionItemList(additionItems);
				}


				int additionItemPrice = 0;
				if (item.getAdditionItemList() != null && item.getAdditionItemList().size() > 0) {
					additionItemText = ShopUtils.viewAdditionOrderItemList(item.getAdditionItemList());

					for (OrderItem additionItem : additionItemList) {
						additionItemPrice += additionItem.getPrice() * additionItem.getQuantity();
					}
				}

				if ("N".equals(item.getAdditionItemFlag())) {

					deliverInfo += "		<tr>";
					deliverInfo += "			<td colspan='2' style='position:relative; padding:12px 0; border-right:1px solid #e1e1e1; border-bottom:1px solid #d9d9d9'>";

					// 상품명
					deliverInfo += "				<div style='margin: 0 0 0 10px; font-size:11px; color:#999; line-height:16px'>";
					deliverInfo += "					<p style='margin:0 0 8px; font-size:13px; font-weight:bold; color:#333'>" + item.getItemName() + "</p>";
					if (optionText != null && !optionText.isEmpty()) {
						deliverInfo += "				<p style='margin:0; font-family:'Dotum''><span style='color:#23ade3;'>옵션:</span> " + optionText + "</p>";
					}

					if (!StringUtils.isEmpty(additionItemText)) {
						deliverInfo += "				<p style='margin:0; font-family:'Dotum'>" + additionItemText + "</p>";
					}

					if (!StringUtils.isEmpty(giftItemText)) {
						deliverInfo += "				<p style='margin:0; font-family:'Dotum'><span style='color:#23ade3;'>사은품:</span> "+giftItemText+"</p>";
					}

					// 배송 시작 메일 발송일 때
					if ("order_delivering".equals(mailConfig.getTemplateId()) && item.getDeliveryNumber() != null && !item.getDeliveryNumber().isEmpty()) {
						deliverInfo += "				<p style='margin:0; font-family:'Dotum''><span style='color:#23ade3;'>운송장번호:</span> " + item.getDeliveryCompanyName() + " " + item.getDeliveryNumber() + "</p>";
					}

					deliverInfo += "				</div>";
					deliverInfo += "			</td>";

					// 상품 수량
					deliverInfo +=
							new StringBuilder("			<td style='padding:42px 0; border-bottom:1px solid #d9d9d9; font-size:13px; text-align:center'>")
									.append(item.getQuantity())
									.append("</td>").toString();

					deliverInfo += "		</tr>";

					//주문금액
					itemsPay += (item.getPrice() + item.getOptionPrice()) * item.getQuantity() + additionItemPrice;

				}
			}

			this.orderPrice = StringUtils.numberFormat(itemsPay);
			this.deliveryPrice = StringUtils.numberFormat(deliveryPay);

			deliverInfo += "		<tr>";
			deliverInfo += "			<th scope='row' style='padding:37px 20px; background:#f7f7f7; font-weight:bold; text-align:left'>받는사람</th>";
			deliverInfo += "			<td colspan='5' style='padding:16px 20px; line-height:18px'>";
			deliverInfo += "				<p style='margin:0; font-weight:bold;'>" + shippingInfo.getReceiveName() + "</p>";
			deliverInfo += "				<p style='margin:0;'>" + address + "</p>";

			if ( !shippingInfo.getFullReceivePhone().isEmpty() && shippingInfo.getFullReceivePhone().length() > 6 ) {
				deliverInfo += "				<p style='margin:0;'>" + shippingInfo.getFullReceivePhone() + " / " + shippingInfo.getFullReceiveMobile() + "</p>";
			} else {
				deliverInfo += "				<p style='margin:0;'>" + shippingInfo.getFullReceiveMobile() + "</p>";
			}

			deliverInfo += "			</td>";
			deliverInfo += "		</tr>";

			if (!StringUtils.isEmpty(shippingInfo.getMemo())) {
				deliverInfo += "		<tr>";
				deliverInfo += "			<th scope='row' style='padding:37px 20px; border-top:1px solid #e1e1e1; background:#f7f7f7; font-weight:bold; text-align:left'>배송시 요청사항</th>";
				deliverInfo += "			<td colspan='5' style='padding:16px 20px; border-top:1px solid #e1e1e1; line-height:18px'>";
				deliverInfo += "				<p style='margin:0;'>" + shippingInfo.getMemo() + "</p>";
				deliverInfo += "			</td>";
				deliverInfo += "		</tr>";
			}

			deliverInfo += "	</tbody>";
			deliverInfo += "</table>";
			
			sb.append(deliverInfo);
		}
		
		return sb.toString();
	}
	
	/**
	 * 결제 수단 정보 테이블(orderPayment)
	 * @param orderPayments
	 * @return
	 */
	private String orderItemPaymentByOrderPayment(List<OrderPayment> orderPayments) {
		
		StringBuffer sb = new StringBuffer();
		
		String orderItemPayment = "";

		orderItemPayment += "";
		orderItemPayment += "<table style='width:100%; margin-top:10px; border-top:1px solid #666; border-bottom:1px solid #ccc; border-collapse:collapse;'>";
		orderItemPayment += "	<colgroup>";
		orderItemPayment += "		<col style='width:130px;'>";
		orderItemPayment += "		<col style='width:auto'>";
		orderItemPayment += "	</colgroup>";
		orderItemPayment += "	<tbody>";

		int discountPay = 0;
		/*int couponDiscountAmount = 0;

		// 쿠폰 할인액 합산 2017-03-03_seungil.lee
		for (OrderShippingInfo info : order.getOrderShippingInfos()) {
			for (OrderItem item : info.getOrderItems()) {
				couponDiscountAmount += item.getCouponDiscountAmount();
			}
		}*/

		for (OrderPayment orderPayment : orderPayments) {
			String unit = "원";
			String payment = "결제완료";

			// 포인트 결제 시 데이터 변경 추가, 결제일 주문일로 되어있던거 payDate 값으로 수정 [2017-02-03]minae.yun
			if ("point".equals(orderPayment.getApprovalType())) {
				unit = "P";
				discountPay +=orderPayment.getAmount();
			}

			// this.usePoint = StringUtils.numberFormat(discountPay + couponDiscountAmount);
//			this.usePoint = StringUtils.numberFormat(discountPay);

			if (StringUtils.isEmpty(orderPayment.getPayDate())) {
				payment = "입금예정";
			} else if (!"1".equals(orderPayment.getPaymentType())) {
				payment = "결제취소";
			}

			if ("bank".equals(orderPayment.getApprovalType()) || "vbank".equals(orderPayment.getApprovalType()) ) {
				orderDate = DateUtils.date(orderPayment.getCreatedDate());
			} else {
				orderDate = DateUtils.date(orderPayment.getPayDate());
			}

			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; border-bottom:1px solid #e1e1e1; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제방식</th>";
			orderItemPayment += "			<td style='padding:15px 20px; border-bottom:1px solid #e1e1e1'>" + orderPayment.getApprovalTypeLabel() + "</td>";
			orderItemPayment += "		</tr>";
			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; border-bottom:1px solid #e1e1e1; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제금액</th>";
			orderItemPayment += "			<td style='padding:15px 20px; border-bottom:1px solid #e1e1e1'>" + StringUtils.numberFormat(orderPayment.getAmount()) + unit + "</td>";
			orderItemPayment += "		</tr>";
			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; border-bottom:1px solid #e1e1e1; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제상태</th>";
			orderItemPayment += "			<td style='padding:15px 20px; border-bottom:1px solid #e1e1e1;'>" + payment + "</td>";
			orderItemPayment += "		</tr>";
			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제일</th>";
			orderItemPayment += "			<td style='padding:15px 20px'>" +  orderDate + "</td>";
			orderItemPayment += "		</tr>";

		}

		orderItemPayment += "	</tbody>";
		orderItemPayment += "</table>";
	
		sb.append(orderItemPayment);
		return sb.toString(); 
	}

	/**
	 * 상품, 배송지 테이블 구성 2019-02-13
	 * @param item
	 * @param receiverInfo
	 * @param shippingAmount
	 * @return
	 */
	private String OrderItemAndReceiverInfoTable(BuyItem item, String receiverInfo, int shippingAmount) {

		String optionText = "";
		String giftItemText = "";
		String couponDiscountText = "";
		String additionItemText = "";

		if (!StringUtils.isEmpty(item.getOptions())) {
			optionText = ShopUtils.viewOptionText(item.getOptions());
		}

		if (!StringUtils.isEmpty(item.getFreeGiftItemText())) {
			giftItemText = item.getFreeGiftItemText();
		}

		if (item.getUsedCoupon() != null && item.getUsedCoupon().getDiscountPrice() > 0) {
			couponDiscountText = "<p style='margin:0px; padding:0px;'>(쿠폰: -" + StringUtils.numberFormat(item.getUsedCoupon().getDiscountPrice()) + "원)</p>";
		}

		// 추가구성상품
		int additionItemPrice = 0;
		int additionItemSaleAmount = 0;
		if (item.getAdditionItemList() != null && item.getAdditionItemList().size() > 0) {
			additionItemText = ShopUtils.viewAdditionItemList(item.getAdditionItemList());

			for (BuyItem buyItem : item.getAdditionItemList()) {
				additionItemPrice += buyItem.getItemPrice().getPrice() * buyItem.getItemPrice().getQuantity();
				additionItemSaleAmount += buyItem.getItemPrice().getSaleAmount();
			}
		}

		if ("N".equals(item.getAdditionItemFlag())) {

			receiverInfo += "		<tr>";
			receiverInfo += "			<td colspan='2' style='position:relative; padding:12px 0; border-right:1px solid #e1e1e1; border-bottom:1px solid #d9d9d9'>";

			// 상품명
			receiverInfo += "				<div style='margin: 0 0 0 10px; font-size:11px; color:#999; line-height:16px'>";
			receiverInfo += "					<p style='margin:0 0 8px; font-size:13px; font-weight:bold; color:#333'>" + item.getItemName() + "</p>";
			if (optionText != null && !optionText.isEmpty()) {
				receiverInfo += "				<p style='margin:0; font-family:'Dotum'><span style='color:#23ade3;'>옵션:</span> " + optionText  +"</p>";
			}

			if (!StringUtils.isEmpty(additionItemText)) {
				receiverInfo += "				<p style='margin:0; font-family:'Dotum'>"+ additionItemText +" </p>";
			}

			if (!StringUtils.isEmpty(giftItemText)) {
				receiverInfo += "				<p style='margin:0; font-family:'Dotum'><span style='color:#23ade3;'>사은품:</span> "+ giftItemText +" </p>";
			}

			receiverInfo += "				</div>";
			receiverInfo += "			</td>";

			// 상품가격
			receiverInfo +=
					new StringBuilder("			<td style='padding:42px 0; border-right:1px solid #e1e1e1; border-bottom:1px solid #d9d9d9; font-size:13px; text-align:center'>")
							.append(StringUtils.numberFormat(item.getItemPrice().getPrice() + item.getItemPrice().getOptionPrice() + additionItemPrice))
							.append("원")
							.append(couponDiscountText)
							.append("</td>").toString();

			// 상품 수량
			receiverInfo +=
					new StringBuilder("			<td style='padding:42px 0; border-right:1px solid #e1e1e1; border-bottom:1px solid #d9d9d9; font-size:13px; text-align:center'>")
							.append(item.getItemPrice().getQuantity())
							.append("</td>").toString();

			// 소계
			receiverInfo += new StringBuilder("			<td style='padding:42px 0; border-right:1px solid #e1e1e1; border-bottom:1px solid #d9d9d9; font-size:13px; font-weight:bold; color:#000; text-align:center'>")
					.append(StringUtils.numberFormat(item.getItemPrice().getSaleAmount() + additionItemSaleAmount + shippingAmount))
					.append("원</td>").toString();

			String shippingText = shippingAmount == 0 ? "무료" : StringUtils.numberFormat(shippingAmount) + "원";
			if ("Y".equals(item.getQuickDeliveryFlag())) {
				shippingText = "퀵 (착불)";
			}

			// 배송비
			receiverInfo += new StringBuilder("			<td style='padding:42px 0; border-bottom:1px solid #d9d9d9; font-size:13px; text-align:center'>")
					.append(shippingText)
					.append("</td>").toString();

			receiverInfo += "		</tr>";

		}

		return receiverInfo;
	}

	/**
	 * 결제 수단 정보 테이블(BuyPayment)
	 * @param buyPayments
	 * @return
	 */
	private String buyItemPayment(List<BuyPayment> buyPayments) {
		
		StringBuffer sb = new StringBuffer();
		
		String orderItemPayment = "";

		orderItemPayment += "";
		orderItemPayment += "<table style='width:100%; margin-top:10px; border-top:1px solid #666; border-bottom:1px solid #ccc; border-collapse:collapse;'>";
		orderItemPayment += "	<colgroup>";
		orderItemPayment += "		<col style='width:130px;'>";
		orderItemPayment += "		<col style='width:auto'>";
		orderItemPayment += "	</colgroup>";
		orderItemPayment += "	<tbody>";
		
		for (BuyPayment buyPayment : buyPayments) {
			String unit = "원";
			String payment = "입금예정";
			String paymentType = "가상계좌";
			String paymentDate = orderDate;

			//포인트 결제방식 추가[2017-01-26]minae.yun
			if ("point".equals(buyPayment.getApprovalType())) {
				unit = "P";
				payment = "결제완료";
				paymentType = "적립 " + MessageUtils.getMessage("M00246");
			} else if ("card".equals(buyPayment.getApprovalType())) {
				payment = "결제완료";
				paymentType = "신용카드";
			} else if ("bank".equals(buyPayment.getApprovalType())) {
				paymentType = "무통장입금";
				paymentDate = "-";
			} else if ("realtimebank".equals(buyPayment.getApprovalType())) {
				payment = "결제완료";
				paymentType = "실시간 계좌이체";
			} else {
				paymentDate = "-";
			}

			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; border-bottom:1px solid #e1e1e1; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제방식</th>";
			orderItemPayment += "			<td style='padding:15px 20px; border-bottom:1px solid #e1e1e1'>" + paymentType + "</td>";
			orderItemPayment += "		</tr>";
			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; border-bottom:1px solid #e1e1e1; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제금액</th>";
			orderItemPayment += "			<td style='padding:15px 20px; border-bottom:1px solid #e1e1e1'>" + StringUtils.numberFormat(buyPayment.getAmount()) + unit + "</td>";
			orderItemPayment += "		</tr>";
			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; border-bottom:1px solid #e1e1e1; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제상태</th>";
			orderItemPayment += "			<td style='padding:15px 20px; border-bottom:1px solid #e1e1e1;'>" + payment + "</td>";
			orderItemPayment += "		</tr>";
			orderItemPayment += "		<tr>";
			orderItemPayment += "			<th scope='row' style='padding:15px 20px; background:#f7f7f7; color:#333; font-weight:bold; text-align:left'>결제일</th>";
			orderItemPayment += "			<td style='padding:15px 20px'>" +  paymentDate + "</td>";
			orderItemPayment += "		</tr>";
			
		}
		
		orderItemPayment += "	</tbody>";
		orderItemPayment += "</table>";
	
		sb.append(orderItemPayment);
		return sb.toString(); 
	}

	// 주문 내역 LSW 2016.08.04 추가
	/*private String orderInfo(List<BuyItem> buyItems) {

		StringBuffer sb = new StringBuffer();
		String orderInfo = "";

		orderInfo += "<div class='mail_board_03' style='margin:13px 0 0 0;'>";
		orderInfo += "	<table cellpadding='0' cellspacing='0' style='border-top:1px solid #333; border-bottom:1px solid #999; width:100%;'>";
		orderInfo += "		<colgroup>";
		orderInfo += "			<col style='width:auto;'> ";
		orderInfo += "			<col style='width:110px'>";
		orderInfo += "			<col style='width:110px'>";
		orderInfo += "			<col style='width:110px'>";
		orderInfo += "		<colgroup>";
		orderInfo += "		<tbody>";
		orderInfo += "			<tr>";
		orderInfo += "				<th style='padding:10px 0 10px 20px; background:#f8f8f8; color:#333; text-align:left; border-top:0;'>상품명/옵션정보</th>";
		orderInfo += "				<th style='padding:10px 0 10px 20px; background:#f8f8f8; color:#333; text-align:center; border-top:0;'>수량</th>";
		orderInfo += "				<th style='padding:10px 0 10px 20px; background:#f8f8f8; color:#333; text-align:center; border-top:0;'>상품금액</th>";
		orderInfo += "				<th style='padding:10px 0 10px 20px; background:#f8f8f8; color:#333; text-align:center; border-top:0;'>상품합계금액</th>";
		orderInfo += "			</tr>";
		for (BuyItem buyItem : buyItems) {
			orderInfo += "			<tr>";
			orderInfo += "				<td style='padding:10px 0 10px 10px; color:#666; text-align:left; border-top:1px solid #c9c9c9;'>";
			orderInfo += "					<div style='line-height:18px;'>["+buyItem.getItemCode()+"]</div> ";
			orderInfo += "					<div style='line-height:18px;'>"+buyItem.getItemName()+"</div> ";
			orderInfo += "					<div style='line-height:18px;'>"+buyItem.getOptions()+"</div> ";
			orderInfo += "				</td>";
			orderInfo += "				<td style='padding:10px 0 10px 10px; color:#666; text-align:left; border-top:1px solid #c9c9c9;'>";
			orderInfo += "					<div style='line-height:18px; text-align:center;'>"+StringUtils.numberFormat(buyItem.getItemPrice().getQuantity())+" 개</div> ";
			orderInfo += "				</td>";
			orderInfo += "				<td style='padding:10px 0 10px 10px; color:#666; text-align:left; border-top:1px solid #c9c9c9;'>";
			orderInfo += "					<div style='line-height:18px; text-align:center;'>"+StringUtils.numberFormat(buyItem.getItemPrice().getSaleAmount() / buyItem.getItemPrice().getQuantity())+" 원</div> ";
			orderInfo += "				</td>";
			orderInfo += "				<td style='padding:10px 0 10px 10px; color:#666; text-align:left; border-top:1px solid #c9c9c9;'>";
			orderInfo += "					<div style='line-height:18px; text-align:center; font-weight:bold;'>"+StringUtils.numberFormat(buyItem.getItemPrice().getSaleAmount())+" 원</div> ";
			orderInfo += "				</td>";
			orderInfo += "			</tr>";
		}
		orderInfo += "		</tbody>";
		orderInfo += "	</table>";
		orderInfo += "</div>";
		sb.append(orderInfo);

		return sb.toString();
	}*/
}