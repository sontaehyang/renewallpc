package saleson.shop.ums.support;


import com.onlinepowers.framework.util.DateUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import saleson.common.web.Param;
import saleson.model.QUmsSendLog;
import saleson.shop.sendsmslog.support.SendSmsLogParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UmsSendLogDto extends Param {

    private static final Logger log = LoggerFactory.getLogger(UmsSendLogDto.class);

    private String created;
    private String templateCode;
    private String message;

    public Predicate getPredicate(SendSmsLogParam param) {
        BooleanBuilder builder = (BooleanBuilder) getPredicateForInit(param);
        QUmsSendLog umsConfig = QUmsSendLog.umsSendLog;

        // 발송유형 검색 조건
        if (!StringUtils.isEmpty(param.getSendType())){
            builder.andAnyOf(umsConfig.templateCode.eq(param.getSendType()),umsConfig.sendType.eq("hpmail-"+param.getSendType()));
        }
        // 작성일 검색조건(시작)
        if (!StringUtils.isEmpty(param.getSearchStartDate())) {
            builder.and(umsConfig.created.goe(LocalDateTime.parse(DateUtils.datetime(param.getSearchStartDate()+"000000"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        // 작성일 검색조건(끝)
        if (!StringUtils.isEmpty(param.getSearchEndDate())) {
            builder.and(umsConfig.created.loe(LocalDateTime.parse(DateUtils.datetime(param.getSearchEndDate()+"235959"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }

        return builder;
    }

    public Predicate getPredicateForInit(SendSmsLogParam param) {
        BooleanBuilder builder = new BooleanBuilder();
        QUmsSendLog umsConfig = QUmsSendLog.umsSendLog;

        if (!StringUtils.isEmpty(param.getUserId())) {
            builder.and(umsConfig.createdBy.eq(param.getUserId()));
        }

        return builder;
    }
}
