package saleson.shop.campaign;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.Const;
import saleson.common.SalesonTest;
import saleson.model.campaign.Campaign;
import saleson.shop.campaign.support.CampaignDto;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class CampaignServiceImplTest extends SalesonTest {

    @Autowired
    CampaignService campaignService;

    @Autowired
    CampaignRepository campaignRepository;

    @Test
    void insertCampaignBatch() {
        campaignService.insertCampaignBatch();
    }

    @Test
    void deleteCampaignBatch() {
    //    campaignService.deleteCampaignBatch();
    }

    @Test
    void generationEventCodeList() {

        /*try {
            List<String> codes = campaignService.generateEventCodeList(10);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(JsonViewUtils.objectToJson(codes));
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Test
    public void insertCampaignMessageBatch() {
        CampaignDto dto = new CampaignDto();
        dto.setBatchDateTime("20200421110000");

        Iterable<Campaign> iterable = campaignRepository.findAll(dto.getSendPredicate());
        AtomicBoolean pushSmsFlag = new AtomicBoolean(false);

        iterable.forEach(campaign -> {

            if ("2".equals(campaign.getSendType())) {
                pushSmsFlag.set(true);
            }

            // 메시지 발송
            try {
                campaignService.sendCampaign(campaign);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (pushSmsFlag.get() == true) {
                campaign.setSendType("2");
            }

            try {
                // 쿠폰 발행
                if (campaign.getCouponId() > 0) {
                    campaignService.publishCouponByCampaign(campaign);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 발송 상태 업데이트
            campaign.setStatus("1");

            // 예약/즉시 발송일시 업데이트
            campaign.setSendDate(null);
            campaign.setSentDate(DateUtils.getToday(Const.DATETIME_FORMAT));

            try {
                // 배치 flag 업데이트
                campaign.setBatchFlag(true);
                campaignRepository.save(campaign);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}