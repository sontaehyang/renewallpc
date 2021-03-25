package saleson.shop.giftgroup;

import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.GiftGroup;

public interface GiftGroupService {

    /**
     * 사은품 그룸 등록
     * @param giftGroup
     * @throws Exception
     */
    void insertGiftGroup(GiftGroup giftGroup) throws Exception;

    /**
     * 사은품 그룹 수정
     * @param giftGroup
     * @throws Exception
     */
    void updateGiftGroup(GiftGroup giftGroup) throws Exception;

    /**
     * 사은품 그룹 삭제
     * @param listParam
     * @throws Exception
     */
    void deleteGiftGroup(ListParam listParam) throws Exception;

    /**
     * 사은품 그룹 조회
     * @param id
     * @return
     * @throws Exception
     */
    GiftGroup getGiftGroupById(long id) throws Exception;

    /**
     * 사은품 그룹 목록
     * @param predicate
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<GiftGroup> getGiftGroupList(Predicate predicate, Pageable pageable) throws Exception;

    /**
     * 사은품 그룹 유효성 체크
     * @param id
     * @return
     */
    boolean isValidGiftGroup(long id);

}
