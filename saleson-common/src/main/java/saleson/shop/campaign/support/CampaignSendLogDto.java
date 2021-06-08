package saleson.shop.campaign.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.web.Param;
import saleson.model.campaign.QCampaignSendLog;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CampaignSendLogDto extends Param {

    private String campaignKey;

    private List<Long> msgkeys;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignSendLog campaignSendLog = QCampaignSendLog.campaignSendLog;

        if (!StringUtils.isEmpty(getCampaignKey())) {
            builder.and(campaignSendLog.campaignKey.eq(getCampaignKey()));
        }

        if (getMsgkeys() != null && !getMsgkeys().isEmpty()) {
            builder.and(campaignSendLog.msgkey.in(getMsgkeys()));
        }

        return builder;
    }
}
