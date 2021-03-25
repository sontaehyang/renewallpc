package saleson.common.notification.micesoft.domain.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saleson.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calculate {

    private String resultCd;
    private String resultMsg;
    private List<CalculateList> list;

    public Calculate(Map<String, Object> response) {

        if (response != null) {

            setResultCd(CommonUtils.dataNvl(response.get("RESULT_CD")));
            setResultMsg(CommonUtils.dataNvl(response.get("RESULT_MSG")));

            List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("LIST");

            if (list != null) {
                List<CalculateList> calculateLists = new ArrayList<>();
                for (Map<String, Object> map : list) {
                    calculateLists.add(new CalculateList(map));
                }

                setList(calculateLists);
            }

        }

    }
}
