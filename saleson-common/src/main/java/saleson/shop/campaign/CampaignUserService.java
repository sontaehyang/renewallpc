package saleson.shop.campaign;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.campaign.CampaignUser;

public interface CampaignUserService {

    /**
     * 목록 조회
     * @param predicate
     * @param pageable
     * @return
     */
    Page<CampaignUser> findAll(Predicate predicate, Pageable pageable);

    /**
     * 캠페인 대상 조회 (발송내역 상세 POPUP)
     */
    Page<CampaignUser> getCampaignUserList(Predicate predicate, Pageable pageable);

    /**
     * redirection 카운트 증가
     * @param campaignId
     * @param userId
     */
    void updateCampaignUserRedirection(long campaignId, long userId);
}
