package saleson.common.notification;

import com.onlinepowers.framework.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import saleson.common.SalesonTest;
import saleson.common.notification.message.ShopMailMessage;

public class MailServiceTest extends SalesonTest {
	@Autowired
	@Qualifier("mailService")
	NotificationService mailService;

	@Test
	void mailTest() {
		String[] toMails = new String[] {
				"skc@onlinepowers.com",
				"khn@onlinepowers.com"
		};
		ShopMailMessage mail = new ShopMailMessage(toMails, "안녕하세요", "메일테스트입니다.", "리뉴올PC<webmail@renewallpc.co.kr>");

		mailService.sendMessage(mail);
	}
}
