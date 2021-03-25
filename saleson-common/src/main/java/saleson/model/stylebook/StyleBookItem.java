package saleson.model.stylebook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;
import saleson.shop.item.domain.Item;

import javax.persistence.*;

@Entity
@Table(name="OP_STYLE_BOOK_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class StyleBookItem extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer itemId;

    @Column(nullable = false)
    private Integer ordering;

    @Transient
    private Item item;

    public StyleBookItem(int itemId, int ordering) {
        setItemId(itemId);
        setOrdering(ordering);
    }

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    public void setDefaultValue() {
        this.ordering = CommonUtils.intNvl(this.ordering);
    }
}
