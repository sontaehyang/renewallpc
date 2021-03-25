package saleson.shop.seller;

import com.onlinepowers.framework.notification.message.OpMessage;
import com.onlinepowers.framework.notification.message.SmsMessage;
import com.onlinepowers.framework.notification.sms.SmsService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import saleson.seller.main.SellerService;
import saleson.seller.main.domain.Seller;
import saleson.shop.order.shipping.OrderShippingMapper;
import saleson.shop.order.support.OrderParam;

import java.util.List;

@Controller
@RequestMapping("/sellerSms")
@RequestProperty(title="임시 페이지", template="front", layout="base")
public class SellerSmsController {
	private static final Logger log = LoggerFactory.getLogger(SellerSmsController.class);

	@Autowired
	private SellerService sellerService;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired 
	private SmsService smsService;
	
	/**
	 * 판매자 신규주문 Sms 발송 임시
	 * @return
	 */
	
	@ResponseBody
	@GetMapping(value="demo")
	public String sendOrderSmsToSeller() {
		
		String Hour = DateUtils.getToday("HH");
		List<Seller> seller = sellerService.getSellerIdBySmsSendTime(Hour);
		
		for(int i = 0; i < seller.size(); i++){
			
			OrderParam orderParam = new OrderParam();
			orderParam.setSellerId(seller.get(i).getSellerId());
			orderParam.setConditionType("SELLER");
			
			int totalCount = orderShippingMapper.getNewOrderCountByParam(orderParam);
			
			if(totalCount > 0){
				
				try {
					
					String smsMessage = Hour + "시 기준 신규주문 " + totalCount + "건이 주문되었으니 확인 해 주시기 바랍니다.";
					OpMessage message = new SmsMessage(seller.get(i).getSecondPhoneNumber(), smsMessage, "");
					smsService.sendMessage(message); // SMS 발송.
					
				} catch (Exception e){
					log.error("ERROR: {}", e.getMessage(), e);
				
				}
			}
		}
		
		return "SUCCESS";
	}
}
