package saleson.shop.mailconfig.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class MailConfigSearchParam extends SearchParam {
	private int mailConfigId;
	private String templateId;
	
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getMailConfigId() {
		return mailConfigId;
	}

	public void setMailConfigId(int mailConfigId) {
		this.mailConfigId = mailConfigId;
	}
	
	
	
}
