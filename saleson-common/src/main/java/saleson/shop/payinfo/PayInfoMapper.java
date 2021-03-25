package saleson.shop.payinfo;

import java.util.List;

import com.onlinepowers.framework.orm.mybatis.annotation.Mapper;

import saleson.shop.payinfo.domain.PayInfo;
import saleson.shop.payinfo.support.PayInfoParam;

@Mapper("payInfoMapper")
public interface PayInfoMapper {

    /**
     * 결제현황리스트
     *
     * @param payInfoParam
     * @return
     */
    List<PayInfo> getPayInfoList(PayInfoParam payInfoParam);

    int getPayInfoListCount(PayInfoParam payInfoParam);
}