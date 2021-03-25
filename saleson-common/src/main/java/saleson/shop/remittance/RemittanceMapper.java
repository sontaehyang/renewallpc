package saleson.shop.remittance;

import java.util.List;

import saleson.common.opmanager.count.OpmanagerCount;
import saleson.shop.order.addpayment.domain.OrderAddPayment;
import saleson.shop.order.domain.OrderItem;
import saleson.shop.order.domain.OrderShipping;
import saleson.shop.remittance.domain.Remittance;
import saleson.shop.remittance.domain.RemittanceConfirm;
import saleson.shop.remittance.domain.RemittanceConfirmDetail;
import saleson.shop.remittance.domain.RemittanceDetail;
import saleson.shop.remittance.domain.RemittanceExpected;
import saleson.shop.remittance.support.EditAddPaymentRemittance;
import saleson.shop.remittance.support.EditItemRemittance;
import saleson.shop.remittance.support.EditShippingRemittance;
import saleson.shop.remittance.support.RemittanceParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("remittanceMapper")
public interface RemittanceMapper {

	/**
	 * 정산 마감 내역 카운트
	 * @param param
	 * @return
	 */
	int getRemittanceFinishingCountByParam(RemittanceParam param);
	
	/**
	 * 정산 마감 내역 리스트
	 * @param param
	 * @return
	 */
	List<Remittance> getRemittanceFinishingListByParam(RemittanceParam param);
	
	/**
	 * 정산 마감 상세 카운트
	 * @param param
	 * @return
	 */
	int getRemittanceFinishingDetailCountByParam(RemittanceParam param);
	
	/**
	 * 정산 마감 상세 리스트
	 * @param param
	 * @return
	 */
	List<RemittanceDetail> getRemittanceFinishingDetailListByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 확정 내역 카운트
	 * @param param
	 * @return
	 */
	int getRemittanceConfirmCountByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 확정 내역
	 * @param param
	 * @return
	 */
	List<RemittanceConfirm> getRemittanceConfirmListByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 카운트 - 상품
	 * @param param
	 * @return
	 */
	int getRemittanceConfirmDetailCountByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 리스트 - 상품
	 * @param param
	 * @return
	 */
	List<RemittanceConfirmDetail> getRemittanceConfirmDetailListByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 조회 일자별 - 카운트
	 * @param param
	 * @return
	 */
	int getRemittanceExpectedCountByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 조회 일자별 - 리스트
	 * @param param
	 * @return
	 */
	List<RemittanceExpected> getRemittanceExpectedListByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 카운트 - 상품
	 * @param param
	 * @return
	 */
	int getRemittanceItemExpectedDetailCountByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 리스트 - 상품
	 * @param param
	 * @return
	 */
	List<OrderItem> getRemittanceItemExpectedDetailListByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 카운트 - 배송비
	 * @param param
	 * @return
	 */
	int getRemittanceShippingExpectedDetailCountByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 리스트 - 배송비
	 * @param param
	 * @return
	 */
	List<OrderShipping> getRemittanceShippingExpectedDetailListByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 카운트 - 추가금
	 * @param param
	 * @return
	 */
	int getRemittanceAddPaymentExpectedDetailCountByParam(RemittanceParam param);
	
	/**
	 * 판매자별 정산 대기 목록 리스트 - 추가금
	 * @param param
	 * @return
	 */
	List<OrderAddPayment> getRemittanceAddPaymentExpectedDetailListByParam(RemittanceParam param);
	
	/**
	 * 상품 정산정보 수정
	 * @param editItemRemittance
	 */
	void updateRemittanceItemExpected(EditItemRemittance editItemRemittance);
	
	/**
	 * 배송비 정산정보 수정
	 * @param editShippingRemittance
	 */
	void updateRemittanceShippingExpected(EditShippingRemittance editShippingRemittance);
	
	/**
	 * 추가금 정산정보 수정
	 * @param editAddPaymentRemittance
	 */
	void updateRemittanceAddPaymentExpected(EditAddPaymentRemittance editAddPaymentRemittance);
	
	/**
	 * 정산 마감 마스터 등록
	 * @param remittance
	 */
	void insertRemittanceMaster(Remittance remittance);
	
	/**
	 * 정산 마감 상세 등록
	 * @param param
	 */
	void insertRemittanceDetail(RemittanceParam param);
	
	/**
	 * 주문 상품 정보 마감처리
	 * @param param
	 */
	void updateItemRemittanceFinishingByParam(RemittanceParam param);
	
	/**
	 * 배송비 마감 처리
	 * @param param
	 */
	void updateShippingRemittanceFinishingByParam(RemittanceParam param);
	
	/**
	 * 추가금 마감 처리
	 * @param param
	 */
	void updateAddPaymentRemittanceFinishingByParam(RemittanceParam param);
	
	/**
	 * 정산 마감후 금액 검증
	 * @param param
	 * @return
	 */
	int getRemittanceFinishingAmountValidateByParam(RemittanceParam param);
	
	/**
	 * 구매 확정시 정산일자 조회
	 * @param sellerId
	 * @return
	 */
	String getRemittanceDateBySellerId(long sellerId);
	
	/**
	 * 환불 완료후 정산되지 않은 잔여 배송비의 정산일자를 셋팅한다.
	 * @param refundCode
	 */
	void updateResidualShippingRemittanceByRefundCode(String refundCode);
	
	/**
	 * 정산정보 수정 - 상품
	 * @param orderItem
	 */
	int updateItemRemittanceInfo(OrderItem orderItem);
	
	/**
	 * 정산정보 수정 - 추가결제 정보
	 * @param orderAddPayment
	 * @return
	 */
	int updateAddPaymentRemittanceInfo(OrderAddPayment orderAddPayment);
	
	/**
	 * 이상우 [2017-05-11 추가]
	 * 관리자 메인 정산예정, 확정 카운트
	 * @return
	 */
	List<OpmanagerCount> getOpmanagerRemittanceCountAll(RemittanceParam remittanceParam);
	
	/**
	 * 오라클 잔여 배송비 정산일자 셋팅을 위한 정보 조회
	 * @author minae.yun [2017-07-12]
	 * @param refundCode
	 * @return
	 */
	List<OrderItem> getOrderShippingForRemittance(String refundCode);
	
	/**
	 * 오라클 잔여 배송비 정산일자 셋팅
	 * @author minae.yun [2017-07-12]
	 * @param orderItem
	 * @return
	 */
	int updateResidualShippingRemittanceByRefundCodeForOracle(OrderItem orderItem);
	
}
