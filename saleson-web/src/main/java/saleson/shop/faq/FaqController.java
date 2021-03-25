package saleson.shop.faq;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.model.Faq;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/faq")
@RequestProperty(title="FAQ", layout="customer")
public class FaqController {

	@Autowired
	FaqService faqService;

	@Autowired
	EnumMapper enumMapper;

	@GetMapping
	public String index(FaqDto faqDto,
						@PageableDefault(size = 100, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
						Model model) {


		Page<Faq> pageContent = faqService.findAll(faqDto.getPredicate(), pageable);

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("faqTypes", enumMapper.get("FaqType"));

		return "view:/faq/list";
	}


	/**
	 * 조회수 증가.
	 * @param id
	 * @return
	 */
	@PostMapping("/{id}/hit")
	public JsonView updateHit(@PathVariable Long id, RequestContext requestContext) {
		if (!requestContext.isAjaxRequest()) {
			throw new NotAjaxRequestException();
		}

		try {
			Optional<Faq> optionalFaq = faqService.findById(id);

			if (!optionalFaq.isPresent()) {
				return JsonViewUtils.failure("데이터가 존재하지 않음.");
			}
			Faq faq = optionalFaq.get();
			faq.setHit(faq.getHit() + 1);

			faqService.save(faq);

			return JsonViewUtils.success();

		} catch (Exception e) {
			log.error("[FAQ] {}", e.getMessage());

			return JsonViewUtils.failure("오류발생!");
		}
	}

}


