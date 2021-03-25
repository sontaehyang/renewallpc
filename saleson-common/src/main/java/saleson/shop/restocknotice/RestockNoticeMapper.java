package saleson.shop.restocknotice;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import saleson.shop.restocknotice.domain.RestockNotice;

import java.util.List;

@Mapper("restockNoticeMapper")
public interface RestockNoticeMapper {

    /**
     * 재입고알림 추가
     * @param restockNotice
     */
    void insertRestockNotice(RestockNotice restockNotice);

    /**
     * 재입고알림 등록시 중복 체크
     * @param restockNotice
     * @return
     */
    int getRestockNoticeCount(RestockNotice restockNotice);

    /**
     * MESSAGE 전송 완료 > SEND_FLAG 수정
     * @param itemId
     */
    void updateSendFlagByItemId(int itemId);

    /**
     * 상품 재입고알림 대상 조회
     * @param itemId
     * @return
     */
    List<RestockNotice> getRestockNoticeListForMessage(int itemId);

    /**
     * MESSAGE 전송 후 데이터 삭제
     * @param itemId
     */
    void deleteRestockNoticeByItemId(int itemId);
}
