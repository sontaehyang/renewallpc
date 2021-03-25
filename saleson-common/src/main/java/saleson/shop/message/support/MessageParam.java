package saleson.shop.message.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class MessageParam extends SearchParam {

	private String id;
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
	

}
