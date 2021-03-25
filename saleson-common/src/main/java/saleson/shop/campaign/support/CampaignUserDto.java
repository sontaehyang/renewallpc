package saleson.shop.campaign.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.web.Param;
import saleson.model.campaign.QCampaignUser;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CampaignUserDto extends Param {

    // 캠페인 ID
    private Long campaignId;

    // 회원 ID
    private Long userId;

    private String receiveSms;

    private String receivePush;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignUser campaignUser = QCampaignUser.campaignUser;

        if (!StringUtils.isEmpty(campaignId)) {
            builder.and(campaignUser.pk.campaignId.eq(campaignId));
        }

        if (!StringUtils.isEmpty(receiveSms)) {
            builder.and(campaignUser.receiveSms.eq(receiveSms).and(campaignUser.receivePush.eq(receivePush)));
        }

        if (!StringUtils.isEmpty(receivePush)) {
            builder.and(campaignUser.receivePush.eq(receivePush));
        }

        if (!StringUtils.isEmpty(userId)) {
            builder.and(campaignUser.pk.userId.eq(userId));
        }

        return builder;
    }
}

