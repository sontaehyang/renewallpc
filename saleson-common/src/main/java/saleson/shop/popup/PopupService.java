package saleson.shop.popup;

import java.util.List;

import com.onlinepowers.framework.web.domain.ListParam;

import saleson.shop.popup.domain.Popup;
import saleson.shop.popup.domain.PopupSearchParam;

public interface PopupService {
	
	/**
	 * 팝업 수량 조회
	 * @param popupSearchParam
	 * @return
	 */
	int popupCount(PopupSearchParam popupSearchParam); 
	
	/**
	 * 팝업 리스트 조회
	 * @param popupSearchParam
	 * @return
	 */
	List<Popup> popupList(PopupSearchParam popupSearchParam);
	
	/**
	 * 팝업 조회
	 * @param popupId
	 * @return
	 */
	Popup getPopup(int popupId);
	
	/**
	 * 팝업 등록
	 * @param popup
	 */
	void insertPopup(Popup popup);
	
	/**
	 * 팝업 수정
	 * @param popup
	 */
	void updatePopup(Popup popup);
	
	/**
	 * 팝업 삭제
	 * @param popupId
	 */
	void deletePopup(int popupId);
	
	/**
	 * 팝업리스트 삭제
	 * @param listparam
	 */
	void deletePopupData(ListParam listparam);
	
	/**
	 * 팝업이미지 삭제
	 * @param popupId
	 */
	void deletePopupImage(int popupId);
	
	/**
	 * 프론트 팝업노출 리스트
	 * @return
	 */
	List<Popup> displayPopupList();
}