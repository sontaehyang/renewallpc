package saleson.shop.businesscode;

import java.util.List;

import saleson.shop.businesscode.domain.BusinessCode;

public interface BusinessCodeService {
	/**
	 * 영업 형태 조회 카운트
	 * @param businessCode
	 * @return
	 */
	public int getBusinessCodeCount(BusinessCode businessCode);
	
	/**
	 * 영업 형태 조회 목록
	 * @param businessCode
	 * @return
	 */
	public List<BusinessCode> getBusinessCodeList(BusinessCode businessCode);
	
	/**
	 * 영업 형태 등록
	 * @param businessCode
	 */
	public void insertBusinessCode(BusinessCode businessCode);
	
	/**
	 * 영업 형태 수정
	 * @param businessCode
	 */
	public void updateBusinessCode(BusinessCode businessCode);
	
	/**
	 * 영업 형태 삭제
	 * @param businessCode
	 */
	public void deleteBusinessCode(int businessCodeId);
	
	/**
	 * 영업 형태 조회 목록
	 * @param businessCode
	 * @return
	 */
	public List<BusinessCode> getBusinessCodeListAll(BusinessCode businessCode);
}
