package saleson.shop.restocknotice;

import saleson.shop.restocknotice.domain.RestockNotice;

public interface RestockNoticeService {

    /**
     * 재입고알림 추가
     * @param restockNotice
     */
    void insertRestockNotice(RestockNotice restockNotice);

    /**
     * 재입고알림 신청 인원 조회
     * @param restockNotice
     * @return
     */
    int getRestockNoticeCount(RestockNotice restockNotice);

    /**
     * 재입고알림 등록 여부 검사
     * @param restockNotice
     * @return
     */
    boolean isRestockNotice(RestockNotice restockNotice);

    /**
     * 상품 재입고알림 대상 조회
     * @param itemId
     * @return
     */
    void sendRestockNotice(int itemId);
}
