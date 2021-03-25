package saleson.shop.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("orderBatchService")
public class OrderBatchServiceImpl implements OrderBatchService {
	private static final Logger log = LoggerFactory.getLogger(OrderBatchServiceImpl.class);

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	Environment environment;

	@Override
	//@Scheduled(fixedDelay=200000)
	public void updateOrderCancelBatch() {
		
		/*
		checkSchedulingExcutionServer();
		log.debug("");
		log.debug("####################################################");
		log.debug("#####      [BATCH] updateOrderCancelBatch      #####");
		log.debug("####################################################");
		log.debug("");
		boolean isError = false;
		String batchKey = "CANCEL-" +  DateUtils.getToday(Const.DATETIME_FORMAT);
		
		int targetCount = orderMapper.insertOrderCancelTarget(batchKey);
		if (targetCount == 0) {
			return;
		}
		
		try {
			
			OrderCancelApply cancelApply = orderMapper.getOrderCancelBatchTargetByBatchKey(batchKey);
			if (cancelApply == null) {
				return;
			}
			
			orderService.orderCancelBatchProcess(cancelApply);
			
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			HashMap<String, Object> map = new HashMap<>();
			map.put("batchKey", batchKey);
			map.put("errorMessage", e.getMessage() + "\n" + e.getStackTrace());
			orderMapper.updateBatchTargetErrorMessage(map);
			
			isError = true;
			
		} finally {
			
			if (!isError) {
				orderMapper.deleteBatchTarget(batchKey);
			}
			
		}
		
		// 에러가 발생하면 잠깐 멈췄다가 실행함
		if (isError) {
			
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				log.error("ERROR: {}", e.getMessage(), e);
			}
			
		}
		
		this.updateOrderCancelBatch();
		*/
	}
	
	@Override
	//@Scheduled(fixedDelay=200000,initialDelay=100000)
	public void updateOrderReturnBatch() {
		/*
		checkSchedulingExcutionServer();
		
		log.debug("");
		log.debug("####################################################");
		log.debug("#####      [BATCH] updateOrderReturnBatch      #####");
		log.debug("####################################################");
		log.debug("");
		boolean isError = false;
		String batchKey = "RETURN-" +  DateUtils.getToday(Const.DATETIME_FORMAT);
		
		int targetCount = orderMapper.insertOrderReturnTarget(batchKey);
		if (targetCount == 0) {
			return;
		}
		
		try {
			//batchKey = "RETURN-20150921133930";
			OrderReturnApply returnApply = orderMapper.getOrderReturnBatchTargetByBatchKey(batchKey);
			if (returnApply == null) {
				return;
			}
			
			orderService.orderReturnBatchProcess(returnApply);
			
		} catch(Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
			HashMap<String, Object> map = new HashMap<>();
			map.put("batchKey", batchKey);
			map.put("errorMessage", e.getMessage() + "\n" + e.getStackTrace());
			orderMapper.updateBatchTargetErrorMessage(map);
			
			isError = true;
			
		} finally {
			
			if (!isError) {
				orderMapper.deleteBatchTarget(batchKey);
			}
			
		}
		
		// 에러가 발생하면 잠깐 멈췄다가 실행함
		if (isError) {
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				log.error("ERROR: {}", e.getMessage(), e);
				
			}
		}
		
		this.updateOrderReturnBatch();
		*/
	}

}
