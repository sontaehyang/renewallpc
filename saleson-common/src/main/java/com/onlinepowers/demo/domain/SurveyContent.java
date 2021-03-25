package com.onlinepowers.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class SurveyContent {
	private int surveyContentId;
	private int surveyId;
	private String content;
	private String contentType;
	private int ordering;
	
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	private List<SurveyItem> surveyItemList = new ArrayList<>();
	
	public int getSurveyContentId() {
		return surveyContentId;
	}
	public void setSurveyContentId(int surveyContentId) {
		this.surveyContentId = surveyContentId;
	}
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public List<SurveyItem> getSurveyItemList() {
		return surveyItemList;
	}
	public void setSurveyItemList(List<SurveyItem> surveyItemList) {
		this.surveyItemList = surveyItemList;
	}
	
	
}
