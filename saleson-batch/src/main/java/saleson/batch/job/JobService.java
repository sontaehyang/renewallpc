package saleson.batch.job;


/**
 * 배치가 실행되는 메서드 목록
 * 메서드 명이 Batch()로 끝나야 배치 실행 로그 (OP_BATCH_EXCUTION)가 기록됨.
 * @author dbclose
 *
 */
public interface JobService {

	/**
	 * 상품 검색어 정보를 갱신한다. 
	 */
	public void itemKeywordBatch();

	/**
	 * 자동 구매확정처리
	 */
	public void autoConfirmPurchaseBatch();
	
	/**
	 * 포인트 만료 처리
	 */
	public void expirationPointBatch();
	
	/**
	 * 포인트 만료 안내 메시지 발송
	 */
	public void expirationPointSendMessageBatch();
	

	/**
	 * 휴면계정 안내 메일 발송
	 */
	public void sendMailToInactiveUserBatch();
	
	/**
	 * 휴면계정으로 전환 처리.
	 */
	public void processInactiveUserBatch();

	/**
	 * 판매자 주문안내 SMS 발송.
	 */
	public void sendOrderSmsToSellerBatch();
	
	/**
	 * 상품 옵션 품절 배치
	 */
	public void updateItemOptionSoldoutBatch();
	
	/**
	 * 카테고리 구분없이 전체 상품에서 랭킹을 정한다.
	 * TOP 100
	 */
	public void itemRankingType1Batch();
	
	/**
	 * 카테고리 그룹을 기준으로 상품의 랭킹을 정한다.
	 */
	public void itemRankingType2Batch();
	
	/**
	 * 1~4 카테고리를 기준으로 상품의 랭킹을 정한다.
	 */
	public void itemRankingType3Batch();
	
	/**
	 * 정기발행쿠폰을 쿠폰리스트에 등록.
	 */
	public void couponRegularBatch();


	/**
	 * 회원 등급 산정
	 */
	public void userLevelBatch();

	/**
	 * 회원 등급 산정 (날짜지정)
	 */
	public void userLevelBatch(String date);

	/**
	 * 입금지연 주문 취소 배치
	 */
	public void cancelWaitingDepositOrderBatch();

	/**
	 * 자동 완성 데이터 처리
	 */
	public void autoCompleteKeywordBatch();

	/**
	 * 카카오 알림톡 업데이트 배치
	 */
	void updateKakaoAlimTalkBatch();

	/**
	 * 캠페인용 유저 업데이트 배치
	 */
	void updateUserCampaignBatch();

	/**
	 * 캠페인용 예약발송 발송처리 배치
	 */
	void sendCampaignMessageBatch();

	/**
	 * 캠페인용 발송통계 배치
	 */
	void updateCampaignSentBatch();

	/**
	 * 구매확정 UMS 배치
	 */
	void autoConfirmPurchaseUmsBatch();

	/**
	 * 구매확정 요청 UMS 배치
	 */
	void autoConfirmPurchaseRequestUmsBatch();
    /**
     * 임시 주문 정보 삭제처리 배치
     */
	void deleteOrderTempInfoBatch();

	/**
	 * 쿠폰만료기간 안내 메시지 발송 배치
	 */
	void expirationCouponSendMessageBatch();

	/**
	 * 포인트만료 안내 메시지 발송 배치
	 */
	void expirationPointMessageBatch();

	/**
	 * ERP 주문 상태 연동 배치
	 */
	void erpOrderStatusBatch();

}
