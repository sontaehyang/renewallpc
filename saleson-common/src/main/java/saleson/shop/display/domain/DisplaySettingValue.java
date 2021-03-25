package saleson.shop.display.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DisplaySettingValue {
	private static final Logger log = LoggerFactory.getLogger(DisplaySettingValue.class);

	// 템플릿 기본 정보
	private String title = ""; // 템플릿 타이틀
	private String key = ""; 
	private int type = 0; // 1 : 이미지 2 : 에디터 3 : 상품 
	private int count = 0; // 해당 컨텐츠 개수
	private String rollingYN = "N"; // 롤링여부
	private String etc = ""; 
	
	// 이미지정보
	private int width = 0; // type이 1일 경우 해당
	private int height = 0; // type이 1일 경우 해당
	
	// 관리자에서 보여지는 미리보기 사이즈
	private int thumbHeight = 0; // type이 1일 경우 해당
	private int thumbWidth = 300; // type이 1일 경우 해당
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRollingYN() {
		return rollingYN;
	}
	public void setRollingYN(String rollingYN) {
		this.rollingYN = rollingYN;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getThumbHeight() {
		// 템플릿 타입이 이미지이고 사이즈가 0보다 클경우
		if (this.type == 1 && this.width > 0 ) {
			try {
				//thumbHeight = (int) Math.round(height / Math.round((double)width / (double)thumbWidth));
				thumbHeight = height * thumbWidth / width;
			} catch (Exception e) {

				log.warn("[Exception] getThumbHeight() : {}", e.getMessage());
			}
		}
		
		return thumbHeight;
	}

	public void setThumbHeight(int thumbHeight) {
		this.thumbHeight = thumbHeight;
	}

	public int getThumbWidth() {
		return thumbWidth;
	}

	public void setThumbWidth(int thumbWidth) {
		this.thumbWidth = thumbWidth;
	}
	
	
	
}
