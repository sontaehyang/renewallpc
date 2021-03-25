package saleson.shop.policy;

import saleson.shop.policy.domain.Policy;
import saleson.shop.policy.support.PolicyParam;

import java.util.List;

public interface PolicyService {

    /**
     * 약관등록
     * @param policy
     */
    void insertPolicy(Policy policy);

    /**
     * 약관 목록 조회
     * @param policyParam
     * @return
     */
    List<Policy> getPolicyListByParam(PolicyParam policyParam);

    /**
     * 약관 목록 카운트 조회
     * @param policyParam
     * @return
     */
    int getCountPolicyListByParam(PolicyParam policyParam);

    /**
     * 약관 조회
     * @param policyId
     * @param policyType
     * @return
     */
    Policy getPolicyByParam(int policyId, String policyType);

    /**
     * 최신 약관 조회
     * @param policyType
     * @return
     */
    Policy getCurrentPolicyByType(String policyType);

    /**
     * policy detail page
     * @param policyId
     * @return
     */
    Policy getPolicyByPolicyId(int policyId);

    /**
     * 약관 수정
     * @param policy
     */
    void updatePolicy(Policy policy);

    /**
     * 약관 등록
     * @param policy
     */
    void createPolicy(Policy policy);

    /**
     * policy 기간
     * @param policyParam
     * @return
     */
    List<Policy> getPeriodListByParam(PolicyParam policyParam);
}
