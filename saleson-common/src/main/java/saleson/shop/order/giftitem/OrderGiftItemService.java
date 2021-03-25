package saleson.shop.order.giftitem;

import saleson.model.OrderGiftItem;

import java.util.List;

public interface OrderGiftItemService {

    /**
     * 주문시 상품별 사은품 등록
     * @param orderCode
     * @param orderSequence
     * @param itemSequence
     * @param itemId
     * @throws Exception
     */
    void insertOrderGiftItemByByItemId(String orderCode, int orderSequence, int itemSequence, int itemId)
            throws Exception;


    /**
     * 주문 상품별 사은품 취소처리
     * @param orderCode
     * @param orderSequence
     * @param itemSequence
     * @throws Exception
     */
    void cancelOrderGiftItem(String orderCode, int orderSequence, int itemSequence) throws Exception;

    /**
     * 주문 상품별 사은품 반품처리
     * @param orderCode
     * @param orderSequence
     * @param itemSequence
     * @throws Exception
     */
    void returnOrderGiftItem(String orderCode, int orderSequence, int itemSequence) throws Exception;

    /**
     * 주문별 사은품 목록 조회
     * @param orderCode
     * @return
     */
    List<OrderGiftItem> getOrderGiftItemListByOrderCode(String orderCode);

    /**
     * 주문별 사은품 목록 조회
     * @param orderCode
     * @return
     */
    List<OrderGiftItem> getOrderGiftItemListByOrderCodes(String... orderCode);
}
