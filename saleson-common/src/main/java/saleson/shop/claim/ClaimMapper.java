package saleson.shop.claim;

import java.util.List;

import saleson.shop.claim.domain.ClaimMemo;
import saleson.shop.claim.support.ClaimMemoParam;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("claimMapper")
public interface ClaimMapper {
	
	/**
	 * 조건에 해당하는 데이터 수
	 * @param param
	 * @return
	 */
	int getClaimMemoCount(ClaimMemoParam param);

	/**
	 * 조건에 해당하는 데이터 목록
	 * @param param
	 * @return
	 */
	List<ClaimMemo> getClaimMemoList(ClaimMemoParam param);
	
	/**
	 * 조건에 해당하는 데이터 조회
	 * @param claimMemoId
	 * @return
	 */
	ClaimMemo getClaimMemoId(int claimMemoId);
	
	/**
	 * ID로 데이터 조회
	 * @param claimMemoId
	 * @return
	 */
	ClaimMemo getClaimMemoById(int claimMemoId);
	
	/**
	 * 데이터 등록
	 * @param claimMemo
	 */
	void insertClaimMemo(ClaimMemo claimMemo);
	
	/**
	 * 데이터 수정
	 * @param claimMemo
	 */
	void updateClaimMemo(ClaimMemo claimMemo);
	
	/**
	 * ID로 데이터 삭제
	 * @param claimMemoId
	 */
	void deleteClaimMemoById(int claimMemoId);
	
}
