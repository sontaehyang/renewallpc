package saleson.common.utils;

import javax.servlet.http.HttpSession;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.seller.main.domain.Seller;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.util.SecurityUtils;

public class SellerUtils {
	public static final long DEFAULT_OPMANAGER_SELLER_ID = 90000000L;

	private SellerUtils() {
	}

	/**
	 * 판매자 로그인 여부
	 */
	public static boolean isSellerLogin() {

		try {
			if (UserUtils.isSellerLogin()) {
				return true;
			} else {
				HttpSession session = RequestContextUtils.getSession();
				if (session == null || (session.getAttribute("SELLER") == null && session.getAttribute("SHADOW_SELLER") == null)) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	/**
	 * 판매자 로그인 정보 조회.
	 * @return
	 */
	public static Seller getSeller() {

		if (!isSellerLogin()) {
			return null;
		}

		if (UserUtils.isSellerLogin()) {
			return UserUtils.getSeller();
		}
		
		if (isShadowSellerLogin()) {
			return getShadowSeller();
		}
		
		HttpSession session = RequestContextUtils.getSession();
		return (Seller) session.getAttribute("SELLER");
	}
	
	
	/**
	 * 판매자 로그인 여부
	 */
	public static boolean isShadowSellerLogin() {
		HttpSession session = RequestContextUtils.getSession();
		
		if (session == null || session.getAttribute("SHADOW_SELLER") == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 판매자 로그인 정보 조회.
	 * @return
	 */
	public static Seller getShadowSeller() {
		if (!isShadowSellerLogin()) {
			return null;
		}
		
		HttpSession session = RequestContextUtils.getSession();
		if (session.getAttribute("SHADOW_SELLER") == null) {
			return null;
		}
		return (Seller) session.getAttribute("SHADOW_SELLER");
	}
	
	/**
	 * 판매자 ID
	 * @return
	 */
	public static long getSellerId() {

		if (ShopUtils.isOpmanagerPage() && SecurityUtils.hasRole("ROLE_OPMANAGER")) {
			return DEFAULT_OPMANAGER_SELLER_ID;
			
		} else if (ShopUtils.isSellerPage()
				&& SecurityUtils.hasRole("ROLE_OPMANAGER")
				&& isShadowSellerLogin()) {	// 관리자인데 쉐도우 Seller Login 한 경우

			Seller shadowSeller = getShadowSeller();
			if (shadowSeller == null) {
				return 0;
			} else {
				shadowSeller.getSellerId();
			}
		}
		
	 	Seller seller = getSeller();
		if (seller == null) {
			return 0;
		} else {
			return seller.getSellerId();
		}
	}
}
