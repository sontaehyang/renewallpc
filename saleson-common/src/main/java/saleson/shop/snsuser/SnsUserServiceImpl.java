package saleson.shop.snsuser;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.ShadowUtils;

import saleson.common.utils.UserUtils;
import saleson.shop.snsuser.domain.SnsUser;
import saleson.shop.snsuser.domain.SnsUserDetail;
import saleson.shop.user.UserService;
import saleson.shop.user.domain.UserDetail;

/**
 * @author	seungil.lee
 * @since	2017-05-24
 */

@Service("snsUserService")
public class SnsUserServiceImpl implements SnsUserService {

	@Autowired
	private SnsUserMapper snsUserMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public SnsUserDetail getSnsUserDetail(SnsUserDetail snsUserDetail) {
		SnsUserDetail snsUserResult = snsUserMapper.getSnsUserDetail(snsUserDetail);
		return snsUserResult;
	}

    @Override
    public SnsUserDetail getUserSnsInfo(SnsUserDetail snsUserDetail) {
        return snsUserMapper.getUserSnsInfo(snsUserDetail);
    }

    @Override
	public int getDuplicatedUserCount(SnsUserDetail snsUserDetail) {
		return snsUserMapper.getDuplicatedUserCount(snsUserDetail);
	}
	
	@Override
	public void insertSnsUser(SnsUserDetail snsUserDetail) {
		snsUserDetail.setSnsUserId(sequenceService.getId("OP_USER_SNS"));
		if (!snsUserDetail.getIsMypage() || snsUserDetail.getSnsUserId()==0) {
			if (UserUtils.isUserLogin()) {
				snsUserDetail.setUserId(UserUtils.getUserId());
				snsUserDetail.setCertifiedDate("Y");
			} else {
				snsUserDetail.setUserId(snsUserDetail.getUserId());
			}
//			snsUserDetail.setSnsUserId(snsUser.getSnsUserId());
//			snsUserMapper.insertSnsUser(snsUser);
		}
		snsUserMapper.insertUserSnsInfo(snsUserDetail);
	}

	@Override
	@Transactional
	public Map<String, String> joinProcess(SnsUserDetail snsUserDetail2, Map<String, String> map, SnsUserDetail snsUserData) {
		SnsUserDetail snsUserDetail = this.getUserSnsInfo(snsUserData);
		if (snsUserDetail == null) {
			int userCount = this.getDuplicatedUserCount(snsUserDetail);

			if (userCount != 0) {
				map.put("value", "01");
				map.put("message", "이미 해당 이메일로 가입 된 아이디가 존재합니다.");
				return map;
			}
			else {
				if (!UserUtils.isUserLogin()) {
					User user = new User();
					UserDetail userDetail = new UserDetail();

					// [SKC_2018-12-10] loginId 수정
					String loginId = new StringBuilder()
							.append(snsUserData.getSnsType() == null ? 'n' : snsUserData.getSnsType().toLowerCase())
							.append("-")
							.append(snsUserData.getSnsId())
							.toString();
					snsUserData.setLoginId(loginId);
					user.setLoginId(loginId);


					// user.setPassword
					String radomPassword = UUID.randomUUID().toString().substring(0, 8);
					user.setPassword(passwordEncoder.encode(radomPassword));
					
					// user.setEmail
					user.setEmail(snsUserData.getEmail());
					
					// user.setName
					user.setUserName(snsUserData.getSnsName());
					
					// userDetail.setNickname
					//userDetail.setNickname(snsUserData.getSnsName());
					
					userDetail.setSiteFlag("3");
//					userService.insertUserAndUserDetailForSns(user, userDetail, snsUserData);

					this.joinProcess(snsUserDetail, map, snsUserData);
				}
				else {
					// mypage일경우 joinProcess 종료
					snsUserData.setUserId(UserUtils.getUserId());
					this.insertSnsUser(snsUserData);
					map.put("value", "00");
					map.put("message", "SNS연결 추가가 정상처리 되었습니다.");
				}
			}
		}
		
		else {
			if (snsUserData.getIsMypage()) {
				// mypage일 경우 sns계정이 이미 있으면 오류 처리
				map.put("value", "01");
				map.put("message", "이미 연동된 SNS계정입니다.");
			}
			else {
				// login일경우 joinProcess 종료
				map.put("value", "00");
				map.put("message", "SNS연결 아이디가 정상적으로 생성되었습니다.");
				map.put("userId", String.valueOf(snsUserDetail.getUserId()));
				map.put("loginId", snsUserDetail.getLoginId());
				
				String loginId = snsUserDetail.getLoginId();
				map.put("shadowUserId", loginId);
				map.put("shadowLoginKey", ShadowUtils.getShadowLoginKey(loginId, ""));
				map.put("shadowLoginPassword", ShadowUtils.getShadowLoginPassword(loginId));
				map.put("shadowLoginSignature", ShadowUtils.getShadowLoginSignature(loginId));
			}
		}
		return map;
	}

	@Override
	public SnsUser getSnsUserInfo(SnsUser snsUser) {
		return snsUserMapper.getSnsUserInfo(snsUser);
	}

	@Override
	public SnsUser getUserSns(SnsUser snsUser) {
		return snsUserMapper.getUserSns(snsUser);
	}

	@Override
	public List<SnsUser> getUserSnsList(SnsUser snsUser) {
		return snsUserMapper.getUserSnsList(snsUser);
	}

	@Override
	public void disconnectSns(SnsUserDetail snsUserDetail) {
		snsUserMapper.disconnectSns(snsUserDetail);
	}
	
	@Override
	public void updateUser(User user) {
		if (user.getPassword()!=null && !"".equals(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		snsUserMapper.updateUser(user);
		snsUserMapper.updateUserSns(user);
	}
	
	@Override
	public void updateSnsUser(User user) {
		snsUserMapper.updateSnsUser(user);
	}
	
	@Override
	public void secedeProcess(User user) {
		snsUserMapper.secedeProcess(user);
	}

	@Override
	public void secedeSnsProcess(User user) {
		snsUserMapper.secedeSnsProcess(user);
	}
}
