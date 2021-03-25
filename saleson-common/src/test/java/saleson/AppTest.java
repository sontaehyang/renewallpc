package saleson;

import com.onlinepowers.framework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class AppTest {

	@Test
	public void mailTestReal() throws UnsupportedEncodingException {
		// "v=spf1 include:spf.cafe24.com ~all"
		String user = "webmail@renewallpc.co.kr"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
		String password = "webmail92%@";   // 패스워드


		// SMTP 서버 정보를 설정한다.
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.renewallpc.co.kr");
		prop.put("mail.smtp.port", 25);

		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "false");
		prop.put("mail.smtp.starttls.required", "false");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

			helper.setTo("ksh@onlinepowers.com");
			helper.setSubject("[리뉴올PC] 테스트메일 x ");
			helper.setText("테스트메일", true);
			helper.setFrom(user, "리뉴올PC");




			// send the message
			Transport.send(message); ////전송
			System.out.println("message sent successfully...");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void mailTest() {
		String user = "shop@renewallpc.tk"; // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
		String password = "1111";   // 패스워드


		// SMTP 서버 정보를 설정한다.
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.renewallpc.tk");
		prop.put("mail.smtp.port", 25);

		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "false");
		prop.put("mail.smtp.starttls.required", "false");

		Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

			helper.setTo("ksh@onlinepowers.com");
			helper.setSubject("[리뉴올PC] 테스트메일  ");
			helper.setText("테스트메일", true);
			helper.setFrom(user);




			// send the message
			Transport.send(message); ////전송
			System.out.println("message sent successfully...");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test2() throws MessagingException {

		String username = "noreply@emoldino.com";
		String password = "emoldino1234";
		String from = "noreply@emoldino.com";

		String recipient = "skc@onlinepowers.com";





		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.from", from);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		props.setProperty("mail.debug", "true");

		Session session = Session.getInstance(props, null);
		MimeMessage msg = new MimeMessage(session);

		msg.setRecipients(Message.RecipientType.TO, recipient);
		msg.setSubject("JavaMail hello world example");
		msg.setSentDate(new Date());
		msg.setText("Hello, world!\n");

		Transport transport = session.getTransport("smtp");

		transport.connect(username, password);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}

	@Test
	void listTest() {

		List<String> list = Arrays.asList(
			"1",
			"2",
			"3",
			"4",
			"5",
			"6",
			"7",
			"8",
			"9",
			"10",
			"11",
			"12",
			"13"
		);

		AtomicInteger index = new AtomicInteger(0);
		Collection<List<String>> groupList = list.stream()
				.collect(Collectors.groupingBy(i -> index.getAndIncrement() / 3))
				.values();


		List<List<String>> groupList2 = new ArrayList<>(groupList);


		for (List<String> lists: groupList) {
			log.debug("LIST : {}", lists.size());

			for (String value: lists) {
				log.debug("-- value : {}", value);
			}
		}

	}

























	@Test
	void groupingByTest() {
		List<String> list = Arrays.asList(
				"1",
				"2",
				"3",
				"4",
				"5",
				"6",
				"7",
				"8",
				"9",
				"10",
				"11",
				"12",
				"13"
		);

		final AtomicInteger counter = new AtomicInteger(0);
		// Map<Integer, List<String>> groups = ;

		List<List<String>> groupList = new ArrayList<>(list.stream()
				.collect(Collectors.groupingBy(i -> counter.getAndIncrement() / 3))
				.values());


		for (List<String> sl : groupList) {
			log.debug("List ==> {}", sl.size());

			for (String code: sl) {
				log.debug(" - {} ", code);

			}
		}
		// partitionIntegerListBasedOnSize

	}

	@Test
	void regexTest() {
		String regex = "(a|b|c)";


		assertThat("a".matches(regex)).isTrue();
		assertThat("b".matches(regex)).isTrue();
		assertThat("c".matches(regex)).isTrue();
		assertThat("aa".matches(regex)).isFalse();
		assertThat("bb".matches(regex)).isFalse();
		assertThat("cc".matches(regex)).isFalse();
	}
	@Test
	void regexTest2() {
		String regex = "(ASC|DESC)";


		assertThat("asc".matches(regex)).isFalse();
		assertThat("ASC".matches(regex)).isTrue();
		assertThat("desc".matches(regex)).isFalse();
		assertThat("DESC".matches(regex)).isTrue();
		assertThat("DESCA".matches(regex)).isFalse();
		assertThat("AASC".matches(regex)).isFalse();
		assertThat("AASC".matches(regex)).isFalse();
	}
	@Test
	void regexTest3() {
		String regex = "(SPOT_END_DATE|HITS|SALE_PRICE)";


		assertThat("SPOT_END_DATE".matches(regex)).isTrue();
		assertThat("SPOT_END_DATE2".matches(regex)).isFalse();
	}

	@Test
	void xssTest() {
		String orgValue = "https://saleson3.onlinepowers.com:8443/event/new?\"onMouseover='0lFu(9900)'bad=\"";

		String value = orgValue;
		log.debug(value);
		Pattern scriptPattern = Pattern.compile("onload(.*?)=", 42);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("onmouseover(.*?)=", 42);
		value = scriptPattern.matcher(value).replaceAll("");

		scriptPattern = Pattern.compile("onerror(.*?)=", 42);
		value = scriptPattern.matcher(value).replaceAll("");

		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");


		assertThat(orgValue).isNotEqualTo(value);
		log.debug(value);

	}


	@Test
	public void request() {

		long a = 0;
		String ip = "";

		try {
			ip = saleson.common.utils.CommonUtils.getClientIp();
		} catch (IllegalStateException e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}

		System.out.println(ip);
	}

	@Test
	public void steramTest() {


		String[] iarr = new String[]{"1", "2", "3"};


		long[] larr = Arrays.stream(iarr)
				.mapToLong(i -> Long.parseLong(i))
				.toArray();
		System.out.println(Arrays.toString(larr));
	}

	@Test
	public void UrlEncoding() {
		String encode = null;
		try {
			encode = URLEncoder.encode("한글- asdfasdf", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}

		System.out.println(encode);
	}

	@Test
	public void origins() {
		String allowedOrigins = "http://www.anver.com, https://sasdfa.com";
		String[] origins = new String[] {"*"};

		if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
			if (allowedOrigins.indexOf(",") > -1) {
				origins = StringUtils.delimitedListToStringArray(allowedOrigins, ",");
				origins = Arrays.stream(origins)
						.map(s -> s.trim()).toArray(String[]::new);
			} else {
				origins = new String[] {allowedOrigins};
			}
		}

		for (int i = 0; i < origins.length; i++) {

			System.out.println(origins[i]);
		}
	}



	@Test
	void uniqNumberTest() {
		String uniq = "K10000016260007700".substring(14, 16);
		assertThat(uniq).isEqualTo("77");
	}
}
