package saleson.shop.user;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import saleson.common.opmanager.count.OpmanagerCount;
import saleson.shop.coupon.domain.ChosenUser;
import saleson.shop.order.domain.Buyer;
import saleson.shop.stats.support.StatsSearchParam;
import saleson.shop.user.domain.AuthUserInfo;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.user.support.UserSearchParam;
import saleson.shop.userlevel.domain.UserLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper("userMapper")
public interface UserMapper {

	/**
	 * 회원ID로 회원정보를 조회한다. (스테이터스 코드 제외)
	 * @param userId
	 * @return
	 */
	User getUserByUserIdNotStatusCode(long userId);
	
	
	UserDetail getUserDetail(long userId);

	/**
	 * 검색 조건에 해당하는 회원 수를 가져옴.
	 * @param searchParam
	 * @return
	 */
	int getUserCount(UserSearchParam searchParam);

	
	/**
	 * 검색 조건에 해당하는 회원 목록을 가져옴..
	 * @param searchParam
	 * @return
	 */
	List<User> getUserList(UserSearchParam searchParam);
	
	/**
	 * 핸드폰번호를 사용중인 회원 수를 조회
	 * @param phoneNumber
	 * @return
	 */
	int getUserCountByPhoneNumber(String phoneNumber);
	
	/**
	 * Email을 사용중인 회원 수를 조회
	 * @param email
	 * @return
	 */
	int getUserCountByEmail(String email);

	/**
	 * LoginId를 사용중인 회원 수를 조회
	 * @param email
	 * @return
	 */
	int getUserCountByLoginId(String loginId);
	
	/**
	 * 닉네임을 사용중인 회원 수를 조회
	 * @param nickname
	 * @return
	 */
	int getUserCountByNickname(String nickname);
	
	/**
	 * 사용자가 입력한 데이터를 사용중인 회원수를 조회
	 * @param loginId
	 * @return
	 */
	public int getUserCountByUserInfo(User user);
	
	/**
	 * 관리자 등록시 Email과 LoginId를 둘다 조회함
	 * @param loginId
	 * @return
	 */
	int getUserCountByManagerId(String loginId);

	
	/**
	 * 회원을 등록한다.
	 * @param user
	 */
	void insertUser(User user);
	
	
	/**
	 * 회원 정보를 수정한다.
	 * @param user
	 */
	void updateUser(User user);
	
	void deleteUserDetail(long userId);
	void deleteUser(long userId);
	

	
	/**
	 * 회원 상세 정보를 등록한다.
	 * @param userDetail
	 */
	void insertUserDetail(UserDetail userDetail);
	
	/**
	 * 회원 상세 정보를 수정한다.
	 * @param userDetail
	 */
	void updateUserDetail(UserDetail userDetail);

	
	/**
	 * 로그 ID로 권한 정보 목록을 조회한다.
	 * @param userId
	 * @return
	 */
	List<UserRole> getUserRoleListByLoginId(String loginId);
	
	/**
	 * USER_ID로 권한 정보 목록을 조회한
	 * @param userId
	 * @return
	 */
	List<UserRole> getUserRoleListByUserId(long userId);
	
	/**
	 * 회원의 역할 정보를 등록한다.
	 * @param userRole
	 */
	void insertUserRole(UserRole userRole);
	
	
	/**
	 * 회원의 역할 정보를 수정한다.
	 * @param userRole
	 */
	void updateUserRole(UserRole userRole);
	
	
	/**
	 * 비밀번호가 일치하는 회원 수 
	 * 
	 * KB사회공헌은 휴대폰번호를 비밀번호로 사용하므로 동일 비밀번호가 존재하지 않음.
	 * 등록 수정 시 체크..
	 * 
	 * @param password
	 * @return
	 */
	int getPasswordCount(String password);
	
	
	/**
	 * 비밀번호가 일치하는 회원 수 
	 * 
	 * 현재 회원 ID를 제외하고 비밀번호(전화번호)가 일치하는 수를 조회함.
	 * @param user
	 * @return
	 */
	int getPasswordCountByUser(User user);

	
	/**
	 * userId 인 회원을 삭제(탈퇴) 처리한다.
	 * KB사회공헌에서는 STAUTUS_CODE = -1로 처리 
	 * @param parseInt
	 */
	void deleteUserById(long userId);

	
	/**
	 * 관리자 카운트를 가져온다.
	 * @param searchParam
	 * @return
	 */
	int getUserManagerCount(UserSearchParam searchParam);

	
	/**
	 * 관리자 목록을 조회한다.
	 * @param searchParam
	 * @return
	 */
	List<User> getUserManagerList(UserSearchParam searchParam);

	
	/**
	 * 이름으로 검색하여 KB 직원 목록을 가져옴.
	 * @param userName
	 * @return
	 */
	List<UserDetail> getKbUserListByUsername(String userName);

	
	/**
	 * 검색 조건에 해당 하는 KB직원을 검색한다..
	 * @param searchParam
	 * @return
	 */
	List<UserDetail> getKbUserListBySearchParam(UserSearchParam searchParam);

	
	/**
	 * 직원 수를 조회한다.
	 * @param searchParam
	 * @return
	 */
	int getEmployeeCount(UserSearchParam searchParam);

	
	/**
	 * 직원 목록을 가져옴.
	 * @param searchParam
	 * @return
	 */
	List<User> getEmployeeList(UserSearchParam searchParam);


	
	/**
	 * 전제 직원 목록을 가져옴..
	 * @return
	 */
	List<User> getUserEmployeeListAll();

	
	
	
	/**
	 * 아이디 / 비밀번호로 회원이 있는 지 조회한다..
	 * @param user
	 * @return
	 */
	int getUserCustmerCount(User user);

	
	/**
	 * 회원 정보를 user.loginId 기준으로 업데이트 한다.
	 * @param user
	 */
	void updateUserByLoginId(User user);


	
	/**
	 * 회원 권한 정보를 MERGE 한다. 
	 * @param userRole
	 */
	void mergeUserRole(UserRole userRole);

	
	/**
	 * 회원 권한 정보를 삭제한다.
	 * @param userRole
	 */
	void deleteUserRole(long userId);

