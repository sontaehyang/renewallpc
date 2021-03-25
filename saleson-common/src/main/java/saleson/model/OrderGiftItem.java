package saleson.model;

import lombok.*;
import saleson.common.enumeration.GiftGroupType;
import saleson.common.enumeration.GiftOrderStatus;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name="OP_ORDER_GIFT_ITEM",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "orderCode",
                                "orderSequence",
                                "itemSequence",
                                "giftSequence",
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderGiftItem extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 주문번호
    @Column(length = 50, updatable = false, nullable = false)
    private String orderCode;

    // 주문순번
    @Column(updatable = false, nullable = false)
    private int orderSequence;

    // 상품순번
    @Column(updatable = false, nullable = false)
    private int itemSequence;

    // 사은품순번
    @Column(updatable = false, nullable = false)
    private int giftSequence;

    // 사은품 ID
    @Column(updatable = false, nullable = false)
    private Long giftItemId;

    // 사은품 코드
    @Column(length = 30, updatable = false, nullable = false)
    private String giftItemCode;

    // 사은품 명
    @Column(length = 30, updatable = false, nullable = false)
    private String giftItemName;

    // 사은품 그룹 관리 ID
    @Column(updatable = false, nullable = false)
    private Long giftGroupId;

    // 그룹타입
    @Enumerated(EnumType.STRING)
    @Column(length = 15, updatable = false, nullable = false)
    private GiftGroupType groupType;

    // 판매자 ID
    @Column(updatable = false, nullable = false)
    private Long sellerId;

    // 사은품 금액
    @Column(updatable = false, nullable = false)
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

    // 사은품 주문 상태
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private GiftOrderStatus giftOrderStatus;


}
