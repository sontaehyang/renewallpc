package saleson.shop.stylebook;

import com.onlinepowers.framework.util.FlashMapUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import saleson.model.stylebook.StyleBook;
import saleson.shop.stylebook.support.StyleBookDto;

@Controller
@RequestMapping("/opmanager/style-book")
@RequestProperty(title = "스타일북 관리", layout = "default", template = "opmanager")
public class StyleBookManagerController {

    private static final Logger log = LoggerFactory.getLogger(StyleBookManagerController.class);

    @Autowired
    private StyleBookService styleBookService;

    @GetMapping("/list")
    public String list(Model model,
                       StyleBookDto styleBookDto,
                       @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable) {

        Page<StyleBook> pageContent = styleBookService.getStyleBookList(styleBookDto.getPredicate(), pageable);

        model.addAttribute("pageContent", pageContent);
        model.addAttribute("styleBookDto", styleBookDto);

        return "view";
    }

    @GetMapping("/create")
    public String create(Model model) {

        model.addAttribute("mode", "create");
        model.addAttribute("styleBook", new StyleBook());

        return "view";
    }

    @PostMapping("/create")
    public String createAction(StyleBook styleBook) {

        try {
            styleBookService.insertStyleBook(styleBook);
            FlashMapUtils.setMessage("등록되었습니다");
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            FlashMapUtils.setMessage("등록에 실패 했습니다.");
        }

        return "redirect:/opmanager/style-book/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {

        StyleBook styleBook = styleBookService.getStyleBookById(id);

        if (styleBook == null) {
            FlashMapUtils.setMessage("스타일 북이 없습니다.");
            return "redirect:/opmanager/style-book/list";
        }

        model.addAttribute("mode", "edit");
        model.addAttribute("styleBook", styleBook);

        return "view:/style-book/form";
    }

    @PostMapping("/edit/{id}")
    public String editAction(StyleBook styleBook) {

        try {
            styleBookService.updateStyleBook(styleBook);
            FlashMapUtils.setMessage("수정 되었습니다");
        } catch (Exception e) {
            log.error("ERROR: {}", e.getMessage(), e);
            FlashMapUtils.setMessage("수정에 실패 했습니다.");
        }

        return "redirect:/opmanager/style-book/list";
    }
}
