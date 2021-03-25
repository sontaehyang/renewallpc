package saleson.common.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.campaign.support.ApplicationInfoDto;
import saleson.shop.campaign.ApplicationInfoRepository;

import java.util.Optional;

@Service("applicationInfoService")
public class ApplicationInfoService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationInfoService.class);

    @Autowired
    ApplicationInfoRepository applicationInfoRepository;

    public ApplicationInfo getApplicationInfo(Long userId) {
        ApplicationInfoDto dto = new ApplicationInfoDto();
        dto.setUserId(userId);

        Optional<ApplicationInfo> optional = applicationInfoRepository.findOne(dto.getPredicate());

        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }

}
