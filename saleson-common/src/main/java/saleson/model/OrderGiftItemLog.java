package saleson.model;

import lombok.*;
import saleson.common.enumeration.GiftGroupType;
import saleson.common.enumeration.GiftOrderStatus;
import saleson.common.enumeration.UserType;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_ORDER_GIFT_ITEM_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderGiftItemLog extends BaseEntity {

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

    // 원 사은품 주문 상태
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private GiftOrderStatus orgGiftOrderStatus;

    // 사은품 주문 상태
    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private GiftOrderStatus giftOrderStatus;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private UserType userType;

}
