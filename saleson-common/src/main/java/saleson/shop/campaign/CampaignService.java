package saleson.shop.campaign;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.campaign.Campaign;
import saleson.model.campaign.CampaignBatch;
import saleson.model.campaign.CampaignSendLog;
import saleson.shop.campaign.statistics.domain.MessageInfo;
import saleson.shop.campaign.support.CampaignUserParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CampaignService {

    /**
     * 캠페인 조회
     *
     * @param id
     * @return
     */
    Optional<Campaign> getCampaign(Long id);

    /**
     * 캠페인 목록 조회
     *
     * @param predicate
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<Campaign> getCampaignList(Predicate predicate, Pageable pageable);

    /**
     * 캠페인 등록
     *
     * @param campaign
     * @throws Exception
     */
    void insertCampaign(Campaign campaign, CampaignUserParam campaignUserParam) throws Exception;

    /**
     * 캠페인 삭제
     *
     * @param id
     * @throws Exception
     */
    void deleteCampaign(Long id) throws Exception;

    /**
     * 대상 회원 등록
     *
     * @param campaignId
     * @param campaignUserParam
     * @throws Exception
     */
    void insertCampaignUser(Long campaignId, CampaignUserParam campaignUserParam, String sendType) throws Exception;

    /**
     * 캠페인 회원 카운트
     *
     * @param predicate
     * @throws Exception
     */
    long getCampaignBatchListCount(Predicate predicate);

    /**
     * 캠페인 회원 배치 조회
     *
     * @param predicate
     * @param pageable
     * @return
     */
    Page<CampaignBatch> getCampaignBatchList(Predicate predicate, Pageable pageable);

    /**
     * 캠페인 배치 등록
     */
    void insertCampaignBatch();

    /**
     * 캠페인 발송
     * @param campaign
     */
    void sendCampaign(Campaign campaign) throws Exception;

    void insertCampaignMessageBatch() throws Exception;

    void publishCouponByCampaign(Campaign campaign) throws Exception;

    List<Map<String, Object>> getUserApplicationInfo(CampaignUserParam campaignUserParam) throws Exception;

    void updateCampaign(Long campaignId, Campaign campaign, CampaignUserParam campaignUserParam, String status) throws Exception;

    Page<CampaignSendLog> getCampaignSendLogs(Predicate predicate, Pageable pageable);
}
