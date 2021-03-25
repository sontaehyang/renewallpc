package saleson.shop.order.giftitem;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.GiftGroupType;
import saleson.common.enumeration.GiftOrderStatus;
import saleson.common.enumeration.UserType;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.model.GiftItem;
import saleson.model.OrderGiftItem;
import saleson.model.OrderGiftItemLog;
import saleson.seller.main.domain.Seller;
import saleson.shop.giftitem.GiftItemService;
import saleson.shop.order.giftitem.support.OrderGiftItemDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service("orderGiftItemService")
public class OrderGiftItemServiceImpl implements OrderGiftItemService{

    @Autowired
    private GiftItemService giftItemService;

    @Autowired
    private OrderGiftItemRepository orderGiftItemRepository;

    @Autowired
    private OrderGiftItemLogRepository orderGiftItemLogRepository;

    @Override
    public void insertOrderGiftItemByByItemId(String orderCode, int orderSequence, int itemSequence, int itemId)
            throws Exception {

        if (!StringUtils.isEmpty(orderCode) &&  orderSequence > -1 && itemSequence > -1) {

            // 1. 현재 주문의 사은품 조회
            int maxGiftSequence = getMaxGiftSequence(orderCode, orderSequence, itemSequence);

            // 2. 상품의 사은품 조회
            List<GiftItem> giftItems = giftItemService.getGiftItemListForFront(itemId);

            if (giftItems != null && !giftItems.isEmpty()) {

                for (GiftItem giftItem : giftItems) {
                    OrderGiftItem orderGiftItem
                            = getSaveOrderGiftItem(orderCode, orderSequence, itemSequence, ++maxGiftSequence, giftItem);
                    orderGiftItemRepository.save(orderGiftItem);
                    insertOrderGiftItemLog(orderGiftItem, GiftOrderStatus.NORMAL);
                }

            }

        }
    }

    /**
     * 상품 사은품 등록용
     * @param orderCode
     * @param orderSequence
     * @param giftSequence
     * @param giftItem
     * @return
     */
    private OrderGiftItem getSaveOrderGiftItem(String orderCode, int orderSequence, int itemSequence,
                                               int giftSequence, GiftItem giftItem) {

        OrderGiftItem orderGiftItem = new OrderGiftItem();

        orderGiftItem.setOrderCode(orderCode);
        orderGiftItem.setOrderSequence(orderSequence);
        orderGiftItem.setItemSequence(itemSequence);
        orderGiftItem.setGiftSequence(giftSequence);
        orderGiftItem.setGiftItemId(giftItem.getId());
        orderGiftItem.setGiftItemCode(giftItem.getCode());
        orderGiftItem.setGiftItemName(giftItem.getName());
        orderGiftItem.setSellerId(giftItem.getSellerId());
        orderGiftItem.setPrice(giftItem.getPrice());
        orderGiftItem.setImage(giftItem.getImageSrc());
        orderGiftItem.setValidStartDate(giftItem.getValidStartDate());
        orderGiftItem.setValidEndDate(giftItem.getValidEndDate());

        orderGiftItem.setGiftOrderStatus(GiftOrderStatus.NORMAL);

        orderGiftItem.setGiftGroupId(0L);
        orderGiftItem.setGroupType(GiftGroupType.NONE);


        return orderGiftItem;
    }

    @Override
    public void cancelOrderGiftItem(String orderCode, int orderSequence, int itemSequence) throws Exception {
        updateGiftOrderStatus(orderCode, orderSequence, itemSequence, GiftOrderStatus.CANCEL);
    }

    @Override
    public void returnOrderGiftItem(String orderCode, int orderSequence, int itemSequence) throws Exception {
        updateGiftOrderStatus(orderCode, orderSequence, itemSequence, GiftOrderStatus.RETURN);
    }

    private void updateGiftOrderStatus(String orderCode, int orderSequence, int itemSequence, GiftOrderStatus updateGiftOrderStatus) throws Exception {

        List<OrderGiftItem> list = getOrderGiftItemList(orderCode, orderSequence, itemSequence);

        if (list != null && !list.isEmpty()) {

            list.stream().forEach(
                    orderGiftItem -> {

                        boolean isMatch = orderCode.equals(orderGiftItem.getOrderCode())
                                && orderSequence == orderGiftItem.getOrderSequence()
                                && itemSequence == orderGiftItem.getItemSequence() ;

                        if (isMatch) {
                            GiftOrderStatus orgGiftOrderStatus = orderGiftItem.getGiftOrderStatus();
                            orderGiftItem.setGiftOrderStatus(updateGiftOrderStatus);
                            orderGiftItemRepository.save(orderGiftItem);
                            insertOrderGiftItemLog(orderGiftItem, orgGiftOrderStatus);
                        }
                    }
            );
        }

    }

