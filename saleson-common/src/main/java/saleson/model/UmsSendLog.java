package saleson.model;

import com.onlinepowers.framework.notification.message.OpMessage;
import lombok.*;
import org.hibernate.annotations.Type;
import saleson.common.enumeration.UmsType;
import saleson.common.notification.domain.UmsTemplate;
import saleson.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="OP_UMS_SEND_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class UmsSendLog extends BaseEntity {

    public UmsSendLog(String templateCode, UmsType umsType, String title, String message, String sendType, String applyCode) {
        this.templateCode = templateCode;
        this.umsType = umsType;
        this.sendType = sendType;
        this.title = title;
        this.message = message;
        this.applyCode = applyCode;
    }

    public UmsSendLog(OpMessage opMessage, UmsType umsType) {
        if (opMessage != null) {

            String applyCode = "-";
            String templateCode = "-";
            String sendType = "";

            try {
                String[] bcc = opMessage.getBcc();

                templateCode = bcc[0];
                applyCode = bcc[1];

            } catch (Exception ignore) {}

            if (umsType == UmsType.MESSAGE) {
                sendType = opMessage.getSmsType();
            }

            this.templateCode = templateCode;
            this.umsType = umsType;
            this.sendType = sendType;
            this.title = opMessage.getTitle();
            this.message = opMessage.getMessage();
            this.applyCode = applyCode;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, updatable = false, nullable = false)
    private String templateCode;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, updatable = false, nullable = false)
    private UmsType umsType;

    // SMS 발송타입
    private String sendType;

    @Column(length = 255)
    private String title;

    // 발송문구
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String message;

    // 카카오톡 승인 코드
    @Column(length=100)
    private String applyCode;
}
