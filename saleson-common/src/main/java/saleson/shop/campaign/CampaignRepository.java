package saleson.shop.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.campaign.Campaign;
import saleson.model.campaign.CampaignQueryConst;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long>, QuerydslPredicateExecutor<Campaign> {

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_SENT, nativeQuery = true)
    void updateSent(@Param("id") long id,
                    @Param("smsSent") long smsSent,
                    @Param("smsSuccess") long smsSuccess,
                    @Param("mmsSent") long mmsSent,
                    @Param("mmsSuccess") long mmsSuccess,
                    @Param("kakaoSent") long kakaoSent,
                    @Param("kakaoSuccess") long kakaoSuccess,
                    @Param("pushSent") long pushSent,
                    @Param("pushSuccess") long pushSuccess,
                    @Param("pushReceive") long pushReceive,
                    @Param("statisticsDate") String statisticsDate);

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_REDIRECTION, nativeQuery = true)
    void updateRedirection(@Param("id") long id,
                         @Param("redirection") long redirection,
                         @Param("statisticsDate") String statisticsDate);

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_ORDER_INFO, nativeQuery = true)
    void updateOrderInfo(@Param("id") long id,
                    @Param("orderAmount") long orderAmount,
                    @Param("orderCount") long orderCount,
                    @Param("statisticsDate") String statisticsDate);

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_COUPON_USER, nativeQuery = true)
    void insertCouponByCampaign(
                    @Param("campaignId") long campaignId,
                    @Param("couponId") Integer couponId,
                    @Param("couponType") String couponType,
                    @Param("couponName") String couponName,
                    @Param("couponComment") String couponComment,
                    @Param("couponApplyType") String couponApplyType,
                    @Param("couponApplyStartDate") String couponApplyStartDate,
                    @Param("couponApplyEndDate") String couponApplyEndDate,
                    @Param("couponPayRestriction") Integer couponPayRestriction,
                    @Param("couponConcurrently") String couponConcurrently,
                    @Param("couponPayType") String couponPayType,
                    @Param("couponPay") Integer couponPay,
                    @Param("couponDiscountLimitPrice") Integer couponDiscountLimitPrice,
                    @Param("couponTargetItemType") String couponTargetItemType,
                    @Param("couponDownloadDate") String couponDownloadDate,
                    @Param("createdDate") String createdDate);


    @Query(value = CampaignQueryConst.SELECT_COUPON_USER_WHERE_DATA_STATUS_CODE, nativeQuery = true)
    List<Long> selectCouponUserWhereDataStatusCode(
                    @Param("campaignId") long campaignId,
                    @Param("couponId") Integer couponId);

    @Modifying
    @Query(value = CampaignQueryConst.DELETE_COUPON_USER_WHERE_MULTIPLE_DOWNLOAD_FLAG, nativeQuery = true)
    void deleteCouponUserWhereMultipleDownloadFlag(
                    @Param("userIds") List<Long> userIds);

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_ORDER_INFO_FOR_USER, nativeQuery = true)
    void updateOrderInfoForUser(@Param("id") long id,
                         @Param("userId") long userId,
                         @Param("orderAmount") long orderAmount,
                         @Param("orderCount") long orderCount,
                         @Param("statisticsDate") String statisticsDate);

}
