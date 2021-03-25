package saleson.shop.island;

import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.ListParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.utils.Message;
import saleson.model.Island;

import javax.validation.Valid;

@Controller
@RequestMapping("/opmanager/island")
@RequestProperty(template = "opmanager", title="제주/도서산간 주소 관리", layout="default")
public class IslandManagerController {

    @Autowired
    IslandService islandService;

    @Autowired
    EnumMapper enumMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IslandValidator islandValidator;

    @GetMapping("/list")
    public String list(@ModelAttribute IslandDto islandDto, Model model,
                                 @PageableDefault(sort="id", direction=Sort.Direction.DESC) Pageable pageable) {

        Page<Island> pageContent = islandService.findAll(islandDto.getPredicate(), pageable);

        model.addAttribute("pageContent", pageContent);

        return "view";
    }

    @GetMapping(value="/create")
    public String create(Model model) {

        model.addAttribute("island", new Island());
        return "view";
    }

    @PostMapping(value="/create")
    public String create(@Valid IslandDto islandDto, Errors errors, Model model) {
        Island island = modelMapper.map(islandDto, Island.class);

        if (hasError(islandDto, errors, island, model)) {
            return "view";
        }

        islandService.save(island);

        FlashMapUtils.setMessage("등록되었습니다.");
        return "redirect:/opmanager/island/list";
    }

    @GetMapping(value="/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        Island island = islandService.findById(id).orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/remote-addr/list"));

        model.addAttribute("island", island);
        return "view";
    }

    @PostMapping(value="/edit/{id}")
    public String edit(@PathVariable("id") Long id, @Valid IslandDto islandDto, Errors errors, Model model) {
        Island island = islandService.findById(id).orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/remote-addr/list"));

        if (hasError(islandDto, errors, island, model)) {
            return "view";
        }

        modelMapper.map(islandDto, island);

        islandService.save(island);

        FlashMapUtils.setMessage("수정되었습니다.");
        return "redirect:/opmanager/island/list";

    }

    @PostMapping(value="/delete")
    public String deleteListData(ListParam listParam) {

        islandService.deleteById(listParam);

        FlashMapUtils.setMessage("삭제되었습니다.");
        return "redirect:/opmanager/island/list";

    }

    private boolean hasError(IslandDto islandDto, Errors errors, Island island, Model model) {

        if (errors.hasErrors()) {
            Message.set(errors);
            model.addAttribute("island", island);
            return true;
        }

        islandValidator.validate(islandDto, errors);
        if (errors.hasErrors()) {
            Message.set(errors);
            model.addAttribute("island", island);
            return true;
        }

        return false;
    }

}
