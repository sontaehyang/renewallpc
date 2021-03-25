package saleson.common.notification.micesoft.domain.kakao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ButtonJson {

    private String name;
    private String type;
    private String url_pc;
    private String url_mobile;
    private String scheme_ios;
    private String scheme_android;

}