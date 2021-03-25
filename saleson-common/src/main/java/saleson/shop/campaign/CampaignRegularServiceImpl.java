package saleson.shop.campaign;

import com.onlinepowers.framework.exception.UserException;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.model.campaign.CampaignRegular;
import saleson.shop.campaign.support.CampaignUserParam;
import saleson.shop.coupon.CouponService;
import saleson.shop.eventcode.EventCodeRepository;
import saleson.shop.group.GroupService;
import saleson.shop.user.UserService;

import java.util.Optional;


@Service("campaignRegularService")
public class CampaignRegularServiceImpl implements CampaignRegularService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignRegularServiceImpl.class);

    @Autowired
    private EventCodeRepository eventCodeRepository;

    @Autowired
    Environment environment;

    @Autowired
    CouponService couponService;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @Autowired
    private CampaignRegularRepository campaignRegularRepository;

    @Override
    public Optional<CampaignRegular> getCampaignRegular(Long id) {
        return campaignRegularRepository.findById(id);
    }

    @Override
    public Page<CampaignRegular> getCampaignRegularList(Predicate predicate, Pageable pageable) {
        return campaignRegularRepository.findAll(predicate, pageable);
    }

    @Override
    public void insertCampaignRegular(CampaignRegular campaignRegular) {
        campaignRegularRepository.save(campaignRegular);
    }

    public void updateCampaignRegular(Long campaignRegularId, CampaignRegular campaignRegular,
                                      CampaignUserParam campaignUserParam, String status) {
        CampaignRegular storeCampaignRegular = campaignRegularRepository.findById(campaignRegularId)
                .orElseThrow(() -> new UserException("정기발송 정보가 없습니다.", "/opmanager/campaign/regular-list"));

        campaignRegular.setId(storeCampaignRegular.getId());
        campaignRegular.setCreated(storeCampaignRegular.getCreated());
        campaignRegular.setCreatedBy(storeCampaignRegular.getCreatedBy());
        campaignRegular.setVersion(storeCampaignRegular.getVersion());

        if (storeCampaignRegular.getUrlList().size() > 0 && "update".equals(status)) {
            eventCodeRepository.deleteCampaignRegularUrl(storeCampaignRegular.getId());
        }

        campaignRegularRepository.save(campaignRegular);
    }

    @Override
    public void deleteCampaignRegular(Long id) {

        CampaignRegular storeCampaignRegularRegular = campaignRegularRepository.findById(id)
                .orElseThrow(() -> new UserException("캠페인 정보가 없습니다.", "/opmanager/ums/list"));

        campaignRegularRepository.deleteById(id);

        if (storeCampaignRegularRegular.getUrlList().size() > 0) {
            eventCodeRepository.deleteCampaignRegularUrl(id);
        }
    }
}
