package saleson.seller.main;

import java.util.List;

import saleson.seller.main.domain.Seller;
import saleson.seller.main.domain.SellerCategory;
import saleson.seller.main.support.SellerParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("sellerMapper")
public interface SellerMapper {

	/**
	 * 판매자 로그인 아이디로 정보 조회.
	 * @param loginId
	 * @return
	 */
	Seller getSellerByLoginId(String loginId);
	
	
	/**
	 * 판매자 ID로 판매자 정보 조회.
	 * @param sellerId
	 * @return
	 */
	Seller getSellerById(long sellerId);

	List<Seller> getAllSellerList();
	/**
	 * 입점업체 조회.
	 * @param sellerParam
	 * @return
	 */
	List<Seller> getSellerListByParam(SellerParam sellerParam);
	
	/**
	 * 입점업체 카운트.
	 * @param sellerParam
	 * @return
	 */
	int getSellerCount(SellerParam sellerParam);
	
	/**
	 * 입점업체 등록.
	 * @param seller
	 * @return
	 */
	void insertSeller(Seller seller);
	
	/**
	 * 입점업체 수정.
	 * @param seller
	 * @return
	 */
	void updateSeller(Seller seller);
	
	/**
	 * 입점업체 삭제.
	 * @param seller
	 * @return
	 */
	void deleteSeller(Seller seller);
	
	
	/**
	 * 입점업체 상품 카테고리
	 * @param sellerId
	 * @return
	 */
	List<SellerCategory> getSellerCategoriesById(long sellerId);
	
	
	/**
	 * 입점업체 상품 목록
	 * @param 
	 * @return
	 
	List<SellerCategory> getSellerItemsByParam(ItemParam itemParam);
	*/
	
	/**
	 * 미니몰 상단 정보 수정.
	 * @param seller
	 */
	void updateSellerMinimall(Seller seller);

	/**
	 * 입점업체 판매자 발송 SMS 시간
	 */
	List<Seller> getSellerIdBySmsSendTime(String hour);

	/**
	 * 입점업체 비밀번호 변경
	 * @param seller
	 */
	void updateSellerPassword(Seller seller);
}
