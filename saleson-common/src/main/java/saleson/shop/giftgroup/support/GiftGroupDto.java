package saleson.shop.giftgroup.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.enumeration.DataStatus;
import saleson.common.enumeration.GiftGroupType;
import saleson.common.enumeration.ProcessType;
import saleson.common.web.Param;
import saleson.model.GiftGroupItem;
import saleson.model.QGiftGroup;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftGroupDto extends Param {

    private static final Logger log = LoggerFactory.getLogger(GiftGroupDto.class);

	private Long id;
	private String name;
	//@Enumerated(EnumType.STRING)
	private GiftGroupType groupType;
	private Integer overOrderPrice;
	private LocalDateTime validStartDate;
	private LocalDateTime validEndDate;
	private DataStatus dataStatus;
	private List<GiftGroupItem> itemList = new ArrayList<>();
	private String[] giftItemIds;
	private String startDate;
	private String endDate;


    private String processType;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QGiftGroup giftGroup = QGiftGroup.giftGroup;

        // 데이터 상태가 정상인 경우만
        builder.and(giftGroup.dataStatus.eq(DataStatus.NORMAL));

        if (!StringUtils.isEmpty(getQuery())) {
            if ("name".equalsIgnoreCase(getWhere())) {
                builder.and(
                        giftGroup.name.contains(getQuery())
                );
            }
        }

        GiftGroupType groupType = getGroupType();
        if (groupType != null) {
            builder.and(giftGroup.groupType.eq(groupType));
        }

        String processType = getProcessType();

        if (!StringUtils.isEmpty(processType)) {

            LocalDateTime now = LocalDateTime.now();

            if (processType.equals(getCodeByProcessType(ProcessType.NOT_PROGRESS))) {

                builder.and(giftGroup.validStartDate.isNotNull().and(giftGroup.validStartDate.gt(now)));

            } else if (processType.equals(getCodeByProcessType(ProcessType.PROGRESS))) {

                builder.andAnyOf(
                        giftGroup.validStartDate.isNull()
                                .and(giftGroup.validEndDate.isNull()),
                        giftGroup.validStartDate.loe(now)
                                .and(giftGroup.validEndDate.goe(now)),
                        giftGroup.validStartDate.isNotNull()
                                .and(giftGroup.validEndDate.isNull())
                                .and(giftGroup.validStartDate.loe(now))
                );

            } else if (processType.equals(getCodeByProcessType(ProcessType.END))) {

                builder.and(giftGroup.validEndDate.isNotNull().and(giftGroup.validEndDate.lt(now)));

            }

        }


        return builder;
    }


    private String getCodeByProcessType(ProcessType processType) {
        return processType.getCode();
    }


}
