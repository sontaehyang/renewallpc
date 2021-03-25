package saleson.shop.cashreceipt;

import saleson.shop.cashreceipt.domain.CashReceipt;
import saleson.shop.cashreceipt.support.CashReceiptParam;
import saleson.shop.order.support.OrderParam;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("cashReceiptMapper")
public interface CashReceiptMapper {

	/**
	 * 현금영수증 발행 신청
	 * @param cashReceipt
	 */
	void insertCashReceipt(CashReceipt cashReceipt);
	
	/**
	 * 현금영수증 발행 성공
	 * @param cashReceipt
	 */
	void updateCashReceiptForSuccess(CashReceipt cashReceipt);
	
	/**
	 * 현금영수증 발행을 신청상태로 되돌림 
	 * @param cashReceipt
	 */
	void updateCashReceiptForRollback(CashReceipt cashReceipt);
	
	/**
	 * 현금영수증 부분 취소
	 * @param cashReceipt
	 */
	void updateCashReceiptForPartCancel(CashReceipt cashReceipt);
	
	// 2017.05.25 - Kspay 현금영수증 조회 관련 추가
	
	/**
	 * 현금영수증 ID 조회
	 * @param orderCode
	 * @return
	 */
	Integer getCashReceiptIdByParam(String orderCode);

	/**
	 * 현금영수증 거래번호 조회
	 * @param cbId
	 * @return
	 */
	String getCashReceiptIssueNumberByParam(int cbId);

	/**
	 * 현금영수증 전체 취소
	 * @param cashReceipt
	 */
	void updateCashReceiptForAllCancel(CashReceipt cashReceipt);

	/**
	 * 현금영수증 재발급
	 * @param cashReceipt
	 */
	void updateCashReceiptForReIssue(CashReceipt cashReceipt);

	/**
	 * 현금영수증 재발급 정보 조회
	 * @param cbId
	 * @return
	 */
	CashReceipt getCashReceiptForReIssueByParam(int cbId);

	/**
	 * 실시간계좌이체 취소신청시 현금영수증 입력정보 업데이트
	 * @param cashReceipt
	 */
	void updateCashReceiptInputForReIssue(CashReceipt cashReceipt);
	
	/**
	 * 현금영수증 목록 조회
	 * @param searchParam 
	 * @return
	 */
	List<CashReceipt> getCashReceiptListByParam(CashReceiptParam searchParam);

	/**
	 * 현금영수증 목록 Count
	 * @param searchParam
	 * @return
	 */
	int getCashReceiptListByParamForCount(CashReceiptParam searchParam);

	// 2017.05.25 - Kspay 현금영수증 조회 관련 추가 End
	
	/**
	 * 현금영수증 정보 조회
	 * @param orderCode
	 * @return
	 */
	CashReceipt getCashReceiptByParam(String orderCode);
}
