package saleson.common.sns;

import java.util.List;

import saleson.common.sns.domain.SnsCount;
import saleson.common.sns.domain.SnsShare;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("snsMapper")
public interface SnsMapper {
	/**
	 * sns 공유 카운트 중복 조회
	 * @param snsShare
	 * @return
	 */
	int getSnsShareCount(SnsShare snsShare);
	
	/**
	 * sns 공유 카운트 등록
	 * @param snsShare
	 */
	void insertSnsShare(SnsShare snsShare);
	
	/**
	 * sns 공유 카운트 업데이트 
	 * @param snsShare
	 */
	void updateSnsShare(SnsShare snsShare);
	
	/**
	 * sns 공유 카운트 조회 
	 * @param snsShare
	 * @return
	 */
	List<SnsCount> getSnsShareCountBySnsRefCode(SnsShare snsShare);
}
