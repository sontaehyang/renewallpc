package saleson.shop.order;

public interface OrderBatchService {
	/**
	 * 주문취소 요청을 처리한다 - Batch
	 * @param batchKey
	 * @return
	 */
	public void updateOrderCancelBatch();
	
	/**
	 * 환불 요청을 처리한다 - Batch
	 * @param batchKey
	 */
	public void updateOrderReturnBatch();
	
}
