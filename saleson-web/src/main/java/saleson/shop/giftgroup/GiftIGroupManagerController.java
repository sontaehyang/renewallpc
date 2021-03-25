package saleson.shop.giftgroup;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.exception.GiftItemException;
import saleson.model.GiftGroup;
import saleson.shop.giftgroup.support.GiftGroupDto;

@Controller
@RequestMapping("/opmanager/gift-group")
@RequestProperty(title="사은품 그룹 관리", layout="default", template="opmanager")
public class GiftIGroupManagerController {

	private static final Logger log = LoggerFactory.getLogger(GiftIGroupManagerController.class);


    @Autowired
    private GiftGroupService giftGroupService;

    @Autowired
	private ModelMapper modelMapper;

    @GetMapping(value = "/list")
    public String list(Model model, GiftGroupDto giftGroupDto,
                       @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable) {

        try {

            Page<GiftGroup> pageContent =  giftGroupService.getGiftGroupList(giftGroupDto.getPredicate(), pageable);

            model.addAttribute("pageContent", pageContent);
            model.addAttribute("giftGroupDto", giftGroupDto);

        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            throw new GiftItemException("사은품 그룹 목록 처리에 문제가 발생 했습니다.");
        }

        return "view";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {

        model.addAttribute("mode", "create");
        model.addAttribute("giftGroup", new GiftGroup());

        return "view";
    }

    @PostMapping("/create")
    public String createAction(GiftGroupDto giftGroupDto) {

        try {
        	GiftGroup giftGroup = modelMapper.map(giftGroupDto, GiftGroup.class);
            giftGroupService.insertGiftGroup(giftGroup);
            FlashMapUtils.setMessage("등록되었습니다");
        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            FlashMapUtils.setMessage("사은품 그룹 등록에 실패 했습니다.");
        }

        return "redirect:/opmanager/gift-group/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(RequestContext requestContext, Model model,
                       @PathVariable("id") long id) {

        try {
            GiftGroup giftGroup = giftGroupService.getGiftGroupById(id);

            model.addAttribute("mode", "edit");
            model.addAttribute("giftGroup", giftGroup);

            return "view";
        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            FlashMapUtils.setMessage("사은품 정보가 없습니다.");
            return "redirect:"+requestContext.getManagerUri()+"/gift-group/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editAction(RequestContext requestContext,
                             @PathVariable("id") long id,
                             GiftGroupDto giftGroupDto) {

        try {
            GiftGroup orgGiftGroup = giftGroupService.getGiftGroupById(id);

            if (!orgGiftGroup.getId().equals(giftGroupDto.getId())) {
                throw new GiftItemException();
            }

			GiftGroup giftGroup = modelMapper.map(giftGroupDto, GiftGroup.class);
            giftGroupService.updateGiftGroup(giftGroup);

            FlashMapUtils.setMessage("수정 되었습니다");
            return "redirect:"+requestContext.getManagerUri()+"/gift-group/list";
        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

            FlashMapUtils.setMessage("사은품 수정에 실패 했습니다.");

            return "redirect:"+requestContext.getManagerUri()+"/gift-group/edit/"+id;
        }

    }

    @PostMapping("/delete")
    public String delete(ListParam listParam) {

        try {

            giftGroupService.deleteGiftGroup(listParam);

            return "redirect:/opmanager/gift-group/list";

        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

            FlashMapUtils.setMessage("사은품 삭제에 실패 했습니다.");

            return "redirect:/opmanager/gift-group/list";
        }

    }
}
