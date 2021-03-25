package saleson.common.utils;

import com.onlinepowers.framework.util.AgentUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AgentUtil extends AgentUtils {
	private static String APP_USER_AGENT_IOS;
	private static String APP_USER_AGENT_ANDROID;

	private AgentUtil() {
	}

	@Value("${app.user.agent.ios}")
	public void setAppUserAgentIos(String appUserAgentIos) {
		APP_USER_AGENT_IOS = appUserAgentIos;
	}

	@Value("${app.user.agent.android}")
	public void setAppUserAgentAndroid(String appUserAgentAndroid) {
		APP_USER_AGENT_ANDROID = appUserAgentAndroid;
	}


	public static String getBrowser(String agent) {
		if (agent == null) {
			return "기타";
		}
		agent = agent.toLowerCase();
		String os = getOS(agent);
		String browser = "";

		if (agent.startsWith("mozilla") && agent.indexOf("like gecko") > -1 && agent.indexOf("edge") > -1) {
	    	browser = "MS Edge"; 
	    }
	    else if (agent.indexOf("rv:11.0") > -1 || agent.indexOf("trident/7.0") > -1) {
	    	browser = "IE 11"; 
	    }
	    else if (agent.indexOf("trident/6.0") > -1) { 
	    	browser = "IE 10"; 
	    }
	    else if (agent.indexOf("trident/5.0") > -1) { 
	    	browser = "IE 9"; 
	    }
	    else if (agent.indexOf("trident/4.0") > -1) {
	    	browser = "IE 8"; 
	    }

	    else if (agent.indexOf("msie 6.") > -1)		{ browser = "IE 6"; }
	    else if (agent.indexOf("msie 5.5") > -1)	{ browser = "IE 5.5"; }
	    else if (agent.indexOf("msie 5.") > -1)		{ browser = "IE 5"; }
	    else if (agent.indexOf("msie 7.") > -1)		{ browser = "IE 7"; }
	    else if (agent.indexOf("msie 8.") > -1)		{ browser = "IE 8"; }
	    else if (agent.indexOf("msie 9.") > -1)		{ browser = "IE 9"; }
	    else if (agent.indexOf("msie 10.") > -1)    { browser = "IE 10"; }
	    else if (agent.indexOf("msie 4.") > -1)     { browser = "IE 4.x"; }
	    
	    else if ("iOS".equals(os)) { 
	    	browser = "Safari Mobile";
	    }
	    else if (agent.indexOf("firefox") > -1 && agent.indexOf("seamonkey") == -1) {
	    	browser = "Firefox"; 
	    	
	    }
	    else if (agent.indexOf("safari") > -1 && !(agent.indexOf("chrome") > -1 || agent.indexOf("chromium") > -1)) { 
	    	browser = "Safari"; 
	    
	    }
	    else if (agent.indexOf("chrome") > -1 && agent.indexOf("chromium") == -1) { 
	    	browser = "Chrome"; 
	    
	    }
	    else if (agent.indexOf("opera") > -1) {
	    	browser = "Opera"; 
	    }
	    
	    else if (agent.indexOf("x11") > -1)                { browser = "Netscape"; }
	    else if (agent.indexOf("gec") > -1)                { browser = "Gecko"; }
	    else if (agent.indexOf("bot|slurp") > -1)          { browser = "Robot"; }
	    else if (agent.indexOf("internet explorer") > -1)  { browser = "IE"; }
	    else if (agent.indexOf("mozilla") > -1)            { browser = "Mozilla"; }
	    else { browser = "기타"; }
	
	    return browser;
	}
	
	public static String getOS(String agent) {
		if (agent == null) {
			return "기타";
		}
	    agent = agent.toLowerCase();
	    String os = "";

	    if (agent.indexOf("windows 98") > -1)                 { os = "Windows 98"; }
	    else if (agent.indexOf("windows 95") > -1)            { os = "Windows 95"; }
	    else if(agent.indexOf("windows nt 4") > -1)   		  { os = "Windows NT"; }
	    else if(agent.indexOf("windows nt 5.0") > -1)         { os = "Windows 2000"; }
	    else if(agent.indexOf("windows nt 5.1") > -1)         { os = "Windows XP"; }
	    else if(agent.indexOf("windows nt 5.2") > -1)         { os = "Windows XP x64"; }
	    else if(agent.indexOf("windows nt 6.0") > -1)         { os = "Windows Vista"; }
	    else if(agent.indexOf("windows nt 6.1") > -1)         { os = "Windows 7"; }
	    else if(agent.indexOf("windows nt 6.2") > -1)         { os = "Windows 8"; }
	    else if(agent.indexOf("windows nt 6.3") > -1)         { os = "Windows 8.1"; }
	    else if(agent.indexOf("windows nt 10") > -1)          { os = "Windows 10"; }
	    else if(agent.indexOf("windows 9x") > -1)             { os = "Windows ME"; }
	    else if(agent.indexOf("windows ce") > -1)             { os = "Windows CE"; }
	    else if(agent.indexOf("macintosh") > -1)              { os = "Mac OS"; }
	    else if(agent.indexOf("iphone") > -1)                 { os = "iOS"; }
	    else if(agent.indexOf("android") > -1)                { os = "Android"; }
	    else if(agent.indexOf("mac") > -1)                    { os = "Mac OS"; }
	    else if(agent.indexOf("linux") > -1)                  { os = "Linux"; }
	    else if(agent.indexOf("sunos") > -1)                  { os = "sunOS"; }
	    else if(agent.indexOf("irix") > -1)                   { os = "IRIX"; }
	    else if(agent.indexOf("phone") > -1)                  { os = "Phone"; }
	    else if(agent.indexOf("bot|slurp") > -1)              { os = "Robot"; }
	    else if(agent.indexOf("internet explorer") > -1)      { os = "IE"; }
	    else if(agent.indexOf("mozilla") > -1)                { os = "Mozilla"; }
	    else { os = "기타"; }
	
	    return os;
	}

	public static String getIosUserAgent() {
		return APP_USER_AGENT_IOS;
	}

	public static String getAndroidUserAgent() {
		return APP_USER_AGENT_ANDROID;
	}

	/**
	 * IOS 여부
	 * @param request
	 * @return
	 */
	public static boolean isIos (HttpServletRequest request) {

		if (request == null) {
			return false;
		}

		String userAgent = request.getHeader("User-Agent");

		String key = getIosUserAgent();

		if (StringUtils.isEmpty(key)) {
			return false;
		}

		return userAgent.contains(key);
	}

	/**
	 * ANDROID 여부
	 * @param request
	 * @return
	 */
	public static boolean isAndroid (HttpServletRequest request) {

		if (request == null) {
			return false;
		}

		String userAgent = request.getHeader("User-Agent");

		String key = getAndroidUserAgent();

		if (StringUtils.isEmpty(key)) {
			return false;
		}
		return userAgent.contains(key);
	}
}