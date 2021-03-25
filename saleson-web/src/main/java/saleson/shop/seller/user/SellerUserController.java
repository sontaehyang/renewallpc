package saleson.shop.seller.user;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.seller.user.SellerUserService;
import saleson.shop.user.support.UserSearchParam;

@Controller
@RequestMapping("/seller/user")
@RequestProperty(template="seller", layout="default")
public class SellerUserController {

    private static final Logger log = LoggerFactory.getLogger(SellerUserController.class);

    @Autowired
    private SellerUserService sellerUserService;

    @GetMapping("list")
    public String list(Model model, UserSearchParam userSearchParam) {

        if (UserUtils.isSellerLogin() && !UserUtils.isSellerMasterUser()) {
            return "redirect:/seller/user/edit/"+UserUtils.getSellerUserId();
        }

        userSearchParam.setSellerId(SellerUtils.getSellerId());

        int totalCount = sellerUserService.getSellerUserListCount(userSearchParam);

        Pagination pagination
                = Pagination.getInstance(totalCount);


        userSearchParam.setPagination(pagination);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pagination",pagination );
        model.addAttribute("list", sellerUserService.getSellerUserList(userSearchParam));
        model.addAttribute("searchParam", userSearchParam);

        return "view:/seller/user/user-list";
    }

    @GetMapping("create")
    public String create (Model model) {

        if (UserUtils.isSellerLogin() && !UserUtils.isSellerMasterUser()) {
            return "redirect:/seller/user/edit/"+UserUtils.getSellerUserId();
        }

        model.addAttribute("user", new User());
        model.addAttribute("formAction", "create");

        return "view:/seller/user/user-form";
    }

    @PostMapping("create")
    public String createAction (User user) {

        String message;

        try {
            int count = sellerUserService.insertSellerUser(SellerUtils.getSellerId(), user);
            message = count > 0 ? "판매자 등록에 성공 했습니다." : "판매자 등록에 실패 했습니다.";
        } catch (Exception e) {
            message = "판매자 등록에 실패 했습니다.";
            log.error("ERROR: {}", e.getMessage(), e);
        }

        FlashMapUtils.alert(message);
        return "redirect:/seller/user/list";
    }

    @PostMapping("duplicate-user")
    public JsonView findUser(@RequestParam("loginId") String loginId) {

        boolean flag = false;

        try {
            flag = sellerUserService.isDuplicateSellerUserByLoginId(loginId);
        } catch (Exception e) {
            flag = false;
            log.error("ERROR: {}", e.getMessage(), e);
        }

        return JsonViewUtils.success(flag);
    }

    @GetMapping("edit/{id}")
    public String edit (Model model, @PathVariable("id") long id) {

        if (UserUtils.isSellerLogin() && id != UserUtils.getSellerUserId()) {
            if (!UserUtils.isSellerMasterUser()) {
                return "redirect:/seller/user/edit/"+UserUtils.getSellerUserId();
            }
        }

        try {
            User user = sellerUserService.getSellerUserById(SellerUtils.getSellerId(), id);
            String phoneNumber1 = "";
            String phoneNumber2 = "";
            String phoneNumber3 = "";

            if (user.getPhoneNumber() != null) {
                String[] phoneNumbers = ShopUtils.phoneNumberForDelimitedToStringArray(user.getPhoneNumber());
                phoneNumber1 = ValidationUtils.isNotNull(phoneNumbers[0]) ? phoneNumbers[0] : "";
                phoneNumber2 = ValidationUtils.isNotNull(phoneNumbers[1]) ? phoneNumbers[1] : "";
                phoneNumber3 = ValidationUtils.isNotNull(phoneNumbers[2]) ? phoneNumbers[2] : "";
            }

            model.addAttribute("formAction", "edit");
            model.addAttribute("user", user);
            model.addAttribute("phoneNumber1", phoneNumber1);
            model.addAttribute("phoneNumber2", phoneNumber2);
            model.addAttribute("phoneNumber3", phoneNumber3);

        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);

            FlashMapUtils.alert("존재하지 않는 사용자 입니다.");
            return "redirect:/seller/user/list";
        }

        return "view:/seller/user/user-form";
    }

    @PostMapping("edit/{id}")
    public String editAction (User user, @PathVariable("id") long id) {

        String message;

        try {
            int count = sellerUserService.updateSellerUser(SellerUtils.getSellerId(), user);
            message = count > 0 ? "판매자 수정에 성공 했습니다." : "판매자 수정에 실패 했습니다.";
        } catch (Exception e) {
            message = "판매자 수정에 실패 했습니다.";
            log.error("ERROR: {}", e.getMessage(), e);
        }

        FlashMapUtils.alert(message);
        return "redirect:/seller/user/list";
    }


    @PostMapping("delete/list")
    public JsonView deleteList (ListParam listParam) {
        try {
            sellerUserService.deleteSellerUserByList(SellerUtils.getSellerId(), listParam);
            return JsonViewUtils.success();
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            return JsonViewUtils.failure("삭제에 실패했습니다.");
        }
    }

    @PostMapping("/tempPassword")
    public JsonView tempPassword (@RequestParam("userId") Long userId) {

        try {
            sellerUserService.updateTempPasswordForSellerUser(userId);
        }catch (Exception e) {
            return JsonViewUtils.failure("임시비밀번호 발송이 실패하였습니다.");
        }

        return JsonViewUtils.success();
    }

}
