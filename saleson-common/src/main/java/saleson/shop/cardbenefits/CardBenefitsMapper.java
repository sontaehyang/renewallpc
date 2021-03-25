package saleson.shop.cardbenefits;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.cardbenefits.domain.CardBenefits;
import saleson.shop.cardbenefits.support.CardBenefitsParam;

@Mapper("cardBenefitsMapper")
public interface CardBenefitsMapper {
	
	/**
	 * 카드혜택 
	 * @param cardBenefitsParam
	 * @return
	 */
	int getCardBenefitsCount(CardBenefitsParam cardBenefitsParam);
	
	/**
	 * 카드혜택 리스트
	 * @param cardBenefitsParam
	 * @return
	 */
	List<CardBenefits> getCardBenefitsList(CardBenefitsParam cardBenefitsParam);
	
	/**
	 * 카드혜택 상세보기
	 * @param cardBenefitsId
	 * @return
	 */
	CardBenefits getCardBenefits(int cardBenefitsId);
	
	/**
	 * 카드혜택 등록
	 * @param cardBenefits
	 */
	void insertCardBenefits(CardBenefits cardBenefits);
	
	/**
	 * 카드혜택 수정
	 * @param cardBenefits
	 */
	void updateCardBenefits(CardBenefits cardBenefits);
	
	/**
	 * 카드혜택 삭제
	 * @param cardBenefitsId
	 */
	void deleteCardBenefits(CardBenefitsParam cardBenefitsParam);
	
	/**
	 * 현재 날짜에 대한 카드혜택을 가져온다.
	 * @param today
	 * @return
	 */
	CardBenefits getTodayCardBenefits(String today);
	
	/**
	 * 카드혜택 정보중 종료일자기준으로 시작일자가 중복 될경우 확인
	 * @param cardBenefits
	 * @return
	 */
	public int getDuplicateCardBenefitsCount(String startDate);
	
	/**
	 * 기간내 카드 혜택 있는지 여부체크
	 * @param cardBenefitsParam
	 * @since 2017-03-08_seungil.lee
	 * @return
	 */
	public List<CardBenefits> checkPeriod(CardBenefits cardBenefits);
}