	/**
	 * NGO 회원에 대한 정보를 userId로 검색해서 조회 한다.
	 * @param userId
	 * @return
	 */
	String getNgoUserByUserId(long userId);
	
	/**
	 * 회원에 대한 정보를 userId로 검색해서 조회 한다.
	 * @param userId
	 * @return
	 */
	int getUserCountByUserId(long userId);

	
	/**
	 * 회원ID로 회원정보를 조회한다.
	 * @param userId
	 * @return
	 */
	User getUserByUserId(long userId);

	
	/**
	 * 로그인 ID로 회원을 조회한다.
	 * @param loginId
	 * @return
	 */
	User getUserByLoginId(String loginId);
	
	

	
	/**
	 * 해당 권한 조건에 해당하는 UserRole 을 삭제한다.
	 * @param string
	 */
	void deleteUserRoleByAuthority(String string);


	
	/**
	 * KB 관리자 중 부서 변경 및 퇴사 직원의 권한 삭제를 위한 대상자 USER_IDs 조회함.
	 * @return
	 */
	List<Integer> getUserIdsOfNonManager();
	
	
	/**
	 * 관리자 > 통계 > 봉사활동 통계 > 부서별 평균인원 카운트.
	 * @param statsSearchParam
	 * @return
	 */
	int getHeadcountStatsCount(StatsSearchParam statsSearchParam);
	
	
	
	/**
	 * 회원 전체 조회수를 가져옴.
	 * @param authority
	 * @return
	 */
	int getUserTotalCount(String authority);
	
	/**
	 * 회원 구매 정보 업데이트 - 관리자 배송완료 처리 시점
	 * @param userDetail
	 */
	void updateUserBuyInfoForOrder(UserDetail userDetail);
	
	/**
	 * 회원정보를 조회한다.
	 * @param searchParam
	 * @return
	 */
	User getUserByParam(UserSearchParam searchParam);
	
	/**
	 * 회원 UserDetail Count
	 * @param userId
	 * @return
	 */
	int getUserDetailCountByUserId(long userId);
	

	/**
	 * 회원 아이디 찾기
	 * @param authUserInfo
	 * @return
	 */
	User getUserInfoByUserName(AuthUserInfo authUserInfo);
	
	/**
	 * 탈퇴 회원 리스트 카운트
	 * @param searchParam
	 * @return
	 */
	int getSecedeUserCount(UserSearchParam searchParam);
	
	/**
	 * 탈퇴 회원 리스트
	 * @param searchParam
	 * @return
	 */
	List<User> getSecedeUserList(UserSearchParam searchParam);
	
	/**
	 * 탈퇴 회원정보 삭제
	 * @param user
	 */
	void updateSecedeUser(User user);
	
	/**
	 * 탈퇴회원 상세정보 삭제
	 * @param userDetail
	 */
	void updateSecedeUpdateUserDetail(UserDetail userDetail);

	/**
	 * 운영자 메뉴 ROLE 정보 목록.
	 * @return
	 */
	List<HashMap<String, String>> getAdminMenuRoleList();
	
	/**
	 * 사용자 비밀번호 변경
	 * @param user
	 */
	void updateUserPasswordByUserId(User user);

