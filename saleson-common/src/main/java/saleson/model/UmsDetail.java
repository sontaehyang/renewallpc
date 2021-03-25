package saleson.model;

import lombok.*;
import org.hibernate.annotations.Type;
import saleson.common.enumeration.UmsType;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_UMS_DETAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class UmsDetail extends BaseEntity {

    public UmsDetail(String templateCode, UmsType umsType) {
        this.templateCode = templateCode;
        this.umsType = umsType;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, updatable = false, nullable = false)
    private String templateCode;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, updatable = false, nullable = false)
    private UmsType umsType;

    @Column(length=1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean usedFlag;

    // SMS 발송타입
    private String sendType;

    // 실패시 추가 진행 FLAG
    @Column(length=1)
    @Convert(converter = BooleanYnConverter.class)
    private boolean failProcessFlag;

    // 발송제목
    @Column(length=255)
    private String title;

    // 발송문구
    @Column(length=1000)
    private String message;

    // 카카오톡 승인 코드
    @Column(length=100)
    private String applyCode;

    // 카카오톡 버튼 정보 JSON
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String alimTalkButtons;

}
