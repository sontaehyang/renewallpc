package saleson.shop.ums;

import com.onlinepowers.framework.exception.UserException;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.common.enumeration.UmsType;
import saleson.model.Ums;
import saleson.model.UmsDetail;
import saleson.shop.ums.support.*;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service("umsService")
public class UmsServiceImpl implements UmsService {

    private static final Logger logger = LoggerFactory.getLogger(UmsServiceImpl.class);

    @Autowired
    private UmsRepository umsRepository;

    @Override
    public Ums getUms(String templateCode) {

        Ums ums = new Ums();
        ums.setTemplateCode(templateCode);
        Optional<Ums> optional = umsRepository.findOne(ums.getPredicate());

        if (optional.isPresent()) {
            return optional.get();
        }

        return new Ums();
    }

    @Override
    public HashMap<String, String> getUmsChangeCodes(String templateCode) {
        HashMap<String, String> codeMap = new HashMap<>();

        if ("order_deposit_wait".equals(templateCode)) {
            codeMap = new OrderBank().getCodeViewMap();
        } else if ("order_cready_payment".equals(templateCode)) {
            codeMap = new OrderNew().getCodeViewMap();
        } else if ("order_delivering".equals(templateCode)) {
            codeMap = new OrderDelivering().getCodeViewMap();
        } else if ("pwsearch".equals(templateCode)) {
            codeMap = new Pwsearch().getCodeViewMap();
        } else if ("member_join".equals(templateCode)) {
            codeMap = new MemberJoin().getCodeViewMap();
        } else if ("qna_complete".equals(templateCode)) {
            codeMap = new QnaComplete().getCodeViewMap();
        } else if ("item_restock".equals(templateCode)) {
            codeMap = new ItemRestock().getCodeViewMap();
        } else if ("user_sms".equals(templateCode)) {
            codeMap = new UserSms().getCodeViewMap();
        } else if ("confirm_purchase".equals(templateCode)) {
            codeMap = new ConfirmPurchase().getCodeViewMap();
        } else if ("confirm_purchase_request".equals(templateCode)) {
            codeMap = new ConfirmPurchaseRequest().getCodeViewMap();
        } else if ("expiration_coupon".equals(templateCode)) {
            codeMap = new ExpirationCoupon().getCodeViewMap();
        } else if ("birthday_coupon".equals(templateCode)) {
            codeMap = new BirthdayCoupon().getCodeViewMap();
        } else if ("order_refund".equals(templateCode)) {
            codeMap = new OrderRefundApproval().getCodeViewMap();
        } else if ("expiration_point".equals(templateCode)) {
            codeMap = new ExpirationPoint().getCodeViewMap();
        }

        return codeMap;
    }

    @Override
    public boolean isValidUms(Ums ums) {

        if (ums == null) {
            return false;
        }

        // 사용 여부 및 야간 발송 체크 (21시 ~ 6시 제한)
        LocalTime startTime = LocalTime.of(20, 59, 59);
        LocalTime endTime = LocalTime.of(6, 0, 1);

        boolean isNightSendFlag = (ums.isNightSendFlag() && !(LocalTime.now().isAfter(startTime) || LocalTime.now().isBefore(endTime))) || !ums.isNightSendFlag();

        return ums.isUsedFlag() && isNightSendFlag;
    }

    @Override
    public Optional<Ums> findById(Long id) {
        return umsRepository.findById(id);
    }

    @Override
    public Page<Ums> findAll(Predicate predicate, Pageable pageable) {
        return umsRepository.findAll(predicate, pageable);
    }

    @Override
    public void saveUms(Ums ums) {

        umsRepository.save(ums);
    }


    @Override
    public void updateUms(Ums ums) {
        Ums storedUms = umsRepository.findById(ums.getId())
                .orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/ums/list"));

        ums.setCreated(storedUms.getCreated());
        ums.setCreatedBy(storedUms.getCreatedBy());
        ums.setVersion(storedUms.getVersion());

        ums.getDetailList().forEach(
                updateData -> {

                    if (updateData.getId() == null) {
                        updateData.setTemplateCode(storedUms.getTemplateCode());
                        setUmsDetail(updateData);
                    } else {
                        storedUms.getDetailList().forEach(
                                originalData -> {
                                    setUmsDetail(originalData, updateData);
                                }
                        );
                    }
                }
        );

        umsRepository.save(ums);
    }


    /**
     * UMS 상세 업데이트 정보 셋팅
     *
     * @param updateData
     */
    private void setUmsDetail(UmsDetail updateData) {
        // 알림톡 템플릿
        if (updateData.getUmsType() == UmsType.ALIM_TALK) {

        } else {

        }
    }

    /**
     * UMS 상세 업데이트 정보 셋팅
     *
     * @param originalData
     * @param updateData
     */
    private void setUmsDetail(UmsDetail originalData, UmsDetail updateData) {

        if (originalData.getId().equals(updateData.getId())) {

            updateData.setId(originalData.getId());
            updateData.setCreated(originalData.getCreated());
            updateData.setCreatedBy(originalData.getCreatedBy());
            updateData.setVersion(originalData.getVersion());

            updateData.setUmsType(originalData.getUmsType());

            // 알림톡은 기존 정보 유지
            if (updateData.getUmsType() == UmsType.ALIM_TALK) {
                updateData.setAlimTalkButtons(originalData.getAlimTalkButtons());
                updateData.setApplyCode(originalData.getApplyCode());
                updateData.setTitle(originalData.getTitle());
                updateData.setMessage(originalData.getMessage());
            }
        }
    }


    private <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return list.stream()
                .collect(Collectors.groupingBy(element -> counter.getAndIncrement() / size))
                .values();
    }
}
