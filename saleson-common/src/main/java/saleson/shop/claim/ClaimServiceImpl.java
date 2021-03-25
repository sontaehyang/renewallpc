package saleson.shop.claim;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.common.utils.UserUtils;
import saleson.shop.claim.domain.ClaimMemo;
import saleson.shop.claim.support.ClaimMemoParam;

import com.onlinepowers.framework.sequence.service.SequenceService;

@Service("claimMemoService")
public class ClaimServiceImpl implements ClaimService {
	
	@Autowired
	private SequenceService sequenceService;
	
	@Autowired
	private ClaimMapper claimMapper;

	@Override
	public int getClaimMemoCount(ClaimMemoParam param) {
		return claimMapper.getClaimMemoCount(param);
	}

	@Override
	public List<ClaimMemo> getClaimMemoList(ClaimMemoParam param) {
		return claimMapper.getClaimMemoList(param);
	}

	@Override
	public ClaimMemo getClaimMemoById(int claimMemoId) {
		return claimMapper.getClaimMemoById(claimMemoId);
	}

	@Override
	public void insertClaimMemo(ClaimMemo claimMemo) {
		
		claimMemo.setClaimMemoId(sequenceService.getId("OP_CLAIM_MEMO"));
		claimMemo.setManagerUserId(UserUtils.getUser().getUserId());
		claimMemo.setManagerLoginId(UserUtils.getUser().getLoginId());
		claimMemo.setDataStatusCode("1");
		
		claimMapper.insertClaimMemo(claimMemo);
	}

	@Override
	public void updateClaimMemo(ClaimMemo claimMemo) {
		claimMemo.setManagerUserId(UserUtils.getUser().getUserId());
		claimMemo.setManagerLoginId(UserUtils.getUser().getLoginId());
		claimMapper.updateClaimMemo(claimMemo);
	}

	@Override
	public void deleteClaimMemoById(int claimMemoId) {
		claimMapper.deleteClaimMemoById(claimMemoId);
	}
}
