package saleson.erp.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.SalesonTest;
import saleson.erp.service.ErpOrder;
import saleson.erp.service.ErpService;
import saleson.shop.item.domain.Item;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.Buyer;
import saleson.shop.order.domain.ItemPrice;
import saleson.shop.order.domain.OrderShippingInfo;
import saleson.user.domain.MigrationMapper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ErpServiceImplTest extends SalesonTest {
	@Autowired
	private ErpMapper erpMapper;


	@Autowired
	private ErpService erpService;

	@Test
	public void test() {
		long a = erpMapper.test();
		log.error("{}", a);

	}

	@Test
	public void getHPR100TTest() {
		String code = "APIM11001U";

		HPR100T hpr100t = erpService.findHPR100TByItemCode(code);

		log.debug("ERP 상품 조회 : {}", hpr100t);

		assertThat(hpr100t).isNotNull();
		assertThat(hpr100t.getItemCode()).isEqualTo(code);
		assertThat(hpr100t.getItemName()).isEqualTo("아이맥 21.5인치 2011");
	}

	@Test
	public void saveOrderListGetTest() {
		String orderCode = "K000000001235";


		ErpOrder erpOrder = new ErpOrder();

		Buyer buyer = new Buyer();
		buyer.setUserName("신극창");
		buyer.setLoginId("skc@onlinepwoers.com");

		erpOrder.setBuyer(buyer);


		erpOrder.addBuyItem(getBuyItem(orderCode, 1));
		erpOrder.addBuyItem(getBuyItem(orderCode, 2));


		erpOrder.addOrderShippingInfo(getOrderShippingInfo(orderCode, 1));
		erpOrder.addOrderShippingInfo(getOrderShippingInfo(orderCode, 2));

		erpService.saveOrderListGet(erpOrder);
	}

	private BuyItem getBuyItem(String orderCode, int itemSequence) {
		BuyItem buyItem = new BuyItem();
		buyItem.setOrderCode(orderCode);
		buyItem.setOrderSequence(0);
		buyItem.setItemSequence(itemSequence);
		buyItem.setShippingSequence(itemSequence);
		buyItem.setShippingInfoSequence(itemSequence);
		buyItem.setItemCode("G000000" + itemSequence);


		buyItem.setItem(new Item());
		buyItem.setItemPrice(new ItemPrice());

		return buyItem;
	}

	private OrderShippingInfo getOrderShippingInfo(String orderCode, int shippingInfoSequence) {
		OrderShippingInfo osi = new OrderShippingInfo();

		osi.setReceiveName("신극창" + shippingInfoSequence);
		osi.setMemo("메모입니다." + shippingInfoSequence);
		osi.setReceiveAddress("서울 구로구 디지털로 27길" + shippingInfoSequence);
		osi.setReceiveAddressDetail("711호 " + shippingInfoSequence );
		osi.setOrderCode(orderCode);
		osi.setShippingInfoSequence(shippingInfoSequence);

		return osi;
	}


	static Collection<List<Integer>> partitionIntegerListBasedOnSize(List<Integer> inputList, int size) {
		return inputList.stream()
				.collect(Collectors.groupingBy(s -> (s-1)/size))
				.values();
	}
	static <T> Collection<List<T>> partitionBasedOnSize(List<T> inputList, int size) {
		final AtomicInteger counter = new AtomicInteger(0);
		return inputList.stream()
				.collect(Collectors.groupingBy(s -> counter.getAndIncrement()/size))
				.values();
	}
	static <T> Collection<List<T>> partitionBasedOnCondition(List<T> inputList, Predicate<T> condition) {
		return inputList.stream().collect(Collectors.partitioningBy(s-> (condition.test(s)))).values();
	}
}