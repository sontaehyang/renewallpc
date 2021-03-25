package saleson.shop.zipcode;

import java.util.List;

import saleson.shop.zipcode.domain.Zipcode;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("zipcodeMapper")
public interface ZipcodeMapper {
	
	Zipcode getZipcode(Zipcode zipcode);
	
	List<String> getDodobuhyunList();
	
}
