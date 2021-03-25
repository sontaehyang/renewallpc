package saleson.shop.config;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.config.domain.Config;
import saleson.shop.point.PointMapper;
import saleson.shop.point.domain.PointConfig;

import java.io.File;
import java.io.IOException;


@Service("configService")
public class ConfigServiceImple implements ConfigService{
	private static final Logger log = LoggerFactory.getLogger(ConfigServiceImple.class);

	@Autowired
	ConfigMapper configMapper;
	
	@Autowired 
	PointMapper pointMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private SequenceService sequenceService;

	@Override
	public int getShopConfigCount(int shopId) {

		return configMapper.getShopConfigCount(shopId);
	}

	@Override
	public Config getShopConfig(int shopId) {

		return configMapper.getShopConfig(shopId);
	}
	
	@Override
	@Cacheable("shopConfig")
	public Config getShopConfigCache(int shopId) {
		return configMapper.getShopConfig(shopId);
	}

	@Override
	public void updateShopConfigDeniedId(Config config) {

		configMapper.updateShopConfigDeniedId(config);
	}

	@Override
	public void updateShopConfig(Config config) {

		configMapper.updateShopConfig(config);
	}

	@Override
	public void updateShopConfigConversionTag(Config config) {

		configMapper.updateShopConfigConversionTag(config);
	}

	@Override
	public void updateShopSiteConfig(Config config) {

		configMapper.updateShopSiteConfig(config);
	}

	@Override
	public void updateShopConfigPayment(Config config) {

		configMapper.updateShopConfigPayment(config);
	}

