package saleson.common.approval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.common.approval.domain.Approval;

@Service("approvalService")
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	ApprovalMapper approvalMapper;
	
	@Override
	public void insertApproval(Approval approval) {
		approvalMapper.insertApproval(approval);
	}

	@Override
	public Approval getLastApproval(Approval approval) {
		return approvalMapper.getLastApproval(approval);
	}

}
