package saleson.shop.featured;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.domain.FeaturedReply;
import saleson.shop.featured.support.*;

public interface FeaturedService {
	
	public int getFeaturedCountByParam(FeaturedParam featuredParam);
	
	/**
	 * 기획전 카운트
	 * @param featuredParam
	 * @return
	 */
	public int getFeaturedCountByParamForFront(FeaturedParam featuredParam);
	
	/**
	 * 기획전 목록
	 * @param featuredParam
	 * @return
	 */
	public List<Featured> getFeaturedListByParamForFront(FeaturedParam featuredParam);
	
	public List<Featured> getFeaturedListByParam(FeaturedParam featuredParam);
	
	public Featured getFeaturedById(FeaturedParam featuredParam);
	
	public void deleteFeaturedsById(FeaturedParam featuredParam);
	
	public int insertFeatured(Featured featured, FeaturedItemParam featuredItemParam);
	
	public void updateFeaturedById(Featured featured, FeaturedItemParam featuredItemParam);
	
	public void deleteImageByItemId(FeaturedParam featuredParam);
	
	public int getFeaturedCountByUrl(String featuredUrl);

	/**
	 * 기획페이지 노출 순서를 설정한다.
	 * @param featuredListParam
	 */
	public void updateFeaturedOrdering(FeaturedListParam featuredListParam);
	
	/** 사용자 그룹 받아오기 */
	public List<String> getUserDefGroupById(FeaturedParam featuredParam);
	
	/* featuredItemList 받아오기 */
	public List<FeaturedItem> getFeaturedItemListByParam(FeaturedParam featuredParam);
	
	/* 기존의 FeaturedItem을 id로 기준으로 삭제 */
	public void deleteFeaturedItemById(Featured featured);
	
	/* 출력될 Item의 Category 정보 가져오기 */
	public List<FeaturedItem> getItemCategoriesByParam(FeaturedParam featuredParam);
	
	public List<Featured> getThemeList();
	
	/* 메인 이벤트 Merge */
	public void mergeEvent(Featured featured, FeaturedItemParam featuredItemParam);
	
	/**
	 *  기획전 상품 분류 타입
	 * @param prodState
	 * @param featuredParam
	 * @return
	 */
	public List<HashMap<String, String>> getItemTypeList(String prodState, FeaturedParam featuredParam);
	
	/**
	 * 기획전 상품 분류별 상품 조회
	 * @param prodState
	 * @param featuredParam
	 * @return
	 */
	public HashMap<String, List<FeaturedItem>> getItemListMap(String prodState, FeaturedParam featuredParam);

	/**
	 * 분류된 기획전 상품들중 상품코드로 재구성
	 * @param itemListMap
	 * @return
	 */
	public List<String> getItemUserCodesByItemListMap(HashMap<String, List<FeaturedItem>> itemListMap);

	/**
	 * 해당 기획전 댓글 리스트 count
	 * @param featuredReplyParam
	 * @return
	 */
	public int getFeaturedReplyCountByParam(FeaturedReplyParam featuredReplyParam);

	/**
	 * 해당 기획전 댓글 리스트
	 * @param featuredReplyParam
	 * @return
	 */
	public List<FeaturedReply> getFeaturedReplyByParam(FeaturedReplyParam featuredReplyParam);

	/**
	 * 기획전 댓글 등록
	 * @param featuredReply
	 */
	public void insertFeaturedReply(FeaturedReply featuredReply);

	/**
	 * 기획전 댓글 비공개 처리
	 * @param featuredReply
	 */
	public void updateDisplayReply(FeaturedReply featuredReply);

	int updateEventCode(int featuredId);
}
