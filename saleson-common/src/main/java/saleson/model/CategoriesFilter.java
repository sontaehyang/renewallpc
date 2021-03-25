package saleson.model;

import lombok.*;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="OP_CATEGORY_FILTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CategoriesFilter extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long filterGroupId;

    @Column(nullable = false)
    private Integer categoryId;

    @Column
    private Integer ordering;

}
