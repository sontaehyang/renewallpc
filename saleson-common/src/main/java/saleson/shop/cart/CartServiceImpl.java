package saleson.shop.cart;

import java.util.*;
import java.util.stream.Collectors;

import com.onlinepowers.framework.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.GiftItem;
import saleson.shop.cart.domain.Cart;
import saleson.shop.cart.domain.OrderQuantity;
import saleson.shop.cart.support.CartParam;
import saleson.shop.config.ConfigService;
import saleson.shop.config.domain.Config;
import saleson.shop.giftitem.GiftItemService;
import saleson.shop.item.ItemMapper;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.domain.ItemOption;
import saleson.shop.order.OrderService;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.BuyItem;
import saleson.shop.order.domain.BuyQuantity;
import saleson.shop.order.domain.ItemPrice;
import saleson.shop.order.domain.Receiver;
import saleson.shop.order.support.OrderException;
import saleson.shop.point.PointService;

import com.onlinepowers.framework.sequence.service.SequenceService;

@Service("cartService")
public class CartServiceImpl implements CartService {

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private PointService pointService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GiftItemService giftItemService;

    @Override
    public List<BuyItem> getCartList(CartParam cartParam, boolean isSetPoint) {
        List<BuyItem> list = cartMapper.getCartList(cartParam);

        if (ValidationUtils.isNotNull(list)) {

            List<String> systemComments = new ArrayList<>();
            List<BuyItem> impossibleToPurchaseProducts = null;

            // 재고 차감 + 재고 검증용
            HashMap<String, Integer> buyQuantityMap = new HashMap<>();

            if (buyQuantityMap == null) {
                buyQuantityMap = new HashMap<>();
            }

            // 옵션 데이터 셋팅
            for (int i = 0; i < list.size(); i++) {
                BuyItem buyItem = list.get(i);
                Item item = buyItem.getItem();

                item.setItemOptions(itemService.getItemOptionList(item, false));

                // 상품의 필수 옵션을 구성
                buyItem.setOptionList(ShopUtils.getRequiredItemOptions(item, buyItem.getOptions()));
            }

            // 구매 상품중에 재고연동 사용하는 상품의 총 재고량 목록
            HashMap<String, HashMap<String, Integer>> stockMap = ShopUtils.makeStockMap(list);

            if (stockMap == null) {
                stockMap = new HashMap<String, HashMap<String, Integer>>();
            }

            // 최소 구매수량 체크용
            HashMap<String, Integer> buyQuantityItemUserCodeMap = new HashMap<>();
            for(int i = 0; i < list.size(); i++) {
                BuyItem buyItem = list.get(i);
                Item item = buyItem.getItem();

                if (buyQuantityItemUserCodeMap.get(item.getItemUserCode()) == null) {
                    buyQuantityItemUserCodeMap.put(item.getItemUserCode(), buyItem.getItemPrice().getQuantity());
                } else {
                    buyQuantityItemUserCodeMap.put(item.getItemUserCode(), buyQuantityItemUserCodeMap.get(item.getItemUserCode()) + buyItem.getItemPrice().getQuantity());
                }
            }

            // 사은품 데이터 셋팅
            for(int i = 0; i < list.size(); i++) {
                BuyItem buyItem = list.get(i);

                if (buyItem == null) {
                    continue;
                }

                try {
                    List<GiftItem> freeGiftItemList = giftItemService.getGiftItemListForFront(buyItem.getItemId());
                    buyItem.setFreeGiftItemText(ShopUtils.makeGiftItemText(freeGiftItemList));
                    buyItem.setFreeGiftItemList(ShopUtils.conventGiftItemInfoList(freeGiftItemList));
                } catch (Exception e) {

                }
            }

            for (int i = 0; i < list.size(); i++) {
                BuyItem buyItem = list.get(i);

                if (buyItem == null) {
                    continue;
                }

                Item item = buyItem.getItem();
                ItemPrice itemPrice = buyItem.getItemPrice();

                ShopUtils.getItemMaxQuantity(buyItem, stockMap, buyQuantityMap);
                OrderQuantity orderQuantity = buyItem.getOrderQuantity();

                // 3. 상품이 판매 종료된 상품인지 검사
                if (orderQuantity.getMaxQuantity() == 0) {
                    buyItem.setAvailableForSaleFlag("N");
                    buyItem.setSystemComment(MessageUtils.getMessage("M00483")); // 이쪽의 상품은 재고가 없습니다.
                }

                // 상품 코드가 같은 상품의 구매 총 수량이 상품에 설정된 최소 구매수량보다 적은경우 구매 못함
                String itemUserCode = item.getItemUserCode();
                if (buyQuantityItemUserCodeMap.get(itemUserCode) != null) {
                    if (item.getOrderMinQuantity() > buyQuantityItemUserCodeMap.get(itemUserCode)) {
                        buyItem.setAvailableForSaleFlag("N");
                    }
                }

                // 추가 구성품인경우 본품이 있는지 체크
                if("Y".equals(buyItem.getAdditionItemFlag())) {

                    boolean isDelete = true;
                    for(BuyItem bItem : list) {
                        if (bItem.getItemId() == buyItem.getParentItemId()) {
                            isDelete = false;
                            break;
                        }
                    }

                    if (isDelete) {
                        buyItem.setAvailableForSaleFlag("N");
                    }

                }

                if ("Y".equals(buyItem.getAvailableForSaleFlag())) {

                    // 적립금은 로그인한 회원에게만 부여됨..
                    if (UserUtils.isUserLogin() && isSetPoint) {
                        buyItem.setPointPolicy(pointService.getPointPolicyByItemId(item.getItemId()));
                    }

                    // 최대 구매가능 수량보다 장바구니에 담겨있는 수량이 많은경우 장바구니 구매수량 변경
                    if (itemPrice.getQuantity() > orderQuantity.getMaxQuantity() && orderQuantity.getMaxQuantity() > 0) {
                        itemPrice.setQuantity(orderQuantity.getMaxQuantity());
                        buyItem.getItemPrice().setQuantity(orderQuantity.getMaxQuantity());

                        systemComments.add(item.getItemName() + MessageUtils.getMessage("M00487")); // oo상품의 재고 부족으로 상품 구매 수량이 변경 되었습니다.

                        // DB의 상품의 구매수량을 변경함..
                        CartParam quantityUpdateCartParam = cartParam;
                        quantityUpdateCartParam.setQuantity(itemPrice.getQuantity());
                        quantityUpdateCartParam.setCartId(buyItem.getCartId());
                        cartMapper.updateQuantity(quantityUpdateCartParam);
                    }

                    // 상품별 금액 셋팅!!
                    buyItem.setItemPrice(new ItemPrice(buyItem));

                } else {

                    // 해당 상품이 구매 불가 상품인 경우 해당 영역에서 처리됨
                    if (impossibleToPurchaseProducts == null) {
                        impossibleToPurchaseProducts = new ArrayList<>();
                    }

                    impossibleToPurchaseProducts.add(buyItem);
                    if (ValidationUtils.isNotNull(buyItem.getSystemComment())) {
                        //systemComments.add(orderItemTemp.getSystemComment());
                    }

                    // 장바구니에 담겨있는 상품중에 구매 불가 상품을 삭제함.
                    CartParam deleteCartParam = new CartParam();
                    deleteCartParam.setUserId(cartParam.getUserId());
                    deleteCartParam.setSessionId(cartParam.getSessionId());
                    deleteCartParam.setCartId(buyItem.getCartId());

                    cartMapper.deleteCart(deleteCartParam);

                    list.remove(i--);
                }
            }
        }

        return list;
    }

