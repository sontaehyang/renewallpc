package saleson.shop.order.pg;

import com.kcp.J_PP_CLI_N;
import com.onlinepowers.framework.notification.exception.NotificationException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.CashbillIssue;
import saleson.shop.order.OrderMapper;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.kcp.domain.KcpRequest;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderManagerException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.receipt.ReceiptService;
import saleson.shop.receipt.support.CashbillIssueRepository;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

@Service("kcpService")
public class KcpServiceImpl implements PgService{
	private static final Logger log = LoggerFactory.getLogger(KcpServiceImpl.class);

	@Autowired
	Environment environment;

	@Autowired
	private OrderPaymentMapper orderPaymentMapper;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private CashbillIssueRepository cashbillIssueRepository;

    @Autowired
    private ReceiptService receiptService;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public String getPayType(String payType) {
		if ("card".equals(payType) || "CARD".equals(payType)) {
			if (ShopUtils.isMobilePage()) {
				return "100000000000";
			} else {
				return "CARD";
			}
		} else if ("realtimebank".equals(payType)) {
			return "010000000000";
		} else if ("vbank".equals(payType)) {
			return "001000000000";
		}
		
		return "";
	}
	
	@Override
	public HashMap<String, Object> init(Object data, HttpSession session) {
				
		HashMap<String, Object> payReqMap = new HashMap<>();
		
		KcpRequest kcpRequest = ((PgData) data).getKcpRequest();
		
		String redirectUrl = environment.getProperty("saleson.url.shoppingmall");
		if (ShopUtils.isMobilePage()) {
			redirectUrl +=  ShopUtils.getMobilePrefix();
		}		

		redirectUrl +=  "/order/kcp/callback-url";
		
		/** 주문정보 */		
		kcpRequest.setReq_tx("pay");	// 요청 구분				
		kcpRequest.setRet_url(redirectUrl);
		
		try {
			
			if(ShopUtils.isMobilePage()) {
        	
        		payReqMap.put("req_tx", kcpRequest.getReq_tx());
        		payReqMap.put("tran_cd", kcpRequest.getTran_cd());
        		payReqMap.put("ordr_idxx", kcpRequest.getOrdr_idxx());		            			            
        		payReqMap.put("good_mny", kcpRequest.getGood_mny());
        		payReqMap.put("good_name", kcpRequest.getGood_name());
        		payReqMap.put("buyr_name", kcpRequest.getBuyr_name());
	        	payReqMap.put("buyr_mail", kcpRequest.getBuyr_mail());
	        	payReqMap.put("buyr_tel1", kcpRequest.getBuyr_tel1());
	        	payReqMap.put("buyr_tel2", kcpRequest.getBuyr_tel2());
	        	payReqMap.put("cash_yn", kcpRequest.getCash_yn());    	        	
	        	payReqMap.put("cash_tr_code", kcpRequest.getCash_tr_code());
	        	payReqMap.put("pay_method", kcpRequest.getPay_method());
	        	payReqMap.put("Ret_URL", kcpRequest.getRet_url());
		            
			} else {
				payReqMap.put("req_tx", kcpRequest.getReq_tx());
	        	payReqMap.put("ret_url",kcpRequest.getRet_url());;   
	        	payReqMap.put("pay_method", getPayType(kcpRequest.getPay_method()));
	        	payReqMap.put("ordr_idxx", kcpRequest.getOrdr_idxx());			            	
	        	payReqMap.put("good_name", kcpRequest.getGood_name());
	        	payReqMap.put("good_mny", kcpRequest.getGood_mny());
	        	payReqMap.put("card_mony", kcpRequest.getGood_mny());
	        	payReqMap.put("buyr_name", kcpRequest.getBuyr_name());
	        	payReqMap.put("buyr_mail", kcpRequest.getBuyr_mail());
	        	payReqMap.put("buyr_tel1", kcpRequest.getBuyr_tel1());
	        	payReqMap.put("buyr_tel2", kcpRequest.getBuyr_tel2());        		   
	        	
	        	payReqMap.put("rcvr_name", kcpRequest.getBuyr_name());
	        	payReqMap.put("rcvr_tel1", kcpRequest.getBuyr_tel1());
	        	payReqMap.put("rcvr_tel2", kcpRequest.getBuyr_tel2());
	        	payReqMap.put("rcvr_mail", kcpRequest.getBuyr_mail());
			}

	        return payReqMap;
		} catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			throw new NotificationException("검색 결과 파싱 오류", e);
		}
	}

	@Override
	public OrderPgData pay(Object data, HttpSession session) {		
		OrderPgData orderPgData = new OrderPgData();
		orderPgData.setSuccess(false);
		
		String g_conf_home_dir = environment.getProperty("pg.kcp.g.conf.home.dir");
		String g_conf_gw_port =environment.getProperty("pg.kcp.g.conf.gw.port");
		String g_conf_key_dir = environment.getProperty("pg.kcp.g.conf.key.dir");
		String g_conf_log_dir = environment.getProperty("pg.kcp.g.conf.log.dir");
		int g_conf_tx_mode = Integer.parseInt(environment.getProperty("pg.kcp.g.conf.tx.mode"));
		String g_conf_site_cd = environment.getProperty("pg.kcp.g.conf.site.cd");
		String g_conf_site_key = environment.getProperty("pg.kcp.g.conf.site.key");
		String g_conf_log_level = environment.getProperty("pg.kcp.g.conf.log.level");
		String g_conf_gw_url = environment.getProperty("pg.kcp.g.conf.gw.url");
		
		KcpRequest kcpRequest = (KcpRequest)data;
		/* ============================================================================== */
	    /* =   01. 지불 요청 정보 설정                                                			= */
	    /* = -------------------------------------------------------------------------- = */
	    String    pay_method = StringUtils.null2void(getPayType(kcpRequest.getPay_method()));  	// 결제 방법
	    String    ordr_idxx  = StringUtils.null2void(kcpRequest.getOrdr_idxx());  				// 주문 번호
	    String    good_name  = StringUtils.null2void(kcpRequest.getGood_name());  				// 상품 정보
	    String    good_mny   = StringUtils.null2void(kcpRequest.getGood_mny());  				// 결제 금액
	    String    buyr_name  = StringUtils.null2void(kcpRequest.getBuyr_name()); 				// 주문자 이름
	    String    buyr_mail  = StringUtils.null2void(kcpRequest.getBuyr_mail());  				// 주문자 E-Mail
	    String    buyr_tel1  = StringUtils.null2void(kcpRequest.getBuyr_tel1());  				// 주문자 전화번호
	    String    buyr_tel2  = StringUtils.null2void(kcpRequest.getBuyr_tel2());  				// 주문자 휴대폰번호
	    String    req_tx     = StringUtils.null2void(kcpRequest.getReq_tx()); 	 				// 요청 종류
	    String    currency   = StringUtils.null2void(kcpRequest.getCurrency()); 				// 화폐단위
	    String    quotaopt   = StringUtils.null2void(kcpRequest.getQuotaopt());  				// 할부개월수 옵션
	    /* = -------------------------------------------------------------------------- = */
	    String    amount     = "";                                                  			// 총 금액
	    String 	  total_amount   = "0";                                            				// 복합결제시 총 거래금액
	    String 	  coupon_mny	  = "";                                            				// 쿠폰금액
	    /* = -------------------------------------------------------------------------- = */
	    String    tran_cd    = StringUtils.null2void(kcpRequest.getTran_cd());     				// 트랜잭션 코드
	    String    bSucc      = "";                                                  			// DB 작업 성공 여부
	    /* = -------------------------------------------------------------------------- = */
	    String    res_cd     = "";                                                  			// 결과코드
	    String    res_msg    = "";                                                 				// 결과메시지
	    String    tno        = StringUtils.null2void(kcpRequest.getTno());  					// KCP 거래 고유 번호
	    String 	  escw_yn    = "";                                                     			// 에스크로 사용여부
	    String 	  vcnt_yn    = StringUtils.null2void(kcpRequest.getVcnt_yn()); 					// 가상계좌 에스크로 사용 유무
	    /* = -------------------------------------------------------------------------- = */
	    String    card_pay_method = StringUtils.null2void(kcpRequest.getCard_pay_method());		// 카드 결제 방법
	    String    card_cd         = StringUtils.null2void(kcpRequest.getCard_cd());				// 카드 코드
	    String    card_name       = "";                                             			// 카드명
	    String    app_time        = "";                                             			// 승인시간
	    String    app_no          = "";                                             			// 승인번호
	    String    noinf           = "";                                             			// 무이자여부
	    String    quota           = StringUtils.null2void(kcpRequest.getQuota());				// 할부개월
	    String    partcanc_yn     = "";                                             			// 부분취소 가능유무   
	    String    card_mny        = "";                                             			// 카드결제금액
	    /* = -------------------------------------------------------------------------- = */
	    String bank_name      = "";                                                     		// 은행명
	    String bank_code      = "";                                                     		// 은행코드
	    String bk_mny         = "";                                                     		// 계좌이체결제금액
	    /* = -------------------------------------------------------------------------- = */
	    String bankname       = "";                                                     		// 입금 은행명
	    String depositor      = "";                                                     		// 입금 계좌 예금주 성명
	    String account        = "";                                                     		// 입금 계좌 번호
	    String va_date        = "";                                                     		// 가상계좌 입금마감시간
	    /* = -------------------------------------------------------------------------- = */
	    String pnt_issue      = "";                                                     		// 결제 포인트사 코드
	    String pnt_amount     = "";                                                     		// 적립금액 or 사용금액
	    String pnt_app_time   = "";                                                     		// 승인시간
	    String pnt_app_no     = "";                                                     		// 승인번호
	    String add_pnt        = "";                                                     		// 발생 포인트
	    String use_pnt        = "";                                                     		// 사용가능 포인트
	    String rsv_pnt        = "";                                                     		// 총 누적 포인트
	    /* = -------------------------------------------------------------------------- = */
	    String commid         = "";                                                     		// 통신사코드
	    String mobile_no      = "";                                                     		// 휴대폰번호
	    /* = -------------------------------------------------------------------------- = */
	    String shop_user_id	  = StringUtils.null2void(kcpRequest.getShop_user_id());			// 가맹점 고객 아이디
	    String tk_van_code	  = "";                                                     		// 발급사코드
	    String tk_app_no	  = "";                                                     		// 승인번호
	    /* = -------------------------------------------------------------------------- = */
	    String cash_yn        = StringUtils.null2void(kcpRequest.getCash_yn()); 				// 현금 영수증 등록 여부
	    String cash_authno    = "";                                                     		// 현금 영수증 승인 번호
	    String cash_tr_code   = StringUtils.null2void(kcpRequest.getCash_tr_code()); 			// 현금 영수증 발행 구분
	    String cash_id_info   = StringUtils.null2void(kcpRequest.getCash_id_info()); 			// 현금 영수증 등록 번호
	    String cash_no        = "";                                                     		// 현금 영수증 거래 번호    
	    /* = -------------------------------------------------------------------------- = */
		String    easy_pay_flg  = StringUtils.null2void(kcpRequest.getEasy_pay_flg());  		// K-Motion 사용여부
	    String    using_point   = StringUtils.null2void(kcpRequest.getUsing_point());			// K-Motion 포인트 금액
		/* = -------------------------------------------------------------------------- = */
	    String    mod_type       = StringUtils.null2void(kcpRequest.getMod_type()); 			// 변경TYPE(승인취소시 필요)
	    String    mod_desc       = StringUtils.null2void(kcpRequest.getMod_desc()); 			// 변경사유
	    String    panc_mod_mny   = "";                                                  		// 부분취소 금액
	    String    panc_rem_mny   = "";                                                  		// 부분취소 가능 금액
	    String    canc_time      = "";                                                  		// 취소시간
	    String    panc_card_mod_mny   = "";                                             		// 카드취소금액    
	    String    mod_tax_mny    = StringUtils.null2void(kcpRequest.getMod_tax_mny()); 			// 공급가 부분 취소 요청 금액
	    String    mod_vat_mny    = StringUtils.null2void(kcpRequest.getMod_vat_mny()); 			// 부과세 부분 취소 요청 금액
	    String    mod_free_mny   = StringUtils.null2void(kcpRequest.getMod_free_mny()); 		// 비과세 부분 취소 요청 금액
	    /* ============================================================================== */
	    /* =   01-1. 에스크로 지불 요청 정보 설정                                      			= */
	    /* = -------------------------------------------------------------------------- = */
	    String escw_used      = StringUtils.null2void(kcpRequest.getEscw_used());    // 에스크로 사용 여부
	    String pay_mod        = StringUtils.null2void(kcpRequest.getPay_mod());    	 // 에스크로 결제처리 모드
	    String deli_term      = StringUtils.null2void(kcpRequest.getDeli_term());    // 배송 소요일
	    String bask_cntx      = StringUtils.null2void(kcpRequest.getBask_cntx());    // 장바구니 상품 개수
	    String good_info      = StringUtils.null2void(kcpRequest.getGood_info());    // 장바구니 상품 상세 정보
	    String rcvr_name      = StringUtils.null2void(kcpRequest.getBuyr_name());    // 수취인 이름
	    String rcvr_tel1      = StringUtils.null2void(kcpRequest.getBuyr_tel1());    // 수취인 전화번호
	    String rcvr_tel2      = StringUtils.null2void(kcpRequest.getBuyr_tel2());    // 수취인 휴대폰번호
	    String rcvr_mail      = StringUtils.null2void(kcpRequest.getBuyr_mail());    // 수취인 E-Mail
	    String rcvr_zipx      = StringUtils.null2void(kcpRequest.getRcvr_zipx());    // 수취인 우편번호
	    String rcvr_add1      = StringUtils.null2void(kcpRequest.getRcvr_add1());    // 수취인 주소
	    String rcvr_add2      = StringUtils.null2void(kcpRequest.getRcvr_add2());    // 수취인 상세주소
	    /* = -------------------------------------------------------------------------- = */
	    /* =   01. 지불 요청 정보 설정 END                                              	= */
	    /* ============================================================================== */


	    /* ============================================================================== */
	    /* =   02. 인스턴스 생성 및 초기화                                              			= */
	    /* = -------------------------------------------------------------------------- = */
	    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

	    c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
	    c_PayPlus.mf_init_set();
	    /* ============================================================================== */
	    
	    // 업체 환경 정보
        String cust_ip = getServerIp();
	    
	    if(!ShopUtils.isMobilePage() && "CARD".equals(pay_method)) {	    	
	    	/* ============================================================================== */
		    /* =   03. 처리 요청 정보 설정, 실행                                            			= */
		    /* = -------------------------------------------------------------------------- = */

		    /* = -------------------------------------------------------------------------- = */
		    /* =   03-1. 승인 요청                                                          			= */
		    /* = -------------------------------------------------------------------------- = */		        

		        if ( req_tx.equals("pay")) {
		            tran_cd = "00100000";			

		            int payx_data_set;
		            int common_data_set;

		            payx_data_set   = c_PayPlus.mf_add_set( "payx_data" );
		            common_data_set = c_PayPlus.mf_add_set( "common"    );

		            c_PayPlus.mf_set_us( common_data_set, "amount",   good_mny    ); 
		            c_PayPlus.mf_set_us( common_data_set, "currency", currency    );

		            c_PayPlus.mf_set_us( common_data_set, "cust_ip",  cust_ip     );
		            c_PayPlus.mf_set_us( common_data_set, "escw_mod", "N"         );

		            c_PayPlus.mf_add_rs( payx_data_set, common_data_set );			


		            // 주문 정보
		            int ordr_data_set;

		            ordr_data_set = c_PayPlus.mf_add_set( "ordr_data" );

		            c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx );
		            c_PayPlus.mf_set_us( ordr_data_set, "good_name", good_name );
		            c_PayPlus.mf_set_us( ordr_data_set, "good_mny",  good_mny  ); 
		            c_PayPlus.mf_set_us( ordr_data_set, "buyr_name", buyr_name );
		            c_PayPlus.mf_set_us( ordr_data_set, "buyr_tel1", buyr_tel1 );
		            c_PayPlus.mf_set_us( ordr_data_set, "buyr_tel2", buyr_tel2 );
		            c_PayPlus.mf_set_us( ordr_data_set, "buyr_mail", buyr_mail );
		            
	                int card_data_set;

	                card_data_set = c_PayPlus.mf_add_set( "card" );
	                c_PayPlus.mf_set_us( card_data_set, "card_mny", good_mny );  

	                if (card_pay_method.equals("ISP")) {
	                    c_PayPlus.mf_set_us( card_data_set, "card_tx_type",  "11221000" );
	                    c_PayPlus.mf_set_us( card_data_set, "quota",          StringUtils.null2void(kcpRequest.getKvp_quota()));
	                    c_PayPlus.mf_set_us( card_data_set, "kcp_isppgid",    StringUtils.null2void(kcpRequest.getKvp_pgid()));
						
						if( easy_pay_flg.equals("K")) {
	                        c_PayPlus.mf_set_us( card_data_set, "kcp_sessionkey" , StringUtils.null2void(kcpRequest.getSession_key()));
	                        c_PayPlus.mf_set_us( card_data_set, "kcp_encdata"    , StringUtils.null2void(kcpRequest.getEnc_data()));
	                        c_PayPlus.mf_set_us( card_data_set, "kcp_cardcode"   , card_cd);
	                        c_PayPlus.mf_set_us( card_data_set, "easy_pay_flg"   , easy_pay_flg                          );
	                        c_PayPlus.mf_set_us( card_data_set, "card_use_pt_mny", using_point                           );
	                    } else {
							String sessionKey = null;
							try {
								sessionKey = URLEncoder.encode(StringUtils.null2void(kcpRequest.getSession_key()), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								log.error("ERROR: {}", e.getMessage(), e);
							}

							String encData = null;
							try {
								encData = URLEncoder.encode(StringUtils.null2void(kcpRequest.getEnc_data()), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								log.error("ERROR: {}", e.getMessage(), e);
							}

							c_PayPlus.mf_set_us( card_data_set, "kcp_cardcode",   card_cd.substring(2, 6));
	                        c_PayPlus.mf_set_us( card_data_set, "kcp_sessionkey", sessionKey);
	                        c_PayPlus.mf_set_us( card_data_set, "kcp_encdata",    encData);
	                    }
	                }else if (card_pay_method.equals("V3D")) {
	                    c_PayPlus.mf_set_us( card_data_set, "card_tx_type", "11321000" );
	                    c_PayPlus.mf_set_us( card_data_set, "quota",       StringUtils.null2void(kcpRequest.getQuota()));
	                    c_PayPlus.mf_set_us( card_data_set, "card_expiry",  StringUtils.null2void(kcpRequest.getCard_expiry()));
	                    c_PayPlus.mf_set_us( card_data_set, "cavv",         StringUtils.null2void(kcpRequest.getCavv()));
	                    c_PayPlus.mf_set_us( card_data_set, "xid",          StringUtils.null2void(kcpRequest.getXid()));
	                    c_PayPlus.mf_set_us( card_data_set, "eci",          StringUtils.null2void(kcpRequest.getEci()));
	                    //c_PayPlus.mf_set_us( card_data_set, "card_no",      request.getParameter( "card_no"  ) ); // 카드번호 ( 암호화 처리시 삭제 )
	                    //카드번호 암호화 처리시
	                    c_PayPlus.mf_set_us( card_data_set, "enc_cardno_yn",StringUtils.null2void(kcpRequest.getEnc_cardno_yn()));
	                    c_PayPlus.mf_set_us( card_data_set, "card_enc_no",  StringUtils.null2void(kcpRequest.getCard_enc_no()));
	                }
	                c_PayPlus.mf_add_rs( payx_data_set, card_data_set );
		            
		        }
		    
			/* ============================================================================== */
		    /* =   03-3. 실행                                                               			= */
		    /* ------------------------------------------------------------------------------ */
		        if ( tran_cd.length() > 0 ) {
		            c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, g_conf_log_level, "0" );
		        } else
		        {
		            c_PayPlus.m_res_cd  = "9562";
		            c_PayPlus.m_res_msg = "연동 오류";
		        }
		        res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
		        res_msg = c_PayPlus.m_res_msg;                     // 결과 메시지
		    /* ============================================================================== */
		    
		    /* ============================================================================== */
		    /* =   04. 승인 결과 처리 	                                                    = */
		    /* = -------------------------------------------------------------------------- = */
		      if (req_tx.equals("pay")) {
		        if (res_cd.equals("0000")) {
		        	tno       = c_PayPlus.mf_get_res("tno"      ); // KCP 거래 고유 번호
		            
		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-1. 신용카드 승인 결과 처리                                            			= */
		    /* = -------------------------------------------------------------------------- = */		        	
                    card_cd     = c_PayPlus.mf_get_res( "card_cd"   );
                    card_name   = c_PayPlus.mf_get_res( "card_name" );
                    app_time    = c_PayPlus.mf_get_res( "app_time"  );
                    app_no      = c_PayPlus.mf_get_res( "app_no"    );
                    noinf       = c_PayPlus.mf_get_res( "noinf"     );
                    quota       = c_PayPlus.mf_get_res( "quota"     );
                    partcanc_yn = c_PayPlus.mf_get_res( "partcanc_yn"); // 부분취소 가능유무
                    card_mny    = c_PayPlus.mf_get_res( "card_mny"   ); // 카드결제금액                    	                
		        }
		      }
		    /* = -------------------------------------------------------------------------- = */
		    /* =   04. 승인 결과 처리 END                                                   	= */
		    /* ============================================================================== */
		    
	    } else {
	    	/* ============================================================================== */
		    /* =   03. 처리 요청 정보 설정, 실행                                            			= */
		    /* = -------------------------------------------------------------------------- = */

		    /* = -------------------------------------------------------------------------- = */
		    /* =   03-1. 승인 요청                                                          			= */
		    /* = -------------------------------------------------------------------------- = */		        

	    	if ( req_tx.equals( "pay" ) )
	        {
	                c_PayPlus.mf_set_enc_data(StringUtils.null2void(kcpRequest.getEnc_data()),
	                						  StringUtils.null2void(kcpRequest.getEnc_info()));

	                /* 1원은 실제로 업체에서 결제하셔야 될 원 금액을 넣어주셔야 합니다. 결제금액 유효성 검증 */
	                if(good_mny.trim().length() > 0)
	                {
	                    int ordr_data_set_no;

	                    ordr_data_set_no = c_PayPlus.mf_add_set( "ordr_data" );

	                    c_PayPlus.mf_set_us( ordr_data_set_no, "ordr_mony", good_mny );
	                }	                
	        }
		    
			/* ============================================================================== */
		    /* =   03-3. 실행                                                               			= */
		    /* ------------------------------------------------------------------------------ */
		        if ( tran_cd.length() > 0 ) {
		            c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, g_conf_log_level, "0" );
		        } else
		        {
		        	c_PayPlus.m_res_cd  = "9562";
		            c_PayPlus.m_res_msg = "연동 오류|Payplus Plugin이 설치되지 않았거나 tran_cd값이 설정되지 않았습니다.";
		        }
		        res_cd  = c_PayPlus.m_res_cd;                      // 결과 코드
		        res_msg = c_PayPlus.m_res_msg;                     // 결과 메시지
		    /* ============================================================================== */
		    
		    /* ============================================================================== */
		    /* =   04. 승인 결과 처리 	                                                    = */
		    /* = -------------------------------------------------------------------------- = */
		        if ( req_tx.equals( "pay" ) )
		        {
		            if ( res_cd.equals( "0000" ) )
		            {
		                tno       = c_PayPlus.mf_get_res( "tno"       ); // KCP 거래 고유 번호
		                amount    = c_PayPlus.mf_get_res( "amount"    ); // KCP 실제 거래 금액
		                pnt_issue = c_PayPlus.mf_get_res( "pnt_issue" ); // 결제 포인트사 코드
		                coupon_mny = c_PayPlus.mf_get_res( "coupon_mny"	); // 쿠폰금액
		                
		    
            /* = -------------------------------------------------------------------------- = */
            /* =   05-1. 신용카드 승인 결과 처리                                            = */
            /* = -------------------------------------------------------------------------- = */
                    if (pay_method.equals("100000000000"))
                    {
                        card_cd     = c_PayPlus.mf_get_res( "card_cd"   ); // 카드사 코드
                        card_name   = c_PayPlus.mf_get_res( "card_name" ); // 카드사 명
                        app_no      = c_PayPlus.mf_get_res( "app_no"    ); // 승인번호
                        noinf       = c_PayPlus.mf_get_res( "noinf"     ); // 무이자 여부
                        quota       = c_PayPlus.mf_get_res( "quota"     ); // 할부 개월 수 
                        partcanc_yn = c_PayPlus.mf_get_res( "partcanc_yn" ); // 부분취소 가능유무
                    }            
		                
		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-2. 계좌이체 승인 결과 처리                                            			= */
		    /* = -------------------------------------------------------------------------- = */
		            if (pay_method.equals("010000000000")) {
		            	app_time  = c_PayPlus.mf_get_res( "app_time"  ); // 승인시간
		                bank_name = c_PayPlus.mf_get_res( "bank_name" ); // 은행명
		                bank_code = c_PayPlus.mf_get_res( "bank_code" ); // 은행코드
		                bk_mny    = c_PayPlus.mf_get_res( "bk_mny"    ); // 계좌이체결제금액
		            }

		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-3. 가상계좌 승인 결과 처리                                            			= */
		    /* = -------------------------------------------------------------------------- = */
		            if (pay_method.equals("001000000000"))
		            {
		                bankname  = c_PayPlus.mf_get_res( "bankname"  ); // 입금할 은행 이름
		                depositor = c_PayPlus.mf_get_res( "depositor" ); // 입금할 계좌 예금주
		                account   = c_PayPlus.mf_get_res( "account"   ); // 입금할 계좌 번호
		                va_date   = c_PayPlus.mf_get_res( "va_date"   ); // 가상계좌 입금마감시간
		            }

		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-4. 포인트 승인 결과 처리                                              			= */
		    /* = -------------------------------------------------------------------------- = */


		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-5. 휴대폰 승인 결과 처리                                              			= */
		    /* = -------------------------------------------------------------------------- = */


		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-6. 상품권 승인 결과 처리                                             			 = */
		    /* = -------------------------------------------------------------------------- = */


		    /* = -------------------------------------------------------------------------- = */
		    /* =   04-7. 현금영수증 승인 결과 처리                                          			= */
		    /* = -------------------------------------------------------------------------- = */
		            if(StringUtils.isNotEmpty(cash_yn) && cash_yn.equals("Y")) {
		            	if(pay_method.equals("010000000000") || pay_method.equals("001000000000")){
				            cash_authno = c_PayPlus.mf_get_res("cash_authno");	// 현금영수증 승인번호
				            cash_no     = c_PayPlus.mf_get_res("cash_no");		// 현금영수증 거래번호
		            	}
		            }
            /* = -------------------------------------------------------------------------- = */
            /* =   06-8. 에스크로 여부 결과 처리                                            = */
            /* = -------------------------------------------------------------------------- = */
		            escw_yn  = c_PayPlus.mf_get_res("escw_yn"); // 에스크로 여부
		        }
		      }
		    /* = -------------------------------------------------------------------------- = */
		    /* =   04. 승인 결과 처리 END                                                   	= */
		    /* ============================================================================== */
		    
	    }
	    
	      	      	   
	    /* = ========================================================================== = */
	    /* =   06. 승인 및 실패 결과 DB 처리                                            			= */
	    /* = -------------------------------------------------------------------------- = */
	    /* =      결과를 업체 자체적으로 DB 처리 작업하시는 부분입니다.                 		= */
	    /* = -------------------------------------------------------------------------- = */

	    if (req_tx.equals("pay")) {

	    /* = -------------------------------------------------------------------------- = */
	    /* =   06-1. 승인 결과 DB 처리(res_cd == "0000")                                	= */
	    /* = -------------------------------------------------------------------------- = */
	    /* =        각 결제수단을 구분하시어 DB 처리를 하시기 바랍니다.                 		= */
	    /* = -------------------------------------------------------------------------- = */
	        if (res_cd.equals("0000")) {
	            // 07-1-1. 신용카드
	        	if (pay_method.equals("CARD") || pay_method.equals("100000000000")) {
	        		
	            }
	        	
	        	// 07-1-2. 실시간 계좌이체
	        	if (pay_method.equals("010000000000")) {
	            	orderPgData.setBankCode(bank_code);	            	
	            	orderPgData.setBankDate(va_date);
	            	orderPgData.setBankInName(buyr_name);	            	
	            }
	        	
	        	// 07-1-3. 가상계좌
	        	if (pay_method.equals("001000000000")) {
	        		orderPgData.setBankName(bankname);
	        		orderPgData.setBankVirtualNo(account);					
					orderPgData.setBankInName(buyr_name);
					orderPgData.setBankDate(va_date);					
	            }
	        	
	        	// 07-1-4. 현금영수증
	        	if (!"".equals(cash_yn)){
	        		if(pay_method.equals("010000000000") || pay_method.equals("001000000000")){
	        			orderPgData.setCbtrno(cash_no);
	        		}
	        	}
	        }

	        /* = -------------------------------------------------------------------------- = */
	        /* =   06-2. 승인 실패 DB 처리(res_cd != "0000")                                	= */
	        /* = -------------------------------------------------------------------------- = */
	        if(!"0000".equals (res_cd)) {
	        	orderPgData.setSuccess(false);
	        }
	    }
	    /* = -------------------------------------------------------------------------- = */
	    /* =   06. 승인 및 실패 결과 DB 처리 END                                        	= */
	    /* = ========================================================================== = */

	    /* = ========================================================================== = */
	    /* =   07. 승인 결과 DB 처리 실패시 : 자동취소                                  		= */
	    /* = -------------------------------------------------------------------------- = */
	    /* =      승인 결과를 DB 작업 하는 과정에서 정상적으로 승인된 건에 대해        			= */
	    /* =      DB 작업을 실패하여 DB update 가 완료되지 않은 경우, 자동으로          		= */
	    /* =      승인 취소 요청을 하는 프로세스가 구성되어 있습니다.                   		= */
	    /* =                                                                            = */
	    /* =      DB 작업이 실패 한 경우, bSucc 라는 변수(String)의 값을 "false"        		= */
	    /* =      로 설정해 주시기 바랍니다. (DB 작업 성공의 경우에는 "false" 이외의    		= */
	    /* =      값을 설정하시면 됩니다.)                                              	= */
	    /* = -------------------------------------------------------------------------- = */

	    // 승인 결과 DB 처리 에러시 bSucc값을 false로 설정하여 거래건을 취소 요청
	    bSucc = ""; 

	    if (req_tx.equals("pay")) {
	        if (res_cd.equals("0000")) {
	        	if ( bSucc.equals("false")) {
                    int mod_data_set_no;

                    c_PayPlus.mf_init_set();

                    tran_cd = "00200000";
                    
                    /* ============================================================================== */
                    /* =   07-1.자동취소시 에스크로 거래인 경우                                     = */
                    /* = -------------------------------------------------------------------------- = */
                    String bSucc_mod_type; // 즉시 취소 시 사용하는 mod_type

                    if ( escw_yn.equals("Y") && pay_method.equals("001000000000") )
                    {
                        bSucc_mod_type = "STE5"; // 에스크로 가상계좌 건의 경우 가상계좌 발급취소(STE5)
                    }
                    else if ( escw_yn.equals("Y") )
                    {
                        bSucc_mod_type = "STE2"; // 에스크로 가상계좌 이외 건은 즉시취소(STE2)
                    }
                    else
                    {
                        bSucc_mod_type = "STSC"; // 에스크로 거래 건이 아닌 경우(일반건)(STSC)
                    }
                    /* = ---------------------------------------------------------------------------- = */
                    /* =   07-1. 자동취소시 에스크로 거래인 경우 처리 END                             = */
                    /* = ============================================================================== */

                    mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );

                    c_PayPlus.mf_set_us( mod_data_set_no, "tno",      tno    );                         // KCP 원거래 거래번호
                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_type", bSucc_mod_type );                 // 원거래 변경 요청 종류
                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",   cust_ip );                        // 변경 요청자 IP
                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc", "결제 오류 - 가맹점에서 자동 취소 요청" );   // 변경 사유
                    c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, tran_cd, cust_ip, ordr_idxx, g_conf_log_level, "0" );

                    res_cd  = c_PayPlus.m_res_cd;
                    res_msg = c_PayPlus.m_res_msg;
                } else {
	            	
                	orderPgData.setSuccess(true);
    				orderPgData.setPgKey(tno);
    				orderPgData.setPgAuthCode(res_cd);
    				
    				StringBuffer sb = new StringBuffer();
    				
    				String[] keys = new String[]{
    					"tno", "amount", "app_time", "pnt_issue", "card_cd", "card_name", "app_no", "noinf",
    					"quota", "partcanc_yn"
    				};
    				
    				for(String key : keys) {
    					String value = c_PayPlus.mf_get_res(key);
    					sb.append(key + " -> " + value + "\n");
    				}
    				
    				if (pay_method.equals("010000000000")) {
    					sb.append("bank_name -> " + bank_name + "\n");
    					sb.append("bank_code -> " + bank_code + "\n");
    					sb.append("cash_no -> " + cash_no + "\n");
    				} else if (pay_method.equals("001000000000")) {    					
    					sb.append("bank_name -> " + bankname + "\n");
    					sb.append("depositor -> " + depositor + "\n");
    					sb.append("va_date -> " + va_date + "\n");
    					sb.append("cash_no -> " + cash_no + "\n");
    					sb.append("account -> " + account + "\n");
    				}
    				
    				orderPgData.setPgProcInfo(sb.toString());
    				orderPgData.setPgPaymentType(pay_method);
    				orderPgData.setErrorMessage(res_msg);
    				orderPgData.setPartCancelFlag(StringUtils.isEmpty(partcanc_yn) ? "N" : partcanc_yn);
    				orderPgData.setPartCancelDetail("");	
	            }
	        }
	    }
	        // End of [res_cd = "0000"]
	    /* = -------------------------------------------------------------------------- = */
	    /* =   07. 승인 결과 DB 처리 END                                                	= */
	    /* = ========================================================================== = */

	    /* ============================================================================== */
	    /* =   08. 폼 구성 및 결과페이지 호출                                           			= */
	    /* -----------------------------------------------------------------------------= */
		
	    if (orderPgData.isSuccess() == false) {
	    	orderPgData.setErrorMessage(res_msg);
	    }
	    
		return orderPgData;
	}

	@Override
	public boolean cancel(OrderPgData orderPgData) {
		J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

		String g_conf_home_dir = environment.getProperty("pg.kcp.g.conf.home.dir");
		String g_conf_gw_port =environment.getProperty("pg.kcp.g.conf.gw.port");
		String g_conf_key_dir = environment.getProperty("pg.kcp.g.conf.key.dir");
		String g_conf_log_dir = environment.getProperty("pg.kcp.g.conf.log.dir");
		int g_conf_tx_mode = Integer.parseInt(environment.getProperty("pg.kcp.g.conf.tx.mode"));
		String g_conf_site_cd = environment.getProperty("pg.kcp.g.conf.site.cd");
		String g_conf_site_key = environment.getProperty("pg.kcp.g.conf.site.key");
		String g_conf_log_level = environment.getProperty("pg.kcp.g.conf.log.level");
		String g_conf_gw_url = environment.getProperty("pg.kcp.g.conf.gw.url");
		 
		c_PayPlus.mf_init("", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir);
	    c_PayPlus.mf_init_set();
		int mod_data_set_no;

        c_PayPlus.mf_init_set();

        String mod_type; // 결제 취소 시 사용하는 mod_type

        
    	// 실시간 계좌이체
    	if("010000000000".equals(orderPgData.getPgPaymentType())){
    		
    		switch (orderPgData.getEscrowStatus()) {
			case "10":	// 결제완료
				mod_type = "STE2";
				break;
			case "20":	// 배송등록완료
				mod_type = "STE4";
				break;
			case "30":	// 구매확인
				mod_type = "STE9_A";
				break;
			case "40":	// 구매거부확인
				mod_type = "STE4";
				break;
				
			default:
				mod_type = "STSC";
				break;
			}
    	}
    	// 가상계좌
    	else if("001000000000".equals(orderPgData.getPgPaymentType())){
    		
    		switch (orderPgData.getEscrowStatus()) {
			case "0": 	// 입금대기
				mod_type = "STE5";					
				break;
			case "10":	// 결제완료
				mod_type = "STE2";
				break;
			case "20":	// 배송등록완료
				mod_type = "STE4";
				break;
			case "30":	// 구매확인
				mod_type = "STE9_V";
				break;
			case "40":	// 구매거부확인
				mod_type = "STE4";
				break;
				
			default:
				mod_type = "STSC";
				break;
			}    		
    	}
    	// 그 외 결제수단
    	else
    		mod_type = "STSC";
    	
        mod_data_set_no = c_PayPlus.mf_add_set("mod_data");

        c_PayPlus.mf_set_us(mod_data_set_no, "tno",      orderPgData.getPgKey()     ); // KCP 원거래 거래번호
        c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", mod_type  ); // 원거래 변경 요청 종류 
        c_PayPlus.mf_set_us(mod_data_set_no, "mod_ip",   this.getServerIp() );	// 변경 요청자 IP
        c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", orderPgData.getCancelReason()); // 변경 사유

        if (!"N".equals(orderPgData.getEscrowStatus())) {
    		if ( mod_type.equals("STE2") || mod_type.equals("STE4") )                                       // 상태변경 타입이 [즉시취소] 또는 [취소]인 계좌이체, 가상계좌의 경우
            {
            	if("001000000000".equals(orderPgData.getPgPaymentType()))
                {
                    c_PayPlus.mf_set_us( mod_data_set_no, "refund_account", orderPgData.getReturnAccountNo());  // 환불수취계좌번호
                    c_PayPlus.mf_set_us( mod_data_set_no, "refund_nm",      orderPgData.getReturnName());  // 환불수취계좌주명
                    c_PayPlus.mf_set_us( mod_data_set_no, "bank_code",      "BK"+orderPgData.getReturnBankName());  // 환불수취은행코드
                }
            }
    	}
        
         c_PayPlus.mf_do_tx(g_conf_site_cd, g_conf_site_key, "00200000", "", orderPgData.getOrderCode(), g_conf_log_level, "0");
        
        String res_cd  = c_PayPlus.m_res_cd;                                 // 결과 코드
        String res_msg = c_PayPlus.m_res_msg;                                // 결과 메시지
        
        System.out.println("PG처리 결과 : " + res_msg);
        
        if ("0000".equals(res_cd)) {
        	return true;
        }
        
		return false;
	}

	@Override
	public String confirmationOfPayment(PgData pgData) {
		
		KcpRequest kcpRequest = pgData.getKcpRequest();
		
		/* ============================================================================== */
	    /* =   01. 공통 통보 페이지 설명(필독!!)                                        	= */
	    /* = -------------------------------------------------------------------------- = */
	    /* =   공통 통보 페이지에서는,                                                  	= */
	    /* =   가상계좌 입금 통보 데이터를 KCP를 통해 실시간으로 통보 받을 수 있습니다. 			= */
	    /* =                                                                            = */
	    /* =   common_return 페이지는 이러한 통보 데이터를 받기 위한 샘플 페이지        		= */
	    /* =   입니다. 현재의 페이지를 업체에 맞게 수정하신 후, 아래 사항을 참고하셔서  			= */
	    /* =   KCP 관리자 페이지에 등록해 주시기 바랍니다.                              		= */
	    /* =                                                                            = */
	    /* =   등록 방법은 다음과 같습니다.                                             	= */
	    /* =  - KCP 관리자페이지(admin.kcp.co.kr)에 로그인 합니다.                      	= */
	    /* =  - [쇼핑몰 관리] -> [정보변경] -> [공통 URL 정보] -> 공통 URL 변경 후에    		= */
	    /* =    가맹점 URL을 입력합니다.                                                	= */
	    /* ============================================================================== */


	    /* ============================================================================== */
	    /* =   02. 공통 통보 데이터 받기                                              			= */
	    /* = -------------------------------------------------------------------------- = */		
	    String site_cd      = StringUtils.null2void(kcpRequest.getSite_cd());  // 사이트 코드
	    String tno          = StringUtils.null2void(kcpRequest.getTno());  // KCP 거래번호
	    String order_no     = StringUtils.null2void(kcpRequest.getOrder_no());  // 주문번호
	    String tx_cd        = StringUtils.null2void(kcpRequest.getTx_cd());  // 업무처리 구분 코드
	    String tx_tm        = StringUtils.null2void(kcpRequest.getTx_tm());  // 업무처리 완료 시간
	    /* = -------------------------------------------------------------------------- = */
	    String ipgm_name    = "";                                                    // 주문자명
	    String remitter     = "";                                                    // 입금자명
	    String ipgm_mnyx    = "";                                                    // 입금 금액
	    String bank_code    = "";                                                    // 은행코드
	    String account      = "";                                                    // 가상계좌 입금계좌번호
	    String op_cd        = "";                                                    // 처리구분 코드
	    String noti_id      = "";                                                    // 통보 아이디
	    String cash_a_no    = "";                                                    // 현금영수증 승인번호
	    String cash_a_dt    = "";                                                    // 현금영수증 승인시간
		String cash_no      = "";                                                    // 현금영수증 거래번호
	    /* = -------------------------------------------------------------------------- = */
	    /* = -------------------------------------------------------------------------- = */
	    /* =   02-1. 가상계좌 입금 통보 데이터 받기                                     			= */
	    /* = -------------------------------------------------------------------------- = */
	    if ( tx_cd.equals("TX00") )
	    {
	        ipgm_name = StringUtils.null2void(kcpRequest.getIpgm_name());           // 주문자명
	        remitter  = StringUtils.null2void(kcpRequest.getRemitter());           // 입금자명
	        ipgm_mnyx = StringUtils.null2void(kcpRequest.getIpgm_mnyx());           // 입금 금액
	        bank_code = StringUtils.null2void(kcpRequest.getBank_code());           // 은행코드
	        account   = StringUtils.null2void(kcpRequest.getAccount());           // 가상계좌 입금계좌번호
	        op_cd     = StringUtils.null2void(kcpRequest.getOp_cd());           // 처리구분 코드
	        noti_id   = StringUtils.null2void(kcpRequest.getNoti_id());           // 통보 아이디
	        cash_a_no = StringUtils.null2void(kcpRequest.getCash_a_no());           // 현금영수증 승인번호
	        cash_a_dt = StringUtils.null2void(kcpRequest.getCash_a_dt());           // 현금영수증 승인시간
			cash_no   = StringUtils.null2void(kcpRequest.getCash_no());           // 현금영수증 거래번호
	    }

	    /* ============================================================================== */
	    /* =   03. 공통 통보 결과를 업체 자체적으로 DB 처리 작업하시는 부분입니다.    			= */
	    /* = -------------------------------------------------------------------------- = */
	    /* =   통보 결과를 DB 작업 하는 과정에서 정상적으로 통보된 건에 대해 DB 작업에  			= */
	    /* =   실패하여 DB update 가 완료되지 않은 경우, 결과를 재통보 받을 수 있는     		= */
	    /* =   프로세스가 구성되어 있습니다.                                            	= */
	    /* =                                                                            = */
	    /* =   * DB update가 정상적으로 완료된 경우                                     		= */
	    /* =   하단의 [04. result 값 세팅 하기] 에서 result 값의 value값을 0000으로     		= */
	    /* =   설정해 주시기 바랍니다.                                                  	= */
	    /* =                                                                            = */
	    /* =   * DB update가 실패한 경우                                                		= */
	    /* =   하단의 [04. result 값 세팅 하기] 에서 result 값의 value값을 0000이외의   		= */
	    /* =   값으로 설정해 주시기 바랍니다.                                           	= */
	    /* = -------------------------------------------------------------------------- = */

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-1. 가상계좌 입금 통보 데이터 DB 처리 작업 부분                        		= */
	    /* = -------------------------------------------------------------------------- = */
	    if ( tx_cd.equals("TX00") )
	    {
	    	OrderPayment orderPayment = orderPaymentMapper.getOrderPaymentByPgDataForKcpVacct(order_no);
			
			if (orderPayment == null) {
				return "9999";
			}
			
			if (orderPayment.getAmount() != Integer.parseInt(kcpRequest.getIpgm_mnyx()))
				return "9999";
						
			if(orderPayment.getOrderStatus().equals("0")){
				
				OrderParam orderParam = new OrderParam();
				orderParam.setOrderCode(orderPayment.getOrderCode());
				orderParam.setOrderSequence(orderPayment.getOrderSequence());
				orderParam.setPaymentSequence(orderPayment.getPaymentSequence());
				orderParam.setAdminUserName("system");
				orderParam.setPayAmount(orderPayment.getAmount());
				orderParam.setConditionType("OPMANAGER");

				if (orderPaymentMapper.updateConfirmationOfPaymentStep1(orderParam) > 0){
					if (orderPaymentMapper.updateConfirmationOfPaymentStep2(orderParam) > 0) {
						orderPaymentMapper.updateConfirmationOfPaymentStep3(orderParam);
						if(!orderPayment.getEscrowStatus().equals("N")){	//에스크로면 에스크로 상태 결제완료로 변경
							orderParam.setEscrowStatus("10");
							orderMapper.updateEscrowStatus(orderParam);
						}
					} else {
						throw new OrderManagerException();
					}								
				} else {
					throw new OrderManagerException(); 
				}
				
				try {
					
					Order order = orderService.getOrderByParam(orderParam);

                    // 현금영수증 발행
                    CashbillParam cashbillParam = new CashbillParam();

                    cashbillParam.setWhere("orderCode");
                    cashbillParam.setQuery(orderParam.getOrderCode());

                    Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

                    log.debug("[CASHBILL] START ---------------------------------------------");
                    log.debug("[CASHBILL] cashbillIssues Size :  {}", ((List<CashbillIssue>) cashbillIssues).size());

                    CashbillResponse response = null;

                    for (CashbillIssue cashbillIssue : cashbillIssues) {

                        if ("popbill".equals(environment.getProperty("cashbill.service"))) {
                            response = receiptService.receiptIssue(cashbillIssue);
                        }

                        if (response == null) {
                            log.debug("[CASHBILL] ERROR >> PG 통신오류(응답없음)");
                            throw new OrderException("PG 통신오류(응답없음)");
                        }

                        log.debug("[CASHBILL] cashbillIssue :  {}", cashbillIssue);
                        log.debug("[CASHBILL] CashbillResponse response.isSuccess() :  {}", response.isSuccess());
                        if (response.isSuccess()) {
                            cashbillIssue.setIssuedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                            cashbillIssue.setUpdatedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                            cashbillIssue.setCashbillStatus(CashbillStatus.ISSUED);
                            cashbillIssue.setMgtKey(response.getMgtKey());

                            if (UserUtils.isUserLogin() || UserUtils.isManagerLogin()) {
                                cashbillIssue.setUpdateBy(UserUtils.getUser().getUserName() + "(" + UserUtils.getLoginId() + ")");
                            } else {
                                cashbillIssue.setUpdateBy("비회원");
                            }

                            cashbillIssueRepository.save(cashbillIssue);

                        } else {
                            log.debug("[CASHBILL] ERROR >> {} : {}", response.getResponseCode(), response.getResponseMessage());
                            throw new OrderException(response.getResponseCode() + " : " + response.getResponseMessage());
                        }
                    }
                    log.debug("[CASHBILL] END ---------------------------------------------");
					
					orderService.sendOrderMessageTx(order, "order_cready_payment", ShopUtils.getConfig());

				} catch(Exception e) {
					log.error("ERROR: ORDER_CODE -> {}", orderPayment.getOrderCode(), e);
				}
			} else {	//입금대기상태가 아닌데 입금통보가 오는 경우 주문번호,날짜등을 로그로 기록.
				//Make Log
				String logLoot = environment.getProperty("pg.kcp.g.conf.home.dir");
				String logPath = logLoot+"/notiLog/"+DateUtils.getToday()+"/";
				String fileNm = orderPayment.getOrderCode()+".log";
				String content = "["+DateUtils.getToday("yyyy-MM-dd HH:mm:ss")+"] "+orderPayment.getOrderCode()+" - PG사로부터 입금통보 수신확인";
				  
				File dir = new File(logPath);
				//디렉토리가 없으면 생성
				if(!dir.isDirectory()){
				 dir.mkdirs();
				}
				  
				//파일에 내용 쓰기
				FileWriter fw = null;
				BufferedWriter out = null;
				try{
					fw = new FileWriter(new File(logPath+fileNm), true);
					fw.write(content);

					out = new BufferedWriter(fw);
					out.newLine();

					fw.flush();

				} catch (Exception e) {
					log.error("ERROR: ORDER_CODE({})", orderPayment.getOrderCode(), e);
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							log.error("BufferedWriter Close Error : {}", e.getMessage(), e);
						}
					}
					if (fw != null) {
						try {
							fw.close();
						} catch (IOException e) {
							log.error("FileWriter Close Error : {}", e.getMessage(), e);
						}
					}

				}
			}
			
			return "0000";
	    }

	    /* = -------------------------------------------------------------------------- = */
	    /* =   03-2. 모바일계좌이체 통보 데이터 DB 처리 작업 부분                     			= */
	    /* = -------------------------------------------------------------------------- = */
	    else if ( tx_cd.equals("TX08") )
	    {
	    	return "0000";
	    }
	    /* ============================================================================== */


	    /* ============================================================================== */
	    /* =   04. result 값 세팅 하기                                                  		= */
	    /* = -------------------------------------------------------------------------- = */
	    /* =   정상적으로 처리된 경우 value값을 0000으로 설정하여 주시기 바랍니다.      		= */
	    /* ============================================================================== */
		
		return "9999";
	}

	@Override
	public OrderPgData partCancel(OrderPgData orderPgData) {
		boolean isSuccess = false;
		
		J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

		String g_conf_home_dir = environment.getProperty("pg.kcp.g.conf.home.dir");
		String g_conf_gw_port =environment.getProperty("pg.kcp.g.conf.gw.port");
		String g_conf_key_dir = environment.getProperty("pg.kcp.g.conf.key.dir");
		String g_conf_log_dir = environment.getProperty("pg.kcp.g.conf.log.dir");
		int g_conf_tx_mode = Integer.parseInt(environment.getProperty("pg.kcp.g.conf.tx.mode"));
		String g_conf_site_cd = environment.getProperty("pg.kcp.g.conf.site.cd");
		String g_conf_site_key = environment.getProperty("pg.kcp.g.conf.site.key");
		String g_conf_log_level = environment.getProperty("pg.kcp.g.conf.log.level");
		String g_conf_gw_url = environment.getProperty("pg.kcp.g.conf.gw.url");
		 
		c_PayPlus.mf_init("", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir);
	    c_PayPlus.mf_init_set();
		int mod_data_set_no;

        c_PayPlus.mf_init_set();

        mod_data_set_no = c_PayPlus.mf_add_set("mod_data");
        
        String mod_type; // 결제 취소 시 사용하는 mod_type

        
    	// 실시간 계좌이체
    	if("010000000000".equals(orderPgData.getPgPaymentType())){
    		
    		switch (orderPgData.getEscrowStatus()) {
			case "10":	// 결제완료
				mod_type = "STE2";
				break;
			case "20":	// 배송등록완료
				mod_type = "STE4";
				break;
			case "30":	// 구매확인
				mod_type = "STE9_A";
				break;
			case "40":	// 구매거부확인
				mod_type = "STE4";
				break;
				
			default:
				mod_type = "STPC";
				break;
			}
    	}
    	// 가상계좌
    	else if("001000000000".equals(orderPgData.getPgPaymentType())){
    		
    		switch (orderPgData.getEscrowStatus()) {
			case "0": 	// 입금대기
				mod_type = "STE5";					
				break;
			case "10":	// 결제완료
				mod_type = "STE2";
				break;
			case "20":	// 배송등록완료
				mod_type = "STE4";
				break;
			case "30":	// 구매확인
				mod_type = "STE9_V";
				break;
			case "40":	// 구매거부확인
				mod_type = "STE4";
				break;
				
			default:
				mod_type = "STPC";
				break;
			}    		
    	}
    	// 그 외 결제수단
    	else
    		mod_type = "STPC";
    	
        mod_data_set_no = c_PayPlus.mf_add_set("mod_data");

        c_PayPlus.mf_set_us(mod_data_set_no, "tno",      orderPgData.getPgKey()     ); // KCP 원거래 거래번호
        c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", mod_type  ); // 원거래 변경 요청 종류 
        c_PayPlus.mf_set_us(mod_data_set_no, "mod_ip",   this.getServerIp() );	// 변경 요청자 IP
        c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", "결제 취소 - 가맹점에서 취소 요청"); // 변경 사유

		if ("STPC".equals(mod_type)) {
			c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", Integer.toString(orderPgData.getCancelAmount())); 								  // 취소요청금액
			c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", Integer.toString(orderPgData.getRemainAmount() + orderPgData.getCancelAmount())); // 취소가능잔액
		}

        if (!"N".equals(orderPgData.getEscrowStatus())) {
    		if ( mod_type.equals("STE2") || mod_type.equals("STE4") )                                       // 상태변경 타입이 [즉시취소] 또는 [취소]인 계좌이체, 가상계좌의 경우
            {
            	if("001000000000".equals(orderPgData.getPgPaymentType()))
                {
                    c_PayPlus.mf_set_us( mod_data_set_no, "refund_account", orderPgData.getReturnAccountNo());  // 환불수취계좌번호
                    c_PayPlus.mf_set_us( mod_data_set_no, "refund_nm",      orderPgData.getReturnName());  // 환불수취계좌주명
                    c_PayPlus.mf_set_us( mod_data_set_no, "bank_code",      "BK"+orderPgData.getReturnBankName());  // 환불수취은행코드
                }
            }
    	}
        
         c_PayPlus.mf_do_tx(g_conf_site_cd, g_conf_site_key, "00200000", "", orderPgData.getOrderCode(), g_conf_log_level, "0");
        
        String res_cd  = c_PayPlus.m_res_cd;                                 // 결과 코드
        String res_msg = c_PayPlus.m_res_msg;                                // 결과 메시지        
            
//        //STPC:부분취소, STSC:승인취소(전체취소)
//        String mod_type = "STPC";	
//        
//        c_PayPlus.mf_set_us(mod_data_set_no, "tno",      orderPgData.getPgKey()     ); // KCP 원거래 거래번호
//        c_PayPlus.mf_set_us(mod_data_set_no, "mod_type", mod_type  ); // 원거래 변경 요청 종류
//        
//        c_PayPlus.mf_set_us(mod_data_set_no, "mod_ip",   this.getServerIp() ); // 변경 요청자 IP
//        c_PayPlus.mf_set_us(mod_data_set_no, "mod_desc", "결제 취소 - 가맹점에서 부분 취소 요청" ); // 변경 사유
//        
//        if ("STPC".equals(mod_type)) {
//	        c_PayPlus.mf_set_us(mod_data_set_no, "mod_mny", Integer.toString(orderPgData.getCancelAmount())); 								  // 취소요청금액
//	        c_PayPlus.mf_set_us(mod_data_set_no, "rem_mny", Integer.toString(orderPgData.getRemainAmount() + orderPgData.getCancelAmount())); // 취소가능잔액	        
//        }
//        
//        c_PayPlus.mf_do_tx(g_conf_site_cd, g_conf_site_key, "00200000", "", orderPgData.getOrderCode(), g_conf_log_level, "0");
//
//        String res_cd  = c_PayPlus.m_res_cd;                                 // 결과 코드
//        String res_msg = c_PayPlus.m_res_msg;                                // 결과 메시지
//        

        if ("0000".equals(res_cd)) {
        	isSuccess =  true;
        	orderPgData.setPartCancelDetail("PART_CANCEL");
        } else {
        	orderPgData.setErrorMessage("("+res_cd+")"+res_msg);
        	System.out.println("테스트 : " + res_msg);
        }
        
        orderPgData.setSuccess(isSuccess);
        
		return orderPgData;
	}

	private String getServerIp() {
		InetAddress serverIp = null;
		
		try {
			serverIp = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
		if (serverIp == null) {
			return null;
		}
		return serverIp.getHostAddress();
	}
	@Override
	public boolean delivery(HashMap<String, Object> paramMap) {
		
		boolean isSuccess = false;

	    J_PP_CLI_N c_PayPlus = new J_PP_CLI_N();

	    String g_conf_gw_port =environment.getProperty("pg.kcp.g.conf.gw.port");
		String g_conf_log_dir = environment.getProperty("pg.kcp.g.conf.log.dir");
		int g_conf_tx_mode = Integer.parseInt(environment.getProperty("pg.kcp.g.conf.tx.mode"));
		String g_conf_site_cd = environment.getProperty("pg.kcp.g.conf.site.cd");
		String g_conf_log_level = environment.getProperty("pg.kcp.g.conf.log.level");
		String g_conf_gw_url = environment.getProperty("pg.kcp.g.conf.gw.url");
		String g_conf_site_key = environment.getProperty("pg.kcp.g.conf.site.key");
				
		c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
	    c_PayPlus.mf_init_set();
	    
        int mod_data_set_no;

        mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" );
        c_PayPlus.mf_set_us( mod_data_set_no, "tno",        paramMap.get("tno"	   ).toString());      // KCP 원거래 거래번호
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_ip",     this.getServerIp()				   );      // 변경 요청자 IP
        c_PayPlus.mf_set_us( mod_data_set_no, "mod_desc",   "가맹점 배송등록 요청"				   );      // 변경 사유

        c_PayPlus.mf_set_us( mod_data_set_no, "mod_type",   "STE1"                             );      // 원거래 변경 요청 종류            
        c_PayPlus.mf_set_us( mod_data_set_no, "deli_numb", paramMap.get("deli_numb").toString());      // 운송장 번호
        c_PayPlus.mf_set_us( mod_data_set_no, "deli_corp", paramMap.get("deli_corp").toString());      // 택배 업체명            	        
	    
        c_PayPlus.mf_do_tx( g_conf_site_cd, g_conf_site_key, "00200000", "", "", g_conf_log_level, "0" );

        String res_cd  = c_PayPlus.m_res_cd;  // 결과 코드
        String res_msg = c_PayPlus.m_res_msg; // 결과 메시지

        if(res_cd.equals("0000"))
        	isSuccess = true;
        
        
		return isSuccess;
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
//        /* ============================================================================== */
//        /* =   01. 요청 정보 설정                                                       = */
//        /* = -------------------------------------------------------------------------- = */
//        String res_cd       = "";                                                      // 결과코드
//        String res_msg      = "";                                                      // 결과메시지
//        String tx_cd        = "";                                                      // 트랜잭션 코드
//        String bSucc        = "";                                                      // DB 작업 성공 여부
//        /* = --------------------------------------------------------------------------= */
//        String pay_type     = "";                 // 결제 서비스 구분
//        String pay_trade_no = orderMapper.getTidByParam(cashReceipt.getOrderCode());   // 결제거래번호
//        /* = --------------------------------------------------------------------------= */
//        String req_tx       = "pay";  									               // 요청 종류(승인 : pay, 변경 : mod)
//        String trad_time    = DateUtils.getToday(Const.DATETIME_FORMAT);                    // 원거래 시각
//        /* = --------------------------------------------------------------------------= */
//        String ordr_idxx    = cashReceipt.getOrderCode() ;                 			   // 주문번호
//        String buyr_name    = cashReceipt.getCashReceiptName() ;                       // 주문자 이름
//        String buyr_tel1    = "";                 								       // 주문자 전화번호
//        String buyr_tel2    = "";                 								       // 주문자 전화번호
//        String buyr_mail    = cashReceipt.getCashReceiptEmail();                 	   // 주문자 메일
//        String good_name    = cashReceipt.getProductName();                 		   // 주문상품명
//        String comment      = "";                 									   // 비고
//        /* = --------------------------------------------------------------------------= */
//        String cash_no      = "" ;                                                     // 현금영수증 거래번호
//        String receipt_no   = "" ;                                                     // 현금영수증 승인번호
//        String app_time     = "" ;                                                     // 승인시간(YYYYMMDDhhmmss)
//        String reg_stat     = "" ;                                                     // 등록 상태 코드
//        String reg_desc     = "" ;                                                     // 등록 상태 설명
//        /* = --------------------------------------------------------------------------= */
//        String corp_type    = "0";                 									   // 사업장 구분 (0 : 직접판매, 1 : 입점몰판매)
//        String corp_tax_type= "TG01";                 								   // 과세/면세 구분 (과세 : TG01, 면세 : TF02)
//        String corp_tax_no  = environment.getProperty("pg.kcp.g.conf.tax.no");     // 발행 사업자 번호
//        String corp_nm      = "environment.getProperty('pg.kcp.g.conf.site.name')";  // 상호
//        String corp_owner_nm= environment.getProperty("pg.kcp.g.conf.corp.owner"); // 대표자명
//        String corp_addr    = environment.getProperty("pg.kcp.g.conf.corp.addr");  // 사업장주소
//        String corp_telno   = environment.getProperty("pg.kcp.g.conf.corp.tel");   // 사업장 대표 연락처
//        /* = --------------------------------------------------------------------------= */
//        String user_type    = "PGNW";                 // 사용자 구분
//        String tr_code      = cashReceipt.getCashReceiptType().equals("1") ? "1" : "0";// 발행용도(소득공제용 : 0, 지출증빙용 : 1)
//        String id_info      = cashReceipt.getCashReceiptCode().replaceAll("-", "");                 // 신분확인 ID
//
//        int tax = (int)(cashReceipt.getCashReceiptAmount() / 1.1 * 0.1);
//        int sup_price = cashReceipt.getCashReceiptAmount() - tax;
//
//        String amt_tot      = StringUtils.integer2string(cashReceipt.getCashReceiptAmount()); // 거래금액 총 합
//        String amt_sup      = StringUtils.integer2string(sup_price);                 		  // 공급가액
//        String amt_svc      = "0";                 											  // 봉사료
//        String amt_tax      = StringUtils.integer2string(tax);                 				  // 부가가치세
//
//        /* ============================================================================== */
//        /* =   02. 인스턴스 생성 및 초기화                                               = */
//        /* = -------------------------------------------------------------------------- = */
//        J_PP_CLI_N  c_PayPlus = new J_PP_CLI_N();
//
//        String g_conf_gw_port =environment.getProperty("pg.kcp.g.conf.gw.port");
//        String g_conf_log_dir = environment.getProperty("pg.kcp.g.conf.log.dir");
//        int g_conf_tx_mode = Integer.parseInt(environment.getProperty("pg.kcp.g.conf.tx.mode"));
//        String g_conf_site_cd = environment.getProperty("pg.kcp.g.conf.site.cd");
//        String g_conf_site_key = environment.getProperty("pg.kcp.g.conf.site.key");
//        String g_conf_log_level = environment.getProperty("pg.kcp.g.conf.log.level");
//        String g_conf_gw_url = environment.getProperty("pg.kcp.g.conf.gw.url");
//
//        c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
//        c_PayPlus.mf_init_set();
//        /* ============================================================================== */
//
//
//        /* ============================================================================== */
//        /* =   03. 처리 요청 정보 설정, 실행                                             = */
//        /* = -------------------------------------------------------------------------- = */
//
//        /* = -------------------------------------------------------------------------- = */
//        /* =   03-1. 승인 요청                                                          = */
//        /* = -------------------------------------------------------------------------- = */
//        // 업체 환경 정보
//        String cust_ip = getServerIp();
//
//        tx_cd = "07010000" ; // 현금영수증 등록 요청
//
//        int rcpt_data_set ;
//        int ordr_data_set ;
//        int corp_data_set ;
//
//        rcpt_data_set   = c_PayPlus.mf_add_set( "rcpt_data" ) ;
//        ordr_data_set   = c_PayPlus.mf_add_set( "ordr_data" ) ;
//        corp_data_set   = c_PayPlus.mf_add_set( "corp_data" ) ;
//
//        // 현금영수증 정보
//        c_PayPlus.mf_set_us( rcpt_data_set, "user_type", user_type ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "trad_time", trad_time ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "tr_code"  , tr_code   ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "id_info"  , id_info   ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "amt_tot"  , amt_tot   ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "amt_sup"  , amt_sup   ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "amt_svc"  , amt_svc   ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "amt_tax"  , amt_tax   ) ;
//        c_PayPlus.mf_set_us( rcpt_data_set, "pay_type" , "PAXX"    ) ;
//
//        // 주문 정보
//        c_PayPlus.mf_set_us( ordr_data_set, "ordr_idxx", ordr_idxx ) ;
//        c_PayPlus.mf_set_us( ordr_data_set, "good_name", good_name ) ;
//        c_PayPlus.mf_set_us( ordr_data_set, "buyr_name", buyr_name ) ;
//        c_PayPlus.mf_set_us( ordr_data_set, "buyr_tel1", buyr_tel1 ) ;
//        c_PayPlus.mf_set_us( ordr_data_set, "buyr_mail", buyr_mail ) ;
//        c_PayPlus.mf_set_us( ordr_data_set, "comment"  , comment   ) ;
//
//        // 가맹점 정보
//        c_PayPlus.mf_set_us( corp_data_set, "corp_type", corp_type ) ;
//
//        // 입점몰인 경우 판매상점 DATA 전문 생성
//        if( "1".equals( corp_type ) )
//        {
//            c_PayPlus.mf_set_us( corp_data_set, "corp_tax_type"   , corp_tax_type  ) ;
//            c_PayPlus.mf_set_us( corp_data_set, "corp_tax_no"     , corp_tax_no    ) ;
//            c_PayPlus.mf_set_us( corp_data_set, "corp_sell_tax_no", corp_tax_no    ) ;
//            c_PayPlus.mf_set_us( corp_data_set, "corp_nm"         , corp_nm        ) ;
//            c_PayPlus.mf_set_us( corp_data_set, "corp_owner_nm"   , corp_owner_nm  ) ;
//            c_PayPlus.mf_set_us( corp_data_set, "corp_addr"       , corp_addr      ) ;
//            c_PayPlus.mf_set_us( corp_data_set, "corp_telno"      , corp_telno     ) ;
//        }
//
//        c_PayPlus.mf_add_rs( ordr_data_set , rcpt_data_set ) ;
//        c_PayPlus.mf_add_rs( ordr_data_set , corp_data_set ) ;
//
//        /* ============================================================================== */
//        /* =   03-3. 실행                                                               = */
//        /* ------------------------------------------------------------------------------ */
//        if ( tx_cd.length() > 0 )
//        {
//            c_PayPlus.mf_do_tx( g_conf_site_cd, "", tx_cd, cust_ip, ordr_idxx, g_conf_log_level, "0" ) ;
//        }
//        else
//        {
//            c_PayPlus.m_res_cd  = "9562" ;
//            c_PayPlus.m_res_msg = "연동 오류" ;
//        }
//        res_cd  = c_PayPlus.m_res_cd ;                           // 결과 코드
//        res_msg = c_PayPlus.m_res_msg ;                          // 결과 메시지
//        /* ============================================================================== */
//
//
//        /* ============================================================================== */
//        /* =   04. 승인 결과 처리                                                       = */
//        /* = -------------------------------------------------------------------------- = */
//        if ( req_tx.equals( "pay" ) )
//        {
//            if ( res_cd.equals( "0000" ) )
//            {
//                cash_no    = c_PayPlus.mf_get_res( "cash_no"    ) ;     // 현금영수증 거래번호
//                receipt_no = c_PayPlus.mf_get_res( "receipt_no" ) ;     // 현금영수증 승인번호
//                app_time   = c_PayPlus.mf_get_res( "app_time"   ) ;     // 승인시간(YYYYMMDDhhmmss)
//                reg_stat   = c_PayPlus.mf_get_res( "reg_stat"   ) ;     // 등록 상태 코드
//                reg_desc   = c_PayPlus.mf_get_res( "reg_desc"   ) ;     // 등록 상태 설명
//
//                /* = -------------------------------------------------------------------------- = */
//                /* =   04-1. 승인 결과를 업체 자체적으로 DB 처리 작업하시는 부분입니다.           = */
//                /* = -------------------------------------------------------------------------- = */
//                /* =         승인 결과를 DB 작업 하는 과정에서 정상적으로 승인된 건에 대해         = */
//                /* =         DB 작업을 실패하여 DB update 가 완료되지 않은 경우, 자동으로         = */
//                /* =         승인 취소 요청을 하는 프로세스가 구성되어 있습니다.                  = */
//                /* =         DB 작업이 실패 한 경우, bSucc 라는 변수(String)의 값을 "false"      = */
//                /* =         로 세팅해 주시기 바랍니다. (DB 작업 성공의 경우에는 "false" 이외의   = */
//                /* =         값을 세팅하시면 됩니다.)                                            = */
//                /* = -------------------------------------------------------------------------- = */
//                cashReceipt.setCashReceiptIssueNumber(cash_no);
//                cashReceipt.setCashReceiptIssueDate(app_time);
//                cashReceipt.setSuccess(true);
//
//                bSucc = "true" ;             // DB 작업 실패일 경우 "false" 로 세팅
//
//                /* = -------------------------------------------------------------------------- = */
//                /* =   04-2. DB 작업 실패일 경우 자동 승인 취소                                  = */
//                /* = -------------------------------------------------------------------------- = */
//                if ( bSucc.equals( "false" ) )
//                {
//                    int mod_data_set_no ;
//
//                    mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" ) ;
//
//                    tx_cd = "07020000" ;	// 취소 요청
//
//                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_type" ,  "STSC"   ) ;
//                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_value", cash_no   ) ;
//                    c_PayPlus.mf_set_us( mod_data_set_no, "mod_gubn" ,  "MG01"   ) ;
//                    c_PayPlus.mf_set_us( mod_data_set_no, "trad_time", trad_time ) ;
//
//                    c_PayPlus.mf_do_tx( g_conf_site_cd, "", tx_cd, cust_ip, ordr_idxx, g_conf_log_level, "0" ) ;
//
//                    res_cd  = c_PayPlus.m_res_cd ;
//                    res_msg = c_PayPlus.m_res_msg ;
//
//                }
//                // End of [res_cd = "0000"]
//
//            }
//            else
//            {
//                cashReceipt.setSuccess(false);
//            }
//            /* = -------------------------------------------------------------------------- = */
//            /* =   04-3. 등록 실패를 업체 자체적으로 DB 처리 작업하시는 부분입니다.            = */
//            /* = -------------------------------------------------------------------------- = */
//            /* ============================================================================== */
//        }

        return null;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {
//        /* ============================================================================== */
//        /* =   01. 요청 정보 설정                                                       = */
//        /* = -------------------------------------------------------------------------- = */
//        String res_cd       = "";                                                      // 결과코드
//        String res_msg      = "";                                                      // 결과메시지
//        String tx_cd        = "";                                                      // 트랜잭션 코드
//        String bSucc        = "";                                                      // DB 작업 성공 여부
//        /* = --------------------------------------------------------------------------= */
//        String req_tx       = "mod";  									               // 요청 종류(승인 : pay, 변경 : mod)
//        String trad_time    = cashReceipt.getCashReceiptIssueDate();                 		   // 원거래 시각
//        /* = --------------------------------------------------------------------------= */
//        String ordr_idxx    = cashReceipt.getOrderCode() ;                 			   // 주문번호
//        /* = --------------------------------------------------------------------------= */
//        String cash_no      = "" ;                                                     // 현금영수증 거래번호
//        String receipt_no   = "" ;                                                     // 현금영수증 승인번호
//        String app_time     = "" ;                                                     // 승인시간(YYYYMMDDhhmmss)
//        String reg_stat     = "" ;                                                     // 등록 상태 코드
//        String reg_desc     = "" ;                                                     // 등록 상태 설명
//        /* = --------------------------------------------------------------------------= */
//
//        /* = --------------------------------------------------------------------------= */
//        String mod_type     = "STSC";                									 	  // 변경 타입	(STSC : 취소요청, STPC : 부분취소요청, STSQ : 조회요청)
//        String mod_value    = cashReceipt.getCashReceiptIssueNumber();                 	 	  // 변경 요청 거래번호
//        String mod_gubn     = "MG01";                 									 	  // 변경 요청 거래번호 구분	(MG01 : 영수증 거래번호 , MG02 : 영수증 승인번호, MG03 : 신분확인 ID, MG04 : 결제 거래번호)
////	    String mod_mny      = "0";                 										 	  // 변경 요청 금액
////	    String rem_mny      = StringUtils.integer2string(cashReceipt.getCashReceiptAmount()); // 변경처리 이전 금액
//        /* ============================================================================== */
//
//
//        /* ============================================================================== */
//        /* =   02. 인스턴스 생성 및 초기화                                               = */
//        /* = -------------------------------------------------------------------------- = */
//        J_PP_CLI_N  c_PayPlus = new J_PP_CLI_N();
//
//        String g_conf_gw_port =environment.getProperty("pg.kcp.g.conf.gw.port");
//        String g_conf_log_dir = environment.getProperty("pg.kcp.g.conf.log.dir");
//        int g_conf_tx_mode = Integer.parseInt(environment.getProperty("pg.kcp.g.conf.tx.mode"));
//        String g_conf_site_cd = environment.getProperty("pg.kcp.g.conf.site.cd");
//        String g_conf_log_level = environment.getProperty("pg.kcp.g.conf.log.level");
//        String g_conf_gw_url = environment.getProperty("pg.kcp.g.conf.gw.url");
//
//        c_PayPlus.mf_init( "", g_conf_gw_url, g_conf_gw_port, g_conf_tx_mode, g_conf_log_dir );
//        c_PayPlus.mf_init_set();
//        /* ============================================================================== */
//
//
//        /* ============================================================================== */
//        /* =   03. 처리 요청 정보 설정, 실행                                             = */
//        /* = -------------------------------------------------------------------------- = */
//
//        /* = -------------------------------------------------------------------------- = */
//        /* =   03-1. 승인 요청                                                          = */
//        /* = -------------------------------------------------------------------------- = */
//        // 업체 환경 정보
//        String cust_ip = getServerIp();
//
//        int     mod_data_set_no ;
//
//        mod_data_set_no = c_PayPlus.mf_add_set( "mod_data" ) ;
//
//        if( mod_type.equals( "STSQ" ) )
//        {
//            tx_cd = "07030000" ;     // 조회 요청
//        }
//        else
//        {
//            tx_cd = "07020000" ;     // 취소 요청
//        }
//
////        if( mod_type.equals( "STPC" ) )     // 부분 취소
////        {
////            c_PayPlus.mf_set_us( mod_data_set_no, "mod_mny"  , mod_mny ) ;
////            c_PayPlus.mf_set_us( mod_data_set_no, "rem_mny"  , rem_mny ) ;
////        }
//
//        c_PayPlus.mf_set_us( mod_data_set_no, "mod_type"  , mod_type  ) ;
//        c_PayPlus.mf_set_us( mod_data_set_no, "mod_value" , mod_value ) ;
//        c_PayPlus.mf_set_us( mod_data_set_no, "mod_gubn"  , mod_gubn  ) ;
//        c_PayPlus.mf_set_us( mod_data_set_no, "trad_time" , trad_time ) ;
//
//        /* ============================================================================== */
//
//
//        /* ============================================================================== */
//        /* =   03-3. 실행                                                               = */
//        /* ------------------------------------------------------------------------------ */
//        if ( tx_cd.length() > 0 )
//        {
//            c_PayPlus.mf_do_tx( g_conf_site_cd, "", tx_cd, cust_ip, ordr_idxx, g_conf_log_level, "0" ) ;
//        }
//        else
//        {
//            c_PayPlus.m_res_cd  = "9562" ;
//            c_PayPlus.m_res_msg = "연동 오류" ;
//        }
//        res_cd  = c_PayPlus.m_res_cd ;                           // 결과 코드
//        res_msg = c_PayPlus.m_res_msg ;                          // 결과 메시지
//        /* ============================================================================== */
//
//        /* ============================================================================== */
//        /* =   05. 변경 결과 처리                                                      			= */
//        /* = -------------------------------------------------------------------------- = */
//
//        if ( res_cd.equals ( "0000" ) )
//        {
//            cash_no    = c_PayPlus.mf_get_res( "cash_no"    ) ;  // 현금영수증 거래번호
//            receipt_no = c_PayPlus.mf_get_res( "receipt_no" ) ;  // 현금영수증 승인번호
//            app_time   = c_PayPlus.mf_get_res( "app_time"   ) ;  // 승인시간(YYYYMMDDhhmmss)
//            reg_stat   = c_PayPlus.mf_get_res( "reg_stat"   ) ;  // 등록 상태 코드
//            reg_desc   = c_PayPlus.mf_get_res( "reg_desc"   ) ;  // 등록 상태 설명
//
//            cashReceipt.setSuccess(true);
//            cashReceipt.setCashReceiptStatusCode("3");
//            cashReceipt.setCashReceiptIssueDate(app_time);
//        }
//        else
//        {
//            cashReceipt.setSuccess(false);
//        }
//
        return null;
    }

    //	private String getApprovalInfo(HttpServletRequest request, KcpRequest kcpRequest){
//		
//		ConnectionKCP suc = new ConnectionKCP();  // KCP 인증 정보를 저장하기 위한 Object                     ( 통신의 기본이 되는 객체 ) - 필수
//	    OpenHash      oh  = new OpenHash();       // KCP 와 통신시 데이터 위변조를 확인 하기 위한 Hash Object ( 업체와 KCP 간의 통신시 데이터 위변조를 확인 하기 위해 필요. 미설정시 통신 구간만 라이브러리에서 자체적으로 hash 처리 )
//	    HttpJsonXml   hjx = new HttpJsonXml();    // 응답값 get value 형식으로 가져올수 있는 Object           ( Java 또는 JSP 내에서 데이터를 파싱할때 필요 - XML 또는 JSON )
//	    ParamData     pd  = new ParamData();      // 파라메타 값을 세팅할수 있는 bean Object                  ( String, HashMap 등으로 대체 가능 )
//	    
//	    pd.setGood_mny(kcpRequest.getGood_mny());
//	    pd.setGood_name(kcpRequest.getGood_name());
//	    pd.setOrdr_idxx(kcpRequest.getOrdr_idxx());
//	    pd.setPay_method(kcpRequest.getPay_method());
//	    pd.setRet_URL(kcpRequest.getRet_url());
//	    pd.setSite_cd(environment.getProperty("pg.kcp.g.conf.site.cd"));
//	    pd.setEscw_used("N");
//
//	    String siteCode      = environment.getProperty("pg.kcp.g.conf.site.cd");
//	    String orderID       = kcpRequest.getOrdr_idxx();
//	    String paymentAmount = kcpRequest.getGood_mny();
//	    String paymentMethod = kcpRequest.getPay_method();
//	    String productName   = kcpRequest.getGood_name();
//	    String response_type = "TEXT";
//	    String Ret_URL       = kcpRequest.getRet_url();
//	    String escrow        = "N";
//
//	    pd.setResponse_type( "JSON" );
//	    
//	    boolean g_conf_server = false;
//	    if ("true".equals(environment.getProperty("pg.kcp.g.conf.server"))) {
//	    	g_conf_server = true;
//	    }
//	    
//	    return suc.kcpPaymentSmartPhone( request, g_conf_server, pd, environment.getProperty("pg.kcp.g.conf.log.dir") );
//		
//	}
}
