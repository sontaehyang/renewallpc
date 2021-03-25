package com.onlinepowers.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.onlinepowers.framework.file.domain.TempFile;

public class DemoComplex2 {
	private int id;
	private String demoName;
	private Demo demo = new Demo();
	private List<TempFile> tempFiles = new ArrayList<>();
	
	
	
	public List<TempFile> getTempFiles() {
		return tempFiles;
	}
	public void setTempFiles(List<TempFile> tempFiles) {
		this.tempFiles = tempFiles;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDemoName() {
		return demoName;
	}
	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}
	public Demo getDemo() {
		return demo;
	}
	public void setDemo(Demo demo) {
		this.demo = demo;
	}
	public List<Demo2> getDemos() {
		return demos;
	}
	public void setDemos(List<Demo2> demos) {
		this.demos = demos;
	}
	private List<Demo2> demos;
	
	
}
