package saleson.shop.giftgroup;

import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleson.common.Const;
import saleson.common.enumeration.DataStatus;
import saleson.common.enumeration.ProcessType;
import saleson.common.exception.GiftItemException;
import saleson.common.utils.LocalDateUtils;
import saleson.common.utils.ModelUtils;
import saleson.model.GiftGroup;
import saleson.model.GiftGroupItem;
import saleson.model.GiftItem;
import saleson.shop.giftitem.GiftItemService;
import saleson.shop.giftitem.GiftItemServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("giftGroupService")
public class GiftGroupServiceImpl implements GiftGroupService {

    private static final Logger log = LoggerFactory.getLogger(GiftItemServiceImpl.class);

    @Autowired
    private GiftGroupRepository giftGroupRepository;

    @Autowired
    private GiftGroupItemRepository giftGroupItemRepository;

    @Autowired
    private GiftItemService giftItemService;

    @Override
    public void insertGiftGroup(GiftGroup giftGroup) throws Exception {
        giftGroup.setDataStatus(DataStatus.NORMAL);
        setBaseGiftGroup(giftGroup);
        giftGroupRepository.save(giftGroup);
    }

    @Override
    public void updateGiftGroup(GiftGroup giftGroup) throws Exception {

        GiftGroup orgGiftGroup = getGiftGroupById(giftGroup.getId());

        giftGroupItemRepository.deleteAll(orgGiftGroup.getItemList());

        // 기존정보에서 가져 와야 하는정보
        giftGroup.setVersion(orgGiftGroup.getVersion());
        giftGroup.setCreated(orgGiftGroup.getCreated());
        giftGroup.setCreatedBy(orgGiftGroup.getCreatedBy());
        giftGroup.setDataStatus(orgGiftGroup.getDataStatus());

        setBaseGiftGroup(giftGroup);

        giftGroupRepository.save(giftGroup);
    }

    @Override
    public void deleteGiftGroup(ListParam listParam) throws Exception {

        List<Long> ids = ModelUtils.getIds(listParam.getId());

        List<GiftGroup> giftGroups = giftGroupRepository.findAllById(ids);

        for (GiftGroup giftGroup : giftGroups) {

            giftGroup.setDataStatus(DataStatus.DELETE);

            giftGroupRepository.save(giftGroup);
        }


    }

    @Override
    public GiftGroup getGiftGroupById(long id) throws Exception {

        String errorMessage = "사은품 그룹 정보가 없습니다.";

        GiftGroup giftGroup = giftGroupRepository.findById(id)
                .orElseThrow(() -> new GiftItemException(errorMessage));

        if (DataStatus.DELETE == giftGroup.getDataStatus()) {
            throw new GiftItemException(errorMessage);
        }

        giftGroup.setStartDate(LocalDateUtils.localDateTimeToString(giftGroup.getValidStartDate(), Const.DATE_FORMAT));
        giftGroup.setEndDate(LocalDateUtils.localDateTimeToString(giftGroup.getValidEndDate(),Const.DATE_FORMAT));

        return giftGroup;
    }

    @Override
    public Page<GiftGroup> getGiftGroupList(Predicate predicate, Pageable pageable) throws Exception {
        return giftGroupRepository.findAll(predicate, pageable);
    }

    private void setBaseGiftGroup(GiftGroup giftGroup){

        if (giftGroup != null) {

            // 사은품 정보 셋팅
            setGiftItemList(giftGroup);

            // 유효일자 설정
            giftGroup.setValidStartDate(getValidLocalDateTime(giftGroup.getStartDate(), "000000"));
            giftGroup.setValidEndDate(getValidLocalDateTime(giftGroup.getEndDate(), "235959"));

        }
    }

    private void setGiftItemList(GiftGroup giftGroup){

        try {

            String[] giftItemIds = giftGroup.getGiftItemIds();

            if (giftItemIds != null && giftItemIds.length > 0) {

                List<Long> ids = ModelUtils.getIds(giftItemIds);

                List<GiftItem> giftItems = giftItemService.getGiftItemListByIds(ids);

                List<GiftGroupItem> itemList = new ArrayList<>();

                for (GiftItem giftItem : giftItems) {
                    itemList.add(new GiftGroupItem(giftItem));
                }

                giftGroup.setItemList(itemList);
            }

        } catch (Exception e) {
            log.error("Set Gift Item List error");
        }
    }

    /**
     * Form 통해 전달받은 기간을 LocalDateTime으로 변환
     * @param date
     * @param time
     * @return
     */
    private LocalDateTime getValidLocalDateTime(String date, String time) {

        if (!StringUtils.isEmpty(date) && !StringUtils.isEmpty(time)) {

            try {

                return LocalDateUtils.getLocalDateTime(date+time);

            } catch (Exception e) {
                log.error("LocalDateTime 변환 에러 > {} | {}", date, time);
            }
        }

        return null;
    }

    @Override
    public boolean isValidGiftGroup(long id) {

        try {

            GiftGroup giftGroup = getGiftGroupById(id);

            return isValidGiftGroup(giftGroup);


        } catch (Exception e) {
            return false;
        }

    }

    private boolean isValidGiftGroup(GiftGroup giftGroup) {

        if (giftGroup != null) {
            return DataStatus.NORMAL == giftGroup.getDataStatus()
                    && ProcessType.PROGRESS == giftGroup.getProcessType();
        }

        return false;
    }
}
