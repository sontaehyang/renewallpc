package saleson.common.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.onlinepowers.framework.security.userdetails.User;
import saleson.common.utils.UserUtils;
import saleson.seller.main.domain.Seller;
import saleson.seller.menu.SellerMenu;

public class SellerContext {
	private boolean login;
	private HttpServletRequest request;
	private SellerMenu sellerMenu;
	private boolean isShadowlogin;
	private boolean sellerUserLogin;
	private boolean sellerMasterUserLogin;
	private User sellerUser;
	
	public SellerContext(HttpServletRequest request) {
		this.request = request;
	}
	
	public boolean isLogin() {
		return login;
	}

	
	public Seller getSeller() {
		HttpSession session = request.getSession();

		Seller seller;

		if(isSellerUserLogin()) {
			seller = UserUtils.getSeller();
		} else if(session.getAttribute("SELLER") != null){
			seller = (Seller)session.getAttribute("SELLER");
		}else{
			seller = (Seller)session.getAttribute("SHADOW_SELLER");
		}
        return seller;
	}

	public SellerMenu getSellerMenu() {
		return sellerMenu;
	}

	public void setSellerMenu(SellerMenu sellerMenu) {
		this.sellerMenu = sellerMenu;
	}

	public boolean getIsShadowlogin() {
		return isShadowlogin;
	}

	public void setShadowlogin(boolean isShadowlogin) {
		this.isShadowlogin = isShadowlogin;
	}

	public boolean isSellerUserLogin() {
		return sellerUserLogin;
	}

	public void setSellerUserLogin(boolean sellerUserLogin) {
		this.sellerUserLogin = sellerUserLogin;
	}

	public User getSellerUser() {
		return sellerUser;
	}

	public void setSellerUser(User sellerUser) {
		this.sellerUser = sellerUser;
	}

	public boolean isSellerMasterUserLogin() {
		return sellerMasterUserLogin;
	}

	public void setSellerMasterUserLogin(boolean sellerMasterUserLogin) {
		this.sellerMasterUserLogin = sellerMasterUserLogin;
	}
}
