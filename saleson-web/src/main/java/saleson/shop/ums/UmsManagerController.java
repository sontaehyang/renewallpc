package saleson.shop.ums;

import com.fasterxml.jackson.core.type.TypeReference;
import com.onlinepowers.framework.exception.UserException;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.StringUtils;
import com.onlinepowers.framework.util.ViewUtils;
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
import org.springframework.web.bind.annotation.*;
import saleson.common.enumeration.UmsType;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.model.Ums;
import saleson.model.UmsDetail;
import saleson.common.notification.domain.UmsTemplate;
import saleson.shop.ums.dto.UmsDto;
import saleson.shop.ums.kakao.domain.AlimTalkButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/opmanager/ums")
@RequestProperty(title = "UMS설정 관리", layout = "default", template = "opmanager")
public class UmsManagerController {
    private static final Logger log = LoggerFactory.getLogger(UmsManagerController.class);

    @Autowired
    UmsRepository umsRepository;

    @Autowired
    UmsService umsService;

    @Autowired
    EnumMapper enumMapper;

    @GetMapping("/list")
    public String list(@ModelAttribute("umsDto") UmsDto umsDto, Model model,
                       @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Ums> umsConfigList = umsRepository.findAll(umsDto.getPredicate(), pageable);

        model.addAttribute("pageContent", umsConfigList);

        return ViewUtils.view();
    }

    @GetMapping("/create")
    public String getCreate(@ModelAttribute("umsConfig") Ums ums) {

        return "view:/ums/form";
    }

    @PostMapping("/create")
    public String postCreate(Ums ums) {

        umsRepository.save(ums);

        return "redirect:/opmanager/ums/list";
    }


    @GetMapping("/edit/{id}")
    public String getUmsEdit(@PathVariable("id") long id, Model model) {
        Optional<Ums> umsOptional = umsService.findById(id);
        umsOptional.orElseThrow(() -> new UserException("정보가 없습니다.", "/opmanager/ums/list"));

        Ums ums = umsOptional.get();

        // Detail 순서 변경
        List<UmsDetail> details = ums.getDetailList();
        List<AlimTalkButton> alimTalkButtons = new ArrayList<>();

        List<UmsDetail> newDetails = new ArrayList<>();

        UmsDetail message = new UmsDetail(ums.getTemplateCode(), UmsType.MESSAGE);
        UmsDetail alimTalk = new UmsDetail(ums.getTemplateCode(), UmsType.ALIM_TALK);
        UmsDetail push = new UmsDetail(ums.getTemplateCode(), UmsType.PUSH);

        if (details != null && details.size() > 0) {



            for (UmsDetail d: details) {
                if (UmsType.MESSAGE == d.getUmsType()) {
                    message = d;
                } else if (UmsType.ALIM_TALK == d.getUmsType()) {
                    alimTalk = d;

                    String buttonString =  d.getAlimTalkButtons();

                    if (!StringUtils.isEmpty(buttonString)) {

                        try {
                            alimTalkButtons
                                    = (List<AlimTalkButton>) JsonViewUtils.jsonToObject(buttonString, new TypeReference<List<Map<String, String>>>(){});


                        } catch (Exception ignore) {

                        }
                    }
                } else if (UmsType.PUSH == d.getUmsType()) {
                    push = d;
                }
            }

        }

        newDetails.add(message);
        newDetails.add(alimTalk);
        newDetails.add(push);

        ums.setDetailList(newDetails);

        model.addAttribute("templateInspStatusList", enumMapper.get("TemplateInspStatus"));
        model.addAttribute("templateButtonTypeList", enumMapper.get("TemplateButtonType"));
        model.addAttribute("templateStatusList", enumMapper.get("TemplateStatus"));

        model.addAttribute("alimTalkButtons",alimTalkButtons);
        model.addAttribute("ums", ums);

        return "view:/ums/form";
    }

    @PostMapping("/edit/{id}")
    public String postUmsEdit(Ums ums, @PathVariable("id") long id) {

        try {
            umsService.updateUms(ums);
        } catch (Exception e) {

            e.printStackTrace();

            String message = e.getMessage();

            if (StringUtils.isEmpty(message)) {
                message = "UMS 처리중 문제가 발생했습니다.";
            }

            return ViewUtils.redirect("/opmanager/ums/edit/" + id, message);
        }


        return "redirect:/opmanager/ums/edit/" + id;
    }

    @RequestProperty(layout="base")
    @RequestMapping("/change-code/{templateCode}")
    public String changeCode(Model model, @PathVariable("templateCode") String templateCode) {

        UmsTemplate umsTemplate = new UmsTemplate();

        model.addAttribute("title", umsTemplate.getTemplateCodeTitle(templateCode));
        model.addAttribute("codeList", umsService.getUmsChangeCodes(templateCode));
        return ViewUtils.getView("/ums/change-code");
    }
}
