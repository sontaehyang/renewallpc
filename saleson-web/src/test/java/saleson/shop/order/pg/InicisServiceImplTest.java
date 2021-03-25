package saleson.shop.order.pg;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import saleson.shop.order.domain.OrderPgData;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.support.OrderParam;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class InicisServiceImplTest {
	@Autowired
	@Qualifier(value = "inicisService")
	PgService inicisService;

	@Autowired
	OrderPaymentMapper orderPaymentMapper;

	//@Test
	public void cancelRequestTest() {

		// Or
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode("K2000008894");
		orderParam.setOrderSequence(0);
		orderParam.setPaymentSequence(1);

		// when
		OrderPgData orderPgData = orderPaymentMapper.getOrderPgDataByOrderParam(orderParam);

		assertThatThrownBy(() -> inicisService.cancel(orderPgData))
				.isInstanceOf(NullPointerException.class);
	}


	//@Test
	public void cancelTest() {
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode("K2000008894");
		orderParam.setOrderSequence(0);
		orderParam.setPaymentSequence(1);


		OrderPgData orderPgData = orderPaymentMapper.getOrderPgDataByOrderParam(orderParam);

		// 환불정보
		if (orderPgData != null) {
			orderPgData.setReturnAccountNo("209211879551");   // 환불계좌번호(숫자만입력)
			orderPgData.setReturnBankName("04");   // 환불계좌은행코드
			orderPgData.setReturnName("신극창");   // 환불계좌주명
		}
		assertThatNullPointerException()
				.isThrownBy(() -> inicisService.cancel(orderPgData));

	}


	//@Test
	public void partCancelTest() {
		OrderParam orderParam = new OrderParam();
		orderParam.setOrderCode("K2000008894");
		orderParam.setOrderSequence(0);
		orderParam.setPaymentSequence(1);


		OrderPgData orderPgData = orderPaymentMapper.getOrderPgDataByOrderParam(orderParam);

		if (orderPgData != null) {
			// 환불정보
			orderPgData.setReturnAccountNo("3333018280490");   // 환불계좌번호(숫자만입력)
			orderPgData.setReturnBankName("56");   // 환불계좌은행코드
			orderPgData.setReturnName("신극창");   // 환불계좌주명


			orderPgData.setMessage("취소합니다.");   // 취소사유
			orderPgData.setCancelAmount(1);    // 취소할 금액
			orderPgData.setRemainAmount(0);    // 부분취소
		}

		assertThatNullPointerException()
				.isThrownBy(() -> inicisService.partCancel(orderPgData));

		//assertThat(orderPgData.getPgKey(), is("INIMX_VBNKjubusstest20190221160342995585"));
	}

}