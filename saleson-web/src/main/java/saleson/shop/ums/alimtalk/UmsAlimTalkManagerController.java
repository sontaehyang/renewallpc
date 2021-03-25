package saleson.shop.ums.alimtalk;

import com.onlinepowers.framework.enumeration.JavaScript;
import com.onlinepowers.framework.util.JsonViewUtils;
import com.onlinepowers.framework.util.ViewUtils;
import com.onlinepowers.framework.web.bind.annotation.RequestProperty;
import com.onlinepowers.framework.web.servlet.view.JsonView;
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
import saleson.common.enumeration.kakao.alimtalk.TemplateInspStatus;
import saleson.common.enumeration.kakao.alimtalk.TemplateStatus;
import saleson.common.enumeration.mapper.EnumMapper;
import saleson.common.exception.KakaoAlimTalkException;
import saleson.model.Ums;
import saleson.model.UmsDetail;
import saleson.model.kakao.AlimTalkTemplate;
import saleson.model.kakao.AlimTalkTemplateComment;
import saleson.shop.ums.UmsRepository;
import saleson.shop.ums.UmsService;
import saleson.shop.ums.kakao.AlimTalkService;
import saleson.shop.ums.kakao.AlimTalkTemplateRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/opmanager/ums/alimtalk")
@RequestProperty(title = "알림톡 설정 관리", layout = "base", template = "opmanager")
public class UmsAlimTalkManagerController {
    private static final Logger log = LoggerFactory.getLogger(UmsAlimTalkManagerController.class);

    @Autowired
    private AlimTalkService alimtalkService;

    @Autowired
    private AlimTalkTemplateRepository alimTalkTemplateRepository;

    @Autowired
    private UmsService umsService;

    @Autowired
    private UmsRepository umsRepository;

    @Autowired
    EnumMapper enumMapper;

    /**
     * 알림톡 조회
     * @param dto
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/{templateCode}/list")
    public String getAlimtalk(AlimTalkTemplate dto, @PageableDefault(sort="id", direction= Sort.Direction.DESC) Pageable pageable, Model model) {

        Page<AlimTalkTemplate> pageContent = null;
        try {
            pageContent = alimTalkTemplateRepository.findAll(dto.getPredicate(), pageable);
        } catch (Exception e) {
            log.error("리뉴올PC 템플릿 목록 조회 에러 : {}", e.getMessage(), e);
            throw new KakaoAlimTalkException(e.getMessage());
        }

        model.addAttribute("templateCode", dto.getTemplateCode());
        model.addAttribute("pageContent", pageContent);
        return "view:/ums/alimtalk/list";
    }

    /**
     * 알림톡 등록
     * @param dto
     * @param model
     * @return
     */
    @GetMapping("/{templateCode}/create")
    public String getCreate(AlimTalkTemplate dto, Model model) {

        model.addAttribute("vendor", new AlimTalkTemplate());
        model.addAttribute("saleson", new AlimTalkTemplate());
        model.addAttribute("templateCode", dto.getTemplateCode());
        model.addAttribute("templateButtonTypeList", enumMapper.get("TemplateButtonType"));
        return "view:/ums/alimtalk/form";
    }

