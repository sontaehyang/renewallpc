package saleson.shop.seo;

import java.util.List;

import saleson.shop.seo.domain.Seo;

import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.domain.SearchParam;

public interface SeoService {
	
	/**
	 * SEO 정보를 조회한다.
	 * @param seoId
	 * @return
	 */
	public Seo getSeoById(int seoId);
	
	
	/**
	 * SEO 데이터 수 조회.
	 * @param param
	 * @return
	 */
	public int getSeoCount(SearchParam param);
	
	
	/**
	 * SEO 목록 조회
	 * @param param
	 * @return
	 */
	public List<Seo> getSeoList(SearchParam param);
	
	
	/**
	 * SEO 전체 목록 조회
	 * 프론트에서 사용.
	 * @param param
	 * @return
	 */
	public List<Seo> getSeoListAll();
	
	
	/**
	 * SEO 등록
	 * @param seo
	 */
	public void insertSeo(Seo seo);
	
	
	
	/**
	 * SEO 수정.
	 * @param seo
	 */
	public void updateSeo(Seo seo); 
	
	
	/**
	 * SEO 삭제
	 * @param seoId
	 */
	public void deleteSeoById(int seoId);

	/**
	 * 일괄수정.
	 * @param listParam
	 */
	public void updateListData(ListParam listParam);


	/**
	 * 목록데이터 중 선택 데이터 전체 삭제.
	 * @param listParam
	 */
	public void deleteListData(ListParam listParam);

	String getSitemapString();
}
