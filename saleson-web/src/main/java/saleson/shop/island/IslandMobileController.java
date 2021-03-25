package saleson.shop.island;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import saleson.model.Island;

@Controller
@RequestMapping("/m/island")
@RequestProperty(template="mobile", layout="default")
public class IslandMobileController {
	private static final Logger log = LoggerFactory.getLogger(IslandMobileController.class);

	@Autowired
	private IslandService islandService;

	/**
	 * 제주 / 도서산간 팝업.
	 * @return
	 */
	@GetMapping("/island-popup")
	@RequestProperty(layout="base")
	public String islandPopup(Model model, IslandDto islandDto, @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable,
							  @RequestParam(value="code", required=false, defaultValue="") String itemUserCode) {

		Page<Island> pageContent = islandService.findAll(islandDto.getPredicate(), pageable);

		model.addAttribute("islandDto", islandDto);
		model.addAttribute("pageContent", pageContent);


		if (!StringUtils.isEmpty(itemUserCode)){
			model.addAttribute("itemUserCode", itemUserCode);
		}

		return "view:/island/island-popup";
	}

}
