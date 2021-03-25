package saleson.shop.item.domain;

import saleson.shop.seo.domain.Seo;

public class ExcelItemSub {
	private String itemUserCode;
	private String itemName = "";
	
	private String itemSummary = "";
	private String listContent = "";
	
	private String useManual = "";
	private String makeManual = "";
	
	private String seoTitle = "";
	private String seoKeywords = "";
	private String seoDescription = "";
	
	private String itemKeyword = "";
	
	private String seoHeaderContents1 = "";
	private String seoThemawordTitle = "";
	private String seoThemawordDescription = "";
	
	private String detailContent = "";
	private String detailContentMobile = "";
	
	private Seo seo = new Seo();
	
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getListContent() {
		return listContent;
	}
	public void setListContent(String listContent) {
		this.listContent = listContent;
	}
	public String getUseManual() {
		return useManual;
	}
	public void setUseManual(String useManual) {
		this.useManual = useManual;
	}
	public String getMakeManual() {
		return makeManual;
	}
	public void setMakeManual(String makeManual) {
		this.makeManual = makeManual;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	
	public String getSeoHeaderContents1() {
		return seoHeaderContents1;
	}
	public void setSeoHeaderContents1(String seoHeaderContents1) {
		this.seoHeaderContents1 = seoHeaderContents1;
	}
	public String getSeoThemawordTitle() {
		return seoThemawordTitle;
	}
	public void setSeoThemawordTitle(String seoThemawordTitle) {
		this.seoThemawordTitle = seoThemawordTitle;
	}
	public String getSeoThemawordDescription() {
		return seoThemawordDescription;
	}
	public void setSeoThemawordDescription(String seoThemawordDescription) {
		this.seoThemawordDescription = seoThemawordDescription;
	}
	public String getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}
	public String getDetailContentMobile() {
		return detailContentMobile;
	}
	public void setDetailContentMobile(String detailContentMobile) {
		this.detailContentMobile = detailContentMobile;
	}
	public String getItemKeyword() {
		return itemKeyword;
	}
	public void setItemKeyword(String itemKeyword) {
		this.itemKeyword = itemKeyword;
	}
	public String getItemSummary() {
		return itemSummary;
	}
	public void setItemSummary(String itemSummary) {
		this.itemSummary = itemSummary;
	}
	public Seo getSeo() {
		return seo;
	}
	public void setSeo(Seo seo) {
		this.seo = seo;
	}
	
	
	
	
	
}