    /**
     * 알림톡 등록 처리
     * @param vendor
     * @return
     */
    @PostMapping("/{templateCode}/create")
    public String postCreate(AlimTalkTemplate vendor) {

        try {
            // 1. Vendor 등록
            alimtalkService.registerTemplate(vendor);

            // 2. Vendor -> SalesOn 동기화 처리
            vendor.setStatus(TemplateStatus.R.getCode());
            vendor.setInspStatus(TemplateInspStatus.REG.getCode());

            alimtalkService.setAlimTalkTemplate(new AlimTalkTemplate(), vendor);
            alimTalkTemplateRepository.save(vendor);
        } catch (Exception e) {
            log.error("Vendor&리뉴올PC 템플릿 등록 에러 : {}", e.getMessage(), e);
            return ViewUtils.redirect("/opmanager/ums/alimtalk/" + vendor.getTemplateCode() + "/create", e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/ums/alimtalk/" + vendor.getTemplateCode() + "/list", "처리되었습니다.");
    }

    /**
     * 알림톡 수정
     * @param dto
     * @param model
     * @return
     */
    @GetMapping("/{templateCode}/edit/{applyCode}")
    public String getEdit(AlimTalkTemplate dto, Model model) {

        AlimTalkTemplate vendor = null;
        Optional<AlimTalkTemplate> optional = null;
        try {
            // Vendor 템플릿 조회
            vendor = alimtalkService.getTemplate(dto.getApplyCode());

            // SalesOn 템플릿 조회
            optional = alimTalkTemplateRepository.findOne(vendor.getPredicate());
        } catch (Exception e) {
            log.error("Vendor&리뉴올PC 템플릿 조회 에러 : {}", e.getMessage(), e);
            throw new KakaoAlimTalkException(e.getMessage());
        }

        model.addAttribute("vendor", vendor);
        model.addAttribute("saleson", (optional.isPresent() ? optional.get() : null));
        model.addAttribute("templateCode", dto.getTemplateCode());
        model.addAttribute("templateButtonTypeList", enumMapper.get("TemplateButtonType"));
        return "view:/ums/alimtalk/form";
    }

    /**
     * 알림톡 수정 처리
     * @param vendor
     * @return
     */
    @PostMapping("/{templateCode}/edit/{applyCode}")
    public String postEdit(AlimTalkTemplate vendor) {

        try {
            // Vendor 수정
            alimtalkService.editTemplate(vendor);

            // 요청후 정보 SalesOn에 동기화 처리
            Optional<AlimTalkTemplate> optional = alimTalkTemplateRepository.findOne(vendor.getPredicate());

            alimtalkService.setAlimTalkTemplate((optional.isPresent() ? optional.get() : new AlimTalkTemplate()), vendor);
            alimTalkTemplateRepository.save(vendor);
        } catch (Exception e) {
            log.error("Vendor&리뉴올PC 템플릿 수정 에러 : {}", e.getMessage(), e);
            return ViewUtils.redirect("/opmanager/ums/alimtalk/" + vendor.getTemplateCode() + "/edit/" + vendor.getApplyCode(), e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/ums/alimtalk/" + vendor.getTemplateCode() + "/edit/" + vendor.getApplyCode(), "처리되었습니다.");
    }

    /**
     * 알림톡 검수요청
     * @param dto
     * @return
     */
    @PostMapping("verify")
    public JsonView verify(AlimTalkTemplate dto) {

        try {
            // Vendor 검수요청
            alimtalkService.verifyTemplate(dto.getApplyCode());

            // 요청후 정보 SalesOn 검수상태 수정
            Optional<AlimTalkTemplate> optional = alimTalkTemplateRepository.findOne(dto.getPredicate());

            if (optional.isPresent()) {
                AlimTalkTemplate saleson = optional.get();
                saleson.setInspStatus(TemplateInspStatus.REQ.getCode());
                alimTalkTemplateRepository.save(saleson);
            }
        } catch (Exception e) {
            log.error("Vendor 템플릿 검수 요청 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    /**
     * 알림톡 문의 등록
     * @param applyCode
     * @param alimTalkTemplateComment
     * @param model
     * @return
     */
    @GetMapping("/popup/{applyCode}/comment/create")
    public String getComment(@PathVariable("applyCode") String applyCode, AlimTalkTemplateComment alimTalkTemplateComment, Model model) {

        model.addAttribute("alimTalkTemplateComment", alimTalkTemplateComment);
        return "view:/ums/alimtalk/popup/comment/form";
    }

    /**
     * 알림톡 문의 등록 처리
     * @param applyCode
     * @param alimTalkTemplateComment
     * @return
     */
    @PostMapping("/popup/{applyCode}/comment/create")
    public String postComment(@PathVariable("applyCode") String applyCode, AlimTalkTemplateComment alimTalkTemplateComment) {

        try {
            alimtalkService.registerTemplateComment(applyCode, alimTalkTemplateComment.getContent());
        } catch (Exception e) {
            log.error("Vendor 템플릿 문의 등록 에러 : {}", e.getMessage(), e);
            return ViewUtils.redirect("/opmanager/ums/alimtalk/popup/" + applyCode + "/comment/create", e.getMessage(), JavaScript.CLOSE);
        }

        return ViewUtils.redirect("/opmanager/ums/alimtalk/popup/" + applyCode + "/comment/create", "문의 등록되었습니다.", JavaScript.CLOSE_AND_OPENER_RELOAD);
    }

    /**
     * 알림톡 동기화
     * @param dto
     * @return
     */
    @PostMapping("/synchronization")
    public JsonView synchronization(AlimTalkTemplate dto) {

        AlimTalkTemplate vendor = null;
        try {
            // Vendor 템플릿 조회
            vendor = alimtalkService.getTemplate(dto.getApplyCode());

            // SalesOn에 동기화
            vendor.setTemplateCode(dto.getTemplateCode());
            Optional<AlimTalkTemplate> optional = alimTalkTemplateRepository.findOne(vendor.getPredicate());

            alimtalkService.setAlimTalkTemplate((optional.isPresent() ? optional.get() : new AlimTalkTemplate()), vendor);
            alimTalkTemplateRepository.save(vendor);
        } catch (Exception e) {
            log.error("Vendor 템플릿 검수 요청 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    /**
     * 알림톡 선택
     * @param dto
     * @return
     */
    @GetMapping("/{templateCode}/select/{applyCode}")
    public String select(AlimTalkTemplate dto) {

        try {
            // SalesOn 템플릿 조회
            Optional<AlimTalkTemplate> optional = alimTalkTemplateRepository.findOne(dto.getPredicate());

            if (!optional.isPresent()) {
                throw new KakaoAlimTalkException("선택 대상 템플릿 조회 불가");
            }

            AlimTalkTemplate alimTalkTemplate = optional.get();

            if (!TemplateInspStatus.APR.getCode().equals(alimTalkTemplate.getInspStatus())) {
                throw new KakaoAlimTalkException(TemplateInspStatus.APR.getTitle() + "(" + TemplateInspStatus.APR.getCode() + ") 상태만 선택 가능합니다.");
            }

            // 알림톡 -> UMS 설정
            Ums ums = umsService.getUms(dto.getTemplateCode());

            List<UmsDetail> umsDetails = ums.getDetailList().stream()
                    .filter(umsDetail -> umsDetail.getUmsType() == UmsType.ALIM_TALK).collect(Collectors.toList());

            if (umsDetails.isEmpty()) {
                UmsDetail umsDetail = new UmsDetail();

                umsDetail.setUmsType(UmsType.ALIM_TALK);
                umsDetail.setTemplateCode(ums.getTemplateCode());
                umsDetail.setApplyCode(alimTalkTemplate.getApplyCode());
                umsDetail.setTitle(alimTalkTemplate.getTitle());
                umsDetail.setMessage(alimTalkTemplate.getContent());
                umsDetail.setAlimTalkButtons(JsonViewUtils.objectToJson(alimtalkService.getAlimTalkButtonsMaps(alimTalkTemplate.getButtons())));

                ums.getDetailList().add(umsDetail);
            } else {
                umsDetails.forEach(
                    umsDetail -> {
                        umsDetail.setApplyCode(alimTalkTemplate.getApplyCode());
                        umsDetail.setTitle(alimTalkTemplate.getTitle());
                        umsDetail.setMessage(alimTalkTemplate.getContent());
                        umsDetail.setAlimTalkButtons(JsonViewUtils.objectToJson(alimtalkService.getAlimTalkButtonsMaps(alimTalkTemplate.getButtons())));
                    }
                );
            }

            umsRepository.save(ums);
        } catch (Exception e) {
            log.error("알림톡 -> UMS 템플릿 적용 에러 : {}", e.getMessage(), e);
            return ViewUtils.redirect("/opmanager/ums/alimtalk/" + dto.getTemplateCode() + "/list", e.getMessage());
        }

        return ViewUtils.redirect("/opmanager/ums/alimtalk/" + dto.getTemplateCode() + "/list", "처리되었습니다.", JavaScript.CLOSE_AND_OPENER_RELOAD);
    }

    /**
     * 알림톡 동기화
     * @param applyCode
     * @return
     */
    @PostMapping("/delete/{applyCode}")
    public JsonView delete(@PathVariable("applyCode") String applyCode) {

        AlimTalkTemplate alimTalkTemplate = new AlimTalkTemplate();
        try {
            // Vendor 템플릿 삭제
            alimtalkService.deleteTemplate(applyCode);

            // SalesOn 템플릿 삭제
            alimTalkTemplate.setApplyCode(applyCode);
            Optional<AlimTalkTemplate> optional = alimTalkTemplateRepository.findOne(alimTalkTemplate.getPredicate());

            if (optional.isPresent()) {
                alimTalkTemplateRepository.delete(optional.get());
            }
        } catch (Exception e) {
            log.error("Vendor 템플릿 검수 요청 에러 : {}", e.getMessage(), e);
            return JsonViewUtils.failure(e.getMessage());
        }

        return JsonViewUtils.success();
    }

    @GetMapping("/batch-test")
    public String batchTest() {
        log.debug("[updateKakaoAlimTalkBatch] START ------------------------------------------");

        try {
            List<AlimTalkTemplate> vendorAlimTalks = alimtalkService.getTemplateList();
            List<AlimTalkTemplate> salesonAlimTalks = alimTalkTemplateRepository.findAll();

            HashSet<String> updateCodeSet = new HashSet<>();
            HashSet<String> removeCodeSet = new HashSet<>();

            if (vendorAlimTalks != null && vendorAlimTalks.size() > 0) {
                // Vendor 알림톡 승인코드 세팅
                vendorAlimTalks.forEach(vendor -> {
                    // 검수상태가 승인(APR)일 경우 삭제 대상에서 배제
                    if (!TemplateInspStatus.APR.getCode().equals(vendor.getInspStatus())) {
                        removeCodeSet.add(vendor.getApplyCode());
                    }
                });

                // Vendor <-> SalesOn 일치하는 데이터 승인코드 세팅 및 SalesOn에 동기화
                vendorAlimTalks.forEach(
                        vendor -> salesonAlimTalks.forEach(
                                saleson -> {
                                    if (vendor.getApplyCode().equals(saleson.getApplyCode())) {
                                        vendor.setTemplateCode(saleson.getTemplateCode());
                                        alimtalkService.setAlimTalkTemplate(saleson, vendor);
                                        alimTalkTemplateRepository.save(vendor);

                                        updateCodeSet.add(vendor.getApplyCode());
                                    }
                                }
                        )
                );
            }

            // Vendor <-> SalesOn 불일치 데이터 삭제 (Vendor에만 남아있는 데이터)
            removeCodeSet.removeAll(updateCodeSet);

            if (removeCodeSet.size() > 0) {
                removeCodeSet.forEach(code -> {
                    try {
                        alimtalkService.deleteTemplate(code);
                    } catch (Exception e) {
                        log.error("Vendor 데이터 삭제 실패 : {}", e.getMessage(), e);
                        throw new KakaoAlimTalkException(e.getMessage(), e);
                    }
                });
            }

        } catch (Exception e) {
            log.error("[updateKakaoAlimTalkBatch] 카카오 알림톡 업데이트 실패 : {}", e.getMessage(), e);
        }

        log.debug("[updateKakaoAlimTalkBatch] END --------------------------------------------");

        return "OK";
    }
}