package saleson.seller.menu;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.web.opmanager.menu.domain.Menu;
import com.onlinepowers.framework.web.opmanager.menu.domain.MenuRight;

@Service("sellerMenuService")
public class MenuServiceImpl implements MenuService {
	@Autowired 
	private MenuMapper sellerMenuMapper;
	@Autowired SequenceService sequenceService;
	
	
	@Override
	public String getMenuCode(HashMap<String, Object> map) {
		return sellerMenuMapper.getMenuCode(map);
	}
	
	@Override
	@Cacheable(value="sellerFirstMenu", key="#menu.cacheKey")
	public List<Menu> getFirstMenuList(Menu menu) {
		return sellerMenuMapper.getFirstMenuList(menu);
	}

	@Override
	@Cacheable(value="sellerSecondAndThirdMenu", key="#menu.cacheKey")
	public List<Menu> getSecondAndThirdMenuList(Menu menu) {
		return sellerMenuMapper.getSecondAndThirdMenuList(menu);
	}

	@Override
	public List<Menu> getAllMenuList() {
		return sellerMenuMapper.getAllMenuList();
	}

	@Override
	public void updateSequence(int[] menuIds) {
		for (int i = 0; i < menuIds.length; i++) {
			Menu menu = new Menu();
			menu.setMenuId(menuIds[i]);
			menu.setMenuSeq(i + 1);
			sellerMenuMapper.updateSequence(menu);
		}
	}

	@Override
	public void insertMenu(Menu menu) {
		menu.setMenuId(sequenceService.getId("OP_MENU"));
		sellerMenuMapper.insertMenu(menu);
	}

	@Override
	public void delete(int menuId) {
		sellerMenuMapper.deleteMenuRightByMenuId(menuId);
		sellerMenuMapper.deleteThirdMenu(menuId);
		sellerMenuMapper.deleteSecondMenu(menuId);
		sellerMenuMapper.delete(menuId);
	}

	
	@Override
	public void updateMenu(Menu menu) {
		sellerMenuMapper.updateMenu(menu);
	}

	@Override
	public List<MenuRight> getMenuRightListByAuthority(String authority) {
		return sellerMenuMapper.getMenuRightListByAuthority(authority);
	}

	@Override
	public String getFisrtMenuUrl(String authority) {
		return sellerMenuMapper.getFisrtMenuUrl(authority);
	}
}
