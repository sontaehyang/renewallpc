package saleson.model;

import lombok.*;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_ITEM_FILTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ItemFilter extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer itemId;

    @Column(nullable = false)
    private Long filterGroupId;

    @Column(nullable = false)
    private Long filterCodeId;

    @Column
    private Integer ordering;
}
