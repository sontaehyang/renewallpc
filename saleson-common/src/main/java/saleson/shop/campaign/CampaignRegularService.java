package saleson.shop.campaign;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import saleson.model.campaign.CampaignRegular;
import saleson.shop.campaign.support.CampaignUserParam;

import java.util.Optional;

public interface CampaignRegularService {

    /**
     * 정기발송 조회
     *
     * @param id
     * @return
     */
    Optional<CampaignRegular> getCampaignRegular(Long id);

    /**
     * 정기발송 목록 조회
     *
     * @param predicate
     * @param pageable
     * @return
     * @throws Exception
     */
    Page<CampaignRegular> getCampaignRegularList(Predicate predicate, Pageable pageable);

    /**
     * 정기발송 등록
     *
     * @param campaignRegular
     * @throws Exception
     */
    void insertCampaignRegular(CampaignRegular campaignRegular) throws Exception;

    /**
     * 정기발송 수정
     *
     * @param campaignRegularId
     * @throws Exception
     */
    void updateCampaignRegular(Long campaignRegularId, CampaignRegular campaignRegular, CampaignUserParam campaignUserParam, String status) throws Exception;

    /**
     * 정기발송 삭제
     *
     * @param id
     * @throws Exception
     */
    void deleteCampaignRegular(Long id) throws Exception;
}
