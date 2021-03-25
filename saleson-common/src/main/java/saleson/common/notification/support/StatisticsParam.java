package saleson.common.notification.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsParam {

    private String tableType;
    private List<String> tables;
    private String campaignKey;

    private boolean autoFlag;
    private boolean openFlag;

    private String beginSearchDate;
    private String endSearchDate;

    private String id;

    public void setTables(List<String> tables) {

        List<String> list = new ArrayList<>();

        if (tables != null && !tables.isEmpty()) {

            for (String table : tables) {
                if (table.matches("^[0-9]+$") && table.length() == 6) {
                    list.add(table);
                }
            }

        }

        this.tables = list;
    }
}
