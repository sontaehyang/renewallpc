package saleson.common.sns;

import java.util.List;

import saleson.common.sns.domain.SnsCount;
import saleson.common.sns.domain.SnsShare;

public interface SnsService {
	/**
	 * sns 공유 카운트 중복 조회
	 * @param snsShare
	 * @return
	 */
	public int getSnsShareCount(SnsShare snsShare);
	
	/**
	 * sns 공유 카운트 등록
	 * @param snsShare
	 */
	public void insertSnsShare(SnsShare snsShare);
	
	/**
	 * sns 공유 카운트 업데이트 
	 * @param snsShare
	 */
	public void updateSnsShare(SnsShare snsShare);
	
	/**
	 * sns 공유 카운트 조회 
	 * @param snsShare
	 * @return
	 */
	public List<SnsCount> getSnsShareCountBySnsRefCode(SnsShare snsShare);
}
