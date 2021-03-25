package saleson.erp.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ErpOrderStatusTest {

	@Test
	void test() {

		for (ErpOrderStatus a : ErpOrderStatus.values() ) {
			log.debug("a : {}", a);
		}

		Optional<ErpOrderStatus> bb = Arrays.stream(ErpOrderStatus.values())
				.filter(e -> e.isEqualToErpCode("xx"))
				.findFirst();

		log.debug("bb : {}", bb.isPresent());


		log.debug("values: {}", ErpOrderStatus.values());
		log.debug("equals : {}", ErpOrderStatus.ORDERED.isEqualToErpCode("주문완료2"));
		;
	}

}