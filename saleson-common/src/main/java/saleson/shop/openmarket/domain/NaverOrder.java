package saleson.shop.openmarket.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class NaverOrder  {
	private static final boolean TRUE = true;
	private static final String ENCODING = "UTF-8";
	private static final String NCKEY_ITEMID = "ITEM_ID";
	private static final String NCKEY_ITEMNAME = "ITEM_NAME";
	private static final String NCKEY_COUNT = "ITEM_COUNT";
	private static final String NCKEY_TPRICE = "ITEM_TPRICE";
	private static final String NCKEY_UPRICE = "ITEM_UPRICE";
	private static final String NCKEY_ITEMIMAGE = "ITEM_IMAGE";
	private static final String NCKEY_ITEMTHUMB = "ITEM_THUMB"; 
	private static final String NCKEY_ITEMURL = "ITEM_URL";
	private static final String NCKEY_OPTION = "ITEM_OPTION";
	private static final String NCKEY_SHIPPINGTYPE = "SHIPPING_TYPE";
	private static final String NCKEY_SHIPPINGPRICE = "SHIPPING_PRICE";
	private static final String NCKEY_TOTALPRICE = "TOTAL_PRICE";
	private static final String NCKEY_SHOPID = "SHOP_ID";
	private static final String NCKEY_CERTIKEY = "CERTI_KEY";
	private static final String NCKEY_BACKURL = "BACK_URL";
	private static final String NCKEY_RESERVE1 = "RESERVE1";
	private static final String NCKEY_RESERVE2 = "RESERVE2";
	private static final String NCKEY_RESERVE3 = "RESERVE3";
	private static final String NCKEY_RESERVE4 = "RESERVE4";
	private static final String NCKEY_RESERVE5 = "RESERVE5";
	private static final String NCKEY_SA_CLICK_ID = "SA_CLICK_ID"; // CTS
	private static final String NCKEY_CPA_INFLOW_CODE = "CPA_INFLOW_CODE";
	private static final String NCKEY_NAVER_INFLOW_CODE = "NAVER_INFLOW_CODE";
    private static final String NCKEY_EC_MALL_PID = "EC_MALL_PID";

	private URL _url;
	private SSLSocketFactory _sslSockFactory;


	public NaverOrder() {
		_url = null;
	}

	public NaverOrder(URL url) {
		_url = url;
		_initHttps();
	}

	public NaverOrder(String url) throws MalformedURLException {
		this(new URL(url));
	}

	public void setUrl(String url) throws MalformedURLException {
		_url = new URL(url);
	}
	/*
	 * public OrderController() { _url = null; }
	 * 
	 * 
	 * public OrderController(URL url) throws MalformedURLException { //url =
	 * new URL("https://test-pay.naver.com/customer/api/order.nhn"); _url = url;
	 * _initHttps(); }
	 * 
	 * public OrderController(String url) throws MalformedURLException {
	 * this(new URL(url)); }
	 * 
	 * public void setUrl(String url) throws MalformedURLException { _url = new
	 * URL(url); }
	 */

	private void _urlEncode(StringBuffer sb, String key, String value) {
		try {
			sb.append(URLEncoder.encode(key, ENCODING));
			sb.append('=');
			sb.append(URLEncoder.encode(value, ENCODING));
		} catch (UnsupportedEncodingException e) {
			// 일어나지 않음
			throw new Error(e);
		}
	}

	private String _makeQueryString(String shopId, String certificationKey, ItemStack[] items, int shippingPrice,
			String shippingType, String backURL, String saClickId) {
		// 주문 금액 = 각 상품 금액 + 배송비(단 선불일 경우)
		int totalPrice = shippingPrice > 0 ? shippingPrice : 0;
		StringBuffer sb = new StringBuffer();
		_urlEncode(sb, NCKEY_SHOPID, shopId);
		sb.append('&');
		_urlEncode(sb, NCKEY_CERTIKEY, certificationKey);
		sb.append('&');
		for (ItemStack is : items) {
			totalPrice += is.getItemTotalPrice();
			_urlEncode(sb, NCKEY_ITEMID, is.getItemId());
			sb.append('&');
			_urlEncode(sb, NCKEY_ITEMNAME, is.getItemName());
			sb.append('&');
			_urlEncode(sb, NCKEY_TPRICE, String.valueOf(is.getItemTotalPrice()));
			sb.append('&');
			_urlEncode(sb, NCKEY_UPRICE, String.valueOf(is.getItemUnitPrice()));
			sb.append('&');
			_urlEncode(sb, NCKEY_COUNT, String.valueOf(is.getCount()));
			sb.append('&');
			_urlEncode(sb, NCKEY_OPTION, is.getSelectedOption());
			sb.append('&');
			_urlEncode(sb, NCKEY_ITEMURL, String.valueOf(is.getItemUrl()));
            sb.append('&');
            _urlEncode(sb, NCKEY_ITEMIMAGE, String.valueOf(is.getItemImage()));
            sb.append('&');
            _urlEncode(sb, NCKEY_ITEMTHUMB, String.valueOf(is.getItemImage()));
			sb.append('&');
            _urlEncode(sb, NCKEY_EC_MALL_PID, is.getEcMallId()); // 네이버쇼핑EP의 MA_PID 네이버쇼핑 가맹점이라면 EP의 MA_PID와 동일한 값을 입력해야 한다.
            sb.append('&');
        }
		_urlEncode(sb, NCKEY_SHIPPINGTYPE, shippingType);
		sb.append('&');
		_urlEncode(sb, NCKEY_SHIPPINGPRICE, String.valueOf(shippingPrice));
		sb.append('&');
		_urlEncode(sb, NCKEY_TOTALPRICE, String.valueOf(totalPrice));
		sb.append('&');
		_urlEncode(sb, NCKEY_BACKURL, backURL);
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE1, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE2, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE3, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE4, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE5, "");
        sb.append('&'); // CTS
        _urlEncode(sb, NCKEY_SA_CLICK_ID, saClickId); // CTS
		// CPA 스크립트 가이드 설치업체는 해당 값 전달
		/*
		 * sb.append('&'); _urlEncode(sb, NCKEY_CPA_INFLOW_CODE, cpaInflowCode);
		 * sb.append('&'); _urlEncode(sb, NCKEY_NAVER_INFLOW_CODE,
		 * naverInflowCode);
		 */
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	private String _makeQueryString(String shopId, String certificationKey, ItemStack[] items) {
		StringBuffer sb = new StringBuffer();
		_urlEncode(sb, NCKEY_SHOPID, shopId);
		sb.append('&');
		_urlEncode(sb, NCKEY_CERTIKEY, certificationKey);
		sb.append('&');
		for (ItemStack is : items) {
			_urlEncode(sb, NCKEY_ITEMID, is.getItemId());
			sb.append('&');
			_urlEncode(sb, NCKEY_ITEMNAME, is.getItemName());
			sb.append('&');
			_urlEncode(sb, NCKEY_UPRICE, String.valueOf(is.getItemUnitPrice()));
			sb.append('&');
			_urlEncode(sb, NCKEY_ITEMIMAGE, String.valueOf(is.getItemImage()));
			sb.append('&');
			_urlEncode(sb, NCKEY_ITEMTHUMB, String.valueOf(is.getItemThumb()));
			sb.append('&');
            _urlEncode(sb, NCKEY_ITEMURL, String.valueOf(is.getItemUrl()));
            sb.append('&');
            _urlEncode(sb, NCKEY_EC_MALL_PID, String.valueOf(is.getEcMallId()));
            sb.append('&');
		}
		_urlEncode(sb, NCKEY_RESERVE1, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE2, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE3, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE4, "");
		sb.append('&');
		_urlEncode(sb, NCKEY_RESERVE5, "");
		System.out.println(sb.toString());
		return sb.toString();
	}

	/* test 환경에서는 인증서 오류가 날 수도 있다. 이 코드를 이용해 인증서 오류를 회피한다. */
	private void _initHttps() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				if (!TRUE) {
					throw new CertificateException();
				}
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				if (!TRUE) {
					throw new CertificateException();
				}
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		} };
		try {
			// SSL -> TLSv1.2 by SonarQube
			SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
			sslContext.init(null, trustAllCerts, new SecureRandom());
			_sslSockFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			RuntimeException re = new RuntimeException(e);
			re.setStackTrace(e.getStackTrace());
			throw re;
		}
	}


	public String sendOrderInfoToNC(String shopId, String certificationKey, ItemStack[] items, int shippingPrice,
			String shippingType, String backURL, String nvadId, String url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
		/* test 환경에서는 인증서 오류가 날 수도 있다. 이 코드를 이용해 인증서 오류를 회피한다. */
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection) conn).setSSLSocketFactory(_sslSockFactory);
			((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return TRUE;
				}
			});
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Writer writer = new OutputStreamWriter(conn.getOutputStream(), ENCODING);
		String resultUrl = _makeQueryString(shopId, certificationKey, items, shippingPrice, shippingType, backURL, nvadId);
		writer.write(resultUrl);
		writer.flush();
		writer.close();
		int respCode = conn.getResponseCode();
		if (respCode != 200) {
			throw new RuntimeException(String.format("NC Response fail : %d %s", respCode, conn.getResponseMessage()));
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String orderKey = reader.readLine();
		return orderKey;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		if (name == null || request == null) {
			return "";
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
		}
		return "";
	}
	
	/**      
	 * @param items 주문 상품 목록.
	 * @return 주문키 
	 * @throws IOException
	 */     
	public String[] sendZzimToNC(String shopId, String certificationKey, ItemStack[] items) throws IOException     {
		for (ItemStack test : items) {
			System.out.println("test::" + test.getItemName());
		}
		
		HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
		/* test 환경에서는 인증서 오류가 날 수도 있다. 이 코드를 이용해 인증서 오류를 회피한다. */
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection)conn).setSSLSocketFactory(_sslSockFactory);
			((HttpsURLConnection)conn).setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return TRUE;
				}
			});
		}         
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Writer writer = new OutputStreamWriter(conn.getOutputStream(), ENCODING);
		writer.write(_makeQueryString(shopId, certificationKey, items));
		writer.flush();
		writer.close();
		int respCode = conn.getResponseCode();
		if (respCode != 200) {
			throw new RuntimeException(String.format("NC Response fail : %d %s", respCode, conn.getResponseMessage()));
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String retStr = reader.readLine();
		System.out.println("retStr:: " + retStr);

		if (retStr == null) {
			return new String[0];
		}
		return retStr.split(",");
	}
}
