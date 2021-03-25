package saleson.shop.snsuser;

import java.util.List;
import java.util.Map;

import com.onlinepowers.framework.security.userdetails.User;

import saleson.shop.snsuser.domain.SnsUser;
import saleson.shop.snsuser.domain.SnsUserDetail;

/**
 * @author	seungil.lee
 * @since	2017-05-24
 */

public interface SnsUserService {
	// sns정보로 snsUser존재 여부 체크
	public SnsUserDetail getSnsUserDetail(SnsUserDetail snsUserDetail);

	// sns정보로 snsUser존재 여부 체크
	public SnsUserDetail getUserSnsInfo(SnsUserDetail snsUserDetail);

	// email로 등록 된 아이디가 있는지 확인
	public int getDuplicatedUserCount(SnsUserDetail snsUserDetail);

	// sns연결 정보 등록
	public void insertSnsUser(SnsUserDetail snsUserDetail);
	
	// sns로 회원가입 Process
	public Map<String, String> joinProcess(SnsUserDetail snsUserDetail, Map<String, String> map, SnsUserDetail snsUserData);

	// sns연결 정보 받아오기
	public SnsUser getSnsUserInfo(SnsUser snsUser);

	// sns연결 정보 받아오기 - OP_USER_SNS
	public SnsUser getUserSns(SnsUser snsUser);

	// sns연결 정보 받아오기 - SNS 연동설정
	public List<SnsUser> getUserSnsList(SnsUser snsUser);

	// sns 연결 끊기
	public void disconnectSns(SnsUserDetail snsUserDetail);
	
	// loginId, userName update
	public void updateUser(User user);
	
	public void updateSnsUser(User user);
	
	// 탈퇴시 SNS인증 내역 삭제
	public void secedeProcess(User user);

	// 탈퇴시 SNS인증 내역 삭제 - OP_USER_SNS
	public void secedeSnsProcess(User user);
}
