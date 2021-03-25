package saleson.shop.campaign;

import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.common.utils.CommonUtils;
import saleson.model.campaign.CampaignUser;
import saleson.shop.campaign.support.CampaignUserDto;

import java.util.Optional;


@Service("CampaignUserService")
public class CampaignUserServiceImpl implements CampaignUserService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignUserServiceImpl.class);

    @Autowired
    private CampaignUserRepository campaignUserRepository;

    @Override
    public Page<CampaignUser> findAll(Predicate predicate, Pageable pageable) {
        return campaignUserRepository.findAll(predicate, pageable);
    }

    @Override
    public Page<CampaignUser> getCampaignUserList(Predicate predicate, Pageable pageable) {
        return campaignUserRepository.findAll(predicate, pageable);
    }

    @Override
    public void updateCampaignUserRedirection(long campaignId, long userId) {
        if (campaignId > 0 && userId > 0) {
            CampaignUserDto dto = new CampaignUserDto();
            dto.setCampaignId(campaignId);
            dto.setUserId(userId);

            Optional<CampaignUser> storeCampaignUser = campaignUserRepository.findOne(dto.getPredicate());

            if (storeCampaignUser.isPresent()) {
                long userRedirection = CommonUtils.longNvl(storeCampaignUser.get().getRedirection());
                campaignUserRepository.updateCampaignUserRedirection(campaignId, userId, userRedirection + 1);
            }
        }
    }
}
