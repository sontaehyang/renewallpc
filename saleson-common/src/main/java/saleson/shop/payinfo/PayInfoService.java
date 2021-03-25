package saleson.shop.payinfo;

import java.util.List;

import saleson.shop.payinfo.domain.PayInfo;
import saleson.shop.payinfo.support.PayInfoParam;

public interface PayInfoService {

    List<PayInfo> getPayInfoList(PayInfoParam payInfoparam);

    int getPayInfoListCount(PayInfoParam payInfoparam);
}