package saleson.seller.user;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.security.userdetails.UserRole;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ValidationUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.AuthorityType;
import saleson.common.utils.RandomStringUtils;
import saleson.shop.sendsmslog.SendSmsLogService;
import saleson.shop.sendsmslog.domain.SendSmsLog;
import saleson.shop.smsconfig.domain.SmsConfig;
import saleson.shop.user.UserService;
import saleson.shop.user.support.UserSearchParam;
import saleson.shop.userrole.UserRoleService;

import java.util.List;

@Service("sellerUserService")
public class SellerUserServiceImpl implements SellerUserService {

    @Autowired
    private SellerUserMapper sellerUserMapper;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private SendSmsLogService sendSmsLogService;

    @Override
    public int insertSellerUser(long sellerId, User user) throws Exception {
        return insertSellerUser(sellerId, user, false);
    }

    @Override
    public int insertSellerMasterUser(long sellerId, User user) throws Exception {
        return insertSellerUser(sellerId, user, true);
    }

    private int insertSellerUser(long sellerId, User user, boolean isMaster) throws Exception {

        if (sellerId <= 0) {
            throw new UserException("판매자 정보가 없습니다.");
        }

        long userId = sequenceService.getLong("OP_USER");
        String enctyptPassword = passwordEncoder.encode(user.getPassword());

        user.setUserId(userId);
        user.setPassword(enctyptPassword);
        user.setPasswordExpiredDate(userService.getPasswordExpiredDate());
        user.setStatusCode("9");

        int insertCount =  sellerUserMapper.insertSellerUser(user);

        if (insertCount > 0) {

            UserRole userRole = new UserRole();
            userRole.setUserId(userId);

            String baseAuthority = AuthorityType.SELLER.getCode();
            // ROLE_SELLER 추가
            userRole.setAuthority(baseAuthority);
            userRoleService.insertUserRole(userRole);

            // 해당 판매자 ROLE 추가
            userRole.setAuthority(baseAuthority+"_"+sellerId);
            userRoleService.insertUserRole(userRole);

            if (isMaster) {
                userRole.setAuthority(AuthorityType.SELLER_MASTER.getCode());
                userRoleService.insertUserRole(userRole);
            }

        }

        return insertCount;
    }


    @Override
    public int updateSellerUser(long sellerId, User user) throws Exception{

        if (!isSellerUserBySellerId(sellerId, user.getUserId())) {
            throw new UserException("존재하지 않는 사용자 입니다.");
        }

        if (!StringUtils.isEmpty(user.getPassword())) {
            String enctyptPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(enctyptPassword);
        }

        return sellerUserMapper.updateSellerUser(user);
    }

    @Override
    public int deleteSellerUserByList(long sellerId, ListParam listParam) throws Exception{

        int count = 0;

        if (listParam.getId() != null) {
            for (String id : listParam.getId())
                if (StringUtils.isNotEmpty(id)) {

                    long userId = Long.parseLong(id);

                    if (!isSellerUserBySellerId(sellerId, userId)) {
                        throw new UserException("존재하지 않는 사용자 입니다.");
                    }

                    count += sellerUserMapper.deleteSellerUserById(userId);

            }
        }

        return count;
    }

    @Override
    public int getSellerUserListCount(UserSearchParam userSearchParam) {
        return sellerUserMapper.getSellerUserListCount(userSearchParam);
    }

    @Override
    public List<User> getSellerUserList(UserSearchParam userSearchParam) {
        return sellerUserMapper.getSellerUserList(userSearchParam);
    }

    @Override
    public User getSellerUserById(long sellerId, long userId) throws Exception{

        User user = sellerUserMapper.getSellerUserById(userId);

        if(!isAuthorityForSellerUser(sellerId, user.getUserRoles())){
            throw new UserException("존재하지 않는 사용자 입니다.");
        }

        return user;
    }

    @Override
    public User getSellerUserByLoginId(long sellerId, String loginId) throws Exception{

        User user = sellerUserMapper.getSellerUserByLoginId(loginId);

        if(!isAuthorityForSellerUser(sellerId, user.getUserRoles())){
            throw new UserException("존재하지 않는 사용자 입니다.");
        }

        return user;
    }

    private boolean isAuthorityForSellerUser(long sellerId, List<UserRole> userRoles) {

        String sellerRole = "ROLE_SELLER_" + sellerId;

        if (!ValidationUtils.isNull(userRoles) && !userRoles.isEmpty()) {
            for (UserRole role : userRoles) {
                if (sellerRole.equals(role.getAuthority())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSellerUserBySellerId(long sellerId, long userId) throws  Exception{
        return !ValidationUtils.isNull(this.getSellerUserById(sellerId, userId));
    }

    @Override
    public boolean isDuplicateSellerUserByLoginId(String loginId) {
        return sellerUserMapper.getDuplicateSellerUserByLoginId(loginId) > 0;
    }

    @Override
    public void updateTempPasswordForSellerUser(long userId) throws UserException {
        User user = sellerUserMapper.getSellerUserById(userId);

        if (user.getPhoneNumber() == null) {
            throw new UserException("핸드폰 번호가 없습니다.");
        } else {

            String tempPassword = RandomStringUtils.getRandomString("!",7,10);

            User tempUser = new User();

            tempUser.setUserId(userId);
            tempUser.setPasswordType("T");
            tempUser.setPassword(passwordEncoder.encode(tempPassword));
            tempUser.setPasswordExpiredDate(userService.getPasswordExpiredDate());

            int count = sellerUserMapper.updatePasswordForSellerUser(tempUser);

            if (count > 0) {

                SmsConfig smsConfig = new SmsConfig();
                smsConfig.setBuyerSendFlag("Y");
                smsConfig.setBuyerContent("임시비밀번호\n"+tempPassword);
                smsConfig.setSmsType("sms");

                SendSmsLog sendSmsLog = new SendSmsLog();

                sendSmsLog.setContent(smsConfig.getBuyerContent());
                sendSmsLog.setSendType("SELLER_USER_TEMP_PASSOWRD");
                sendSmsLogService.sendSms(smsConfig, sendSmsLog, user.getPhoneNumber());

            }
        }
    }

    @Override
    public void updatePasswordForSellerUser(long userId, String passowrd, String changePassowrd) throws UserException {

        User user = sellerUserMapper.getSellerUserById(userId);

        if (passwordEncoder.matches(passowrd, user.getPassword())) {

            if (passwordEncoder.matches(changePassowrd, user.getPassword())) {
                throw new UserException("변경 하려는 비밀번호가 동일합니다.");
            }

            User tempUser = new User();

            tempUser.setUserId(userId);
            tempUser.setPasswordType("N");
            tempUser.setPassword(passwordEncoder.encode(changePassowrd));
            tempUser.setPasswordExpiredDate(userService.getPasswordExpiredDate());

            int count = sellerUserMapper.updatePasswordForSellerUser(tempUser);

            if (count == 0) {
                throw new UserException("비밀번호가 변경되지 않았습니다.");
            }

        } else {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

    }

}
