package saleson.shop.giftitem;

import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.GiftItem;
import saleson.model.GiftItemLog;
import saleson.model.GiftItemRelation;

import java.util.List;

public interface GiftItemService {

    /**
     * 사은품 등록
     * @param giftItem
     * @throws Exception
     */
    void insertGiftItem(GiftItem giftItem) throws Exception;

    /**
     * 사은품 수정
     * @param giftItem
     * @throws Exception
     */
    void updateGiftItem(GiftItem giftItem) throws Exception;

    /**
     * 사은품 삭제
     * @param listParam
     * @throws Exception
     */
    void deleteGiftItem(ListParam listParam) throws Exception;

    /**
     * 사은품 이미지 삭제
     * @param id
     * @throws Exception
     */
    void deleteGiftItemImage(long id) throws Exception;

    /**
     * 사은품 조회
     * @param id
     * @return
     * @throws Exception
     */
    GiftItem getGiftItemById(long id) throws Exception;

    /**
     * 사은품 조회 (배열 순서대로 처리됨)
     * @param ids
     * @return
     * @throws Exception
     */
    List<GiftItem> getGiftItemListByIds(List<Long> ids) throws Exception;

    /**
     * 사은품 목록 조회
     * @param predicate
     * @param pageable
     * @return
     */
    Page<GiftItem> getGiftItemList(Predicate predicate, Pageable pageable) throws Exception;

    /**
     * 사은품 유효성 체크
     * @param id
     * @return
     */
    boolean isValidGiftItem(long id);

    /**
     * 사은품 로그 조회
     * @param predicate
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<GiftItemLog> getGiftItemLogList(Predicate predicate, Pageable pageable) throws Exception;

    /**
     * 해당 상품에 사은품 정보 연결
     * @param itemId
     * @param giftItemIds
     */
    void insertGiftItemRelation(int itemId, List<Long> giftItemIds) throws Exception;

    /**
     * 해당 상품의 사은품 삭제
     * @param itemId
     */
    void deleteGiftItemRelation(int itemId) throws Exception;

    /**
     * 해당 상품의 사은품 조회
     * @param itemId
     * @return
     * @throws Exception
     */
    List<GiftItemRelation> getGiftItemRelationList(int itemId) throws Exception;

    /**
     * 해당 상품의 사은품 조회
     * @param itemId
     * @return
     * @throws Exception
     */
    List<GiftItem> getGiftItemListForFront(int itemId) throws Exception;
}
