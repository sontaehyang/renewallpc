package saleson.shop.giftitem;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.web.domain.ListParam;
import com.querydsl.core.types.Predicate;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.enumeration.DataStatus;
import saleson.common.enumeration.ProcessType;
import saleson.common.exception.GiftItemException;
import saleson.common.utils.LocalDateUtils;
import saleson.common.utils.ModelUtils;
import saleson.common.utils.SellerUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.GiftItem;
import saleson.model.GiftItemLog;
import saleson.model.GiftItemRelation;
import saleson.shop.giftitem.support.GiftItemDto;
import saleson.shop.giftitem.support.GiftItemRelationDto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

@Service("giftItemService")
public class GiftItemServiceImpl implements GiftItemService{

    private static final Logger log = LoggerFactory.getLogger(GiftItemServiceImpl.class);

    @Autowired
    SequenceService sequenceService;

    @Autowired
    FileService fileService;

    @Autowired
    private GiftItemRepository giftItemRepository;

    @Autowired
    private GiftItemLogRepository giftItemLogRepository;

    @Autowired
    private GiftItemRelationRepository giftItemRelationRepository;

	@Autowired
	Environment environment;

    @Override
    public void insertGiftItem(GiftItem giftItem) throws Exception {

        int seq = sequenceService.getId("GIFT_ITEM_CODE");
        String code = "GIFT" + StringUtils.lPad(Integer.toString(seq), 9, '0');

        giftItem.setDataStatus(DataStatus.NORMAL);
        giftItem.setCode(code);
        giftItem.setSellerId(SellerUtils.getSellerId());

        setBaseGiftItem(giftItem);

        giftItemRepository.save(giftItem);
        insertGiftItemLog(giftItem);
    }

    @Override
    public void updateGiftItem(GiftItem giftItem) throws Exception {

        GiftItem orgGiftItem = getGiftItemById(giftItem.getId());
        String orgImage = orgGiftItem.getImage();

        if (StringUtils.isEmpty(orgImage)) {
            orgImage = "";
        }

        // 기존정보에서 가져 와야 하는정보
        giftItem.setVersion(orgGiftItem.getVersion());
        giftItem.setCreated(orgGiftItem.getCreated());
        giftItem.setCreatedBy(orgGiftItem.getCreatedBy());
        giftItem.setCode(orgGiftItem.getCode());
        giftItem.setDataStatus(orgGiftItem.getDataStatus());
        giftItem.setSellerId(orgGiftItem.getSellerId());
        giftItem.setImage(orgImage);

        setBaseGiftItem(giftItem);

        giftItemRepository.save(giftItem);

        insertGiftItemLog(giftItem);
    }

    @Override
    public void deleteGiftItem(ListParam listParam) throws Exception {

        if (listParam.getId() == null) {
            throw new GiftItemException("처리할 데이터가 없습니다.");
        }

        List<Long> ids = ModelUtils.getIds(listParam.getId());

        List<GiftItem> giftItems = giftItemRepository.findAllById(ids);

        for (GiftItem giftItem : giftItems) {

            giftItem.setDataStatus(DataStatus.DELETE);

            giftItemRepository.save(giftItem);

            insertGiftItemLog(giftItem);
        }
    }

    @Override
    public void deleteGiftItemImage(long id) throws Exception {
        GiftItem giftItem = getGiftItemById(id);

        giftItem.setImage("");
        giftItemRepository.save(giftItem);
    }

    private void setBaseGiftItem(GiftItem giftItem) {

        if (giftItem != null) {

            // 유효일자 설정
            giftItem.setValidStartDate(getValidLocalDateTime(giftItem.getStartDate(), giftItem.getStartTime()));
            giftItem.setValidEndDate(getValidLocalDateTime(giftItem.getEndDate(), giftItem.getEndTime()));

            // 사은품 이미지 설정
            saveGiftItemImage(giftItem);


        }
    }

