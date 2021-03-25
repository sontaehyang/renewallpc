package saleson.shop.item.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class SearchIndexParam extends SearchParam  {
	
	private String index = "";
	private String startIndex = "";		// 상품 검색 조회용 
	private String endIndex = "";		// 상품 검색 조회용
	
	private String firstIndex = "";		// active index
	private String secondIndex = "";	// active index
	
	private int depth;
	private String type = "";			// A:알파벳, N:숫자
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
		
		boolean isMatched = false;
		// a-z 인가?
		if ("a-z".equalsIgnoreCase(index)) {
			this.startIndex = "a-z";
			this.firstIndex = "a-z";
			this.depth = 1;
			this.type = "A";
			isMatched = true;
		}
		
		// a~z 인가?
		if (!isMatched) {
			for(char j = 'a'; j <= 'z' ; j++) {
				if (String.valueOf(j).equalsIgnoreCase(index)) {
					this.firstIndex = "a-z";
					this.secondIndex = index;
					
					this.startIndex = index;
					this.depth = 2;
					this.type = "A";
					
					isMatched = true;
					break;
				}
	        }
		}
		
		
		Pattern pattern = Pattern.compile("^[0-9]{1,5}-[0-9]{3,5}$");
		Matcher matcher = pattern.matcher(index);
		
		if (matcher.matches()) {
			this.type = "N";
			
			// 색인범위
			String[] indexes = StringUtils.delimitedListToStringArray(index, "-");
			this.startIndex = indexes[0];
			this.endIndex = indexes[1];
			
			// 뎁스 판단하기
			String startCheckValue = this.startIndex.equals("0") ? "0" : this.startIndex.substring(this.startIndex.length() - 3, this.startIndex.length());
			String endCheckValue = this.endIndex.substring(this.endIndex.length() - 3, this.endIndex.length());
			
			if (("0".equals(startCheckValue) || "000".equals(startCheckValue))
					&& "999".equals(endCheckValue)) {
				this.depth = 1;
				
				this.firstIndex = index;
			} else {
				if (this.startIndex.length() < 4) {
					this.firstIndex = "0-999";
				} else if (this.startIndex.length() == 4) {
					String value = this.startIndex.substring(0, 1);
					this.firstIndex = value + "000-" + value + "999";
				} else {
					String value = this.startIndex.substring(0, 2);
					this.firstIndex = value + "000-" + value + "999";
				}
				this.secondIndex = index;
			}
		}
	}
	public String getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}
	public String getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(String endIndex) {
		this.endIndex = endIndex;
	}
	
	
	public String getFirstIndex() {
		return firstIndex;
	}
	public void setFirstIndex(String firstIndex) {
		this.firstIndex = firstIndex;
	}
	public String getSecondIndex() {
		return secondIndex;
	}
	public void setSecondIndex(String secondIndex) {
		this.secondIndex = secondIndex;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 1차 인덱스 카운트 조회 범위
	 * @return
	 */
	public List<HashMap<String, Object>> getIndexList() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		

		for (int i = 0; i <= 23; i++) {
			HashMap<String, Object> index = new HashMap<>();
			
			index.put("start", (i * 1000) + "");
			index.put("end", ((i * 1000) + 999) + "");
			
			list.add(index);
		}
		return list;
	}
	
	/**
	 * 2차 인덱스 조회 범위
	 * @return
	 */
	public List<HashMap<String, Object>> getSubIndexList() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		if ("".equals(firstIndex)) {
			return list;
		}
		
		if ("a-z".equals(firstIndex)) {
			for(char j = 'a'; j <= 'z' ; j++) {
				HashMap<String, Object> index = new HashMap<>();
				index.put("start", String.valueOf(j));
				index.put("end", "");
				list.add(index);
	        }
		
		} else {
			
			int startNumber = Integer.parseInt(StringUtils.delimitedListToStringArray(this.firstIndex, "-")[0]);
			
			for (int i = 0; i <= 9; i++) {
				HashMap<String, Object> index = new HashMap<>();
				
				index.put("start", (startNumber + (i * 100)) + "");
				index.put("end", (startNumber + 99 + (i * 100)) + "");
				
				list.add(index);
			}
		}
		
		return list;
	}
}
