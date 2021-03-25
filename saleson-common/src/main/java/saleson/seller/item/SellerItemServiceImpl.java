package saleson.seller.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.item.ItemServiceImpl;

@Service("sellerItemService")
public class SellerItemServiceImpl extends ItemServiceImpl implements SellerItemService{
	@Autowired
	private SellerItemMapper sellerItemMapper;

	@Override
	public int getItemCountForSeller() {
		return sellerItemMapper.getItemCountForSeller();
	}
	
	
}
