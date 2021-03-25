package saleson.shop.categoriesfilter;

import com.onlinepowers.framework.file.service.FileService;
import com.onlinepowers.framework.sequence.service.SequenceService;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.FileUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
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
import saleson.common.enumeration.FilterType;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.RandomStringUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.CategoriesFilter;
import saleson.model.FilterCode;
import saleson.model.FilterGroup;
import saleson.model.ItemFilter;
import saleson.shop.categories.CategoriesMapper;
import saleson.shop.categories.CategoriesService;
import saleson.shop.categories.domain.Breadcrumb;
import saleson.shop.categories.domain.BreadcrumbCategory;
import saleson.shop.categories.domain.Categories;
import saleson.shop.categoriesfilter.support.CategoriesFilterDto;
import saleson.shop.categoriesfilter.support.ItemFilterDto;
import saleson.shop.item.ItemService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service("categoriesFilterService")
public class CategoriesFilterServiceImpl implements CategoriesFilterService {

    private static final Logger log = LoggerFactory.getLogger(CategoriesFilterServiceImpl.class);

    @Autowired
    SequenceService sequenceService;

    @Autowired
    private CategoriesFilterRepository categoriesFilterRepository;

    @Autowired
    private FilterGroupRepository filterGroupRepository;

    @Autowired
    private FilterCodeRepository filterCodeRepository;

    @Autowired
    private ItemFilterRepository itemFilterRepository;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    Environment environment;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Override
    public Page<FilterGroup> getFilterGroupList(Predicate predicate, Pageable pageable) {
        return filterGroupRepository.findAll(predicate, pageable);
    }

    @Override
    public Optional<FilterGroup> getFilterGroupById(Long id) {
        return filterGroupRepository.findById(id);
    }

