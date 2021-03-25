package saleson.shop.giftitem;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seller/gift-item")
@RequestProperty(title="사은품 관리", layout="default", template="seller")
public class GiftItemSellerController extends GiftItemManagerController{
}
