package saleson.seller.menu;

import java.util.List;

import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;

public class SellerMenu {
	private int firstMenuId;
	private String menuCode;
	private List<Menu> firstMenuList;
	private List<Menu> secondAndThirdMenuList;
	
	
	public int getFirstMenuId() {
		return firstMenuId;
	}
	public void setFirstMenuId(int firstMenuId) {
		this.firstMenuId = firstMenuId;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public List<Menu> getFirstMenuList() {
		return firstMenuList;
	}
	public void setFirstMenuList(List<Menu> firstMenuList) {
		this.firstMenuList = firstMenuList;
	}
	public List<Menu> getSecondAndThirdMenuList() {
		return secondAndThirdMenuList;
	}
	public void setSecondAndThirdMenuList(List<Menu> secondAndThirdMenuList) {
		this.secondAndThirdMenuList = secondAndThirdMenuList;
	}
}
