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
import saleson.model.campaign.QCampaign;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CampaignDto extends Param {

    // 캠페인 ID
    private Long id;

    private List<Long> ids;

    // 캠페인 제목
    private String title;

    // 캠페인 문구
    private String content;

    // 메시지유형 (0:일반, 1:광고)
    private String messageType;

    // 발송수단 (0:SMS, 1:PUSH)
    private String sendType;

    private String autoMonth;

    private String batchDateTime;

    private String where;
    private String query;

    private String startSentDate;
    private String endSentDate;

    private String groupCode;

    private int levelId = -1;

    private int startOrderAmount1;
    private int endOrderAmount1;

    private int startOrderAmount2;
    private int endOrderAmount2;

    private int startCartAmount;

    private int endCartAmount;

    private int cartCount;

    private String lastLoginDate;
    private String lastLoginDateType = "0";

    // 예약발송 배치 데이터 조회
    public Predicate getSendPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaign campaign = QCampaign.campaign;

        String date = DateUtils.getToday(Const.DATEHOUR_FORMAT) + "0000";

        if (!StringUtils.isEmpty(getBatchDateTime())) {
            date = getBatchDateTime();
        }

        builder.and(campaign.sendDate.eq(date));
        builder.and(campaign.batchFlag.eq(false));
        builder.and(campaign.status.eq("0"));
        return builder;
    }

    // 발송 메시지 불러오기
    public Predicate getSentCampaignPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaign campaign = QCampaign.campaign;

        builder.andAnyOf(
                campaign.status.eq("0"),
                campaign.status.eq("1")
                );

        if (!StringUtils.isEmpty(getQuery())) {

            if ("title".equalsIgnoreCase(getWhere())) {
                builder.and(campaign.title.like("%" + getQuery() + "%"));
            } else if ("content".equalsIgnoreCase(getWhere())) {
                builder.and(campaign.content.like("%" + getQuery() + "%"));
            }
        }

        return builder;
    }

    // 발송 내역 조회
    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaign campaign = QCampaign.campaign;

        if (!StringUtils.isEmpty(getTitle())) {
            builder.and(campaign.title.like("%" + title + "%"));
        }

        if (!StringUtils.isEmpty(getMessageType())) {
            builder.and(campaign.messageType.eq(messageType));
        }

        if (!StringUtils.isEmpty(getSendType())) {
            builder.and(campaign.sendType.eq(sendType));
        }

        if (!StringUtils.isEmpty(getAutoMonth())) {
            builder.and(campaign.autoMonth.eq(autoMonth));
        }

        if (!StringUtils.isEmpty(getGroupCode())) {
            builder.and(campaign.groupCode.eq(groupCode));
        }

        if (getLevelId() >= 0) {
            builder.and(campaign.levelId.eq(levelId));
        }

        if (getStartOrderAmount1() > 0) {
            builder.and(campaign.startOrderAmount1.goe(getStartOrderAmount1()));
        }

        if (getEndOrderAmount1() > 0) {
            builder.and(campaign.endOrderAmount1.loe(getEndOrderAmount1()));
        }

        if (getStartOrderAmount2() > 0) {
            builder.and(campaign.startOrderAmount2.goe(getStartOrderAmount2()));
        }

        if (getEndOrderAmount2() > 0) {
            builder.and(campaign.endOrderAmount2.loe(getEndOrderAmount2()));
        }

        if (getStartCartAmount() > 0) {
            builder.and(campaign.startCartAmount.goe(getStartCartAmount()));
        }

        if (getEndCartAmount() > 0) {
            builder.and(campaign.endCartAmount.loe(getEndCartAmount()));
        }

        if (getCartCount() > 0) {
            builder.and(campaign.cartCount.goe(getCartCount()));
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
            builder.and(campaign.lastLoginDate.isNotNull());
            builder.and(campaign.lastLoginDate.notIn(""));
            builder.and(campaign.lastLoginDate.loe(lastLoginDate+"235959"));
        }

        if (!StringUtils.isEmpty(getStartSentDate())) {
            builder.andAnyOf(
                    campaign.sendDate.goe(getStartSentDate().substring(0, 8)),
                    campaign.sentDate.goe(getStartSentDate().substring(0, 8))
            );
        }

        if (!StringUtils.isEmpty(getEndSentDate())) {
            builder.andAnyOf(
                    campaign.sendDate.loe(getEndSentDate().substring(0, 8) + "235959"),
                    campaign.sentDate.loe(getEndSentDate().substring(0, 8) + "235959")
            );
        }

        return builder;

    }
}

