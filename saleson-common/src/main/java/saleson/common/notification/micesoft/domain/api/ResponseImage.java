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
public class ResponseImage {

    private String resultCd;
    private String resultMsg;
    private List<FilePath> list;

    public ResponseImage(Map<String, Object> response) {

        if (response != null) {

            setResultCd(CommonUtils.dataNvl(response.get("RESULT_CD")));
            setResultMsg(CommonUtils.dataNvl(response.get("RESULT_MSG")));

            List<Map<String, Object>> list = (List<Map<String, Object>>) response.get("LIST");
            List<FilePath> filePaths = new ArrayList<>();
            if (list != null) {

                for (Map<String, Object> map : list) {
                    String filePath = CommonUtils.dataNvl(map.get("FILE_PATH"));

                    filePaths.add(new FilePath(filePath));
                }

                setList(filePaths);
            }
        }

    }
}
