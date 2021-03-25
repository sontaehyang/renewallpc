package saleson.seller.user;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.web.domain.ListParam;
import saleson.shop.user.support.UserSearchParam;

import java.util.List;

public interface SellerUserService {

    /**
     * 운영자 등록
     * @param sellerId
     * @param user
     * @return
     * @throws Exception
     */
    int insertSellerUser(long sellerId, User user) throws Exception;

    /**
     * 마스터 운영자 등록
     * @param sellerId
     * @param user
     * @throws Exception
     */
    int insertSellerMasterUser(long sellerId, User user) throws Exception;
    /**
     * 운영자 수정
     * @param sellerId
     * @param user
     * @return
     * @throws Exception
     */
    int updateSellerUser(long sellerId, User user) throws Exception;

    /**
     * 운영자 삭제
     * @param sellerId
     * @param listParam
     * @return
     * @throws Exception
     */
    int deleteSellerUserByList(long sellerId, ListParam listParam) throws Exception;

    /**
     * 운영자 목록 조회
     * @param userSearchParam
     * @return
     */
    int getSellerUserListCount(UserSearchParam userSearchParam);

    /**
     * 운영자 목록 조회
     * @param userSearchParam
     * @return
     */
    List<User> getSellerUserList(UserSearchParam userSearchParam);

    /**
     * 운영자 상세 조회
     * @param sellerId
     * @param userId
     * @return
     * @throws Exception
     */
    User getSellerUserById(long sellerId, long userId) throws Exception;

    /**
     * 운영자 상세 조회
     * @param sellerId
     * @param loginId
     * @return
     * @throws Exception
     */
    User getSellerUserByLoginId(long sellerId, String loginId) throws Exception;

    /**
     * 운영자 로그인 ID 중복 체크
     * @param loginId
     * @return
     */
    boolean isDuplicateSellerUserByLoginId(String loginId);

    /**
     * 해당 관리자 비밀번호를 임시 비밀번호로 변경
     * @param userId
     */
    void updateTempPasswordForSellerUser(long userId) throws UserException;


    /**
     * 해당 관리자 비밀번호 변경
     * @param userId
     * @param passowrd
     * @param changePassowrd
     */
    void updatePasswordForSellerUser(long userId, String passowrd, String changePassowrd) throws UserException;

}
