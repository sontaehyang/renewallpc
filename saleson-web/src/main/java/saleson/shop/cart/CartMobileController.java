package saleson.shop.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;

import saleson.common.asp28.analytics.Asp28Analytics;
import saleson.common.utils.UserUtils;
import saleson.shop.cart.domain.Cart;
import saleson.shop.cart.support.CartParam;
import saleson.shop.openmarket.domain.NaverPay;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.pg.config.ConfigPgService;
import saleson.shop.ranking.RankingService;

@Controller
@RequestMapping({"/m/cart/**", "/m/Cart/**"})
@RequestProperty(template="mobile", layout="default")
public class CartMobileController {

    private static final Logger log = LoggerFactory.getLogger(CartMobileController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private ConfigPgService configPgService;

    @GetMapping("/")
    public String index(RequestContext requestContext, CartParam cartParam, HttpSession session, Model model) {

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());

        Buy buy = cartService.getBuyInfoByCart(cartParam);

        // ?????????????????? ???????????? ???????????? JSONArray??? ?????????
        JSONArray jsonCartItems = JSONArray.fromObject(buy.getReceivers());

        model.addAttribute("naverPay", new NaverPay("Mobile", "/m/cart", configPgService.getConfigPg()));
        model.addAttribute("asp28Analytics", new Asp28Analytics(buy.getItems(), "Mobile", "Cart"));
        model.addAttribute("categoriesTeamList", rankingService.getRankingListForMain(5));
        model.addAttribute("buy", buy);
        model.addAttribute("count", buy.getTotalItemCount());
        model.addAttribute("jsonCartItems", jsonCartItems);
        return ViewUtils.getView("/cart/index");
    }

    /**
     * ???????????? ??????
     * <pre>
     * arrayItemId = ?????? ??????
     * arrayQuantitys = ??????
     * arrayRequiredOptions = ?????? (??????ID^^^?????????^^^????????????/???????????????@??????ID^^^?????????^^^????????????/???????????????)
     * </pre>
     * @param cart
     * @return
     */
    @PostMapping("add-item-to-cart")
    public JsonView addItemToCart(RequestContext requestContext, Cart cart, HttpSession session) {
        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        cart.setSessionId(session.getId());
        cart.setUserId(UserUtils.getUserId());

        if (UserUtils.isUserLogin() == false) {
            cart.setUserId(0);
        }
        //int count = cartService.getCartAvailableInsertValidationCountForItemType(cart);
        //if (count == 0) {
        int insertCount = cartService.insertCart(cart);
        if (insertCount == 0) {
            return JsonViewUtils.failure(MessageUtils.getMessage("M00486")); // ?????? ?????? ????????? ????????? ?????? ???????????????. \n????????? ?????? ????????? ?????????.
        } else {

            // ???????????? ?????? ?????? ??????
            CartParam cartParam = new CartParam();
            cartParam.setUserId(UserUtils.getUserId());
            cartParam.setSessionId(session.getId());
            //skc//List<OrderItem> cartList = cartService.getCartList(cartParam);
            List<OrderItem> cartList = new ArrayList<>();

            int cartQuantity = 0;
            int cartPrice = 0;
            for (OrderItem orderItem : cartList) {
                cartQuantity += orderItem.getQuantity();
                //skc//cartPrice += orderItem.getItemPriceData().getSumItemPrice();
            }

            HashMap<String, Object> result = new HashMap<>();
            result.put("cartQuantity", cartQuantity);
            result.put("cartPrice", StringUtils.numberFormat(cartPrice));


            return JsonViewUtils.success(result);
        }
        //} else {
        //	return JsonViewUtils.failure(MessageUtils.getMessage("M00482")); // ??????????????? ??????????????? ??????????????? ?????? ?????? ????????? ?????????????????? ????????? ?????? ??????????????????.
        //}

    }

    /**
     * ???????????? ?????? ??????
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("list/delete")
    public JsonView deleteListData(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        long userId = 0;
        if (UserUtils.isUserLogin()) {
            userId = UserUtils.getUserId();
        }

        cartParam.setUserId(userId);
        cartParam.setSessionId(session.getId());

        cartService.deleteListData(cartParam);
        return JsonViewUtils.success();
    }

    /**
     * ???????????? ?????? ??????
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("edit-quantity")
    public JsonView editQuantity(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        User user = requestContext.getUser();

        if(user != null){
            cartParam.setUserId(user.getUserId());
        }
        cartParam.setSessionId(session.getId());

        if (UserUtils.isUserLogin() == false) {
            cartParam.setUserId(0);
        }

        cartService.updateQuantity(cartParam);
        return JsonViewUtils.success();
    }

    /**
     * ?????? ??????
     * @param requestContext
     * @param cart
     * @param session
     * @return
     */
    @PostMapping("buy-now")
    public JsonView buyNow(RequestContext requestContext, Cart cart, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        User user = requestContext.getUser();
        cart.setUserId(user.getUserId());
        cart.setSessionId(session.getId());

        if (UserUtils.isUserLogin() == false) {
            cart.setUserId(0);
        }

        try {
            cartService.immediatelyBuy(cart);
            cart = null;
            return JsonViewUtils.success();
        } catch(Exception e) {
            return JsonViewUtils.failure(e.getMessage());
        }

    }

    /**
     * ???????????? -> ?????? ?????? ??????????????? ??????
     * @param requestContext
     * @param cartParam
     * @param session
     * @return
     */
    @PostMapping("save-order-item-temp")
    public JsonView saveOrderItemTemp(RequestContext requestContext, CartParam cartParam, HttpSession session) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        User user = requestContext.getUser();
        cartParam.setUserId(user.getUserId());
        cartParam.setSessionId(session.getId());

        if (UserUtils.isUserLogin() == false) {
            cartParam.setUserId(0);
        }

        try {
            cartService.copyCartToOrderItemTemp(cartParam);
            cartParam = null;
            return JsonViewUtils.success();
        } catch(Exception e) {
            return JsonViewUtils.failure(e.getMessage());
        }
    }
}
