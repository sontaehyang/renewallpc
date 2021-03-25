package saleson.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import saleson.common.enumeration.AuthorityType;
import saleson.seller.main.domain.Seller;
import saleson.shop.user.domain.GuestUser;
import saleson.shop.user.domain.UserDetail;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.CodeUtils;
import com.onlinepowers.framework.util.SecurityUtils;

public class UserUtils {

	private UserUtils() {}

	/**
	 * 회원(고객) 로그인 여부
	 * @return
	 */
	public static boolean isUserLogin() {
		return SecurityUtils.hasRole("ROLE_USER");
	}
	
	
	public static boolean isGuestLogin() {
		try {
			HttpSession session = RequestContextUtils.getSession();

			if(session.getAttribute("guestDomain") != null) {
				return true;
			}

			HttpServletRequest request = RequestContextUtils.getRequestContext().getRequest();
			if (request != null) {
				GuestUser guestUser = JwtUtils.getGuestUser(request);

				if (guestUser != null) {
					return true;
				}

			}

		} catch (Exception e) {
			return false;
		}

		return false;
	}
	
	/**
	 * 시스템관리자 여부 (ROLE_SUPERVISOR)
	 * @return
	 */
	public static boolean isSupervisor() {
		return SecurityUtils.isSupervisor();
	}
	
	/**
	 * 관리자 로그인 여부 (ROLE_OPMANAGER)
	 * @return
	 */
	public static boolean isManagerLogin() {
		if (SecurityUtils.hasRole("ROLE_OPMANAGER")) {
			return true;
		}
		return false;
	}
	
