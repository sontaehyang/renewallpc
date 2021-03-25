package saleson.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.pg.payco.domain.*;
import saleson.shop.order.support.OrderException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaycoUtils {
	private static final Logger log = LoggerFactory.getLogger(PaycoUtils.class);

	private static String PAYCO_SELLER_CP_ID;

	private PaycoUtils() {
	}

	@Value("${payco.seller.cpId}")
	public void setPaycoSellerCpId(String paycoSellerCpId) {
		PAYCO_SELLER_CP_ID = paycoSellerCpId;
	}

	
	public static String getBankName(String bankCode) {
		Map<String, String> bankMap = new HashMap<>();
		bankMap.put("11" ,"농협");
		bankMap.put("20" ,"우리은행");
		bankMap.put("23" ,"SC제일은행");
		bankMap.put("26" ,"신한은행");
		bankMap.put("27" ,"씨티은행");
		bankMap.put("31" ,"대구은행");
		bankMap.put("32" ,"부산은행");
		bankMap.put("34" ,"광주은행");
		bankMap.put("35" ,"제주은행");
		bankMap.put("37" ,"전북은행");
		bankMap.put("39" ,"경남은행");
		bankMap.put("45" ,"새마을금고 ");
		bankMap.put("48" ,"신용협동조합");
		bankMap.put("50" ,"상호신용금고");
		bankMap.put("54" ,"HSBC은행");
		bankMap.put("55" ,"도이치은행 ");
		bankMap.put("71" ,"우체국 ");
		bankMap.put("81" ,"하나은행");
		bankMap.put("88" ,"신한은행");
		bankMap.put("209" ,"동양종합금융증권");
		bankMap.put("218" ,"현대증권");
		bankMap.put("230" ,"미래에셋증권");
		bankMap.put("238" ,"대우증권");
		bankMap.put("240" ,"삼성증권");
		bankMap.put("243" ,"한국투자증권");
		bankMap.put("247" ,"우리투자증권");
		bankMap.put("261" ,"교보증권");
		bankMap.put("262" ,"하이투자증권");
		bankMap.put("263" ,"HMC투자증권 ");
		bankMap.put("264" ,"키움증권");
		bankMap.put("265" ,"이트레이드증권 ");
		bankMap.put("266" ,"SK증권");
		bankMap.put("267" ,"대신증권");
		bankMap.put("268" ,"솔로몬투자증권 ");
		bankMap.put("269" ,"한화증권");
		bankMap.put("270" ,"하나대투증권");
		bankMap.put("278" ,"신한금융투자");
		bankMap.put("279" ,"동부증권");
		bankMap.put("280" ,"유진투자증권");
		bankMap.put("287" ,"메리츠종합금융증권 ");
		bankMap.put("289" ,"NH투자증권");
		bankMap.put("290" ,"부국증권");
		bankMap.put("291" ,"신영증권");
		bankMap.put("292" ,"LIG투자증권 ");
		bankMap.put("02" ,"산업은행");
		bankMap.put("03" ,"기업은행");
		bankMap.put("04" ,"국민은행");
		bankMap.put("05" ,"외환은행");
		bankMap.put("07" ,"수협");
		
		return bankMap.get(bankCode);
	}
	public static String getCardCompany(String cardCode) {
		Map<String, String> cardMap = new HashMap<>();
		cardMap.put("CCBC", "BC카드");
		cardMap.put("CCKM", "KB국민카드");
		cardMap.put("CCNH", "NH농협카드");
		cardMap.put("CCKJ", "광주카드");
		cardMap.put("CCAM", "롯데아멕스카드");
		cardMap.put("CCLO", "롯데카드");
		cardMap.put("CCKD", "산업카드");
		cardMap.put("CCSS", "삼성카드");
		cardMap.put("CCSU", "수협카드");
		cardMap.put("CCSG", "신세계한미");
		cardMap.put("CCLG", "신한카드");
		cardMap.put("CCCU", "신협카드");
		cardMap.put("CCCT", "씨티카드");
		cardMap.put("CCPH", "우리카드");
		cardMap.put("CCUF", "은련카드");
		cardMap.put("CCSB", "저축카드");
		cardMap.put("CCJB", "전북카드");
		cardMap.put("CCCJ", "제주카드");
		cardMap.put("CCKE", "하나(외환)카드");
		cardMap.put("CCHN", "하나카드");
		cardMap.put("CCHM", "한미카드");
		cardMap.put("CJCF", "해외JCB");
		cardMap.put("CMCF", "해외마스터");
		cardMap.put("CVSF", "해외비자");
		cardMap.put("CCDI", "현대카드");
		
		return cardMap.get(cardCode);
	}
	public static String getPaymentMethod(String methodCode) {
		Map<String, String> methodMap = new HashMap<>();
		methodMap.put("01" ,"신용카드(일반)");
		methodMap.put("02" ,"무통장입금");
		methodMap.put("04" ,"계좌이체");
		methodMap.put("05" ,"휴대폰(일반)");
		methodMap.put("31" ,"신용카드");
		methodMap.put("35" ,"계좌(결제/송금)");
		methodMap.put("60" ,"휴대폰");
		methodMap.put("98" ,"PAYCO 포인트");
		methodMap.put("75" ,"페이코 쿠폰(자유이용쿠폰)");
		methodMap.put("76" ,"카드 쿠폰");
		methodMap.put("77" ,"가맹점 쿠폰");
		methodMap.put("96" ,"충전금 환불");
		
		return methodMap.get(methodCode);
	}
	
	public static String makePgLog(PayApprovalResponse payApprovalResponse) {
		StringBuffer sb = new StringBuffer();
		
		/*****************************************************************************************************************
		 *  1 모든 결제 수단에 공통되는 결제 결과 데이터
		 * 	거래번호 : inipay.GetResult("tid")
		 * 	결과코드 : inipay.GetResult("ResultCode") ("00"이면 지불 성공)
		 * 	결과내용 : inipay.GetResult("ResultMsg") (지불결과에 대한 설명)
		 * 	지불방법 : inipay.GetResult("PayMethod") (매뉴얼 참조)
		 * 	상점주문번호 : inipay.GetResult("MOID")
		 *	결제완료금액 : inipay.GetResult("TotPrice")
		 * 	이니시스 승인날짜 : inipay.GetResult("ApplDate") (YYYYMMDD)
		 * 	이니시스 승인시각 : inipay.GetResult("ApplTime") (HHMMSS)
		 *  
		 *  가상계좌 채번에 사용된 주민번호 : inipay.GetResult("VACT_RegNum")
		 * 	가상계좌 번호 : inipay.GetResult("VACT_Num")
		 * 	입금할 은행 코드 : inipay.GetResult("VACT_BankCode")
		 * 	입금예정일 : inipay.GetResult("VACT_Date") (YYYYMMDD)
		 * 	송금자 명 : inipay.GetResult("VACT_InputName")
		 * 	예금주 명 : inipay.GetResult("VACT_Name")
		 */
		
		String[] keys = new String[]{
			"orderNo", "code", "message", "paymentMethodCode", "totalPaymentAmt", "paymentCompletionYn", "orderCertifyKey", "paymentCompletionYn"
		};
		String[] paymentKeys = new String[]{};
		
		Boolean isMultiple = payApprovalResponse.getResult().getPaymentDetails().size() > 1 ? true : false;
		sb.append("isMultiple -> " + isMultiple + "\n");
		
		String paymentType = "";
		CardSettleInfo card = null;	
		CellphoneSettleInfo cp = null;
		NonBankbookSettleInfo bank = null;
		RealtimeAccountTransferSettleInfo realTime = null;	
		
		String paymentString = "{";
		for (PaymentDetail paymentDetail : payApprovalResponse.getResult().getPaymentDetails()) {
			String paymentMethodCode = paymentDetail.getPaymentMethodCode();
			if (paymentMethodCode.equals("31")) { 
				paymentType = "cardEasy";
				paymentKeys = new String[]{
					"cardCompanyCode", "cardNo", "cardInstallmentMonthNumber", "cardAdmissionNo", "corporateCardYn", "partCancelPossibleYn"
				};
				card = paymentDetail.getCardSettleInfo();
			}
			else if (paymentMethodCode.equals("35")) { 
				paymentType = "bankEasy";
				bank = paymentDetail.getNonBankbookSettleInfo();
				paymentKeys = new String[]{
					"bankName", "bankCode", "accountNo", "paymentExpirationYmd"
				};
			}
			else if (paymentMethodCode.equals("60")) { 
				paymentType = "cpEasy";
				cp = paymentDetail.getCellphoneSettleInfo();
			}
			else if (paymentMethodCode.equals("01")) {
				paymentType = "card";
				card = paymentDetail.getCardSettleInfo();
				paymentKeys = new String[]{
					"cardCompanyCode", "cardNo", "cardInstallmentMonthNumber", "cardAdmissionNo", "corporateCardYn", "partCancelPossibleYn"
				};
			}
			else if (paymentMethodCode.equals("02")) { 
				paymentType = "bank";
				bank = paymentDetail.getNonBankbookSettleInfo();
				paymentKeys = new String[]{
					"bankName", "bankCode", "accountNo", "paymentExpirationYmd"
				};
			}
			else if (paymentMethodCode.equals("04")) { 
				paymentType = "bank";
				realTime = paymentDetail.getRealtimeAccountTransferSettleInfo();
			}
			else if (paymentMethodCode.equals("05")) { 
				paymentType = "cp";
				cp = paymentDetail.getCellphoneSettleInfo();
			}
			else {
				paymentString += ",[paymentMethodCode:" + paymentDetail.getPaymentMethodCode() 
								+ "paymentMethodName:" + paymentDetail.getPaymentMethodName() 
								+ "paymentAmt:" + paymentDetail.getPaymentAmt()
								+ "tradeYmdt:" + paymentDetail.getTradeYmdt()
								+ "pgAdmissionNo:" + paymentDetail.getPgAdmissionNo()
								+ "pgAdmissionYmdt:" + paymentDetail.getPgAdmissionYmdt()
								+ "easyPaymentYn:" + paymentDetail.getEasyPaymentYn() + "]";
			}
		}
		paymentString += "}";
		paymentString = paymentString.replace("{,[", "{[");
		
		for(String key : keys) {
			String value = String.valueOf(payApprovalResponse.getResult());
			sb.append(key + " -> " + value + "\n");
		}
		
		for(String key : paymentKeys) {
			String value = String.valueOf(payApprovalResponse.getResult());
			sb.append(key + " -> " + value + "\n");
		}
		
		return sb.toString();
	}

	/**
	 * 페이코 주문취소 상품정보 만들기
	 * @param orderItem
	 * @param returnAmount
	 * @param addShipping
	 * @param pgDataInfo
	 * @return
	 */
	public static List<CancelProduct> makePaycoCancelProducts(OrderItem orderItem, int returnAmount, int addShipping, String pgDataInfo) {
		
		List<CancelProduct> list = new ArrayList<>();
		
		CancelProduct cancelProduct = new CancelProduct();
		cancelProduct.setCpId(PAYCO_SELLER_CP_ID);
		cancelProduct.setProductId("PROD_EASY");
		cancelProduct.setProductAmt(addShipping < 0 ? returnAmount - (-addShipping) : returnAmount);
		cancelProduct.setSellerOrderProductReferenceKey(orderItem.getItemUserCode());
		cancelProduct.setCancelDetailContent("사용자 주문취소 요청");
		list.add(cancelProduct);
		
		// 추가 배송비가 0보자 작으면 배송비 환불 발생!! 
		if (addShipping < 0) {
			
			if (StringUtils.isEmpty(pgDataInfo)) {
				throw new OrderException("잘못된 접근입니다.");
			}
			
			boolean hasShippingItem = false;
			try {
				PayApprovalResponse payApprovalResponse = (PayApprovalResponse) JsonViewUtils.jsonToObject(pgDataInfo, new TypeReference<PayApprovalResponse>(){});
				for(PayProduct payProduct : payApprovalResponse.getResult().getOrderProducts()) {
					if ("SHIPPING-ITEM".equals(payProduct.getSellerOrderProductReferenceKey())) {
						hasShippingItem = true;
						break;
					}
				}
			} catch(Exception e) {
				log.error("PAYCO부분취소 데이터 검증도중 에러가 발생하였습니다 : {}", e.getMessage(), e);
				throw new OrderException("PAYCO부분취소 데이터 검증도중 에러가 발생하였습니다.");
			}
			
			// 최초 결제 데이터에 배송비 상품이 없었으면.. 상품 정보에 배송비를 포함해서 환불해야됨
			if (hasShippingItem == false) {
				
				cancelProduct.setProductAmt(returnAmount);
				
			} else {
				CancelProduct cancelShipping = new CancelProduct();
				cancelShipping.setCpId(PAYCO_SELLER_CP_ID);
				cancelShipping.setProductId("PROD_EASY");
				cancelShipping.setProductAmt((-addShipping));
				cancelShipping.setSellerOrderProductReferenceKey("SHIPPING-ITEM");
				cancelShipping.setCancelDetailContent("사용자 주문취소 요청");
				list.add(cancelShipping);
			}
		} 
		
		list.add(cancelProduct);
		return list;
	}
	
}