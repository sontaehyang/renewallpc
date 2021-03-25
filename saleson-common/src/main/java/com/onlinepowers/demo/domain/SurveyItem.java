package com.onlinepowers.demo.domain;

public class SurveyItem {
	private int surveyItemId;
	private int surveyContentId;
	private String itemContent;
	private int ordering;
	private int surveyCount;
	
	public int getSurveyItemId() {
		return surveyItemId;
	}
	public void setSurveyItemId(int surveyItemId) {
		this.surveyItemId = surveyItemId;
	}
	public int getSurveyContentId() {
		return surveyContentId;
	}
	public void setSurveyContentId(int surveyContentId) {
		this.surveyContentId = surveyContentId;
	}
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	public int getSurveyCount() {
		return surveyCount;
	}
	public void setSurveyCount(int surveyCount) {
		this.surveyCount = surveyCount;
	}
	
	
	
}
