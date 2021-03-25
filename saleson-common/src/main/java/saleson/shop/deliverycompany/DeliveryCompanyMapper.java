package saleson.shop.deliverycompany;

import java.util.List;

import saleson.shop.deliverycompany.domain.DeliveryCompany;
import saleson.shop.deliverycompany.support.DeliveryCompanyParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("deliveryCompanyMapper")
public interface DeliveryCompanyMapper {
	
	/**
	 * 활성화된 배송업체 리스트 조회
	 * @return
	 */
	List<DeliveryCompany> getActiveDeliveryCompanyListAll();
	
	/**
	 * 배송업체 리스트 조회
	 * @return
	 */
	List<DeliveryCompany> getDeliveryCompanyList(DeliveryCompanyParam deliveryCompanyParam);

	/**
	 * 배송업체 삭제
	 * @param deliveryCompanyId
	 */
	void deleteDeliveryCompanyById(int deliveryCompanyId);
	
	/**
	 * 배송업체 리스트수 조회
	 * @param deliveryCompanyParam
	 * @return
	 */
	int getDeliveryCompanyCount(DeliveryCompanyParam deliveryCompanyParam);
	
	/**
	 * 배송업체 상세 조회
	 * @param deliveryCompanyId
	 * @return
	 */
	DeliveryCompany getDeliveryCompanyById(int deliveryCompanyId);
	
	/**
	 * 배송업체 등록
	 * @param deliveryCompany
	 */
	void insertDeliveryCompany(DeliveryCompany deliveryCompany);
	
	/**
	 * 배송업체 수정
	 * @param deliveryCompany
	 */
	void updateDeliveryCompanyById(DeliveryCompany deliveryCompany);
	
	
}
