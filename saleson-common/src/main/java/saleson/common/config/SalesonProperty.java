package saleson.common.config;

import com.onlinepowers.framework.util.PropertiesUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesonProperty extends PropertiesUtils {
	private static String uploadBaseFolder;
	private static String uploadSaveFolder;
	private static String salesonUrlShoppingmall;
	private static String salesonUrlBo;
	private static String salesonUrlApi;
	private static String salesonUrlFrontend;
	private static String salesonViewType;
	private static String configQueryLogIntercepter;
	private static String configDatabaseVendor;
	private static String pgService;
	private static String pgLgdacomMid;
	private static String pgLgdacomKey;
	private static String pgInipayMid;
	private static String pgInipayKeypass;
	private static String pgInipayMobileMid;
	private static String pgInipayMobileKeypass;
	private static String pgInipayEscrowMid;
	private static String pgInipayEscrowKeypass;
	private static String pgCjMid;
	private static String pgKspayMid;
	private static String pgKcpGConfSiteCd;
	private static String pgKcpGConfSiteKey;
	private static String pgEasypayGConfSiteCd;
	private static String kakaopayMid;
	private static String kakaopayEncodeKey;
	private static String paycoSellerCpId;
	private static String paycoSellerKey;
	private static String naverCheckoutPcPayurl;
	private static String naverCheckoutPcWishlist;
	private static String naverCheckoutPcButtonScript;
	private static String naverCheckoutMobilePayurl;
	private static String naverCheckoutMobileWishlist;
	private static String naverCheckoutMobileButtonScript;
	private static String naverCheckoutWishlistApiUrl;
	private static String naverCheckoutOrderApiUrl;
	private static String mailSender;



	@Value("${upload.base.folder}")
	public void setUploadBaseFolder(String value) {
		uploadBaseFolder = value;
	}
	@Value("${upload.save.folder}")
	public void setUploadSaveFolder(String value) {
		uploadSaveFolder = value;
	}
	@Value("${saleson.url.shoppingmall}")
	public void setSalesonUrlShoppingmall(String value) {
		salesonUrlShoppingmall = value;
	}
	@Value("${saleson.url.bo}")
	public void setSalesonUrlBo(String value) {
		salesonUrlBo = value;
	}
	@Value("${saleson.url.api}")
	public void setSalesonUrlApi(String value) {
		salesonUrlApi = value;
	}
	@Value("${saleson.url.frontend}")
	public void setSalesonUrlFrontend(String value) {
		salesonUrlFrontend = value;
	}
	@Value("${saleson.view.type}")
	public void setSalesonViewType(String value) {
		salesonViewType = value;
	}
	@Value("${config.query.log.intercepter}")
	public void setConfigQueryLogIntercepter(String value) {
		configQueryLogIntercepter = value;
	}
	@Value("${config.database.vendor}")
	public void setConfigDatabaseVendor(String value) {
		configDatabaseVendor = value;
	}

	@Value("${pg.service}")
	public void setPgService(String value) {
		pgService = value;
	}



	@Value("${pg.lgdacom.mid}")
	public void setPgLgdacomMid(String value) {
		pgLgdacomMid = value;
	}
	@Value("${pg.lgdacom.key}")
	public void setPgLgdacomKey(String value) {
		pgLgdacomKey = value;
	}

	@Value("${pg.inipay.escrow.mid}")
	public void setPgInipayEscrowMid(String value) {
		pgInipayEscrowMid = value;
	}
	@Value("${pg.inipay.escrow.keypass}")
	public void setPgInipayEscrowKeypass(String value) {
		pgInipayEscrowKeypass = value;
	}

	@Value("${pg.inipay.mobile.mid}")
	public void setPgInipayMobileMid(String value) {
		pgInipayMobileMid = value;
	}
	@Value("${pg.inipay.mobile.keypass}")
	public void setPgInipayMobileKeypass(String value) {
		pgInipayMobileKeypass = value;
	}

	@Value("${pg.inipay.mid}")
	public void setPgInipayMid(String value) {
		pgInipayMid = value;
	}
	@Value("${pg.inipay.keypass}")
	public void setPgInipayKeypass(String value) {
		pgInipayKeypass = value;
	}


	@Value("${pg.cj.mid}")
	public void setPgCjMid(String value) {
		pgCjMid = value;
	}
	@Value("${pg.kspay.mid}")
	public void setPgKspayMid(String value) {
		pgKspayMid = value;
	}
	@Value("${pg.kcp.g.conf.site.cd}")
	public void setPgKcpGConfSiteCd(String value) {
		pgKcpGConfSiteCd = value;
	}
	@Value("${pg.kcp.g.conf.site.key}")
	public void setPgKcpGConfSiteKey(String value) {
		pgKcpGConfSiteKey = value;
	}
	@Value("${pg.easypay.g.conf.site.cd}")
	public void setPgEasypayGConfSiteCd(String value) {
		pgEasypayGConfSiteCd = value;
	}
	@Value("${kakaopay.mid}")
	public void setKakaopayMid(String value) {
		kakaopayMid = value;
	}
	@Value("${kakaopay.encode.key}")
	public void setKakaopayEncodeKey(String value) {
		kakaopayEncodeKey = value;
	}

	@Value("${payco.seller.cpId}")
	public void setPaycoSellerCpId(String value) {
		paycoSellerCpId = value;
	}
	@Value("${payco.seller.key}")
	public void setPaycoSellerKey(String value) {
		paycoSellerKey = value;
	}

	@Value("${naver.checkout.pc.payUrl}")
	public void setNaverCheckoutPcPayurl(String value) {
		naverCheckoutPcPayurl = value;
	}
	@Value("${naver.checkout.pc.wishlist}")
	public void setNaverCheckoutPcWishlist(String value) {
		naverCheckoutPcWishlist = value;
	}
	@Value("${naver.checkout.pc.button.script}")
	public void setNaverCheckoutPcButtonScript(String value) {
		naverCheckoutPcButtonScript = value;
	}
	@Value("${naver.checkout.mobile.payUrl}")
	public void setNaverCheckoutMobilePayurl(String value) {
		naverCheckoutMobilePayurl = value;
	}
	@Value("${naver.checkout.mobile.wishlist}")
	public void setNaverCheckoutMobileWishlist(String value) {
		naverCheckoutMobileWishlist = value;
	}
	@Value("${naver.checkout.mobile.button.script}")
	public void setNaverCheckoutMobileButtonScript(String value) {
		naverCheckoutMobileButtonScript = value;
	}
	@Value("${naver.checkout.wishlist.api.url}")
	public void setNaverCheckoutWishlistApiUrl(String value) {
		naverCheckoutWishlistApiUrl = value;
	}
	@Value("${naver.checkout.order.api.url}")
	public void setNaverCheckoutOrderApiUrl(String value) {
		naverCheckoutOrderApiUrl = value;
	}

	@Value("${mail.sender}")
	public void setMailSender(String value) {
		mailSender = value;
	}

	public static String getUploadBaseFolder() {
		return uploadBaseFolder;
	}

	public static String getUploadSaveFolder() {
		return uploadSaveFolder;
	}

	public static String getSalesonUrlShoppingmall() {
		return salesonUrlShoppingmall;
	}

	public static String getSalesonUrlBo() {
		return salesonUrlBo;
	}

	public static String getSalesonUrlApi() {
		return salesonUrlApi;
	}

	public static String getSalesonUrlFrontend() {
		return salesonUrlFrontend;
	}

	public static String getSalesonViewType() {
		return salesonViewType;
	}

	public static String getConfigQueryLogIntercepter() {
		return configQueryLogIntercepter;
	}

	public static String getConfigDatabaseVendor() {
		return configDatabaseVendor;
	}

	public static String getPgService() {
		return pgService;
	}

	public static String getPgLgdacomMid() {
		return pgLgdacomMid;
	}

	public static String getPgLgdacomKey() {
		return pgLgdacomKey;
	}

	public static String getPgInipayMid() {
		return pgInipayMid;
	}

	public static String getPgInipayKeypass() {
		return pgInipayKeypass;
	}

	public static String getPgInipayMobileMid() {
		return pgInipayMobileMid;
	}

	public static String getPgInipayMobileKeypass() {
		return pgInipayMobileKeypass;
	}

	public static String getPgInipayEscrowMid() {
		return pgInipayEscrowMid;
	}

	public static String getPgInipayEscrowKeypass() {
		return pgInipayEscrowKeypass;
	}

	public static String getPgCjMid() {
		return pgCjMid;
	}

	public static String getPgKspayMid() {
		return pgKspayMid;
	}

	public static String getPgKcpGConfSiteCd() {
		return pgKcpGConfSiteCd;
	}

	public static String getPgKcpGConfSiteKey() {
		return pgKcpGConfSiteKey;
	}

	public static String getPgEasypayGConfSiteCd() {
		return pgEasypayGConfSiteCd;
	}

	public static String getKakaopayMid() {
		return kakaopayMid;
	}

	public static String getKakaopayEncodeKey() {
		return kakaopayEncodeKey;
	}

	public static String getPaycoSellerCpId() {
		return paycoSellerCpId;
	}

	public static String getPaycoSellerKey() {
		return paycoSellerKey;
	}

	public static String getNaverCheckoutPcPayurl() {
		return naverCheckoutPcPayurl;
	}

	public static String getNaverCheckoutPcWishlist() {
		return naverCheckoutPcWishlist;
	}

	public static String getNaverCheckoutPcButtonScript() {
		return naverCheckoutPcButtonScript;
	}

	public static String getNaverCheckoutMobilePayurl() {
		return naverCheckoutMobilePayurl;
	}

	public static String getNaverCheckoutMobileWishlist() {
		return naverCheckoutMobileWishlist;
	}

	public static String getNaverCheckoutMobileButtonScript() {
		return naverCheckoutMobileButtonScript;
	}

	public static String getNaverCheckoutWishlistApiUrl() {
		return naverCheckoutWishlistApiUrl;
	}

	public static String getNaverCheckoutOrderApiUrl() {
		return naverCheckoutOrderApiUrl;
	}

	public static String getMailSender() {
		return mailSender;
	}
}
