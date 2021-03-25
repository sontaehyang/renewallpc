package saleson.shop.island;

import com.onlinepowers.framework.context.util.RequestContextUtils;
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
import saleson.model.Island;

@Controller
@RequestMapping("/island")
@RequestProperty(title="제주 / 도서산간관련", layout="sub")
public class IslandController {
	private static final Logger log = LoggerFactory.getLogger(IslandController.class);
	
	@Autowired
	private IslandService islandService;
	
	/**
	 * 제주 / 도서산간 팝업.
	 * @return
	 */
	@GetMapping("/island-popup")
	public String islandPopup(Model model, IslandDto islandDto,
							  @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
		RequestContextUtils.setLayout("base");

		Page<Island> pageContent = islandService.findAll(islandDto.getPredicate(), pageable);

		model.addAttribute("pageContent", pageContent);

		//model.addAttribute("mode", mode);
		model.addAttribute("islandDto", islandDto);

		return "view:/island/island-popup";
	}
	
}
