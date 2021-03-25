package saleson.shop.mall.auction;

import saleson.shop.mall.auction.domain.CancelApproval;
import saleson.shop.mall.auction.domain.ExchangeBase;
import saleson.shop.mall.auction.domain.PaidOrder;
import saleson.shop.mall.auction.domain.ReturnList;
import saleson.shop.mall.domain.MallOrder;
import saleson.shop.mall.domain.MallOrderCancel;
import saleson.shop.mall.domain.MallOrderExchange;
import saleson.shop.mall.domain.MallOrderReturn;
import saleson.shop.mall.est.domain.Product;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("auctionMapper")
public interface AuctionMapper {
	
	/**
	 * 클레임 요청 정보 조회 - 취소
	 * @param cancelApproval
	 * @return
	 */
	MallOrderCancel getMallOrderCancel(CancelApproval cancelApproval);
	
	/**
	 * 클레임 요청 정보 조회 - 반품
	 * @param returnList
	 * @return
	 */
	MallOrderReturn getMallOrderReturn(ReturnList returnList);
	
	/**
	 * 클레임 요청 정보 조회 - 교환
	 * @param exchangeBase
	 * @return
	 */
	MallOrderExchange getMallOrderExchange(ExchangeBase exchangeBase);
	
	/**
	 * 옥션 주문 카운트
	 * @param paidOrder
	 * @return
	 */
	int getMallOrderCount(PaidOrder paidOrder);
	
	
	/**
	 * 옥션 주문 등록
	 * @param paidOrder
	 */
	void insertMallOrder(PaidOrder paidOrder);
	
	/**
	 * 클레임 - 취소 정보 등록
	 * @param cancelApproval
	 */
	void insertMallOrderCancel(CancelApproval cancelApproval);
	
	/**
	 * 클레임 - 반품 정보 등록
	 * @param returnList
	 */
	void insertMallOrderReturn(ReturnList returnList);
	
	/**
	 * 옥션 클레임 등록 - 교환
	 * @param exchangeBase
	 */
	void insertMallOrderExchange(ExchangeBase exchangeBase);
	
	/**
	 * 옥션 클레임 수정 - 교환
	 * @param exchangeBase
	 */
	void updateMallOrderExchange(ExchangeBase exchangeBase);
	
	/**
	 * 주문 업데이트
	 * @param mallOrder
	 */
	void updateMallOrder(MallOrder mallOrder);
	
	/**
	 * 클레임 - 취소 정보 갱신
	 * @param cancelApproval
	 */
	void updateMallOrderCancel(CancelApproval cancelApproval);
	
	/**
	 * 클레임 - 반품 정보 갱신
	 * @param returnList
	 */
	void updateMallOrderReturn(ReturnList returnList);
	
	/**
	 * 클레임 처리 완료
	 * @param mallOrderCancel
	 */
	void updateMallORderCancelFinish(MallOrderCancel mallOrderCancel);
	
	/**
	 * 반품 보류
	 * @param product
	 */
	void updateReturnHold(MallOrderReturn returnApply);
	
	/**
	 * 반품 완료
	 * @param returnApply
	 */
	void updateReturnConfirm(MallOrderReturn returnApply);
	
	/**
	 * 교환 완료
	 * @param exchangeApply
	 */
	void updateExchangeConfirm(MallOrderExchange exchangeApply);
}
