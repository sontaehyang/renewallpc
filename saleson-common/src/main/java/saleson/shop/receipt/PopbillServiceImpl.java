package saleson.shop.receipt;

import com.popbill.api.CashbillService;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.cashbill.CashbillInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.CashbillStatusCode;
import saleson.common.enumeration.CashbillType;
import saleson.common.enumeration.TaxType;
import saleson.common.utils.ShopUtils;
import saleson.model.CashbillIssue;
import saleson.shop.config.domain.Config;
import saleson.shop.receipt.support.CashbillParam;
import saleson.shop.receipt.support.CashbillResponse;

@Service("popbillService")
public class PopbillServiceImpl implements PopbillService {

    @Autowired
    private CashbillService cashbillService;

	@Autowired
	Environment environment;


    @Override
    public CashbillResponse cashbillRegistIssue(CashbillParam param) {
        /**
         * 1건의 현금영수증을 즉시발행합니다.
         * - 발행일 기준 오후 5시 이전에 발행된 현금영수증은 다음날 오후 2시에 국세청
         *   전송결과를 확인할 수 있습니다.
         * - 현금영수증 국세청 전송 정책에 대한 정보는 "[현금영수증 API 연동매뉴얼]
         *   > 1.4. 국세청 전송정책"을 참조하시기 바랍니다.
         * - 취소현금영수증 작성방법 안내 - http://blog.linkhub.co.kr/702
         */

        CashbillResponse result = new CashbillResponse();

        result.setSuccess(false);
        result.setMgtKey(param.getMgtKey());

        Config config = ShopUtils.getConfig();

        // 메모
        String Memo = "";

        // 현금영수증 정보 객체
        com.popbill.api.cashbill.Cashbill cashbill = new com.popbill.api.cashbill.Cashbill();

        // 문서관리번호, 최대 24자리, 영문, 숫자 '-', '_'로 구성 임의로 등록. 중복허용 안됨
        cashbill.setMgtKey(param.getMgtKey());

        // 문서형태, {승인거래, 취소거래} 중 기재
        cashbill.setTradeType("승인거래");

        // 취소거래시 기재, 원본 현금영수증 국세청 승인번호 - getInfo API를 통해 confirmNum 값 기재
        cashbill.setOrgConfirmNum("");

        // 취소거래시 기재, 원본 현금영수증 거래일자 - getInfo API를 통해 tradeDate 값 기재
        cashbill.setOrgTradeDate("");

        // 과세형태, {과세, 비과세} 중 기재
        String taxType = "비과세";
        if (TaxType.CHARGE == param.getTaxType()) {
            taxType = "과세";
        }
        cashbill.setTaxationType(taxType);

        // 거래처 식별번호, 거래유형에 따라 작성
        // 소득공제용 - 주민등록/휴대폰/카드번호 기재가능
        // 지출증빙용 - 사업자번호/주민등록/휴대폰/카드번호 기재가능
        cashbill.setIdentityNum(param.getCashbillCode());

        // 거래구분, {소득공제용, 지출증빙용} 중 기재
        String cashbillType = "소득공제용";
        if (CashbillType.BUSINESS == param.getCashbillType()) {
            cashbillType = "지출증빙용";
        }
        cashbill.setTradeUsage(cashbillType);

        // 거래유형, {읿반, 도서공연, 대중교통} 중 기재
        cashbill.setTradeOpt("일반");

        long tax = (long)(param.getAmount() / 1.1 * 0.1);      // 부가세

        if ("비과세".equals(taxType)) {
            tax = 0;
        }

		long sup_price = param.getAmount() - tax;             // 공급가

        // 공급가액, 숫자만 가능
        cashbill.setSupplyCost(String.valueOf(sup_price));

        // 부가세, 숫자만 가능
        cashbill.setTax(String.valueOf(tax));

        // 봉사료, 숫자만 가능
        cashbill.setServiceFee("0");

        // 합계금액, 숫자만 가능, 공급가액 + 부가세 + 봉사료
        cashbill.setTotalAmount(String.valueOf(sup_price + tax + 0));


        // 발행자 사업자번호, '-'제외 10자리
        //cashbill.setFranchiseCorpNum(config.getCompanyNumber().replaceAll("-",""));
        cashbill.setFranchiseCorpNum(environment.getProperty("popbill.corp.num"));

        // 발행자 상호
        cashbill.setFranchiseCorpName(config.getShopName());

        // 발행자 대표자명
        cashbill.setFranchiseCEOName(config.getBossName());

        // 발행자 주소
        cashbill.setFranchiseAddr(config.getAddress() + " " + config.getAddressDetail());

        // 발행자 연락처
        cashbill.setFranchiseTEL(config.getTelNumber());

        // 발행안내 문자 전송여부
        cashbill.setSmssendYN(false);


        // 거래처 고객명
        cashbill.setCustomerName(param.getCustomerName());

        // 거래처 주문상품명
        cashbill.setItemName(param.getItemName());

        // 거래처 주문번호
        cashbill.setOrderNumber(param.getOrderCode());

        // 거래처 이메일
        cashbill.setEmail("");

        // 거래처 휴대폰
        cashbill.setHp("");

        if (CashbillType.PERSONAL == param.getCashbillType()) {
            cashbill.setHp(param.getCashbillCode());
        }

        try {
            Response response = cashbillService.registIssue(environment.getProperty("popbill.corp.num"), cashbill, Memo);

            result.setSuccess(true);
            result.setResponseCode(String.valueOf(response.getCode()));
            result.setResponseMessage(response.getMessage());
        } catch (PopbillException e) {
            result.setSuccess(false);
            result.setResponseCode(String.valueOf(e.getCode()));
            result.setResponseMessage(e.getMessage());
        }

        return result;
    }

