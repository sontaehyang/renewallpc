package saleson.shop.ums.kakao.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlimTalkButton {

    // 버튼 ID
    private long id;

    // 버튼명
    private String name;

    // 버튼 타입
    private String type;

    // 버튼 타입 명
    private String typeName;

    // PC LINK
    private String linkPc;

    // Mobile Link
    private String linkMobile;

    // APP LINK (IOS )
    private String schemeIos;

    // APP LINK (ANDROID )
    private String schemeAndroid;

    // 버튼 노출 순서
    private int ordering;

    // Form 전송용 링크
    private String link1;

    // Form 전송용 링크
    private String link2;

}
