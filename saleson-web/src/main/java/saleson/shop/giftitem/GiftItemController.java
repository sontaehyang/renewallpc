package saleson.shop.giftitem;

import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.exception.GiftItemException;
import saleson.shop.item.ItemService;

@Controller
@RequestMapping("/gift-item")
@RequestProperty(title="사은품 정보", layout="sub")
public class GiftItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    GiftItemService giftItemService;

    @GetMapping("/list-popup/{itemUserCode}")
    @RequestProperty(layout = "base")
    public String listPopup(Model model,
                                @PathVariable("itemUserCode") String itemUserCode) {

        Integer itemId = itemService.getItemIdByItemUserCode(itemUserCode);

        if (itemId == null) {
            throw new GiftItemException("상품정보가 없습니다.");
        }

        try {

            model.addAttribute("list", giftItemService.getGiftItemListForFront(itemId));

        } catch (Exception e) {
            throw new GiftItemException("사은품 목록 처리에 문제가 발생 했습니다.");
        }

        return "view:/gift-item/list-popup";
    }

}