    @Override
    public CashbillResponse cancelIssue(CashbillIssue cashbillIssue) {

        CashbillResponse result = new CashbillResponse();

        // 문서관리번호
        String mgtKey = cashbillIssue.getMgtKey();

        // 메모
        String memo = "";

        try {
            Response response = cashbillService.cancelIssue(environment.getProperty("popbill.corp.num"), mgtKey, memo);

            result.setResponseCode(String.valueOf(response.getCode()));
            result.setResponseMessage(response.getMessage());
            result.setSuccess(true);
        } catch (PopbillException e) {
            result.setResponseCode(String.valueOf(e.getCode()));
            result.setResponseMessage(e.getMessage());
            result.setSuccess(false);

            return result;
        }

        return result;
    }

    @Override
    public CashbillResponse revokeRegistIssue(CashbillIssue cashbillIssue, String orgConfirmNum, String orgTradeDate) {

        CashbillResponse result = new CashbillResponse();

        try {
			// 문서관리번호, 영문, 숫자, 하이픈(-), 언더바(_)를 조합하여 24자리 문자열로 사업자별로
			// 중복되지 않도록 구성
			String mgtKey = "C" + cashbillIssue.getMgtKey().substring(1);

            Response response = cashbillService.revokeRegistIssue(environment.getProperty("popbill.corp.num"), mgtKey, orgConfirmNum, orgTradeDate);

            result.setResponseCode(String.valueOf(response.getCode()));
            result.setResponseMessage(response.getMessage());
            result.setSuccess(true);
        } catch (PopbillException e) {
            result.setResponseCode(String.valueOf(e.getCode()));
            result.setResponseMessage(e.getMessage());
            result.setSuccess(false);

            return result;
        }

        return result;
    }

    @Override
    public CashbillResponse getInfo(String mgtKey) {
        /**
         * 1건의 현금영수증 상태/요약 정보를 확인합니다.
         * - 응답항목에 대한 자세한 정보는 "[현금영수증 API 연동매뉴얼] > 4.2.
         *   현금영수증 상태정보 구성"을 참조하시기 바랍니다.
         */
        CashbillResponse result = new CashbillResponse();

        try {
            CashbillInfo cashbillInfo = cashbillService.getInfo(environment.getProperty("popbill.corp.num"), mgtKey);

            int statusCode = cashbillInfo.getStateCode();

            switch (statusCode) {
                case 100 :
                    result.setStatusCode(CashbillStatusCode.TEMP);
                    break;
                case 300 :
                    result.setStatusCode(CashbillStatusCode.ISSUED);
                    break;
                case 304 :
                    result.setStatusCode(CashbillStatusCode.COMPLETED);
                    break;
                case 305 :
                    result.setStatusCode(CashbillStatusCode.CANCEL);
                    break;
                default :
                    result.setStatusCode(CashbillStatusCode.SENDING);
            }

            result.setOrgConfirmNum(cashbillInfo.getConfirmNum());
            result.setOrgTradeDate(cashbillInfo.getTradeDate());
            result.setSuccess(true);
        } catch (PopbillException e) {
            result.setResponseCode(String.valueOf(e.getCode()));
            result.setResponseMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }
}
