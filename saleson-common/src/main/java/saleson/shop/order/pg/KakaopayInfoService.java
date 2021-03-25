package saleson.shop.order.pg;

import saleson.shop.order.pg.kakaopay.domain.KakaopayInfo;

public interface KakaopayInfoService {
	
	/**
	 * 카카오페이 거래번호로 거래 정보를 조회함.
	 * @param TID 거래 ID
	 * @param CancelNo 취소번호 취소요청시 전달한 취소번호 
	 * @return
	 */
	KakaopayInfo getPayInfo(String TID, String CancelNo);
}
