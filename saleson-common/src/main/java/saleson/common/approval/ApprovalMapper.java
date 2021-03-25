package saleson.common.approval;

import saleson.common.approval.domain.Approval;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

@Mapper("approvalMapper")
public interface ApprovalMapper {
	public void insertApproval(Approval approval);

	
	/**
	 * 마지막 승인 정보를 가져옴.
	 * @param approval
	 * @return
	 */
	public Approval getLastApproval(Approval approval);
}