    /**
     * 사은품 주문 로그 등록
     * @param orderGiftItem
     * @param orgGiftOrderStatus
     */
    private void insertOrderGiftItemLog(OrderGiftItem orderGiftItem, GiftOrderStatus orgGiftOrderStatus) {

        OrderGiftItemLog log = new OrderGiftItemLog();

        log.setOrderCode(orderGiftItem.getOrderCode());
        log.setOrderSequence(orderGiftItem.getOrderSequence());
        log.setItemSequence(orderGiftItem.getItemSequence());
        log.setGiftSequence(orderGiftItem.getGiftSequence());
        log.setGiftItemId(orderGiftItem.getGiftItemId());
        log.setGiftItemCode(orderGiftItem.getGiftItemCode());
        log.setGiftItemName(orderGiftItem.getGiftItemName());
        log.setGiftGroupId(orderGiftItem.getGiftGroupId());
        log.setGroupType(orderGiftItem.getGroupType());
        log.setGiftOrderStatus(orderGiftItem.getGiftOrderStatus());
        log.setUserType(getUserType());

        log.setOrgGiftOrderStatus(orgGiftOrderStatus);

        orderGiftItemLogRepository.save(log);
    }

    private UserType getUserType() {
        UserType userType = null;

        Seller seller = SellerUtils.getSeller();
        if (ValidationUtils.isNotNull(seller) && ShopUtils.isSellerPage()) {
            userType = UserType.SELLER;
        } else {
            if (UserUtils.isManagerLogin()) {
                userType = UserType.MANAGER;
            } else {
                if (UserUtils.isUserLogin()) {
                    userType = UserType.USER;
                } else {
                    userType = UserType.GUEST;
                }
            }
        }

        return userType;
    }

    private List<OrderGiftItem> getOrderGiftItemList(String orderCode, int orderSequence, int itemSequence) throws Exception {

        OrderGiftItemDto param = new OrderGiftItemDto(orderCode, orderSequence, itemSequence);

        Iterable<OrderGiftItem> orderItems = orderGiftItemRepository.findAll(param.getPredicate());

        return Lists.newArrayList(orderItems.iterator());
    }


    private int getMaxGiftSequence(String orderCode, int orderSequence, int itemSequence) throws Exception {

        List<OrderGiftItem> list = getOrderGiftItemList(orderCode, orderSequence, itemSequence);

        int maxSequence = 0;

        if (list != null && !list.isEmpty()) {

            List<Integer> giftSequneces = new ArrayList<>();

            List<OrderGiftItem> giftItemList = list.stream().filter(
                    giftItem -> orderCode.equals(giftItem.getOrderCode())
                            && orderSequence == giftItem.getOrderSequence()
                            && itemSequence == giftItem.getItemSequence()
            ).collect(Collectors.toList());

            giftItemList.stream().forEach(gift -> giftSequneces.add(gift.getGiftSequence()));

            OptionalInt max = giftSequneces.stream().mapToInt(Integer::intValue).max();

            maxSequence = max.orElse(0);

        }

        return maxSequence;
    }

    @Override
    public List<OrderGiftItem> getOrderGiftItemListByOrderCode(String orderCode) {

        if (StringUtils.isEmpty(orderCode)) {
            return new ArrayList<>();
        }

        OrderGiftItemDto param = new OrderGiftItemDto();
        param.setOrderCode(orderCode);

        Iterable<OrderGiftItem> orderItems = orderGiftItemRepository.findAll(param.getPredicate());

        return Lists.newArrayList(orderItems.iterator());
    }

    @Override
    public List<OrderGiftItem> getOrderGiftItemListByOrderCodes(String... orderCode) {

        if (orderCode == null || orderCode.length == 0) {
            return new ArrayList<>();
        }

        OrderGiftItemDto param = new OrderGiftItemDto();
        param.setOrderCodes(orderCode);

        Iterable<OrderGiftItem> orderItems = orderGiftItemRepository.findAll(param.getPredicate());

        return Lists.newArrayList(orderItems.iterator());
    }
}
