package saleson.model;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import org.springframework.util.StringUtils;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="OP_UMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Ums extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, updatable = false, nullable = false)
    private String templateCode;

    @Column(length = 200, updatable = false, nullable = false)
    private String templateName;

    @Column(length=1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean usedFlag;

    @Column(length=1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean nightSendFlag;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="umsId")
    private List<UmsDetail> detailList;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QUms ums = QUms.ums;

        if (!StringUtils.isEmpty(this.templateCode)) {
            builder.and(ums.templateCode.eq(getTemplateCode()));
        }

        return builder;
    }

}
