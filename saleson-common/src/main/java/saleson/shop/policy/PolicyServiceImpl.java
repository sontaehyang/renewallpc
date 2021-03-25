package saleson.shop.policy;

import com.onlinepowers.framework.sequence.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.utils.UserUtils;
import saleson.shop.policy.domain.Policy;
import saleson.shop.policy.support.PolicyParam;

import java.util.List;

@Service("policyService")
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public void insertPolicy(Policy policy) {

        policy.setPolicyId(sequenceService.getId("OP_POLICY"));

        policyMapper.insertPolicy(policy);
    }

    @Override
    public List<Policy> getPolicyListByParam(PolicyParam policyParam) {
        return policyMapper.getPolicyListByParam(policyParam);
    }

    @Override
    public Policy getPolicyByParam(int policyId, String policyType) {
        PolicyParam policyParam = new PolicyParam();

        policyParam.setPolicyId(policyId);
        policyParam.setPolicyType(policyType);

        return policyMapper.getPolicyByParam(policyParam);
    }

    @Override
    public Policy getCurrentPolicyByType(String policyType) {
        return policyMapper.getCurrentPolicyByType(policyType);
    }

    @Override
    public Policy getPolicyByPolicyId(int policyId) {
        return policyMapper.getPolicyByPolicyId(policyId);
    }

    @Override
    public void updatePolicy(Policy policy) {
        policy.setUpdatedLoginId(UserUtils.getLoginId());

        policyMapper.updatePolicy(policy);
    }

    @Override
    public void createPolicy(Policy policy) {
        policy.setPolicyId(sequenceService.getId("OP_POLICY"));
        policy.setCreatedUserId(UserUtils.getManagerId());

        policyMapper.createPolicy(policy);
    }

    @Override
    public int getCountPolicyListByParam(PolicyParam policyParam) {
        return policyMapper.getCountPolicyListByParam(policyParam);
    }

    @Override
    public List<Policy> getPeriodListByParam(PolicyParam policyParam) {
        return policyMapper.getPeriodListByParam(policyParam);
    }
}
