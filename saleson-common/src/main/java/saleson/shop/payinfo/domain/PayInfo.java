package saleson.shop.payinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayInfo {

    public String payUserName;
    public String orderDate;
    public String payDate;
    public String approvalType;
    public int amount;
    public String orderCode;
    private int orderSequence;
    public String searchDate;

    private Object PayInfoList;

    private String searchStartDate;
    private String searchEndDate;


}