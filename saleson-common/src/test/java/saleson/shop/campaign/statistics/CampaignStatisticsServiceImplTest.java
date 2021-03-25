package saleson.shop.campaign.statistics;

import com.onlinepowers.framework.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.Const;
import saleson.common.SalesonTest;

import static org.junit.jupiter.api.Assertions.*;

class CampaignStatisticsServiceImplTest extends SalesonTest {

    @Autowired
    private CampaignStatisticsService campaignStatisticsService;

    @Test
    void updateCampaignSentBatch() {

        try {

            String date = DateUtils.getToday(Const.DATE_FORMAT);

            campaignStatisticsService.updateCampaignSentBatch(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}