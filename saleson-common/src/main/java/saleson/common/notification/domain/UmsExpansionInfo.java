package saleson.common.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saleson.common.enumeration.DeviceType;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UmsExpansionInfo {

    private boolean testFlag;

    private String messageKey;
    private String messageType;
    private String loginId;

    private String reservationDate;

    private String pushToken;
    private String applicationNo;
    private String applicationVersion;
    private String uuid;
    private String deepLink;

    private DeviceType deviceType;

    private String imagePath;
    private int imageSize;

    private String resendType;

    private Map<String, Object> otherData;

    private boolean alimTalkFailProcessFlag;
    private boolean pushFailProcessFlag;
}
