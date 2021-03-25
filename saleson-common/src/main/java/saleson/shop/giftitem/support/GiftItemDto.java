package saleson.shop.giftitem.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.enumeration.DataStatus;
import saleson.common.enumeration.ProcessType;
import saleson.common.web.Param;
import saleson.model.QGiftItem;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftItemDto extends Param {

    private static final Logger log = LoggerFactory.getLogger(GiftItemDto.class);

	private Long id;
	private String code;
	private String name;
	private long sellerId;
	private int price;
	private String image;
	private LocalDateTime validStartDate;
	private LocalDateTime validEndDate;
	private DataStatus dataStatus;
	private MultipartFile imageFile;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;




    private String targetId;
    private String processType;
    private List<Long> ids;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QGiftItem giftItem = QGiftItem.giftItem;

        // 데이터 상태가 정상인 경우만
        builder.and(giftItem.dataStatus.eq(DataStatus.NORMAL));

        if (!StringUtils.isEmpty(getQuery())) {

            if ("code".equalsIgnoreCase(getWhere())) {
                builder.and(
                        giftItem.code.contains(getQuery())
                );
            } else if ("name".equalsIgnoreCase(getWhere())) {
                builder.and(
                        giftItem.name.contains(getQuery())
                );
            }
        }

        String processType = getProcessType();

        if (!StringUtils.isEmpty(processType)) {

            LocalDateTime now = LocalDateTime.now();

            if (processType.equals(getCodeByProcessType(ProcessType.NOT_PROGRESS))) {

                builder.and(giftItem.validStartDate.isNotNull().and(giftItem.validStartDate.gt(now)));

            } else if (processType.equals(getCodeByProcessType(ProcessType.PROGRESS))) {

                builder.andAnyOf(
                        giftItem.validStartDate.isNull()
                                .and(giftItem.validEndDate.isNull()),
                        giftItem.validStartDate.loe(now)
                                .and(giftItem.validEndDate.goe(now)),
                        giftItem.validStartDate.isNotNull()
                                .and(giftItem.validEndDate.isNull())
                                .and(giftItem.validStartDate.loe(now)),
                        giftItem.validEndDate.isNotNull()
                                .and(giftItem.validStartDate.isNull())
                                .and(giftItem.validEndDate.goe(now))
                );

            } else if (processType.equals(getCodeByProcessType(ProcessType.END))) {

                builder.and(giftItem.validEndDate.isNotNull().and(giftItem.validEndDate.lt(now)));

            }

        }

        if (getSellerId() > 0) {
            builder.and(giftItem.sellerId.eq(getSellerId()));
        }

        if (getIds() != null && getIds().size() > 0) {

            builder.and(giftItem.id.in(getIds()));

        }

        return builder;
    }


    private String getCodeByProcessType(ProcessType processType) {
        return processType.getCode();
    }
}
