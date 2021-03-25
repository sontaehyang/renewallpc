package saleson.shop.order.pg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.common.ServiceType;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.notification.exception.NotificationException;
import com.onlinepowers.framework.util.ConvertUtils;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import saleson.common.Const;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.CashbillStatus;
import saleson.common.enumeration.CashbillStatusCode;
import saleson.common.enumeration.CashbillType;
import saleson.common.enumeration.TaxType;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.OrderUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.erp.domain.ErpOrderType;
import saleson.erp.service.ErpOrder;
import saleson.erp.service.ErpService;
import saleson.model.Cashbill;
import saleson.model.CashbillIssue;
import saleson.model.ConfigPg;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.Order;
import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.order.pg.domain.PgData;
import saleson.shop.order.pg.domain.ReturnUrlParam;
import saleson.shop.order.support.OrderException;
import saleson.shop.order.support.OrderParam;
import saleson.shop.receipt.support.CashbillIssueRepository;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service("nicepayService")
public class NicepayServiceImpl implements PgService {

    private static final Logger log = LoggerFactory.getLogger(NicepayServiceImpl.class);

    private final String CASHBILL_ISSUE_URI = "https://webapi.nicepay.co.kr/webapi/cash_receipt.jsp";
    private final String CANCEL_URI = "https://webapi.nicepay.co.kr/webapi/cancel_process.jsp";

    @Autowired
    private OrderPaymentMapper orderPaymentMapper;

    @Autowired
    RestTemplate customRestTemplate;

	@Autowired
	Environment environment;

	@Autowired
    ConfigPgService configPgService;

	@Autowired
    CashbillIssueRepository cashbillIssueRepository;

	@Autowired
    OrderService orderService;

	@Autowired
    ErpService erpService;

    public NicepayServiceImpl(RestTemplate customRestTemplate, Environment environment) {
        this.customRestTemplate = customRestTemplate;
        this.environment = environment;
    }

