package saleson.shop.storeinquiry;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.shop.storeinquiry.domain.StoreInquiry;
import saleson.shop.storeinquiry.support.StoreInquiryParam;

import java.util.StringTokenizer;

@Controller
@RequestMapping("/opmanager/store-inquiry")
@RequestProperty(title="입첨문의", layout="default", template="opmanager")
public class StoreInquiryManagerController {

	@Autowired
	StoreInquiryService storeInquiryService;

	@Autowired
	FileService fileService;

	@Autowired
	SequenceService sequenceService;

	@GetMapping("/list")
	public String list (@ModelAttribute StoreInquiryParam storeInquiryParam, Model model) {

		Pagination pagination = Pagination.getInstance(storeInquiryService.getStoreInquiryCount(storeInquiryParam));
		storeInquiryParam.setPagination(pagination);

		model.addAttribute("list", storeInquiryService.getStoreInquiryList(storeInquiryParam));
		model.addAttribute("totalCount", storeInquiryParam.getPagination().getTotalItems());
		model.addAttribute("pagination",pagination);

		return "view:/store-inquiry/list";
	}

	@GetMapping("/detail/{storeInquiryId}")
	public String detail (@PathVariable("storeInquiryId") int storeInquiryId, Model model) {

		StoreInquiry storeInquiry = storeInquiryService.getStoreInquiryByFileName(storeInquiryId);
		String fileName = storeInquiry.getFileName();

		String extension = "";

		if(fileName != null && !"".equals(fileName)){
			StringTokenizer tokens = new StringTokenizer(fileName);
			String subFileName = tokens.nextToken(".");
			extension = tokens.nextToken(".");
		}

		model.addAttribute("extension", extension);
		model.addAttribute("storeInquiry", storeInquiryService.getStoreInquiry(storeInquiryId));
		return "view:/store-inquiry/form";
	}

	@PostMapping("/detail/{storeInquiryId}")
	public String update (StoreInquiry storeInquiry, Model model) {

		storeInquiryService.updateStoreInquiryStatus(storeInquiry);
		model.addAttribute("storeInquiry", storeInquiryService.getStoreInquiry(storeInquiry.getStoreInquiryId()));

		return "redirect:/opmanager/store-inquiry/detail/" + storeInquiry.getStoreInquiryId();
	}

}
