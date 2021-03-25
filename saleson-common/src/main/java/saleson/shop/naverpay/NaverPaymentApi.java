package saleson.shop.naverpay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import saleson.common.utils.UserUtils;
import saleson.model.ConfigPg;
import saleson.shop.naverpay.domain.Detail;
import saleson.shop.naverpay.domain.NaverApiResponse;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.config.ConfigPgService;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("naverPaymentApi")
public class NaverPaymentApi {

    private final String PAY_URL = "/naverpay/payments/v2.2/apply/payment";
    private final String CANCEL_URL = "/naverpay/payments/v1/cancel";
    private final String POINT_URL = "/naverpay/payments/v1/naverpoint-save";
    private final String HISTORY_URL = "/naverpay/payments/v2.2/list/history";

    @Autowired
    RestTemplate naverPaymentRestTemplate;

    @Autowired
    RestTemplate naverOtherRestTemplate;

    @Autowired
    Environment environment;

    @Autowired
    ConfigPgService configPgService;

    public NaverPaymentApi(RestTemplate naverPaymentRestTemplate, RestTemplate naverOtherRestTemplate,
                           Environment environment) {
        this.naverPaymentRestTemplate = naverPaymentRestTemplate;
        this.naverOtherRestTemplate = naverOtherRestTemplate;
        this.environment = environment;
    }


    private HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(MultiValueMap<String, Object> parameters, ConfigPg configPg, String contentType) {
        return new HttpEntity<>(parameters, getHttpHeaders(configPg, contentType));
    }

