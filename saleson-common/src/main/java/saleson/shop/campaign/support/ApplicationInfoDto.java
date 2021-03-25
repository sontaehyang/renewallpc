package saleson.shop.campaign.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.enumeration.DeviceType;
import saleson.common.web.Param;
import saleson.model.campaign.QApplicationInfo;
import saleson.model.campaign.QCampaignBatch;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApplicationInfoDto extends Param {

    private Long userId;

    private String loginId;

    private String userName;

    private String phoneNumber;

    private String pushToken;

    private String uuid;

    private DeviceType deviceType;

    private String applicationNo;

    private String applicationVersion;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignBatch campaignBatch = QCampaignBatch.campaignBatch;
        QApplicationInfo applicationInfo = QApplicationInfo.applicationInfo;

        if (!StringUtils.isEmpty(getQuery())) {

            if ("loginId".equalsIgnoreCase(getWhere())) {
                builder.and(campaignBatch.loginId.like("%" + getQuery() + "%"));
            } else if ("userName".equalsIgnoreCase(getWhere())) {
                builder.and(campaignBatch.userName.like("%" + getQuery() + "%"));
            }
        }

        if (!StringUtils.isEmpty(this.userId)) {
            builder.and(applicationInfo.userId.eq(getUserId()));
        }

        return builder;

    }
}

