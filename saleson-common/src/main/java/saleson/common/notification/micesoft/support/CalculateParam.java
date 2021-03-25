package saleson.common.notification.micesoft.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculateParam {

    private String searchYear;
    private String searchMonth;
    private String id;

    public String getSearchDate() {
        return getSearchYear() + getSearchMonth();
    }
}
