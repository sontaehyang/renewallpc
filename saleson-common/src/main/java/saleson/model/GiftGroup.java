package saleson.model;

import lombok.*;
import saleson.common.enumeration.DataStatus;
import saleson.common.enumeration.GiftGroupType;
import saleson.common.enumeration.ProcessType;
import saleson.common.utils.LocalDateUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="OP_GIFT_GROUP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class GiftGroup extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 사은품 그룹명
    @Column(length = 30, nullable = false)
    private String name;

    // 그룹타입
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private GiftGroupType groupType;

    // 주문금액별 사은품 조건 50,000 -> 주문금액이 50,000 이상일경우
    private Integer overOrderPrice;

    // 사은품 유효 시작 기간
    @Column
    private LocalDateTime validStartDate;

    // 사은품 유효 종료 기간
    @Column
    private LocalDateTime validEndDate;

    // 데이터 상태
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private DataStatus dataStatus;

    // 그룹 사은품 목록
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="giftGroupId")
    private List<GiftGroupItem> itemList;

    // form 데이터 영역

    // 그룹 사은품 ID
    @Transient
    private String[] giftItemIds;

    // 사은품 유효 시작 기간
    @Transient
    private String startDate;

    // 사은품 유효 종료 기간
    @Transient
    private String endDate;

    public String getValidStartDateText() {
        return LocalDateUtils.getDateTime(getValidStartDate());
    }

    public String getValidEndDateText() {
        return LocalDateUtils.getDateTime(getValidEndDate());
    }

    public ProcessType getProcessType() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validStartDate = getValidStartDate();
        LocalDateTime validEndDate = getValidEndDate();

        if (validStartDate == null && validEndDate == null) {

            return ProcessType.PROGRESS;

        } else if (validStartDate != null && validEndDate == null) {

            if (now.isBefore(validStartDate)) {
                return ProcessType.NOT_PROGRESS;
            } else {
                return ProcessType.PROGRESS;
            }

        } else if (validStartDate == null && validEndDate != null) {

            if (now.isAfter(validEndDate)) {
                return ProcessType.END;
            } else {
                return ProcessType.PROGRESS;
            }

        } else {

            if (now.isAfter(validStartDate) && now.isBefore(validEndDate)) {

                return ProcessType.PROGRESS;

            } else {

                if (now.isBefore(validStartDate)) {
                    return ProcessType.NOT_PROGRESS;
                }

                if (now.isAfter(validEndDate)) {
                    return ProcessType.END;
                }
            }

        }

        return null;
    }
}
