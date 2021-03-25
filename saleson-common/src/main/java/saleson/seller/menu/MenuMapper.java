package saleson.seller.menu;

import java.util.HashMap;
import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;
import com.onlinepowers.framework.web.opmanager.menu.domain.MenuRight;

@Mapper("sellerMenuMapper")
public interface MenuMapper {
	public List<Menu> getFirstMenuList(Menu menu);

	public List<Menu> getSecondAndThirdMenuList(Menu menu);

	public List<Menu> getAllMenuList();

	/**
	 * 메뉴 순서를 변경한다.
	 * <pre>
	 * Menu 객체에 menuId 와 seq 값을 업데이트 한다.
	 * </pre>
	 * @param menu
	 */
	public void updateSequence(Menu menu);

	public void insertMenu(Menu menu);

	public void delete(int menuId);

	public void deleteThirdMenu(int menuId);

	public void deleteSecondMenu(int menuId);

	public void deleteMenuRightByMenuId(int menuId);

	/**
	 * URI 기준 예상 menuCode를 조회하여 실제 menuCode를 찾아낸다.
	 * @param map 
	 * @return  실제 menuCode
	 */
	public String getMenuCode(HashMap<String, Object> map );

	public void updateMenu(Menu menu);

	public void deleteMenuRightByAuthority(String roleId);

	public void insertMenuRight(MenuRight menuRight);

	public List<MenuRight> getMenuRightListByAuthority(String authority);

	public String getFisrtMenuUrl(String authority);
}
