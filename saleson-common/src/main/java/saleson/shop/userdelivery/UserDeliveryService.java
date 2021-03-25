package saleson.shop.userdelivery;

import java.util.List;

import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userdelivery.support.UserDeliveryParam;

public interface UserDeliveryService {

	/**
	 * 회원이 등록한 배송지 목록을 조회
	 * @param param
	 * @return
	 */
	public List<UserDelivery> getUserDeliveryList(long userId);
	
	/**
	 * 회원이 등록한 배송지 상세 정보 조회
	 * @param userDeliveryId
	 * @return
	 */
	public UserDelivery getUserDeliveryById(long userId, int userDeliveryId);
	
	/**
	 * 기본 배송지를 조회
	 * @return
	 */
	public UserDelivery getDefaultUserDelivery();
	
	/**
	 * 배송지 등록
	 * @param userDelivery
	 */
	public void insertUserDelivery(UserDelivery userDelivery);
	
	/**
	 * 배송지 수정
	 * @param userDelivery
	 */
	public void updateUserDelivery(UserDelivery userDelivery);
	
	/**
	 * 선택 삭제등..
	 * @param param
	 */
	public void listAction(UserDeliveryParam param);

	/**
	 * 배송지 카운트
	 * @param userId
	 */
	public int getDeliveryCount(long userId);

	/**
	 * 회원이 등록한 배송지 목록을 조회 - API 용
	 * @param param
	 * @return
	 */
	public List<UserDelivery> getUserApiDeliveryList(UserDeliveryParam param);
}
