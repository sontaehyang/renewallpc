package com.onlinepowers.demo.domain;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Demo {

	private int demoId;
	

	@Range(min = 4, max = 50)
	private String title;
	private String content;
	private String date;
	
	@NotEmpty
	@Email
	private String email;
	
	public Demo() {}
	public Demo(int demoId, String title, String content, String date) {
		this.demoId = demoId;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	public int getDemoId() {
		return demoId;
	}
	public void setDemoId(int demoId) {
		this.demoId = demoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}
