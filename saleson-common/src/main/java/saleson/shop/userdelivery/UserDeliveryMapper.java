package saleson.shop.userdelivery;

import java.util.List;

import saleson.shop.userdelivery.domain.UserDelivery;
import saleson.shop.userdelivery.support.UserDeliveryParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("userDeliveryMapper")
public interface UserDeliveryMapper {
	
	/**
	 * 회원이 등록한 배송지 목록을 조회
	 * @param param
	 * @return
	 */
	List<UserDelivery> getUserDeliveryListByParam(UserDeliveryParam param);
	
	/**
	 * 회원의 기본 배송지를 조회
	 * @param param
	 * @return
	 */
	UserDelivery getDefaultUserDelivery(UserDeliveryParam param);
	
	/**
	 * 회원이 등록한 배송지 상세 정보 조회
	 * @param param
	 * @return
	 */
	UserDelivery getUserDeliveryById(UserDeliveryParam param);
	
	/**
	 * 배송지 등록
	 * @param userDelivery
	 */
	void insertUserDelivery(UserDelivery userDelivery);
	
	/**
	 * 배송지 수정
	 * @param userDelivery
	 */
	void updateUserDelivery(UserDelivery userDelivery);
	
	/**
	 * 기본 배송지를 초기화함
	 * @param userId
	 */
	void initializationDefaultFlag(long userId);
	
	/**
	 * 기본 배송지 설정
	 * @param param
	 */
	void updateDefaultFlagByParam(UserDeliveryParam param);
	
	/**
	 * 배송지 선택 삭제
	 * @param param
	 */
	void deleteUserDeliveryByParam(UserDeliveryParam param);

	/**
	 * 배송지 카운트
	 * @param param
	 */
	int getDeliveryCount(UserDeliveryParam param);

	/**
	 * 회원이 등록한 배송지 목록을 조회 - API 용
	 * @param param
	 * @return
	 */
	public List<UserDelivery> getUserApiDeliveryList(UserDeliveryParam param);
}
