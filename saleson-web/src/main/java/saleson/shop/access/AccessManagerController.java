package saleson.shop.access;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.common.utils.UserUtils;
import saleson.shop.access.domain.Access;
import saleson.shop.access.support.AccessParam;

import java.util.HashMap;
import java.util.List;
//import saleson.shop.isms.domain.Isms;

//import saleson.shop.isms.IsmsService;

@Controller
@RequestMapping("/opmanager/access/")
@RequestProperty(layout="default")
public class AccessManagerController {

	@Autowired
	AccessService accessService;

	/*
	@Autowired
	private IsmsService ismsService;
*/

	/**
	 * 접속 ip 리스트
	 * @return
	 */
	@GetMapping("list")
	@RequestProperty(title="접속IP관리")
	public String list(AccessParam accessParam, Model model) {

		accessParam.setDisplayFlag("Y");
		int count = accessService.getAllowIpCount(accessParam);

		Pagination pagination = Pagination.getInstance(count, 10);
		accessParam.setPagination(pagination);

		List<Access> list = accessService.getAllowIpList(accessParam);

		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		model.addAttribute("count", count);

		return ViewUtils.view();
	}

	/**
	 * 접속 ip 등록
	 * @return
	 */
	@GetMapping("write")
	@RequestProperty(title="접속IP관리")
	public String write(Access access, Model model){

		model.addAttribute("access", access);
		return ViewUtils.view();
	}

	/**
	 * 접속 ip 등록
	 * @return
	 */
	@PostMapping("write")
	public String writeAction(Access access) {

		try {

			accessService.insertAllowIp(access);
			FlashMapUtils.alert("접속 IP가 등록되었습니다.");

		} catch (Exception e) {
			FlashMapUtils.alert(e.getLocalizedMessage());
		}

		return ViewUtils.redirect("/opmanager/access/list");
	}

	/**
	 * 접속 ip 삭제 처리
	 * @return
	 */
	@PostMapping("delete")
	public JsonView delete(RequestContext requestContext, @RequestParam("id") int allowIpId) {

		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		try {

			HashMap<String, Object> params = new HashMap<>();
			params.put("allowIpId", allowIpId);
			params.put("managerId", UserUtils.getManagerId());

			accessService.deleteAllowIp(params);
			return JsonViewUtils.success("접속 IP가 삭제되었습니다.");

		} catch (Exception e) {

			return JsonViewUtils.failure(e.getLocalizedMessage());
		}

	}

}
