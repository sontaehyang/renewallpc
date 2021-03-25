package saleson.shop.maindisplayitem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.shop.maindisplayitem.domain.MainDisplayItem;
import saleson.shop.maindisplayitem.support.MainDisplayItemParam;

@Service("mainDisplayItemService")
public class MainDisplayItemServiceImpl implements MainDisplayItemService {

	@Autowired
	private MainDisplayItemMapper mainDisplayItemMapper;

	@Override
	public List<MainDisplayItem> getMainDisplayItemListByParam(
			MainDisplayItemParam mainDisplayItemParam) {
		
		return mainDisplayItemMapper.getMainDisplayItemListByParam(mainDisplayItemParam);
	}

	@Override
	public void insertMainDisplayItemByParam(MainDisplayItemParam mainDisplayItemParam) {
		
		MainDisplayItem mainDisplayItem = new MainDisplayItem();
		mainDisplayItem.setTemplateId(mainDisplayItemParam.getTemplateId());
		
		int displayOrder = 0;
		if (mainDisplayItemParam.getMainDisplayItemIds() == null) {
			return;
		}
		
		for(String itemId : mainDisplayItemParam.getMainDisplayItemIds()) {
			mainDisplayItem.setItemId(Integer.parseInt(itemId));
			mainDisplayItem.setDisplayOrder(displayOrder++);
			mainDisplayItemMapper.insertMainDisplayItem(mainDisplayItem);
		}
	}

	@Override
	public void deleteMainDisplayItemByTemplateId(String templateId) {
		mainDisplayItemMapper.deleteMainDisplayItemByTemplateId(templateId);
	}

}