	/**
	 * MD 여부 (ROLE_MD) 
	 * @author minae.yun
	 * @return
	 */
	public static boolean isMd() {
		if (SecurityUtils.hasRole("ROLE_MD")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 로그인 회원 정보 
	 * @return
	 */
	public static User getUser() {
		return SecurityUtils.getCurrentUser();
	}
	
	/**
	 * 로그인 회원 USER_ID
	 * @return
	 */
	public static long getUserId() {
		if (getUser() == null) {
			return 0;
		}
		
		if (!isUserLogin()) {
			return 0;
		}
		
		return getUser().getUserId();
	}
	
	/**
	 * 로그인 관리자 USER_ID
	 * 판매자 페이지이고 판매자 로그인 된 경우는 판매자 아이디를 리턴.
	 * @return
	 */
	public static long getManagerId() {
		if (ShopUtils.isSellerPage() && SellerUtils.isSellerLogin()) {
			return SellerUtils.getSellerId();
		}
		if (getUser() == null) {
			return 0;
		}
				
		return getUser().getUserId();
	}
	
	/**
	 * 로그인 관리자 USER_ID
	 * 판매자 페이지이고 판매자 로그인 된 경우는 판매자 아이디를 리턴.
	 * @return
	 */
	public static String getManagerName() {
		if (ShopUtils.isSellerPage() && SellerUtils.isSellerLogin()) {
			return SellerUtils.getSeller().getSellerName();
		}
		
		if (getUser() == null) {
			return "";
		}
				
		return getUser().getUserName();
	}
	
	/**
	 * 회윈의 이메일 정보를 가져온다.
	 * @return
	 */
	public static String getEmail() {
		if (getUser() == null) {
			return "";
		}
		return getUser().getEmail();
	}
	
	
	/**
	 * 로그인 회원의 LOGIN_ID
	 * @return
	 */
	public static String getLoginId() {
		return getUser().getLoginId();
	}
	
	
	
	
	/**
	 * 회원 상세 정보
	 * @return
	 */
	public static UserDetail getUserDetail() {
		if (getUser() != null && getUser().getUserDetail() != null) {
			return (UserDetail) getUser().getUserDetail();
		} else {
			return new UserDetail();
		}
	}
	
	
	public static void setGuestLogin(String username, String phoneNumber) {
		
		User user = new User();
		UserDetail userDetail = new UserDetail();
		
		user.setUserName(username);
		userDetail.setPhoneNumber(phoneNumber);
		
		user.setUserDetail(userDetail);
		
		HttpSession session = RequestContextUtils.getSession();
		session.setAttribute("guestDomain", user);

	}
	
	public static User getGuestLogin() {
		HttpSession session = RequestContextUtils.getSession();
		
		if (session != null && session.getAttribute("guestDomain") != null) {
			return (User) session.getAttribute("guestDomain");
		}

		HttpServletRequest request = RequestContextUtils.getRequestContext().getRequest();
		if (request != null) {

			try {
				GuestUser guestUser = JwtUtils.getGuestUser(request);

				if (guestUser != null) {
					User user = new User();
					UserDetail userDetail = new UserDetail();

					user.setUserName(guestUser.getUserName());
					userDetail.setPhoneNumber(guestUser.getPhoneNumber());

					user.setUserDetail(userDetail);

					return user;
				}


			} catch (Exception ignore) {}

		}

		return null;
	}
	
	
	/**
	 * 전용구분 상품 조회용 코드 (상품 목록 조회 시 검색 조건으로 포함)
	 * 
	 * OP_COMMON_CODE : ITEM_PRIVATE_TYPE 항목
	 * 
	 * 업체별 설정에 따라 변경이 필요함.
	 * @return
	 */
	public static List<String> getPrivateTypes() {
		List<String> privateTypes = new ArrayList<>();
		
		privateTypes.add("00");		// 00:일반상품
		
		
		List<Code> itemPrivateTypes = CodeUtils.getCodeList("ITEM_PRIVATE_TYPE");
		
		for (Code code : itemPrivateTypes) {
			
			if (code.getDetail() == null) {
				continue;
			}
			
			// 1. 회원 ROLE 체크 
			List<String> userRoles =SecurityUtils.getAuthorities();
			
			if (userRoles != null) {
				for (String role : userRoles) {
					if (code.getDetail().indexOf(role) > -1) {
						privateTypes.add(code.getValue());
					}
				}
			}
			
			// 2. 회원 그룹 체크 
			if (getUserDetail() != null 
					&& getUserDetail().getGroupCode() != null
					&& code.getDetail().indexOf(getUserDetail().getGroupCode()) > -1) {
				privateTypes.add(code.getValue());
			}
		}
		
		return privateTypes;
	}
	
	/**
	 * 휴면 계정인가?
	 * ROLE_USER, STATUS_CODE = 4
	 * @return
	 */
	public static boolean isDormantUser() {
		// ROLE_USER, STATUS_CODE = 4
		
		if (!UserUtils.isUserLogin()) {
			return false;
		}
		
	
		User currentUser = UserUtils.getUser();
		if ("4".equals(currentUser.getStatusCode())) {
    		return true;
    	}
		return false;
	}

	public static String masking(String str, String kind) {
		String returnValue = "";
		switch (kind) {
			case "name":
				// 한글명이면 2성은 성 다음 한자리 *
				// 1성이면 가운데 자리 *
				if(charCheck(str) == 5) {
					if(str.length() > 2) {
						if("|강전|고전|길강|길성|독고|남궁|동방|망절|사공|서문|선우|소봉|어금|장곡|제갈|황보|"
								.indexOf(str.substring(0, 2)) > -1) {
							returnValue = str.substring(0, 2) + "*" + str.substring(3, str.length());
						} else {
							returnValue = str.substring(0, 1) + "*" + str.substring(2, str.length());
						}
					} else {
						returnValue = str.substring(0, 1) + "*";
					}

					// 영문명이면 전체사이즈의 1/3 만큼 마스킹 처리
				} else {
					int str_length = str.length() / 3 * 2 ;
					returnValue =  str.substring(0,str_length) + "***";
				}
				break;
			case "tel":
				String[] arr = str.split("-");
				if(arr.length > 2) {
					returnValue = arr[0] + "-****-" + arr[2];
				}
				break;
			case "day":
				arr = str.split("-");
				if(arr.length > 2) {
					returnValue = "****-**-**";
				}
				break;
			default :
		}

		return returnValue;
	}

	public static int charCheck(String chr){
		char char_ASCII = chr.charAt(0);
		//alert(char_ASCII);

		//공백
		if (char_ASCII == 32)
			return 0;
			//숫자
		else if (char_ASCII >= 48 && char_ASCII <= 57 )
			return 1;
			//영어(대문자)
		else if (char_ASCII>=65 && char_ASCII<=90)
			return 2;
			//영어(소문자)
		else if (char_ASCII>=97 && char_ASCII<=122)
			return 3;
			//특수기호
		else if ((char_ASCII>=33 && char_ASCII<=47)
				|| (char_ASCII>=58 && char_ASCII<=64)
				|| (char_ASCII>=91 && char_ASCII<=96)
				|| (char_ASCII>=123 && char_ASCII<=126))
			return 4;
			//한글
		else if ((char_ASCII >= 12592) || (char_ASCII <= 12687))
			return 5;
		else
			return 9;
	}

	public static String reMasking(String str, String kind){
		String result = str;
		Matcher matcher = null;
		StringBuffer resultStr = new StringBuffer();
		switch (kind) {
			case "id":

			case "name":
				String pattern = "";
				if(str.length() == 2) {
					pattern = "^(.)(.+)$";
				} else {
					pattern = "^(.)(.+)(.)$";
				}
				matcher = Pattern.compile(pattern).matcher(str);

				if(matcher.matches()) {
					result = "";

					for(int i=1;i<=matcher.groupCount();i++) {
						String replaceTarget = matcher.group(i);
						if(i == 2) {
							char[] c = new char[replaceTarget.length()];
							Arrays.fill(c, '*');

							result = result + String.valueOf(c);
						} else {
							result = result + replaceTarget;
						}
					}
				}
				break;
			case "account":
				if(str.split("-").length > 2){
					char[] acc = new char[str.split("-")[1].length()];
					Arrays.fill(acc, '*');
					resultStr.append(str.split("-")[0]);
					resultStr.append("-");
					resultStr.append(String.valueOf(acc));
					resultStr.append("-");
					resultStr.append(str.split("-")[2]);
					result = resultStr.toString();
				}
				break;
			case "phone" :
			case "tel":
				matcher = Pattern.compile("^(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$").matcher(str);
				if(matcher.matches()) {
					result = "";
					boolean isHyphen = false;
					if(str.indexOf("-") > -1) {
						isHyphen = true;
					}
					for(int i=1;i<=matcher.groupCount();i++) {
						String replaceTarget = matcher.group(i);
						if(i == 2) {
							char[] c = new char[replaceTarget.length()];
							Arrays.fill(c, '*');
							result = result + String.valueOf(c);
						} else {
							result = result + replaceTarget;
						}
						if(isHyphen && i < matcher.groupCount()) {
							result = result + "-";
						}
					}
				}
				break;
			case "addr":
				String[] ary = result.split(" ");
				StringBuffer resultBf = new StringBuffer();
				char[] adc = null;
				for(int i = 0; i < ary.length; i ++){
					if(i == (ary.length-1)){
						adc = new char[ary[i].split("").length-1];
						Arrays.fill(adc, '*');
						resultBf.append(adc);
					} else {
						resultBf.append(ary[i].toString()+" ");
					}
				}
				result = resultBf.toString();
				break;
			case "addrDetail":
				String aryDetail[] =  result.split(" ");
				StringBuffer bufferDetail = new StringBuffer();
				char[] addc = null;
				for(int i = 0; i < aryDetail.length; i ++){
					addc = new char[aryDetail[i].split("").length-1];
					Arrays.fill(addc, '*');
					bufferDetail.append(String.valueOf(addc)+" ");
				}
				result = bufferDetail.toString();
				break;
			case "zipCode":
				if(result.split("-").length > 1){
					char[] zc = new char[result.split("-")[1].length()];
					Arrays.fill(zc, '*');
					resultStr.append(result.split("-")[0]);
					resultStr.append("-");
					resultStr.append(String.valueOf(zc));
				}
				result = resultStr.toString();
				break;
			case "newZipCode":
				char[] zc = new char[result.length()];
				Arrays.fill(zc, '*');
				resultStr.append(String.valueOf(zc));
				result = resultStr.toString();
				break;
			case "email":
				matcher = Pattern.compile("^(..)(.*)([@]{1})(.*)$").matcher(str);

				if(matcher.matches()) {
					result = "";

					for(int i=1;i<=matcher.groupCount();i++) {
						String replaceTarget = matcher.group(i);
						if(i == 2) {
							char[] c = new char[replaceTarget.length()];
							Arrays.fill(c, '*');

							result = result + String.valueOf(c);
						} else {
							result = result + replaceTarget;
						}
					}
				}
				break;
			case "ip":
				matcher = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$").matcher(str);

				if(matcher.matches()) {
					result = "";

					for(int i=1;i<=matcher.groupCount();i++) {
						String replaceTarget = matcher.group(i);
						if(i == 3) {
							char[] c = new char[replaceTarget.length()];
							Arrays.fill(c, '*');

							result = result + String.valueOf(c);
						} else {
							result = result + replaceTarget;
						}
						if(i < matcher.groupCount()) {
							result =result + ".";
						}
					}
				}
				break;
			default :
		}

		return result;
	}

	public static boolean isSellerLogin() {
		return SecurityUtils.hasRole("ROLE_SELLER");
	}

	public static long getSellerUserId() {

		if (getUser() == null) {
			return 0;
		}

		if (!UserUtils.isSellerLogin()) {
			return 0;
		}

		return getUser().getUserId();
	}

	public static Seller getSeller() {

		if (UserUtils.isSellerLogin() && (getUser() != null && getUser().getObject() != null)) {
			return (Seller)UserUtils.getUser().getObject();
		}

		return null;
	}

	/**
	 * 판매관리자 마스터 운영자 여부
	 * @return
	 */
	public static boolean isSellerMasterUser() {

		return UserUtils.isSellerLogin()
				&& SecurityUtils.hasRole(AuthorityType.SELLER_MASTER.getCode());
	}

}
