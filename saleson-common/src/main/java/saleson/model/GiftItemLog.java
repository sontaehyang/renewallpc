package saleson.model;

import lombok.*;
import saleson.common.enumeration.DataStatus;
import saleson.common.utils.LocalDateUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="OP_GIFT_ITEM_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class GiftItemLog extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private Long giftItemId;

    // 사은품명
    @Column(length = 30, updatable = false)
    private String name;

    // 사은품 금액
    @Column(updatable = false)
    private int price;

    // 사은품 이미지
    @Column(length = 255, updatable = false)
    private String image;

    // 사은품 유효 시작 기간
    @Column(updatable = false)
    private LocalDateTime validStartDate;

    // 사은품 유효 종료 기간
    @Column(updatable = false)
    private LocalDateTime validEndDate;

    // 데이터 상태
    @Enumerated(EnumType.STRING)
    @Column(length = 10, updatable = false)
    private DataStatus dataStatus;

    public String getValidStartDateText() {
        return LocalDateUtils.getDateTime(getValidStartDate());
    }

    public String getValidEndDateText() {
        return LocalDateUtils.getDateTime(getValidEndDate());
    }
}