    private HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(MultiValueMap<String, Object> parameters) {
        return new HttpEntity<>(parameters, getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        StringBuffer sb = new StringBuffer();
        sb.append(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        sb.append(";");
        sb.append("charset=EUC-KR");
        headers.setContentType(MediaType.valueOf(sb.toString()));
        return headers;
    }

    @Override
    public HashMap<String, Object> init(Object data, HttpSession session) {

        /*
         *******************************************************
         * <결제요청 파라미터>
         * 결제시 Form 에 보내는 결제요청 파라미터입니다.
         * 샘플페이지에서는 기본(필수) 파라미터만 예시되어 있으며,
         * 추가 가능한 옵션 파라미터는 연동메뉴얼을 참고하세요.
         *******************************************************
         */
        HashMap<String, Object> payReqMap = new HashMap<>();
        

        /*
         *******************************************************
         * <서버 IP값>
         *******************************************************
         */
        InetAddress inet = null;
        try {
            inet = InetAddress.getLocalHost();
        } catch (Exception e) {
            throw new NotificationException(e.getMessage(), e);
        }

        try {

            ConfigPg configPg = configPgService.getConfigPg();

            if (configPg == null) {
                throw new NullPointerException("(Init) PG 정보가 존재하지 않습니다.");
            }

            String merchantId = configPg.getMid();
            String merchantKey = configPg.getKey();

            String charset = environment.getProperty("nicepay.charset");
            String socketYN = environment.getProperty("nicepay.socket.flag");
            String encodingParameters = environment.getProperty("nicepay.encoding.parameters");
            String ediDate = DateUtils.getToday(Const.DATETIME_FORMAT);

            /*
             *******************************************************
             * <해쉬암호화> (수정하지 마세요)
             * SHA-256 해쉬암호화는 거래 위변조를 막기위한 방법입니다.
             *******************************************************
             */
            String hashString = encrypt(ediDate + merchantId + ((PgData) data).getAmt() + merchantKey);
            
            payReqMap.put("PayMethod", getPayType(((PgData) data).getApprovalType()));
            payReqMap.put("SelectQuota", ((PgData) data).getSelectQuota());
            payReqMap.put("SelectCardCode", ((PgData) data).getSelectCardCode());

            payReqMap.put("GoodsName", ((PgData) data).getGoodsName());;
            payReqMap.put("Amt", ((PgData) data).getAmt());
            payReqMap.put("BuyerName", ((PgData) data).getBuyerName());
            payReqMap.put("BuyerTel", ((PgData) data).getBuyerTel());
            payReqMap.put("Moid", ((PgData) data).getMoid());
            payReqMap.put("MID", merchantId);

            payReqMap.put("MallIP", inet.getHostAddress());

            payReqMap.put("CharSet", charset);
            payReqMap.put("BuyerEmail", ((PgData) data).getBuyerEmail());

            payReqMap.put("EdiDate", ediDate);
            payReqMap.put("EncryptData", hashString);

            if ("api".equals(getSalesonViewType())) {
                String deviceType = ((PgData) data).getDeviceType();
                if ("MOBILE".equals(deviceType)) {
                    String returnPgUrl = SalesonProperty.getSalesonUrlShoppingmall() + "/api/order/redirect-pay";

                    if (ServiceType.LOCAL) {
                        returnPgUrl = "http://localhost:9080/api/order/redirect-pay";
                    }

                    ReturnUrlParam returnUrlParam = new ReturnUrlParam();
                    returnUrlParam.setSuccessUrl(CommonUtils.dataNvl(((PgData) data).getSuccessUrl()));
                    returnUrlParam.setFailUrl(CommonUtils.dataNvl(((PgData) data).getFailUrl()));
                    returnUrlParam.setSalesonId(CommonUtils.dataNvl(((PgData) data).getSalesonId()));
                    returnUrlParam.setToken(CommonUtils.dataNvl(((PgData) data).getSalesonToken()));
                    returnUrlParam.setTokenType(CommonUtils.dataNvl(((PgData) data).getSalesonTokenType()));
                    returnUrlParam.setDeviceType(((PgData) data).getDeviceType());

                    StringBuffer sb = new StringBuffer();
                    sb.append("?returnUrlParam=" + OrderUtils.encodeUrlSafeBase64(returnUrlParam));
                    sb.append("&Moid=" + ((PgData) data).getMoid());

                    returnPgUrl = returnPgUrl + sb.toString();

                    payReqMap.put("ReturnURL", returnPgUrl);

                } else {
                    payReqMap.put("UserIP", ((PgData) data).getUserIP());
                    payReqMap.put("SocketYN", socketYN);
                    payReqMap.put("EncodeParameters", encodingParameters);
                }

            } else {
                if (ShopUtils.isMobilePage()) {
                    String returnUrl = environment.getProperty("saleson.url.shoppingmall") + ShopUtils.getMobilePrefix() + "/order/pay";
                    payReqMap.put("ReturnURL", returnUrl);
                } else {
                    payReqMap.put("UserIP", ((PgData) data).getUserIP());
                    payReqMap.put("SocketYN", socketYN);
                    payReqMap.put("EncodeParameters", encodingParameters);
                }
            }

            return payReqMap;
        } catch (NullPointerException ne) {
            throw new NotificationException(ne.getMessage(), ne);
        } catch (Exception e) {
            throw new NotificationException("(Init) PG모듈 검색 결과 파싱 오류", e);
        }
    }

    @Override
    public OrderPgData pay(Object data, HttpSession session) {
        OrderPgData orderPgData = new OrderPgData();
        orderPgData.setSuccess(false);

        HttpServletRequest request = ((PgData) data).getRequest();
        HttpServletResponse response = ((PgData) data).getResponse();

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }

        /*
         *******************************************************
         * <변수 초기화>
         *******************************************************
         */
        String resultCode           = "";  String resultMsg            = "";  String authDate             = "";
        String authCode             = "";  String buyerName            = "";  String mallUserID           = "";
        String goodsName            = "";  String mid                  = "";  String tid                  = "";
        String moid                 = "";  String amt                  = "";  String cardCode             = "";
        String cardName             = "";  String cardQuota            = "";  String cardCl               = "";
        String ccPartCl             = "";  String bankCode             = "";  String bankName             = "";
        String rcptType             = "";  String rcptAuthCode         = "";  String rcptTID              = "";
        String carrier              = "";  String dstAddr              = "";  String vbankBankCode        = "";
        String vbankBankName        = "";  String vbankNum             = "";  String vbankExpDate         = "";
        String vbankInputName       = "";

        /*
         *******************************************************
         * <인증 결과>
         *******************************************************
         */
        String authResultCode       = getRequestStringParam(request.getParameter("AuthResultCode"));              // 인증결과 : 0000(성공)
        String authResultMsg        = getRequestStringParam(request.getParameter("AuthResultMsg"));               // 인증결과 메시지

        if (authResultCode == null) {
            authResultCode       = getRequestStringParam(request.getParameter("authResultCode"));              // 인증결과 : 0000(성공)
        }
        if (authResultMsg == null) {
            authResultMsg        = getRequestStringParam(request.getParameter("authResultMsg"));               // 인증결과 메시지
        }

        if (authResultCode == null) {
            authResultCode          = ((PgData) data).getAuthResultCode();                      // 인증결과 : 0000(성공)
        }
        if (authResultMsg == null) {
            authResultMsg           = ((PgData) data).getAuthResultMsg();                       // 인증결과 메시지
        }

        log.debug("[" + ((PgData) data).getOrderCode() + "] [" + getSalesonViewType() + " NicepayServiceImpl] {} = {}", "authResultCode", authResultCode);
        log.debug("[" + ((PgData) data).getOrderCode() + "] [" + getSalesonViewType() + " NicepayServiceImpl] {} = {}", "authResultMsg", authResultMsg);

        if ("0000".equals(authResultCode)) {
            try {
                /*
                 *******************************************************
                 * <결제 결과 설정>
                 * 사용전 결과 옵션을 사용자 환경에 맞도록 변경하세요.
                 * 로그 디렉토리는 꼭 변경하세요.
                 *******************************************************
                 */
                NicePayWEB nicepayWEB = new NicePayWEB();

                String logPath = environment.getProperty("nicepay.log.path");
                String payMethod = getPayType(((PgData) data).getApprovalType());

                ConfigPg configPg = configPgService.getConfigPg();

                log.debug("[" + ((PgData) data).getOrderCode() + "] [" + getSalesonViewType() + " NicepayServiceImpl] {} = {}", "configPg", configPg);

                if (configPg == null) {
                    throw new NullPointerException("(Pay) PG 정보가 존재하지 않습니다.");
                }

                String merchantKey = configPg.getKey();

                nicepayWEB.setParam("NICEPAY_LOG_HOME", logPath);                    // 로그 디렉토리 설정
                nicepayWEB.setParam("APP_LOG", "1");                                 // 어플리케이션로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicepayWEB.setParam("EVENT_LOG", "1");                               // 이벤트로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicepayWEB.setParam("EncFlag", "S");                                 // 암호화플래그 설정(N: 평문, S:암호화)
                nicepayWEB.setParam("SERVICE_MODE", "PY0");                          // 서비스모드 설정(결제 서비스 : PY0 , 취소 서비스 : CL0)
                nicepayWEB.setParam("Currency", "KRW");                              // 통화 설정(현재 KRW(원화) 가능)
                nicepayWEB.setParam("PayMethod", payMethod);                         // 결제방법
                nicepayWEB.setParam("EncodeKey", merchantKey);                       // 상점키

                /*
                 *******************************************************
                 * <결제 결과 필드>
                 * 아래 응답 데이터 외에도 전문 Header와 개별부 데이터 Get 가능
                 *******************************************************
                 */
                NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);
                httpRequestWrapper.addParameter("EncMode","S10");					    // 전문 암호화
                WebMessageDTO responseDTO   = nicepayWEB.doService(httpRequestWrapper, response);

                resultCode           = responseDTO.getParameter("ResultCode");       // 결과코드 (정상 결과코드:3001)
                resultMsg            = responseDTO.getParameter("ResultMsg");        // 결과메시지
                authDate             = responseDTO.getParameter("AuthDate");         // 승인일시 (YYMMDDHH24mmss)
                authCode             = responseDTO.getParameter("AuthCode");         // 승인번호
                buyerName            = responseDTO.getParameter("BuyerName");        // 구매자명
                mallUserID           = responseDTO.getParameter("MallUserID");       // 회원사고객ID
                goodsName            = responseDTO.getParameter("GoodsName");        // 상품명
                mid                  = responseDTO.getParameter("MID");              // 상점ID
                tid                  = responseDTO.getParameter("TID");              // 거래ID
                moid                 = responseDTO.getParameter("Moid");             // 주문번호
                amt                  = responseDTO.getParameter("Amt");              // 금액
                cardCode             = responseDTO.getParameter("CardCode");         // 결제카드사코드
                cardName             = responseDTO.getParameter("CardName");         // 결제카드사명
                cardQuota            = responseDTO.getParameter("CardQuota");        // 카드 할부개월 (00:일시불,02:2개월)
                cardCl               = responseDTO.getParameter("CardCl");           // 카드구분 (0:신용, 1:체크)
                ccPartCl             = responseDTO.getParameter("CcPartCl");         // 부분취소가능여부
                bankCode             = responseDTO.getParameter("BankCode");         // 은행코드
                bankName             = responseDTO.getParameter("BankName");         // 은행명
                rcptType             = responseDTO.getParameter("RcptType");         // 현금 영수증 타입 (0:발행되지않음,1:소득공제,2:지출증빙)
                rcptAuthCode         = responseDTO.getParameter("RcptAuthCode");     // 현금영수증 승인 번호
                rcptTID              = responseDTO.getParameter("RcptTID");          // 현금 영수증 TID
                carrier              = responseDTO.getParameter("Carrier");          // 이통사구분
                dstAddr              = responseDTO.getParameter("DstAddr");          // 휴대폰번호
                vbankBankCode        = responseDTO.getParameter("VbankBankCode");    // 가상계좌은행코드
                vbankBankName        = responseDTO.getParameter("VbankBankName");    // 가상계좌은행명
                vbankNum             = responseDTO.getParameter("VbankNum");         // 가상계좌번호
                vbankExpDate         = responseDTO.getParameter("VbankExpDate");     // 가상계좌입금예정일

                log.debug("[" + ((PgData) data).getOrderCode() + "] [" + getSalesonViewType() + " NicepayServiceImpl] {} = {}", "responseDTO", responseDTO.toString());

                /*
                 *******************************************************
                 * <결제 성공 여부 확인>
                 *******************************************************
                 */

                // 신용카드
                if("CARD".equals(payMethod) && "3001".equals(resultCode)) {
                    orderPgData.setSuccess(true);
                }

                // 계좌이체
                if("BANK".equals(payMethod) && "4000".equals(resultCode)) {
                    orderPgData.setBankCode(bankCode);
                    orderPgData.setBankInName(buyerName);
                    orderPgData.setRcptType(rcptType);
                    orderPgData.setSuccess(true);
                }

                // 휴대폰
                if("CELLPHONE".equals(payMethod) && "A000".equals(resultCode)) {
                    orderPgData.setSuccess(true);
                }

                // 가상계좌
                if("VBANK".equals(payMethod) && "4100".equals(resultCode)) {
                    orderPgData.setBankCode(vbankBankCode);
                    orderPgData.setBankName(vbankBankName);
                    orderPgData.setBankVirtualNo(vbankNum);
                    orderPgData.setBankInName(buyerName);
                    orderPgData.setBankDate(vbankExpDate);
                    orderPgData.setRcptType(rcptType);
                    orderPgData.setSuccess(true);
                }

                // SSG 은행계좌
                if("SSG_BANK".equals(payMethod) && "0000".equals(resultCode)) {
                    orderPgData.setSuccess(true);
                }

                orderPgData.setPgKey(tid);
                orderPgData.setPgAuthCode(authResultCode);
                orderPgData.setPgProcInfo(this.makePgLog(responseDTO, payMethod, authResultCode, authResultMsg));
                orderPgData.setPgPaymentType(payMethod);

                // 계좌이체
                if ("BANK".equals(payMethod)) {
                    orderPgData.setPartCancelFlag(configPg.isRealtimePartcancelFlag() ? "Y" : "N");
                } else {
                    orderPgData.setPartCancelFlag("1".equals(ccPartCl) ? "Y" : "N");
                }

                orderPgData.setPartCancelDetail("");

                if (!orderPgData.isSuccess()) {
                    orderPgData.setPgAuthCode(resultCode);
                    orderPgData.setErrorMessage(getPayTypeText(((PgData) data).getApprovalType()) + " 결제 요청 실패 : " + resultMsg);
                }

            } catch (Exception e) {
                throw new NotificationException("(Pay) PG모듈 검색 결과 파싱 오류", e);
            }
        } else {
            orderPgData.setPgAuthCode(authResultCode);
            orderPgData.setErrorMessage("결제 요청 실패 : " + authResultMsg);
        }

        return orderPgData;
    }

