package saleson.shop.chart;

import saleson.shop.chart.domain.Chart;
import saleson.shop.chart.support.ChartParam;

import java.util.HashMap;
import java.util.List;

public interface ChartService {

    List<Chart> getChartItemList(ChartParam chartParam);

    List<Chart> getChartCategory1(HashMap categoryMap);

    List<Chart> getChartCategory2(String ItemLevel1);

    List<Chart> getChartCategory3(String ItemLevel2);

    List<Chart> getCategoryInfo(ChartParam chartParam);
}
