package saleson.shop.ums.kakao;

import saleson.model.kakao.AlimTalkTemplate;
import saleson.model.kakao.AlimTalkTemplateButton;

import java.util.List;
import java.util.Map;

public interface AlimTalkService {

    /**
     * 템플릿 코드별 알림톡 템플릿 조회
     * @param applyCode
     * @return
     */
    AlimTalkTemplate getTemplate(String applyCode) throws Exception;

    /**
     * 알림톡 템플릿 목록 조회
     * @return
     */
    List<AlimTalkTemplate> getTemplateList() throws Exception;

    /**
     * 알림톡 템플릿 등록
     * @param alimTalkTemplate
     */
    void registerTemplate(AlimTalkTemplate alimTalkTemplate) throws Exception;

    /**
     * 알림톡 템플릿 수정
     * @param alimTalkTemplate
     */
    void editTemplate(AlimTalkTemplate alimTalkTemplate) throws Exception;

    /**
     * 알림톡 템플릿 삭제
     * @param applyCode
     */
    void deleteTemplate(String applyCode) throws Exception;

    /**
     * 알림톡 템플릿 문의 등록
     * @param applyCode
     * @param comment
     */
    void registerTemplateComment(String applyCode, String comment) throws Exception;

    /**
     * 알림톡 템플릿 검수 확인
     * @param applyCode
     */
    void verifyTemplate(String applyCode) throws Exception;

    /**
     * 알림톡 템플릿 버튼 타입별로 링크 정보 셋팅
     * @param button
     */
    void changeButtonLinkByType(AlimTalkTemplateButton button);

    /**
     * 알림톡 셋팅
     * @param orgTemplate
     * @param template
     */
    void setAlimTalkTemplate(AlimTalkTemplate orgTemplate, AlimTalkTemplate template);

    /**
     * 알림톡 버튼 Map 세팅
     * @param buttons
     */
    List<Map<String, String>> getAlimTalkButtonsMaps(List<AlimTalkTemplateButton> buttons);
}
