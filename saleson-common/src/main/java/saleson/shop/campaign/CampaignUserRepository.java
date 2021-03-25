package saleson.shop.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.campaign.CampaignQueryConst;
import saleson.model.campaign.CampaignUser;
import saleson.model.campaign.CampaignUserPk;

import java.time.LocalDateTime;
import java.util.List;

public interface CampaignUserRepository extends JpaRepository<CampaignUser, CampaignUserPk>, QuerydslPredicateExecutor<CampaignUser> {

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_USER
                        + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_RECEIVE, nativeQuery = true)
    void insertCampaignUserByBatchBase(
            @Param("created") LocalDateTime created,
            @Param("campaignId") Long campaignId,
            @Param("groupCodes") List<String> groupCodes,
            @Param("levelIds") List<Integer> levelIds,
            @Param("startOrderAmount1") Integer startOrderAmount1,
            @Param("endOrderAmount1") Integer endOrderAmount1,
            @Param("startOrderAmount2") Integer startOrderAmount2,
            @Param("endOrderAmount2") Integer endOrderAmount2,
            @Param("lastLoginDate") String lastLoginDate,
            @Param("cartCount") Integer cartCount,
            @Param("startCartAmount") Integer startCartAmount,
            @Param("endCartAmount") Integer endCartAmount,
            @Param("receiveSms") List<String> receiveSms,
            @Param("receivePush") List<String> receivePush
    );

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_USER + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_LOGIN_ID
                        + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_RECEIVE, nativeQuery = true)
    void insertCampaignUserByBatchWhereLoginId(
            @Param("created") LocalDateTime created,
            @Param("query") String query,
            @Param("campaignId") Long campaignId,
            @Param("groupCodes") List<String> groupCodes,
            @Param("levelIds") List<Integer> levelIds,
            @Param("startOrderAmount1") Integer startOrderAmount1,
            @Param("endOrderAmount1") Integer endOrderAmount1,
            @Param("startOrderAmount2") Integer startOrderAmount2,
            @Param("endOrderAmount2") Integer endOrderAmount2,
            @Param("lastLoginDate") String lastLoginDate,
            @Param("cartCount") Integer cartCount,
            @Param("startCartAmount") Integer startCartAmount,
            @Param("endCartAmount") Integer endCartAmount,
            @Param("receiveSms") List<String> receiveSms,
            @Param("receivePush") List<String> receivePush
    );

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_USER + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_USER_NAME
                        + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_RECEIVE, nativeQuery = true)
    void insertCampaignUserByBatchWhereUserName(
            @Param("created") LocalDateTime created,
            @Param("query") String query,
            @Param("campaignId") Long campaignId,
            @Param("groupCodes") List<String> groupCodes,
            @Param("levelIds") List<Integer> levelIds,
            @Param("startOrderAmount1") Integer startOrderAmount1,
            @Param("endOrderAmount1") Integer endOrderAmount1,
            @Param("startOrderAmount2") Integer startOrderAmount2,
            @Param("endOrderAmount2") Integer endOrderAmount2,
            @Param("lastLoginDate") String lastLoginDate,
            @Param("cartCount") Integer cartCount,
            @Param("startCartAmount") Integer startCartAmount,
            @Param("endCartAmount") Integer endCartAmount,
            @Param("receiveSms") List<String> receiveSms,
            @Param("receivePush") List<String> receivePush
    );

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_USER
            + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_RECEIVE_SMS_PUSH, nativeQuery = true)
    void insertCampaignUserByBatch1(
            @Param("created") LocalDateTime created,
            @Param("campaignId") Long campaignId,
            @Param("groupCodes") List<String> groupCodes,
            @Param("levelIds") List<Integer> levelIds,
            @Param("startOrderAmount1") Integer startOrderAmount1,
            @Param("endOrderAmount1") Integer endOrderAmount1,
            @Param("startOrderAmount2") Integer startOrderAmount2,
            @Param("endOrderAmount2") Integer endOrderAmount2,
            @Param("lastLoginDate") String lastLoginDate,
            @Param("cartCount") Integer cartCount,
            @Param("startCartAmount") Integer startCartAmount,
            @Param("endCartAmount") Integer endCartAmount,
            @Param("receiveSms") List<String> receiveSms,
            @Param("receivePush") List<String> receivePush
    );

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_USER + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_LOGIN_ID
            + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_RECEIVE_SMS_PUSH, nativeQuery = true)
    void insertCampaignUserByBatch2(
            @Param("created") LocalDateTime created,
            @Param("query") String query,
            @Param("campaignId") Long campaignId,
            @Param("groupCodes") List<String> groupCodes,
            @Param("levelIds") List<Integer> levelIds,
            @Param("startOrderAmount1") Integer startOrderAmount1,
            @Param("endOrderAmount1") Integer endOrderAmount1,
            @Param("startOrderAmount2") Integer startOrderAmount2,
            @Param("endOrderAmount2") Integer endOrderAmount2,
            @Param("lastLoginDate") String lastLoginDate,
            @Param("cartCount") Integer cartCount,
            @Param("startCartAmount") Integer startCartAmount,
            @Param("endCartAmount") Integer endCartAmount,
            @Param("receiveSms") List<String> receiveSms,
            @Param("receivePush") List<String> receivePush
    );

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_USER + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_USER_NAME
            + CampaignQueryConst.INSERT_CAMPAIGN_USER_WHERE_RECEIVE_SMS_PUSH, nativeQuery = true)
    void insertCampaignUserByBatch3(
            @Param("created") LocalDateTime created,
            @Param("query") String query,
            @Param("campaignId") Long campaignId,
            @Param("groupCodes") List<String> groupCodes,
            @Param("levelIds") List<Integer> levelIds,
            @Param("startOrderAmount1") Integer startOrderAmount1,
            @Param("endOrderAmount1") Integer endOrderAmount1,
            @Param("startOrderAmount2") Integer startOrderAmount2,
            @Param("endOrderAmount2") Integer endOrderAmount2,
            @Param("lastLoginDate") String lastLoginDate,
            @Param("cartCount") Integer cartCount,
            @Param("startCartAmount") Integer startCartAmount,
            @Param("endCartAmount") Integer endCartAmount,
            @Param("receiveSms") List<String> receiveSms,
            @Param("receivePush") List<String> receivePush
    );

    @Modifying
    @Query(value = CampaignQueryConst.DELETE_CAMPAIGN_USER, nativeQuery = true)
    void deleteCampaignUserById(
            @Param("campaignId") Long campaignId
    );

    @Query(value = CampaignQueryConst.SELECT_CAMPAIGN_USER_WHERE_CAMPAIGN_ID, nativeQuery = true)
    List<Long> getCampaignUserByCampaignId(
            @Param("campaignId") Long campaignId
    );

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_USER_REDIRECTION, nativeQuery = true)
    void updateCampaignUserRedirection(
            @Param("campaignId") Long campaignId,
            @Param("userId") Long userId,
            @Param("redirection") Long redirection
    );

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_USER_SMS, nativeQuery = true)
    void updateCampaignUserSmsSent(@Param("statisticsDate") String statisticsDate
    );

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_USER_MMS, nativeQuery = true)
    void updateCampaignUserMmsSent(@Param("statisticsDate") String statisticsDate
    );

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_USER_KAKAO, nativeQuery = true)
    void updateCampaignUserKakaoSent(@Param("statisticsDate") String statisticsDate
    );

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_USER_PUSH, nativeQuery = true)
    void updateCampaignUserPushSent(@Param("statisticsDate") String statisticsDate
    );
}
