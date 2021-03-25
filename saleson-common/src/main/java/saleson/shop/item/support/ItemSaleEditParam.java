package saleson.shop.item.support;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class ItemSaleEditParam extends SearchParam{

	private int itemSaleEditId;
	private int itemId;
	private long sellerId;
	private String sellerName;
	
	//가격요청상태
	private String status;
	
	// 가격
	private String priceRange;
	
	// 검색 시작일 
	private String searchStartDate;
		
	// 검색 종료일 
	private String searchEndDate;


	
	public int getItemSaleEditId() {
		return itemSaleEditId;
	}

	public void setItemSaleEditId(int itemSaleEditId) {
		this.itemSaleEditId = itemSaleEditId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	
	public String getPriceStart() {
		if (this.priceRange != null && !this.priceRange.equals("")) {
			return StringUtils.delimitedListToStringArray(this.priceRange, "|")[0];
		}
		return "";
	}
	
	public String getPriceEnd() {
		if (this.priceRange != null && !this.priceRange.equals("") && this.priceRange.indexOf("|") > -1) {
			return StringUtils.delimitedListToStringArray(this.priceRange, "|")[1];
		}
		return "";
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	
}
