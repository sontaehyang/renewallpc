package saleson.shop.maindisplayitem;

import java.util.List;

import saleson.shop.maindisplayitem.domain.MainDisplayItem;
import saleson.shop.maindisplayitem.support.MainDisplayItemParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("mainDisplayItemMapper")
public interface MainDisplayItemMapper {
	
	/**
	 * 등록된 상품 리스트 조회
	 * @param mainDisplayItemParam
	 * @return
	 */
	List<MainDisplayItem> getMainDisplayItemListByParam(MainDisplayItemParam mainDisplayItemParam);
	
	/**
	 * 매인 노출 상품 등록
	 * @param mainDisplayItem
	 */
	void insertMainDisplayItem(MainDisplayItem mainDisplayItem);
	
	/**
	 * TEMPLATE ID로 삭제
	 * @param templateId
	 */
	void deleteMainDisplayItemByTemplateId(String templateId);
	
}
