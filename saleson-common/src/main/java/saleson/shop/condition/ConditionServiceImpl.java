package saleson.shop.condition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.condition.domain.CategoryCondition;
import saleson.shop.condition.domain.Condition;
import saleson.shop.condition.domain.ConditionDetail;
import saleson.shop.condition.support.ConditionParam;

/**
 * @author seungil.lee
 * @since 2017-07-05
 */

@Service("conditionService")
public class ConditionServiceImpl implements ConditionService {
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private ConditionMapper conditionMapper;
	
	@Override
	public List<CategoryCondition> getCategoryConditionList() {
		return conditionMapper.getCategoryConditionList();
	}
	
	@Override
	public CategoryCondition getCategoryInfo(ConditionParam param) {
		return conditionMapper.getCategoryInfo(param);
	}
	
	@Override
	public CategoryCondition getCategoryCondition(ConditionParam param) {
		return conditionMapper.getCategoryCondition(param);
	}
	
	@Override
	public void insertCondition(Condition condition) {
		condition.setConditionId(sequenceService.getId("OP_CONDITION"));
		conditionMapper.insertCondition(condition);
	}
	
	@Override
	public void updateCondition(Condition condition) {
		conditionMapper.updateCondition(condition);
	}
	
	@Override
	public void insertConditionDetail(ConditionDetail conditionDetail) {
		conditionDetail.setDetailId(sequenceService.getId("OP_CONDITION_DETAIL"));
		conditionMapper.insertConditionDetail(conditionDetail);
	}
	
	@Override
	public void updateConditionDetail(ConditionDetail conditionDetail) {
		conditionMapper.updateConditionDetail(conditionDetail);
	}
	
	@Override
	public void updateDetailOrdering(ConditionParam conditionParam) {
		conditionMapper.updateDetailOrdering(conditionParam);
	}
}
