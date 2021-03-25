package saleson.model.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import saleson.common.utils.CommonUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_ITEM_REVIEW_LIKE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ItemReviewLike extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer itemReviewId;

    @Column
    private Long userId;

    @Column(length = 20)
    private String ip;

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    public void setDefaultValue() {
        setUserId(CommonUtils.longNvl(getUserId()));
        setIp(CommonUtils.dataNvl(getIp()));
    }
}