	@Override
	public void updateShopConfigTopBanner(Config config) {

		
		if (config.getTopBannerImageFile() != null) {
			if (config.getTopBannerImageFile().getSize() > 0) {
				deleteShopConfigBannerImage(config, "topBanner");
				
				MultipartFile multipartFile = config.getTopBannerImageFile();
				
				String fileName = createBannerImage(config, multipartFile, "topBanner", 0);
				// 대표이미지 파일명
				config.setTopBannerImage(fileName);
			}
		}
		
		if (config.getRightBannerImageFile() != null) {
			if (config.getRightBannerImageFile().getSize() > 0) {
				deleteShopConfigBannerImage(config, "rightBanner");
				
				MultipartFile multipartFile = config.getRightBannerImageFile();
				
				String fileName = createBannerImage(config, multipartFile, "rightBanner", 1);
				// 대표이미지 파일명
				config.setRightBannerImage(fileName);
			}
		}
		configMapper.updateShopConfigTopBanner(config);
	}

	
	@Override
	public void deleteShopConfigTopBanner(Config config) {
		deleteShopConfigBannerImage(config, "topBanner");
		deleteShopConfigBannerImage(config, "rightBanner");
		configMapper.deleteShopConfigTopBanner(config);
	}
	
	
	@Override
	public void deleteShopConfigBannerImage(Config config, String bannerType) {
		Config secondConfig = configMapper.getShopConfig(config.getShopConfigId());
		
		if (bannerType.equals("topBanner")) {
			if (secondConfig.getTopBannerImage() != null) {
				// 1. 이미지 파일 삭제
				if (secondConfig.getTopBannerImage().indexOf("/") > -1) {
					FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(secondConfig.getTopBannerImage())).replaceAll("/",  File.separator)));
				} else {
					String popupImageSrc = "/config/" + secondConfig.getShopConfigId() + "/topBanner/" + ShopUtils.unescapeHtml(secondConfig.getTopBannerImage());
					FileUtils.delete(popupImageSrc);
				}
				// 2. 상품 이미지 정보 업데이트.
				secondConfig.setTopBannerImage("");
			}
		} else if (bannerType.equals("rightBanner")) {
			if (secondConfig.getRightBannerImage() != null) {
				// 1. 이미지 파일 삭제
				if (secondConfig.getRightBannerImage().indexOf("/") > -1) {
					FileUtils.delete(new File((FileUtils.getWebRootPath() + ShopUtils.unescapeHtml(secondConfig.getRightBannerImage())).replaceAll("/",  File.separator)));
				} else {
					String popupImageSrc = "/config/" + secondConfig.getShopConfigId() + "/rightBanner/" + secondConfig.getRightBannerImage();
					FileUtils.delete(popupImageSrc);
				}
				// 2. 상품 이미지 정보 업데이트.
				secondConfig.setRightBannerImage("");
			}
		}
		
		configMapper.deleteShopConfigBannerImage(secondConfig);
	}

	
	public String createBannerImage(Config config, MultipartFile multipartFile, String bannerType, int index) {
		String[] ITEM_DEFAULT_IMAGE_WIDTHS = { "1150x-1", "200x-1" };
		String[] ITEM_DEFAULT_IMAGE_SAVE_FOLDER = new String[] {bannerType};
		String ITEM_DEFAULT_IMAGE_SAVE_SIZE = ITEM_DEFAULT_IMAGE_WIDTHS[index];
		
		String defaultFileName = DateUtils.getToday(Const.DATETIME_FORMAT) + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
		
		for (int i = 0; i < ITEM_DEFAULT_IMAGE_SAVE_FOLDER.length; i++) {
			String saveFolderName = ITEM_DEFAULT_IMAGE_SAVE_FOLDER[i];
				
			// 1. 업로드 경로설정
			String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + "config" + File.separator + config.getShopConfigId() + File.separator + saveFolderName;
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
				
				ThumbnailUtils.create(multipartFile, saveFile, Integer.parseInt(thumbnailSize[0]), Integer.parseInt(thumbnailSize[1]));
			} catch (IOException e) {
				log.warn("ThumbnailUtils.create(multipartFile...: {}", e.getMessage(), e);
			}
	        //fileService.createThumbnail(saveFile, uploadPath, newFileName, thumbnailSize, "0");
		}
		
		return defaultFileName;
	}
	
	
	@Override
	public void updateShopConfigReserve(Config config) {
		pointMapper.deleteShopPointConfig(new PointConfig("1"));
		// 적립금 설정
		if (config.getPointType() != null) {					
			for (int i = 0; i < config.getPointType().length; i++) {
				PointConfig pointConfig = new PointConfig();
				
				pointConfig.setPointConfigId(sequenceService.getId("OP_POINT_CONFIG"));
				
				pointConfig.setConfigType("1");
				pointConfig.setPeriodType(ArrayUtils.get(config.getPointRepeatDay(), i).trim().equals("") ? "1" : "2");
				pointConfig.setPointType(ArrayUtils.get(config.getPointType(), i));
				pointConfig.setPoint(Double.parseDouble(ArrayUtils.get(config.getPoint(), i)));
				pointConfig.setStartDate(ArrayUtils.get(config.getStartDate(), i));
				pointConfig.setStartTime(ArrayUtils.get(config.getStartTime(), i));
				pointConfig.setEndDate(ArrayUtils.get(config.getEndDate(), i));
				pointConfig.setEndTime(ArrayUtils.get(config.getEndTime(),i));
				pointConfig.setRepeatDay(ArrayUtils.get(config.getPointRepeatDay(), i));
				pointConfig.setItemId(0);
				pointConfig.setStatusCode("1");
				pointConfig.setCreatedUserId(UserUtils.getManagerId());
				
				pointMapper.insertPointConfig(pointConfig);
			}
		}
		configMapper.updateShopConfigReserve(config);
	}

	@Override
	public void updateShopConfigDeliveryInfo(Config config) {
		configMapper.updateShopConfigDeliveryInfo(config);
	}

	@Override
	public void updateShopConfigDeliveryHope(Config config) {
		configMapper.updateShopConfigDeliveryHope(config);
	}

	@Override
	public void updateCategoryUpdatedDate() {
		configMapper.updateCategoryUpdatedDate();
	}

	@Override
	public void updateShopConfigItem(Config config) {
		configMapper.updateShopConfigItem(config);
	}
}
