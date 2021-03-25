package saleson.shop.categoriesfilter;

import com.onlinepowers.framework.context.util.RequestContextUtils;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.FilterType;
import saleson.model.FilterCode;
import saleson.model.FilterGroup;
import saleson.shop.categoriesfilter.support.FilterGroupDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/opmanager/categories-filter")
@RequestProperty(title="카테고리 필터 관리", layout="default")
public class CategoriesFilterManagerController {

    private static final Logger log = LoggerFactory.getLogger(CategoriesFilterManagerController.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoriesFilterService categoriesFilterService;

    /**
     * 카테고리 필터 리스트
     * @param filterGroupDto
     * @param target
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(FilterGroupDto filterGroupDto,
                       @RequestParam(value = "target", defaultValue = "normal") String target,
                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        Page<FilterGroup> pageContent = categoriesFilterService.getFilterGroupList(filterGroupDto.getPredicate(), pageable);

        if ("popup".equals(target)) {
            RequestContextUtils.setLayout("base");
        }

        model.addAttribute("pageContent", pageContent);
        model.addAttribute("filterTypes", FilterType.values());
        model.addAttribute("target", target);

        return ViewUtils.view();
    }

    /**
     * 카테고리 필터 등록
     * @param target
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String create(@RequestParam(value = "target", defaultValue = "normal") String target, Model model) {

        if ("popup".equals(target)) {
            RequestContextUtils.setLayout("base");
        }

        model.addAttribute("filterGroup", new FilterGroup());
        model.addAttribute("filterTypes", FilterType.values());

        model.addAttribute("color", FilterType.COLOR);
        model.addAttribute("image", FilterType.IMAGE);
        model.addAttribute("target", target);

        return ViewUtils.getView("/categories-filter/form");
    }

    /**
     * 카테고리 필터 등록 처리
     * @param target
     * @param filterGroupDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public String createAction(@RequestParam(value = "target", defaultValue = "normal") String target,
                               @Valid FilterGroupDto filterGroupDto, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                throw new Exception("필수 입력 항목을 정확히 입력해 주세요.");
            }

            FilterGroup filterGroup = modelMapper.map(filterGroupDto, FilterGroup.class);

            List<FilterCode> newCodeList = new ArrayList<>();
            filterGroupDto.getCodeList().forEach(newCode -> {
                if (newCode.getLabel() != null) {
                    newCodeList.add(newCode);
                }
            });

            filterGroup.setCodeList(newCodeList);

            categoriesFilterService.saveFilter(filterGroup, null);
        } catch (Exception e) {
            log.error("categories-filter create error : {}", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/categories-filter/list?target=" + target, "등록되었습니다.");
    }

    /**
     * 카테고리 필터 수정
     * @param id
     * @param target
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @RequestParam(value = "target", defaultValue = "normal") String target, Model model) {

        FilterGroup filterGroup = categoriesFilterService.getFilterGroupById(id)
                .orElseThrow(() -> new UserException("필터 정보가 없습니다.", "/opmanager/categories-filter/list?target=" + target));

        if ("popup".equals(target)) {
            RequestContextUtils.setLayout("base");
        }

        List<FilterCode> filterCodeList = filterGroup.getCodeList().stream()
                .sorted(Comparator.comparing(FilterCode::getOrdering))
                .collect(Collectors.toList());

        filterGroup.setCodeList(filterCodeList);


        model.addAttribute("filterGroup", filterGroup);
        model.addAttribute("filterTypes", FilterType.values());

        model.addAttribute("color", FilterType.COLOR);
        model.addAttribute("image", FilterType.IMAGE);
        model.addAttribute("target", target);

        return ViewUtils.getView("/categories-filter/form");
    }

    /**
     * 카테고리 필터 수정 처리
     * @param id
     * @param target
     * @param filterGroupDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit/{id}")
    public String editAction(@PathVariable("id") Long id,
                             @RequestParam(value = "target", defaultValue = "normal") String target,
                             @Valid FilterGroupDto filterGroupDto, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                throw new Exception("필수 입력 항목을 정확히 입력해 주세요.");
            }

            FilterGroup filterGroup = categoriesFilterService.getFilterGroupById(id)
                    .orElseThrow(() -> new UserException("필터 정보가 없습니다.", "/opmanager/categories-filter/list?target=" + target));

            // filter code 매핑 제외
            if (modelMapper.getTypeMap(FilterGroupDto.class, FilterGroup.class) == null) {
                modelMapper.createTypeMap(FilterGroupDto.class, FilterGroup.class).addMappings(mapping -> {
                    mapping.skip(FilterGroup::setCodeList);
                });
            }

            modelMapper.map(filterGroupDto, filterGroup);

            List<FilterCode> newCodeList = new ArrayList<>();
            filterGroupDto.getCodeList().forEach(newCode -> {
                if (newCode.getLabel() != null) {
                    newCodeList.add(newCode);
                }
            });

            categoriesFilterService.saveFilter(filterGroup, newCodeList);
        } catch (Exception e) {
            log.error("categories-filter edit error : {}", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/categories-filter/edit/" + id + "?target=" + target, "수정되었습니다.");
    }

    /**
     * 카테고리 필터 삭제 처리
     * @param ids
     * @return
     */
    @PostMapping("/delete-list")
    public JsonView delete(@RequestParam("id") String[] ids) {

        try {
            categoriesFilterService.deleteFilter(ids);
        } catch (Exception e) {
            log.error("categories-filter delete-list error : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    @GetMapping("/filterGroupList")
    public JsonView getBreadcrumbFilterGroupList(@RequestParam(name = "categoryId")int categoryId){
        return JsonViewUtils.success(categoriesFilterService.getBreadcrumbFilterGroupList(categoryId));
    }

    @GetMapping("/getItemFilterList")
    public JsonView getItemFilterList(@RequestParam(name = "itemId")int itemId){
        return JsonViewUtils.success(categoriesFilterService.getItemFilterList(itemId));
    }


}
