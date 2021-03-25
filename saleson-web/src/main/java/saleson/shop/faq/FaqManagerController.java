package saleson.shop.faq;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.utils.Message;
import saleson.model.Faq;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/opmanager/faq")
@RequestProperty(template = "opmanager", layout="default")
public class FaqManagerController {

	@Autowired
	private FaqService faqService;
	
	@Autowired
	EnumMapper enumMapper;

	@Autowired
	FaqValidator validator;

	@Autowired
	ModelMapper modelMapper;
	
	
	/**
	 * FAQ 리스트
	 * @param faqDto
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@ModelAttribute FaqDto faqDto,
						  @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
						  Model model) {

		Page<Faq> pageContent = faqService.findAll(faqDto.getPredicate(), pageable);

		model.addAttribute("pageContent", pageContent);
		model.addAttribute("faqTypes", enumMapper.get("FaqType"));

		return "view";
	}
	
	/**
	 * FAQ 등록
	 * @param model
	 * @return
	 */
	@GetMapping(value="/create")
	public String create(Model model) {
		
		model.addAttribute("faq", new Faq());
		model.addAttribute("faqTypes", enumMapper.get("FaqType"));
		
		return "view";
	}
	
	/**
	 * FAQ 등록처리
	 * @param faqDto
	 * @return
	 */
	@PostMapping("/create")
	public String create(@Valid FaqDto faqDto, Errors errors, Model model) {
		model.addAttribute("faqTypes", enumMapper.get("FaqType"));

		// 1. 데이터 처리
		Faq faq = modelMapper.map(faqDto, Faq.class);

		// 2. 기본 입력 값 검증
		if (hasError(faqDto, errors, faq, model)) {
			return "view";
		}

		// 3. 데이터 저장
		faqService.save(faq);


		FlashMapUtils.setMessage("등록되었습니다.");
		return "redirect:/opmanager/faq/list";
	}
	
	/**
	 * FAQ 수정
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping(value="edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {

		// 1. 데이터 조회
		Faq faq = faqService.findById(id)
				.orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/faq/list"));

		// 2. Model data
		model.addAttribute("faq", faq);
		model.addAttribute("faqTypes", enumMapper.get("FaqType"));

		return "view";
	}


	/**
	 * 수정 처리
	 * @param id
	 * @param faqDto
	 * @param errors
	 * @param model
	 * @return
	 */
	@PostMapping(value="edit/{id}")
	public String edit(@PathVariable("id") Long id,
					   @RequestParam(value = "url", required = false) String url,
					   @Valid FaqDto faqDto,
					   Errors errors,
					   Model model) {
		// 0. 공통 데이터
		model.addAttribute("faqTypes", enumMapper.get("FaqType"));

		// 1. 데이터 조회
		Faq faq = faqService.findById(id)
				.orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/faq/list"));

		// 2. 기본 입력 값 검증
		if (hasError(faqDto, errors, faq, model)) {
			return "view";
		}

		// 3. 데이터 저장
		modelMapper.map(faqDto, faq);
		faqService.save(faq);


		FlashMapUtils.setMessage("수정되었습니다.");


		// 4. return url
		StringBuilder sb = new StringBuilder()
				.append("redirect:/opmanager/faq/edit/")
				.append(id);

		if (url != null) {
			sb.append("?url=").append(url);
		}

		return sb.toString();
	}


	/**
	 * FAQ 데이터 삭제
	 * @param requestContext
	 * @param listParam
	 * @return
	 */
	@PostMapping(value="delete")
	public JsonView deleteListData(RequestContext requestContext, ListParam listParam) {

		if (!requestContext.isAjaxRequest()) { 
		    throw new NotAjaxRequestException();
		}

		try {
			faqService.deleteListData(listParam);

		} catch (Exception e) {
			return JsonViewUtils.failure(e.getMessage());

		}

		return JsonViewUtils.success();  
	}


	/**
	 * 데이터 검증
	 * @param faqDto
	 * @param errors
	 * @param faq
	 * @param model
	 * @return
	 */
	private boolean hasError(FaqDto faqDto, Errors errors, Faq faq, Model model) {
		// 1. 기본 입력 값 검증
		if (errors.hasErrors()) {
			Message.set(errors);
			model.addAttribute("faq", faq);
			return true;
		}

		// 2. 추가 로직 검증
		validator.validate(faqDto, errors);
		if (errors.hasErrors()) {
			Message.set(errors);
			model.addAttribute("faq", faq);
			return true;
		}
		return false;
	}
}
