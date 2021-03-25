package saleson.shop.mall.est;

import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.est.domain.Product;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("estMapper")
public interface EstMapper {
	
	/**
	 * 클레임 요청 정보 조회 - 취소
	 * @param product
	 * @return
	 */
	MallOrderCancel getMallOrderCancel(Product product);
	
	/**
	 * 클레임 요청 정보 조회 - 반품
	 * @param product
	 * @return
	 */
	MallOrderReturn getMallOrderReturn(Product product);
	
	/**
	 * 클레임 요청 정보 조회 - 교환
	 * @param product
	 * @return
	 */
	MallOrderExchange getMallOrderExchange(Product product);
	
	/**
	 * 11번가 주문등록 여부 조회
	 * @param product
	 * @return
	 */
	int getMallOrderCount(Product product);
	
	/**
	 * 11번가 신규주문 등록
	 * @param product
	 * @return
	 */
	void insertMallOrder(Product product);
	
	/**
	 * 11번가 클레임 등록 - 취소
	 * @param product
	 */
	void insertMallOrderCancel(Product product);
	
	/**
	 * 11번가 클레임 수정 - 취소
	 * @param product
	 */
	void updateMallOrderCancel(Product product);
	
	/**
	 * 11번가 클레임 등록 - 반품
	 * @param product
	 */
	void insertMallOrderReturn(Product product);
	
	/**
	 * 11번가 클레임 수정 - 반품
	 * @param product
	 */
	void updateMallOrderReturn(Product product);
	
	/**
	 * 11번가 클레임 등록 - 교환
	 * @param product
	 */
	void insertMallOrderExchange(Product product);
	
	/**
	 * 11번가 클레임 수정 - 교환
	 * @param product
	 */
	void updateMallOrderExchange(Product product);
	
	/**
	 * 11번가 주문 수정
	 * @param product
	 */
	void updateMallOrder(Product product);

	/**
	 * 11번가 반품 보류
	 * @param product
	 */
	void updateReturnHoldForApply(Product product);
	
	/**
	 * 11번가 반품 거부
	 * @param product
	 */
	void updateReturnRefusalForApply(Product product);
	
	/**
	 * 11번가 교환 거부
	 * @param product
	 */
	void updateExchangeRefusalForApply(Product product);
	
	/**
	 * 11번가 반품 승인
	 * @param product
	 */
	void updateReturnConfirmForApply(Product product);
	
	/**
	 * 11번가 교환 완료
	 * @param product
	 */
	void updateExchangeConfirmForApply(Product product);
}
