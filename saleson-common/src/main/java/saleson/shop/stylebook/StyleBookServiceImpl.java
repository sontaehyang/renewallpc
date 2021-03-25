package saleson.shop.stylebook;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.types.Predicate;
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
import saleson.model.stylebook.StyleBook;
import saleson.model.stylebook.StyleBookItem;
import saleson.shop.item.ItemService;
import saleson.shop.item.domain.Item;
import saleson.shop.item.support.ItemParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("styleBookService")
public class StyleBookServiceImpl implements StyleBookService{

    private static final Logger log = LoggerFactory.getLogger(StyleBookServiceImpl.class);

    @Autowired
    private StyleBookRepository styleBookRepository;

    @Autowired
    private StyleBookItemRepository styleBookItemRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ItemService itemService;

    @Override
    public void insertStyleBook(StyleBook styleBook) throws Exception{

        styleBook.setImage(saveImageFile(styleBook));

        setStyleBookItem(styleBook);

        styleBookRepository.save(styleBook);
    }

    @Override
    public void updateStyleBook(StyleBook styleBook) throws Exception{

        StyleBook storeStyleBook = getStyleBookById(styleBook.getId());

        if (storeStyleBook != null) {

            storeStyleBook.setTitle(styleBook.getTitle());
            storeStyleBook.setContent(styleBook.getContent());
            storeStyleBook.setDisplayItemIds(styleBook.getDisplayItemIds());
            storeStyleBook.setImageFile(styleBook.getImageFile());

            String filePath = saveImageFile(storeStyleBook);

            if (!StringUtils.isEmpty(filePath)) {
                storeStyleBook.setImage(filePath);
            }

            setStyleBookItem(storeStyleBook);

            styleBookRepository.save(storeStyleBook);

        }
    }

    private void setStyleBookItem(StyleBook styleBook) {

        List<Integer> itemIds = styleBook.getDisplayItemIds();
        List<StyleBookItem> items = styleBook.getItems();

        List<StyleBookItem> saveItems = new ArrayList<>();

        // 기존 상품 정보 삭제
        if (items != null && !items.isEmpty()) {
            styleBookItemRepository.deleteAll(items);
        }
        // 상품 정보 재 등록
        if (itemIds != null && !itemIds.isEmpty()) {
            int ordering = 0;
            for (Integer id : itemIds) {

                if (id == null) {
                    continue;
                }

                saveItems.add(new StyleBookItem(id, ordering));
                ordering++;
            }

            styleBook.setItems(saveItems);
        }
    }

    private String saveImageFile(StyleBook styleBook) throws Exception {

        String filePath = "";
        MultipartFile imageFile = styleBook.getImageFile();
        String orgImage = styleBook.getImage();

        if (imageFile != null && !imageFile.isEmpty() && imageFile.getSize() > 0) {

            String extension = FileUtils.getExtension(imageFile.getOriginalFilename());
            int maxSize = 5 * 1024 * 1024; // 업로드 가능한 최대 용량 : 5MB

            String[] AVAILABLE_EXTENSION = { "jpg", "jpeg", "png" };

            boolean extenstionFlag = false;

            for (int i = 0 ; i < AVAILABLE_EXTENSION.length ; i++) {
                if (extension.equals(AVAILABLE_EXTENSION[i])) {
                    extenstionFlag = true;
                }
            }

            if (!extenstionFlag) {
                throw new IOException("유효하지 않은 파일입니다.");
            }

            if (maxSize < imageFile.getSize()) {
                throw new IOException("업로드 가능한 최대 용량 : 5MB 입니다");
            }

            String date = DateUtils.getToday(Const.DATETIME_FORMAT);
            String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + styleBook.getImageUploadPath();

            StringBuffer sb;

            fileService.makeUploadPath(uploadPath);
            BufferedImage bufferedImage = null;

            try {
                bufferedImage = ImageIO.read(imageFile.getInputStream());
            } catch (IOException e1) {
                throw new IOException("이미지 읽어오기 실패 - ERROR");
            }

            if (bufferedImage == null) {
                throw new IOException("이미지 읽어오기 실패 - ERROR");
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

            filePath = saveFile.getName();

            // 기존 파일 삭제
            try {
                if (!StringUtils.isEmpty(orgImage)) {
                    FileUtils.delete(uploadPath, ShopUtils.unescapeHtml(orgImage));
                }
            } catch (IOException e1) {
                log.error("delete style book image error {}", e1.getMessage(), e1);
            }
        }

        return filePath;
    }

    @Override
    public void deleteStyleBookById(long id) {

        StyleBook styleBook = getStyleBookById(id);

        if (styleBook != null) {

            List<StyleBookItem> items = styleBook.getItems();

            if (items != null && !items.isEmpty()) {
                styleBookItemRepository.deleteAll(items);
            }

            styleBookRepository.deleteById(id);
        }
    }

    @Override
    public Page<StyleBook> getStyleBookList(Predicate predicate, Pageable pageable) {
        return styleBookRepository.findAll(predicate, pageable);
    }

    @Override
    public StyleBook getStyleBookById(long id) {

        StyleBook styleBook = styleBookRepository.findById(id)
                .orElse(null);

        setItemList(styleBook);

        return styleBook;
    }

    @Override
    public void setItemList(StyleBook styleBook) {

        if (styleBook != null) {
            List<StyleBookItem> items = styleBook.getItems();

            if (items != null && !items.isEmpty()) {
                ItemParam itemParam = new ItemParam();
                itemParam.setStyleBookId(styleBook.getId());

                List<Item> searchItemList = itemService.getItemList(itemParam);

                if (searchItemList != null && !searchItemList.isEmpty()) {

                    for (StyleBookItem styleBookItem : items) {

                        for (Item item : searchItemList) {

                            if (styleBookItem.getItemId() != null) {
                                int itemId = styleBookItem.getItemId().intValue();

                                if (itemId == item.getItemId()) {
                                    styleBookItem.setItem(item);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
