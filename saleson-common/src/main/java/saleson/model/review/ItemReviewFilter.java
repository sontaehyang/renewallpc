package saleson.model.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_ITEM_REVIEW_FILTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ItemReviewFilter extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer itemReviewId;

    @Column(nullable = false)
    private Integer itemId;

    @Column(nullable = false)
    private Long filterGroupId;

    @Column(nullable = false)
    private Long filterCodeId;

    @Column
    private Integer ordering;
}
