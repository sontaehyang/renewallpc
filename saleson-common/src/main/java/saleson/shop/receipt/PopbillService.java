package saleson.shop.receipt;

import saleson.model.CashbillIssue;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

public interface PopbillService {

    /**
     * 현금영수증 즉시발행
     * @param cashbillParam
     * @return
     */
    public CashbillResponse cashbillRegistIssue(CashbillParam cashbillParam);

    /**
     * 현금영수증 상태/요약정보 확인
     * @param mgtKey
     * @return
     */
    public CashbillResponse getInfo(String mgtKey);

    /**
     * 현금영수증 발행취소
     * 발행취소는 국세청 전송전에만 가능
     * @param cashbillIssue
     * @return
     */
    public CashbillResponse cancelIssue(CashbillIssue cashbillIssue);

    /**
     * 취소현금영수증 즉시발행
     * 발행일 기준 오후5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청 전송결과 확인가능
     * @param cashbillIssue
     * @return
     */
    public CashbillResponse revokeRegistIssue(CashbillIssue cashbillIssue, String orgConfirmNum, String orgTradeDate);
}