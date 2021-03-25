package saleson.shop.order.pg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.notification.exception.NotificationException;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.cj.domain.CjResult;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("cjService")
public class CjServiceImpl implements PgService {
	private static final Logger log = LoggerFactory.getLogger(CjServiceImpl.class);

	@Autowired
	Environment environment;

	@Override
	public String getPayType(String payType) {
		if ("card".equals(payType)) {
			return "Creditcard";
		} else if ("vbank".equals(payType)) {
			return "VirtualAccount";
		} else if ("realtimebank".equals(payType)) {
			return "Account";
		}
			
		
		return "";
	}

	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {
		
		String payMethod = this.getPayType(((PgData) data).getApprovalType());
		
		HashMap<String, Object> payReqMap = new HashMap<>();
		payReqMap.put("PayMethod", payMethod);
		payReqMap.put("ShopOrderNo", ((PgData) data).getOrderCode());
		payReqMap.put("GoodsName", ((PgData) data).getGoodname());
		payReqMap.put("ShopID", environment.getProperty("pg.cj.mid"));
		payReqMap.put("UserID", ((PgData) data).getUserID());
		payReqMap.put("UserName", ((PgData) data).getUsername());
		payReqMap.put("UserEmail", ((PgData) data).getUserEmail());
		payReqMap.put("UserPhone", ((PgData) data).getUserPhone());
		payReqMap.put("AmountTotal", ((PgData) data).getAmount());
		
		if ("VirtualAccount".equals(payMethod)) {
			
			// 가상계좌 입금 만료일 7일이후 23시 59분 59초까지
			String closeDate = DateUtils.addDay(DateUtils.getToday(Const.DATE_FORMAT), 7);
			closeDate += "235959";
			payReqMap.put("CloseDate", closeDate);
			
		}
		
		String redirectUrl = environment.getProperty("saleson.url.shoppingmall");
		if (ShopUtils.isMobilePage()) {
			redirectUrl +=  ShopUtils.getMobilePrefix();
		}
		redirectUrl +=  "/order/cj/redirect-url";
		
		String callbackUrl = environment.getProperty("saleson.url.shoppingmall") + "/order/cj/callback-url";
		if (ServiceType.LOCAL) {
			callbackUrl = "https://mall2.onlinepowers.com/order/cj/callback-url";
		}
		payReqMap.put("RedirectUrl", redirectUrl); // 결제완료 화면URL
		payReqMap.put("CallbackUrl", callbackUrl); // 결제완료처리 서버 URL
		payReqMap.put("reserve01", session.getId()); // 여분필드에 sessionId셋팅
		payReqMap.put("reserve02", UserUtils.getUserId()); // 여분필드에 UserId셋팅
		return payReqMap;
	}

	@Override
	public OrderPgData pay(Object data, HttpSession session) {
		OrderPgData orderPgData = new OrderPgData();
        orderPgData.setSuccess(false);
        
        try {
        	
        	CjResult result = (CjResult) data;
        	orderPgData.setPgAuthCode(result.getCJSPayID());
        	orderPgData.setPgKey(result.getCJSTradeID());
        	orderPgData.setPgProcInfo(this.makePgLog(result));
        	orderPgData.setSuccess(true);
        	
        	// 카드만 부분취소가 가능함
        	orderPgData.setPartCancelFlag("N");
        	
        	if ("Creditcard".equals(result.getCJSPayMethod())) {
        		orderPgData.setPartCancelFlag("Y"); // CJ는 다 부분취소가 되나? 지금 작업된건 카드밖에 없으니...
        	}
        	
        } catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
        	orderPgData.setSuccess(false);
        }
        
