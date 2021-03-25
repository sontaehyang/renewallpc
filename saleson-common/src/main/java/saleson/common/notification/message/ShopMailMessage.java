package saleson.common.notification.message;

import com.onlinepowers.framework.notification.message.MailMessage;
import com.onlinepowers.framework.notification.message.OpMessage;
import java.util.Arrays;
import java.util.Collection;
import saleson.common.utils.CommonUtils;


public class ShopMailMessage extends MailMessage implements OpMessage {
	private String toEmail;
	private String[] receivers;
	private String title;
	private String content;
	private String from;
	private boolean isHtml = false;
	
	public ShopMailMessage(String toEmail, String title, String content, String from) {
		
		if (this.isHTML() == true) {
			content = content.replace("\n", "<br/>");
		}
		
		this.receivers = new String[1];
		this.receivers[0] = toEmail;
		this.title = title;
		this.content = content;
		this.from = from;
	}
	
	public ShopMailMessage(String[] toEmails, String title, String content, String from) {
		this.receivers = toEmails;
		this.title = title;
		this.content = content;
		this.from = from;
	}
	
	@Override
	public String getMessage() {

		return null;
	}

	@Override
	public Collection<String> getMessageReceivers() {

		return null;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String[] getReceivers() {
		return CommonUtils.copy(receivers);
	}

	@Override
	public String getFrom() {
		return from;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}

	@Override
	public boolean isHTML() {

		return this.isHtml;
	}


	@Override
	public String toString() {
		return "ShopMailMessage [toEmail=" + toEmail + ", receivers="
				+ Arrays.toString(receivers) + ", title=" + title
				+ ", content=" + content + ", from=" + from + "]";
	}

	@Override
	public String[] getCc() {
		return CommonUtils.copy(cc);
	}

	@Override
	public String[] getBcc() {
		return CommonUtils.copy(bcc);
	}

	@Override
	public String getSmsType() {

		return null;
	}
}
