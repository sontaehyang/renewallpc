package saleson.seller.main;

import java.util.List;

import com.onlinepowers.framework.security.userdetails.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlinepowers.framework.sequence.service.SequenceService;

import saleson.seller.main.domain.Seller;
import saleson.seller.main.domain.SellerCategory;
import saleson.seller.main.support.SellerParam;
import saleson.seller.user.SellerUserService;
import saleson.shop.item.ItemMapper;
import saleson.shop.item.support.ItemParam;

@Service("sellerService")
public class SellerServiceImpl implements SellerService {

	private static final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

	@Autowired
	private SellerMapper sellerMapper;
	
	@Autowired
	private ItemMapper itemMapper;
	
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SellerUserService sellerUserService;

	@Override
	public Seller getSellerByLoginId(String loginId) {
		return sellerMapper.getSellerByLoginId(loginId);
	}

	@Override
	public Seller getSellerById(long sellerId) {
		return sellerMapper.getSellerById(sellerId);
	}

	@Override
	public List<Seller> getSellerListByParam(SellerParam sellerParam) {
		return sellerMapper.getSellerListByParam(sellerParam);
	}
	
	@Override
	public List<Seller> getAllSellerList() {
		return sellerMapper.getAllSellerList();
	}
	
	
	@Override
	public int getSellerCount(SellerParam sellerParam) {
		return sellerMapper.getSellerCount(sellerParam);
	}
	
	@Override
	public void insertSeller(Seller seller) {

		long sellerId = sequenceService.getLong("OP_SELLER");
		String rawPassword = seller.getPassword();
		
		String telephoneNumber = seller.getTelephoneNumber1() + "-" + seller.getTelephoneNumber2() + "-" + seller.getTelephoneNumber3();
		String phoneNumber = seller.getPhoneNumber1() + "-" + seller.getPhoneNumber2() + "-" + seller.getPhoneNumber3();
		String faxNumber = seller.getFaxNumber1() + "-" + seller.getFaxNumber2() + "-" + seller.getFaxNumber3();
		String post = seller.getPost();
		String businessNumber = seller.getBusinessNumber1() + "-" + seller.getBusinessNumber2() + "-" + seller.getBusinessNumber3();
		
		
		String secondTelephoneNumber = seller.getSecondTelephoneNumber1() + "-" + seller.getSecondTelephoneNumber2() + "-" + seller.getSecondTelephoneNumber3();
		String secondPhoneNumber = seller.getSecondPhoneNumber1() + "-" + seller.getSecondPhoneNumber2() + "-" + seller.getSecondPhoneNumber3();
		
		
		seller.setSecondTelephoneNumber(secondTelephoneNumber);
		seller.setSecondPhoneNumber(secondPhoneNumber);
		
		seller.setTelephoneNumber(telephoneNumber);
		seller.setPhoneNumber(phoneNumber);
		
		seller.setFaxNumber(faxNumber);
		seller.setPost(post);
		seller.setBusinessNumber(businessNumber);
		
		seller.setPassword(passwordEncoder.encode(rawPassword));
		
		seller.setSellerId(sellerId);
		
		sellerMapper.insertSeller(seller);

		try {
			User user = new User();

			user.setLoginId(seller.getLoginId());
			user.setPassword(rawPassword);
			user.setUserName(seller.getSellerName());
			user.setPhoneNumber(phoneNumber);
			user.setEmail(seller.getEmail());

			sellerUserService.insertSellerMasterUser(sellerId, user);
		} catch (Exception e) {
			log.error("insert seller user error > {}", sellerId, e);
		}

	}
	
	@Override
	public void updateSeller(Seller seller) {
		
		String telephoneNumber = seller.getTelephoneNumber1() + "-" + seller.getTelephoneNumber2() + "-" + seller.getTelephoneNumber3();
		String phoneNumber = seller.getPhoneNumber1() + "-" + seller.getPhoneNumber2() + "-" + seller.getPhoneNumber3();
		String faxNumber = seller.getFaxNumber1() + "-" + seller.getFaxNumber2() + "-" + seller.getFaxNumber3();
		String post = seller.getPost1() + "-" + seller.getPost2();
		String businessNumber = seller.getBusinessNumber1() + "-" + seller.getBusinessNumber2() + "-" + seller.getBusinessNumber3();
		
		String secondTelephoneNumber = seller.getSecondTelephoneNumber1() + "-" + seller.getSecondTelephoneNumber2() + "-" + seller.getSecondTelephoneNumber3();
		String secondPhoneNumber = seller.getSecondPhoneNumber1() + "-" + seller.getSecondPhoneNumber2() + "-" + seller.getSecondPhoneNumber3();
		
		
		seller.setSecondTelephoneNumber(secondTelephoneNumber);
		seller.setSecondPhoneNumber(secondPhoneNumber);
		
		if (seller.getTelephoneNumber1() != null){
			seller.setTelephoneNumber(telephoneNumber);
		}
		if (seller.getPhoneNumber1() != null){
			seller.setPhoneNumber(phoneNumber);
		}
		if (seller.getFaxNumber1() != null){
			seller.setFaxNumber(faxNumber);
		}
		if (seller.getPost1() != null){
			seller.setPost(post);
		}
		if (seller.getBusinessNumber1() != null){
			seller.setBusinessNumber(businessNumber);
		}

		if (seller.getPassword() != null){
			if(!seller.getPassword().equals("")){
				seller.setPassword(passwordEncoder.encode(seller.getPassword()));
			}
		}
		sellerMapper.updateSeller(seller);
		
		
		// 판매자 배송비 조건부 상품 배송비 일괄 업데이트.
		itemMapper.updateShipmentPriceForSeller(seller);
		
		// 판매자 정보의 담당MD가 변경된 경우 해당 업체 상품 정보에 변경 전 담당MD가 지정된 경우에만 상품MD 일괄 UPDATE 
		if (seller.getCurrentMdId() != null 
				&& seller.getMdId() != null
				&& !seller.getCurrentMdId().equals(seller.getMdId())) {
			
			itemMapper.updateItemMdUserForSeller(seller);
		}
	}

	@Override
	public void deleteSeller(Seller seller) {
		sellerMapper.deleteSeller(seller);
	}

	@Override
	public List<SellerCategory> getSellerCategoriesById(long sellerId) {
		return sellerMapper.getSellerCategoriesById(sellerId);
	}

	@Override
	public List<SellerCategory> getSellerItemsByParam(ItemParam itemParam) {
		//return sellerMapper.getSellerItemsByParam(itemParam);
		return null;
	}

	@Override
	public void updateSellerMinimall(Seller seller) {
		sellerMapper.updateSellerMinimall(seller);
		
	}
	
	@Override
	public List<Seller> getSellerIdBySmsSendTime(String Hour) {
		return sellerMapper.getSellerIdBySmsSendTime(Hour);
	}
	
	@Override
	public void updateSellerPassword(Seller seller) {
		if (seller.getPassword() != null){
			if(!seller.getPassword().equals("")){
				seller.setPassword(passwordEncoder.encode(seller.getPassword()));
				
				sellerMapper.updateSellerPassword(seller);
				
			}
		}
	}
	
}
