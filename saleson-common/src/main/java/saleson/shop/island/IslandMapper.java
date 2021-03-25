package saleson.shop.island;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("islandMapper")
public interface IslandMapper {
	
	int getSellerCount(IslandDto islandDto);
}
