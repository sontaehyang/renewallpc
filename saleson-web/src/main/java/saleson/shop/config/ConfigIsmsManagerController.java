package saleson.shop.config;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinepowers.framework.context.RequestContext;
import com.onlinepowers.framework.exception.NotAjaxRequestException;
import com.onlinepowers.framework.isms.ConfigIsmsService;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.domain.SearchParam;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import saleson.shop.order.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/opmanager/isms/")
@RequestProperty(layout="default")
public class ConfigIsmsManagerController {

	private static final Logger log = LoggerFactory.getLogger(ConfigIsmsManagerController.class);
	
	
    @Autowired
    ConfigIsmsService configIsmsService;

    /**
     * ismsConfig 조회ismsConfig
     * @return
     */
    @GetMapping(value="isms-config")
    //@RequestProperty(title="isms 시간 설정")
    public String ismsConfig(SearchParam searchParam , Model model){

        model.addAttribute("getIsmsList", configIsmsService.getIsmsList());

        return ViewUtils.view();
    }

    /**
     * ISMS 시간설정 변경
     * @param requestContext
     * @param session
     * @return
     */
    @PostMapping("isms-config")
    public JsonView updateIsmsConfig(RequestContext requestContext
            , HttpServletRequest request
            , HttpSession session
            , HttpServletResponse response){

        if (!requestContext.isAjaxRequest()) {
            throw new NotAjaxRequestException();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            String data = request.getParameterMap().get("data")[0].toString();
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> objList = mapper.readValue(data, new TypeReference<List<Map<String, Object>>>(){});

            configIsmsService.updateIsmsConfig(objList);

        } catch (JsonGenerationException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return JsonViewUtils.success();
    }

}
