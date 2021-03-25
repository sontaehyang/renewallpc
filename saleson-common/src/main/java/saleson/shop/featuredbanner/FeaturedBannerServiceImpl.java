package saleson.shop.featuredbanner;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ThumbnailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.shop.featuredbanner.domain.BannerImage;
import saleson.shop.featuredbanner.domain.FeaturedBanner;

import java.io.File;
import java.io.IOException;

@Service("featuredBannerService")
public class FeaturedBannerServiceImpl implements FeaturedBannerService {
	private static final Logger log = LoggerFactory.getLogger(FeaturedBannerServiceImpl.class);

	@Autowired
	FeaturedBannerMapper featuredBannerMapper;
	
	@Autowired
	FileService fileService;
	
	
	@Override
	public FeaturedBanner getFeaturedBanner(int featuredBannerId) {

		return featuredBannerMapper.getFeaturedBanner(featuredBannerId);
	}

	
	@Override
	public void updateFeaturedBanner(FeaturedBanner featuredBanner) {

		if (featuredBanner.getBannerImages() == null) {
			return;
		}
		
		for(BannerImage image : featuredBanner.getBannerImages()) {
			if (image.getUploadFile() != null) {
				if (image.getUploadFile().getSize() > 0) {
					String code = image.getCode();
					MultipartFile file = image.getUploadFile();
					
					if ("left-top".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerLeftTopImage());
						featuredBanner.setBannerLeftTopImage(createBannerImage(featuredBanner, file, code, 0));
					} else if ("left-bottom1".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerLeftBottom1Image());
						featuredBanner.setBannerLeftBottom1Image(createBannerImage(featuredBanner, file, code, 1));
					} else if ("left-bottom2".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerLeftBottom2Image());
						featuredBanner.setBannerLeftBottom2Image(createBannerImage(featuredBanner, file, code, 2));
					} else if ("center".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerCenterImage());
						featuredBanner.setBannerCenterImage(createBannerImage(featuredBanner, file, code, 3));
					} else if ("right-top".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerRightTopImage());
						featuredBanner.setBannerRightTopImage(createBannerImage(featuredBanner, file, code, 4));
					} else if ("right-bottom1".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerRightBottom1Image());
						featuredBanner.setBannerRightBottom1Image(createBannerImage(featuredBanner, file, code, 5));
					} else if ("right-bottom2".equals(code)) {
						deleteBannerImage(featuredBanner.getBannerRightBottom2Image());
						featuredBanner.setBannerRightBottom2Image(createBannerImage(featuredBanner, file, code, 6));
					}
				}
			}
		}
		
		featuredBannerMapper.updateFeaturedBanner(featuredBanner);
	}

	
	@Override
	public void deleteBannerImage(String imageName) {

		// 1. 이미지 파일 삭제
		if (imageName != null && !"".equals(imageName)) {
			if (imageName.indexOf("/") > -1) {
				FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(imageName)).replaceAll("/",  File.separator)));
			} else {
				String popupImageSrc = "/featured-banner/1/" + ShopUtils.unescapeHtml(imageName);
				FileUtils.delete(popupImageSrc);
			}
			// 2. 상품 이미지 정보 업데이트.
			FeaturedBanner featuredBanner = featuredBannerMapper.getFeaturedBanner(1);
			String[] realType = imageName.split("_");
			
			if ("left-top".equals(realType[0])) {
				featuredBanner.setBannerLeftTopImage("");
			} else if ("left-bottom1".equals(realType[0])) {
				featuredBanner.setBannerLeftBottom1Image("");
			} else if ("left-bottom2".equals(realType[0])) {
				featuredBanner.setBannerLeftBottom2Image("");
			} else if ("center".equals(realType[0])) {
				featuredBanner.setBannerCenterImage("");
			} else if ("right-top".equals(realType[0])) {
				featuredBanner.setBannerRightTopImage("");
			} else if ("right-bottom1".equals(realType[0])) {
				featuredBanner.setBannerRightBottom1Image("");
			} else if ("right-bottom2".equals(realType[0])) {
				featuredBanner.setBannerRightBottom2Image("");
			}
			
			featuredBannerMapper.updateBannerImage(featuredBanner);
		}
	}

	
	public String createBannerImage(FeaturedBanner featuredBanner, MultipartFile multipartFile, String bannerType, int index) {
		String[] ITEM_DEFAULT_IMAGE_WIDTHS = {
				"400x-1", "200x-1",  "200x-1", "400x-1", "400x-1", "200x-1",  "200x-1"
		};
		String ITEM_DEFAULT_IMAGE_SAVE_FOLDER = "featured-banner";
		String ITEM_DEFAULT_IMAGE_SAVE_SIZE = ITEM_DEFAULT_IMAGE_WIDTHS[index];
		
		String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
		String saveFolderName = ITEM_DEFAULT_IMAGE_SAVE_FOLDER;
			
		// 1. 업로드 경로설정
		String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + saveFolderName + File.separator + featuredBanner.getFeaturedBannerId();
		fileService.makeUploadPath(uploadPath);
    	
		// 2. 파일명 중복파일 삭제
		try {
			FileUtils.delete(uploadPath, ShopUtils.unescapeHtml(defaultFileName));
		} catch (IOException e1) {
			log.warn(e1.getMessage(), e1);
		}
		
		// 2-1. 새로운 파일명.
		defaultFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);
		
		// 3. 저장될 파일 
		File saveFile = new File(uploadPath + File.separator + defaultFileName);
		
		// 4. 섬네일 사이즈 
		String[] thumbnailSize = StringUtils.delimitedListToStringArray(ITEM_DEFAULT_IMAGE_SAVE_SIZE, "x");
		
	
		// 생성.
		try {
			//FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
			ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
		} catch (IOException e) {
			log.error("ERROR: {}", e.getMessage(), e);
		}
        //fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");
		return defaultFileName;
	}
}
