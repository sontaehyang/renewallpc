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
import saleson.common.utils.ShopUtils;
import saleson.common.web.Param;
import saleson.model.campaign.QCampaignRegular;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CampaignRegularDto extends Param {

    // 정기발송 ID
    private Long id;

    private List<Long> ids;

    // 정기발송 제목
    private String title;

    // 정기발송 문구
    private String content;

    // 메시지유형 (0:일반, 1:광고)
    private String messageType;

    // 정기발송수단 (0:SMS, 1:PUSH)
    private String sendType;

    private boolean sendFlag;
    private String batchDateTime;

    private String where;
    private String query;

    private String startSendDate;
    private String endSendDate;

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

    // 발송 처리 할 데이터 조회
    public Predicate getSendPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignRegular campaignRegular = QCampaignRegular.campaignRegular;

        String today = DateUtils.getToday(Const.DATEHOUR_FORMAT) + "0000";
        String date = DateUtils.getToday("dd");
        String time = DateUtils.getToday("HH");
        int day = ShopUtils.getLocalDate(DateUtils.getToday("yyyyMMdd")).getDayOfWeek().getValue();

        builder.and(campaignRegular.startSendDate.loe(today));
        builder.and(campaignRegular.endSendDate.goe(today));
        builder.and(campaignRegular.sendTime.eq(time));

        builder.andAnyOf(
            campaignRegular.status.eq("0"),
            campaignRegular.status.eq("1")
        );

        builder.andAnyOf(
                campaignRegular.sendCycle.eq("month")
                        .and(campaignRegular.sendDate.eq(date)),
                campaignRegular.sendCycle.eq("week")
                        .and(campaignRegular.sendDay.eq(Integer.toString(day))),
                campaignRegular.sendCycle.eq("daily")
        );

        return builder;
    }

    // 기간이 만료된 데이터 조회
    public Predicate getExpireSendPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignRegular campaignRegular = QCampaignRegular.campaignRegular;

        String today = DateUtils.getToday(Const.DATEHOUR_FORMAT) + "0000";
        builder.and(campaignRegular.endSendDate.lt(today));
        builder.and(campaignRegular.status.eq("1"));

        return builder;
    }

    // 정기발송 내역 조회
    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QCampaignRegular campaignRegular = QCampaignRegular.campaignRegular;

        if (!StringUtils.isEmpty(getTitle())) {
            builder.and(campaignRegular.title.like("%" + title + "%"));
        }

        if (!StringUtils.isEmpty(getMessageType())) {
            builder.and(campaignRegular.messageType.eq(messageType));
        }

        if (!StringUtils.isEmpty(getSendType())) {
            builder.and(campaignRegular.sendType.eq(sendType));
        }

        if (!StringUtils.isEmpty(getGroupCode())) {
            builder.and(campaignRegular.groupCode.eq(groupCode));
        }

        if (getLevelId() >= 0) {
            builder.and(campaignRegular.levelId.eq(levelId));
        }

        if (getStartOrderAmount1() > 0) {
            builder.and(campaignRegular.startOrderAmount1.goe(getStartOrderAmount1()));
        }

        if (getEndOrderAmount1() > 0) {
            builder.and(campaignRegular.endOrderAmount1.loe(getEndOrderAmount1()));
        }

        if (getStartOrderAmount2() > 0) {
            builder.and(campaignRegular.startOrderAmount2.goe(getStartOrderAmount2()));
        }

        if (getEndOrderAmount2() > 0) {
            builder.and(campaignRegular.endOrderAmount2.loe(getEndOrderAmount2()));
        }

        if (getStartCartAmount() > 0) {
            builder.and(campaignRegular.startCartAmount.goe(getStartCartAmount()));
        }

        if (getEndCartAmount() > 0) {
            builder.and(campaignRegular.endCartAmount.loe(getEndCartAmount()));
        }

        if (getCartCount() > 0) {
            builder.and(campaignRegular.cartCount.goe(getCartCount()));
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
            builder.and(campaignRegular.lastLoginDate.isNotNull());
            builder.and(campaignRegular.lastLoginDate.notIn(""));
            builder.and(campaignRegular.lastLoginDate.loe(lastLoginDate+"235959"));
        }

        if (!StringUtils.isEmpty(getStartSendDate())) {
            builder.and(campaignRegular.startSendDate.goe(getStartSendDate().substring(0, 8)));
        }

        if (!StringUtils.isEmpty(getEndSendDate())) {
            builder.and(campaignRegular.endSendDate.loe(getEndSendDate().substring(0, 8) + "235959"));
        }

        return builder;

    }
}

