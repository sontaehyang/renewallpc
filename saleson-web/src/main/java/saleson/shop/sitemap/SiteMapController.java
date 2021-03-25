package saleson.shop.sitemap;


import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import saleson.shop.seo.SeoService;

@Controller
@RequestMapping("/")
@RequestProperty(layout="default")
public class SiteMapController {

	@Autowired
	private SeoService seoService;

	/**
	 * 사이트맵
	 * @return
	 */
	@GetMapping(value= "/sitemap.xml", produces="application/xml; charset=UTF-8")
	@ResponseBody
	public String list() {
		return seoService.getSitemapString();
	}

}
