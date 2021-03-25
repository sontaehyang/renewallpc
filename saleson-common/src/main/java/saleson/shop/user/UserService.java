package saleson.shop.user;

import java.util.HashMap;
import java.util.List;

import com.onlinepowers.framework.exception.UserException;
import org.springframework.ui.Model;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import com.onlinepowers.framework.web.domain.ListParam;

import saleson.common.opmanager.count.OpmanagerCount;
import saleson.shop.coupon.domain.ChosenUser;
import saleson.shop.order.domain.Buyer;
import saleson.shop.snsuser.domain.SnsUserDetail;
import saleson.shop.user.domain.*;
import saleson.shop.user.support.UserSearchParam;
import saleson.shop.usersns.domain.UserSns;

import javax.servlet.http.HttpSession;


public interface UserService {
	
	/**
	 * 마이페이지 상단 데이터 - 공통
	 * @param model
	 */
	public void setMypageUserInfoForFront(Model model);
	
	/**
	 * 회원 비밀번호 수동 변경
	 * @param user
	 */
	public void updateUserPasswod(User user);
	
	public UserDetail getUserDetail(long userId);

	/**
	 * 회원 수를 가져옴.
	 * @param searchParam
	 * @return
	 */
	public int getUserCount(UserSearchParam searchParam);
	
	
	/**
	 * 검색 조건에 해당하는 회원 목록을 가져옴.
	 * @param searchParam
	 * @return
	 */
	public List<User> getUserList(UserSearchParam searchParam);
	/**
	 * 회원등록.
	 * @param user
	 * @return
	 */
	public void insertUser(User user);
	
	public void insertUserDetail(UserDetail userDetail);
	
	public void insertUserRole(UserRole userRole);
	
	public void insertUserAndUserDetailByManager(User user, UserDetail userDetail);
	
	public void insertUserAndUserDetail(User user, UserDetail userDetail);

	public void insertUserAndUserDetailForSns(User user, UserDetail userDetail , UserSns UserSns);
	
	/**
	 * 회원수정.
	 * @param user
	 * @return
	 */
	public void updateUser(User user);
	
	public void updateUserDetail(UserDetail userDetail);
	
	public void updateUserRole(UserRole userRole);
	
	public void updateUserAndUserDetail(User user, UserDetail userDetail);
	
	/**
	 * 회원삭제.
	 * @param userId
	 * @return
	 */
	public void deleteUser(long userId);
	
	public void deleteUserDetail(long userId);
	
	public void deleteUserRole(long userId);

	public int getUserCountByPhoneNumber(String phoneNumber);
	
	/**
	 * Email 주소를 사용중인 회원수를 조회
	 * @param email
	 * @return
	 */
	public int getUserCountByEmail(String email);

	/**
	 * LoginId 를 이용한 회원 수 조회
	 * @param loginId
	 * @return
	 */
	public int getUserCountByLoginId(String loginId);
	
	/**
	 * 닉네임을 사용중인 회원 수를 조회
	 * @param nickname
	 * @return
	 */
	public int getUserCountByNickname(String nickname);

	/**
	 * 사용자가 입력한 데이터를 사용중인 회원수를 조회
	 * @param user
	 * @return
	 */
	public int getUserCountByUserInfo(User user); 
	
	/**
	 * 로그인 ID에 해당 하는 회원 수를 조회한다..
	 * @param loginId
	 * @return
	 */
	public int getUserCountByManagerId(String loginId);

	
	/**
	 * 관리자 카운트를 가져옴.
	 * @param searchParam
	 * @return
	 */
	public int getUserManagerCount(UserSearchParam searchParam);

	/**
	 * 회원ID로 회원을 조회한다.
	 * @param userId
	 * @return
	 */
	public User getUserByUserId(long userId);
	
	/**
	 * loginID로 회원을 조회한다.
	 * @param loginId
	 * @return
	 */
	public User getUserByLoginId(String loginId);

	/**
	 * 회원 전체 카운트를 가져옴.
	 * @param authority
	 * @return
	 */
	public int getUserTotalCount(String authority);
	
	/**
	 * 회원 전체 삭제 및 선택 삭제
	 * @param listParam
	 */
	public void deleteUserByListParam(ListParam listParam);

