package saleson.shop.seo;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;

import saleson.shop.seo.domain.Seo;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;
import com.onlinepowers.framework.web.domain.SearchParam;

@Mapper("seoMapper")
public interface SeoMapper {
	
	/**
	 * SEO 정보를 조회한다.
	 * @param seoId
	 * @return
	 */
	Seo getSeoById(int seoId);
	
	
	/**
	 * SEO 데이터 수 조회.
	 * @param param
	 * @return
	 */
	int getSeoCount(SearchParam param);
	
	
	/**
	 * SEO 목록 조회
	 * @param param
	 * @return
	 */
	List<Seo> getSeoList(SearchParam param);
	
	
	/**
	 * SEO 등록
	 * @param seo
	 */
	@CacheEvict(value="fixedMetaData", allEntries=true)
	void insertSeo(Seo seo);
	
	
	
	/**
	 * SEO 수정.
	 * @param seo
	 */
	@CacheEvict(value="fixedMetaData", allEntries=true)
	void updateSeo(Seo seo); 
	
	
	/**
	 * SEO 삭제
	 * @param seoId
	 */
	@CacheEvict(value="fixedMetaData", allEntries=true)
	void deleteSeoById(int seoId);


	/**
	 * SEO_URL 중복 체크
	 * @param seoUrl
	 * @return
	 */
	int getSeoCountBySeoUrl(String seoUrl);
}
