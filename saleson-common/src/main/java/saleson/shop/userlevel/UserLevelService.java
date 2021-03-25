package saleson.shop.userlevel;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import saleson.shop.userlevel.domain.UserLevel;
import saleson.shop.userlevel.support.UserLevelSearchParam;

import com.onlinepowers.framework.security.userdetails.User;


public interface UserLevelService {
	
	/**
	 * 회원 등급 조회 - 회원 그룹별
	 * @param groupCode
	 * @return
	 */
	public List<UserLevel> getUserLevelListByGroupCode(String groupCode);
	
	/**
	 * 회원 레벨 카운트
	 * @param userLevelSearchParam
	 * @return
	 */
	public int getUserLevelCount(UserLevelSearchParam userLevelSearchParam);
	
	/**
	 * 회원 레벨 리스트
	 * @return
	 */
	public List<UserLevel> getUserLevelList(UserLevelSearchParam userLevelSearchParam);
	
	/**
	 * 회원 레벨 상세 정보
	 * @param userLevelSearchParam
	 * @return
	 */
	public UserLevel getUserLevelDetail(UserLevelSearchParam userLevelSearchParam);
	
	/**
	 * 회원 레벨 등록
	 * @param userLevel
	 */
	public void insertUserLevel(UserLevel userLevel,MultipartFile[] multipartFiles);
	
	/**
	 * 회원 레벨 수정
	 * @param userLevel
	 */
	public void updateUserLevel(UserLevel userLevel,MultipartFile[] multipartFiles);
	
	/**
	 * 회원 레벨 삭제
	 * @param levelId
	 */
	public void deleteUserLevel(int levelId);
	
	/**
	 * 회원 레벨 파일 삭제
	 * @param levelId
	 */
	public void updateUserLevelFileDelete(int levelId);
	
	/**
	 * 회원 레벨 정보를 그룹코드별로 묶는다
	 * @param groupCode
	 * @return
	 */
	public HashMap<String, List<UserLevel>> getUserLevelGroupList(String groupCode);

	/**
	 * 회원 레벨 변경
	 * @param user
	 */
	public void setUserLevel(User user);
}
