package saleson.shop.campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import saleson.model.campaign.ApplicationInfo;
import saleson.model.campaign.CampaignQueryConst;

import java.util.List;
import java.util.Map;

public interface ApplicationInfoRepository extends JpaRepository<ApplicationInfo, Long>, QuerydslPredicateExecutor<ApplicationInfo> {

    @Query(value = CampaignQueryConst.SELECT_APPLICATION_INFO, nativeQuery = true)
    List<Map<String, Object>> selectApplicationInfo();

    @Query(value = CampaignQueryConst.SELECT_APPLICATION_INFO + CampaignQueryConst.SELECT_APPLICATION_INFO_WHERE_LOGIN_ID, nativeQuery = true)
    List<Map<String, Object>> selectApplicationInfoByLoginId(
            @Param("query") String query
    );

    @Query(value = CampaignQueryConst.SELECT_APPLICATION_INFO + CampaignQueryConst.SELECT_APPLICATION_INFO_WHERE_USER_NAME, nativeQuery = true)
    List<Map<String, Object>> selectApplicationInfoByUserName(
            @Param("query") String query
    );
}
