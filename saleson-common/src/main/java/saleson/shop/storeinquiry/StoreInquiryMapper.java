package saleson.shop.storeinquiry;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.order.domain.OrderCount;
import saleson.shop.storeinquiry.domain.StoreInquiry;
import saleson.shop.storeinquiry.support.StoreInquiryParam;


@Mapper("storeInquiryMapper")
public interface StoreInquiryMapper {
	
	/**
	 * 입점문의 insert
	 * @param storeInquiry
	 */
	void insertStoreInquiry(StoreInquiry storeInquiry);
	
	/**
	 * 입점문의 리스트
	 * @param storeInquiryParam
	 * @return
	 */
	List<StoreInquiry> getStoreInquiryList(StoreInquiryParam storeInquiryParam);
	
	/**
	 * 입점문의 가져오기
	 * @param storeInquiryId
	 * @return
	 */
	StoreInquiry getStoreInquiry(int storeInquiryId);
	
	/**
	 * 입점문의 전체 카운트
	 * @param storeInquiryParam
	 * @return
	 */
	int getStoreInquiryCount(StoreInquiryParam storeInquiryParam);
	
	/**
	 * 입점문의 상태 업데이트
	 * @param storeInquiry
	 */
	void updateStoreInquiryStatus(StoreInquiry storeInquiry);
	
	StoreInquiry getStoreInquiryByFileName(int storeInquiryId);
	
	/**
	 * 메인화면 입점문의 현황 집계
	 * @return
	 */
	List<OrderCount> getOpmanagerStoreCountAll();
	
}
