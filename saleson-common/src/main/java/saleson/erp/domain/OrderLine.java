package saleson.erp.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import saleson.erp.service.ErpOrder;

@Slf4j @Getter @Setter @AllArgsConstructor
@ToString
public class OrderLine {
	private String orderCode;
	private int orderSequence;
	private int itemSequence;
	private int shippingSequence;
	private int shippingInfoSequence;
	private String additionItemFlag;


	private int solNo;
	private String uniq;
	private String oriUniq;
	private String bundleNo;
	private String ordStatus;
	private String skuCd;
	private int carrNo;
	private String carrName;
	private String invoiceNo;
	private String shipPlanDate;
	private String shopCd;
	private String shopName;
	private String shopId;
	private String sellerNick;
	private String shopOrdNo;
	private String shopSaleNo;
	private String shopSaleName;
	private String shopOptName;
	private int saleCnt;
	private String shopAddOptName;
	private String shipMethod;
	private String outTime;
	private String wdate;
	private String ordTime;
	private String payTime;
	private String ordConfirmTime;
	private int sales;
	private int salesTax;
	private int shopCostPrice;
	private int shopSupplyPrice;
	private int shipDelayYn;
	private String orderName;
	private String orderId;
	private String orderTel;
	private String orderHtel;
	private String orderEmail;
	private String shipMsg;
	private String outOrderTime;
	private String toCtryCd;
	private String toName;
	private String toTel;
	private String toHtel;
	private String toAddr1;
	private String toAddr2;
	private String toZipcd;
	private int shipCost;
	private String cSaleCd;
	private String ordCurrCd;
	private String gprivateNo;
	private String barcode;
	private int depotNo;
	private String depotName;
	private String invoiceSendTime;
	private String giftName;
	private String giftProdName;
	private int mapYn;
	private int shipAvailYn;
	private String shipUnableReason;
	private String ordStatusMsg;
	private int exchangeYn;
	private int memoYn;
	private int bundleAvailYn;
	private int setNo;
	private int setPackUnit;
	private String setName;
	private String payMethod;
	private String misc1;
	private int recordstotal;
	private String billType;
	private int optionIndex;
	private String applyFlag;

	private String additionBundleNo;

	@Builder
	public OrderLine(String orderCode, int orderSequence, int itemSequence, int shippingSequence, int shippingInfoSequence, String additionItemFlag, String additionBundleNo, String payMethod, String billType, String orderStatus, String statusCode, int optionIndex) {
		this.orderCode = orderCode;
		this.orderSequence = orderSequence;
		this.itemSequence = itemSequence;
		this.shippingSequence = shippingSequence;
		this.shippingInfoSequence = shippingInfoSequence;
		this.additionItemFlag = additionItemFlag;
		this.additionBundleNo = additionBundleNo;

		this.shopCd = "S001";
		this.shopName = "리뉴올쇼핑몰";
		this.solNo = 9999;
		this.uniq = orderCode + "" + orderSequence + "" + (itemSequence < 10 ? "0" + itemSequence : itemSequence) + "" + statusCode + "" + (optionIndex < 10 ? "0" + optionIndex : optionIndex);
		this.bundleNo = getNewBundleNo();
		this.shopOrdNo = orderCode;
		this.ordStatus = orderStatus;
		this.payMethod = payMethod;
		this.billType = billType;
		this.applyFlag = "N";
	}

	private String getNewBundleNo() {
		if ("Y".equals(this.getAdditionItemFlag())) {
			return additionBundleNo;
		}
		return orderCode + "" + orderSequence + "" + (itemSequence < 10 ? "0" + itemSequence : itemSequence);
	}
}
