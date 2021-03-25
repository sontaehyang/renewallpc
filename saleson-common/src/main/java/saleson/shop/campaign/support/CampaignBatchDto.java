package saleson.shop.campaign.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.Const;
import saleson.common.web.Param;
import saleson.model.campaign.Campaign;
import saleson.model.campaign.CampaignRegular;
import saleson.model.campaign.QCampaignBatch;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CampaignBatchDto extends Param {

    // 회원 그룹 코드
    private String groupCode;

    // 회원 등급 ID
    private int levelId = -1;

    // SMS 수신여부
    private String receiveSms;

    // 푸시 수신여부
    private String receivePush;

    // 가입이후 총 구매액
    private int startOrderAmount1;
    private int endOrderAmount1;

    // 최근 3개월간 총 구매액
    private int startOrderAmount2;
    private int endOrderAmount2;

    // 최종 로그인 일자
    private String lastLoginDateType = "0";

    // 장바구니 총액
    private int startCartAmount;

    // 장바구니 총액
    private int endCartAmount;

    private int cartCount;

    private String lastLoginDate;

    private String sendType;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignBatch campaignBatch = QCampaignBatch.campaignBatch;

        if (!StringUtils.isEmpty(getQuery())) {

            if ("loginId".equalsIgnoreCase(getWhere())) {
                builder.and(campaignBatch.loginId.like("%" + getQuery() + "%"));
            } else if ("userName".equalsIgnoreCase(getWhere())) {
                builder.and(campaignBatch.userName.like("%" + getQuery() + "%"));
            }

        }

        if (!StringUtils.isEmpty(getGroupCode())) {
            builder.and(campaignBatch.groupCode.eq(groupCode));
        }

        if (getLevelId() >= 0) {
            builder.and(campaignBatch.levelId.eq(levelId));
        }

        if (getStartOrderAmount1() > 0) {
            builder.and(campaignBatch.orderAmount1.goe(getStartOrderAmount1()));
        }

        if (getEndOrderAmount1() > 0) {
            builder.and(campaignBatch.orderAmount1.loe(getEndOrderAmount1()));
        }

        if (getStartOrderAmount2() > 0) {
            builder.and(campaignBatch.orderAmount2.goe(getStartOrderAmount2()));
        }

        if (getEndOrderAmount2() > 0) {
            builder.and(campaignBatch.orderAmount2.loe(getEndOrderAmount2()));
        }

        if (getStartCartAmount() > 0) {
            builder.and(campaignBatch.cartAmount.goe(getStartCartAmount()));
        }

        if (getEndCartAmount() > 0) {
            builder.and(campaignBatch.cartAmount.loe(getEndCartAmount()));
        }

        if (getCartCount() > 0) {
            builder.and(campaignBatch.cartCount.goe(getCartCount()));
        }

        String today = DateUtils.getToday(Const.DATE_FORMAT);
        String lastLoginDate = "";
        String lastLoginDateType = getLastLoginDateType();
        switch (lastLoginDateType) {
            case "1" : lastLoginDate = DateUtils.addMonth(today, -1); break;
            case "2" : lastLoginDate = DateUtils.addMonth(today, -2); break;
            case "3" : lastLoginDate = DateUtils.addMonth(today, -3); break;
            case "6" : lastLoginDate = DateUtils.addMonth(today, -6); break;
            case "24" : lastLoginDate = DateUtils.addYear(today, -1); break;
            default: lastLoginDate = "";
        }
        if (!StringUtils.isEmpty(lastLoginDate)) {
            builder.and(campaignBatch.lastLoginDate.loe(lastLoginDate+"235959"));
            setLastLoginDate(lastLoginDate);
        }

        if (!StringUtils.isEmpty(sendType)) {
            builder.andAnyOf(
                campaignBatch.receiveSms.eq(receiveSms),
                campaignBatch.receivePush.eq(receivePush)
            );

        } else {
            if (!StringUtils.isEmpty(receiveSms)) {
                builder.and(campaignBatch.receiveSms.eq(receiveSms));
            }

            if (!StringUtils.isEmpty(receivePush)) {
                builder.and(campaignBatch.receivePush.eq(receivePush));
            }
        }

        return builder;
    }

    public void setCampaignBatchDto(Campaign campaign) {
        setWhere(campaign.getSearchWhere());
        setQuery(campaign.getQuery());
        setGroupCode(campaign.getGroupCode());
        setLevelId(campaign.getLevelId());
        setCartCount(campaign.getCartCount());
        setStartCartAmount(campaign.getStartCartAmount());
        setEndCartAmount(campaign.getEndCartAmount());
        setStartOrderAmount1(campaign.getStartOrderAmount1());
        setEndOrderAmount1(campaign.getEndOrderAmount1());
        setStartOrderAmount2(campaign.getStartOrderAmount2());
        setEndOrderAmount2(campaign.getEndOrderAmount2());
        setLastLoginDateType(campaign.getLastLoginDateType());
    }

    public void setCampaignBatchDto(CampaignRegular campaignRegular) {
        setWhere(campaignRegular.getSearchWhere());
        setQuery(campaignRegular.getQuery());
        setGroupCode(campaignRegular.getGroupCode());
        setLevelId(campaignRegular.getLevelId());
        setCartCount(campaignRegular.getCartCount());
        setStartCartAmount(campaignRegular.getStartCartAmount());
        setEndCartAmount(campaignRegular.getEndCartAmount());
        setStartOrderAmount1(campaignRegular.getStartOrderAmount1());
        setEndOrderAmount1(campaignRegular.getEndOrderAmount1());
        setStartOrderAmount2(campaignRegular.getStartOrderAmount2());
        setEndOrderAmount2(campaignRegular.getEndOrderAmount2());
        setLastLoginDateType(campaignRegular.getLastLoginDateType());
    }
}

