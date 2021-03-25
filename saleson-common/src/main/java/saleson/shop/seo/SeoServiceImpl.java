package saleson.shop.seo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import saleson.common.config.SalesonProperty;
import saleson.common.utils.ItemUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.categories.CategoriesMapper;
import saleson.shop.categories.domain.Categories;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;
import saleson.shop.seo.domain.Seo;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.web.domain.ListParam;
import com.onlinepowers.framework.web.domain.SearchParam;

@Service("seoService")
public class SeoServiceImpl implements SeoService {
	@Autowired
	private SeoMapper seoMapper;
	
	@Autowired
	private SequenceService sequenceService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CategoriesMapper categoriesMapper;


	@Override
	public Seo getSeoById(int seoId) {
		return seoMapper.getSeoById(seoId);
	}

	@Override
	public int getSeoCount(SearchParam param) {
		return seoMapper.getSeoCount(param);
	}

	@Override
	public List<Seo> getSeoList(SearchParam param) {
		return seoMapper.getSeoList(param);
	}

	@Override
	public void insertSeo(Seo seo) {
		
		// URL 중복체크 
		if (seoMapper.getSeoCountBySeoUrl(seo.getSeoUrl()) > 0) {
			throw new UserException("이미 등록된 URL입니다.");
		}
		
		seo.setSeoId(sequenceService.getId("OP_SEO"));
		seo.setCreatedUserId(UserUtils.getManagerId());
		seoMapper.insertSeo(seo);
		
	}

	@Override
	public void updateSeo(Seo seo) {
		seoMapper.updateSeo(seo);
	}

	@Override
	public void deleteSeoById(int seoId) {
		seoMapper.deleteSeoById(seoId);
	}

	@Override
	@Cacheable("fixedMetaData")
	public List<Seo> getSeoListAll() {
		return seoMapper.getSeoList(new SearchParam());
	}

	@Override
	public void updateListData(ListParam listParam) {

		
	}

	@Override
	public void deleteListData(ListParam listParam) {
		
		if (listParam.getId() != null) {

			for (String seoId : listParam.getId()) {
				seoMapper.deleteSeoById(Integer.parseInt(seoId));
			}
			
		}
	}

	@Override
	public String getSitemapString() {

		ItemParam itemParam = new ItemParam();
		itemParam = ItemUtils.bindItemParam(itemParam);
		List<Item> itemList = itemService.getItemList(itemParam);

		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
		for (Item item : itemList) {

			String link = SalesonProperty.getSalesonUrlShoppingmall()
					+ "/seo/item/"
					+ item.getItemUserCode();

			sb.append(getSitemapUrlString(link));

		}

		List<Categories> categories =  categoriesMapper.getCategoryListBySitemap();

		for (Categories c : categories) {

			String link = SalesonProperty.getSalesonUrlShoppingmall()
					+ "/seo/categories/"
					+ c.getCategoryUrl();

			sb.append(getSitemapUrlString(link));

		}

		sb.append("</urlset>");

		return sb.toString();
	}

	private String getSitemapUrlString(String loc) {
		StringBuffer sb = new StringBuffer();
		sb.append("<url>");
		sb.append("<loc>");
		sb.append(loc);
		sb.append("</loc>");
		sb.append("<changefreq>always</changefreq>");
		sb.append("</url>");
		return sb.toString();
	}
}
