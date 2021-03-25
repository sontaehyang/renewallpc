package saleson.shop.label;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.LabelType;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.utils.Message;
import saleson.model.Label;
import saleson.shop.item.support.ItemParam;
import saleson.shop.label.support.LabelDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/opmanager/label")
@RequestProperty(title="라벨관리", layout="default", template="opmanager")
public class LabelManagerController {
    private static final Logger log = LoggerFactory.getLogger(LabelManagerController.class);

    @Autowired
    private LabelService labelService;

    @Autowired
    LabelValidator validator;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EnumMapper enumMapper;

    /**
     * 라벨 목록 조회
     * @param labelDto
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("list")
    public String list(LabelDto labelDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        // 상품 라벨
        labelDto.setLabelType(LabelType.ITEM);

        Page<Label> pageContent = labelService.findAll(labelDto.getPredicate(), pageable);

        model.addAttribute("pageContent", pageContent);
        model.addAttribute("labelTypes", enumMapper.get("LabelType"));

        return ViewUtils.view();
    }

    /**
     * 라벨 등록
     * @param model
     * @return
     */
    @GetMapping("create")
    public String create(Model model) {

        model.addAttribute("label", new Label());
        model.addAttribute("labelTypes", enumMapper.get("LabelType"));

        return ViewUtils.getView("/label/form");
    }

    /**
     * 라벨 등록 처리
     * @param labelDto
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("create")
    public String createAction(@Valid LabelDto labelDto, Errors errors, Model model) {

        try {
            // 1. 데이터 처리
            Label label = modelMapper.map(labelDto, Label.class);

            // 2. 기본 입력 값 검증
            if (hasError(labelDto, errors, label, model)) {
                return ViewUtils.getView("/label/form");
            }

            // 3. 데이터 저장
            labelService.save(label, null);
        } catch (Exception e) {
            throw new UserException(e.getMessage(), "/opmanager/label/create");
        }

        return ViewUtils.redirect("/opmanager/label/list", "등록되었습니다.");
    }

    /**
     * 라벨 수정
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        // 1. 데이터 조회
        Label label = labelService.findById(id)
                .orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/label/list"));

        // 2. Model data
        model.addAttribute("label", label);
        model.addAttribute("labelTypes", enumMapper.get("LabelType"));

        return ViewUtils.getView("/label/form");
    }

    /**
     * 라벨 수정 처리
     * @param id
     * @param labelDto
     * @param errors
     * @param model
     * @return
     */
    @PostMapping("edit/{id}")
    public String editAction(@PathVariable("id") Long id, @Valid LabelDto labelDto, Errors errors, Model model) {

        try {
            // 1. 데이터 조회
            Label label = labelService.findById(id)
                    .orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/label/list"));

            // 2. 기본 입력 값 검증
            if (hasError(labelDto, errors, label, model)) {
                return ViewUtils.getView("/label/form");
            }

            // 3. ItemParam 세팅 (해당 라벨 사용중인 상품의 Json Data 일괄 수정)
            ItemParam itemParam = new ItemParam();
            itemParam.setItemLabelValue(labelService.getJsonValue(label));

            // 4. 데이터 저장
            modelMapper.map(labelDto, label);
            labelService.save(label, itemParam);
        } catch (Exception e) {
            throw new UserException(e.getMessage(), "/opmanager/label/edit/" + id);
        }

        return ViewUtils.redirect("/opmanager/label/list", "수정되었습니다.");
    }

    /**
     * 라벨 삭제
     * @param requestContext
     * @param ids
     * @return
     */
    @PostMapping("delete")
    public JsonView delete(RequestContext requestContext, @RequestParam("id") String[] ids) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        try {
            labelService.deleteByIds(ids);
        } catch (Exception e) {
            log.error("label delete error : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    /**
     * 라벨 이미지 삭제
     * @param requestContext
     * @param id
     * @return
     */
    /*@PostMapping("/delete-image")
    @RequestProperty(layout = "blank")
    public JsonView deleteImage(RequestContext requestContext,
                                    @RequestParam(name = "id") long id) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        try {
            Label label = labelService.findById(id)
                    .orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/label/edit/" + id));

            labelService.deleteImage(label);

            label.setImage(null);
            labelService.save(label);

            return JsonViewUtils.success();
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            return JsonViewUtils.exception("라벨 이미지 삭제에 실패 했습니다.");
        }

    }*/

    /**
     * 데이터 검증
     * @param labelDto
     * @param errors
     * @param label
     * @param model
     * @return
     */
    private boolean hasError(LabelDto labelDto, Errors errors, Label label, Model model) {
        // 1. 기본 입력 값 검증
        if (errors.hasErrors()) {
            Message.set(errors);
            model.addAttribute("label", label);
            return true;
        }

        // 2. 추가 로직 검증
        validator.validate(labelDto, errors);
        if (errors.hasErrors()) {
            Message.set(errors);
            model.addAttribute("label", label);
            return true;
        }

        return false;
    }
}
