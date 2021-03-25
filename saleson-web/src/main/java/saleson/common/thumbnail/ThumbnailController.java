/*
 * Thumbnail 생성.
 * 
 * @Author skc@onlinepwers.com
 * @Date 2015-09-30
 * 
 * <example>
 * /thumbnail?src=/upload/a/b.jpg&size=300  (&width=400&height=300) (&type=file or url)
 * </example>
 * 
 * <see>
 * https://code.google.com/p/thumbnailator/
 * </see>
 * 

 */
package saleson.common.thumbnail;

import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.common.thumbnail.domain.Thumbnail;
import saleson.common.utils.ShopUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;

@Controller
@RequestMapping({"/thumbnail", "/m/thumbnail"})
public class ThumbnailController {

	private static final Logger log = LoggerFactory.getLogger(ThumbnailController.class);

	@Autowired
	Environment environment;

	@GetMapping
	public void index(Thumbnail thumbnail,
			HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream os = null;
		try {
			String mimeType = FileUtils.getExtension(thumbnail.getSrc());
			
			if (mimeType != null) {
				mimeType = mimeType.toLowerCase().replace("jpg", "jpeg");
			}
			
			response.setContentType("image/" + mimeType + ";charset=UTF-8");
			os = response.getOutputStream();
			//ImageIO.setUseCache(false);
		
			int w = 0;
			int h = 0;
			
			if (thumbnail.getWidth() > 0 && thumbnail.getHeight() > 0) {
				w = thumbnail.getWidth();
				h = thumbnail.getHeight();
				
			} else if (thumbnail.getSize() > 0) {
				w = thumbnail.getSize();
				h = thumbnail.getSize();
				
			} 
			
			if ("FILE".equalsIgnoreCase(thumbnail.getType())) {
				File image = new File(environment.getProperty("upload.root"), thumbnail.getSrc());
				
				Thumbnails.of(image)
					.crop(Positions.CENTER)
			        .size(w, h)
			        .outputFormat(mimeType)
			        .toOutputStream(os);
				
			} else {
				String baseUri = "http://" + request.getServerName();
				if (request.getServerPort() == 443 || request.getServerPort() == 8443) {
					baseUri = "https://" + request.getServerName();
				}
				
				if (!(request.getServerPort() == 443 || request.getServerPort() == 8443)) {
					baseUri = baseUri + ":" + request.getServerPort();
				}
				
				String imageUrl = baseUri + thumbnail.getSrc();
				
				URL image = new URL(imageUrl);
				Thumbnails.of(image)
					.crop(Positions.CENTER)
			        .size(w, h)
			        .outputFormat(thumbnail.getOutputFormat())
			        .toOutputStream(os);
			}
			
		} catch (Exception e) {
			log.debug("섬네일 생성오류1 :  {}", e.getMessage());
			try {
				String redirectUrl = ShopUtils.getNoImagePath();
				
				if (!StringUtils.isEmpty(thumbnail.getSrc())) {
					redirectUrl = thumbnail.getSrc();
				}
				response.sendRedirect(redirectUrl);
				
			} catch (Exception e1) {
				log.debug("섬네일 생성 오류 시 Redirect 오류 :  {}", e1.getMessage());
				
			}
		} 
	}
	
}