    private String getRequestStringParam(Object requestGetParam) {
        return requestGetParam == null ? null : (String) requestGetParam;
    }

    @Override
    public boolean cancel(OrderPgData orderPgData) {

        boolean isSuccess = false;

        try {
            if (orderPgData.getRequest() != null) {

                String logPath = environment.getProperty("nicepay.log.path");

                ConfigPg configPg = configPgService.getConfigPg();

                if (configPg == null) {
                    throw new NullPointerException("(Cancel) PG 정보가 존재하지 않습니다.");
                }

                // 가상계좌 환불 서비스에 가입 되어있으면 취소 API 호출
                if ("VBANK".equals(orderPgData.getPgPaymentType()) && configPg.isUseVbackRefundService()) {
                    return vbankCancel(orderPgData, false);
                }

                String cancelPassword = configPg.getCancelPassword();

                HttpServletRequest request = orderPgData.getRequest();

                NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);
                httpRequestWrapper.addParameter("MID", orderPgData.getPgServiceMid());
                httpRequestWrapper.addParameter("TID", orderPgData.getPgKey());         // 거래 아이디 (필수)
                httpRequestWrapper.addParameter("CancelAmt", Integer.toString(orderPgData.getCancelAmount()));                  // 취소 금액 (필수)
                httpRequestWrapper.addParameter("CancelPwd", cancelPassword);           // 취소 패스워드 (가맹점 관리에서 취소 패스워드 설정 시 applcation.properties에서 설정된 값 세팅 (필수)
                httpRequestWrapper.addParameter("PartialCancelCode", "0");  // 부분취소 여부 (0: 전체 취소, 1: 부분 취소) (필수)
                httpRequestWrapper.addParameter("CancelMsg", orderPgData.getCancelReason()); // 취소 사유 (선택)
                httpRequestWrapper.addParameter("EncMode", "S10");					    // 전문 암호화

                NicePayWEB nicepayWEB = new NicePayWEB();

                nicepayWEB.setParam("NICEPAY_LOG_HOME", logPath);   // 로그 디렉토리 설정
                nicepayWEB.setParam("APP_LOG", "1");                // 이벤트로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicepayWEB.setParam("EVENT_LOG", "1 ");             // 어플리케이션로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicepayWEB.setParam("EncFlag", "S");                // 암호화플래그 설정(N: 평문, S:암호화)
                nicepayWEB.setParam("SERVICE_MODE", "CL0");         // 서비스모드 설정(결제 서비스 : PY0 , 취소 서비스 : CL0)

                WebMessageDTO responseDTO = nicepayWEB.doService(httpRequestWrapper, orderPgData.getResponse());

                String resultCode = responseDTO.getParameter("ResultCode");      // 결과코드 (취소성공: 2001, 취소성공(LGU 계좌이체):2211)
                String resultMsg = responseDTO.getParameter("ResultMsg");        // 결과메시지
                String cancelAmt = responseDTO.getParameter("CancelAmt");        // 취소금액
                String cancelDate = responseDTO.getParameter("CancelDate");      // 취소일
                String cancelTime = responseDTO.getParameter("CancelTime");      // 취소시간
                String cancelNum = responseDTO.getParameter("CancelNum");        // 취소번호
                String payMethod = responseDTO.getParameter("PayMethod");        // 취소 결제수단
                String tid = responseDTO.getParameter("TID");                    // 거래아이디 TID

                if ("2001".equals(resultCode) || "2211".equals(resultCode)) {
                    isSuccess = true;
                } else {
                    orderPgData.setPgAuthCode(resultCode);
                    orderPgData.setErrorMessage(resultMsg);
                }
            }
        } catch (Exception e) {
            throw new NotificationException("(Cancel) PG모듈 검색 결과 파싱 오류", e);
        }