    // 살때?
    @Override
    public Buy getBuyInfoByCart(CartParam cartParam) {

        Buy buy = new Buy();

        Receiver receiver = new Receiver();

        List<BuyItem> items = this.getCartList(cartParam, true);

        if (!items.isEmpty()) {
            // 배송지별 구매상품 초기화
            List<BuyQuantity> buyQuantitys = new ArrayList<>();
            for (BuyItem buyItem : items) {
                BuyQuantity buyQuantity = new BuyQuantity();
                buyQuantity.setItemSequence(buyItem.getItemSequence());
                buyQuantity.setQuantity(buyItem.getItemPrice().getQuantity());
                buyQuantitys.add(buyQuantity);
            }

            receiver.setBuyQuantitys(buyQuantitys);

            // 장바구니 내 추가구성상품 조회
            List<BuyItem> additionItems = items.stream()
                    .filter(cl -> "Y".equals(cl.getAdditionItemFlag())).collect(Collectors.toList());

            // 추가구성상품 데이터 세팅
            for (BuyItem buyItem : items) {
                List<BuyItem> buyAdditionItems = new ArrayList<>();

                for (BuyItem additionItem : additionItems) {
                    int itemId = buyItem.getItem().getItemId();

                    // 부모의 ITEM_ID, OPTIONS 가 일치하는 경우
                    if (additionItem.getParentItemId() == itemId && buyItem.getOptions().equals(additionItem.getParentItemOptions())) {
                        additionItem.setItemPrice(new ItemPrice(additionItem));
                        additionItem.setOrderQuantity(additionItem.getOrderQuantity());
                        additionItem.setPointPolicy(pointService.getPointPolicyByItemId(additionItem.getItemId()));

                        buyAdditionItems.add(additionItem);
                    }
                }

                buyItem.setAdditionItemList(buyAdditionItems);
            }

            // 상품단위로 처리되는 기존 로직을 유지하기 위해 items에 추가한 추가구성상품 삭제
            List<BuyItem> itemList = items.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());
            items.clear();
            items.addAll(itemList);

            receiver.setItems(items);
            receiver.setShipping("");

            List<Receiver> receivers = new ArrayList<>();
            receivers.add(receiver);

            buy.setReceivers(receivers);
            buy.setOrderPrice(0, configService.getShopConfig(Config.SHOP_CONFIG_ID));
        }

