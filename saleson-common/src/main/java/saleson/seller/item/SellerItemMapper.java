package saleson.seller.item;

import saleson.shop.item.ItemMapper;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("sellerItemMapper")
public interface SellerItemMapper extends ItemMapper {

	int getItemCountForSeller();
}
