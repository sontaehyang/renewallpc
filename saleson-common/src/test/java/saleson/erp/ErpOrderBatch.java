package saleson.erp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.SalesonTest;
import saleson.shop.order.OrderService;

@Slf4j
public class ErpOrderBatch extends SalesonTest {
	@Autowired
	private OrderService orderService;

	@Test
	void erpOrderStatusBatchTest() {
		orderService.erpOrderStatusBatch();
	}
}
