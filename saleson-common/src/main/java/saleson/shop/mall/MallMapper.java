package saleson.shop.mall;

import java.util.List;

import saleson.shop.item.domain.Item;
import saleson.shop.mall.domain.MallConfig;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.support.MallConfigParam;
import saleson.shop.mall.support.MallOrderParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("mallMapper")
public interface MallMapper {
	
	/**
	 * 상품 조회
	 * @param mallConfig
	 * @return
	 */
	Item getItemByMallConfig(MallOrderParam mallOrderParam);
	
	/**
	 * 마켓 주문 상세 조회
	 * @param mallOrderParam
	 * @return
	 */
	MallOrder getMallOrderDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 신규 주문 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getNewOrderCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 신규 주문 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrder> getNewOrderListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 신규 주문 상세
	 * @param mallOrderParam
	 * @return
	 */
	MallOrder getNewOrderDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 배송준비중 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getShippingReadyCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 배송준비중 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrder> getShippingReadyListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 배송준비중 상세
	 * @param mallOrderParam
	 * @return
	 */
	MallOrder getShippingReadyDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 배송중 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getShippingCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 배송중 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrder> getShippingListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 요청 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getCancelRequestCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 요청 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrderCancel> getCancelRequestListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 상세
	 * @param mallOrderParam
	 * @return
	 */
	MallOrderCancel getCancelRequestDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 완료 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getCancelFinishCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 주문취소 완료 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrderCancel> getCancelFinishListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품신청 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getReturnRequestCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품신청 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrderReturn> getReturnRequestListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품신청 상세
	 * @param mallOrderParam
	 * @return
	 */
	MallOrderReturn getReturnRequestDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품완료 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getReturnFinishCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 반품완료 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrderReturn> getReturnFinishListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 교환요청 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getExchangeRequestCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 교환 요청 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrderExchange> getExchangeRequestListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 교환요청 상세
	 * @param mallOrderParam
	 * @return
	 */
	MallOrderExchange getExchangeRequestDetailByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 교환완료 카운트
	 * @param mallOrderParam
	 * @return
	 */
	int getExchangeFinishCountByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 교환 완료 리스트
	 * @param mallOrderParam
	 * @return
	 */
	List<MallOrderExchange> getExchangeFinishListByParam(MallOrderParam mallOrderParam);
	
	/**
	 * 등록된 오픈마켓 목록을 조회
	 * @param mallConfigParam
	 * @return
	 */
	List<MallConfig> getMallConfigListByParam(MallConfigParam mallConfigParam);
	
	/**
	 * 수집 결과 업데이트
	 * @param openMarketConfig
	 */
	void updateMallConfig(MallConfig mallConfig);

	/**
	 * 클레임 수집 결과 업데이트
	 * @param mallConfig
	 */
	void updateMallConfigForClaim(MallConfig mallConfig);
	
	/**
	 * 일반주문 수집상태 업데이트
	 * @param mallConfig
	 */
	void updateCollectStatusCode(MallConfig mallConfig);
	
	/**
	 * 클레임 수집상태 업데이트
	 * @param mallConfig
	 */
	void updateClaimCollectStatusCode(MallConfig mallConfig);
	
	/**
	 * 마켓 주문을 내부 상품과 매칭 시킴
	 * @param mallOrderParam
	 */
	void updateMallOrderMatchingItemUserCode(MallOrderParam mallOrderParam);
	
}
