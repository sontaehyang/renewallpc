package saleson.shop.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.campaign.CampaignBatch;
import saleson.model.campaign.CampaignQueryConst;

import java.time.LocalDateTime;

public interface CampaignBatchRepository extends JpaRepository<CampaignBatch, Long>, QuerydslPredicateExecutor<CampaignBatch> {

    @Modifying
    @Query(value= CampaignQueryConst.INSERT_CAMPAIGN_BATCH, nativeQuery = true)
    void insertCampaignBatch(@Param("created")LocalDateTime created,
                             @Param("intervalDate") String intervalDate,
                             @Param("intervalPayDate") String intervalPayDate );

    @Modifying
    @Query(value = CampaignQueryConst.INSERT_CAMPAIGN_BATCH_POINT, nativeQuery = true)
    void insertCampaignBatchPoint(@Param("today") String today);

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_BATCH_FOR_POINT_MYSQL, nativeQuery = true)
    void updateCampaignBatchForPointByMysql();

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_BATCH_FOR_POINT_ORACLE, nativeQuery = true)
    void updateCampaignBatchForPointByOracle();

    @Modifying
    @Query(value = CampaignQueryConst.UPDATE_CAMPAIGN_BATCH_FOR_POINT_POSTGRES, nativeQuery = true)
    void updateCampaignBatchForPointByPostgres();

    @Modifying
    @Query(value = CampaignQueryConst.DELETE_CAMPAIGN_BATCH_POINT, nativeQuery = true)
    void deleteCampaignBatchPoint();

    @Modifying
    @Query(value = CampaignQueryConst.DELETE_CAMPAIGN_BATCH, nativeQuery = true)
    void deleteCampaignBatch();
}
