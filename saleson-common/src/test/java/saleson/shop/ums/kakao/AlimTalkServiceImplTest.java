package saleson.shop.ums.kakao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import saleson.common.SalesonTest;
import saleson.common.enumeration.kakao.alimtalk.TemplateButtonType;
import saleson.model.kakao.AlimTalkTemplate;
import saleson.model.kakao.AlimTalkTemplateButton;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AlimTalkServiceImplTest extends SalesonTest {

    @Autowired
    private AlimTalkService alimTalkService;

    @Test
    public void getTemplate() {

        try {
            alimTalkService.getTemplate("111");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getTemplateList() {

        List<AlimTalkTemplate> templates = null;

        try {
            templates = alimTalkService.getTemplateList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerTemplate() {

        AlimTalkTemplate template = new AlimTalkTemplate();

        List<AlimTalkTemplateButton> buttons = new ArrayList<>();

        template.setApplyCode("test00");
        template.setTitle("테스트");
        template.setContent("테스트 입니다.");

        AlimTalkTemplateButton button = new AlimTalkTemplateButton();

        button.setType(TemplateButtonType.AL.getCode());
        button.setName("앱링크");
        button.setSchemeAndroid("aaaaa");
        button.setSchemeIos("bbbbb");
        button.setLinkMobile("");
        button.setLinkPc("");

        buttons.add(button);

        button = new AlimTalkTemplateButton();

        button.setType(TemplateButtonType.WL.getCode());
        button.setName("웹링크링크");
        button.setSchemeAndroid("");
        button.setSchemeIos("");
        button.setLinkMobile("aaaaa");
        button.setLinkPc("bbbbb");

        buttons.add(button);

        template.setButtons(buttons);

        try {
            alimTalkService.registerTemplate(template);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void editTemplate() {
    }

    @Test
    public void deleteTemplate() {
    }

    @Test
    public void registerTemplateComment() {
    }

    @Test
    public void verifyTemplate() {
    }

    @Test
    public void changeButtonLinkByType() {
    }
}