package saleson.shop.condition;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.condition.domain.CategoryCondition;
import saleson.shop.condition.domain.Condition;
import saleson.shop.condition.domain.ConditionDetail;
import saleson.shop.condition.support.ConditionParam;

/**
 * @author seungil.lee
 * @since 2017-07-05
 */

@Mapper("conditionMapper")
public interface ConditionMapper {
	
	// 그룹, 카테고리 포함 검색조건 목록
	List<CategoryCondition> getCategoryConditionList();
	
	// 카테고리 정보 받아오기 (create)
	CategoryCondition getCategoryInfo(ConditionParam param);
	
	// 그룹, 카테고리 포함 검색조건 (edit)
	CategoryCondition getCategoryCondition(ConditionParam param);
	
	// 검색조건 등록
	void insertCondition(Condition condition);
	
	// 검색조건 수정
	void updateCondition(Condition condition);
	
	// 검색조건 상세 등록
	void insertConditionDetail(ConditionDetail conditionDetail);
	
	// 검색조건 상세 수정
	void updateConditionDetail(ConditionDetail conditionDetail);
	
	// 검색조건 상세 순서변경
	void updateDetailOrdering(ConditionParam conditionParam);
}
