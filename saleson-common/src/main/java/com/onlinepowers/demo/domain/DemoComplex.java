package com.onlinepowers.demo.domain;

import java.util.List;

public class DemoComplex {
	private int id;
	private String demoName;
	private Demo demo = new Demo();
	
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
	public List<Demo> getDemos() {
		return demos;
	}
	public void setDemos(List<Demo> demos) {
		this.demos = demos;
	}
	private List<Demo> demos;
	
	
}
