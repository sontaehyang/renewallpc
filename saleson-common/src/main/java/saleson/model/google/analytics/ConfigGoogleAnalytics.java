package saleson.model.google.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.hibenate.converter.BooleanYnConverter;

import javax.persistence.*;

@Entity
@Table(name="OP_CONFIG_GOOGLE_ANALYTICS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ConfigGoogleAnalytics {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20)
    private String measurementId;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean commonTrackingFlag;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean ecommerceTrackingFlag;

    @Column(length=1, nullable = false)
    @Convert(converter = BooleanYnConverter.class)
    private boolean statisticsFlag;

    // 전자상거래 관련 설정 데이터
    @Column(length=3)
    private String currency;

    // 통계 연동을 위한 애널리틱스 프로파일 목록
    // , 단위로 구분
    @Column(length=100)
    private String profile;

    // auth를 위한 파일 (암호화 처리)
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String authFile;

    @Transient
    public MultipartFile authJsonFile;

    public void setUpdateData(ConfigGoogleAnalytics data) {
        setMeasurementId(data.getMeasurementId());

        setCommonTrackingFlag(data.isCommonTrackingFlag());

        setEcommerceTrackingFlag(data.isEcommerceTrackingFlag());
        setCurrency(data.getCurrency());

        setStatisticsFlag(data.isStatisticsFlag());

        setProfile(data.getProfile());
    }

}
