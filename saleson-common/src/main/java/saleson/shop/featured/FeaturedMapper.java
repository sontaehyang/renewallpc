package saleson.shop.featured;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.featured.domain.Featured;
import saleson.shop.featured.domain.FeaturedReply;
import saleson.shop.featured.support.FeaturedItem;
import saleson.shop.featured.support.FeaturedParam;
import saleson.shop.featured.support.FeaturedReplyParam;

@Mapper("featuredMapper")
public interface FeaturedMapper {
	
	int getFeaturedCountByParam(FeaturedParam featuredParam);
	
	List<Featured> getFeaturedListByParam(FeaturedParam featuredParam);
	
	Featured getFeaturedById(FeaturedParam featuredParam);
	
	void deleteFeaturedsById(FeaturedParam featuredParam);
	
	void insertFeatured(Featured featured);
	
	void updateFeaturedById(Featured featured);
	
	void updateFeaturedImageById(Featured featured);
	
	int getFeaturedCountByUrl(String featuredUrl);

	List<String> getUserDefGroupById(FeaturedParam featuredParam);
	
	/**
	 * 순서 증가.
	 */
	void updateFeaturedOrderingAll();
	
	/* FeaturedItem을 등록 */
	void insertFeaturedItem(FeaturedItem featuredItem);
	
	/** 테마 목록 */
	List<Featured> getThemeList();
	
	/* featuredItemList 받아오기 */
	List<FeaturedItem> getFeaturedItemListByParam(FeaturedParam featuredParam);
	
	/* 기존의 FeaturedItem을 id로 기준으로 삭제 */
	void deleteFeaturedItemById(Featured featured);
	
	/* 출력될 Item의 Category 정보 가져오기 */
	List<FeaturedItem> getItemCategoriesByParam(FeaturedParam featuredParam);
	
	/* 메인 이벤트 Merge */
	public void mergeEvent(Featured featured);
	
	/**
	 * 기획전 카운트
	 * @param featuredParam
	 * @return
	 */
	int getFeaturedCountByParamForFront(FeaturedParam featuredParam);
	
	/**
	 * 기획전 목록
	 * @param featuredParam
	 * @return
	 */
	List<Featured> getFeaturedListByParamForFront(FeaturedParam featuredParam);

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

	public int updateEventCode(Featured featured);

}
