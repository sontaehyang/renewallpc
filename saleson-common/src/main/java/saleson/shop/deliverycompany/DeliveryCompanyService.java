package saleson.shop.deliverycompany;

import java.util.List;

import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.deliverycompany.support.DeliveryCompanyParam;

import com.onlinepowers.framework.web.domain.ListParam;

public interface DeliveryCompanyService {

	/**
	 * 배송업체 리스트 조회
	 * @param deliveryCompanyParam
	 * @return
	 */
	List<DeliveryCompany> getDeliveryCompanyList(DeliveryCompanyParam deliveryCompanyParam);
	
	/**
	 * 배송업체 삭제
	 * @param listParam
	 */
	void deleteDeliveryCompanyById(ListParam listParam);
	
	/**
	 * 배송업체 리스트수 조회
	 * @param deliveryCompanyParam
	 * @return
	 */
	int getDeliveryCompanyCount(DeliveryCompanyParam deliveryCompanyParam);
	
	/**
	 * 배송업체 조회
	 * @param deliveryCompanyId
	 * @return
	 */
	DeliveryCompany getDeliveryCompanyById(int deliveryCompanyId);
	
	/**
	 * 배송업체 수정
	 * @param deliveryCompany
	 */
	void updateDeliveryCompanyById(DeliveryCompany deliveryCompany);
	
	/**
	 * 배송업체 등록
	 * @param deliveryCompany
	 */
	void insertDeliveryCompany(DeliveryCompany deliveryCompany);
}
