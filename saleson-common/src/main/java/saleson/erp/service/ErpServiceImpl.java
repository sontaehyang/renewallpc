package saleson.erp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saleson.erp.domain.ErpMapper;
import saleson.erp.domain.HPR100T;
import saleson.erp.domain.OrderLine;
import saleson.erp.domain.OrderLineStatus;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.support.OrderException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("erpService")
public class ErpServiceImpl implements ErpService {
	private final ErpMapper erpMapper;
	private final ErpOrderMapper erpOrderMapper;

	public ErpServiceImpl(ErpMapper erpMapper, ErpOrderMapper erpOrderMapper) {
		this.erpMapper = erpMapper;
		this.erpOrderMapper = erpOrderMapper;
	}

	@Override
	public HPR100T findHPR100TByItemCode(String itemCode) {
		return erpMapper.findHPR100TByItemCode(itemCode);
	}

	@Override
	public List<OrderLine> validateOrderListGetForClaim(ErpOrder erpOrder) {
		List<OrderLine> orderLines = erpOrderMapper.mapFrom(erpOrder);
		long errorCount = orderLines.stream().filter(ol -> "00".equals(ol.getUniq().substring(14, 16))).count();

		orderLines.stream().forEach(oi -> log.debug("[saveOrderListGet] orderLine: {}", oi));

		if (errorCount > 0) {
			throw new OrderException("[ERP] Uniq값이 잘못 설정되었습니다. (상태코드:00)");
		}

		return orderLines;
	}

	@Override
	public void saveOrderListGet(ErpOrder erpOrder) {
		List<OrderLine> orderLines = erpOrderMapper.mapFrom(erpOrder);
		saveOrderListGetForErp(orderLines);
	}

	@Override
	public void saveOrderListGetAutoCancel(List<OrderLine> orderLines) {
		saveOrderListGetForErp(orderLines);
	}

	private void saveOrderListGetForErp(List<OrderLine> orderLines) {
		orderLines.stream().forEach(oi -> log.debug("[saveOrderListGet] orderLine: {}", oi));

		if (orderLines.isEmpty()) {
			log.error("[saveOrderListGet] 등록할 데이터가 없습니다: orderLines is empty");
		}

		try {
			erpMapper.saveOrderListGet(orderLines);
		} catch (Exception e) {
			log.error("[saveOrderListGet] erpMapper.saveOrderListGet(orderLines): {} ", e.getMessage(), e);
		}
	}

	@Override
	public List<OrderLineStatus> findOrderLineStatusAll(String uniq) {
		return findOrderLineStatusAll(Arrays.asList(uniq));
	}

	@Override
	public List<OrderLineStatus> findOrderLineStatusAll(List<String> uniqs) {
		if (uniqs.isEmpty()) {
			log.debug("[ERP SERVICE] 조회할 UNIQ 값이 필요합니다.");
			return new ArrayList<>();
		}

		return erpMapper.findOrderLineAll(uniqs);
	}

	@Override
	public List<OrderItem> findOrderItemAll(List<OrderItem> orderItems) {
		List<String> uniqs = orderItems.stream().map(oi -> oi.getUniq()).collect(Collectors.toList());
		return erpOrderMapper.map(findOrderLineStatusAll(uniqs), orderItems);
	}


	@Override
	public void updateApplyFlagIfOrderListPut(List<OrderItem> orderItems) {
		List<String> uniqs = orderItems.stream().map(oi -> oi.getUniq()).collect(Collectors.toList());
		erpMapper.updateApplyFlagIfOrderListPut(uniqs);
	}

}
