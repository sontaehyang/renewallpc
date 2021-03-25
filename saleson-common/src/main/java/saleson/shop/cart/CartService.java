package saleson.shop.cart;

import java.util.List;

import saleson.shop.cart.domain.Cart;
import saleson.shop.cart.support.CartParam;
import saleson.shop.order.domain.Buy;
import saleson.shop.order.domain.BuyItem;

public interface CartService {

    /**
     * 장바구니 리스트
     * @param cartParam
     * @param isSetPoint
     * @return
     */
    public List<BuyItem> getCartList(CartParam cartParam, boolean isSetPoint);

    /**
     * 사용자 장바구니 데이터 조회
     * @param cartParam
     * @return
     */
    Buy getBuyInfoByCart(CartParam cartParam);

    /**
     * 사용자 견적서 데이터 조회
     * @param cartParam
     * @return
     */
    Buy getBuyInfoByQuotation(Cart cartParam);

    /**
     * 장바구니에 있는 데이터를 주문 상품 임시 테이블로 복사
     * @param cartParam
     */
    public void copyCartToOrderItemTemp(CartParam cartParam);

    /**
     * 바로 구매
     * @param cartParam
     * @return
     */
    public void immediatelyBuy(Cart cart);

    /**
     * 장바구니 데이터 저장
     * @param cart
     */
    public int insertCart(Cart cart);

    /**
     * 장바구니 수량 변경
     * @param cartParam
     */
    public void updateQuantity(CartParam cartParam);

    /**
     * 사용자 로그인시 회원ID를 세션ID로 조회해서 업데이트
     * @param cartParam
     */
    public void updateUserIdBySessionId(long userId, String sessionId);

    /**
     * 장바구니 선택 삭제
     * @param cartParam
     */
    public void deleteListData(CartParam cartParam);

    /**
     * 장바구니 배송비 지불방법 설정
     * @param cartParam
     */
    public void updateShippingPaymentType(CartParam cartParam);

    /**
     *
     * @param cart
     * @return
     */
    public List<Cart> makeCartListByCart(Cart cart);
}
