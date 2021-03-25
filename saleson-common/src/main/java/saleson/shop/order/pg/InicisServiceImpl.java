package saleson.shop.order.pg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inicis.inipay.INIpay50;
import com.inicis.inipay.escrow.INIescrow;
import com.inicis.std.util.SignatureUtil;
import com.onlinepowers.framework.notification.exception.NotificationException;
import com.onlinepowers.framework.util.CommonUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
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
import saleson.common.enumeration.CashbillStatusCode;
import saleson.common.enumeration.CashbillType;
import saleson.common.enumeration.TaxType;
import saleson.common.utils.ShopUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.support.OrderException;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("inicisService")
public class InicisServiceImpl implements PgService {
	private static final Logger log = LoggerFactory.getLogger(InicisServiceImpl.class);

	@Autowired
	Environment environment;

	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("goodname", ((PgData) data).getGoodname());
	    map.put("oid", ((PgData) data).getOrderCode());
	    map.put("buyername", ((PgData) data).getBuyername());
	    map.put("buyertel", ((PgData) data).getBuyertel());
	    map.put("buyeremail", ((PgData) data).getBuyeremail());
	    map.put("orderCode", ((PgData) data).getOrderCode());
	    map.put("mid", ((PgData) data).getMid());
	    map.put("price", ((PgData) data).getAmount());
	    map.put("taxfree", ((PgData) data).getTaxFreeAmount());
	    
	    Config shopConfig = ShopUtils.getConfig();
	    
		if (!"webStandard".equals(environment.getProperty("pg.inipay.web.type"))) {
			INIpay50 inipay = new INIpay50();
			
		    /***************************************
		     * 3. 암호화 대상/값 설정              *
		     ***************************************/
		    inipay.SetField("inipayhome", environment.getProperty("pg.inipay.home")); // 이니페이 홈디렉터리(상점수정 필요)
		    inipay.SetField("admin", ((PgData) data).getKeypass()); 							  // 키패스워드(상점아이디에 따라 변경)
	    	inipay.SetField("mid", ((PgData) data).getMid());                           //상점아이디
		    
		    //***********************************************************************************************************
		    //* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
		    //* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
		    //* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
		    //* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
		    //***********************************************************************************************************
		    inipay.SetField("type", "chkfake");							  // 고정 (절대 수정 불가)
		    
		    inipay.SetField("enctype","asym"); 			                  // 고정 (절대 수정 불가) asym:비대칭, symm:대칭
		    inipay.SetField("checkopt", "false"); 		                  // 고정 (절대 수정 불가) base64함:false, base64안함:true
		    inipay.SetField("debug","true");                              // 로그모드("true"로 설정하면 상세로그가 생성됨.)
		    inipay.SetField("crypto", "execure");						  // Extrus 암호화모듈 사용(고정)
		    
		    //필수항목 : mid, price, nointerest, quotabase
		    //추가가능 : INIregno, oid
		    //*주의* : 	추가가능한 항목중 암호화 대상항목에 추가한 필드는 반드시 hidden 필드에선 제거하고 
		    //          SESSION이나 DB를 이용해 다음페이지(INIsecureresult.jsp)로 전달/셋팅되어야 합니다.
		    inipay.SetField("price", ((PgData) data).getAmount());                       // 가격
		    inipay.SetField("nointerest", "no");                            //무이자여부
		    inipay.SetField("quotabase", "선택:일시불:2개월:3개월:6개월");  //할부기간
		    String[] parameters = {"price","nointerest", "quotabase"};
		    inipay.SetField("parameters",parameters);
		    
		    /********************************
		     * 4. 암호화 대상/값을 암호화함 *
		     ********************************/

		    inipay.startAction();
	
		    /*********************
		     * 5. 암호화 결과    *
		     *********************/
	 		if ("00".equals(inipay.GetResult("ResultCode")))
			{
	 			throw new OrderException("결제 모듈 준비 과정중 에러가 발생하였습니다. \n선택하신 방법의 결제 진행이 불가능합니다.");
			}
	 		
		    /*********************
		     * 6. 세션정보 저장  *
		     *********************/
		    session.setAttribute("INI_MID"    , inipay.GetResult("mid"));
		    session.setAttribute("INI_PRICE"  , inipay.GetResult("price") );
		    session.setAttribute("INI_RN"     , inipay.GetResult("rn"));
		    session.setAttribute("INI_ENCTYPE", inipay.GetResult("enctype"));
		    session.setAttribute("admin"      , inipay.GetResult("admin"));
	
		    /*******************************************
		     * 7. 플러그인 전달 정보, hidden field 설정*
		     *******************************************/
		   
	
		    map.put("encfield", inipay.GetResult("encfield"));
		    map.put("ini_encfield", inipay.GetResult("encfield"));
		    map.put("certid", inipay.GetResult("certid"));
		    map.put("ini_certid", inipay.GetResult("certid"));
		    
		    map.put("amount", inipay.GetResult("price"));
		   
		    
		    
		} else {
		    String timestamp = SignatureUtil.getTimestamp();
		    
		    map.put("timestamp", timestamp);
		    //############################################
			// 1.전문 필드 값 설정(***가맹점 개발수정***)
			//############################################
		    String signKey			    = environment.getProperty("pg.inipay.sign.key");	// 가맹점에 제공된 웹 표준 사인키(가맹점 수정후 고정)
		    
		    
			//###############################################
			// 2.signature 생성	
			//###############################################
		    HashMap<String, String> signParam = new HashMap<>();
	
			signParam.put("oid",		((PgData) data).getOrderCode()); 						// 필수
			signParam.put("price", 		((PgData) data).getAmount());							// 필수			
			signParam.put("timestamp",	timestamp);												// 필수
	
			String returnUrl = environment.getProperty("saleson.url.shoppingmall") + "/order/pay?orderCode=" + ((PgData) data).getOrderCode();
			String closeUrl = environment.getProperty("saleson.url.shoppingmall") + "/order/ini-cancel";
			String nextUrl = "";
			if ("MOBILE".equals(((PgData) data).getDeviceType())) {
				returnUrl = environment.getProperty("saleson.url.shoppingmall") + "/m/order/pay?orderCode=" + ((PgData) data).getOrderCode();
				nextUrl = environment.getProperty("saleson.url.shoppingmall") + "/m/order/ini-next?orderCode=" + ((PgData) data).getOrderCode();
				closeUrl = environment.getProperty("saleson.url.shoppingmall") + "/m/order/ini-cancel";
			}
			
			if ("MOBILE".equals(((PgData) data).getDeviceType())) {
				map.put("gopaymethod", this.getPayType(((PgData) data).getApprovalType()));
				map.put("paymethod", this.getPayType(((PgData) data).getApprovalType()));
			} else {
				map.put("gopaymethod", this.getWebPayType(((PgData) data).getApprovalType()));
				map.put("paymethod", this.getWebPayType(((PgData) data).getApprovalType()));
			}
			
			map.put("returnUrl", returnUrl);
			map.put("closeUrl", closeUrl);
			
			// 가상계좌의 입금 만료일 설정
			if ("vbank".equals(((PgData) data).getApprovalType()) || "escrow".equals(((PgData) data).getApprovalType())) {
		    	
		    	int bankDepositDueDay = shopConfig.getBankDepositDueDay();
		    	if (bankDepositDueDay <= 0) {
		    		bankDepositDueDay = 7;
		    	}
		    	
		    	String bankDepositDueDate = DateUtils.addDay(DateUtils.getToday(Const.DATE_FORMAT), bankDepositDueDay);
		    	
		    	if ("MOBILE".equals(((PgData) data).getDeviceType())) {
		    		map.put("P_VBANK_DT", bankDepositDueDate);
		    	} else {
		    		map.put("acceptmethod", "no_receipt:vbank("+ bankDepositDueDate +")");
		    	}
		    }
			
			// signature 데이터 생성 (모듈에서 자동으로 signParam을 알파벳 순으로 정렬후 NVP 방식으로 나열해 hash)
			try {
				String mKey = SignatureUtil.hash(signKey, "SHA-256");
				String signature = SignatureUtil.makeSignature(signParam);
				map.put("signature",	signature);		// 필수
				map.put("mKey",			mKey);		// 필수
			} catch (Exception e) {
				
				log.error(" SignatureUtil.hash(...) :  {}", e.getMessage());
				throw new OrderException();
			}
		    
			if ("MOBILE".equals(((PgData) data).getDeviceType())) {
				
				/**
				 * P_NEXT_URL 결과
				 * P_STATUS : 인증상태 - 성공시 00, 그외 실패
				 * P_RMESG1 : 결과메시지
				 * P_TID : 인증거래번호 - Char(40) / 성공시에만 반환
				 * P_REQ_URL : 승인요청 Url
				 * P_NOTI : 기타주문정보
				 */
				
				/**
				 * P_NOTI_URL 용도는?
				 * 계좌이체 : 입금완료 송신
				 * 가상계좌 : 채번정보 송신, 입금완료 송신
				 */
				
				/**
				 * P_RETURN_URL 용도는?
				 * 계좌이체 : 사용안함
				 * 가상계좌 : 인증결과 송신
				 */
			    
			    map.put("P_MID", ((PgData) data).getMid());
			    map.put("P_GOODS", ((PgData) data).getGoodname());
			    map.put("P_AMT", ((PgData) data).getAmount());
			    map.put("P_TAXFREE", ((PgData) data).getTaxFreeAmount());
			    
			    map.put("P_MOBILE", ((PgData) data).getBuyertel());
			    map.put("P_UNAME", ((PgData) data).getBuyername());
			    map.put("P_EMAIL", ((PgData) data).getBuyeremail());
			    
			    map.put("P_OID", ((PgData) data).getOrderCode());
			    map.put("P_USERID", ((PgData) data).getUserID());
			    map.put("P_SESSIONID", ((PgData) data).getSessionkey());
			    
			    //map.put("P_CANCEL_URL", nextUrl);
			    
			    
			    map.put("P_MNAME", shopConfig.getShopName());
			    
			    if ("card".equals(((PgData) data).getApprovalType())) {
			    	
			    	map.put("P_NEXT_URL", nextUrl);
			    	map.put("P_RESERVED", "twotrs_isp=Y&block_isp=Y&twotrs_isp_noti=N&apprun_check=Y");
			    	
			    } else if ("vbank".equals(((PgData) data).getApprovalType()) || "escrow".equals(((PgData) data).getApprovalType())) {
			    	
			    	/**
			    	 * 가상계좌는 기본적으로 현금영수증 입력란이 없습니다. 이 옵션을 사용하면, 현금영수증 입력란이 Display 됩니다.
			    	 */
			    	map.put("P_RESERVED", "vbank_receipt="+environment.getProperty("pg.autoCashReceipt"));
			    	map.put("P_NOTI_URL", environment.getProperty("saleson.url.shoppingmall") + "/order/ini-mobile-noti-url?orderCode=" + ((PgData) data).getOrderCode() + "&P_USERID=" + ((PgData) data).getUserID() + "&P_SESSIONID=" + ((PgData) data).getSessionkey());
			    	map.put("P_NEXT_URL", nextUrl);
			    	map.put("P_NOTI", ((PgData) data).getSessionkey());
			    } else if ("realtimebank".equals(((PgData) data).getApprovalType())) {
			    	map.put("P_RESERVED", "bank_receipt="+environment.getProperty("pg.autoCashReceipt"));
			    	map.put("P_RETURN_URL", environment.getProperty("saleson.url.shoppingmall") + "/m/order/step3/99/" + ((PgData) data).getOrderCode());
			    	map.put("P_NOTI_URL", environment.getProperty("saleson.url.shoppingmall") + "/order/ini-mobile-noti-url?orderCode=" + ((PgData) data).getOrderCode() + "&P_USERID=" + ((PgData) data).getUserID() + "&P_SESSIONID=" + ((PgData) data).getSessionkey());
			    }
			}
		}
		
