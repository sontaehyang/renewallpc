package saleson.shop.order.support;

import lombok.Data;
import lombok.ToString;
import saleson.shop.order.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

@Data @ToString
public class BatchKey {
	private String batchKey;
	private String orderStatus;

	private List<OrderItem> orderItems = new ArrayList<>();

	public BatchKey(String batchKey, String orderStatus) {
		this.batchKey = batchKey;
		this.orderStatus = orderStatus;
	}
}
