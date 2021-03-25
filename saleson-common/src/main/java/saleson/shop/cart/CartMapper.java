package saleson.shop.cart;

import java.util.List;

import saleson.shop.cart.domain.Cart;
import saleson.shop.cart.support.CartParam;
import saleson.shop.order.domain.BuyItem;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("cartMapper")
public interface CartMapper {
	
	/**
	 * 장바구니 리스트
	 * @param cartParam
	 * @return
	 */
	List<BuyItem> getCartList(CartParam cartParam);
	
	/**
	 * 장바구니 등록전 같은 데이터가 있는지 확인한다..
	 * @param cart
	 * @return
	 */
	List<Cart> getDuplicateCart(Cart cart);
	
	/**
	 * 회원 장바구니 카운트
	 * @param userId
	 * @return
	 */
	int getCountForUserItemByUserId(long userId);
	
	/**
	 * 비회원 장바구니 목록 조회
	 * @param sessionId
	 * @return
	 */
	List<Cart> getCartListBySessionId(String sessionId);
	
	/**
	 * 비회원이 담아놓은 상품중에 회원정보로 변경할수 있는 목록 조회
	 * @param cartParam
	 * @return
	 */
	List<Cart> getGuestConvertibleItems(CartParam cartParam);
	
	/**
	 * 장바구니에 해당 상품을 등록 할수 있는지 검증 
	 * 0보다 큰경우 실패!!
	 * @param cart
	 * @return
	 */
	int getCartAvailableInsertValidationCountForItemTypeByCartParam(CartParam cartParam);
	
	/**
	 * 장바구니 데이터 저장
	 * @param cart
	 */
	void insertCart(Cart cart);

	/**
	 * 장바구니에 같은 데이터를 수량만 변경함..
	 * @param cart
	 */
	void updateDuplicateCartQuantity(Cart cart);
	
	/**
	 * 장바구니 수량 변경
	 * @param cartParam
	 */
	void updateQuantity(CartParam cartParam);
	
	/**
	 * 사용자 로그인시 회원ID를 세션ID로 조회해서 업데이트
	 * @param cartParam
	 */
	void updateUserIdByCart(Cart cart);
	
	/**
	 * 장바구니 데이터 삭제
	 * @param cart
	 */
	void deleteCart(CartParam cartParam);
	
	
	
	/**
	 * 중복 장바구니 수량 통합을 위한 삭제  
	 * @param cart
	 */
	void deleteDuplicateCart(Cart cart);
	
	/**
	 * cartId 일괄 삭제
	 * @param cartParam
	 */
	void deleteCartByCartIds(CartParam cartParam);
	
	/**
	 * 장바구니 삭제
	 * @param cartParam
	 */
	void deleteCartByItemIds(CartParam cartParam);
	
	/**
	 * 장바구니 배송비 지불방법 설정
	 * @param cartParam
	 */
	void updateShippingPaymentType(CartParam cartParam);
}
