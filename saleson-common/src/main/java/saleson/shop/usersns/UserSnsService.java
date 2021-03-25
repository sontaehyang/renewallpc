package saleson.shop.usersns;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.shop.user.domain.AuthUserInfo;
import saleson.shop.usersns.domain.UserSns;

import java.util.List;
import java.util.Map;

public interface UserSnsService {

    // sns연결 정보 받아오기 - SNS 연동설정
    public List<UserSns> getUserSnsList(UserSns userSns);

    // 해당 SNS 타입 계정 정보 조회
    public UserSns getUserSnsInfo(UserSns userSns);

    // loginId, userName update
    public void updateUser(User user);

    // 탈퇴시 SNS인증 내역 삭제 - OP_USER_SNS
    public void secedeSnsProcess(User user);

    // sns 연결 끊기
    public void disconnectSns(UserSns userSns);

    // email로 등록 된 아이디가 있는지 확인
    public int getDuplicatedUserCount(UserSns userSns);

    // sns연결 정보 받아오기 - OP_USER_SNS
    public UserSns getUserSns(UserSns userSns);

    // SNS로 회원가입 process
    public Map<String, String> joinProcess(UserSns userSns, Map<String, String> map, UserSns userSnsData);

    // SNS 연결 정보 등록
    public void insertUserSns(UserSns userSns);

    /**
     * SNS 연동된 회원인지 조회
     * @param authUserInfo
     */
    int getUserSnsCount(AuthUserInfo authUserInfo);

    /**
     * SNS 연동된 회원 중 정보 입력이 완료된 회원인지 조회
     * @param userId
     */
    int getUserSnsCertifyCount(long userId);
}
