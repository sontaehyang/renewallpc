package saleson.shop.brand;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import saleson.common.utils.ShopUtils;
import saleson.shop.brand.domain.Brand;
import saleson.shop.brand.support.BrandParam;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.web.domain.ListParam;

@Service("brandService")
public class BrandServiceImpl implements BrandService {
	private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Autowired
	private FileService fileService;

	@Override
	public int getBrandCount(BrandParam param) {
		return brandMapper.getBrandCount(param);
	}

	@Override
	public List<Brand> getBrandList(BrandParam param) {
		return brandMapper.getBrandList(param);
	}

	@Override
	public Brand getBrand(BrandParam param) {
		return brandMapper.getBrand(param);
	}

	@Override
	public Brand getBrandById(int brandId) {
		return brandMapper.getBrandById(brandId);
	}

	@Override
	public void insertBrand(Brand brand) {
		brand.setBrandId(sequenceService.getId("OP_BRAND"));
		processUploadedFile(brand);
		brandMapper.insertBrand(brand);
	}

	@Override
	public void updateBrand(Brand brand) {
		
		// 삭제 체크 시 처리.
		if ("Y".equals(brand.getBrandImageDeleteFlag())) {
			brand.setBrandImage("");
		}
		processUploadedFile(brand);
		brandMapper.updateBrand(brand);
	}

	/**
	 * 업로드 파일 처리.
	 * @param brand
	 */
	private void processUploadedFile(Brand brand) {
		if (brand.getFile() != null && brand.getFile().getSize() > 0) {
			MultipartFile multipartFile = brand.getFile();
			
			String SAVE_FOLDER = "brand";
			String defaultFileName = brand.getBrandId() + "." + FileUtils.getExtension(multipartFile.getOriginalFilename());
				
			// 1. 업로드 경로설정
			String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + SAVE_FOLDER + File.separator + brand.getBrandId();
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
			

			// 생성.
			try {
				FileCopyUtils.copy(multipartFile.getBytes(), saveFile);
			} catch (IOException e) {
				log.error("FileCopyUtils.copy(multipartFile.getBytes(), saveFile); : {}", e.getMessage(), e);
			}


			// 대표이미지 파일명
			brand.setBrandImage(defaultFileName);
		}
	}

	@Override
	public void deleteBrandById(int brandId) {
		brandMapper.deleteBrandById(brandId);
	}

	@Override
	public void deleteBrandData(ListParam listParam) {
		
		if (listParam.getId() != null) {

			for (String brandId : listParam.getId()) {
				brandMapper.deleteBrandById(Integer.parseInt(brandId));
			}	
		}
	}
}
