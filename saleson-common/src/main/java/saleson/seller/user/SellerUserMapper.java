package saleson.seller.user;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.security.userdetails.User;
import saleson.shop.user.support.UserSearchParam;

import java.util.List;

@Mapper("sellerUserMapper")
public interface SellerUserMapper {

    /**
     * 판매관리자 등록
     * @param user
     */
    int insertSellerUser(User user);

    /**
     * 판매관리자 수정
     * @param user
     */
    int updateSellerUser(User user);

    /**
     * 판매관리자 삭제
     * @param userId
     */
    int deleteSellerUserById(long userId);

    /**
     * 판매관리자 목록 조회
     * @param userSearchParam
     * @return
     */
    int getSellerUserListCount(UserSearchParam userSearchParam);

    /**
     * 판매관리자 목록 조회
     * @param userSearchParam
     * @return
     */
    List<User> getSellerUserList(UserSearchParam userSearchParam);

    /**
     * 판매관리자 상세 조회
     * @param userId
     * @return
     */
    User getSellerUserById(long userId);

    /**
     * 판매관리자 상세 조회
     * @param loginId
     * @return
     */
    User getSellerUserByLoginId(String loginId);

    /**
     * 판매관리자 로그인 ID 중복 체크
     * @param loginId
     * @return
     */
    int getDuplicateSellerUserByLoginId(String loginId);

    int updatePasswordForSellerUser(User user);
}