        return buy;
    }

    @Override
    public Buy getBuyInfoByQuotation(Cart cartParam) {

        Buy buy = new Buy();
        Receiver receiver = new Receiver();
        List<BuyItem> buyItems = new ArrayList<>();

        List<Cart> cartList = this.makeCartListByCart(cartParam);

        if (cartList != null && !cartList.isEmpty()) {
            // 일반상품
            List<Cart> list = cartList.stream().filter(c -> "N".equals(c.getAdditionItemFlag())).collect(Collectors.toList());

            // 추가구성상품
            List<Cart> additionList = cartList.stream().filter(c -> "Y".equals(c.getAdditionItemFlag())).collect(Collectors.toList());

            // 견적서 데이터 세팅 (Cart -> Receiver)
            List<BuyQuantity> buyQuantities = new ArrayList<>();
            for (Cart cart : cartList) {
                BuyQuantity buyQuantity = new BuyQuantity();
                buyQuantity.setQuantity(cart.getQuantity());
                buyQuantities.add(buyQuantity);
            }

            receiver.setBuyQuantitys(buyQuantities);

            // 견적서 데이터 세팅 (Cart -> Buy)
            int itemSequence = 0;
            for (Cart info : list) {
                // 일반상품 가격정보
                ItemPrice itemPrice = new ItemPrice();
                itemPrice.setQuantity(info.getQuantity());
                itemPrice.setCostPrice(info.getItem().getCostPrice());

                // 일반상품 정보
                BuyItem buyItem = new BuyItem();
                buyItem.setItem(info.getItem());
                buyItem.setOptions(info.getOptions());
                buyItem.setOptionList(ShopUtils.getRequiredItemOptions(buyItem.getItem(), buyItem.getOptions()));
                buyItem.setItemPrice(itemPrice);
                buyItem.setItemPrice(new ItemPrice(buyItem));

                // 첫번째 상품에만 추가구성상품 세팅
                if (itemSequence == 0) {
                    List<BuyItem> buyAdditionItems = new ArrayList<>();

                    for (Cart additionInfo : additionList) {
                        // 추가구성상품 가격정보
                        ItemPrice additionItemPrice = new ItemPrice();
                        additionItemPrice.setQuantity(additionInfo.getQuantity());
                        additionItemPrice.setCostPrice(additionInfo.getItem().getCostPrice());

                        // 추가구성상품 정보
                        BuyItem buyAdditionItem = new BuyItem();
                        buyAdditionItem.setItem(additionInfo.getItem());
                        buyAdditionItem.setItemPrice(additionItemPrice);
                        buyAdditionItem.setItemPrice(new ItemPrice(buyAdditionItem));

                        buyAdditionItems.add(buyAdditionItem);
                    }

                    buyItem.setAdditionItemList(buyAdditionItems);
                }

                buyItems.add(buyItem);
                itemSequence++;
            }

            receiver.setItems(buyItems);
            receiver.setShipping("");

            List<Receiver> receivers = new ArrayList<>();
            receivers.add(receiver);

            buy.setReceivers(receivers);
            buy.setOrderPrice(0, configService.getShopConfig(Config.SHOP_CONFIG_ID));
        }

        return buy;
    }

    @Override
    public void immediatelyBuy(Cart cart) {

        // 필수 옵션 정보 구성
        List<Cart> list = this.makeCartListByPostData(cart.getArrayRequiredItems(), false);

        String shippingGroupCode = list.get(0).getShippingGroupCode();      // 본품 - 묶음기준 코드
        String shippingPaymentType = list.get(0).getShippingPaymentType();  // 본품 - 배송비 지불 방법
        String parentItemOptions = list.get(0).getOptions();                // 본품 - 옵션

        // 추가 구성상품 정보 구성
        List<Cart> additionList = this.makeCartListByPostData(cart.getArrayAdditionItems(), true);
        if (additionList != null) {
            if (list == null) {
                list = new ArrayList<>();
            }

            // 본품 배송비 지불 방법, 묶음기준 코드 적용
            additionList.stream().forEach(addition -> {
                addition.setShippingGroupCode(shippingGroupCode);
                addition.setShippingPaymentType(shippingPaymentType);
            });

            list.addAll(additionList);
        }

        if (list == null) {
            throw new OrderException(MessageUtils.getMessage("M00481")); // 잘못된 접근입니다.
        }

        List<Integer> cartSequence = new ArrayList<>();
        if (list != null) {
            for (Cart c : list) {
                int sequence = sequenceService.getId("OP_CART");
                try {
                    c.setCartId(sequence);

                    int quantity = c.getQuantity();

                    // 구매 수량이 0인경우 에러
                    if (quantity <= 0) {
                        throw new OrderException(MessageUtils.getMessage("M01590"));
                    }

                    c.setSessionId(cart.getSessionId());
                    c.setUserId(cart.getUserId());

                    // 추가 구성품의 부모 상품 ID, OPTIONS를 저장
                    if ("Y".equals(c.getAdditionItemFlag())) {
                        c.setParentItemId(itemMapper.getParentAdditionItemId(c.getItemAdditionId()));
                        c.setParentItemOptions(parentItemOptions);
                    }

                    cartMapper.insertCart(c);
                    cartSequence.add(sequence);
                } catch(Exception e) {
                    log.warn(e.getMessage());
                }
            }
        }

        if (cartSequence.isEmpty()) {
            // 주문 상품 처리중 오류가 발생 하였습니다. \n주문을 다시 시도해 주세요.
            throw new OrderException(MessageUtils.getMessage("M00486"));
        }

        CartParam cartParam = new CartParam();
        cartParam.setUserId(cart.getUserId());
        cartParam.setSessionId(cart.getSessionId());
        cartParam.setCartIds(cartSequence);
        cartParam.setCampaignCode(cart.getCampaignCode());

        // 렌탈 결제시 주문상품 렌탈정보 임시 저장
        if (cart.getBuyRentalPay().equals("Y")) {
            cartParam.setBuyRentalPay(cart.getBuyRentalPay());
            cartParam.setRentalTotAmt(cart.getRentalTotAmt());
            cartParam.setRentalMonthAmt(cart.getRentalMonthAmt());
            cartParam.setRentalPartnershipAmt(cart.getRentalPartnershipAmt());
            cartParam.setRentalPer(cart.getRentalPer());

            orderService.deleteRentalOrderItemTem(cartParam.getUserId(), cartParam.getSessionId());
            orderService.insertRentalOrderItemTemp(cartParam);
        }

        // 장바구니에 담긴 상품을 Temp로 복사..
        this.copyCartToOrderItemTemp(cartParam);

        cartMapper.deleteCartByCartIds(cartParam);
    }

    /**
     * 상품페이지에서 넘어온 정보를 Cart로 변경
     * @param items
     * @param isAdditionItem
     * @return
     */
    private List<Cart> makeCartListByPostData(String[] items, boolean isAdditionItem) {

        if (items == null) {
            return null;
        }

        List<Cart> cartList = new ArrayList<>();
        for(int j = 0; j < items.length; j++) {
            String itemString = items[j];

            String[] itemInfo = StringUtils.delimitedListToStringArray(itemString, "||");
            if (itemInfo.length != 3) {
                continue;
            }

            int itemId = Integer.parseInt(itemInfo[0]);
            int quantity = Integer.parseInt(itemInfo[1]);
            Item item = itemMapper.getItemById(itemId);

            if (item == null) {
                continue;
            }

            int itemAdditionId = 0;
            if (isAdditionItem == true) {
                itemAdditionId = Integer.parseInt(itemInfo[2]);
            }

            Cart cartTemp = new Cart();
            cartTemp.setItemId(itemId);
            cartTemp.setQuantity(quantity);
            cartTemp.setItemId(item.getItemId());
            cartTemp.setAdditionItemFlag(isAdditionItem ? "Y" : "N");
            cartTemp.setShippingGroupCode(item.getShippingGroupCode());
            cartTemp.setItemAdditionId(itemAdditionId);

            cartTemp.setItem(item);

            String optionsText = itemInfo[2];
            String options = "";
            if (StringUtils.isNotEmpty(optionsText)) {

                item.setItemOptions(itemService.getItemOptionList(item, false));

                String[] optionList = StringUtils.delimitedListToStringArray(optionsText, "^^^");

                for(String optionText : optionList) {

                    String[] optionInfo = StringUtils.delimitedListToStringArray(optionText, "```");
                    if (optionInfo.length != 2) {
                        continue;
                    }

                    int optionId = Integer.parseInt(optionInfo[0]);
                    String text = StringUtils.isNotEmpty(optionInfo[1]) ? optionInfo[1] : "";

                    for(ItemOption itemOption : item.getItemOptions()) {


                        if (itemOption.getItemOptionId() == optionId) {

                            if (StringUtils.isNotEmpty(options)) {
                                options += "^^^";
                            }

                            options += ShopUtils.makeOptionText(item, itemOption, text);
                            break;
                        }

                    }
                }

            }

            cartTemp.setOptions(options);
            cartList.add(cartTemp);
        }

        return cartList;
    }

    @Override
    public void copyCartToOrderItemTemp(CartParam cartParam) {

        if (cartParam.getCartIds().isEmpty()) {
            throw new OrderException();
        }

        Buy buy = this.getBuyInfoByCart(cartParam);
        List<BuyItem> list = buy.getItems();

        // 상품단위로 처리되는 기존 로직을 유지하기 위해 buyItem.additionItemList 를 buyItem에 추가
        List<BuyItem> items = new ArrayList<>();
        list.stream().forEach(l -> items.addAll(l.getAdditionItemList()));
        list.addAll(items);


        try {

            // 구매가능여부 채크
            ShopUtils.buyVerification(list, cartParam.getCartIds().size());

        } catch (OrderException oe) {
            throw new OrderException(oe.getMessage(), "/cart");
        }

        // 장바구니 결제 금액에 최소 결제 금액 재한이 걸려 있는 경우 총 결제 예정 금액을 체크함..

        Config shopConfig = ShopUtils.getConfig();
        if (shopConfig.getMinimumPaymentAmount() > 0) {

            int minimumPaymentAmount = shopConfig.getMinimumPaymentAmount();
            if (buy.getOrderPrice().getOrderPayAmount() < minimumPaymentAmount) {

                String message = MessageUtils.getMessage("M01046") +" (" + NumberUtils.formatNumber(minimumPaymentAmount, "#,##0") + ") "+ MessageUtils.getMessage("M01266");
                throw new OrderException(message);

            }

        }

        orderService.deleteOrderItemTemp(cartParam.getUserId(), cartParam.getSessionId());

        int itemSequence = 0;
        for (BuyItem buyItem : list) {

            ItemPrice itemPrice = buyItem.getItemPrice();

            // 구매 수량이 0보다 작은경우
            if (itemPrice.getQuantity() <= 0) {
                throw new OrderException(MessageUtils.getMessage("M01590"));
            }

            if (UserUtils.isUserLogin() == false) {
                buyItem.setUserId(0);
            }

            buyItem.setItemSequence(itemSequence++);
            buyItem.setSessionId(cartParam.getSessionId());
            buyItem.setCampaignCode(cartParam.getCampaignCode());

            if ("Y".equals(buyItem.getAdditionItemFlag())) {

                for(BuyItem temp : list) {
                    if ("N".equals(temp.getAdditionItemFlag()) && buyItem.getParentItemId() == temp.getItemId()) {
                        buyItem.setParentItemSequence(temp.getItemSequence());
                        buyItem.setParentItemId(temp.getItemId());
                        buyItem.setParentItemOptions(temp.getOptions());
                        break;
                    }
                }

            }

            orderService.insertOrderItemTemp(buyItem);
        }


        // 기존 주문 임시 데이터 삭제
        orderService.deleteOrderTemp(cartParam.getUserId(), cartParam.getSessionId());

        // 상품단위로 처리되는 기존 로직을 유지하기 위해 list에 추가한 추가구성상품 삭제
        List<BuyItem> itemList = list.stream().filter(l -> "N".equals(l.getAdditionItemFlag())).collect(Collectors.toList());
        list.clear();
        list.addAll(itemList);
    }

    // 장바구니에 등록
    @Override
    public int insertCart(Cart cart) {

        // 필수 옵션 정보 구성
        List<Cart> list = this.makeCartListByPostData(cart.getArrayRequiredItems(), false);

        String shippingGroupCode = list.get(0).getShippingGroupCode();      // 본품 - 묶음기준 코드
        String shippingPaymentType = list.get(0).getShippingPaymentType();  // 본품 - 배송비 지불 방법

        // 추가 구성상품 정보 구성
        List<Cart> additionList = this.makeCartListByPostData(cart.getArrayAdditionItems(), true);
        if (additionList != null) {
            if (list == null) {
                list = new ArrayList<>();
            }

            // 본상품의 배송정보 적용
            additionList.stream().forEach(addition -> {
                addition.setShippingGroupCode(shippingGroupCode);
                addition.setShippingPaymentType(shippingPaymentType);
            });

            list.addAll(additionList);
        }

        int insertCount = 0;
        int parentItemId = 0;
        if (list != null) {
            for (Cart c : list) {
                int quantity = c.getQuantity();
                int sequence = sequenceService.getId("OP_CART");

                c.setSessionId(cart.getSessionId());
                c.setUserId(cart.getUserId());

                // 구매 수량이 0인경우 에러
                if (quantity <= 0) {
                    throw new OrderException(MessageUtils.getMessage("M01590"));
                }

                if ("Y".equals(c.getAdditionItemFlag())) {
                    c.setParentItemId(parentItemId);
                }

                List<Cart> searchCartList = cartMapper.getDuplicateCart(c);

                if (searchCartList.isEmpty()) {
                    c.setCartId(sequence);

                    // 추가 구성품의 부모 상품 ID, OPTIONS를 저장
                    if ("Y".equals(c.getAdditionItemFlag())) {
                        this.setParentItem(cart, c, parentItemId);
                    }

                    cartMapper.insertCart(c);
                } else {
                    int makeQuantity = 0;
                    for(Cart searchCart : searchCartList) {
                        makeQuantity += searchCart.getQuantity();
                    }
                    makeQuantity = makeQuantity + quantity;
                    c.setQuantity(makeQuantity);
                    if (searchCartList.size() > 1) {

                        // 중복상품 통합
                        cartMapper.deleteDuplicateCart(c);
                        c.setCartId(sequence);

                        // 추가 구성품의 부모 상품 ID, OPTIONS를 저장
                        if ("Y".equals(c.getAdditionItemFlag())) {
                            this.setParentItem(cart, c, parentItemId);
                        }

                        cartMapper.insertCart(c);
                    } else {
                        cartMapper.updateDuplicateCartQuantity(c);
                    }
                }

                if (insertCount == 0) {
                    parentItemId = c.getItemId();
                }

                insertCount++;
            }
        }

        return insertCount;
    }

    @Override
    public void updateQuantity(CartParam cartParam) {

        List<BuyItem> itemList = this.getCartList(cartParam, true);

        int addItemQuantity = cartParam.getQuantity();
        int maxQuantity = 0;
        int minQuantity = 0;

        int result = 0;

        for (BuyItem buyItem : itemList) {
            // [손준의] 추가하려는 상품과 동일한 장바구니 상품인지 확인 후 해당 상품의 구매제한 확인
            if (cartParam.getCartId() == buyItem.getCartId()) {
                maxQuantity = buyItem.getItem().getOrderMaxQuantity();
                minQuantity = buyItem.getItem().getOrderMinQuantity();
            }
        }

        if (maxQuantity > 0 && addItemQuantity > maxQuantity) {
            result = 2;
        }
        if (minQuantity > 0 && addItemQuantity < minQuantity){
            result = 1;
        }
        if (addItemQuantity > 999) {
            result = 3;
        }

        if (result == 0) {
            cartMapper.updateQuantity(cartParam);
        } else if (result == 1) {
            throw new OrderException("장바구니에 담긴 수량이 최소 구매 가능 수량을 미달하였습니다.");
        } else if (result == 2) {
            throw new OrderException("장바구니에 담긴 수량이 최대 구매 가능 수량을 초과하였습니다.");
        } else if (result == 3) {
            throw new OrderException("장바구니에 담긴 수량은 999개를 초과할 수 없습니다.");
        }
    }

    @Override
    public void updateUserIdBySessionId(long userId, String sessionId) {
        if (UserUtils.isUserLogin()) {

            CartParam param = new CartParam();
            param.setUserId(userId);
            param.setSessionId(sessionId);

            List<Cart> guestCartList = null;

            if (cartMapper.getCountForUserItemByUserId(userId) == 0) {
                guestCartList = cartMapper.getCartListBySessionId(sessionId);
            } else {
                guestCartList = cartMapper.getGuestConvertibleItems(param);
            }

            if (guestCartList != null) {
                for(Cart cart : guestCartList) {

                    int quantity = cart.getQuantity();
                    if (quantity > 0) {
                        cart.setUserId(userId);

                        List<Cart> searchCartList = cartMapper.getDuplicateCart(cart);

                        if (searchCartList.isEmpty()) {
                            cartMapper.updateUserIdByCart(cart);
                        } else {
                            int makeQuantity = 0;
                            for(Cart searchCart : searchCartList) {
                                makeQuantity += searchCart.getQuantity();
                            }

                            makeQuantity = makeQuantity + quantity;
                            cart.setQuantity(makeQuantity);

                            // 중복상품 통합 - 회원의 카드가 삭제됨
                            cartMapper.deleteDuplicateCart(cart);
                            cartMapper.updateUserIdByCart(cart);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deleteListData(CartParam cartParam) {
        if (cartParam.getId() != null) {
            for (String id : cartParam.getId()) {
                cartParam.setCartId(Integer.parseInt(id));
                cartMapper.deleteCart(cartParam);
            }
        }
    }

    @Override
    public void updateShippingPaymentType(CartParam cartParam) {
        cartMapper.updateShippingPaymentType(cartParam);
    }

    @Override
    public List<Cart> makeCartListByCart(Cart cart) {

        if (cart == null) {
            return null;
        }

        // 필수 옵션 정보 구성
        List<Cart> list = this.makeCartListByPostData(cart.getArrayRequiredItems(), false);

        // 추가 구성상품 정보 구성
        List<Cart> additionList = this.makeCartListByPostData(cart.getArrayAdditionItems(), true);
        if (additionList != null) {
            if (list == null) {
                list = new ArrayList<>();
            }

            list.addAll(additionList);
        }

        return list;
    }

    private void setParentItem(Cart cart, Cart c, int parentItemId) {
        Cart cartInfo = new Cart();
        cartInfo.setSessionId(cart.getSessionId());
        cartInfo.setUserId(cart.getUserId());
        cartInfo.setItemId(parentItemId);

        // 해당 ITEM_ID가 장바구니에 있는지 조회 - 무조건 첫번째 ITEM과 추가구성상품을 묶어주기 위해
        List<Cart> cartList = cartMapper.getDuplicateCart(cartInfo);

        if (!cartList.isEmpty()) {
            // 장바구니에 있으면 첫번째 상품의 ITEM_ID, OPTIONS를 추가구성상품에 SET
            c.setParentItemId(cartList.get(0).getItemId());
            c.setParentItemOptions(cartList.get(0).getOptions());
        }
    }
}
