package saleson.shop.label;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.querydsl.core.types.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.Const;
import saleson.common.utils.RandomStringUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.Label;
import saleson.shop.item.ItemService;
import saleson.shop.item.support.ItemParam;
import saleson.shop.label.domain.LabelJson;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("labelService")
public class LabelServiceImpl implements LabelService {

    private static final Logger log = LoggerFactory.getLogger(LabelServiceImpl.class);

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ItemService itemService;

    @Override
    public Page<Label> findAll(Predicate predicate, Pageable pageable) {
        return labelRepository.findAll(predicate, pageable);
    }

    @Override
    public List<Label> findAll(Predicate predicate) {
        Iterable<Label> iterable = labelRepository.findAll(predicate);

        List<Label> labels = StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());

        return labels;
    }

    @Override
    public Optional<Label> findById(Long id) {
        return labelRepository.findById(id);
    }

    @Override
    public void save(Label label, ItemParam itemParam) throws Exception {

        try {
            // 라벨 이미지 처리
            saveImage(label);

            // 라벨 수정시, 해당 라벨 사용중인 상품의 Json Data 일괄 수정 (replace)
            if (itemParam != null) {
                itemParam.setNewItemLabelValue(getJsonValue(label));
                itemService.updateItemLabelValue(itemParam);
            }

            labelRepository.save(label);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteByIds(String[] idArray) throws Exception {

        try {
            if (idArray != null && idArray.length > 0) {
                List<Long> ids = Arrays.stream(idArray).map(i -> Long.parseLong(i)).collect(Collectors.toList());

                // 이미지 삭제 & 해당 라벨 사용중인 상품의 Json Data 일괄 수정 (replace)
                List<Label> labels = labelRepository.findAllById(ids);
                labels.stream()
                    .filter(label -> !StringUtils.isEmpty(label.getImage()))
                    .forEach(label -> {
                        deleteImage(label);

                        ItemParam itemParam = new ItemParam();
                        itemParam.setItemLabelValue(getJsonValue(label));
                        itemService.deleteItemLabelValue(itemParam);
                    });

                // 데이터 삭제
                labelRepository.deleteByIds(ids);
            } else {
                throw new Exception("잘못된 접근입니다.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

    }

    @Override
    public void deleteImage(Label label) {
        String uploadPath = File.separator + label.getImageUploadPath() + ShopUtils.unescapeHtml(label.getImage());
        FileUtils.delete(uploadPath);
    }

    // 파일 업로드
    private void saveImage(Label label) throws Exception {
        MultipartFile imageFile = label.getImageFile();

        if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() > 0) {
            // 이전에 등록된 이미지 삭제
            deleteImage(label);

            String extension = FileUtils.getExtension(imageFile.getOriginalFilename());
            int maxSize = 10 * 1024 * 1024; // 업로드 가능한 최대 용량 : 10MB

            String[] AVAILABLE_EXTENSION = { "jpg", "jpeg", "png" };

            boolean extensionFlag = false;

            for (int i = 0 ; i < AVAILABLE_EXTENSION.length ; i++) {
                if (extension.equals(AVAILABLE_EXTENSION[i])) {
                    extensionFlag = true;
                }
            }

            if (!extensionFlag) {
                throw new IOException("유효하지 않은 파일입니다.");
            }

            if (maxSize < imageFile.getSize()) {
                throw new IOException("업로드 가능한 최대 용량 : 10MB 입니다");
            }

            String date = DateUtils.getToday(Const.DATETIME_FORMAT);
            String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + label.getImageUploadPath();

            StringBuffer sb;

            fileService.makeUploadPath(uploadPath);
            BufferedImage bufferedImage = null;

            try {
                bufferedImage = ImageIO.read(imageFile.getInputStream());
            } catch (IOException e1) {
                throw new IOException("이미지 읽어오기 실패 : {}", e1);
            }

            if (bufferedImage == null) {
                throw new IOException("이미지 읽어오기 실패 : bufferedImage null");
            }

            sb = new StringBuffer();
            sb.append(RandomStringUtils.getRandomString(date, 0,5));
            sb.append(".");
            sb.append(FileUtils.getExtension(imageFile.getOriginalFilename()));
            String defaultFileName = sb.toString();
            String newFileName = FileUtils.getNewFileName(uploadPath, defaultFileName);

            File saveFile = new File(uploadPath + File.separator + newFileName);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.flush();
            ImageIO.write(bufferedImage, FileUtils.getExtension(imageFile.getOriginalFilename()), output);
            output.close();
            FileCopyUtils.copy(output.toByteArray(), saveFile);

            // 이미지 변경정보 set
            label.setImage(saveFile.getName());
        }
    }

    @Override
    public String getJsonValue(String[] idArray) {
        List<Long> ids = Arrays.stream(idArray).map(i -> Long.parseLong(i)).collect(Collectors.toList());
        List<Label> labels = labelRepository.findAllById(ids);

        String jsonValue = null;

        if (labels != null && !labels.isEmpty()) {
            List<LabelJson> labelJsons = new ArrayList<>();

            for (Label label : labels) {
                LabelJson labelJson = new LabelJson();

                labelJson.setId(label.getId());
                labelJson.setImageSrc(label.getImageSrc());
                labelJson.setDescription(label.getDescription());
                labelJson.setLabelType(label.getLabelType());

                labelJsons.add(labelJson);
            }

            if (labelJsons != null && !labelJsons.isEmpty()) {
                jsonValue = JsonViewUtils.objectToJson(labelJsons);
            }
        }

        return jsonValue;
    }

    @Override
    public String getJsonValue(Label label) {
        String jsonValue = "";

        if (label.getId() != null) {
            LabelJson labelJson = new LabelJson();

            labelJson.setId(label.getId());
            labelJson.setImageSrc(label.getImageSrc());
            labelJson.setDescription(label.getDescription());
            labelJson.setLabelType(label.getLabelType());

            jsonValue = JsonViewUtils.objectToJson(labelJson);
        }

        return jsonValue;
    }
}
