package saleson.shop.brand;

import java.util.List;

import com.onlinepowers.framework.web.domain.ListParam;

import saleson.shop.brand.domain.Brand;
import saleson.shop.brand.support.BrandParam;

public interface BrandService {

	/**
	 * 조건에 해당하는 데이터 수
	 * @param param
	 * @return
	 */
	int getBrandCount(BrandParam param);

	/**
	 * 조건에 해당하는 데이터 목록
	 * @param param
	 * @return
	 */
	List<Brand> getBrandList(BrandParam param);
	
	/**
	 * 조건에 해당하는 데이터 조회
	 * @param param
	 * @return
	 */
	Brand getBrand(BrandParam param);
	
	/**
	 * ID로 데이터 조회
	 * @param brandId
	 * @return
	 */
	Brand getBrandById(int brandId);
	
	/**
	 * 데이터 등록
	 * @param brand
	 */
	void insertBrand(Brand brand);
	
	/**
	 * 데이터 수정
	 * @param brand
	 */
	void updateBrand(Brand brand);
	
	/**
	 * ID로 데이터 삭제
	 * @param brandId
	 */
	void deleteBrandById(int brandId);

	void deleteBrandData(ListParam listParam);

}
