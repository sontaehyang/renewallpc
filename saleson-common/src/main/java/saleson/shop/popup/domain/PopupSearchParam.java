package saleson.shop.popup.domain;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class PopupSearchParam extends SearchParam {
	
	private String popupClose;
	private String popupStyle;
	private String startDate;
	private String endDate;
	
	
	public String getPopupClose() {
		return popupClose;
	}
	public void setPopupClose(String popupClose) {
		this.popupClose = popupClose;
	}
	public String getPopupStyle() {
		return popupStyle;
	}
	public void setPopupStyle(String popupStyle) {
		this.popupStyle = popupStyle;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}