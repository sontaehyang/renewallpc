package saleson.shop.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import saleson.erp.domain.ErpMapper;
import saleson.shop.chart.domain.Chart;
import saleson.shop.chart.support.ChartParam;

import java.util.HashMap;
import java.util.List;

@Service("chartService")
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartService chartService;

    @Autowired
    private ErpMapper erpMapper;

    @Override
    public List<Chart> getChartItemList(ChartParam chartParam) {
        return erpMapper.getChartItemList(chartParam);
    };

    @Override
    public List<Chart> getChartCategory1(HashMap categoryMap) {
        return erpMapper.getChartCategory1(categoryMap);
    }

    @Override
    public List<Chart> getChartCategory2(String ItemLevel1) {
        return erpMapper.getChartCategory2(ItemLevel1);
    }

    @Override
    public List<Chart> getChartCategory3(String ItemLevel2) {
        return erpMapper.getChartCategory3(ItemLevel2);
    }

    @Override
    public List<Chart> getCategoryInfo(ChartParam chartParam) {
        return erpMapper.getCategoryInfo(chartParam);
    }
}

