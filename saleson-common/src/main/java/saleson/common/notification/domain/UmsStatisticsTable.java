package saleson.common.notification.domain;

import lombok.Data;

import java.util.List;

@Data
public class UmsStatisticsTable {
    private String type;
    private List<String> table;
}
