package saleson.shop.cardbenefits;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.cardbenefits.domain.CardBenefits;
import saleson.shop.cardbenefits.support.CardBenefitsParam;

@Service("cardBenefitsService")
public class CardBenefitsServiceImpl implements CardBenefitsService {

	@Autowired
	CardBenefitsMapper cardBenfitsMapper;
	
	@Autowired
	SequenceService sequenceService;
	
	@Override
	public int getCardBenefitsCount(CardBenefitsParam cardBenefitsParam) {

		return cardBenfitsMapper.getCardBenefitsCount(cardBenefitsParam);
	}

	@Override
	public List<CardBenefits> getCardBenefitsList(CardBenefitsParam cardBenefitsParam) {

		return cardBenfitsMapper.getCardBenefitsList(cardBenefitsParam);
	}

	@Override
	public CardBenefits getCardBenefits(int cardBenefitsId) {

		return cardBenfitsMapper.getCardBenefits(cardBenefitsId);
	}

	@Override
	public void insertCardBenefits(CardBenefits cardBenefits) {

		cardBenefits.setBenefitsId(sequenceService.getId("OP_CARD_BENEFITS"));
		cardBenfitsMapper.insertCardBenefits(cardBenefits);
	}

	@Override
	public void updateCardBenefits(CardBenefits cardBenefits) {

		cardBenfitsMapper.updateCardBenefits(cardBenefits);
	}

	@Override
	public void deleteCardBenefits(CardBenefitsParam cardBenefitsParam) {

				
		cardBenfitsMapper.deleteCardBenefits(cardBenefitsParam);
	}

	@Override
	public CardBenefits getTodayCardBenefits(String today) {

		return cardBenfitsMapper.getTodayCardBenefits(today);
	}
	
	@Override
	public int getDuplicateCardBenefitsCount(String startDate) {
		return cardBenfitsMapper.getDuplicateCardBenefitsCount(startDate);
	}

	@Override
	public List<CardBenefits> checkPeriod(CardBenefits cardBenefits) {
		return cardBenfitsMapper.checkPeriod(cardBenefits);
	}
	
	

}
