package saleson.shop.receipt;

import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.Cashbill;
import saleson.model.CashbillIssue;
import saleson.shop.order.support.OrderParam;
import saleson.shop.receipt.support.CashbillResponse;

import java.util.List;

public interface ReceiptService {

    public Page<Cashbill> getAllCashbill(Predicate predicate, Pageable pageable);

    public Page<CashbillIssue> findCashbillIssueAll(Predicate predicate, Pageable pageable);

    public Page<Cashbill> findAll(Pageable pageable);

    /**
     * 현금영수증등록(사용자입력정보)
     * @param cashbill
     */
    public void insertCashbillInfo(Cashbill cashbill);

    public void deleteById(Long id);

    /**
     * 주문번호로 현금영수증 사용자 입력정보 조회
     * @param cashbill
     * @return
     */
    public List<Cashbill> getCashbill(Cashbill cashbill);

    public void updateCashbillForSuccess(Cashbill cashbill);

    public List<Cashbill> findAllByOrderCode(String orderCode);

    public List<CashbillIssue> findAllCashbillIssue(Predicate predicate);

    /**
     * 목록 데이터로 현금영수증 발급 (팝빌 연동)
     * @param listParam
     */
    void issueCashbillByListData(ListParam listParam);

    /**
     * 목록 데이터로 현금영수증 취소 (팝빌 연동)
     * @param listParam
     */
    void cancelCashbillByListData(ListParam listParam);

    /**
     * 현금영수증 발급기능 (팝빌연동)
     * @param cashbillIssue
     * @return
     */
    public CashbillResponse receiptIssue(CashbillIssue cashbillIssue);

    /**
     * 현금영수증 상세데이터 저장
     * @param cashbillIssue
     */
    public void insertCashbillIssue(CashbillIssue cashbillIssue);

    /**
     * 주문번호에 해당하는 현금영수증 취소
     */
    public CashbillResponse cancelCashbill(String orderCode);

    /**
     * 현금영수증 취소 및 재발급
     * @param orderParam
     */
    public void cashbillReIssue(OrderParam orderParam);
}
