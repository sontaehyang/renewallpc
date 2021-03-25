package saleson.model.kakao;


import lombok.*;
import saleson.common.enumeration.kakao.alimtalk.TemplateButtonType;
import saleson.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(
        name="OP_ALIM_TALK_TEMPLATE_BUTTION"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class AlimTalkTemplateButton extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 버튼명
    @Column(length = 28)
    private String name;

    // 버튼 타입
    @Column(length = 3)
    private String type;

    // 버튼 타입 명
    @Column(length = 10)
    private String typeName;

    // PC LINK
    @Column(length = 200)
    private String linkPc;

    // Mobile Link
    @Column(length = 200)
    private String linkMobile;

    // APP LINK (IOS)
    @Column(length = 200)
    private String schemeIos;

    // APP LINK (ANDROID)
    @Column(length = 200)
    private String schemeAndroid;

    // 버튼 노출 순서
    @Column
    private int ordering;

    // Form 전송용 링크
    @Transient
    private String link1;

    // Form 전송용 링크
    @Transient
    private String link2;

}