        orderPgData.setSuccess(isSuccess);

        return isSuccess;
    }

    @Override
    public String confirmationOfPayment(PgData pgData) {
        HttpServletRequest request = pgData.getRequest();

        //*********************************************************************************
        // 구매자가 입금하면 결제데이터 통보를 수신하여 DB 처리 하는 부분 입니다.
        // 수신되는 필드에 대한 DB 작업을 수행하십시오.
        // 수신필드 자세한 내용은 메뉴얼 참조
        //*********************************************************************************
        String payMethod        = pgData.getPayMethod();              //지불수단
        String mid              = pgData.getMID();                    //상점ID
        String mallUserID       = pgData.getMallUserID();             //회원사ID
        String amt              = pgData.getAmt();                    //금액
        String name             = pgData.getName();                   //구매자명
        String goodsName        = pgData.getGoodsName();              //상품명
        String tid              = pgData.getTID();                    //거래번호
        String moid             = pgData.getMOID();                   //주문번호
        String authDate         = pgData.getAuthDate();               //입금일시 (yyMMddHHmmss)
        String resultCode       = pgData.getResultCode();             //결과코드 ('4110' 경우 입금통보)
        String resultMsg        = pgData.getResultMsg();              //결과메시지
        String vbankNum         = pgData.getVbankNum();               //가상계좌번호
        String fnCd             = pgData.getFnCd();                   //가상계좌 은행코드
        String vbankName        = pgData.getVbankBankName();          //가상계좌 은행명
        String vbankInputName   = pgData.getVbankInputName();         //입금자 명
        String cancelDate       = pgData.getCancelDate();             //취소일시

        //*********************************************************************************
        //가상계좌채번시 현금영수증 자동발급신청이 되었을경우 전달되며
        //RcptTID 에 값이 있는경우만 발급처리 됨
        //*********************************************************************************
        String rcptTID          = pgData.getRcptTID();                //현금영수증 거래번호
        String rcptType         = pgData.getRcptType();               //현금 영수증 구분(0:미발행, 1:소득공제용, 2:지출증빙용)
        String rcptAuthCode     = pgData.getRcptAuthCode();           //현금영수증 승인번호

        // 가상계좌 입금통보
        if ("VBANK".equals(payMethod) && "4110".equals(resultCode)) {
            OrderPayment orderPayment = orderPaymentMapper.getOrderPaymentByPgDataForKcpVacct(moid);

            if (orderPayment == null) {
                log.debug("[ORDER] ERROR >> 주문 데이터 없음");
                return "FAIL";
            }

            if (orderPayment.getAmount() != Integer.parseInt(amt)) {
                log.debug("[ORDER] ERROR >> 주문 금액 불일치");
                return "FAIL";
            }

            if ("0".equals(orderPayment.getOrderStatus())) {

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
                        /*if(!orderPayment.getEscrowStatus().equals("N")){	//에스크로면 에스크로 상태 결제완료로 변경
                            orderParam.setEscrowStatus("10");
                            orderMapper.updateEscrowStatus(orderParam);
                        }*/
                    } else {
                        log.debug("[ORDER] ERROR >> 잘못된 접근입니다.");
                        return "FAIL";
                    }
                } else {
                    log.debug("[ORDER] ERROR >> 잘못된 접근입니다.");
                    return "FAIL";
                }

                // 현금영수증 발행
                CashbillParam cashbillParam = new CashbillParam();

                cashbillParam.setWhere("orderCode");
                cashbillParam.setQuery(orderParam.getOrderCode());

                Iterable<CashbillIssue> cashbillIssues = cashbillIssueRepository.findAll(cashbillParam.getPredicate());

                log.debug("[CASHBILL] START ---------------------------------------------");
                log.debug("[CASHBILL] cashbillIssues Size :  {}", ((List<CashbillIssue>) cashbillIssues).size());

                CashbillResponse response = null;

                for (CashbillIssue cashbillIssue : cashbillIssues) {

                    Cashbill cashbill = cashbillIssue.getCashbill();

                    cashbillParam.setAmount(cashbillIssue.getAmount());
                    cashbillParam.setItemName(cashbillIssue.getItemName());
                    cashbillParam.setTaxType(cashbillIssue.getTaxType());
                    cashbillParam.setCashbillCode(cashbill.getCashbillCode());
                    cashbillParam.setCashbillType(cashbill.getCashbillType());
                    cashbillParam.setCustomerName(cashbill.getCustomerName());
                    cashbillParam.setOrderCode(orderParam.getOrderCode());

                    response = this.cashReceiptIssued(cashbillParam);

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


                Order order = orderService.getOrderByParam(orderParam);

                ErpOrder erpOrder = new ErpOrder(order, ErpOrderType.ORDER_BANK);

                // 0:미발행, 1:소득공제용, 2:지출증빙용
                List<String> billTypes = Arrays.asList("", "현금영수증", "세금계산서");
                erpOrder.setBillType(billTypes.get(Integer.parseInt("".equals(rcptType) ? "0" : rcptType)));

                try {
                    // ERP 연동
                    erpService.saveOrderListGet(erpOrder);
                } catch (Exception e) {
                    log.error("ERROR : {}", e.getMessage(), e);
                    return "FAIL";
                }

            } else {    // 입금대기상태가 아닌데 입금통보가 오는 경우 주문번호, 날짜등을 로그로 기록

				//*********************************************************************************
				// 이부분에 로그파일 경로를 수정해주세요.
				// 로그는 문제발생시 오류 추적의 중요데이터 이므로 반드시 적용해주시기 바랍니다.
				//*********************************************************************************
				String logLoot = environment.getProperty("nicepay.log.path");
				String logPath = logLoot + "/notiLog/" + DateUtils.getToday() + "/";
				String fileName = orderPayment.getOrderCode()+".log";

                File file = new File(logPath);
                if (!file.isDirectory()) {
                    file.mkdirs();
                }

				try (FileWriter fw = new FileWriter(logPath + fileName, true)) {

                    fw.write("["+DateUtils.getToday("yyyy-MM-dd HH:mm:ss")+"] " + orderPayment.getOrderCode() + " - PG사로부터 입금통보 수신확인");
                    fw.write("************************************************\r\n");
                    fw.write("PayMethod     : " + payMethod + "\r\n");
                    fw.write("MID           : " + mid + "\r\n");
                    fw.write("MallUserID    : " + mallUserID + "\r\n");
                    fw.write("Amt           : " + amt + "\r\n");
                    fw.write("name          : " + name + "\r\n");
                    fw.write("GoodsName     : " + goodsName + "\r\n");
                    fw.write("TID           : " + tid + "\r\n");
                    fw.write("MOID          : " + moid + "\r\n");
                    fw.write("AuthDate      : " + authDate + "\r\n");
                    fw.write("ResultCode    : " + resultCode + "\r\n");
                    fw.write("ResultMsg     : " + resultMsg + "\r\n");
                    fw.write("VbankNum      : " + vbankNum + "\r\n");
                    fw.write("FnCd          : " + fnCd + "\r\n");
                    fw.write("VbankName     : " + vbankName + "\r\n");
                    fw.write("VbankInputName : " + vbankInputName + "\r\n");
                    fw.write("RcptTID       : " + rcptTID + "\r\n");
                    fw.write("RcptType      : " + rcptType + "\r\n");
                    fw.write("RcptAuthCode  : " + rcptAuthCode + "\r\n");
                    fw.write("CancelDate    : " + cancelDate + "\r\n");
                    fw.write("************************************************\r\n");

					try (BufferedWriter bf = new BufferedWriter(fw)){
						bf.newLine();
					} catch (Exception e) {
						log.error("[FILE] ERROR >> 로그파일 저장 오류 :  {}", e.getMessage(), e);
						return "FAIL";
					}

                } catch (Exception e) {
                    log.error("[FILE] ERROR >> 로그파일 저장 오류 :  {}", e.getMessage(), e);
                    return "FAIL";
                }
            }

        } else {
            log.error("[PG] ERROR >> 가상계좌 입금통보 실패 :  {}", resultMsg);
            return "FAIL";
        }

        //가맹점 DB처리

        //**************************************************************************************************
        //**************************************************************************************************
        //결제 데이터 통보 설정 > “OK” 체크박스에 체크한 경우" 만 처리 하시기 바랍니다.
        //**************************************************************************************************
        //TCP인 경우 OK 문자열 뒤에 라인피드 추가
        //위에서 상점 데이터베이스에 등록 성공유무에 따라서 성공시에는 "OK"를 NICEPAY로
        //리턴하셔야합니다. 아래 조건에 데이터베이스 성공시 받는 FLAG 변수를 넣으세요
        //(주의) OK를 리턴하지 않으시면 NICEPAY 서버는 "OK"를 수신할때까지 계속 재전송을 시도합니다
        //기타 다른 형태의 PRINT(out.print)는 하지 않으시기 바랍니다
        //if (데이터베이스 등록 성공 유무 조건변수 = true)
        //  {
        //      out.print("OK"); // 절대로 지우지 마세요
        //  }
        //  else
        //  {
        //      out.print("FAIL"); // 절대로 지우지 마세요
        //  }
        //*************************************************************************************************
        //*************************************************************************************************

        return "OK";
    }

    @Override
    public OrderPgData partCancel(OrderPgData orderPgData) {

        boolean isSuccess = false;

        try {
            String logPath = environment.getProperty("nicepay.log.path");

            ConfigPg configPg = configPgService.getConfigPg();

            if (configPg == null) {
                throw new NullPointerException("(PartCancel) PG 정보가 존재하지 않습니다.");
            }

            // 가상계좌 환불 서비스에 가입 되어있으면 취소 API 호출
            if ("VBANK".equals(orderPgData.getPgPaymentType()) && configPg.isUseVbackRefundService()) {
                isSuccess = vbankCancel(orderPgData, true);

            } else {

                String cancelPassword = configPg.getCancelPassword();

                NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(orderPgData.getRequest());
                httpRequestWrapper.addParameter("MID", orderPgData.getPgServiceMid());
                httpRequestWrapper.addParameter("TID", orderPgData.getPgKey());         // 거래 아이디 (필수)
                httpRequestWrapper.addParameter("CancelAmt", Integer.toString(orderPgData.getCancelAmount()));                  // 취소 금액 (필수)
                httpRequestWrapper.addParameter("CancelPwd", cancelPassword);           // 취소 패스워드 (가맹점 관리에서 취소 패스워드 설정 시 applcation.properties에서 설정된 값 세팅 (필수)
                httpRequestWrapper.addParameter("PartialCancelCode", "1");              // 부분취소 여부 (0: 전체 취소, 1: 부분 취소) (필수)
                httpRequestWrapper.addParameter("CancelMsg", orderPgData.getCancelReason()); // 취소 사유 (선택)
                httpRequestWrapper.addParameter("EncMode", "S10");					    // 전문 암호화

                NicePayWEB nicepayWEB = new NicePayWEB();

                nicepayWEB.setParam("NICEPAY_LOG_HOME", logPath);                           // 로그 디렉토리 설정
                nicepayWEB.setParam("APP_LOG","1");                                         // 이벤트로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicepayWEB.setParam("EVENT_LOG","1 ");                                      // 어플리케이션로그 모드 설정(0: DISABLE, 1: ENABLE)
                nicepayWEB.setParam("EncFlag","S");                                         // 암호화플래그 설정(N: 평문, S:암호화)
                nicepayWEB.setParam("SERVICE_MODE","CL0");                                  // 서비스모드 설정(결제 서비스 : PY0 , 취소 서비스 : CL0)

                WebMessageDTO responseDTO = nicepayWEB.doService(httpRequestWrapper, orderPgData.getResponse());

                String resultCode           = responseDTO.getParameter("ResultCode");       // 결과코드 (취소성공: 2001, 취소성공(LGU 계좌이체):2211)
                String resultMsg            = responseDTO.getParameter("ResultMsg");        // 결과메시지
                String cancelAmt            = responseDTO.getParameter("CancelAmt");        // 취소금액
                String cancelDate           = responseDTO.getParameter("CancelDate");       // 취소일
                String cancelTime           = responseDTO.getParameter("CancelTime");       // 취소시간
                String cancelNum            = responseDTO.getParameter("CancelNum");        // 취소번호
                String payMethod            = responseDTO.getParameter("PayMethod");        // 취소 결제수단
                String tid                  = responseDTO.getParameter("TID");              // 거래아이디 TID

                if ("2001".equals(resultCode) || "2211".equals(resultCode)) {
                    isSuccess = true;

                    orderPgData.setPgAmount(orderPgData.getRemainAmount());
                    orderPgData.setPgProcInfo(this.makePgLog(responseDTO, orderPgData.getPgPaymentType(), "", ""));
                    orderPgData.setPartCancelDetail("PART_CANCEL");

                } else {
                    orderPgData.setPgAuthCode(resultCode);
                    orderPgData.setErrorMessage(resultMsg);
                }
            }

        } catch (Exception e) {
            throw new NotificationException("(PartCancel) PG모듈 검색 결과 파싱 오류", e);
        }

        orderPgData.setSuccess(isSuccess);

        return orderPgData;
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
    public boolean delivery(HashMap<String, Object> paramMap) {

        return false;
    }

    public CashbillResponse issue(MultiValueMap<String, Object> parameter) {

        ResponseEntity<String> response = customRestTemplate.postForEntity(CASHBILL_ISSUE_URI,
                getMultiValueMapHttpEntity(parameter), String.class);

        CashbillResponse cashbillResponse = null;

        if (200 == response.getStatusCode().value()) {
            // API에서 200이 아닌 경우에도 응답이 오는 지 확인하여 로직을 구현함.
            HashMap<String,Object> responseMap = (HashMap<String,Object>) JsonViewUtils.jsonToObject(response.getBody(),new TypeReference<HashMap<String,Object>>(){});
            cashbillResponse = (CashbillResponse) ConvertUtils.mapToObject(responseMap, CashbillResponse.class);

            // 현금영수증 처리 성공
            if ("7001".equals(cashbillResponse.getResultCode())) {
                cashbillResponse.setSuccess(true);
                cashbillResponse.setMgtKey(cashbillResponse.getTID());
                cashbillResponse.setCashbillIssueDate(cashbillResponse.getAuthDate());
            }  else {
                cashbillResponse.setSuccess(false);
                cashbillResponse.setResponseCode(cashbillResponse.getResultCode());
                cashbillResponse.setResponseMessage(cashbillResponse.getResultMsg());
            }
        }
        return cashbillResponse;
    }

    public CashbillResponse cancel(MultiValueMap<String, Object> parameter) {

        ResponseEntity<String> response = customRestTemplate.postForEntity(CANCEL_URI,
                getMultiValueMapHttpEntity(parameter), String.class);

        CashbillResponse cashbillResponse = null;

        if (200 == response.getStatusCode().value()) {
            // API에서 200이 아닌 경우에도 응답이 오는 지 확인하여 로직을 구현함.
            HashMap<String,Object> responseMap = (HashMap<String,Object>) JsonViewUtils.jsonToObject(response.getBody(),new TypeReference<HashMap<String,Object>>(){});
            cashbillResponse = (CashbillResponse) ConvertUtils.mapToObject(responseMap, CashbillResponse.class);

            // 현금영수증 취소 성공
            if ("2001".equals(cashbillResponse.getResultCode())) {
                cashbillResponse.setSuccess(true);
                cashbillResponse.setCashbillIssueDate(cashbillResponse.getAuthDate());
                cashbillResponse.setStatusCode(CashbillStatusCode.CANCEL);
            } else {
                cashbillResponse.setResponseCode(cashbillResponse.getResultCode());
                cashbillResponse.setResponseMessage(cashbillResponse.getResultMsg());
                cashbillResponse.setSuccess(false);
            }
        }

        return cashbillResponse;
    }

    @Override
    public CashbillResponse cashReceiptIssued(CashbillParam cashbillParam) {

        MultiValueMap<String, Object> requestMap= new LinkedMultiValueMap<>();
        CashbillResponse cashbillResponse = null;


        ConfigPg configPg = configPgService.getConfigPg();

        if (configPg == null) {
            throw new NullPointerException("(CashReceiptIssued) PG 정보가 존재하지 않습니다.");
        }

        String merchantId = configPg.getMid();
        String merchantKey = configPg.getKey();

        String ediDate = DateUtils.getToday(Const.DATETIME_FORMAT);
        String shortYearDate = DateUtils.getToday(Const.SHORT_YEAR_DATETIME_FORMAT);

        SecureRandom secureRandom = new SecureRandom();
        String randomValue = StringUtils.toSubString(Integer.toString(secureRandom.nextInt(999999)), 0, 4);

        // TID 생성 규칙 : MID + 04 (현금영수증) + 01 (고정) + 시간정보 (yyMMddHHmmss) + 랜덤 (4byte)
        String tid = merchantId + "04" + "01" + shortYearDate + randomValue;

        // SignData 생성 규칙 : Hex(SHA256(MID + ReceiptAmt + EdiDate + Moid + MerchantKey))
        String signData = encrypt(merchantId + cashbillParam.getAmount() + ediDate + cashbillParam.getOrderCode() + merchantKey);

        String receiptType = CashbillType.PERSONAL == cashbillParam.getCashbillType() ? "1" : "2";

        int receiptVAT = 0;
        if (TaxType.CHARGE == cashbillParam.getTaxType()) {
            receiptVAT = (int)(cashbillParam.getAmount() / 1.1 * 0.1);
        }

        int receiptSupplyAmt = (int)cashbillParam.getAmount() - receiptVAT;

        try {
            requestMap.add("TID", tid);                                                             // 거래번호
            requestMap.add("MID", merchantId);                                                      // 상점 ID
            requestMap.add("EdiDate", ediDate);                                                     // 전문생성일시 (YYYYMMDDHHMISS)
            requestMap.add("Moid", cashbillParam.getOrderCode());                                   // 상점에서 부여한 주문번호(Unique하게 구성)
            requestMap.add("ReceiptAmt", cashbillParam.getAmount());                                // 현금영수증 요청 금액
            requestMap.add("GoodsName", StringUtils.null2void(cashbillParam.getItemName()));        // 상품명
            requestMap.add("SignData", signData);                                                   // 위변조 검증 Data
            requestMap.add("ReceiptType", receiptType);                                             // 증빙구분(1: 소득공제, 2: 지출증빙)
            requestMap.add("ReceiptTypeNo", cashbillParam.getCashbillCode());                       // 현금영수증 발급번호
            requestMap.add("ReceiptSupplyAmt", receiptSupplyAmt);                                   // 별도 공급가액 설정 시 사용
            requestMap.add("ReceiptVAT", receiptVAT);                                               // 별도 부가세 설정 시 사용
            requestMap.add("ReceiptServiceAmt", "0");                                               // 별도 봉사료 설정 시 사용
            requestMap.add("ReceiptTaxFreeAmt", receiptVAT > 0 ? "0" : receiptVAT);                 // 별도 면세금액 설정 시 사용
            requestMap.add("BuyerName", StringUtils.null2void(cashbillParam.getCustomerName()));    // 구매자 이름
            requestMap.add("BuyerEmail", cashbillParam.getEmail());                                 // 구매자 이메일주소
            requestMap.add("CharSet", "UTF-8");                                                     // 응답파라메터 인코딩 방식 (utf-8 / euc-kr(Default))

            cashbillResponse = issue(requestMap);
        } catch (Exception e) {
            throw new NotificationException("현금영수증 발급 처리 중 문제가 발생했습니다. ", e);
        }

        return cashbillResponse;
    }

    @Override
    public CashbillResponse cashReceiptCancel(CashbillParam cashbillParam) {

        MultiValueMap<String, Object> requestMap= new LinkedMultiValueMap<>();
        CashbillResponse cashbillResponse = null;

        ConfigPg configPg = configPgService.getConfigPg();

        if (configPg == null) {
            throw new NullPointerException("(CashReceiptCancel) PG 정보가 존재하지 않습니다.");
        }

        String merchantId = configPg.getMid();
        String merchantKey = configPg.getKey();

        String ediDate = DateUtils.getToday(Const.DATETIME_FORMAT);

        // SignData 생성 규칙 : Hex(SHA256(MID + CancelAmt + EdiDate + Moid + MerchantKey))
        String signData = encrypt(merchantId + cashbillParam.getAmount() + ediDate + merchantKey);

        int receiptVAT = 0;
        if (TaxType.CHARGE == cashbillParam.getTaxType()) {
            receiptVAT = (int)(cashbillParam.getAmount() / 1.1 * 0.1);
        }

        int receiptSupplyAmt = (int)cashbillParam.getAmount() - receiptVAT;

        try {
            requestMap.add("TID", cashbillParam.getMgtKey());                   // 거래 ID
            requestMap.add("MID", merchantId);                                 // 상점 ID
            requestMap.add("Moid", cashbillParam.getOrderCode());               // 상점에서 부여한 주문번호 (Unique하게 구성)
            requestMap.add("CancelAmt", cashbillParam.getAmount());             // 취소 금액
            requestMap.add("SupplyAmt", receiptSupplyAmt);                      // 별도 공급가액 설정 시 사용
            requestMap.add("GoodsVat", receiptVAT);                             // 별도 부가가치세 설정 시 사용
            requestMap.add("ServiceAmt", "0");                                  // 별도 봉사료 설정 시 사용
            requestMap.add("TaxFreeAmt", receiptVAT > 0 ? "0" : receiptVAT);    // 별도 면세금액 설정 시 사용
            requestMap.add("CancelMsg", "주문취소");                             // 취소 사유
            requestMap.add("PartialCancelCode", "0");                           // 부분취소 여부(전체취소 : 0 / 부분취소 : 1)
            requestMap.add("EdiDate", ediDate);                                 // 전문생성일시 (YYYYMMDDHHMISS)
            requestMap.add("SignData", signData);                               // 위변조 검증 Dat
            requestMap.add("CharSet", "UTF-8");                                 // 응답파라메터 인코딩 방식 (utf-8 / euc-kr(default))
            requestMap.add("Editype", "KV");                                    // 응답전문 유형 (default(미설정): JSON / KV(설정): Key=Value형식 응답)

            cashbillResponse = cancel(requestMap);
        } catch (Exception e) {
            throw new NotificationException("현금영수증 취소 처리 중 문제가 발생했습니다. ", e);
        }

        return cashbillResponse;
    }

    public boolean vbankCancel(OrderPgData orderPgData, boolean partCancelFlag) throws Exception {
        String merchantKey = orderPgData.getPgServiceKey();
        String MID = orderPgData.getPgServiceMid();
        String TID = orderPgData.getPgKey();
        String Moid = orderPgData.getOrderCode();
        String CancelAmt = Integer.toString(orderPgData.getCancelAmount());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("TID", TID);                // 거래 ID (필수)
        map.add("MID", MID);                // 상점 ID (필수)
        map.add("Moid", Moid);              // 상점 주문번호 (필수)
        map.add("CancelAmt", CancelAmt);    // 취소금액

        String cancelReason = StringUtils.isEmpty(orderPgData.getCancelReason())? "주문 취소(사유 미입력)" : orderPgData.getCancelReason();
        map.add("CancelMsg", URLEncoder.encode(cancelReason,"euc-kr")); // 취소 사유 (필수)
        map.add("PartialCancelCode", partCancelFlag ? "1" :"0");  // 부분취소 여부 (0: 전체 취소, 1: 부분 취소) (필수)

        String EdiDate = DateUtils.getToday(Const.DATETIME_FORMAT);
        String SignData = encrypt(MID + CancelAmt + EdiDate + merchantKey);

        map.add("EdiDate", EdiDate);                                // 전문생성일시 (YYYYMMDDHHMISS)
        map.add("SignData", SignData);                              // 위변조 검증 Data, Hex(SHA256(MID + CancelAmt + EdiDate + merchantKey))
        map.add("CharSet", "UTF-8");                                // 응답파라메터 인코딩 방식 (utf-8 / euc-kr(default))
        map.add("CartType", "0");                                   // 장바구니 결제 유형 (장바구니 결제: 1 / 그 외:0 )
        map.add("EdiType ", "KV");                                  // 응답전문 유형 (default(미설정): JSON / KV(설정): Key=Value형식 응답)
        map.add("RefundAcctNo", orderPgData.getReturnAccountNo());  // 환불계좌번호
        map.add("RefundBankCd", orderPgData.getReturnBankName());   // 환불계좌 은행코드
        map.add("RefundAcctNm", orderPgData.getReturnName());       // 환불예금주명

        String ResultCode 	= "9999"; // 결과 코드
        String ResultMsg 	= "통신실패"; // 결과 메시지
        boolean isSuccess = false;

        ResponseEntity<String> res = customRestTemplate.postForEntity(CANCEL_URI,
                getMultiValueMapHttpEntity(map), String.class);
        HashMap<String,Object> responseMap = (HashMap<String,Object>) JsonViewUtils.jsonToObject(res.getBody(),new TypeReference<HashMap<String,Object>>(){});

        ResultCode 	= (String)responseMap.get("ResultCode");
        ResultMsg 	= (String)responseMap.get("ResultMsg");

        if ("2001".equals(ResultCode) || "2211".equals(ResultCode)) {
            isSuccess = true;

            if (partCancelFlag) {

                Set<String> keySet = responseMap.keySet();
                WebMessageDTO responseDTO = new WebMessageDTO();
                keySet.forEach(s -> {
                    responseDTO.setParameter(s, (String)responseMap.get(s));
                });

                String pgLog = makePgLog(responseDTO, "VBANK", ResultCode, ResultMsg);

                orderPgData.setPgAmount(orderPgData.getRemainAmount());
                orderPgData.setPgProcInfo(pgLog);
                orderPgData.setPartCancelDetail("PART_CANCEL");
            }

        } else {
            orderPgData.setPgAuthCode(ResultCode);
            orderPgData.setErrorMessage(ResultMsg);
        }

        return isSuccess;
    }


    @Override
    public String getPayType(String payType) {
        if ("card".equals(payType)) {
            return "CARD";
        } else if ("vbank".equals(payType)) {
            return "VBANK";
        } else if ("realtimebank".equals(payType)) {
            return "BANK";
        } else if ("hp".equals(payType)) {
            return "CELLPHONE";
        }

        return "";
    }

    public String getPayTypeText(String payType) {
        if ("card".equals(payType)) {
            return "신용카드";
        } else if ("vbank".equals(payType)) {
            return "가상계좌";
        } else if ("realtimebank".equals(payType)) {
            return "실시간 계좌이체";
        } else if ("hp".equals(payType)) {
            return "휴대전화";
        }

        return "";
    }

    private String makePgLog(WebMessageDTO responseDTO, String approvalType, String authResultCode, String authResultMsg) {
        StringBuffer sb = new StringBuffer();

        /*****************************************************************************************************************
         *  모든 결제 수단에 공통되는 결제 결과 데이터
         *
         * 	거래ID : responseDTO.getParameter("TID")
         * 	결과코드 : responseDTO.getParameter("ResultCode") (정상 결과코드:3001)
         * 	결과메시지 : responseDTO.getParameter("ResultMsg")
         * 	결제방법 : responseDTO.getParameter("PayMethod")
         * 	주문번호 : responseDTO.getParameter("Moid")
         *	금액 : responseDTO.getParameter("Amt")
         *  승인일시 : responseDTO.getParameter("AuthDate") (YYMMDDHH24mmss)
         * 	승인번호 : responseDTO.getParameter("AuthCode")
         *
         *  가상계좌 은행코드 : responseDTO.getParameter("VbankBankCode")
         *  가상계좌 은행명 : responseDTO.getParameter("VbankBankName")
         * 	가상계좌 번호 : responseDTO.getParameter("VbankNum")
         * 	가상계좌 입금예정일 : responseDTO.getParameter("VbankExpDate")
         */

        String[] keys = new String[]{
            "TID", "AuthResultCode", "AuthResultMsg", "ResultCode", "ResultMsg", "PayMethod", "Moid", "Amt", "AuthDate", "AuthCode"
        };

        if (approvalType.equals("VBANK")) {
            keys = new String[]{
                "TID", "AuthResultCode", "AuthResultMsg", "ResultCode", "ResultMsg", "PayMethod", "Moid",
                "Amt", "AuthDate", "AuthCode", "VbankBankCode", "VbankBankName", "VbankNum", "VbankExpDate"
            };
        } else if (approvalType.equals("CARD")) {
            keys = new String[]{
                    "TID", "AuthResultCode", "AuthResultMsg", "ResultCode", "ResultMsg", "PayMethod", "Moid",
                    "Amt", "AuthDate", "AuthCode", "CardName", "CardQuota"
            };
        } else if (approvalType.equals("BANK")) {
            keys = new String[]{
                    "TID", "AuthResultCode", "AuthResultMsg", "ResultCode", "ResultMsg", "PayMethod", "Moid",
                    "Amt", "AuthDate", "AuthCode", "BankCode", "BankName"
            };
        }

        for(String key : keys) {
            try {
                String value = responseDTO.getParameter(key);

                if ("AuthResultCode".equals(key)) {
                    value = authResultCode;
                }

                if ("AuthResultMsg".equals(key)) {
                    value = authResultMsg;
                }

                sb.append("[" + responseDTO.getParameter("Moid") + "] [" + getSalesonViewType() + " NicepayServiceImpl] " + key + " -> " + value + "\n");

            } catch (Exception e) {
                log.error("NicepayServiceImpl Error: {}", e.getMessage());
            }
        }

        return sb.toString();
    }

    public String encrypt(String strData) {
        String passACL = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(strData.getBytes());
            byte[] raw = md.digest();
            passACL = encodeHex(raw);
        } catch(Exception e) {
            throw new NotificationException("암호화 에러" + e.toString(), e);
        }

        return passACL;
    }

    public String encodeHex(byte [] b) {
        char [] c = Hex.encodeHex(b);
        return new String(c);
    }

    public String getSalesonViewType() {
        String salesonViewType = environment.getProperty("saleson.view.type");
        return salesonViewType;
    }
}

