package saleson.model;

import lombok.*;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_GIFT_GROUP_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class GiftGroupItem extends BaseEntity {

    public GiftGroupItem(GiftItem item) {
        this.item = item;
    }

    @Id
    @GeneratedValue
    private Long id;

    // 사은품 정보
    @OneToOne(targetEntity = GiftItem.class)
    @JoinColumn(name="giftItemId")
    private GiftItem item;
}