	/**
	 * 회원 구매 정보 업데이트 - 관리자 배송완료 처리 시점
	 * @param userId
	 * @param price
	 */
	public void updateUserBuyInfoForOrder(long userId, int price);
	
	public void getUserPasswordSearch(UserSearchParam searchParam);
	
	public void updateFrontUserAndUserDetail(User user);
	
	public User getUserByParam(UserSearchParam searchParam);

	/**
	 * 회원 아이디 찾기
	 * @param authUserInfo
	 * @return
	 */
	public User getUserInfoByUserName(AuthUserInfo authUserInfo);
	
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
	 * 탈퇴 회원 정보 삭제
	 * @param user
	 */
	public void updateSecedeFrontUserAndUserDetail(User user);

	
	/**
	 * 운영자 메뉴 ROLE 정보 목록.
	 * @return
	 */
	public List<HashMap<String, String>> getAdminMenuRoleList();

	public void updateUserForAdmin(User user);
	
	/**
	 * 타입에 따라 문자, 메일 발송. LSW 2016.08.05 추가 (비밀번호 변경 시 발송을 위해..)
	 * 사용하지 않아 주석처리 KSH 2019.06.11 (구 SMS 로직때문에 UMS 작업시 에러)
	 */
	// public void sendSmsAndEmail(User user, String templateId);

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
	 * 관리자 선택 삭제 
	 * @param listParam
	 */
	public void deleteManagerByListParam(ListParam listParam);
	
	/**
	 * 휴면계정 안내메일 발송
	 */
	public void sendSleepUserMail();

	/**
	 * 휴면계정 처리
	 */
	public void setSleepUser();
	
	/**
	 * 휴면계정 정상화
	 */
	public void wakeupUser(User currentUser);
	
	/**
	 * 이상우 [2017-05-11 추가]
	 * 관리자 메인 방문자,가입자(오늘, 주중) 카운트
	 */
	public List<OpmanagerCount> getOpmanagerUserCountAll();

	/**
	 * 유율선 [2017-05-18 추가]
	 * 주문자 정보 기본 정보로 설정
	 * @param buyer
	 */
	public void updateUserDetailForOrder(Buyer buyer);

	/**
	 * 해당 관리자 비밀번호를 임시 비밀번호로 변경
	 * @param userId
	 */
	public void updateTempPasswordForManager(long userId) throws UserException;


	/**
	 * 해당 관리자 비밀번호 변경
	 * @param userId
	 * @param passowrd
	 * @param changePassowrd
	 */
	public void updatePasswordForManager(long userId, String passowrd, String changePassowrd) throws UserException;

	/**
	 * 해당 회원 비밀번호 변경
	 * @param userId
	 * @param passowrd
	 * @param changePassowrd
	 * @throws UserException
	 */
	public void updatePasswordForUser(long userId, String passowrd, String changePassowrd) throws UserException;

	/**
	 * 해당 회원 비밀번호 변경 유예
	 * @param userId
	 */
	public void updatePasswordExpiredDateForUser(long userId) throws UserException;

	/**
	 * 관리자 로그인 세션정보 등록
	 * @param session
	 * @param userId
	 */
	void insertLoginSessionForManager(HttpSession session, long userId);

	/**
	 * 관리자 로그인 세션정보 삭제
	 * @param userId
	 */
	void deleteLoginSessionForManager(long userId);

	/**
	 * 해당 사용자의 중복 세션 목록 조회
	 * @param userId
	 * @return
	 */
	List<ManagerLogin> getLoginSessionForManagerByUserId(long userId);

	/**
	 * 비밀번호 유효기간
	 * @return
	 */
	String getPasswordExpiredDate();

	/**
	 * 가입불가 아이디 및 회원 아이디 중복 체크
	 * @param user
	 * @return
	 */
	String checkDuplication(User user);

	/**
	 * 기존 Asis 회원 패스워드 업데이트
	 * @param userId
	 * @param password
	 */
	void updatePasswordByAsisUser(long userId, String password);

	/**
	 * 회원 아이디 찾기 (SNS 연동 계정 제외)
	 * @param authUserInfo
	 * @return
	 */
	User getUserInfoExceptForSnsUser(AuthUserInfo authUserInfo);
}
