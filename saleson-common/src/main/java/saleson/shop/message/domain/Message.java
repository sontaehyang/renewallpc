package saleson.shop.message.domain;

public class Message {
	
	private String id;
	private String language;
	private String kMessage;
	private String jMessage;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getkMessage() {
		return kMessage;
	}
	public void setkMessage(String kMessage) {
		this.kMessage = kMessage;
	}
	public String getjMessage() {
		return jMessage;
	}
	public void setjMessage(String jMessage) {
		this.jMessage = jMessage;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
