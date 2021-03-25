package saleson.api.nicepay;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import saleson.common.SalesonTest;
import saleson.common.config.HttpConnectionConfig;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.pg.NicepayServiceImpl;
import saleson.shop.receipt.support.CashbillResponse;
import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class NicepayServiceImplTest extends SalesonTest{

	private NicepayServiceImpl nicepayServiceImpl;

	@BeforeEach
	void setup() {
		MockEnvironment environment = new MockEnvironment();
        HttpConnectionConfig connectionConfig = new HttpConnectionConfig();
        RestTemplate restTemplate = connectionConfig.customRestTemplate();
		this.nicepayServiceImpl = new NicepayServiceImpl(restTemplate, environment);
	}

//    @Test
    void issue() {
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("TID", "nictest00m04012006011535011234");
        map.add("MID", "nictest00m");
        map.add("EdiDate", "20200601153501");
        map.add("Moid", "K1000017837");
        map.add("ReceiptAmt", "1200");
        map.add("GoodsName", "결제 테스트 상품 1200원");
        map.add("SignData", "96BDF4466E28A8F5AFE651D71A9F061CF4623267543DFA41435365950F0E16CA");
        map.add("ReceiptType", "1");
        map.add("ReceiptTypeNo", "01000001234");
        map.add("ReceiptSupplyAmt", "0");
        map.add("ReceiptVAT", "0");
        map.add("ReceiptServiceAmt", "0");
        map.add("ReceiptTaxFreeAmt", "0");
        map.add("CharSet", "UTF-8");

        CashbillResponse result = this.nicepayServiceImpl.issue(map);
        log.debug("{}", result);

        assertThat(result.getResultCode()).isEqualTo("A225");
    }

    //@Test
    void cancel() {
        MultiValueMap<String, Object> map= new LinkedMultiValueMap<>();
        map.add("TID", "nictest00m04012006011535011234");
        map.add("MID", "nictest00m");
        map.add("Moid", "K1000017837");
        map.add("CancelAmt", "1200");
        map.add("SupplyAmt", "0");
        map.add("GoodsVat", "1200");
        map.add("ServiceAmt", "1200");
        map.add("TaxFreeAmt", "1200");
        map.add("CancelMsg", "1200");
        map.add("PartialCancelCode", "1200");
        map.add("EdiDate", "20200601153501");
        map.add("SignData", "96BDF4466E28A8F5AFE651D71A9F061CF4623267543DFA41435365950F0E16CA");
        map.add("CharSet", "UTF-8");
        map.add("Editype", "KV");

        CashbillResponse result = this.nicepayServiceImpl.cancel(map);
        log.debug("{}", result);

        assertThat(result.getResultCode()).isEqualTo("A221");
    }


    @Test
    void vBankcancel() {
        String mid = "nictest00m";
        String orderCode = "K1000000908";
        String merchantKey = "33F49GnCMS1mFYlGXisbUDzVf2ATWCl9k3R++d5hDd3Frmuos/XLx8XhXpe+LDYAbpGKZYSwtlyyLOtS/8aD7A==";
        int cancelAmount = 100;
        String tid = "nictest00m03012103121358408780";

        OrderPgData pgData = new OrderPgData();
        pgData.setPgServiceKey(merchantKey);
        pgData.setPgServiceMid(mid);
        pgData.setOrderCode(orderCode);
        pgData.setCancelReason("가상계좌 환불 테스트");

        pgData.setPgKey(tid);
        pgData.setCancelAmount(cancelAmount);
        pgData.setReturnAccountNo("1002636647234");
        pgData.setReturnBankName("020");
        pgData.setBankInName("김하나");
        pgData.setPgPaymentType("vbank");

        boolean isSuccess = false;

        // 전체취소 테스트
        // isSuccess = this.nicepayServiceImpl.cancel(pgData);

        // 부분취소 테스트
        pgData.setReturnAccountNo("");
        pgData.setReturnBankName("");
        pgData.setBankInName("");
        OrderPgData orderPgData = this.nicepayServiceImpl.partCancel(pgData);
        isSuccess = orderPgData.isSuccess();

        log.debug("{}", isSuccess);
        assertThat(isSuccess).isEqualTo(true);
    }


	private RestTemplate getCustomRestTemplate() {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = null;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy)
					.build();

			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			CloseableHttpClient httpClient = HttpClients.custom()
					.setSSLSocketFactory(csf)
					.build();

			HttpComponentsClientHttpRequestFactory requestFactory =
					new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);

			return new RestTemplate(requestFactory);
		} catch (Exception e) {
			e.printStackTrace();

			return new RestTemplate();
		}
	}
}