	public List<ChosenUser> getChosenUserList(List<String> list);
	public List<ChosenUser> getChosenUserListbyParam(ChosenUser chosenUser);
	public List<ChosenUser> getUserListForChosen(UserSearchParam userSearchParam);

	/**
	 * 관리자 카운트 
	 * @param searchParam
	 * @return
	 */
	int getManagerCount(UserSearchParam searchParam);


	/**
	 * 관리자 목록 
	 * @param searchParam
	 * @return
	 */
	List<User> getManagerList(UserSearchParam searchParam);

	/**
	 * 관리자 역할 정보 조회 
	 * @param userId
	 * @return
	 */
	List<UserRole> getManagerRoleListByUserId(long userId);


	/**
	 * 관리자 정보 조회 
	 * @param userId
	 * @return
	 */
	User getManagerByUserId(long userId);

	/**
	 * 이메일로 관리자 카운트 조회 
	 * @param email
	 * @return
	 */
	int getManagerCountByEmail(String email);

	/**
	 * 관리자 정보 수정. 
	 * @param user
	 */
	void updateManager(User user);

	/**
	 * 관리자 등록
	 * @param user
	 */
	void insertManager(User user);

	/**
	 * 관리자 삭제 
	 * @param parseInt
	 */
	void deleteManagerByUserId(long parseInt);
	
	/**
	 * 휴면 안내 메일 발송 대상자
	 * @return
	 */
	List<User> getWaitSleepUser();
	
	/**
	 * 휴면 안내 메일 대상자
	 */
	void updateWaitSleepUser(List<User> list);
	
	/**
	 * 휴면 대상자 리스트 조회
	 * @return
	 */
	List<User> getUserListForSleepTarget();
	
	/**
	 * 휴면 계정 처리
	 * @param user
	 */
	void updateUserToSleep(List<User> list);

	/**
	 * 휴면 계정 처리
	 * @param user
	 */
	void updateUserDetailToSleep(List<User> list);

	/**
	 * 휴면 계정 정보 저장
	 * @param list
	 */
	void insertSleepUser(List<User> list);
	
	/**
	 * 휴면 전환 데이터 조회
	 * @param user
	 * @return
	 */
	User getUserForWakeup(User user);
	
	/**
	 * 휴면 계정 복구
	 * @param user
	 */
	void wakeupUser(User user);

	/**
	 * 휴면 계정 복구
	 * @param user
	 */
	void wakeupUserDetail(User user);

	/**
	 * 휴면 계정 정보 삭제
	 * @param user
	 */
	void deleteSleepUser(User user);
	
	/**
	 * 이상우 [2017-05-11 추가]
	 * 관리자 메인 방문자,가입자(오늘, 주중) 카운트
	 * @param param
	 */
	List<OpmanagerCount> getOpmanagerUserCountAll(Map<String, String> param);


	/**
	 * 유율선 [2017-05-18 추가]
	 * 주문자 정보 기본 정보로 설정
	 * @param buyer
	 */
	void updateUserDetailForOrder(Buyer buyer);

	/**
	 * 해당 관리자 비밀번호 변경
	 * @param user
	 */
	int updatePasswordForManager(User user);

	/**
	 * 해당 회원 비밀번호 변경
	 * @param user
	 */
	int updatePasswordForUser(User user);

	/**
	 * 해당 회원 비밀번호 변경 유예
	 * @param user
	 */
	int updatePasswordExpiredDateForUser(User user);

	/**
	 * 해당 등급에 맞는 회원 수 조회 (주문데이터에서 확인)
	 * @param userLevel
	 * @return
	 */
	int getUserCountByUserLevel(UserLevel userLevel);


	/**
	 * 회원 레벨 변경 로그 등록
	 * @param userLevel
	 */
	int insertUserLevelLogByUserLevel(UserLevel userLevel);

	/**
	 * 해당 등급에 맞는 회원의 등급 정보를 일괄 업데이트 (주문데이터에서 확인)
	 * @param userLevel
	 */
	int updateUserDetailByUserLevel(UserLevel userLevel);

	void updatePasswordByAsisUser(User user);

	/**
	 * +-day 일에 구매확정한 회원 목록 조회 (UMS)
	 * @param day
	 * @return
	 */
	List<UserDetail> getConfirmPurchaseUserList(int day);

	/**
	 * 배송중 상태값 변경일로부터 day 일 이후,
	 * 구매확정 요청 회원 목록 조회 (UMS)
	 * @return
	 */
	List<UserDetail> getConfirmPurchaseRequestUserList(int day);

	/**
	 * 회원 아이디 찾기 (SNS 연동 계정 제외)
	 * @param authUserInfo
	 * @return
	 */
	User getUserInfoExceptForSnsUser(AuthUserInfo authUserInfo);
}
