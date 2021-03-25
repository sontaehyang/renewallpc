package com.onlinepowers.demo;

import com.onlinepowers.demo.domain.CommonCode;
import com.onlinepowers.demo.support.DemoUtils;
import com.onlinepowers.framework.exception.OpRuntimeException;
import com.onlinepowers.framework.i18n.support.CodeResolver;
import com.onlinepowers.framework.notification.NotificationService;
import com.onlinepowers.framework.properties.support.RefreshableProperies;
import com.onlinepowers.framework.repository.CodeInfo;
import com.onlinepowers.framework.repository.support.EarlyLoadingCodeInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingMessageInfoRepository;
import com.onlinepowers.framework.repository.support.EarlyLoadingRepositoryEvent;
import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import saleson.common.notification.message.ShopMailMessage;
import saleson.common.utils.ShopUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private final Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @Autowired(required = true)
    private CodeResolver codeResolver;

    @Autowired
    private EarlyLoadingCodeInfoRepository codeInfoRepository;

    @Autowired
    private EarlyLoadingMessageInfoRepository messageInfoRepository;

    @Autowired
    private RefreshableProperies property;

    @RequestProperty(title="메세지", layout="blank")
    @GetMapping(value="your-ip")
    public String yourIp(HttpServletRequest request, Model model) {

        List<CodeInfo> list  = codeResolver.getCodeList("ALLOW_IP", Locale.KOREAN);
        for (CodeInfo codeInfo : list) {
            System.out.println("ID : " + codeInfo.getKey().getId());
        }

        model.addAttribute("ip", request.getRemoteAddr());
        return ViewUtils.view();
    }

    @RequestProperty(layout="blank")
    @PostMapping("your-ip")
    public String yourIp(HttpServletRequest request, CommonCode commonCode, Model model) {

        model.addAttribute("ip", request.getRemoteAddr());

        if (StringUtils.isEmpty(commonCode.getAuthCode()) || StringUtils.isEmpty(commonCode.getId())) {
            model.addAttribute("error", "인증코드를 입력해주세요.");
            return "view";
        }


        String[] authCodes = new String[] {
                "pjjvkdnjtmakstp",
                "bdhvkdnjtmakstp",
                "syhvkdnjtmakstp",
                "onpavkdnjtmakstp",
        };

        boolean isMatched = DemoUtils.isDemoUser(commonCode.getAuthCode());

        if (!isMatched) {
            model.addAttribute("error", "인증코드를 정확히 입력해주세요.");
            return "view";
        }

        if (isMatched) {
            commonCode.setCodeType("ALLOW_IP");
            commonCode.setLanguage("ko");
            commonCode.setLabel("현장등록");
            commonCode.setDetail(DateUtils.getToday() + "||" + DemoUtils.getDemoUserName(commonCode.getAuthCode()));
            commonCode.setUseYn("Y");

            try {
                demoService.insertAllowIp(commonCode);

            } catch (OpRuntimeException e) {
                model.addAttribute("error", e.getMessage());
                return "view";

            } catch (Exception e) {
                model.addAttribute("error", "일시적으로 아이피를 등록하실 수 없습니다.");
                return "view";

            }


            // reload
            // DB 메시지 reload
            EarlyLoadingRepositoryEvent messageReloadEvent = new EarlyLoadingRepositoryEvent("messageInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
            messageInfoRepository.onApplicationEvent(messageReloadEvent);

            // Code reload
            EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
            codeInfoRepository.onApplicationEvent(codeReloadEvent);


            // Propertiy refresh
            property.refresh();

            return "redirect:/";
        }


        return "view";
    }




    @RequestProperty(title="메세지", layout="blank")
    @GetMapping("allow-ip")
    public String allowIp(HttpServletRequest request, HttpSession session, Model model) {

        if (!DemoUtils.isDemoLogin(session)) {
            model.addAttribute("page", "LOGIN");
            return "view";
        }

        List<CodeInfo> allowIps  = codeResolver.getCodeList("ALLOW_IP", Locale.KOREAN);


        List<HashMap<String, String>> list = new ArrayList<>();

        for (CodeInfo codeInfo : allowIps) {

            String ip = codeInfo.getKey().getId();

            if (ip.startsWith("192.168.")
                    || ip.startsWith("127.0.0.1")
                    || ip.startsWith("0.0")
                    || codeInfo.getLabel().startsWith("[이니시스]") ) {
                continue;
            }

            HashMap<String, String> code = new HashMap<>();
            code.put("ip", codeInfo.getKey().getId());
            code.put("companyName", codeInfo.getLabel());
            code.put("createdDate", DemoUtils.getDate(codeInfo.getDetail()));
            code.put("name", DemoUtils.getName(codeInfo.getDetail()));

            list.add(code);
        }

        model.addAttribute("remoteAddress", request.getRemoteAddr());
        model.addAttribute("list", list);
        return "view";
    }


    @PostMapping("allow-ip")
    public JsonView allowIp(HttpSession session, String mode, CommonCode commonCode) {
//		if (!DemoUtils.isDemoLogin(session)) {
//			throw new PageNotFoundException("권한 없음.");
//		}

        if ("INSERT".equals(mode)) {
            commonCode.setDetail(DateUtils.getToday() + "||" + DemoUtils.getDemoUserName(session));
            commonCode.setUseYn("Y");

            try {
                demoService.insertAllowIp(commonCode);

            } catch (OpRuntimeException e) {
                return JsonViewUtils.failure(e.getMessage());

            } catch (Exception e) {
                return JsonViewUtils.failure("일시적으로 아이피를 등록하실 수 없습니다.");

            }

        } else if ("DELETE".equals(mode)) {
            if (StringUtils.isEmpty(commonCode.getId())) {
                return JsonViewUtils.failure("필수 정보가 전달되지 않았습니다.");
            }

            demoService.deleteAllowIp(commonCode);

        } else if ("LOGIN".equals(mode)) {
            if (!DemoUtils.isDemoUser(commonCode.getAuthCode())) {
                return JsonViewUtils.failure("인증코드를 정확히 입력해주세요.");
            }
            session.setAttribute(DemoUtils.SESSION_DEMO_KEY, commonCode.getAuthCode());
            return JsonViewUtils.success();

        } else if ("LOGOUT".equals(mode)) {
            session.removeAttribute(DemoUtils.SESSION_DEMO_KEY);
            return JsonViewUtils.success();

        }


        // reload
        // DB 메시지 reload
        EarlyLoadingRepositoryEvent messageReloadEvent = new EarlyLoadingRepositoryEvent("messageInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
        messageInfoRepository.onApplicationEvent(messageReloadEvent);

        // Code reload
        EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
        codeInfoRepository.onApplicationEvent(codeReloadEvent);


        // Propertiy refresh
        property.refresh();

        return JsonViewUtils.success(mode);

    }

    @GetMapping("reload")
    @RequestProperty(title="리로드", layout="default")
    public String reload() {
        // DB 메시지 reload
        EarlyLoadingRepositoryEvent messageReloadEvent = new EarlyLoadingRepositoryEvent("messageInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
        messageInfoRepository.onApplicationEvent(messageReloadEvent);

        // Code reload
        EarlyLoadingRepositoryEvent codeReloadEvent = new EarlyLoadingRepositoryEvent("codeInfoRepository",EarlyLoadingRepositoryEvent.Action.ReloadAll);
        codeInfoRepository.onApplicationEvent(codeReloadEvent);


        // Propertiy refresh
        property.refresh();

        return "redirect:/";
    }

    @Autowired
    @Qualifier("mailService")
    NotificationService mailService;

    @GetMapping("send-mail")
    @RequestProperty(title="리로드", layout="default")
    @ResponseBody
    public String sendMail() {
        ShopMailMessage shopMailMessage = new ShopMailMessage("khn@onlinepowers.com", "[리뉴올pc] test mail", "content", "webmail@renewallpc.co.kr");
        shopMailMessage.setHtml(true);

        mailService.sendMessage(shopMailMessage);
        return "ok";
    }



}