    private HttpHeaders getHttpHeaders(ConfigPg configPg, String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));

        headers.set("X-Naver-Client-Id", configPg.getNpayClientId());
        headers.set("X-Naver-Client-Secret", configPg.getNpayClientSecret());

        if ("json".equals(contentType)) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        } else {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }



        return headers;
    }

    /**
     * 결제승인
     * @param parameter
     * @param configPg
     * @return
     */
    public OrderPgData pay(MultiValueMap<String, Object> parameter, ConfigPg configPg) {

        OrderPgData orderPgData = new OrderPgData();
        orderPgData.setSuccess(false);
        orderPgData.setAmountModification(false);

        ResponseEntity<NaverApiResponse> response = null;

        try {
            response = naverPaymentRestTemplate.postForEntity(environment.getProperty("naver.pay.api.url") + "/" + configPg.getNpayPartnerId() + PAY_URL,
                    getMultiValueMapHttpEntity(parameter, configPg, "x-www-form-urlencoded"), NaverApiResponse.class);

        } catch (Exception e) {
            log.error("naverpay pay response error :[{}] {}", configPg.getNpayPartnerId(), e.getMessage(), e);
            orderPgData.setErrorMessage("네이버페이 파트너ID를 확인해 주십시오.");
            return orderPgData;
        }

        try {
            int orderPayAmount = Integer.parseInt(parameter.get("amount").get(0).toString());

            if (HttpStatus.OK == response.getStatusCode()) {
                // API에서 200이 아닌 경우에도 응답이 오는 지 확인하여 로직을 구현함.
                NaverApiResponse naverApiResponse = response.getBody();

                if ("Success".equals(naverApiResponse.getCode())) {
                    orderPgData.setSuccess(true);
                    orderPgData.setPgAuthCode(naverApiResponse.getMessage());
                    Detail detail = naverApiResponse.getBody().getDetail();
                    orderPgData.setOrderCode(detail.getMerchantPayKey());

                    if ("BANK".equals(detail.getPrimaryPayMeans())) {
                        orderPgData.setBankCode(detail.getBankCorpCode());
                    }

                    orderPgData.setPgServiceType("naverpay");
                    orderPgData.setPgServiceMid(configPg.getNpayClientId());
                    orderPgData.setPgServiceKey(configPg.getNpayClientSecret());
                    orderPgData.setPgAmount(detail.getTotalPayAmount());
                    orderPgData.setPartCancelFlag("Y");
                    orderPgData.setPgKey(detail.getPaymentId());
                    orderPgData.setPgProcInfo(this.makePgLog(naverApiResponse));
                    orderPgData.setPgPaymentType(detail.getPrimaryPayMeans());
                    orderPgData.setApprovalType(detail.getPrimaryPayMeans());
                    orderPgData.setPayDate(detail.getAdmissionYmdt());

                    if (orderPayAmount != detail.getTotalPayAmount()) {
                        orderPgData.setAmountModification(true);
                    }

                } else {
                    orderPgData.setErrorMessage("결제 요청 실패 : " + naverApiResponse.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("naverpay pay error : {}", e.getMessage(), e);
            orderPgData.setErrorMessage("네이버페이 결제 처리중 오류가 발생했습니다.");
        }

        return orderPgData;
    }

    private String makePgLog(NaverApiResponse naverApiResponse) {
        StringBuffer sb = new StringBuffer();

        String approvalType = naverApiResponse.getBody().getDetail().getPrimaryPayMeans();

        Map<String, String> responseMap = new HashMap<String, String>();
        responseMap.put("TID", naverApiResponse.getBody().getPaymentId());
        responseMap.put("AuthResultCode", naverApiResponse.getCode());
        responseMap.put("AuthResultMsg", naverApiResponse.getMessage());
        responseMap.put("PayMethod", naverApiResponse.getBody().getDetail().getPrimaryPayMeans());
        responseMap.put("Moid", naverApiResponse.getBody().getDetail().getMerchantPayKey());
        responseMap.put("Amt", naverApiResponse.getBody().getDetail().getTotalPayAmount() + "");
        responseMap.put("AuthDate", naverApiResponse.getBody().getDetail().getAdmissionYmdt());
        responseMap.put("CardCode", naverApiResponse.getBody().getDetail().getCardCorpCode());
        responseMap.put("CardQuota", naverApiResponse.getBody().getDetail().getCardInstCount() + "");
        responseMap.put("CardNo", naverApiResponse.getBody().getDetail().getCardNo());
        responseMap.put("BankCode", naverApiResponse.getBody().getDetail().getBankCorpCode());
        responseMap.put("BankAccountNo", naverApiResponse.getBody().getDetail().getBankAccountNo());


        String[] keys = new String[]{
                "TID", "AuthResultCode", "AuthResultMsg", "PayMethod", "Moid", "Amt", "AuthDate"
        };

        if (approvalType.equals("CARD")) {
            keys = new String[]{
                    "TID", "AuthResultCode", "PayMethod", "Moid",
                    "Amt", "AuthDate", "CardCode", "CardQuota", "CardNo"
            };
        } else if (approvalType.equals("BANK")) {
            keys = new String[]{
                    "TID", "AuthResultCode", "PayMethod", "Moid",
                    "Amt", "AuthDate", "BankCode", "BankAccountNo"
            };
        }

        for(String key : keys) {
            String value = responseMap.get(key);
            sb.append(key + " -> " + value + "\n");
        }

        return sb.toString();
    }

    /**
     * 결제취소
     * @param orderPgData
     * @param configPg
     * @return
     */
    public OrderPgData cancel(OrderPgData orderPgData, ConfigPg configPg) {

        Boolean isSuccess = false;

        MultiValueMap<String, Object> parameter = new LinkedMultiValueMap<String, Object>();
        parameter.add("paymentId", orderPgData.getPgKey());
        parameter.add("merchantPayKey", orderPgData.getOrderCode());
        parameter.add("cancelAmount", orderPgData.getCancelAmount());
        parameter.add("taxScopeAmount", orderPgData.getCancelAmount());
        parameter.add("taxExScopeAmount", 0);
        parameter.add("cancelReason", orderPgData.getCancelReason());

        if (UserUtils.isManagerLogin()) {
            parameter.add("cancelRequester", "2");
        } else {
            parameter.add("cancelRequester", "1");
        }

        ResponseEntity<NaverApiResponse> response = naverPaymentRestTemplate.postForEntity(environment.getProperty("naver.pay.api.url") + "/" + configPg.getNpayPartnerId() + CANCEL_URL,
                getMultiValueMapHttpEntity(parameter, configPg, "x-www-form-urlencoded"), NaverApiResponse.class);

        if (HttpStatus.OK == response.getStatusCode()) {
            // API에서 200이 아닌 경우에도 응답이 오는 지 확인하여 로직을 구현함.
            NaverApiResponse naverApiResponse = response.getBody();

            if ("Success".equals(naverApiResponse.getCode())) {
                isSuccess = true;
            } else {
                orderPgData.setErrorMessage(naverApiResponse.getMessage());
            }
        }

        orderPgData.setSuccess(isSuccess);

        return orderPgData;
    }

    /**
     * 포인트적립
     * @param parameter
     * @param configPg
     * @return
     */
    public NaverApiResponse point(MultiValueMap<String, Object> parameter, ConfigPg configPg) {

        ResponseEntity<NaverApiResponse> response = naverOtherRestTemplate.postForEntity(environment.getProperty("naver.pay.api.url") + "/" + configPg.getNpayPartnerId() + POINT_URL,
                getMultiValueMapHttpEntity(parameter, configPg, "x-www-form-urlencoded"), NaverApiResponse.class);

        NaverApiResponse naverApiResponse = null;
        if (HttpStatus.OK == response.getStatusCode()) {
            // API에서 200이 아닌 경우에도 응답이 오는 지 확인하여 로직을 구현함.
            naverApiResponse = response.getBody();

        }

        return naverApiResponse;
    }
}