    @Override
    public void saveFilter(FilterGroup filterGroup, List<FilterCode> newCodeList) throws Exception {

        try {

            if (filterGroup.getCodeList() != null && !filterGroup.getCodeList().isEmpty()) {

                List<Long> deleteList = new ArrayList<>();

                // (edit mode) 새로 입력된 filter code <-> 원본 filter code 비교
                if (newCodeList != null && !newCodeList.isEmpty()) {
                    for (FilterCode news : newCodeList) {
                        for (FilterCode original : filterGroup.getCodeList()) {
                            if (original.getId().equals(news.getId())) {
                                // 1. 필수 데이터 세팅
                                if (!FilterType.COLOR.equals(filterGroup.getFilterType())) {
                                    news.setLabelCode(null);
                                }

                                news.setCreated(original.getCreated());
                                news.setCreatedBy(original.getCreatedBy());

                                // 2. delete -> insert 필요한 데이터 id 추출 & version 세팅
                                if (isDelete(filterGroup.getFilterType(), original, news)) {
                                    deleteList.add(original.getId());
                                    news.setVersion(0L);

                                    // 기존 파일 삭제
                                    if (!StringUtils.isEmpty(original.getLabelImage())) {
                                        String uploadPath = File.separator + original.getImageUploadPath() + ShopUtils.unescapeHtml(original.getLabelImage());
                                        FileUtils.delete(uploadPath);
                                    }

                                } else {
                                    news.setVersion(original.getVersion());

                                    if (FilterType.IMAGE.equals(filterGroup.getFilterType()) && !"".equals(original.getLabelImage())) {
                                        news.setLabelImage(original.getLabelImage());
                                    }
                                }

                                // 3. 정제가 끝난 데이터 remove
                                filterGroup.getCodeList().remove(original);
                                break;
                            }
                        }
                    }

                    // 4. 3번에서 걸러지지 않은 원본 데이터 (form에서 제거된 데이터) id 추출
                    if (filterGroup.getCodeList() != null && !filterGroup.getCodeList().isEmpty()) {
                        filterGroup.getCodeList().forEach(original -> {
                            deleteList.add(original.getId());

                            // 기존 파일 삭제
                            if (!StringUtils.isEmpty(original.getLabelImage())) {
                                String uploadPath = File.separator + original.getImageUploadPath() + ShopUtils.unescapeHtml(original.getLabelImage());
                                FileUtils.delete(uploadPath);
                            }
                        });
                    }

                    // 5. 처리 후 새로 입력한 데이터 덮어쓰기
                    filterGroup.setCodeList(newCodeList);
                }

                // delete list 존재시, filter code & item filter 삭제
                if (deleteList != null && !deleteList.isEmpty()) {
                    filterCodeRepository.deleteByFilterCodeIds(deleteList);
                    itemFilterRepository.deleteByFilterCodeIds(deleteList);
                }

                Integer ordering = 1;
                for (FilterCode filterCode : filterGroup.getCodeList()) {
                    filterCode.setOrdering(ordering++);

                    if (filterCode.getImageFile().getSize() > 0) {
                        filterCode.setLabelImage(saveImageFile(filterCode));
                    }
                }

                // 데이터 저장
                filterGroupRepository.save(filterGroup);
            } else {
                throw new Exception("잘못된 접근입니다.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public void deleteFilter(String[] idArray) throws Exception {

        try {

            if (idArray != null && idArray.length > 0) {
                // paramter 설정
                List<Long> ids = Arrays.stream(idArray)
                        .map(i -> Long.parseLong(i))
                        .collect(Collectors.toList());

                List<FilterGroup> filterGroupList = filterGroupRepository.findAllById(ids);
                for (FilterGroup filterGroup : filterGroupList) {
                    for (FilterCode filterCode : filterGroup.getCodeList()) {
                        // 파일 삭제
                        if (!StringUtils.isEmpty(filterCode.getLabelImage())) {
                            String uploadPath = File.separator + filterCode.getImageUploadPath() + ShopUtils.unescapeHtml(filterCode.getLabelImage());
                            FileUtils.delete(uploadPath);
                        }

                    }
                }

                // filter code 삭제
                filterCodeRepository.deleteByFilterGroupIds(ids);

                // category filter 삭제
                categoriesFilterRepository.deleteByFilterGroupIds(ids);

                // item filter 삭제
                itemFilterRepository.deleteByFilterGroupIds(ids);

                // filter group 삭제
                filterGroupRepository.deleteByIds(ids);
            } else {
                throw new Exception("잘못된 접근입니다.");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public List<FilterGroup> getBreadcrumbFilterGroupList(int categoryId) {

        List<FilterGroup> filterGroups = new ArrayList<>();

        Categories categories = categoriesService.getCategoriesById(categoryId);

        if (categories == null ) {
            return new ArrayList<>();
        }

        List<Breadcrumb> breadcrumbs = categoriesService.getBreadcrumbListByCategoryUrl(categories.getCategoryUrl());

        if (breadcrumbs != null && !breadcrumbs.isEmpty()) {

            List<BreadcrumbCategory> breadcrumbCategories = breadcrumbs.get(0).getBreadcrumbCategories();

            if (breadcrumbCategories != null && !breadcrumbCategories.isEmpty()) {

                List<Long> addIdList = new ArrayList<>();

                for (BreadcrumbCategory breadcrumbCategory : breadcrumbCategories) {

                    try {
                        int id = Integer.parseInt(breadcrumbCategory.getCategoryId());
                        List<FilterGroup> tempList = getFilterGroupList(id);

                        tempList.stream().forEach(t ->{
                            if(!addIdList.contains(t.getId())) {

                                t.setCodeList(t.getCodeList().stream()
                                        .sorted(Comparator.comparing(FilterCode::getOrdering))
                                        .collect(Collectors.toList()));

                                filterGroups.add(t);
                                addIdList.add(t.getId());
                            }
                        });

                    } catch (Exception ignore) {}
                }
            }
        }

        return filterGroups;
    }

    @Override
    public List<FilterGroup> getFilterGroupList(int categoryId) {

        CategoriesFilterDto dto = new CategoriesFilterDto();
        dto.setCategoryId(categoryId);

        Iterable<CategoriesFilter> iterable = categoriesFilterRepository.findAll(dto.getPredicate());

        List<Long> ids = new ArrayList<>();

        iterable.forEach(categoriesFilter -> {
            ids.add(categoriesFilter.getFilterGroupId());
        });

        return filterGroupRepository.findAllById(ids);
    }

    @Override
    public Iterable<ItemFilter> getItemFilterList(int itemId) {
        ItemFilterDto itemFilterDto = new ItemFilterDto();
        itemFilterDto.setItemId(itemId);
        return itemFilterRepository.findAll(itemFilterDto.getPredicate());
    }

    @Override
    public void saveCategoriesFilter(int categoryId, Set<Long> filterGroupIdSet) {

        if (categoryId > 0) {

            boolean flag = filterGroupIdSet != null && !filterGroupIdSet.isEmpty();
            List<Long> keepFilterGroupIds = new ArrayList<>();

            if (flag) {

                List<FilterGroup> filterGroups = getFilterGroupList(categoryId);

                List<Long> filterGroupIds = new ArrayList<>();

                filterGroups.forEach( fg ->{
                    filterGroupIds.add(fg.getId());
                });


                for (Long id : filterGroupIdSet) {
                    boolean keepFlag = false;
                    for (Long searchId : filterGroupIds) {
                        if (searchId.equals(id)) {
                            keepFlag = true;
                        }
                    }

                    if (keepFlag) {
                        keepFilterGroupIds.add(id);
                    }
                }
            }

            deleteCategoriesFilterByCategoryId(categoryId, keepFilterGroupIds);

            if (flag) {

                int ordering = 0;
                for (Long id : filterGroupIdSet) {
                    CategoriesFilter categoriesFilter = new CategoriesFilter();
                    categoriesFilter.setCategoryId(categoryId);
                    categoriesFilter.setFilterGroupId(id);
                    categoriesFilter.setOrdering(ordering);
                    ordering++;

                    categoriesFilterRepository.save(categoriesFilter);
                }
            }
        }
    }

    private void deleteCategoriesFilterByCategoryId(int categoryId, List<Long> keepFilterGroupIds) {

        List<Breadcrumb> breadcrumbs = categoriesService.getBreadcrumbListByCategoryId(categoryId);
        List<BreadcrumbCategory> breadcrumbCategories = new ArrayList<>();
        if (breadcrumbs != null && !breadcrumbs.isEmpty()) {
            breadcrumbCategories = breadcrumbs.get(0).getBreadcrumbCategories();
        }

        for (BreadcrumbCategory breadcrumbCategory : breadcrumbCategories) {
            int breadcrumbCategoryId = Integer.parseInt(breadcrumbCategory.getCategoryId());
            if (breadcrumbCategoryId == categoryId) {
                continue;
            }

            List<FilterGroup> filterGroups = getFilterGroupList(breadcrumbCategoryId);
            List<Long> ids = new ArrayList<>();

            filterGroups.forEach(fg -> {
                Long id = fg.getId();
                if (!keepFilterGroupIds.contains(id)) {
                    ids.add(id);
                }
            });

            keepFilterGroupIds.addAll(ids);
        }

        List<Integer> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId);

        if (keepFilterGroupIds != null && !keepFilterGroupIds.isEmpty()) {
            itemFilterRepository.deleteByCategoryId(categoryIds, keepFilterGroupIds);
        } else {
            itemFilterRepository.deleteByCategoryId(categoryIds);
        }

        CategoriesFilterDto dto = new CategoriesFilterDto();
        dto.setCategoryId(categoryId);

        Iterable<CategoriesFilter> iterable = categoriesFilterRepository.findAll(dto.getPredicate());

        categoriesFilterRepository.deleteAll(iterable);
    }

    @Override
    public void saveItemFilter(int itemId, Map<String, List<String>> filterCodes) {
        if (itemId > 0) {

            itemFilterRepository.deleteByItemId(itemId);

            if (filterCodes != null) {
                Iterator<String> iter = filterCodes.keySet().iterator();
                while(iter.hasNext()){
                    Long filterGroupId = Long.parseLong(iter.next());
                    List<String> filterCodeIds = (ArrayList<String>)filterCodes.get(Long.toString(filterGroupId));
                    int ordering = 0;
                    for(int i = 0; i < filterCodeIds.size(); i++){
                        ItemFilter itemFilter = new ItemFilter();
                        itemFilter.setItemId(itemId);
                        itemFilter.setFilterGroupId(filterGroupId);
                        itemFilter.setFilterCodeId(Long.parseLong(filterCodeIds.get(i)));
                        itemFilter.setOrdering(ordering);
                        ordering++;
                        itemFilterRepository.save(itemFilter);
                    }
                }
            }
        }
    }

    @Override
    public Iterable<CategoriesFilter> getCategoriesFilters(CategoriesFilterDto dto) {
        return categoriesFilterRepository.findAll(dto.getPredicate());
    }

    @Override
    public void initCategoryFilter(Categories categories, List<Categories> childCategories) {

        int categoryId = categories.getCategoryId();

        List<Integer> targetCategoryIds = new ArrayList<>();

        targetCategoryIds.add(categoryId);

        for (Categories child : childCategories) {
            targetCategoryIds.add(child.getCategoryId());
        }

        // 카테고리 필터 영역 삭제
        if (!targetCategoryIds.isEmpty()) {

            itemFilterRepository.deleteByCategoryId(targetCategoryIds);

            categoriesFilterRepository.deleteByCategoryIds(targetCategoryIds);
        }

    }

    /**
     * Filter Code 삭제 조건 (true / false)
     * @param filterType
     * @param original
     * @param news
     * @return
     */
    private boolean isDelete(FilterType filterType, FilterCode original, FilterCode news) {
        boolean isEqualsLabelCode = CommonUtils.dataNvl(original.getLabelCode()).equals(CommonUtils.dataNvl(news.getLabelCode()));
        boolean isEqualsLabel = CommonUtils.dataNvl(original.getLabel()).equals(CommonUtils.dataNvl(news.getLabel()));
        boolean isEqualsLabelImage = true;

        if (!"".equals(CommonUtils.dataNvl(original.getLabelImage())) && news.getImageFile().getSize() > 0) {
            isEqualsLabelImage = false;
        }

        if (FilterType.COLOR.equals(filterType) && !isEqualsLabelCode) {
            return true;                // 필터 타입 '색상' -> 라벨 코드 비교
        } else if (FilterType.IMAGE.equals(filterType) && !isEqualsLabelImage) {
            return true;                // 필터 타입 '이미지' -> 라벨 이미지 비교
        } else if ((!FilterType.COLOR.equals(filterType) && !FilterType.IMAGE.equals(filterType)) && !(isEqualsLabel && isEqualsLabelCode)) {
            return true;                // 그 외 -> 라벨, 라벨 코드 비교
        }

        return false;
    }

    private String saveImageFile(FilterCode filterCode) throws Exception {

        String filePath = "";
        MultipartFile imageFile = filterCode.getImageFile();

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
            String uploadPath = FileUtils.getDefaultUploadPath() + File.separator + filterCode.getImageUploadPath();

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
        }

        return filePath;
    }
}