	    return map;
	}
	
	@Override
	public OrderPgData pay(Object data, HttpSession session) {
		OrderPgData orderPgData = new OrderPgData();
		
		if (ShopUtils.isMobilePage()) {
			((PgData) data).setMobilePage(true);
		}
			
		/**
		 * 모바일 이니시스 결제 요청후 승인 타입
		 * 신용카드 : wcard
		 * 휴대폰 : mobile
		 * 문화상품권 : culture
		 * 해피머니상품권 : hpmn
		 * 스마트문상 : dgcl
		 */
		if ("1".equals(((PgData) data).getTransactionType())) {
		
			boolean isSuccess = false;
			
			String P_STATUS = ((PgData) data).getP_STATUS();
			String P_REQ_URL = ((PgData) data).getP_REQ_URL();
			String P_TID = ((PgData) data).getP_TID();
			
			if (!((PgData) data).isMobilePage()) {
				P_STATUS = ((PgData) data).getResultCode();
				P_REQ_URL = ((PgData) data).getAuthUrl();
				P_TID = "";
			}
			
			if ("00".equals(P_STATUS) || "0000".equals(P_STATUS)) {
				
				// CJH 2016.12.23 모바일 실시간 계좌이체의 경우 그냥 넘어오는 데이터 저장하자...
				if ("realtimebank".equals(((PgData) data).getApprovalType()) && ((PgData) data).isMobilePage()) {
					
					HashMap<String, String> map = new HashMap<>();
					map.put("P_AUTH_DT", ((PgData) data).getP_AUTH_DT());
					map.put("P_AUTH_NO", ((PgData) data).getP_AUTH_NO());
					map.put("P_RMESG1", ((PgData) data).getP_RMESG1());
					map.put("P_TID", ((PgData) data).getP_TID());
					map.put("P_TYPE", ((PgData) data).getP_TYPE());
					map.put("P_UNAME", ((PgData) data).getP_UNAME());
					map.put("P_MID", ((PgData) data).getP_MID());
					
    				orderPgData.setPgKey(((PgData) data).getP_TID());
    				orderPgData.setPgAuthCode(((PgData) data).getP_STATUS());
    				orderPgData.setPgProcInfo(this.makeMobilePgLog(map, ((PgData) data).getApprovalType()));

    				orderPgData.setPartCancelFlag("Y");
					orderPgData.setPartCancelDetail("");
					orderPgData.setPgPaymentType(((PgData) data).getP_TYPE());
					orderPgData.setSuccess(true);
				} else {

					HttpPost httpPost = new HttpPost(P_REQ_URL);
					List <NameValuePair> nvps = new ArrayList <NameValuePair>();
					
					if (((PgData) data).isMobilePage()) {
			            nvps.add(new BasicNameValuePair("P_TID", P_TID));
			            nvps.add(new BasicNameValuePair("P_MID", ((PgData) data).getMid()));
					} else {
						
						String timestamp = SignatureUtil.getTimestamp();
						HashMap<String, String> signParam = new HashMap<>();
	
						signParam.put("authToken",	((PgData) data).getAuthToken());		// 필수
						signParam.put("timestamp",	timestamp);		// 필수
						
						String signature = "";
						try {
							signature = SignatureUtil.makeSignature(signParam);
						} catch (Exception e) {
							log.error("InicisServiceImpl : {}", e.getMessage());
						}
						
						nvps.add(new BasicNameValuePair("mid", ((PgData) data).getMid()));
						nvps.add(new BasicNameValuePair("authToken", ((PgData) data).getAuthToken()));
						nvps.add(new BasicNameValuePair("signature", signature));
						nvps.add(new BasicNameValuePair("timestamp", timestamp));
						nvps.add(new BasicNameValuePair("charset", "UTF-8"));
						nvps.add(new BasicNameValuePair("format", "JSON"));
						nvps.add(new BasicNameValuePair("price", ((PgData) data).getAmount()));
						nvps.add(new BasicNameValuePair("taxfree", ((PgData) data).getTaxFreeAmount()));
					}
					
					try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
						httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

						HttpResponse response = httpClient.execute(httpPost);
						int statusCode = response.getStatusLine().getStatusCode();
						if (statusCode == 200) {
							HttpEntity entity = response.getEntity();

							if (entity != null) {

								try {

									String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");

									HashMap<String, String> map = new HashMap<>();

									if (((PgData) data).isMobilePage()) {
										String[] temp = StringUtils.delimitedListToStringArray(responseBody, "&");
										for (String s : temp) {
											String[] t = StringUtils.delimitedListToStringArray(s, "=");
											if (t.length == 2) {
												map.put(t[0], t[1]);
											}
										}
									} else {
										map = (HashMap<String, String>) JsonViewUtils.jsonToObject(responseBody, new TypeReference<HashMap<String, String>>() {
										});
									}

									if (((PgData) data).isMobilePage()) {
										if ("00".equals(map.get("P_STATUS"))) {
											isSuccess = true;

											orderPgData.setPgKey(map.get("P_TID"));
											orderPgData.setPgAuthCode(map.get("P_STATUS"));
											orderPgData.setPgProcInfo(this.makeMobilePgLog(map, ((PgData) data).getApprovalType()));

											String partCancelFlag = "N";
											if ("card".equals(((PgData) data).getApprovalType())) {
												partCancelFlag = "1".equals(map.get("CARD_PRTC_CODE")) ? "Y" : "N";
											} else if ("vbank".equals(((PgData) data).getApprovalType())) {
												partCancelFlag = "Y";
											} else if ("realtimebank".equals(((PgData) data).getApprovalType())) {
												partCancelFlag = "Y";
											}

											orderPgData.setPartCancelFlag(partCancelFlag);
											orderPgData.setPartCancelDetail("");
											orderPgData.setPgPaymentType(map.get("P_TYPE"));

											if ("vbank".equals(((PgData) data).getApprovalType()) || "escrow".equals(((PgData) data).getApprovalType())) {
												orderPgData.setBankVirtualNo(map.get("P_VACT_NUM"));
												orderPgData.setBankCode(map.get("P_VACT_BANK_CODE"));
												orderPgData.setBankInName(map.get("P_VACT_NAME"));
												orderPgData.setBankDate(map.get("P_VACT_DATE"));
											}

										} else {
											isSuccess = false;
											orderPgData.setErrorMessage("결제요청 실패 : " + map.get("P_RMESG1"));
										}
									} else {
										if ("0000".equals(map.get("resultCode"))) {
											isSuccess = true;

											orderPgData.setPgKey(map.get("tid"));
											orderPgData.setPgAuthCode(map.get("resultCode"));
											orderPgData.setPgProcInfo(this.makeWebPgLog(map, ((PgData) data).getApprovalType()));

											String partCancelFlag = "N";
											if ("card".equals(((PgData) data).getApprovalType())) {
												partCancelFlag = "1".equals(map.get("CARD_PRTC_CODE")) ? "Y" : "N";
											} else if ("vbank".equals(((PgData) data).getApprovalType())) {
												partCancelFlag = "Y";
											} else if ("realtimebank".equals(((PgData) data).getApprovalType())) {
												partCancelFlag = "Y";
											}

											orderPgData.setPartCancelFlag(partCancelFlag);
											orderPgData.setPartCancelDetail("");
											orderPgData.setPgPaymentType(map.get("payMethod"));

											if ("vbank".equals(((PgData) data).getApprovalType()) || "escrow".equals(((PgData) data).getApprovalType())) {
												orderPgData.setBankVirtualNo(map.get("VACT_Num"));
												orderPgData.setBankCode(map.get("VACT_BankCode"));
												orderPgData.setBankInName(map.get("VACT_InputName"));
												orderPgData.setBankDate(map.get("VACT_Date"));
											}

										}
									}

								} catch (IOException ex) {
									log.error("ERROR: {}", ex.getMessage(), ex);
									throw new NotificationException("검색 결과 파싱 오류", ex);

								} catch (RuntimeException ex) {
									log.error("ERROR: {}", ex.getMessage(), ex);
									httpPost.abort();

									// throw ex;
									throw new NotificationException("검색 결과 파싱 오류", ex);

								}

							}
						}
					} catch (UnsupportedEncodingException e) {
						log.error("InicisServiceImpl : {}", e.getMessage());
						throw new NotificationException("결제 요청 실패", e);

					} catch (Exception e) {
						log.error("InicisServiceImpl : {}", e.getMessage());
						throw new NotificationException("결제 요청 실패", e);
					}
		            
					orderPgData.setSuccess(isSuccess);
				}
			} else {
				orderPgData.setSuccess(false);
				orderPgData.setErrorMessage("결제요청 실패");
			}
		} else {
			
			INIpay50 inipay = new INIpay50();
			
			/*********************
			 * 3. 지불 정보 설정 *
			 *********************/
			
			inipay.SetField("inipayhome", environment.getProperty("pg.inipay.home"));  // 이니페이 홈디렉터리(상점수정 필요)
			inipay.SetField("type", "securepay");  // 고정 (절대 수정 불가)
			
			inipay.SetField("admin", ((PgData) data).getKeypass()); 							  // 키패스워드(상점아이디에 따라 변경)
			//***********************************************************************************************************
			//* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
			//* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
			//* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
			//* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
			//***********************************************************************************************************
			inipay.SetField("debug", "true");  // 로그모드("true"로 설정하면 상세로그가 생성됨.)
			inipay.SetField("crypto", "execure");	// Extrus 암호화모듈 사용(고정)
			
			inipay.SetField("uid", ((PgData) data).getUid());  // INIpay User ID (절대 수정 불가)
			inipay.SetField("oid", ((PgData) data).getOid());  // 상품명 
			inipay.SetField("goodname", ((PgData) data).getGoodname());  // 상품명 
			inipay.SetField("currency", ((PgData) data).getCurrency());  // 화폐단위
			
			inipay.SetField("mid", ((PgData) data).getMid());  // 상점아이디
			inipay.SetField("enctype", session.getAttribute("INI_ENCTYPE") );  //웹페이지 위변조용 암호화 정보
			inipay.SetField("rn", session.getAttribute("INI_RN") );  //웹페이지 위변조용 RN값
			inipay.SetField("price", session.getAttribute("INI_PRICE") );  //가격
			
			
			/**---------------------------------------------------------------------------------------
			 * price 등의 중요데이터는
			 * 브라우저상의 위변조여부를 반드시 확인하셔야 합니다.
			 *
			 * 결제 요청페이지에서 요청된 금액과
			 * 실제 결제가 이루어질 금액을 반드시 비교하여 처리하십시오.
			 *
			 * 설치 메뉴얼 2장의 결제 처리페이지 작성부분의 보안경고 부분을 확인하시기 바랍니다.
			 * 적용참조문서: 이니시스홈페이지->가맹점기술지원자료실->기타자료실 의
			 *              '결제 처리 페이지 상에 결제 금액 변조 유무에 대한 체크' 문서를 참조하시기 바랍니다.
			 * 예제)
			 * 원 상품 가격 변수를 OriginalPrice 하고  원 가격 정보를 리턴하는 함수를 Return_OrgPrice()라 가정하면
			 * 다음 같이 적용하여 원가격과 웹브라우저에서 Post되어 넘어온 가격을 비교 한다.
			 *
					String originalPrice = merchant.getOriginalPrice();
					String postPrice = inipay.GetResult("price"); 
					if ( originalPrice != postPrice )
					{
						//결제 진행을 중단하고  금액 변경 가능성에 대한 메시지 출력 처리
						//처리 종료 
					}
			  ---------------------------------------------------------------------------------------**/
			inipay.SetField("paymethod", ((PgData) data).getPaymethod());			          // 지불방법 (절대 수정 불가)
			inipay.SetField("encrypted", ((PgData) data).getEncrypted());			          // 암호문
			inipay.SetField("sessionkey",((PgData) data).getSessionkey());			        // 암호문
			inipay.SetField("buyername", ((PgData) data).getBuyername());			          // 구매자 명
			inipay.SetField("buyertel", ((PgData) data).getBuyertel());			            // 구매자 연락처(휴대폰 번호 또는 유선전화번호)
			inipay.SetField("buyeremail", ((PgData) data).getBuyeremail());			        // 구매자 이메일 주소
			inipay.SetField("url", environment.getProperty("saleson.url.shoppingmall")); 	                      // 실제 서비스되는 상점 SITE URL로 변경할것
			inipay.SetField("cardcode", ((PgData) data).getCardcode()); 	          		// 카드코드 리턴
			inipay.SetField("parentemail", ((PgData) data).getParentemail()); 			    // 보호자 이메일 주소(핸드폰 , 전화결제시에 14세 미만의 고객이 결제하면  부모 이메일로 결제 내용통보 의무, 다른결제 수단 사용시에 삭제 가능)
			
			/*-----------------------------------------------------------------*
			 * 수취인 정보 *                                                   *
			 *-----------------------------------------------------------------*
			 * 실물배송을 하는 상점의 경우에 사용되는 필드들이며               *
			 * 아래의 값들은 INIsecurestart.jsp 페이지에서 포스트 되도록        *
			 * 필드를 만들어 주도록 하십시요.                                  *
			 * 컨텐츠 제공업체의 경우 삭제하셔도 무방합니다.                   *
			 *-----------------------------------------------------------------*/
			inipay.SetField("recvname", ((PgData) data).getRecvname());	// 수취인 명
			inipay.SetField("recvtel", ((PgData) data).getRecvtel());		// 수취인 연락처
			inipay.SetField("recvaddr", ((PgData) data).getRecvaddr());	// 수취인 주소
			inipay.SetField("recvpostnum", ((PgData) data).getRecvpostnum());  // 수취인 우편번호
			inipay.SetField("recvmsg", ((PgData) data).getRecvmsg());		// 전달 메세지
			
			inipay.SetField("joincard", ((PgData) data).getJoincard());        // 제휴카드코드
			inipay.SetField("joinexpire", ((PgData) data).getJoinexpire());    // 제휴카드유효기간
			inipay.SetField("id_customer", ((PgData) data).getId_customer());  // 일반적인 경우 사용하지 않음, user_id
			
			
			/****************
			 * 4. 지불 요청 *
			 ****************/ 
			inipay.startAction();
				
			//Get PG Added Entity Sample
			if(inipay.GetResult("ResultCode").equals("00"))
			{
				orderPgData.setSuccess(true);
				orderPgData.setPgKey(inipay.GetResult("tid"));
				orderPgData.setPgAuthCode(inipay.GetResult("ResultCode"));
				orderPgData.setPgProcInfo(this.makePgLog(inipay, ((PgData) data).getApprovalType()));
				
				if ("card".equals(((PgData) data).getApprovalType())) {
					
					String partCancelFlag = "1".equals(inipay.GetResult("CARD_PRTC_CODE")) ? "Y" : "N";
					
					orderPgData.setPartCancelFlag(partCancelFlag);
					orderPgData.setPartCancelDetail("");
				} else if ("vbank".equals(((PgData) data).getApprovalType())) {
					orderPgData.setPartCancelFlag("Y");
					orderPgData.setPartCancelDetail("");
				} else if ("realtimebank".equals(((PgData) data).getApprovalType())) {
					orderPgData.setPartCancelFlag("Y");
					orderPgData.setPartCancelDetail("");
				}
				
				orderPgData.setErrorMessage(inipay.GetResult("ResultMsg"));
				
			} else {
				orderPgData.setSuccess(false);
				orderPgData.setErrorMessage(inipay.GetResult("ResultMsg"));
			}
			
			/*****************
			 * 5. 결제  결과 *
			 *****************/
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
			 *
			 * 결제 되는 금액 =>원상품가격과  결제결과금액과 비교하여 금액이 동일하지 않다면
			 * 결제 금액의 위변조가 의심됨으로 정상적인 처리가 되지않도록 처리 바랍니다. (해당 거래 취소 처리)
			 *
			 *  2. 일부 결제 수단에만 존재하지 않은 정보,
			 *     OCB Point/VBank 를 제외한 지불수단에 모두 존재.
			 * 	승인번호 : inipay.GetResult("ApplNum") 
			 *
			 *
			 *  3. 신용카드 결제 결과 데이터 (Card, VCard 공통)
			 * 	할부기간 : inipay.GetResult("CARD_Quota")
			 * 	무이자할부 여부 : inipay.GetResult("CARD_Interest") ("1"이면 무이자할부), 
			 *                    또는 inipay.GetResult("EventCode") (무이자/할인 행사적용 여부, 값에 대한 설명은 메뉴얼 참조)
			 * 	신용카드사 코드 : inipay.GetResult("CARD_Code") (매뉴얼 참조)
			 * 	카드발급사 코드 : inipay.GetResult("CARD_BankCode") (매뉴얼 참조)
			 * 	본인인증 수행여부 : inipay.GetResult("CARD_AuthType") ("00"이면 수행)
			 *  각종 이벤트 적용 여부 : inipay.GetResult("EventCode")
			 *
			 *
			 *      ** 달러결제 시 통화코드와  환률 정보 **
			 *	해당 통화코드 : inipay.GetResult("OrgCurrency")
			 *	환율 : inipay.GetResult("ExchangeRate")
			 *
			 *      아래는 "신용카드 및 OK CASH BAG 복합결제" 또는"신용카드 지불시에 OK CASH BAG적립"시에 추가되는 데이터
			 * 	OK Cashbag 적립 승인번호 : inipay.GetResult("OCB_SaveApplNum")
			 * 	OK Cashbag 사용 승인번호 : inipay.GetResult("OCB_PayApplNum")
			 * 	OK Cashbag 승인일시 : inipay.GetResult("OCB_ApplDate") (YYYYMMDDHHMMSS)
			 * 	OCB 카드번호 : inipay.GetResult("OCB_Num")
			 * 	OK Cashbag 복합결재시 신용카드 지불금액 : inipay.GetResult("CARD_ApplPrice")
			 * 	OK Cashbag 복합결재시 포인트 지불금액 : inipay.GetResult("OCB_PayPrice")
			 *
			 * 4. 실시간 계좌이체 결제 결과 데이터
			 *
			 * 	은행코드 : inipay.GetResult("ACCT_BankCode")
			 *	현금영수증 발행결과코드 : inipay.GetResult("CSHR_ResultCode")
			 *	현금영수증 발행구분코드 : inipay.GetResult("CSHR_Type")
			 *
			 * 5. OK CASH BAG 결제수단을 이용시에만  결제 결과 데이터
			 * 	OK Cashbag 적립 승인번호 : inipay.GetResult("OCB_SaveApplNum")
			 * 	OK Cashbag 사용 승인번호 : inipay.GetResult("OCB_PayApplNum")
			 * 	OK Cashbag 승인일시 : inipay.GetResult("OCB_ApplDate") (YYYYMMDDHHMMSS)
			 * 	OCB 카드번호 : inipay.GetResult("OCB_Num")
			 *
			 * 6. 무통장 입금 결제 결과 데이터
			 * 	가상계좌 채번에 사용된 주민번호 : inipay.GetResult("VACT_RegNum")
			 * 	가상계좌 번호 : inipay.GetResult("VACT_Num")
			 * 	입금할 은행 코드 : inipay.GetResult("VACT_BankCode")
			 * 	입금예정일 : inipay.GetResult("VACT_Date") (YYYYMMDD)
			 * 	송금자 명 : inipay.GetResult("VACT_InputName")
			 * 	예금주 명 : inipay.GetResult("VACT_Name")
			 *
			 * 7. 핸드폰, 전화 결제 결과 데이터( "실패 내역 자세히 보기"에서 필요 , 상점에서는 필요없는 정보임)
			 * 	전화결제 사업자 코드 : inipay.GetResult("HPP_GWCode")
			 *
			 * 8. 핸드폰 결제 결과 데이터
			 * 	휴대폰 번호 : inipay.GetResult("HPP_Num") (핸드폰 결제에 사용된 휴대폰번호)
			 *
			 * 9. 전화 결제 결과 데이터
			 * 	전화번호 : inipay.GetResult("ARSB_Num") (전화결제에  사용된 전화번호)
			 *
			 * 10. 문화 상품권 결제 결과 데이터
			 * 	컬쳐 랜드 ID : inipay.GetResult("CULT_UserID")
			 *
			 * 11. 현금영수증 발급 결과코드 (은행계좌이체시에만 리턴)
			 *    inipay.GetResult("CSHR_ResultCode")
			 *
			 * 12.틴캐시 잔액 데이터
			 *    inipay.GetResult("TEEN_Remains")
			 *  틴캐시 ID : inipay.GetResult("TEEN_UserID")
			 *
			 * 13.스마트문상 상품권
			 *	사용 카드 갯수 : inipay.GetResult("GAMG_Cnt")
			 *
			 * 14.도서문화 상품권
			 *	사용자 ID : inipay.GetResult("BCSH_UserID")
			 *
			 ****************************************************************************************************************/
			
			
			/*******************************************************************
			 * 7. DB연동 실패 시 강제취소                                      *
			 *                                                                 *
			 * 지불 결과를 DB 등에 저장하거나 기타 작업을 수행하다가 실패하는  *
			 * 경우, 아래의 코드를 참조하여 이미 지불된 거래를 취소하는 코드를 *
			 * 작성합니다.                                                     *
			 *******************************************************************/
			/*
			  boolean cancelFlag = false;
			  	// cancelFlag를 "ture"로 변경하는 condition 판단은 개별적으로
			  	// 수행하여 주십시오.
			
			  if(cancelFlag)
			  {
			    String tmp_TID = inipay.GetResult("tid");
			    inipay.SetField("type", "cancel");         // 고정
			    inipay.SetField("tid", tmp_TID);              // 고정
			    inipay.SetField("cancelmsg", "DB FAIL");   // 취소사유
			    inipay.startAction();
			  }
			  */
			
		}
		
		
		return orderPgData;
	}
	
	@Override
	public boolean cancel(OrderPgData orderPgData) {
		/***************************************
		 * 2. INIpay 클래스의 인스턴스 생성 *
		 ***************************************/
		INIpay50 inipay = new INIpay50();
		
		/*********************
		 * 3. 취소 정보 설정 *
		 *********************/
		inipay.SetField("inipayhome", environment.getProperty("pg.inipay.home"));  // 이니페이 홈디렉터리(상점수정 필요)
		inipay.SetField("type", "cancel");                            // 고정 (절대 수정 불가)
		inipay.SetField("debug", "true");                             // 로그모드("true"로 설정하면 상세로그가 생성됨.)
		inipay.SetField("mid", orderPgData.getPgServiceMid());                           //상점아이디
		inipay.SetField("admin", orderPgData.getPgServiceKey()); 							  // 키패스워드(상점아이디에 따라 변경)

		if ("vbank".equals(orderPgData.getPgPaymentType().toLowerCase()) && orderPgData.getReturnBankName() != null) {	//환불 은행정보가 없으면 입금대기중 상태로 보고 환불없이 바로 취소
			
			inipay.SetField("type", "refundvacct");                            // 고정 (절대 수정 불가)
			
			inipay.SetField("refundacctnum", orderPgData.getReturnAccountNo().replace("-", ""));   // 환불계좌번호(숫자만입력)
			inipay.SetField("refundbankcode", orderPgData.getReturnBankName());   // 환불계좌은행코드
			inipay.SetField("refundacctname", orderPgData.getReturnName());   // 환불계좌주명
			inipay.SetField("refundflgremit", "");   // 펌뱅킹 사용여부(1: 사용)
		} else {
			inipay.SetField("type", "cancel");                            // 고정 (절대 수정 불가)
		}
		
		inipay.SetField("cancelreason", "");   // 현금영수증 취소코드
	  
		//***********************************************************************************************************
		//* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      * 
		//* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
		//* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
		//* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
		//***********************************************************************************************************
		inipay.SetField("tid", orderPgData.getPgKey());         // 취소할 거래의 거래아이디
		inipay.SetField("cancelmsg", orderPgData.getCancelReason());   // 취소사유
		inipay.SetField("crypto", "execure");						    // Extrus 암호화모듈 사용(고정)

		/****************
		 * 4. 취소 요청 *
		 ****************/
		
		try {
			inipay.startAction();
		} catch(Exception e) {
			log.error(" inipay.startAction(); :  {}", e.getMessage());
			return false;
		}
		
		boolean isSuccess = false;
		if(inipay.GetResult("ResultCode").equals("00")) {
			
			isSuccess = true;
			
		}
		
		return isSuccess;
	}
	
	private String makeWebPgLog(HashMap<String, String> map, String approvalType) {
		StringBuffer sb = new StringBuffer();
		String[] keys = new String[]{
			"tid", "payMethod", "resultCode", "resultMsg", "TotPrice", "MOID", "applDate", "applTime", 
			"CARD_Quota", "EventCode", "CARD_Num", "CARD_Quota", "CARD_Interest", "point", "CARD_Code", "CARD_BankCode", "CARD_PRTC_CODE", "CARD_CheckFlag",
			"OCB_Num", "OCB_SaveApplNum", "OCB_PayPrice", 
			"GSPT_Num", "GSPT_Remains", "GSPT_ApplPrice",
			"UNPT_CardNum", "UPNT_UsablePoint", "UPNT_PayPrice"
		};
		
		if (approvalType.equals("vbank") || approvalType.equals("escrow")) {
			keys = new String[]{
				"tid", "payMethod", "resultCode", "resultMsg", "TotPrice", "MOID", "applDate", "applTime",
				"VACT_Num", "VACT_BankCode", "vactBankName", "VACT_Name", "VACT_InputName", "VACT_Date", "VACT_Time"
			};
		} else if (approvalType.equals("hp")) {
			keys = new String[]{
				"tid", "payMethod", "resultCode", "resultMsg", "TotPrice", "MOID", "applDate", "applTime",
				"HPP_Num", "HPP_Corp"
			};
		} else if (approvalType.equals("realtimebank")) {
			keys = new String[]{
					"tid", "payMethod", "resultCode", "resultMsg", "TotPrice", "MOID", "applDate", "applTime",
					"ACCT_BankCode", "ACCT_BankName"
				};
			}
		
		for(String key : keys) {
			try {
				String value = (String) map.get(key);
				sb.append(key + " -> " + value + "\n");
			} catch (Exception e) {
				log.error("InicisServiceImpl : {}", e.getMessage());
			}
		}
		
		return sb.toString();
	}
	
	private String makeMobilePgLog(HashMap<String, String> map, String approvalType) {
		StringBuffer sb = new StringBuffer();
		String[] keys = new String[]{
			"P_AUTH_DT", "P_AUTH_NO", "P_RMESG1", "P_TID", "P_TYPE", "P_UNAME", "P_MID", "P_CARD_NUM", "P_CARD_ISSUER_CODE", "P_CARD_PURCHASE_CODE", "P_CARD_PRTC_CODE", 
			"P_CARD_INTEREST", "P_CARD_CHECKFLAG", "P_CARD_ISSUER_NAME", "P_CARD_PURCHASE_NAME", "P_FN_NM", "P_MERCHANT_RESERVED", "P_CARD_APPLPRICE", "P_CARD_USEPOINT"
		};
		
		if (approvalType.equals("vbank") || approvalType.equals("escrow")) {
			keys = new String[]{
				"P_VACT_NUM", "P_VACT_DATE", "P_VACT_TIME", "P_VACT_DATE", "P_VACT_BANK_CODE", "P_AUTH_DT", "P_AUTH_NO", "P_RMESG1", "P_TID", "P_TYPE", "P_UNAME", "P_MID"
			};
		} else if (approvalType.equals("hp")) {
			keys = new String[]{
				"P_AUTH_DT", "P_AUTH_NO", "P_RMESG1", "P_TID", "P_TYPE", "P_UNAME", "P_MID",
				"P_HPP_CORP", "P_HPP_NUM"
			};
		} else if (approvalType.equals("realtimebank")) {
			keys = new String[]{
				"P_AUTH_DT", "P_AUTH_NO", "P_RMESG1", "P_TID", "P_TYPE", "P_UNAME", "P_MID"
			};
		}
		
		for(String key : keys) {
			try {
				String value = (String) map.get(key);
				sb.append(key + " -> " + value + "\n");
			} catch (Exception e) {
				log.error("InicisServiceImpl : {}", e.getMessage());
			}
		}
		
		return sb.toString();
	}
	
	private String makePgLog(INIpay50 iniPay, String approvalType) {
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
			"tid", "ResultCode", "ResultMsg", "PayMethod", "MOID", "TotPrice", "ApplDate", "ApplTime"
		};
		
		if (approvalType.equals("vbank") || approvalType.equals("escrow")) {
			keys = new String[]{
				"tid", "VACT_RegNum", "VACT_Num", "VACT_BankCode", "VACT_Date", "VACT_InputName", "VACT_Name", 
				"ResultCode", "ResultMsg", "PayMethod", "MOID", "TotPrice", "ApplDate", "ApplTime"
			};
		}
		
		for(String key : keys) {
			String value = iniPay.GetResult(key);
			sb.append(key + " -> " + value + "\n");
		}
		
		return sb.toString();
	}

	private String getWebPayType(String payType) {
		if ("card".equals(payType)) {
			return "Card";
		} else if ("vbank".equals(payType)) {
			return "VBank";
		} else if ("escrow".equals(payType)) {
			return "VBank";
		} else if ("realtimebank".equals(payType)) {
			return "DirectBank";
		} else if ("hp".equals(payType)) {
			return "HPP";
		}
		
		return "";

	}
	
	@Override
	public String getPayType(String payType) {
		if ("card".equals(payType)) {
			return "wcard";
		} else if ("vbank".equals(payType)) {
			return "vbank";
		} else if ("escrow".equals(payType)) {
			return "vbank";
		} else if ("realtimebank".equals(payType)) {
			return "bank";
		} else if ("hp".equals(payType)) {
			return "mobile";
		}
		
		return "";
	}

	@Override
	public String confirmationOfPayment(PgData pgData) {

		return null;
	}
	
	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {
		/***************************************
		 * 2. INIpay 클래스의 인스턴스 생성 *
		 ***************************************/
		INIpay50 inipay = new INIpay50();
		
		/*********************
		 * 3. 취소 정보 설정 *
		 *********************/
		inipay.SetField("inipayhome", environment.getProperty("pg.inipay.home"));  // 이니페이 홈디렉터리(상점수정 필요)
		
		inipay.SetField("debug", "true");                             // 로그모드("true"로 설정하면 상세로그가 생성됨.)
		inipay.SetField("mid", orderPgData.getPgServiceMid());                           //상점아이디
		inipay.SetField("admin", orderPgData.getPgServiceKey()); 							  // 키패스워드(상점아이디에 따라 변경)
		
		if ("vbank".equals(orderPgData.getPgPaymentType().toLowerCase())) {
			
			inipay.SetField("type", "repayvacct");                            // 고정 (절대 수정 불가)
			
			inipay.SetField("refundacctnum", orderPgData.getReturnAccountNo().replace("-", ""));   // 환불계좌번호(숫자만입력)
			inipay.SetField("refundbankcode", orderPgData.getReturnBankName());   // 환불계좌은행코드
			inipay.SetField("refundacctname", orderPgData.getReturnName());   // 환불계좌주명
			inipay.SetField("refundflgremit", "");   // 펌뱅킹 사용여부(1: 사용)
		} else {
			inipay.SetField("type", "repay");                            // 고정 (절대 수정 불가)
		}
		
		inipay.SetField("cancelreason", "");   // 현금영수증 취소코드
	  
		//***********************************************************************************************************
		//* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
		//* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
		//* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
		//* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
		//***********************************************************************************************************
		
		inipay.SetField("oldtid", orderPgData.getPgKey());         // 취소할 거래의 거래아이디
		inipay.SetField("cancelmsg", orderPgData.getCancelReason());   // 취소사유
		inipay.SetField("price", Integer.toString(orderPgData.getCancelAmount()));	// 취소할 금액
		inipay.SetField("confirm_price", Integer.toString(orderPgData.getRemainAmount()));	// 부분취소 후 승인될 금액
		inipay.SetField("crypto", "execure");						    // Extrus 암호화모듈 사용(고정)

		/****************
		 * 4. 취소 요청 *
		 ****************/
		try {
			inipay.startAction();
		} catch(Exception e) {
			
			log.error(" inipay.startAction(); :  {}", e.getMessage());
			orderPgData.setSuccess(false);
			return orderPgData;
		}
		
		boolean isSuccess = false;
		if("00".equals(inipay.GetResult("ResultCode"))) {
			
			isSuccess = true;
			
			// CJH 2016.12.07 이니시스 부분취소시 TID를 새로 발급해주지만 취소 요청을 해당 거래번호로 할수가 없다... 무슨용도지?? 알수없음....
			// 이니시스는 원거래 TID말고도 입금,취소,배송등록,구매확인등등의 과정을 수행할때마다 각각의 TID를 생성하고 해당 TID는 수행한 액션에 대한 정보를 가지고 있음.
			// 부분취소시 발급되는 TID는 취소 TID로 해당 부분취소시 필요정보들을 가지고있음(관리자 사이트에서 확인가능). 부분취소후 나머지 부분에 대한 취소는 원거래 TID를 대상으로 해야함.
			// orderPgData.setPgKey(inipay.GetResult("TID"));
			orderPgData.setPgKey(orderPgData.getPgKey());
			
			if ("card".equals(orderPgData.getPgPaymentType().toLowerCase())) {
				
				// 부분취소때는 부분취소 가능여부가 안넘어오나??
				String partCancelFlag = "1".equals(inipay.GetResult("CARD_PRTC_CODE")) ? "Y" : "N";
				partCancelFlag = "Y";
				
				orderPgData.setPartCancelFlag(partCancelFlag);
			} else if ("vbank".equals(orderPgData.getPgPaymentType().toLowerCase())) {
				orderPgData.setPartCancelFlag("Y");
			} else if ("realtimebank".equals(orderPgData.getPgPaymentType().toLowerCase())) {
				orderPgData.setPartCancelFlag("Y");
			}
			
			orderPgData.setPgAmount(orderPgData.getRemainAmount());
			orderPgData.setPgProcInfo(this.makePgLog(inipay, orderPgData.getPgPaymentType()));
			orderPgData.setPartCancelDetail("PART_CANCEL");

		} else {
			orderPgData.setErrorMessage(inipay.GetResult("ResultMsg"));
		}
		
		orderPgData.setSuccess(isSuccess);
		return orderPgData;
	}
	
	@Override
	public CashbillResponse cashReceiptIssued(CashbillParam param) {
		
		/**************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 **************************************/
		INIpay50 inipay = new INIpay50();

		/*********************
		 * 2. 발급 정보 설정 *
		 *********************/
		inipay.SetField("inipayhome", environment.getProperty("pg.inipay.home"));  // 이니페이 홈디렉터리(상점수정 필요)
		inipay.SetField("mid", environment.getProperty("pg.inipay.mid"));          // 상점아이디
		inipay.SetField("admin", environment.getProperty("pg.inipay.keypass"));                             // 키패스워드(상점아이디에 따라 변경)
	  //***********************************************************************************************************
	  //* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
	  //* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
	  //* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
	  //* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
	  //***********************************************************************************************************
		inipay.SetField("type","receipt");                            // 고정
		inipay.SetField("paymethod","CASH");                          // 고정(요청분류)
		inipay.SetField("debug", "false");                             // 로그모드("true"로 설정하면 상세로그가 생성됨.)
		inipay.SetField("crypto", "execure");						              // Extrus 암호화모듈 사용(고정)

		inipay.SetField("currency", "WON");     // 화폐단위 (고정)
		inipay.SetField("goodname", param.getItemName());     // 상품명
		inipay.SetField("price", param.getAmount() + "");        // 총 현금결제 금액

        int tax = 0;

        if (TaxType.CHARGE == param.getTaxType()) {
            tax = (int)(param.getAmount() / 1.1 * 0.1);
        }

        int sup_price = (int)param.getAmount() - tax;
		
		inipay.SetField("sup_price", StringUtils.integer2string(sup_price));   // 공급가액
		inipay.SetField("tax", StringUtils.integer2string(tax));               // 부가세
		inipay.SetField("srvc_price", "0"); // 봉사료
		inipay.SetField("reg_num", param.getCashbillCode().replaceAll("-", ""));       // 현금결제자정보(주민번호,사업자번호,휴대폰번호등)

        String useopt = "0";

        if (CashbillType.BUSINESS == param.getCashbillType()) {
            useopt = "1";
        }

		inipay.SetField("useopt", useopt);         // 현금영수증 발행용도 ("0" - 소비자 소득공제용, "1" - 사업자 지출증빙용)

		inipay.SetField("buyername", param.getCustomerName());   // 구매자 성명
		inipay.SetField("buyeremail", param.getEmail()); // 구매자 이메일 주소
		inipay.SetField("buyertel", "0".equals(useopt) ? param.getCashbillCode().replaceAll("-", "") : "");     // 구매자 전화번호
		inipay.SetField("oid", param.getOrderCode());     // 상점 주문번호
		
		/****************
		 * 3. 지불 요청 *
		 ****************/
		inipay.startAction();

		/*****************************************************************
		 * 4. 발급 결과                           	                		 *
		 ****************************************************************/
		 
		String tid          = inipay.GetResult("tid");               // 거래번호
		String resultcode   = inipay.GetResult("ResultCode");        // 현금영수증 발행 결과코드 (4자리 - "0000"이면 발급성공)
		String resultMsg    = inipay.GetResult("ResultMsg");         // 결과내용 (발행결과에 대한 설명)
		String payMethod    = inipay.GetResult("paymethod");         // 지불방법 (매뉴얼 참조)
		String applnum      = inipay.GetResult("ApplNum");           // 현금영수증 발행 승인 번호
		String appldate     = inipay.GetResult("ApplDate");          // 이니시스 승인날짜 (YYYYMMDD)
		String appltime     = inipay.GetResult("ApplTime");          // 이니시스 승인시각 (HHMMSS)
		String cshr_price   = inipay.GetResult("CSHR_ApplPrice");    // 총현금결제 금액 ( or "TotPrice")
		String rsup_price   = inipay.GetResult("CSHR_SupplyPrice");  // 공급가
		String rtax         = inipay.GetResult("CSHR_Tax");          // 부가세
		String rsrvc_price  = inipay.GetResult("CSHR_ServicePrice"); // 봉사료
		String rshr_type    = inipay.GetResult("CSHR_Type");         // 현금영수증 사용구분

        CashbillResponse response = new CashbillResponse();

		if (resultcode.equals("00")) {
	        // 1)현금영수증 발급/취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
	    	response.setSuccess(true);
	    	response.setMgtKey(tid);
	    	response.setCashbillIssueDate(appldate+appltime);
	    } else {
            response.setSuccess(false);
            response.setResponseCode(resultcode);
            response.setResponseMessage(resultMsg);
	    }
		
		return response;
	}
	
	@Override
	public CashbillResponse cashReceiptCancel(CashbillParam param) {
		/***************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 ***************************************/
		INIpay50 inipay = new INIpay50();
		
		/*********************
		 * 2. 취소 정보 설정 *
		 *********************/
		inipay.SetField("inipayhome", environment.getProperty("pg.inipay.home"));  	// 이니페이 홈디렉터리(상점수정 필요)
		inipay.SetField("type", "cancel");                            					// 고정 (절대 수정 불가)
		inipay.SetField("debug", "true");                             					// 로그모드("true"로 설정하면 상세로그가 생성됨.)
		inipay.SetField("mid", environment.getProperty("pg.inipay.mid"));          	// 상점아이디
		inipay.SetField("admin", environment.getProperty("pg.inipay.keypass"));     // 키패스워드(상점아이디에 따라 변경)
		inipay.SetField("cancelreason", "1");  						 					// 현금영수증 취소코드(1: 거래취소, 2: 오류, 3: 기타사항)
		inipay.SetField("tid", param.getMgtKey());         		// 취소할 거래의 거래아이디
		inipay.SetField("cancelmsg", "주문취소");   										// 취소사유
		inipay.SetField("crypto", "execure");						    				// Extrus 암호화모듈 사용(고정)
		inipay.SetField("oid", param.getOrderCode());								// 상점 주문번호
		
		/****************
		 * 3. 취소 요청 *
		 ****************/
		inipay.startAction();
		
		/****************************************************************
		 * 4. 취소 결과
		 *
		 * 결과코드 : inipay.GetResult("ResultCode") ("00"이면 취소 성공)
		 * 결과내용 : inipay.GetResult("ResultMsg") (취소결과에 대한 설명)
		 * 취소날짜 : inipay.GetResult("CancelDate") (YYYYMMDD)
		 * 취소시각 : inipay.GetResult("CancelTime") (HHMMSS)
		 * 현금영수증 취소 승인번호 : inipay.GetResult("CSHR_CancelNum")
		 * (현금영수증 발급 취소시에만 리턴됨)
		 ****************************************************************/

		String resultCode = inipay.GetResult("ResultCode");
		String resultMsg = inipay.GetResult("ResultMsg");
		String cancelDate = inipay.GetResult("CancelDate");
		String cancelTime = inipay.GetResult("CancelTime");
		String cancelNum = inipay.GetResult("CSHR_CancelNum");

		CashbillResponse response = new CashbillResponse();

		/*
		 * 5. 현금영수증 발급/취소 요청 결과처리
		 */
		if (resultCode.equals("00")) 
		{
		    //1)현금영수증 발급/취소결과 화면처리(성공,실패 결과 처리를 하시기 바랍니다.)
			response.setSuccess(true);
			response.setCashbillIssueDate(cancelDate + cancelTime);
			response.setStatusCode(CashbillStatusCode.CANCEL);
		}
		else 
		{
		    response.setResponseCode(resultCode);
		    response.setResponseMessage(resultMsg);
            response.setSuccess(false);
		}
		
		return response;
	}

	@Override
	public boolean delivery(HashMap<String, Object> paramMap) {
		
		boolean isSuccess = false;
		
		/**************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 **************************************/
		INIescrow iniescrow = new INIescrow();
		
		/*********************
		 * 2. 지불 정보 설정 *
		 *********************/
		iniescrow.SetField("inipayhome",  environment.getProperty("pg.inipay.home"));						 // 이니페이 홈디렉터리(상점수정 필요)
		iniescrow.SetField("tid",paramMap.get("TID"));                    									     // 거래아이디
		iniescrow.SetField("mid",  environment.getProperty("pg.inipay.escrow.mid"));                         //상점아이디
		iniescrow.SetField("admin", environment.getProperty("pg.inipay.escrow.keypass")); 					 // 키패스워드(상점아이디에 따라 변경)
	  //***********************************************************************************************************
	  //* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
	  //* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
	  //* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
	  //* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
	  //***********************************************************************************************************
		iniescrow.SetField("type", "escrow"); 				                         // 고정 (절대 수정 불가)
		iniescrow.SetField("escrowtype", "dlv"); 				                     // 고정 (절대 수정 불가)
		iniescrow.SetField("debug","true");                                          // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
	    iniescrow.SetField("crypto", "execure");								     // Extrus 암호화모듈 사용(고정)
		
		iniescrow.SetField("oid", paramMap.get("ORDER_CODE").toString());						// [필수]상점 거래 주문번호
		iniescrow.SetField("dlv_report",paramMap.get("dlv_report").toString());					// [필수]에스크로 등록형태(등록:I , 변경:U)
		iniescrow.SetField("dlv_invoice",paramMap.get("DELIVERY_NUMBER").toString());			// [필수]운송장번호
		iniescrow.SetField("dlv_name",paramMap.get("SELLER_NAME").toString());					// [필수]배송등록자
		
		iniescrow.SetField("dlv_excode", getDeliveryCode(paramMap.get("DELIVERY_COMPANY_NAME").toString()));				// [필수]택배사코드
		iniescrow.SetField("dlv_exname",paramMap.get("DELIVERY_COMPANY_NAME").toString());									// [필수]택배사
		iniescrow.SetField("dlv_charge",paramMap.get("dlv_charge").toString());												// [필수]배송비 부담(SH:판매자부담, BH:구매자부담)
		
		iniescrow.SetField("dlv_invoiceday",DateUtils.getToday(Const.DATETIME_FORMAT));											// [필수]배송등록 확인일자
		iniescrow.SetField("dlv_sendname",paramMap.get("SELLER_NAME").toString());											// [필수]송신자 이름
		iniescrow.SetField("dlv_sendpost",paramMap.get("SEND_ZIPCODE").toString());											// [필수]송신자 우편번호
		iniescrow.SetField("dlv_sendaddr1",paramMap.get("SEND_ADDRESS").toString());										// [필수]송신자 주소1
		iniescrow.SetField("dlv_sendaddr2",paramMap.get("SEND_ADDRESS_DETAIL").toString());									// 송신자 주소2(선택사항)
		iniescrow.SetField("dlv_sendtel",paramMap.get("TELEPHONE_NUMBER").toString());										// [필수]송신자 전화번호

		iniescrow.SetField("dlv_recvname",paramMap.get("BUYER_NAME").toString());											// [필수]수신자 이름
		iniescrow.SetField("dlv_recvpost",paramMap.get("RECEIVE_ZIPCODE").toString());										// [필수]수신자 우편번호(구분자 - 없이 입력)
		iniescrow.SetField("dlv_recvaddr",paramMap.get("RECEIVE_ADDRESS")+" "+paramMap.get("RECEIVE_ADDRESS_DETAIL"));		// [필수]수신자 주소
		iniescrow.SetField("dlv_recvtel",paramMap.get("MOBILE").toString());												// [필수]수신자 전화번호
		
		iniescrow.SetField("dlv_goodscode",paramMap.get("ITEM_CODE").toString());				// 상품코드
		iniescrow.SetField("dlv_goods",paramMap.get("ITEM_NAME").toString());					// 상품명
		iniescrow.SetField("dlv_goodcnt",paramMap.get("ORDER_QUANTITY").toString());			// 상품수량
		iniescrow.SetField("price",paramMap.get("SALE_PRICE").toString());						// [필수]상품가격
		iniescrow.SetField("dlv_reserved1",paramMap.get("OPTIONS") == null ? "" : paramMap.get("OPTIONS").toString());					// 상품옵션1
		iniescrow.SetField("dlv_reserved2","");													// 상품옵션2
		iniescrow.SetField("dlv_reserved3","");													// 상품옵션3
		

		/*********************
		 * 3. 배송 등록 요청 *
		 *********************/
		iniescrow.startAction();
		
		
		/**********************
		 * 4. 배송 등록  결과 *
		 **********************/
		 
		 String tid        = iniescrow.GetResult("tid"); 		  // 거래번호
		 String resultCode = iniescrow.GetResult("ResultCode");	  // 결과코드 ("00"이면 성공)
		 String resultMsg  = iniescrow.GetResult("ResultMsg"); 	  // 결과내용 (결과에 대한 설명)
		 String dlv_date   = iniescrow.GetResult("DLV_Date");     // 등록날짜 (결과에 대한 설명)
		 String dlv_time   = iniescrow.GetResult("DLV_Time");     // 등록시각 (결과에 대한 설명)
		 
		 if(resultCode.equals("00"))
			 isSuccess = true;
		
		return isSuccess;
	}
	
	public String getDeliveryCode(String deliveryName){
		
		String code = "";
		
		switch (deliveryName) {
		case "CJ대한통운":
			code = "cjgls";
			break;
		case "한진택배":
			code = "hanjin";
			break;
		case "우체국택배":
			code = "EPOST";
			break;

		default:
			break;
		}
		
		return code;
	}
	
	@Override
	public boolean escrowConfirmPurchase(HttpServletRequest request) {

		boolean isSuccess = false;
		
		/**************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 **************************************/
		INIescrow iniescrow = new INIescrow();
		
		/*********************
		 * 2. 지불 정보 설정 *
		 *********************/
		iniescrow.SetField("inipayhome",  environment.getProperty("pg.inipay.home"));						 // 이니페이 홈디렉터리(상점수정 필요)
		iniescrow.SetField("tid", request.getParameter("tid"));                    								 // 거래아이디
		iniescrow.SetField("mid", request.getParameter("mid"));                        							 //상점아이디
		iniescrow.SetField("admin", environment.getProperty("pg.inipay.escrow.keypass")); 					 // 키패스워드(상점아이디에 따라 변경)
		
		//***********************************************************************************************************
		//* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
		//* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
		//* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
		//* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
		//***********************************************************************************************************
		iniescrow.SetField("type", "escrow"); 				                         // 고정 (절대 수정 불가)
		iniescrow.SetField("escrowtype", "confirm"); 				                 // 고정 (절대 수정 불가)
		iniescrow.SetField("uip", CommonUtils.getRemoteAddr(request));                           // 고정
		iniescrow.SetField("debug","true");                                          // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
	    iniescrow.SetField("crypto", "execure");								     // Extrus 암호화모듈 사용(고정)

		iniescrow.SetField("encrypted",request.getParameter("encrypted"));
		iniescrow.SetField("sessionkey",request.getParameter("sessionkey"));

		/*********************
		 * 3. 배송 등록 요청 *
		 *********************/
		iniescrow.startAction();
				
		/**********************
		 * 4. 배송 등록  결과 *
		 **********************/		
		String tid          = iniescrow.GetResult("tid"); 					// 거래번호
		String resultCode   = iniescrow.GetResult("ResultCode");		// 결과코드 ("00"이면 지불 성공)
		String resultMsg    = iniescrow.GetResult("ResultMsg");    // 결과내용 (결과에 대한 설명)
		String resultDate   = iniescrow.GetResult("CNF_Date");    // 처리 날짜
		String resultTime   = iniescrow.GetResult("CNF_Time");    // 처리 시각

		if(resultDate == null || resultDate.trim().length() <= 0 )
		{
		         resultDate   = iniescrow.GetResult("DNY_Date");    // 처리 날짜
		         resultTime   = iniescrow.GetResult("DNY_Time");    // 처리 시각
		}
		
		if(resultCode.equals("00"))
			isSuccess = true;
	
		return isSuccess;
	}
	
	@Override
	public boolean escrowDenyConfirm(List<String> param) {

		boolean isSuccess = false;
		
//		try {
//			request.setCharacterEncoding("euc-kr");
//		} catch (UnsupportedEncodingException e) {
//			log.error("ERROR: {}", e.getMessage(), e);
//		}
		/**************************************
		 * 1. INIpay 클래스의 인스턴스 생성 *
		 **************************************/
		INIescrow iniescrow = new INIescrow();
		
		/*********************
		 * 2. 지불 정보 설정 *
		 *********************/
		iniescrow.SetField("inipayhome", environment.getProperty("pg.inipay.home"));				// 이니페이 홈디렉터리(상점수정 필요)
		iniescrow.SetField("tid",param.get(0));                  // 거래아이디
		iniescrow.SetField("mid",param.get(1));                  // 상점아이디
		iniescrow.SetField("admin","1111");                                     // 키패스워드(상점아이디에 따라 변경)
	  //***********************************************************************************************************
	  //* admin 은 키패스워드 변수명입니다. 수정하시면 안됩니다. 1111의 부분만 수정해서 사용하시기 바랍니다.      *
	  //* 키패스워드는 상점관리자 페이지(https://iniweb.inicis.com)의 비밀번호가 아닙니다. 주의해 주시기 바랍니다.*
	  //* 키패스워드는 숫자 4자리로만 구성됩니다. 이 값은 키파일 발급시 결정됩니다.                               *
	  //* 키패스워드 값을 확인하시려면 상점측에 발급된 키파일 안의 readme.txt 파일을 참조해 주십시오.             *
	  //***********************************************************************************************************
		iniescrow.SetField("type", "escrow"); 				                    // 고정 (절대 수정 불가)
		iniescrow.SetField("escrowtype", "dcnf"); 				                // 고정 (절대 수정 불가)
		iniescrow.SetField("dcnf_name",param.get(2));      // 거절확인자
		iniescrow.SetField("debug","true");                                     // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
	    iniescrow.SetField("crypto","execure");								    // Extrus 암호화모듈 사용(고정)

		/*********************
		 * 3. 배송 등록 요청 *
		 *********************/
		iniescrow.startAction();
		
		
		/**********************
		 * 4. 배송 등록  결과 *
		 **********************/
		 
		 String tid          = iniescrow.GetResult("tid"); 			// 거래번호
		 String resultCode   = iniescrow.GetResult("ResultCode");	// 결과코드 ("00"이면 지불 성공)
		 String resultMsg    = iniescrow.GetResult("ResultMsg");    // 결과내용 (지불결과에 대한 설명)
		 String resultDate   = iniescrow.GetResult("DCNF_Date");    // 처리 날짜
		 String resultTime   = iniescrow.GetResult("DCNF_Time");    // 처리 시각
		 
		 if(resultCode.equals("00"))
			 isSuccess = true;
		
		return isSuccess;
	}
}

/*	택배사 코드
 
		택배사					:				택배사 코드
		동원로엑스택배			:				ajutb
		옐로우캡					:				yellow		                        
		로젠택배					:				kgb
		KG로지스					:				dongbu
		우체국택배				:				EPOST
		우편등기					:				registpost
		한진택배					:				hanjin
		현대택배					:				hyundai
		사가와익스프레스택배		:				Sagawa
		KGB택배					:				kgbls
		하나로택배				:				Hanaro
		세덱스택배				:				sedex
		네덱스택배				:				nedex
		이노지스택배				:				innogis
		CJ대한통운				:				cjgls
		기타택배					:				9999

*/