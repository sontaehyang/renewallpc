package saleson.shop.usersns;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.ShadowUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.utils.UserUtils;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.AuthUserInfo;
import saleson.shop.user.domain.UserDetail;
import saleson.shop.usersns.domain.UserSns;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("UserSnsService")
public class UserSnsServiceImpl implements UserSnsService {

    @Autowired
    private UserSnsMapper userSnsMapper;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserSns> getUserSnsList(UserSns userSns) {
        return userSnsMapper.getUserSnsList(userSns);
    }

    @Override
    public void secedeSnsProcess(User user) {
        userSnsMapper.secedeSnsProcess(user);
    }

    @Override
    public void updateUser(User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userSnsMapper.updateUser(user);
        userSnsMapper.updateUserSns(user);
    }

    @Override
    public UserSns getUserSnsInfo(UserSns userSns) {
        return userSnsMapper.getUserSnsInfo(userSns);
    }

    @Override
    public int getDuplicatedUserCount(UserSns userSns) {
        return userSnsMapper.getDuplicatedUserCount(userSns);
    }

    @Override
    public UserSns getUserSns(UserSns userSns) {
        return userSnsMapper.getUserSns(userSns);
    }

    @Override
    public void insertUserSns(UserSns userSns) {
        userSns.setSnsUserId(sequenceService.getId("OP_USER_SNS"));
        if (!userSns.getIsMypage() || userSns.getSnsUserId()==0) {
            if (UserUtils.isUserLogin()) {
                userSns.setUserId(UserUtils.getUserId());
                userSns.setCertifiedDate("Y");
            } else {
                userSns.setUserId(userSns.getUserId());
            }
        }
        userSnsMapper.insertUserSnsInfo(userSns);
    }

    @Override
    public void disconnectSns(UserSns userSns) {
        userSnsMapper.disconnectSns(userSns);
    }

    @Override
    public Map<String, String> joinProcess(UserSns userSns2, Map<String, String> map, UserSns userSnsData) {
        UserSns userSns = this.getUserSnsInfo(userSnsData);

		if (userSns == null) {
			int userCount = this.getDuplicatedUserCount(userSns);

			if (userCount != 0) {
                map.put("value", "01");
                map.put("message", "?????? ?????? ???????????? ?????? ??? ???????????? ???????????????.");
                return map;
            } else {
                if (!UserUtils.isUserLogin()) {
                    User user = new User();
                    UserDetail userDetail = new UserDetail();

                    // [SKC_2018-12-10] loginId ??????
                    String loginId = new StringBuilder()
                            .append(userSnsData.getSnsType() == null ? 'n' : userSnsData.getSnsType().toLowerCase())
                            .append("-")
                            .append(userSnsData.getSnsId())
                            .toString();
                    userSnsData.setLoginId(loginId);
                    user.setLoginId(loginId);

                    // user.setPassword
                    String radomPassword = UUID.randomUUID().toString().substring(0, 8);
                    user.setPassword(passwordEncoder.encode(radomPassword));

                    // user.setEmail
                    user.setEmail(userSnsData.getEmail());

                    // user.setName
                    user.setUserName(userSnsData.getSnsName());

                    // userDetail.setNickname
                    //userDetail.setNickname(snsUserData.getSnsName());

                    userDetail.setSiteFlag("3");
                    userService.insertUserAndUserDetailForSns(user, userDetail, userSnsData);

                    this.joinProcess(userSns, map, userSnsData);
                } else {
                    // mypage????????? joinProcess ??????
                    userSnsData.setUserId(UserUtils.getUserId());
                    if (userSnsData.getIsMypage()) {
                        userSnsData.setCertifiedDate(DateUtils.getToday(Const.DATETIME_FORMAT));
                    }

                    this.insertUserSns(userSnsData);

                    map.put("value", "00");
                    map.put("message", "SNS?????? ????????? ???????????? ???????????????.");
                    map.put("userId", String.valueOf(UserUtils.getUserId()));
                }
            }
        } else {
            // mypage??? ?????? sns????????? ?????? ????????? ?????? ??????
            if (userSnsData.getIsMypage()) {
                map.put("value", "01");
                map.put("message", "?????? ????????? SNS???????????????.");
            } else {
                // login????????? joinProcess ??????
                map.put("value", "00");
                map.put("message", "SNS?????? ???????????? ??????????????? ?????????????????????.");
                map.put("userId", String.valueOf(userSns.getUserId()));
                map.put("loginId", userSns.getLoginId());

                String loginId = userSns.getLoginId();
                map.put("shadowUserId", loginId);
                map.put("shadowLoginKey", ShadowUtils.getShadowLoginKey(loginId, ""));
                map.put("shadowLoginPassword", ShadowUtils.getShadowLoginPassword(loginId));
                map.put("shadowLoginSignature", ShadowUtils.getShadowLoginSignature(loginId));
            }
        }

        return map;
    }

    @Override
    public int getUserSnsCount(AuthUserInfo authUserInfo) {
        return userSnsMapper.getUserSnsCount(authUserInfo);
    }

    @Override
    public int getUserSnsCertifyCount(long userId) {
        return userSnsMapper.getUserSnsCertifyCount(userId);
    }
}
