package saleson.seller.menu;


import java.util.HashMap;
import java.util.List;

import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;
import com.onlinepowers.framework.web.opmanager.menu.domain.MenuRight;

public interface MenuService {
	public List<Menu> getFirstMenuList(Menu menu);

	public List<Menu> getSecondAndThirdMenuList(Menu menu);

	public List<Menu> getAllMenuList();

	public void updateSequence(int[] menuIds);

	public void insertMenu(Menu menu);

	public void delete(int menuId);

	/**
	 * URI 기준의 예상 menuCode로 부터 권한이 있는 메뉴의 DB 메뉴코드를 찾아 실제 menuCode를 반환한다.
	 * @param map 예상 menuCode ('/opmanager/sample/list' 와  '/opmanager/sample/')형태
	 * @return DB menuCode
	 */
	public String getMenuCode(HashMap<String, Object> map);

	
	/**
	 * 메뉴의 정보를 업데이트 한다.
	 * @param menu
	 */
	public void updateMenu(Menu menu);

	public List<MenuRight> getMenuRightListByAuthority(String authority);

	
	/**
	 * 해당 롤에 대한 권한이 있는 메뉴 중 첫번째 URL을 가져온다.
	 * @param authority
	 * @return
	 */
	public String getFisrtMenuUrl(String authority);
}
