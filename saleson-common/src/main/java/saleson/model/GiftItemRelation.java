package saleson.model;

import lombok.*;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_GIFT_ITEM_RELATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class GiftItemRelation extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column (updatable = false, nullable = false)
    private int itemId;

    // 사은품 정보
    @OneToOne(targetEntity = GiftItem.class)
    @JoinColumn(name="giftItemId")
    private GiftItem item;

}
