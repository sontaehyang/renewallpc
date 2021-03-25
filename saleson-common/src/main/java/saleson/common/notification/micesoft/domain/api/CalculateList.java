package saleson.common.notification.micesoft.domain.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saleson.common.utils.CommonUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculateList {

    private String id;
    private String name;
    private String calDt;
    private String basicRate;
    private String smsCnt;
    private String smsAmt;
    private String lmsCnt;
    private String lmsAmt;
    private String mmsCnt;
    private String mmsAmt;
    private String kkoCnt;
    private String kkoAmt;
    private String pushCnt;
    private String pushAmt;

    public CalculateList(Map<String, Object> response) {
        if (response != null) {
            setId(CommonUtils.dataNvl(response.get("ID")));
            setName(CommonUtils.dataNvl(response.get("NAME")));
            setCalDt(CommonUtils.dataNvl(response.get("CAL_DT")));
            setBasicRate(CommonUtils.dataNvl(response.get("BASIC_RATE")));
            setSmsCnt(CommonUtils.dataNvl(response.get("SMS_CNT")));
            setSmsAmt(CommonUtils.dataNvl(response.get("SMS_AMT")));
            setLmsCnt(CommonUtils.dataNvl(response.get("LMS_CNT")));
            setLmsAmt(CommonUtils.dataNvl(response.get("LMS_AMT")));
            setMmsCnt(CommonUtils.dataNvl(response.get("MMS_CNT")));
            setMmsAmt(CommonUtils.dataNvl(response.get("MMS_AMT")));
            setKkoCnt(CommonUtils.dataNvl(response.get("KKO_CNT")));
            setKkoAmt(CommonUtils.dataNvl(response.get("KKO_AMT")));
            setPushCnt(CommonUtils.dataNvl(response.get("PUSH_CNT")));
            setPushAmt(CommonUtils.dataNvl(response.get("PUSH_AMT")));
        }

    }
}

