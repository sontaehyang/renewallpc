package saleson.shop.zipcode;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.util.StringUtils;

import saleson.shop.zipcode.domain.Zipcode;


@Service("zipcodeService")
public class ZipcodeServiceImple implements ZipcodeService{
	private static final Logger log = LoggerFactory.getLogger(ZipcodeServiceImple.class);
	
	@Autowired 
	ZipcodeMapper zipcodeMapper;

	@Override
	public Zipcode getZipcode(Zipcode zipcode) {

		return zipcodeMapper.getZipcode(zipcode);
	}

	@Override
	public List<String> getDodobuhyunList() {
		// 도도부현 순서 때문에 DB조회 -> 고정으로 변경 (skc20140910)
		//return zipcodeMapper.getDodobuhyunList();
		
		String[] DODO = StringUtils.delimitedListToStringArray(
				"北海道,青森県,岩手県,宮城県,秋田県,山形県,福島県,茨城県,栃木県,群馬県,埼玉県,千葉県,東京都,神奈川県,新潟県,富山県,石川県,福井県,山梨県,長野県,岐阜県,静岡県,愛知県,三重県,滋賀県,京都府,大阪府,兵庫県,奈良県,和歌山県,鳥取県,島根県,岡山県,広島県,山口県,徳島県,香川県,愛媛県,高知県,福岡県,佐賀県,長崎県,熊本県,大分県,宮崎県,鹿児島県,沖縄県"
				, ",");
		
		List<String> dodoList = new ArrayList<>();
		for (String dodobuhyun : DODO) {
			dodoList.add(dodobuhyun);
		}
		return dodoList;
	}
}
