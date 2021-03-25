package saleson.shop.item;

import com.onlinepowers.framework.util.StringUtils;
import org.springframework.stereotype.Component;
import saleson.common.utils.UserUtils;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;

@Component
public class ItemValidator {
	public void validate(Item item) {

	}

	public void validateItemParam(ItemParam itemParam) {
		if (itemParam.getPage() == 0) {
			itemParam.setPage(1);
		}

		if (!StringUtils.isEmpty(itemParam.getQuery())) {
			itemParam.setQuery(StringUtils.stripXSS(itemParam.getQuery()));
		}

		if (StringUtils.isEmpty(itemParam.getSort())
				|| !itemParam.getSort().matches("(ASC|DESC)")) {
			itemParam.setSort("ASC");
		}
		// 리스트 타입.
		if (StringUtils.isEmpty(itemParam.getListType())
				|| !itemParam.getListType().matches("(a|b|c)")) {
			itemParam.setListType("a");
		}

		if (!StringUtils.isEmpty(itemParam.getWhere())) {
			itemParam.setWhere(StringUtils.stripXSS(itemParam.getWhere()));
		}
	}

	public void validateCategoryItemParam(ItemParam itemParam) {
		validateItemParam(itemParam);

		if (StringUtils.isEmpty(itemParam.getOrderBy())
				|| !itemParam.getOrderBy().matches("(ORDERING|HITS|SALE_PRICE)")) {
			itemParam.setOrderBy("ORDERING");
		}
	}

	public void validateSpotItemParam(ItemParam itemParam) {
		validateItemParam(itemParam);

		if (StringUtils.isEmpty(itemParam.getOrderBy())
				|| !itemParam.getOrderBy().matches("(SPOT_END_DATE|HITS|SALE_PRICE)")) {
			itemParam.setOrderBy("SPOT_END_DATE");
		}

		// 진행중인 것만 (날짜기준)
		itemParam.setSpotStatus("1");

		// 그룹에 속한 회원만 (PC/MOBILE)
		if (!UserUtils.isManagerLogin()
				&& UserUtils.getUserDetail() != null
				&& StringUtils.isNotEmpty(UserUtils.getUserDetail().getGroupCode())) {
			itemParam.setSpotApplyGroup(UserUtils.getUserDetail().getGroupCode());
		}
	}
}
