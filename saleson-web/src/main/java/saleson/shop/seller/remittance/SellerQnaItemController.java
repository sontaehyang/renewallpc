package saleson.shop.seller.remittance;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.common.utils.SellerUtils;
import saleson.shop.qna.QnaItemManagerController;
import saleson.shop.qna.support.QnaParam;

@Controller
@RequestMapping("/seller/qna-item")
@RequestProperty(title = "고객센터", template="seller", layout = "default")
public class SellerQnaItemController extends QnaItemManagerController {
	
	/**
	 * QNA ITEM
	 * @param qnaParam
	 * @param model
	 * @return
	 */
	@GetMapping("list")
	public String answerList(QnaParam qnaParam, Model model) {
		qnaParam.setSellerId(SellerUtils.getSellerId());
		return super.answerList(qnaParam, model);
	}
	
	@PostMapping("delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {
		return super.deleteListData(requestContext, listParam);
	}
}
