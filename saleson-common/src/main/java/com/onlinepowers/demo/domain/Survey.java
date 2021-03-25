package com.onlinepowers.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Survey {
	private int surveyId;
	private String subject;
	private String startDate;
	private String endDate;
	private String surveyType;
	private String resultType;
	private String creationDate;
	
	private List<SurveyContent> surveyContentList = new ArrayList<>();
	
	
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSurveyType() {
		return surveyType;
	}
	public void setSurveyType(String surveyType) {
		this.surveyType = surveyType;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public List<SurveyContent> getSurveyContentList() {
		return surveyContentList;
	}
	public void setSurveyContentList(List<SurveyContent> surveyContentList) {
		this.surveyContentList = surveyContentList;
	}
	
	
	
}
