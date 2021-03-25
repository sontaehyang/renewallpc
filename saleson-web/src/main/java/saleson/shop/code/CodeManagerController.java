package saleson.shop.code;

import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.MessageUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.pagination.Pagination;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.shop.code.domain.Code;
import saleson.shop.code.domain.CodeType;
import saleson.shop.code.support.CodeParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/opmanager/code")
@RequestProperty(title="CODE", layout="default")
public class CodeManagerController {

    private static final Logger log = LoggerFactory.getLogger(CodeManagerController.class);

    @Autowired
    private CodeService codeService1;

    /**
     * CODE 리스트
     * @param codeParam
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String commonCodeList(CodeParam codeParam, Model model) {

        int codeTypeCount = codeService1.getCodeTypeCount(codeParam);
        int codeCount = codeService1.getCodeCount(codeParam);

        Pagination pagination = Pagination.getInstance(codeCount);
        codeParam.setPagination(pagination);

        List<CodeType> codeTypeList = codeService1.getCodeTypeList(codeParam);
        List<Code> codeList = codeService1.getCodeList(codeParam);

        model.addAttribute("codeTypeCount", codeTypeCount);
        model.addAttribute("codeCount", codeCount);
        model.addAttribute("codeTypeList", codeTypeList);
        model.addAttribute("codeList", codeList);
        model.addAttribute("codeParam", codeParam);
        model.addAttribute("pagination", pagination);

        return ViewUtils.view();
    }

    /**
     * CODE 등록
     * @param model
     * @param code
     * @return
     */
    @GetMapping("create")
    @RequestProperty(layout="base", title="코드등록")
    public String codeInsert(Model model, Code code) {

        code.setCodeType(code.getWhereCodeType());

        model.addAttribute("code", code);

        return ViewUtils.getManagerView("/code/form");
    }

    /**
     * SYTEM 등록처리
     * @param code
     * @return
     */
    @PostMapping("create")
    public String codeInsertAction(Code code) {

        codeService1.insertCode(code);  // id 있는경우

        String message = MessageUtils.getMessage("M00632"); // 등록되었습니다
        String javascript = "opener.fnSearch(); self.close()";

        return ViewUtils.redirect("/opmanager/code/create", message, javascript);
    }

    /**
     * CODE 수정
     * @param model
     * @param code
     * @return
     */
    @GetMapping("edit")
    @RequestProperty(layout="base", title="코드수정")
    public String codeUpdate(Model model, Code code) {


        Map<String, Object> params = new HashedMap();
        params.put("codeType", code.getWhereCodeType());
        params.put("id", code.getWhereId());

        model.addAttribute("code", codeService1.getCodeById(params));

        return ViewUtils.getManagerView("/code/form");
    }

    /**
     * CODE 수정처리
     * @param code
     * @return
     */
    @PostMapping("edit")
    public String codeUpdateAction(Code code) {

        codeService1.updateCode(code);

        String javascript = "opener.fnSearch(); self.close()";
        String message = MessageUtils.getMessage("M01673"); // 수정되었습니다 ;

        return ViewUtils.redirect("/opmanager/code/create", message, javascript);
    }

    /**
     * CODE 데이터 삭제
     * @param requestContext
     * @param code
     * @code
     */
    @PostMapping("delete")
    public JsonView deleteListData(RequestContext requestContext, Code code) {

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        codeService1.deleteCode(code);
        return JsonViewUtils.success();
    }


}
