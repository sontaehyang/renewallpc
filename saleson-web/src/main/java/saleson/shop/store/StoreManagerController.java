package saleson.shop.store;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.CodeUtils;
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
import saleson.common.enumeration.StoreType;
import saleson.model.Store;
import saleson.shop.store.support.StoreDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/opmanager/store")
@RequestProperty(title="판매처 관리", layout="default")
public class StoreManagerController {

    private static final Logger log = LoggerFactory.getLogger(StoreManagerController.class);

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StoreService storeService;

    /**
     * 판매처 목록 조회
     * @param storeDto
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(StoreDto storeDto,
                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        Page<Store> pageContent = storeService.findAll(storeDto.getPredicate(), pageable);

        model.addAttribute("sidoList", CodeUtils.getCodeList("SIDO_LIST"));
        model.addAttribute("pageContent", pageContent);
        model.addAttribute("storeTypes", StoreType.values());

        return ViewUtils.view();
    }

    /**
     * 판매처 등록
     * @param model
     * @return
     */
    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("telCodes", CodeUtils.getCodeList("TEL"));
        model.addAttribute("store", new Store());
        model.addAttribute("storeTypes", StoreType.values());

        return ViewUtils.getView("/store/form");
    }

    /**
     * 판매처 등록 처리
     * @param storeDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public String createAction(@Valid StoreDto storeDto, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                throw new Exception("필수 입력 항목을 정확히 입력해 주세요.");
            }

            Store store = modelMapper.map(storeDto, Store.class);
            storeService.save(store);
        } catch (Exception e) {
            log.error("store create error : {}", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/store/list", "등록되었습니다.");
    }

    /**
     * 판매처 수정
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        Store store = storeService.findById(id)
                .orElseThrow(() -> new UserException("판매처 정보가 없습니다.", "/opmanager/store/list"));

        model.addAttribute("telCodes", CodeUtils.getCodeList("TEL"));
        model.addAttribute("store", store);
        model.addAttribute("storeTypes", StoreType.values());

        return ViewUtils.getView("/store/form");
    }

    /**
     * 판매처 수정 처리
     * @param id
     * @param storeDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/edit/{id}")
    public String editAction(@PathVariable("id") Long id,
                             @Valid StoreDto storeDto, BindingResult bindingResult) {

        try {
            if (bindingResult.hasErrors()) {
                throw new Exception("필수 입력 항목을 정확히 입력해 주세요.");
            }

            Store store = storeService.findById(id)
                    .orElseThrow(() -> new UserException("판매처 정보가 없습니다.", "/opmanager/store/list"));

            modelMapper.map(storeDto, store);
            storeService.save(store);
        } catch (Exception e) {
            log.error("store edit error : {}", e.getMessage(), e);
            throw new UserException(e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/store/edit/" + id, "수정되었습니다.");
    }

    /**
     * 판매처 삭제 처리
     * @param ids
     * @return
     */
    @PostMapping("/delete-list")
    public JsonView delete(@RequestParam("id") String[] ids) {

        try {
            storeService.deleteByIds(ids);
        } catch (Exception e) {
            log.error("store delete-list error : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }
}
