package saleson.shop.maindisplayitem;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import saleson.shop.maindisplayitem.domain.MainDisplayItem;
import saleson.shop.maindisplayitem.support.MainDisplayItemParam;


public interface MainDisplayItemService {
	
	/**
	 * 등록된 상품 리스트 조회
	 * @param mainDisplayItemParam
	 * @return
	 */
	public List<MainDisplayItem> getMainDisplayItemListByParam(MainDisplayItemParam mainDisplayItemParam);
	
	/**
	 * 매인 노출 상품 등록
	 * @param mainDisplayItemParam
	 */
	public void insertMainDisplayItemByParam(MainDisplayItemParam mainDisplayItemParam);
	
	/**
	 * TEMPLATE ID로 삭제
	 * @param templateId
	 */
	public void deleteMainDisplayItemByTemplateId(String templateId);
}
