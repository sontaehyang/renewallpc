package saleson.common.utils;


import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;

public class CommonUtils {
	private CommonUtils() {
	}

	/**
	 * 배열을 복사한다.(int[])
	 * @param original
	 * @return
	 */
	public static int[] copy(int[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * 배열을 복사한다.(long[])
	 * @param original
	 * @return
	 */
	public static long[] copy(long[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * 배열을 복사한다.(double[])
	 * @param original
	 * @return
	 */
	public static double[] copy(double[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * 배열을 복사한다. (String[])
	 * @param original
	 * @return
	 */
	public static String[] copy(String[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}
	
	
	/**
	 * 배열을 복사한다. (MultipartFile[])
	 * @param original
	 * @return
	 */
	public static MultipartFile[] copy(MultipartFile[] original) {
		// MultipartFile을 clone하는 방법은?
		
		return original;
	}


	/**
	 * Jpa Page 객체를 이용하여 리스트를 출력하는 경우 순번을 표시함.
	 * @param page
	 * @param index
	 * @return
	 */
	public static Integer numbering(Page page, Integer index) {
		return (int) page.getTotalElements() - (page.getNumber() * page.getSize()) - index;
	}


	public static void setMessage(String message) {
		RequestContextUtils.setMessage(message);
		FlashMapUtils.setMessage(message);
	}

	public static void setErrorMessage(Errors errors) {
		String message = errors.getFieldError().getDefaultMessage() + " (" + errors.getFieldError().getField() + ")";
		setMessage(message);
	}


	/**
	 * Client IP 조회
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		if (request == null) {
			return "";
		}

		String ip = request.getHeader("X-Forwarded-For");

		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * Client IP 조회
	 * @return
	 */
	public static String getClientIp() {
		HttpServletRequest request = null;
		try {
			request = RequestContextUtils.getRequestContext().getRequest();
			return getClientIp(request);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 문자열 변수 NULL 처리
	 * @param object
	 * @return
	 */
	public static String dataNvl(Object object){
		return object == null ? "" : object.toString();
	}

	/**
	 * 문자열 배열 NULL 처리
	 * @param param
	 * @return
	 */
	public static String[] dataAryNvl(String[] param){
		String[] tt = {};
		if(param == null){
			param = tt;
		}
		return param;
	}

	/**
	 * 정수형 변수 Empty 처리
	 * @param object
	 * @return
	 */
	public static Integer intNvl(Object object){
		return object == null ? 0 : (Integer)object;
	}

	/**
	 * 정수형 배열 Empty 처리
	 * @param param
	 * @return
	 */
	public static Integer[] intAryNvl(Integer[] param){
		Integer[] tt = {};
		if(param == null){
			param = tt;
		}
		return param;
	}

	/**
	 * 정수형 변수 Empty 처리
	 * @param object
	 * @return
	 */
	public static Long longNvl(Object object){
		return object == null ? 0 : (Long)object;
	}

	public static BigDecimal bigDecimalNvl(Object object) {
		return object == null ? BigDecimal.ZERO : (BigDecimal)object;
	}
}
