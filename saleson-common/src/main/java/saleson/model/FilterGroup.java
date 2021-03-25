package saleson.model;

import lombok.*;
import saleson.common.enumeration.FilterType;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="OP_FILTER_GROUP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class FilterGroup extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1000, nullable = false)
    private String label;

    @Column(length = 1000, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private FilterType filterType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "filterGroupId", nullable = false, updatable = false)
    private List<FilterCode> codeList;
}
