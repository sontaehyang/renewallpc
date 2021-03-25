package saleson.model.campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.enumeration.DeviceType;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "OP_APPLICATION_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationInfo extends BaseEntity {

    // 사용자 ID
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long userId;

    // PUSH 토큰
    @Column(length = 4000, nullable = false)
    private String pushToken;

    // 디바이스고유번호
    @Column(length = 200, nullable = false)
    private String uuid;

    // 디바이스 타입 OS
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private DeviceType deviceType;

    // 어플리케이션 번호
    @Column(length = 100, nullable = false)
    private String applicationNo;

    // 어플리케이션 버전
    @Column(length = 20, nullable = false)
    private String applicationVersion;
}
