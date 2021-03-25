package saleson.model;

import lombok.*;
import saleson.common.enumeration.StoreType;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_STORE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String sido;

    @Column(length = 50)
    private String newPost;

    @Column(length = 8, nullable = false)
    private String post;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(length = 250)
    private String addressDetail;

    @Enumerated(EnumType.STRING)
    @Column
    private StoreType storeType;

    @Column(length = 20)
    private String telNumber;

    @Column(length = 4)
    private String startTime;

    @Column(length = 4)
    private String endTime;
}
