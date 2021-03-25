package saleson.shop.payinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saleson.shop.payinfo.domain.PayInfo;
import saleson.shop.payinfo.support.PayInfoParam;

@Service("payInfoService")
public class PayInfoSerivceImpl implements PayInfoService {

    @Autowired
    PayInfoMapper payInfoMapper;

    /**
     * 결제현황리스트
     */
    @Override
    public List<PayInfo> getPayInfoList(PayInfoParam payInfoParam) {
        return payInfoMapper.getPayInfoList(payInfoParam);
    }

    @Override
    public int getPayInfoListCount(PayInfoParam payInfoParam) {
        return payInfoMapper.getPayInfoListCount(payInfoParam);
    }

}