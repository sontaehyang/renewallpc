package com.onlinepowers.demo.domain;

public class Complex {

	private String id;
	private String userName;
	private String createdDate;
	
	private String[] itemId;
	private String[] itemName;
	
	private Simple rankSeo;
	private Simple reviewSeo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Simple getRankSeo() {
		return rankSeo;
	}
	public void setRankSeo(Simple rankSeo) {
		this.rankSeo = rankSeo;
	}
	public Simple getReviewSeo() {
		return reviewSeo;
	}
	public void setReviewSeo(Simple reviewSeo) {
		this.reviewSeo = reviewSeo;
	}
	
	public String[] getItemId() {
		return itemId;
	}
	public void setItemId(String[] itemId) {
		this.itemId = itemId;
	}
	public String[] getItemName() {
		return itemName;
	}
	public void setItemName(String[] itemName) {
		this.itemName = itemName;
	}
	
}
