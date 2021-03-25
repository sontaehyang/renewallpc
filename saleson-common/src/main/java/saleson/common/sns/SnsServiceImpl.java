package saleson.common.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.common.sns.domain.SnsCount;
import saleson.common.sns.domain.SnsShare;

@Service("snsService")
public class SnsServiceImpl implements SnsService{
	
	@Autowired
	SnsMapper snsMapper;

	@Override
	public int getSnsShareCount(SnsShare snsShare) {
		return snsMapper.getSnsShareCount(snsShare);
	}

	@Override
	public void insertSnsShare(SnsShare snsShare) {
		snsMapper.insertSnsShare(snsShare);
	}

	@Override
	public void updateSnsShare(SnsShare snsShare) {
		snsMapper.updateSnsShare(snsShare);
	}

	@Override
	public List<SnsCount> getSnsShareCountBySnsRefCode(SnsShare snsShare) {
		return snsMapper.getSnsShareCountBySnsRefCode(snsShare);
	}

}