		return orderPgData;
	}

	private String makePgLog(CjResult result) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("CJSShopID -> " + result.getCJSShopID() + "\n");
		sb.append("CJSResultCode -> " + result.getCJSResultCode() + "\n");
		sb.append("CJSResultMessage -> " + result.getCJSResultMessage() + "\n");
		sb.append("CJSPayID -> " + result.getCJSPayID() + "\n");
		sb.append("CJSTradeID -> " + result.getCJSTradeID() + "\n");
		sb.append("CJSPayDate -> " + result.getCJSPayDate() + "\n");
		
		if ("Creditcard".equals(result.getCJSPayMethod())) {
			
			sb.append("CJSCardCode -> " + result.getCJSCardCode() + "\n");
			sb.append("CJSCardName -> " + result.getCJSCardName() + "\n");
			sb.append("CJSApprovalNo -> " + result.getCJSApprovalNo() + "\n");
			sb.append("CJSNoInt -> " + result.getCJSNoInt() + "\n");
			sb.append("CJSHalbu -> " + result.getCJSHalbu() + "\n");

		} else if ("VirtualAccount".equals(result.getCJSPayMethod())) { // 가상계좌
			
			sb.append("CJSBankName -> " + result.getCJSBankName() + "\n");
			sb.append("CJSAccountOWNER -> " + result.getCJSAccountOWNER() + "\n");
			sb.append("CJSAccountNo -> " + result.getCJSAccountNo() + "\n");
			sb.append("CJSCashApprovalNo -> " + result.getCJSCashApprovalNo() + "\n");
			
		} else if ("Account".equals(result.getCJSPayMethod())) { // 계좌이체
			
			sb.append("CJSDepositDate -> " + result.getCJSDepositDate() + "\n");
			sb.append("CJSBankName -> " + result.getCJSBankName() + "\n");
			sb.append("CJSBankCode -> " + result.getCJSBankCode() + "\n");
			sb.append("CJSCashApprovalNo -> " + result.getCJSCashApprovalNo() + "\n");
			
		}
		
		return sb.toString();
	}
	
	@Override
	public boolean cancel(OrderPgData orderPgData) {
		
		HttpPost httpPost = new HttpPost(environment.getProperty("pg.cj.card.cancel.url"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        	
        	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("CJSShopID", environment.getProperty("pg.cj.mid")));
			nvps.add(new BasicNameValuePair("CJSPayID", orderPgData.getPgAuthCode()));
            nvps.add(new BasicNameValuePair("CJSTradeID", orderPgData.getPgKey()));
            nvps.add(new BasicNameValuePair("CJSCancelOne", "System"));
            nvps.add(new BasicNameValuePair("CJSCancelDesc", "취소"));
            nvps.add(new BasicNameValuePair("ResponseFormat", "Json"));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return false;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {
	            try {
	
	            	String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	//System.out.println(responseBody + "\n" + statusCode);	
	            	
	            	HashMap<String, Object> map = (HashMap<String, Object>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, Object>>(){});
	            	if (map.get("PayResultCode") != null) {
	            		String code = (String) map.get("PayResultCode");
	            		if ("0000".equals(code)) {
	            			return true;
	            		}
	            	}
	            	
	            } catch (RuntimeException ex) {
					log.error("ERROR: {}", ex.getMessage(), ex);
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("검색 결과 파싱 오류", ex);
	
	            }
	        }
	        
	        return false;
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new NotificationException("검색 결과 파싱 오류", e);
		}
	}

	@Override
	public String confirmationOfPayment(PgData pgData) {

		return null;
	}

	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {
		
		orderPgData.setSuccess(false);
		orderPgData.setErrorMessage("PG 데이터가 넘어오지 않았습니다.");
		HttpPost httpPost = new HttpPost(environment.getProperty("pg.cj.card.cancel.url"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        	
        	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("CJSShopID", environment.getProperty("pg.cj.mid")));
			nvps.add(new BasicNameValuePair("CJSPayID", orderPgData.getPgAuthCode()));
            nvps.add(new BasicNameValuePair("CJSTradeID", orderPgData.getPgKey()));
            nvps.add(new BasicNameValuePair("CJSCancelOne", "System"));
            nvps.add(new BasicNameValuePair("CJSCancelDesc", "취소"));
            nvps.add(new BasicNameValuePair("ResponseFormat", "Json"));
            
            // 잔여액과 환불 금액이 다르면.. 부분취소 하자
            if (orderPgData.getRemainAmount() != orderPgData.getCancelAmount()) {
            	nvps.add(new BasicNameValuePair("CancelAmount", Integer.toString(orderPgData.getCancelAmount())));
            }
            
            // 가상계좌일때 환불 계좌를 받는다..
            if ("vbank".equals(orderPgData.getApprovalType())) {
            	nvps.add(new BasicNameValuePair("AccNumber", orderPgData.getReturnAccountNo()));
                nvps.add(new BasicNameValuePair("bankcode", orderPgData.getReturnBankName()));
            }
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return orderPgData;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {

	            try {
	
	            	String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	//System.out.println(responseBody + "\n" + statusCode);	
	            	
	            	HashMap<String, Object> map = (HashMap<String, Object>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, Object>>(){});
	            	if (map.get("PayResultCode") != null) {
	            		String code = (String) map.get("PayResultCode");
	            		if ("0000".equals(code)) {
	            			orderPgData.setSuccess(true);
	            			return orderPgData;
	            		}
	            	}
	            	
	            	orderPgData.setErrorMessage((String) map.get("PayResultMessage"));
	            	
	            } catch (RuntimeException ex) {
					log.error("ERROR: {}", ex.getMessage(), ex);
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("검색 결과 파싱 오류", ex);
	
	            }
	        }
	        
	        return orderPgData;
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new NotificationException("검색 결과 파싱 오류", e);
		}
	}

	@Override
	public boolean delivery(HashMap<String, Object> paramMap) {

		return false;
	}
	@Override
	public boolean escrowConfirmPurchase(HttpServletRequest request) {

		return false;
	}
	@Override
	public boolean escrowDenyConfirm(List<String> param) {

		return false;
	}

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {
        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
        return null;
    }
}
