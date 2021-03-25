package saleson.shop.seller.remittance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import saleson.shop.qna.QnaManagerController;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;

@Controller
@RequestMapping("/seller/qna")
@RequestProperty(title = "고객센터", template="seller", layout = "default")
public class SellerQnaController extends QnaManagerController {

}
