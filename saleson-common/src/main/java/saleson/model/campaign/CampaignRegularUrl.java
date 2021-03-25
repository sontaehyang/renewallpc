package saleson.model.campaign;

import lombok.*;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "OP_CAMPAIGN_REGULAR_URL",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"eventCode"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class CampaignRegularUrl extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 컨텐츠
    @Column(length = 500)
    private String contents;

    // 대체코드
    @Column(length = 100)
    private String changeCode;

    // 이벤트 코드
    @Column(length = 10, updatable = false, nullable = false)
    private String eventCode;

    @Column
    private Long redirection;

    // GA 설정 (Campaign의 GA항목 합침)
    @Column
    private String utmQueryString;

}
