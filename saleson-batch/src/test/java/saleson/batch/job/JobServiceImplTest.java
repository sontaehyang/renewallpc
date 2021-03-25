package saleson.batch.job;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import saleson.common.SalesonTest;
import saleson.common.utils.LocalDateUtils;
import saleson.common.web.Paging;
import saleson.shop.campaign.CampaignService;
import saleson.shop.group.GroupService;
import saleson.shop.order.claimapply.OrderClaimApplyService;
import saleson.shop.order.domain.OrderPayment;
import saleson.shop.order.payment.OrderPaymentMapper;
import saleson.shop.order.support.OrderParam;
import saleson.shop.user.UserMapper;
import saleson.shop.user.UserService;
import saleson.shop.userlevel.UserLevelMapper;
import saleson.shop.userlevel.UserLevelService;

import java.time.LocalDate;
import java.util.List;

public class JobServiceImplTest extends SalesonTest {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImplTest.class);

	@Autowired
	UserLevelService userLevelService;

	@Autowired
	GroupService groupService;


	@Autowired
	UserMapper userMapper;

	@Autowired
	UserLevelMapper userLevelMapper;


	@Autowired
	JobService jobService;

	@Autowired
    OrderPaymentMapper orderPaymentMapper;

    @Autowired
    OrderClaimApplyService orderClaimApplyService;

    @Autowired
    UserService userService;

    @Autowired
	Environment environment;

    @Autowired
    CampaignService campaignService;

	//@Test
	public void jobserviceTest() {
		log.debug( environment.getProperty("saleson.url.shoppingmall"));


	}

    //@Test
    public void userSleepBatch() {
        //jobService.processInactiveUserBatch();

        userService.setSleepUser();
    }

	//@Test
	public void userLevelBatch() {
		String expirationDate = LocalDateUtils.localDateToString(LocalDate.now().minusDays(1L));

		jobService.userLevelBatch(expirationDate);
	}


	//@Test
	public void autoConfirmPurchaseBatch() {
		jobService.autoConfirmPurchaseBatch();
	}
	//@Test
	public void cancelWaitingDepositOrderBatch() {
		//jobService.cancelWaitingDepositOrderBatch();

        log.debug("[cancelWaitingDepositOrderBatch] START ------------------------------------------");

        OrderParam orderParam = new OrderParam();
        orderParam.setConditionType("CANCEL-BATCH");
        orderParam.setSearchDelayDay("1400");

        // 입금지연 주문 카운트
        int totalCount = orderPaymentMapper.getWaitingDepositCountByParamForBatch(orderParam);

        log.debug("[cancelWaitingDepositOrderBatch] totalCount : {}", totalCount);

        Paging paging = new Paging(totalCount, 100);

        for (int i = 1; i <= paging.getTotalPages(); i++) {
            paging.setCurrentPage(i);
            orderParam.setPaging(paging);


            // 입금지연 주문 목록 조회
            List<OrderPayment> list = orderPaymentMapper.getWaitingDepositListByParamForBatch(orderParam);

            for (OrderPayment payment : list) {
                orderParam.setOrderCode(payment.getOrderCode());
                orderParam.setOrderSequence(payment.getOrderSequence());
                orderParam.setUserId(payment.getUserId());
                orderParam.setUserName(payment.getUserName());

                // 취소
                try {
                    orderClaimApplyService.orderCancelAllProcessNewTx(orderParam);
                } catch (Exception e) {
                    log.error("[cancelWaitingDepositOrderBatch] 전체 취소 실패 : {}", payment.getOrderCode(), e);
                }
            }

        }
	}

	//@Test
	public void updateUserCampaignBatch() {
	    jobService.updateUserCampaignBatch();
    }

    //@Test
    public void sendCampaignMessageBatch() {
        try {
            campaignService.insertCampaignMessageBatch();
        } catch (Exception e) {
            log.error("[sendCampaignMessageBatch] 캠페인용 예약발송 발송처리 배치 오류 : {}", e.getMessage(), e);
        }
    }

    @Test
    public void erpOrderStatusBatch() {
        jobService.erpOrderStatusBatch();
    }

}