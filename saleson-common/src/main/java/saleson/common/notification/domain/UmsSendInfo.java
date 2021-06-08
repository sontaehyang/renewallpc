package saleson.common.notification.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saleson.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UmsSendInfo {

    private String campaignKey;
    private Map<String, Map<String, Long>> sendMap;

    public List<String> sendTypes() {

        if (sendMap != null && !sendMap.isEmpty()) {
            return new ArrayList<>(sendMap.keySet());
        }

        return new ArrayList<>();
    }

    public long appendCount(long count, String sendType, String countType) {
        return count + getCount(sendType, countType);
    }

    private long getCount(String sendType, String countType) {
        long count = 0;

        if (sendMap != null && !sendMap.isEmpty()) {

            Map<String, Long> map = sendMap.get(sendType);

            if (map != null) {
                return CommonUtils.longNvl(map.get(countType));
            }

        }

        return 0;
    }
}
