package saleson.shop.userlevel;

import java.util.List;

import saleson.shop.user.domain.UserDetail;
import saleson.shop.userlevel.domain.UserLevel;
import saleson.shop.userlevel.domain.UserLevelLog;
import saleson.shop.userlevel.support.UserLevelSearchParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("userlevelMapper")
public interface UserLevelMapper {
	int getUserLevelCount(UserLevelSearchParam userLevelSearchParam);
	List<UserLevel> getUserLevelList(UserLevelSearchParam userLevelSearchParam);
	UserLevel getUserLevelDetail(UserLevelSearchParam userLevelSearchParam);
	void insertUserLevel(UserLevel userLevel);
	void updateUserLevel(UserLevel userLevel);
	void deleteUserLevel(int levelId);
	void updateUserLevelFileDelete(int levelId);
	
	/**
	 * 회원 등급 조회 - 회원 그룹별
	 * @param groupCode
	 * @return
	 */
	List<UserLevel> getUserLevelListByGroupCode(String groupCode);
	
	/**
	 * 배송비 쿠폰을 발행하는 레벨 조회
	 * @return
	 */
	List<UserLevel> getUserLevelListForGiftShippingCoupon();
	
	/**
	 * 회원에게 설정되어야 하는 레벨 조회 - 결제 확인일 기준
	 * @param param
	 * @return
	 */
	UserLevel getLevelToBeSettingToUser(UserLevelSearchParam param);
	
	/**
	 * 회원 등급 변경 로그 기록
	 * @param log
	 */
	void insertUserLevelLog(UserLevelLog log);
	
	/**
	 * 회원정보의 등급 수정
	 * @param userDetail
	 * @return
	 */
	int updateUser(UserDetail userDetail);
	
	/**
	 * 등급 정보 조회
	 * @param levelId
	 * @return
	 */
	UserLevel getUserLevelById(int levelId);


	/**
	 * 회원 등급 설정 정보 조회 (등급산정시 사용)
	 * @return
	 */
	List<UserLevel> getUserLevelsAll();
}
