package saleson.shop.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;

@Controller
@RequestMapping("/seller/order")
@RequestProperty(title="주문관리", layout="default", template="seller")
public class SellerOrderController extends OrderManagerController {

}
