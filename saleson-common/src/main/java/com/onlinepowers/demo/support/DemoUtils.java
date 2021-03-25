package com.onlinepowers.demo.support;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;

public class DemoUtils {
	public static final String SESSION_DEMO_KEY = "DEMO_SESSION";
	public static final String DELIMITER = "||";


	public static String getDate(String detail) {
		String[] parse = StringUtils.delimitedListToStringArray(detail, DELIMITER);

		if (parse.length >= 1) {
			return parse[0];
		}
		return "";
	}

	public static String getName(String detail) {
		String[] parse = StringUtils.delimitedListToStringArray(detail, DELIMITER);

		if (parse.length >= 2) {
			return parse[1];
		}
		return "";
	}

	public static boolean isDemoLogin(HttpSession session) {
		return !StringUtils.isEmpty(getDemoUserName(session));
	}

	public static String getDemoUserName(HttpSession session) {
		if (session.getAttribute(DemoUtils.SESSION_DEMO_KEY) == null) {
			return "";
		}
		String authenticationCode = (String) session.getAttribute(DemoUtils.SESSION_DEMO_KEY);
		return getDemoUserName(authenticationCode);
	}

	public static String getDemoUserName(String authenticationCode) {
		String[] authCodes = new String[] {
				"박종진-pjj@onlinepowers.com",
				"배동혁-bdh@onlinepowers.com",
				"송영훈-syh@onlinepowers.com",
				"온파-onpa@onlinepowers.com",
		};

		for (String authCode : authCodes) {
			String[] authInfos = StringUtils.delimitedListToStringArray(authCode, "-");
			String name = authInfos[0];
			String password = authInfos[1];

			if (password.equals(authenticationCode)) {
				return name;
			}
		}

		return "";
	}

	public static boolean isDemoUser(String authenticationCode) {
		return !StringUtils.isEmpty(getDemoUserName(authenticationCode));
	}

}
