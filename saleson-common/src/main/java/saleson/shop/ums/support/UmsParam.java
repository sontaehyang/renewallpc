package saleson.shop.ums.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.web.Param;
import saleson.model.QUms;
import saleson.model.UmsDetail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UmsParam extends Param {

    private static final Logger log = LoggerFactory.getLogger(UmsParam.class);

    // 발송 항목 - 기획이 정확히 정해지지 않음 (추가 예정)
    // 발송 시기 - 기획이 정확히 정해지지 않음 (추가 예정)
    private String usedFlag;          // 사용 여부
    private String umsType;        // 발송 타입
    private String[] umsTypes;        // 발송 타입
    private String nightSendFlag;     // 야간 발송 여부
    private UmsDetail umsDetail;
    private String templateName;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QUms ums = QUms.ums;

        if (!StringUtils.isEmpty(getQuery())) {
            log.debug("ums.templateName:  {}", ums.templateName);
            if ("templateName".equalsIgnoreCase(getWhere())) {
                builder.and(
                        ums.templateName.contains(getQuery())
                );
            }
        }

        if (!StringUtils.isEmpty(getUsedFlag())) {
            log.debug("ums.usedFlag:  {}", ums.usedFlag);
            builder.and(
                    ums.usedFlag.eq("Y".equals(getUsedFlag()) ? true : false)
            );

        }

        if (!StringUtils.isEmpty(getNightSendFlag())) {
            log.debug("ums.nightSendFlag:  {}", ums.nightSendFlag);
            builder.and(
                    ums.nightSendFlag.eq("Y".equals(getNightSendFlag()) ? true : false)
            );
        }

        return builder;
    }

}