    private void saveGiftItemImage(GiftItem giftItem) {

        MultipartFile imageFile = giftItem.getImageFile();

        if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() > 0) {
            String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + giftItem.getImageUploadPath();

            StringBuffer sb;

            fileService.makeUploadPath(uploadPath);
            String newFileName = "";
            BufferedImage bufferedImage = null;

            try {
                String date = DateUtils.getToday(Const.DATETIME_FORMAT);
                for (String sizeName : ShopUtils.getThumbnailType()) {

                    try {
                        bufferedImage = ImageIO.read(imageFile.getInputStream());
                    } catch (IOException e1) {
                        throw new GiftItemException("이미지 읽어오기 실패 - ERROR");
                    }

                    if (bufferedImage == null) {
                        throw new GiftItemException("이미지 읽어오기 실패 - ERROR");
                    }

                    // 2. 파일명
                    sb = new StringBuffer();
                    sb.append(date);
                    sb.append(sizeName);
                    sb.append(".");
                    sb.append(FileUtils.getExtension(imageFile.getOriginalFilename()));
                    String defaultFileName = sb.toString();
                    newFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

                    // 3. 저장될 파일
                    File saveFile = new File(uploadPath + File.separator + newFileName);

                    int imageSize = ShopUtils.getImageSize(sizeName);

                    if (ShopUtils.checkImageSize2(bufferedImage.getWidth(), bufferedImage.getHeight(), imageSize)) {
                        try {
                            bufferedImage = ShopUtils.getThumbnailImage(bufferedImage, imageSize);
                        } catch (Exception e) {
                            throw new GiftItemException("썸네일 생성중 오류 - ERROR");
                        }
                    }

                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    output.flush();
                    ImageIO.write(bufferedImage, FileUtils.getExtension(imageFile.getOriginalFilename()), output);
                    output.close();
                    FileCopyUtils.copy(output.toByteArray(), saveFile);

                    giftItem.setImage(saveFile.getName());
                }

            } catch (IOException e) {
                log.debug("섬네일 생성 오류 :  {}", e.getMessage());
                throw new GiftItemException("썸네일 생성중 오류 - ERROR");
            }
        }

    }


    private String getImageNameBySize(String fileName, String sizeName) {

        int pos = fileName.lastIndexOf( "." );
        String ext = FileUtils.getExtension(fileName);//fileName.substring( pos + 1 );
        String reFileName = fileName.substring(0, pos);

        String returnFileName = "";

        if (reFileName.contains("(") && reFileName.contains(")")) {
            //이미지 이름에 ()괄호가 있을경우
            pos = fileName.lastIndexOf( "(" );
            ext = fileName.substring( pos, fileName.length());
            returnFileName = reFileName.substring(0, 14) + sizeName + ext;

        } else {
            if (reFileName.length()>14) reFileName = reFileName.substring(0, 14) + sizeName;

            returnFileName = reFileName + "." + ext;
        }

        return returnFileName;
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
                log.error("LocalDateTime 변환 에러 > {} | {}", date,  time, e);
            }
        }

        return null;
    }

    /**
     * 사은품 정보 로그 등록
     * @param giftItem
     */
    private void insertGiftItemLog(GiftItem giftItem) {

        GiftItemLog giftItemLog = new GiftItemLog();

        giftItemLog.setGiftItemId(giftItem.getId());
        giftItemLog.setName(giftItem.getName());

        giftItemLog.setImage(giftItem.getImage());
        giftItemLog.setPrice(giftItem.getPrice());
        giftItemLog.setValidStartDate(giftItem.getValidStartDate());
        giftItemLog.setValidEndDate(giftItem.getValidEndDate());
        giftItemLog.setDataStatus(giftItem.getDataStatus());

        giftItemLogRepository.save(giftItemLog);

    }

    @Override
    public GiftItem getGiftItemById(long id) throws Exception{

        String errorMessage = "사은품 정보가 없습니다.";

        GiftItem giftItem = giftItemRepository.findById(id)
                .orElseThrow(() -> new GiftItemException(errorMessage));

        if (DataStatus.DELETE == giftItem.getDataStatus()) {
            throw new GiftItemException(errorMessage);
        }
        giftItem.setEnvironment(environment);

        giftItem.setStartDate(LocalDateUtils.localDateTimeToString(giftItem.getValidStartDate(),Const.DATE_FORMAT));
        giftItem.setStartTime(LocalDateUtils.localDateTimeToString(giftItem.getValidStartDate(),"HHmmss"));

        giftItem.setEndDate(LocalDateUtils.localDateTimeToString(giftItem.getValidEndDate(),Const.DATE_FORMAT));
        giftItem.setEndTime(LocalDateUtils.localDateTimeToString(giftItem.getValidEndDate(),"HHmmss"));

        return giftItem;
    }


    @Override
    public Page<GiftItem> getGiftItemList(Predicate predicate, Pageable pageable) throws Exception {

        return giftItemRepository.findAll(predicate, pageable);
    }

    @Override
    public boolean isValidGiftItem(long id) {

        try {

            GiftItem giftItem = getGiftItemById(id);


            return isValidGiftItem(giftItem);

        } catch (Exception e) {
            return false;
        }

    }

    private boolean isValidGiftItem(GiftItem giftItem) {

        if (giftItem != null) {

            return DataStatus.NORMAL == giftItem.getDataStatus()
                    && ProcessType.PROGRESS == giftItem.getProcessType();

        }

        return false;

    }

    @Override
    public Page<GiftItemLog> getGiftItemLogList(Predicate predicate, Pageable pageable) throws Exception {
        return giftItemLogRepository.findAll(predicate, pageable);
    }

    @Override
    public List<GiftItem> getGiftItemListByIds(List<Long> ids) throws Exception {

        GiftItemDto param = new GiftItemDto();
        param.setIds(ids);
        Iterable<GiftItem> list = giftItemRepository.findAll(param.getPredicate());

        List<GiftItem> tempGiftItems = Lists.newArrayList(list.iterator());

        List<GiftItem> giftItems = new ArrayList<>();

        for (Long id : ids) {

            if (id == null) {
                continue;
            }

            for (GiftItem giftItem : tempGiftItems) {

                if (id.equals(giftItem.getId())) {
                    giftItems.add(giftItem);
                    break;
                }
            }
        }

        return giftItems;
    }

    @Override
    public void insertGiftItemRelation(int itemId, List<Long> giftItemIds) throws Exception {

        if (itemId > 0) {

            List<GiftItem> giftItemList = getGiftItemListByIds(giftItemIds);

            List<GiftItemRelation> saveRelationList = new ArrayList<>();

            for (GiftItem giftItem : giftItemList) {
                GiftItemRelation relation = new GiftItemRelation();

                relation.setItemId(itemId);
                relation.setItem(giftItem);

                saveRelationList.add(relation);
            }

            if (!saveRelationList.isEmpty()) {
                giftItemRelationRepository.saveAll(saveRelationList);
            }
        }

    }

    @Override
    public void deleteGiftItemRelation(int itemId) throws Exception {

        if (itemId > 0) {

            Iterable<GiftItemRelation> giftItemRelations = getGiftItemRelationListByItemId(itemId);
            long size = StreamSupport.stream(giftItemRelations.spliterator(),false).count();

            if (giftItemRelations != null && size > 0) {
                giftItemRelationRepository.deleteAll(giftItemRelations);
            }
        }
    }

    @Override
    public List<GiftItemRelation> getGiftItemRelationList(int itemId) throws Exception {

        return Lists.newArrayList(getGiftItemRelationListByItemId(itemId).iterator());
    }

    private Iterable<GiftItemRelation> getGiftItemRelationListByItemId(int itemId) {
        GiftItemRelationDto param = new GiftItemRelationDto();

        param.setItemId(itemId);

        return giftItemRelationRepository.findAll(param.getPredicate());

    }

    @Override
    public List<GiftItem> getGiftItemListForFront(int itemId) throws Exception {

        Iterator<GiftItemRelation> iterator = getGiftItemRelationListByItemId(itemId).iterator();

        List<GiftItem> list = new ArrayList<>();

        while (iterator.hasNext()) {
            GiftItemRelation relation = iterator.next();
            GiftItem item = relation.getItem();

            if (item != null) {
                if (isValidGiftItem(item)) {
                	item.setEnvironment(environment);
                    list.add(item);
                }
            }
        }

        return list;
    }
}
