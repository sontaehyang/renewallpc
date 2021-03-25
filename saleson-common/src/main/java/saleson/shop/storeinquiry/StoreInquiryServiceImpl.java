package saleson.shop.storeinquiry;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.order.domain.OrderCount;
import saleson.shop.storeinquiry.domain.StoreInquiry;
import saleson.shop.storeinquiry.support.StoreInquiryParam;


@Service("storeInquiryService")
public class StoreInquiryServiceImpl implements StoreInquiryService {
	private static final Logger log = LoggerFactory.getLogger(StoreInquiryServiceImpl.class);

	@Autowired
	private StoreInquiryMapper storeInquiryMapper;
	
	/**
	 * 입점문의 insert
	 * @param storeInquiry
	 */
	public void insertStoreInquiry(StoreInquiry storeInquiry){
		storeInquiryMapper.insertStoreInquiry(storeInquiry);
	};
	
	/**
	 * 입점문의 리스트
	 * @param storeInquiryParam
	 * @return
	 */
	public List<StoreInquiry> getStoreInquiryList(StoreInquiryParam storeInquiryParam){;
		return storeInquiryMapper.getStoreInquiryList(storeInquiryParam);
	}
	
	/**
	 * 입점문의 상태 업데이트
	 * @param storeInquiry
	 */
	public void updateStoreInquiryStatus(StoreInquiry storeInquiry){
		storeInquiryMapper.updateStoreInquiryStatus(storeInquiry);
	}

	@Override
	public int getStoreInquiryCount(StoreInquiryParam storeInquiryParam) {
		return storeInquiryMapper.getStoreInquiryCount(storeInquiryParam);
	}

	@Override
	public StoreInquiry getStoreInquiry(int storeInquiryId) {
		return storeInquiryMapper.getStoreInquiry(storeInquiryId);
	};
	
	@Override
	public StoreInquiry getStoreInquiryByFileName(int storeInquiryId) {
		return storeInquiryMapper.getStoreInquiryByFileName(storeInquiryId);
	}
	
	@Override
	public List<OrderCount> getOpmanagerStoreCountAll() {
		return storeInquiryMapper.getOpmanagerStoreCountAll();
	}

}
