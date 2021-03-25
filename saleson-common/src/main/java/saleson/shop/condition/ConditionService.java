package saleson.shop.condition;

import java.util.List;

/**
 * @author seungil.lee
 * @since 2017-07-05
 */

import saleson.shop.condition.domain.CategoryCondition;
import saleson.shop.condition.domain.Condition;
import saleson.shop.condition.domain.ConditionDetail;
import saleson.shop.condition.support.ConditionParam;

public interface ConditionService {
	// 그룹, 카테고리 포함 검색조건 목록
	public List<CategoryCondition> getCategoryConditionList();
	
	// 카테고리 정보 받아오기 (create)
	public CategoryCondition getCategoryInfo(ConditionParam param);
	
	// 그룹, 카테고리 포함 검색조건 (edit)
	public CategoryCondition getCategoryCondition(ConditionParam param);
	
	// 검색조건 등록
	public void insertCondition(Condition condition);
	
	// 검색조건 수정
	public void updateCondition(Condition condition);
	
	// 검색조건 상세 등록
	public void insertConditionDetail(ConditionDetail conditionDetail);
	
	// 검색조건 상세 수정
	public void updateConditionDetail(ConditionDetail conditionDetail);
	
	// 검색조건 상세 순서변경
	public void updateDetailOrdering(ConditionParam conditionParam);
}
