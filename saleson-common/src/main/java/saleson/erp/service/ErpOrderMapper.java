package saleson.erp.service;

import com.onlinepowers.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import saleson.common.utils.ShopUtils;
import saleson.erp.domain.ErpOrderStatus;
import saleson.erp.domain.ErpOrderType;
import saleson.erp.domain.OrderLine;
import saleson.erp.domain.OrderLineStatus;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.order.claimapply.domain.OrderCancelApply;
import saleson.shop.order.claimapply.domain.OrderExchangeApply;
import saleson.shop.order.claimapply.domain.OrderReturnApply;
import saleson.shop.order.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class ErpOrderMapper {
	@Autowired
	private ItemService itemService;
	public List<OrderLine> mapFrom(ErpOrder erpOrder) {
		List<OrderLine> orderLines = new ArrayList<>();

		// 신규 주문
		if (erpOrder.getErpOrderType() == ErpOrderType.ORDER) {
			int itemId = 0;
			int optionIndex = 0; // ERP Uniq 생성시 필요
			String optionType = "";

			String additionBundleNo = "";
			for (BuyItem buyItem : erpOrder.getBuyItems()) {

				String[] options = StringUtils.delimitedListToStringArray(buyItem.getOptions(), "^^^");

				// 본품 데이터 세팅: 옵션조합형이 아닐 경우에는 옵션마다 각각의 본품 row도 계속 생성돼야 함
				if (("C".equals(optionType) && buyItem.getOptionIndex() == 0) || !"C".equals(optionType)) {
					optionIndex = 0;

					// 옵션을 사용하지 않는 상품의 옵션에 임의로 "N" 세팅
					if (options.length == 0) {
						buyItem.setOptions("N");
					}

					OrderLine orderLine = this.toOrderLine(buyItem, additionBundleNo, buyItem.getItem().getStockCode());

					if ("N".equals(orderLine.getAdditionItemFlag())) {
						additionBundleNo = orderLine.getBundleNo();
					}

					orderLines.add(orderLine);

					itemId = buyItem.getItemId();
					optionIndex++;
				}

				// 옵션상품 데이터 세팅: 옵션마다 row 생성
				int orderQuantity = buyItem.getItemPrice().getQuantity();
				for (String option : options) {

					String[] temp = StringUtils.delimitedListToStringArray(option, "||");

					if (temp.length > 1) {

						optionType = temp[0];
						String itemName = temp[1] + " : " + temp[2];
						String optionStockCode = temp[temp.length - 3];
						int optionPrice = Integer.parseInt(temp[temp.length - 4]);
						int optionCostPrice = Integer.parseInt(temp[temp.length - 2]);
						int optionQuantity = Integer.parseInt(temp[temp.length - 1]);

						// 옵션 조합형이 아닐 경우 옵션의 총합 계산
						if (!"C".equals(optionType)) {
							optionPrice = 0;
							optionCostPrice = 0;

							for (String o : options) {
								String[] t = StringUtils.delimitedListToStringArray(o, "||");

								optionPrice += Integer.parseInt(t[t.length - 4]);
								optionCostPrice += Integer.parseInt(t[t.length - 2]);
							}
						}


						ItemPrice itemPrice = new ItemPrice();
						itemPrice.setQuantity(optionQuantity * orderQuantity);
						itemPrice.setCostPrice(optionCostPrice * orderQuantity);
						itemPrice.setOptionPrice(optionPrice * orderQuantity);

						buyItem.setItemPrice(itemPrice);
						buyItem.setOptionIndex(optionIndex);

						if (!"C".equals(optionType)) {
							itemName = ShopUtils.viewOptionTextNoUl(buyItem.getOptions());
						}

						itemName = optionPrice * orderQuantity > 0 ? "(추가옵션) " + itemName : itemName;
						buyItem.setItemName(itemName);
						buyItem.setOptions("");


						orderLines.add(this.toOrderLine(buyItem, additionBundleNo, optionStockCode));
						optionIndex++;

						if (!"C".equals(optionType)) {
							break;
						}

					}
				}

			}


		// 가상계좌 입금통보, 클레임 (취소, 반품, 교환)
		} else {

			int itemId = 0;
			String optionType = "";
			String additionBundleNo = "";

			for (OrderItem orderItem : erpOrder.getOrderItems()) {

				log.debug("[erpOrderMapper] erpOrderType: {}", erpOrder.getErpOrderType());
				log.debug("[erpOrderMapper] orderItem.getCancelApply() : {}", orderItem.getCancelApply());
				log.debug("[erpOrderMapper] orderItem.getReturnApply() : {}", orderItem.getReturnApply());
				log.debug("[erpOrderMapper] orderItem.getExchangeApply() : {}", orderItem.getExchangeApply());

				if (ErpOrderType.ORDER_CLAIM == erpOrder.getErpOrderType()) {
					if (orderItem.getCancelApply() == null && orderItem.getReturnApply() == null && orderItem.getExchangeApply() == null) {
						continue;
					}
				}

				int optionIndex = 0; // ERP Uniq 생성시 필요
				String[] options = StringUtils.delimitedListToStringArray(orderItem.getOptions(), "^^^");

				// 본품 데이터 세팅: 옵션조합형이 아닐 경우에는 옵션마다 각각의 본품 row도 생성돼야 함

				if (("C".equals(optionType) && orderItem.getOptionIndex() == 0) || !"C".equals(optionType)) {
					optionIndex = 0;

					// 옵션을 사용하지 않는 상품의 옵션에 임의로 "N" 세팅
					if (options.length == 0) {
						orderItem.setOptions("N");
					}

					OrderLine orderLine = this.toOrderLine(orderItem, additionBundleNo);

					if ("N".equals(orderLine.getAdditionItemFlag())) {
						additionBundleNo = orderLine.getBundleNo();
					}

					orderLines.add(orderLine);

					itemId = orderItem.getItemId();
					optionIndex++;
				}
				// 옵션상품 데이터 세팅: 옵션마다 row 생성
				for (String option : options) {

					String[] temp = StringUtils.delimitedListToStringArray(option, "||");

					if (temp.length > 1) {

						optionType = temp[0];
						String itemName = temp[1] + " : " + temp[2];					// 옵션명
						String optionStockCode = temp[temp.length - 3];					// 옵션 관리코드
						int optionPrice = Integer.parseInt(temp[temp.length - 4]);		// 옵션 판매가
						int optionCostPrice = Integer.parseInt(temp[temp.length - 2]);	// 옵션 원가
						int optionQuantity = Integer.parseInt(temp[temp.length - 1]);	// 옵션 판매수량

						// 옵션 조합형이 아닐 경우 옵션의 총합 계산
						if (!"C".equals(optionType)) {
							optionPrice = 0;
							optionCostPrice = 0;

							for (String o : options) {
								String[] t = StringUtils.delimitedListToStringArray(o, "||");

								optionPrice += Integer.parseInt(t[t.length - 4]);
								optionCostPrice += Integer.parseInt(t[t.length - 2]);
							}
						}

						orderItem.setStockCode(optionStockCode);
						orderItem.setOptionQuantity(optionQuantity);
						orderItem.setCostPrice(optionCostPrice);
						orderItem.setOptionPrice(optionPrice);			// 가상계좌 입금통보용
						orderItem.setSalePrice(optionPrice);			// 클레임 처리용
						orderItem.setCommissionBasePrice(optionPrice);	// 클레임 처리용
						orderItem.setOptionIndex(optionIndex);

						if (!"C".equals(optionType)) {
							itemName = ShopUtils.viewOptionTextNoUl(orderItem.getOptions());
						}

						itemName = optionPrice > 0 ? "(추가옵션) " + itemName : itemName;
						orderItem.setItemName(itemName);
						orderItem.setOptions("");


						orderLines.add(this.toOrderLine(orderItem, additionBundleNo));
						optionIndex++;

						if (!"C".equals(optionType)) {
							break;
						}
					}
				}

			}

		}

		orderLines.forEach(ol -> {
			this.bindOrder(ol, erpOrder.getBuyer());
			this.bindOrderShippingInfo(ol, erpOrder.getOrderShippingInfos());
			ol.setPayMethod(erpOrder.getPayMethod());
			ol.setBillType(erpOrder.getBillType());
		});

		return orderLines;
	}

	private OrderLine toOrderLine(BuyItem buyItem, String additionBundleNo, String stockCode) {
		OrderLine orderLine = OrderLine.builder()
				.orderCode(buyItem.getOrderCode())
				.orderSequence(buyItem.getOrderSequence())
				.itemSequence(buyItem.getItemSequence())
				.shippingSequence(buyItem.getShippingSequence())
				.shippingInfoSequence(buyItem.getShippingInfoSequence())
				.additionItemFlag(buyItem.getAdditionItemFlag())
				.additionBundleNo(additionBundleNo)
				.orderStatus(ErpOrderStatus.ORDERED.getErpCode())
				.statusCode("00")
				.optionIndex(buyItem.getOptionIndex())
				.build();

		String additionItemName = "Y".equals(buyItem.getAdditionItemFlag()) ? "┗(추가상품) " : "";
		String shippingType = buyItem.getBuyShipping().getShippingType();

		// 배송비가 있으면 선결제, 없으면 무료배송
		String shipMethod = "";
		if ("Y".equals(buyItem.getQuickDeliveryFlag())) {
			shipMethod = "퀵";
		} else if ("3".equals(shippingType) && buyItem.getBuyShipping().getRealShipping() == 0) {
			shipMethod = "무료배송";
		} else if ("1".equals(buyItem.getShippingPaymentType())) {
			shipMethod = "선결제";
		} else if ("2".equals(buyItem.getShippingPaymentType())) {
			shipMethod = "착불";
		} else {
			shipMethod = "무료배송";
		}


		// 본상품, 추가구성상품
		int sales = 0;
		int shopSupplyPrice = 0;
		if (!"".equals(buyItem.getOptions()) || "Y".equals(buyItem.getAdditionItemFlag()) || "N".equals(buyItem.getOptionList())) {
			int quantity = buyItem.getItemPrice().getQuantity();

			sales = buyItem.getItemPrice().getItemSalePrice() * quantity;
			shopSupplyPrice = buyItem.getItemPrice().getPrice() * quantity;

			// 임의로 세팅한 "N" 원복
			if ("N".equals(buyItem.getOptions())) {
				buyItem.setOptions("");
			}

		// 옵션 상품
		} else {
			sales = buyItem.getItemPrice().getOptionPrice();
			shopSupplyPrice = sales;
		}

		// 값을 mapping
		//orderLine.setCarrName(buyItem.getDeliveryCompanyName());
		orderLine.setSkuCd(stockCode);
		orderLine.setShopSaleNo(buyItem.getItemUserCode());
		orderLine.setShopSaleName(additionItemName + buyItem.getItemName());
		orderLine.setShopOptName(ShopUtils.viewOptionTextNoUl(buyItem.getOptions()));
		orderLine.setSaleCnt(buyItem.getItemPrice().getQuantity());
		orderLine.setGiftName(buyItem.getFreeGiftName());

		orderLine.setShipMethod(shipMethod);
		orderLine.setShipCost(buyItem.getBuyShipping().getRealShipping());

		// 판매금액: 할인되기 전 금액
		orderLine.setSales(sales);
		// 원가
		orderLine.setShopCostPrice(buyItem.getItemPrice().getCostPrice());
		// 쇼핑몰 공급가: 실제 판매되는 금액, 판매가에 할인이 적용된 금액
		orderLine.setShopSupplyPrice(shopSupplyPrice);

		return orderLine;
	}

	private OrderLine toOrderLine(OrderItem orderItem, String additionBundleNo) {

		int quantity = orderItem.getQuantity();
		int optionQuantity = orderItem.getOptionQuantity();
		int realShipping = orderItem.getOrderShipping().getRealShipping();		// 가상계좌 입금통보용
		String shippingType = orderItem.getOrderShipping().getShippingType();	// 가상계좌 입금통보용
		String orderStatus = ErpOrderStatus.ORDERED.getErpCode();				// 가상계좌 입금통보용
		String statusCode = "00";

		// 본상품, 추가구성상품
		int sales = 0;
		int shopSupplyPrice = 0;
		if (!"".equals(orderItem.getOptions()) || "Y".equals(orderItem.getAdditionItemFlag()) || "N".equals(orderItem.getOptions())) {
			sales = orderItem.getPurchasePrice();
			shopSupplyPrice = orderItem.getPrice();

		// 옵션 상품
		} else {

			if (quantity != 0) {	// 입금통보
				orderItem.setCostPrice(quantity * orderItem.getCostPrice());
			}

			sales = orderItem.getOptionPrice() * quantity;
			shopSupplyPrice = sales;

			quantity = quantity * orderItem.getOptionQuantity();	// 본품 수량과 sales 계산 후 총옵션 수량 set
		}


		// 클레임
		String claimStatus = "";
		String claimReason = "";
		boolean isReturnExchange = false;

		if (orderItem.getCancelApply() != null) {
			OrderCancelApply cancelApply = orderItem.getCancelApply();

			int claimApplyQuantity = cancelApply.getClaimApplyQuantity();
			quantity =  optionQuantity > 0 ? optionQuantity * claimApplyQuantity : claimApplyQuantity;

			claimStatus = cancelApply.getClaimStatus();
			sales = orderItem.getPurchasePrice() * claimApplyQuantity;
			shopSupplyPrice = orderItem.getPrice() * claimApplyQuantity;

			if (optionQuantity > 0) {
				sales = orderItem.getCommissionBasePrice() * claimApplyQuantity;
				shopSupplyPrice = orderItem.getSalePrice() * claimApplyQuantity;
			}

			realShipping = orderItem.getOrderShipping().getRealShipping();

			orderItem.setCostPrice(orderItem.getCostPrice() * claimApplyQuantity);

			// 취소 신청
			if ("01".equals(claimStatus)) {
				orderStatus = ErpOrderStatus.CANCEL_APPLY.getErpCode();
				statusCode = ErpOrderStatus.CANCEL_APPLY.getCode();

			// 취소 승인, 취소 완료
			} else if ("03".equals(claimStatus) || "04".equals(claimStatus)) {
				orderStatus = ErpOrderStatus.CANCELED.getErpCode();
				statusCode = ErpOrderStatus.CANCELED.getCode();
			}

			// 취소 사유
			if (!" ".equals(cancelApply.getCancelReasonDetail())) {
				claimReason = cancelApply.getCancelReasonDetail();
			} else {
				claimReason = cancelApply.getCancelReasonText();
			}


		} else if (orderItem.getReturnApply() != null) {
			OrderReturnApply returnApply = orderItem.getReturnApply();
			isReturnExchange = true;

			int claimApplyQuantity = returnApply.getClaimApplyQuantity();
			quantity =  optionQuantity > 0 ? optionQuantity * claimApplyQuantity : claimApplyQuantity;

			claimStatus = returnApply.getClaimStatus();
			sales = orderItem.getPurchasePrice() * claimApplyQuantity;
			shopSupplyPrice = orderItem.getPrice() * claimApplyQuantity;

			if (optionQuantity > 0) {
				sales = orderItem.getCommissionBasePrice() * claimApplyQuantity;
				shopSupplyPrice = orderItem.getSalePrice() * claimApplyQuantity;
			}

			realShipping = orderItem.getShippingReturn();

			// 반품 신청
			if ("01".equals(claimStatus)) {
				orderStatus = ErpOrderStatus.RETURN_APPLY.getErpCode();
				statusCode = ErpOrderStatus.RETURN_APPLY.getCode();

			// 반품 승인
			} else if ("03".equals(claimStatus)) {
				orderStatus = ErpOrderStatus.RETURNED.getErpCode();
				statusCode = ErpOrderStatus.RETURNED.getCode();
			}

			// 반품 사유
			if (!" ".equals(returnApply.getReturnReasonDetail())) {
				claimReason = returnApply.getReturnReasonDetail();
			} else {
				claimReason = returnApply.getReturnReasonText();
			}


		} else if (orderItem.getExchangeApply() != null) {
			OrderExchangeApply exchangeApply = orderItem.getExchangeApply();
			isReturnExchange = true;

			int claimApplyQuantity = exchangeApply.getClaimApplyQuantity();
			quantity =  optionQuantity > 0 ? optionQuantity * claimApplyQuantity : claimApplyQuantity;

			claimStatus = exchangeApply.getClaimStatus();
			sales = orderItem.getPurchasePrice() * claimApplyQuantity;
			shopSupplyPrice = orderItem.getPrice() * claimApplyQuantity;

			if (optionQuantity > 0) {
				sales = orderItem.getCommissionBasePrice() * claimApplyQuantity;
				shopSupplyPrice = orderItem.getSalePrice() * claimApplyQuantity;
			}

			realShipping = orderItem.getShippingReturn();

			// 교환상품 회수 완료
			if ("11".equals(claimStatus)) {
				orderStatus = ErpOrderStatus.EXCHANGE_APPLY.getErpCode();
				statusCode = ErpOrderStatus.EXCHANGE_APPLY.getCode();
			}

			// 교환 사유
			if (!" ".equals(exchangeApply.getExchangeReasonDetail())) {
				claimReason = exchangeApply.getExchangeReasonDetail();
			} else {
				claimReason = exchangeApply.getExchangeReasonText();
			}
		}


		// 반품, 교환이 아닌 경우에만 배송방법 처리 - 배송비가 있으면 선결제, 없으면 무료배송
		String shipMethod = "";
		if (!isReturnExchange) {
			if ("Y".equals(orderItem.getQuickDeliveryFlag())) {
				shipMethod = "퀵";
			} else if ("3".equals(shippingType) && realShipping == 0) {
				shipMethod = "무료배송";
			} else if ("1".equals(orderItem.getOrderShipping().getShippingPaymentType())) {
				shipMethod = "선결제";
			} else if ("2".equals(orderItem.getOrderShipping().getShippingPaymentType())) {
				shipMethod = "착불";
			} else {
				shipMethod = "무료배송";
			}
		}

		Item item = itemService.getItemById(orderItem.getItemId());


		OrderLine orderLine = orderLineBuilder(orderItem, orderStatus, statusCode, additionBundleNo);

		if (!ErpOrderStatus.ORDERED.getErpCode().equals(orderStatus)) {
			String erpOriginUnique = orderItem.getErpOriginUnique();

			if ("N".equals(orderItem.getAdditionItemFlag())) {
				erpOriginUnique = erpOriginUnique.substring(0, 16) + (orderItem.getOptionIndex() < 10 ? "0" + orderItem.getOptionIndex() : orderItem.getOptionIndex());
			}

			orderLine.setOriUniq(erpOriginUnique);
		}

		String stockCode = "";
		if (("".equals(orderItem.getStockCode()) || orderItem.getStockCode() == null)) {

			// 옵션상품 중 관리코드가 없으면 null, 본품인데 관리코드가 없으면 item.getStockCode
			stockCode = "".equals(orderItem.getOptions()) ? null : item.getStockCode();

			// 임의로 세팅한 "N" 원복
			if ("N".equals(orderItem.getOptions())) {
				orderItem.setOptions("");
			}

		} else {
			stockCode = orderItem.getStockCode();
		}

		String itemName = "Y".equals(orderItem.getAdditionItemFlag()) ? "┗(추가상품)" : "";

		// 값을 mapping
		//orderLine.setCarrName(orderItem.getDeliveryCompanyName());
		orderLine.setSkuCd(stockCode);
		orderLine.setShopSaleNo(orderItem.getItemUserCode());
		orderLine.setShopSaleName(itemName + orderItem.getItemName());
		orderLine.setShopOptName(ShopUtils.viewOptionTextNoUl(orderItem.getOptions()));
		orderLine.setGiftName("Y".equals(item.getFreeGiftFlag()) ? item.getFreeGiftName() : "");
		orderLine.setSaleCnt(quantity);

		orderLine.setShipMethod(shipMethod);
		orderLine.setShipCost(realShipping);

		// 판매금액 - 할인되기 전 금액
		orderLine.setSales(sales);
		// 원가
		orderLine.setShopCostPrice(orderItem.getCostPrice());
		// 쇼핑몰 공급가 - 실제 판매되는 금액, 판매가에 할인이 적용된 금액
		orderLine.setShopSupplyPrice(shopSupplyPrice);

		orderLine.setOrdStatusMsg(claimReason);

		return orderLine;
	}

   	private void bindOrder(OrderLine orderLine, Buyer buyer) {
		String fullPhone = buyer.getFullPhone();
		if (fullPhone.length() < 10 || fullPhone.indexOf("null") > -1) {
			fullPhone = "";
		}

		orderLine.setOrderName(buyer.getUserName());
		orderLine.setOrderId(buyer.getLoginId());
		orderLine.setOrderTel(fullPhone);
		orderLine.setOrderHtel(buyer.getFullMobile());
		orderLine.setOrderEmail(buyer.getEmail());

	}

	private void bindOrderShippingInfo(OrderLine orderLine, List<OrderShippingInfo> orderShippingInfos) {
		if (!orderShippingInfos.isEmpty()) {

			Optional<OrderShippingInfo> osiOptional = null;
			osiOptional = orderShippingInfos.stream()
					.filter(o -> o.getOrderCode().equals(orderLine.getOrderCode())
							&& o.getShippingInfoSequence() == orderLine.getShippingInfoSequence())
					.findFirst();


			if (osiOptional.isPresent()) {
				OrderShippingInfo osi = osiOptional.get();

				String receivePhone = osi.getReceivePhone();
				if (receivePhone.length() < 10 || receivePhone.indexOf("null") > -1) {
					receivePhone = "";
				}

				orderLine.setShipMsg(osi.getMemo());
				orderLine.setToName(osi.getReceiveName());
				orderLine.setToTel(receivePhone);
				orderLine.setToHtel(osi.getReceiveMobile());
				orderLine.setToAddr1(osi.getReceiveAddress() + " " + osi.getReceiveAddressDetail());
				orderLine.setToZipcd(osi.getReceiveNewZipcode());
			}
		}
	}

	public List<OrderItem> map(List<OrderLineStatus> orderLineStatusList, List<OrderItem> orderItems) {

		orderItems.stream().forEach(oi -> {
			Optional<OrderLineStatus> optional = orderLineStatusList.stream()
					.filter(s -> s.getUniq().equals(oi.getUniq())).findFirst();

			if (optional.isPresent()) {
				OrderLineStatus ols = optional.get();
				//oi.setDeliveryCompanyId(ols.getCarrNo());
				//oi.setDeliveryCompanyName(ols.getCarrName());
				oi.setDeliveryNumber(ols.getInvoiceNo());
				Optional<ErpOrderStatus> optionalOrderStatus = findErpOrderStautsByErpCode(ols.getOrdStatus());

				if (optionalOrderStatus.isPresent()) {
					// 교환일 경우 GET, PUT 테이블의 ORDER_STATUS가 모두 '상품교환'이므로 code값 55로 세팅
					if ("상품교환".equals(ols.getOrdStatus())) {
						oi.setOrderStatus("55");

					} else {
						oi.setOrderStatus(optionalOrderStatus.get().getCode());
					}
				}
			}
		});

		return orderItems;
	}


	private Optional<ErpOrderStatus> findErpOrderStautsByErpCode(String erpCode) {
		return Arrays.stream(ErpOrderStatus.values())
				.filter(e -> e.isEqualToErpCode(erpCode))
				.findFirst();
	}

	private OrderLine orderLineBuilder(OrderItem orderItem, String orderStatus, String statusCode, String additionBundleNo) {
		OrderLine orderLine = OrderLine.builder()
				.orderCode(orderItem.getOrderCode())
				.orderSequence(orderItem.getOrderSequence())
				.itemSequence(orderItem.getCopyItemSequence() > 0 ? orderItem.getCopyItemSequence() : orderItem.getItemSequence())
				.shippingSequence(orderItem.getShippingSequence())
				.shippingInfoSequence(orderItem.getShippingInfoSequence())
				.additionItemFlag(orderItem.getAdditionItemFlag())
				.additionBundleNo(additionBundleNo)
				.orderStatus(orderStatus)
				.statusCode(statusCode)
				.optionIndex(orderItem.getOptionIndex())
				.build();

		return orderLine;
	}
}
