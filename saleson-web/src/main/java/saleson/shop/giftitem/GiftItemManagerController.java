package saleson.shop.giftitem;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.servlet.view.JsonView;
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
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.GiftItem;
import saleson.model.GiftItemLog;
import saleson.seller.main.SellerService;
import saleson.seller.main.support.SellerParam;
import saleson.shop.giftitem.support.GiftItemDto;
import saleson.shop.giftitem.support.GiftItemLogDto;

@Controller
@RequestMapping("/opmanager/gift-item")
@RequestProperty(title="사은품 관리", layout="default", template="opmanager")
public class GiftItemManagerController {
	private static final Logger log = LoggerFactory.getLogger(GiftItemManagerController.class);

    @Autowired
    private GiftItemService giftItemService;

    @Autowired
    private SellerService sellerService;

    @Autowired
	ModelMapper modelMapper;

    /**
     * 사은품 목록
     * @param model
     * @param giftItemDto
     * @param pageable
     * @throws Exception
     */
    private void setBaseModelForGiftItemList(Model model, GiftItemDto giftItemDto, Pageable pageable) throws Exception{

        boolean isSellerPage = ShopUtils.isSellerPage();

        if (isSellerPage) {
            giftItemDto.setSellerId(SellerUtils.getSellerId());
        }

        Page<GiftItem> pageContent =  giftItemService.getGiftItemList(giftItemDto.getPredicate(), pageable);

        model.addAttribute("isSellerPage", isSellerPage);

        model.addAttribute("pageContent", pageContent);
        model.addAttribute("giftItemDto", giftItemDto);
        model.addAttribute("sellerList", sellerService.getSellerListByParam(new SellerParam()));

    }

    @GetMapping(value = "/list")
    public String list(Model model, GiftItemDto giftItemDto,
                       @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable) {

        try {

            setBaseModelForGiftItemList(model, giftItemDto, pageable);

        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            throw new GiftItemException("사은품 목록 처리에 문제가 발생 했습니다.");
        }

        return "view";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {

        model.addAttribute("mode", "create");
        model.addAttribute("seller", sellerService.getSellerById(SellerUtils.getSellerId()));
        model.addAttribute("giftItem", new GiftItem());

        return "view";
    }

    @PostMapping("/create")
    public String createAction(RequestContext requestContext,
                               Model model, GiftItemDto giftItemDto) {

        try {
        	GiftItem giftItem = modelMapper.map(giftItemDto, GiftItem.class);
            giftItemService.insertGiftItem(giftItem);
            FlashMapUtils.setMessage("등록되었습니다");
        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            FlashMapUtils.setMessage("사은품 등록에 실패 했습니다.");
        }

        return "redirect:"+requestContext.getManagerUri()+"/gift-item/list";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(RequestContext requestContext, Model model,
                       @PathVariable("id") long id) {


        try {
            GiftItem giftItem = giftItemService.getGiftItemById(id);

            if (isSellerGitfItem(giftItem)) {
                throw new GiftItemException();
            }

            model.addAttribute("seller", sellerService.getSellerById(giftItem.getSellerId()));
            model.addAttribute("mode", "edit");
            model.addAttribute("giftItem", giftItem);


            return "view";
        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            FlashMapUtils.setMessage("사은품 정보가 없습니다.");
            return "redirect:"+requestContext.getManagerUri()+"/gift-item/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editAction(RequestContext requestContext, Model model,
                             @PathVariable("id") long id,
                             GiftItemDto giftItemDto) {

        try {

            GiftItem orgGiftItem = giftItemService.getGiftItemById(id);

            if (isSellerGitfItem(orgGiftItem)) {
                throw new GiftItemException();
            }

            if (!orgGiftItem.getId().equals(giftItemDto.getId())) {
                throw new GiftItemException();
            }

            GiftItem giftItem = modelMapper.map(giftItemDto, GiftItem.class);

            giftItemService.updateGiftItem(giftItem);

            FlashMapUtils.setMessage("수정 되었습니다");
            return "redirect:"+requestContext.getManagerUri()+"/gift-item/list";
        } catch (Exception e) {

			log.error("ERROR: {}", e.getMessage(), e);

            FlashMapUtils.setMessage("사은품 수정에 실패 했습니다.");

            return "redirect:"+requestContext.getManagerUri()+"/gift-item/edit/"+id;
        }

    }

    @PostMapping("/delete")
    public String delete(RequestContext requestContext, ListParam listParam) {

        try {

            giftItemService.deleteGiftItem(listParam);

            return "redirect:"+requestContext.getManagerUri()+"/gift-item/list";

        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);

            FlashMapUtils.setMessage("사은품 삭제에 실패 했습니다.");

            return "redirect:"+requestContext.getManagerUri()+"/gift-item/list";
        }

    }

    @PostMapping("/delete-image")
    @RequestProperty(layout = "blank")
    public JsonView deleteItemImage(RequestContext requestContext,
                                    @RequestParam(name = "id") long id) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        try {

            GiftItem giftItem = giftItemService.getGiftItemById(id);

            if (isSellerGitfItem(giftItem)) {
                throw new GiftItemException();
            }

            giftItemService.deleteGiftItemImage(id);

            return JsonViewUtils.success();
        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            return JsonViewUtils.exception("사은품 이미지 삭제에 실패 했습니다.");
        }

    }

    @RequestProperty(layout="log")
    @GetMapping("/popup/log/{id}")
    public String logList(Model model, @PathVariable("id") long id,
                          GiftItemLogDto giftItemLogDto,
                          @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable) {

        try {

            giftItemLogDto.setGiftItemId(id);

            GiftItem giftItem = giftItemService.getGiftItemById(id);

            Page<GiftItemLog> pageContent =  giftItemService.getGiftItemLogList(giftItemLogDto.getPredicate(), pageable);

            model.addAttribute("pageContent", pageContent);
            model.addAttribute("giftItemDto", giftItemLogDto);
            model.addAttribute("giftItem", giftItem);

        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            throw new GiftItemException("사은품 목록 처리에 문제가 발생 했습니다.");
        }

        return "view:/gift-item/popup/log-list";
    }

    @RequestProperty(layout="base")
    @GetMapping(value = "/find-item")
    public String findItem(Model model, GiftItemDto giftItemDto,
                           @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable) {

        try {
            setBaseModelForGiftItemList(model, giftItemDto, pageable);

        } catch (Exception e) {
			log.error("ERROR: {}", e.getMessage(), e);
            throw new GiftItemException("사은품 목록 처리에 문제가 발생 했습니다.");
        }

        return "view";
    }

    private boolean isSellerGitfItem(GiftItem giftItem) {
        return ShopUtils.isSellerPage() && (giftItem.getSellerId() != SellerUtils.getSellerId());
    }
}
