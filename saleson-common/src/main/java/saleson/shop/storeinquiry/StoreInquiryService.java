package saleson.shop.storeinquiry;

import java.util.List;

import saleson.shop.order.domain.OrderCount;
import saleson.shop.storeinquiry.domain.StoreInquiry;
import saleson.shop.storeinquiry.support.StoreInquiryParam;

public interface StoreInquiryService {

    /**
     * 입점문의 insert
     *
     * @param storeInquiry
     */
    public void insertStoreInquiry(StoreInquiry storeInquiry);

    /**
     * 입점문의 리스트
     *
     * @param storeInquiryParam
     * @return
     */
    public List<StoreInquiry> getStoreInquiryList(StoreInquiryParam storeInquiryParam);

    /**
     * 입점문의전체카운트
     *
     * @param storeInquiryParam
     * @return
     */
    public int getStoreInquiryCount(StoreInquiryParam storeInquiryParam);

    /**
     * 입점문의 상태 업데이트
     *
     * @param storeInquiry
     */
    public void updateStoreInquiryStatus(StoreInquiry storeInquiry);

    /**
     * 입점문의 가져오기
     *
     * @param storeInquiryId
     * @return
     */
    public StoreInquiry getStoreInquiry(int storeInquiryId);

    /* 파일이름 가져오기 */
    public StoreInquiry getStoreInquiryByFileName(int storeInquiryId);

    /**
     * 메인화면 입점문의 현황 집계
     *
     * @return
     */
    public List<OrderCount> getOpmanagerStoreCountAll();

}
