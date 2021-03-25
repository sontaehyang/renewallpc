package saleson.shop.restocknotice;

import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleson.common.notification.ApplicationInfoService;
import saleson.common.notification.UnifiedMessagingService;
import saleson.model.Ums;
import saleson.model.campaign.ApplicationInfo;
import saleson.shop.item.ItemMapper;
import saleson.shop.item.domain.Item;
import saleson.shop.restocknotice.domain.RestockNotice;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.support.ItemRestock;
import saleson.shop.user.UserMapper;

import java.util.List;

@Service("restockNoticeService")
public class RestockNoticeServiceImpl implements RestockNoticeService {

    @Autowired
    private RestockNoticeMapper restockNoticeMapper;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private UmsService umsService;

    @Autowired
    private UnifiedMessagingService unifiedMessagingService;

    @Autowired
    private ApplicationInfoService applicationInfoService;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertRestockNotice(RestockNotice restockNotice) {
        restockNotice.setRestockNoticeId(sequenceService.getId("OP_RESTOCK_NOTICE"));
        restockNoticeMapper.insertRestockNotice(restockNotice);
    }

    @Override
    public int getRestockNoticeCount(RestockNotice restockNotice) {
        return restockNoticeMapper.getRestockNoticeCount(restockNotice);
    }

    @Override
    public boolean isRestockNotice(RestockNotice restockNotice) {
        return restockNoticeMapper.getRestockNoticeCount(restockNotice) > 0 ? true : false;
    }

    @Override
    public void sendRestockNotice(int itemId) {
        String templateId = "item_restock";

        List<RestockNotice> restockNotices = restockNoticeMapper.getRestockNoticeListForMessage(itemId);

        Ums ums = umsService.getUms(templateId);

        Item item = itemMapper.getItemById(itemId);

        if (umsService.isValidUms(ums)) {

            restockNotices.stream()
                    .filter(rn -> !StringUtils.isEmpty(rn.getPhoneNumber()))
                    .forEach(rn -> {

                        ApplicationInfo applicationInfo = applicationInfoService.getApplicationInfo(rn.getUserId());

                        User user = userMapper.getUserByUserId(rn.getUserId());

                        unifiedMessagingService.sendMessage(new ItemRestock(ums, rn, rn.getPhoneNumber(), applicationInfo, item, user));
                    });

            if (!restockNotices.isEmpty()) {
                //restockNoticeMapper.updateSendFlagByItemId(itemId);
                restockNoticeMapper.deleteRestockNoticeByItemId(itemId);
            }

        }


    }
}
