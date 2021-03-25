package saleson.erp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import saleson.erp.domain.ErpOrderType;
import saleson.erp.domain.OrderLineStatus;
import saleson.shop.order.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ErpOrderMapperTest {


	@Test
	void mapTest() {
		ErpOrderMapper erpOrderMapper = new ErpOrderMapper();


		List<OrderItem> orderItems = getOrderItem("K00000001", 5);
		List<OrderLineStatus> orderLineStatus = getOrderLineStatus("K00000001", 10);


		List<OrderItem> results = erpOrderMapper.map(orderLineStatus, orderItems);

		results.stream().forEach(o -> {
			log.debug("------------------------------------------");
			log.debug("orderCode: {}", o.getOrderCode());
			log.debug("itemSquence: {}", o.getItemSequence());
			log.debug("orderStatus: {}", o.getOrderStatus());
			log.debug("deliveryCompanyId: {}", o.getDeliveryCompanyId());
			log.debug("deliveryCompanyName: {}", o.getDeliveryCompanyName());
			log.debug("deliveryNumber: {}", o.getDeliveryNumber());
		});

		assertThat(results.size()).isEqualTo(5);

	}

	private List<OrderItem> getOrderItem(String orderCode, int count) {
		List<OrderItem> orderItems = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderCode(orderCode);
			orderItem.setOrderSequence(0);
			orderItem.setItemSequence(i);
			orderItem.setOrderStatus("10");


			orderItems.add(orderItem);
		}
		return orderItems;
	}


	private List<OrderLineStatus> getOrderLineStatus(String orderCode, int count) {
		List<OrderLineStatus> orderItems = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			OrderLineStatus orderItem = new OrderLineStatus();

			orderItem.setUniq(orderCode + "0" + i);
			orderItem.setOrdStatus("배송준비중");

			if (i % 2 == 0) {
				orderItem.setOrdStatus("배송중");
				orderItem.setCarrName("CJ대한통운");
				orderItem.setCarrNo(4);
				orderItem.setInvoiceNo("23423423" + i);
			}

			orderItems.add(orderItem);
		}
		return orderItems;
	}

	@Test
	void erpOrderType() {

		assertThat(ErpOrderType.ORDER_BANK == ErpOrderType.ORDER_BANK).isTrue();
		assertThat(ErpOrderType.ORDER_BANK == ErpOrderType.ORDER).isFalse();

	}

}