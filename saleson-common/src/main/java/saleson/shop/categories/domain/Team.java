package saleson.shop.categories.domain;

import java.util.ArrayList;
import java.util.List;


public class Team {
	private String url;
	private String name;
	private List<Group> groups = new ArrayList<>();
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
