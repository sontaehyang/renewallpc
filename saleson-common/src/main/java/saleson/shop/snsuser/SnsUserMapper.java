package saleson.shop.snsuser;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.security.userdetails.User;

import saleson.shop.snsuser.domain.SnsUser;
import saleson.shop.snsuser.domain.SnsUserDetail;

import java.util.List;

/**
 * @author	seungil.lee
 * @since	2017-05-24
 */

@Mapper("snsUserMapper")
public interface SnsUserMapper {
	// sns정보로 snsUser존재 여부 체크
	SnsUserDetail getSnsUserDetail(SnsUserDetail snsUserDetail);

    // sns정보로 snsUser존재 여부 체크
	SnsUserDetail getUserSnsInfo(SnsUserDetail snsUserDetail);

	// email로 등록 된 아이디가 있는지 확인
	int getDuplicatedUserCount(SnsUserDetail snsUserDetail);
	
	// sns연결 정보 등록
	int insertSnsUser(SnsUser snsUser);

	// SNS 계정정보 등록
	int insertUserSnsInfo(SnsUserDetail snsUserDetail);

	void insertSnsUserDetail(SnsUserDetail snsUserDetail);
	
	// sns연결 정보 받아오기
	SnsUser getSnsUserInfo(SnsUser snsUser);

	// sns연결 정보 받아오기 - OP_USER_SNS
	SnsUser getUserSns(SnsUser snsUser);

	// sns연결 정보 받아오기 - SNS 연동 설정
	List<SnsUser> getUserSnsList(SnsUser snsUser);

	// sns 연결 끊기
	void disconnectSns(SnsUserDetail snsUserDetail);
	
	// loginId, userName update
	void updateUser(User user);
	
	// SNS -> 쇼핑몰 아이디로 인증일 update
	void updateSnsUser(User user);

	// SNS -> 쇼핑몰 아이디로 인증일 update (OP_USER_SNS)
	void updateUserSns(User user);

	// 탈퇴시 SNS인증 내역 삭제
	void secedeProcess(User user);

	// 탈퇴시 SNS인증 내역 삭제 - OP_USER_SNS
	void secedeSnsProcess(User user);
}
