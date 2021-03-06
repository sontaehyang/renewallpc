package saleson.shop.order.pg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.notification.exception.NotificationException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.NumberUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.utils.PaycoUtils;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.payco.domain.*;
import saleson.shop.order.support.OrderException;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Service("paycoService")
public class PaycoServiceImpl implements PgService  {

	private static final Logger log = LoggerFactory.getLogger(PaycoServiceImpl.class);


	@Autowired
	Environment environment;

	@Override
	public String getPayType(String payType) {

		return null;
	}

	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		Payco payco = new Payco((Buy) data, environment);

		HttpPost httpPost = new HttpPost(environment.getProperty("payco.order.api.url") +"/outseller/order/reserve");
		httpPost.addHeader("content-type", "application/json");

		
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

        	StringEntity params = new StringEntity(JsonViewUtils.objectToJson(payco), "UTF-8");
        	httpPost.setEntity(params);
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return null;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {

	            try {
	
	            	String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	map = (HashMap<String, Object>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, Object>>(){});
	            	
	            } catch (IOException ex) {
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            } catch (RuntimeException ex) {
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            }
	        }
	        
	        return map;
		} catch (Exception e) {
        	log.error("PaycoServiceImpl.init(..) ??????", e);
			throw new NotificationException("?????? ?????? ?????? ??????", e);
		}
	}

	@Override
	public OrderPgData pay(Object data, HttpSession session) {
		ReservationResponse paycoResponse = (ReservationResponse) data;
		
		HttpPost httpPost = new HttpPost(environment.getProperty("payco.order.api.url") +"/outseller/payment/approval");
		httpPost.addHeader("content-type", "application/json");

		
        OrderPgData orderPgData = new OrderPgData();
        orderPgData.setSuccess(false);
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        	
        	StringEntity params = new StringEntity(paycoResponse.getPayApprovalJsonData(), "UTF-8");
        	httpPost.setEntity(params);
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return null;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {

	            try {

					String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

					PayApprovalResponse payApprovalResponse = (PayApprovalResponse) JsonViewUtils
							.jsonToObject(responseBody, new TypeReference<PayApprovalResponse>() {
							});

					String paymentType = "";
					if (payApprovalResponse.getResult().getPaymentDetails().size() < 1) {
						throw new RuntimeException("???????????? ??????");
					} else {
						for (PaymentDetail paymentDetail : payApprovalResponse.getResult().getPaymentDetails()) {
							String paymentMethodCode = paymentDetail.getPaymentMethodCode();
							if (paymentMethodCode.equals("31")) {
								paymentType += "cardEasy";
							} else if (paymentMethodCode.equals("35")) {
								paymentType += "bankEasy";
							} else if (paymentMethodCode.equals("60")) {
								paymentType += "phoneEasy";
							} else if (paymentMethodCode.equals("01")) {
								paymentType += "card";
							} else if (paymentMethodCode.equals("98")) {
								paymentType += "paycoPoint";
							} else if (paymentMethodCode.equals("75")) {
								paymentType += "paycoCoupon";
							} else if (paymentMethodCode.equals("04")) {
								paymentType += "????????????";
							} else if (paymentMethodCode.equals("02")) {
								paymentType += "bank";
								orderPgData.setBankVirtualNo(paymentDetail.getNonBankbookSettleInfo().getAccountNo());
								orderPgData.setBankDate(
										paymentDetail.getNonBankbookSettleInfo().getPaymentExpirationYmd());
								System.out.println("bank:: " + paymentDetail.getPaymentAmt());
							} else if (paymentMethodCode.equals("05")) {
								paymentType += "phone";
								System.out.println("phone:: " + paymentDetail.getPaymentAmt());
							} else {
								paymentType += "payco";
								System.out.println(
										"method:: " + PaycoUtils.getPaymentMethod(paymentDetail.getPaymentMethodCode())
												+ ", price:: " + paymentDetail.getPaymentAmt());
								throw new RuntimeException("???????????? ??????");
							}
						}
					}
	            	
	            	if (payApprovalResponse.getCode() == 0) {
	            		orderPgData.setSuccess(true);
	            		PayApprovalResult result = payApprovalResponse.getResult();
	            		
	            		// orderCertifyKey??? ?????????????????? ?????? TID?????? ????????????.. ?????? ????????? ?????? ???????????? ????????? ????????? ??????... ???????
	            		orderPgData.setPgKey(result.getOrderNo());
	            		orderPgData.setPgAuthCode(Integer.toString(payApprovalResponse.getCode()));
	            		//orderPgData.setPgProcInfo(responseBody);
	                    orderPgData.setPgProcInfo(result.toString());
	                    orderPgData.setPartCancelDetail(result.getOrderCertifyKey());
	                    orderPgData.setPgServiceMid(environment.getProperty("payco.seller.cpId"));
	                    orderPgData.setPgServiceKey(environment.getProperty("payco.seller.key"));
	                    orderPgData.setPgPaymentType(paymentType);
	                    orderPgData.setPgAmount(Integer.parseInt(paycoResponse.getTotalPaymentAmt()));
	                    
	            		// ???????????? ??????? - ????????? ?????? ??????????????? ???????????? ????????? ????????? ??????
	            		orderPgData.setPartCancelFlag("Y");
	            		for(PaymentDetail detail : result.getPaymentDetails()) {
	            			if (detail.getCardSettleInfo() != null) {
	            				if ("N".equals(detail.getCardSettleInfo().getPartCancelPossibleYn())) {
	            					orderPgData.setPartCancelFlag("N");
	            					break;
	            				}
	            			}
	            		}
	            		
	            		orderPgData.setPaymentCompletion("Y".equals(result.getPaymentCompletionYn()) ? true : false);
	            		
	            	} else {
	            		orderPgData.setErrorMessage(payApprovalResponse.getMessage());
	            	}
	            	
	            } catch (IOException ex) {
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            } catch (RuntimeException ex) {
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            }
	        }
		} catch (Exception e) {
        	log.error("PaycoServiceImpl.pay(..) ????????? ?????? ?????? ", e);
			throw new NotificationException("?????? ?????? ?????? ??????", e);
		}
        
        return orderPgData;
	}
	
	/**
	 * ?????? ???????????? ??????
	 * @param cancelRequest
	 * @return
	 */
	private HashMap<String, Object> checkAvailabilityForCancel(CancelRequest cancelRequest) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		HttpPost httpPost = new HttpPost(environment.getProperty("payco.order.api.url") +"/outseller/order/cancel/checkAvailability");
		httpPost.addHeader("content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        	StringEntity params = new StringEntity(JsonViewUtils.objectToJson(cancelRequest), "UTF-8");
        	httpPost.setEntity(params);
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return null;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {

	            try {
	
	            	String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	map = (HashMap<String, Object>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, Object>>(){});
	            	if (map.get("result") != null) {
	            		return (HashMap<String, Object>) map.get("result");
	            	}
	            	
	            } catch (IOException ex) {
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            } catch (RuntimeException ex) {
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            }
	        }
	        
	        return null;
		} catch (Exception e) {
			throw new NotificationException("?????? ?????? ?????? ??????", e);
		}
	}
	
	@Override
	public boolean cancel(OrderPgData orderPgData) {
		HashMap<String, Object> map = new HashMap<>();
		
		CancelRequest cancelRequest = new CancelRequest();
		cancelRequest.setSellerKey(environment.getProperty("payco.seller.key"));
		cancelRequest.setCancelTotalAmt(orderPgData.getRemainAmount());
		cancelRequest.setOrderNo(orderPgData.getPgKey());
		
		HashMap<String, Object> checkAvailabilityForCancel = this.checkAvailabilityForCancel(cancelRequest);
		if (checkAvailabilityForCancel == null) {
			throw new OrderException("PAYCO ???????????? ???????????? ????????????.");
		}

		String cancelPossibleYn = checkAvailabilityForCancel.get("cancelPossibleYn") == null ? "" : (String) checkAvailabilityForCancel.get("cancelPossibleYn");
		if ("N".equals(cancelPossibleYn)) {
			String pgCancelPossibleAmt = (String) checkAvailabilityForCancel.get("pgCancelPossibleAmt");
			throw new OrderException("???????????? ??????????????? ??????????????????. [" + NumberUtils.formatNumber(Integer.parseInt(pgCancelPossibleAmt), "#,###"));
		}
		
		HttpPost httpPost = new HttpPost(environment.getProperty("payco.order.api.url") +"/outseller/order/cancel/request");
		httpPost.addHeader("content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        	StringEntity params = new StringEntity(JsonViewUtils.objectToJson(cancelRequest), "UTF-8");
        	httpPost.setEntity(params);
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return false;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {

	            try {
	
	            	String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	map = (HashMap<String, Object>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, Object>>(){});
	            	if (map.get("code") != null) {
	            		int code = (Integer) map.get("code");
	            		if (code == 0) {
	            			return true;
	            		}
	            	}
	            	
	            } catch (IOException ex) {
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            } catch (RuntimeException ex) {
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            }
	        }
	        
	        return false;
		} catch (Exception e) {
			throw new NotificationException("?????? ?????? ?????? ??????", e);
		}
	}

	@Override
	public String confirmationOfPayment(PgData pgData) {

		return null;
	}

	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {

		orderPgData.setSuccess(false);

		CancelRequest cancelRequest = new CancelRequest();
		cancelRequest.setSellerKey(environment.getProperty("payco.seller.key"));
		cancelRequest.setCancelTotalAmt(orderPgData.getCancelAmount());
		cancelRequest.setOrderNo(orderPgData.getPgKey());
		cancelRequest.setOrderProducts(orderPgData.getPaycoCancelProducts());
		cancelRequest.setOrderCertifyKey(orderPgData.getPartCancelDetail());

		HashMap<String, Object> checkAvailabilityForCancel = this.checkAvailabilityForCancel(cancelRequest);

		if (checkAvailabilityForCancel == null) {
			orderPgData.setSuccess(false);

		} else {
			String cancelPossibleYn = checkAvailabilityForCancel.get("cancelPossibleYn") == null ? "" : (String) checkAvailabilityForCancel.get("cancelPossibleYn");
			if ("N".equals(cancelPossibleYn)) {
				orderPgData.setSuccess(false);
			}
		}
		
		HttpPost httpPost = new HttpPost(environment.getProperty("payco.order.api.url") +"/outseller/order/cancel/request");
		httpPost.addHeader("content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        	StringEntity params = new StringEntity(JsonViewUtils.objectToJson(cancelRequest), "UTF-8");
        	httpPost.setEntity(params);
        	
        	HttpResponse response = httpClient.execute(httpPost);
        	int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return orderPgData;
			}
        	
            HttpEntity entity = response.getEntity();
            
            
            if (entity != null) {

	            try {
	
	            	String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");	
	            	HashMap<String, Object> map = (HashMap<String, Object>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, Object>>(){});
	            	if (map.get("code") != null) {
	            		int code = (Integer) map.get("code");
	            		if (code == 0 || code == 1301) { //1301:????????? ??????????????????.
	            			orderPgData.setSuccess(true);
	            			return orderPgData;
	            		}
	            	}
	            	
	            } catch (IOException ex) {
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            } catch (RuntimeException ex) {
	            	httpPost.abort();
	            	
	            	// throw ex;
	            	throw new NotificationException("?????? ?????? ?????? ??????", ex);
	
	            }
	        }
	        
	        return orderPgData;
		} catch (Exception e) {
			throw new NotificationException("?????? ?????? ?????? ??????", e);
		}
	}

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {
        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
        return null;
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
}
