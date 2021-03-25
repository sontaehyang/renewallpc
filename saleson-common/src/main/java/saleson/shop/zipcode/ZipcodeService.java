package saleson.shop.zipcode;

import java.util.List;

import saleson.shop.zipcode.domain.Zipcode;


public interface ZipcodeService {
	
	public Zipcode getZipcode(Zipcode zipcode);
	
	public List<String> getDodobuhyunList();
	
}
