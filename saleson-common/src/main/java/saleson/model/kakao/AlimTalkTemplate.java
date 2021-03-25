package saleson.model.kakao;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.*;
import saleson.common.enumeration.kakao.alimtalk.TemplateInspStatus;
import saleson.common.enumeration.kakao.alimtalk.TemplateStatus;
import saleson.common.hibenate.converter.BooleanYnConverter;
import saleson.common.utils.ShopUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
@Table(
        name="OP_ALIM_TALK_TEMPLATE"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class AlimTalkTemplate extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 검수 상태
    @Column(length = 3, nullable = false)
    private String inspStatus;

    @Column(length = 20, nullable = false)
    private String templateCode;

    @Column(length = 7, nullable = false)
    private String applyCode;

    @Column(length = 20, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    // 탬플릿 상태
    @Column(length = 1, nullable = false)
    private String status;

    // 탬플릿 버튼
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="alimTalkTemplateButtonId")
    private List<AlimTalkTemplateButton> buttons;

    // 탬플릿 문의사항 (API 통신을 통해 가져옴)
    @Transient
    private List<AlimTalkTemplateComment> comments;

    // 템플릿 API 요청 여부
    @Transient
    @Convert(converter = BooleanYnConverter.class)
    private boolean requestFlag;

    public String getInspStatusTitle() {

        AtomicReference<String> title = new AtomicReference<>();

        Arrays.stream(TemplateInspStatus.values()).forEach(
                t -> {
                    if (t.getCode().equals(getInspStatus())) {
                        title.set(t.getTitle());
                    }
                }
        );

        return StringUtils.null2void(title.get());
    }

    public String getStatusTitle() {

        AtomicReference<String> title = new AtomicReference<>();

        Arrays.stream(TemplateStatus.values()).forEach(
                t -> {
                    if (t.getCode().equals(getStatus())) {
                        title.set(t.getTitle());
                    }
                }
        );

        return StringUtils.null2void(title.get());

    }

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QAlimTalkTemplate alimTalkTemplate = QAlimTalkTemplate.alimTalkTemplate;

        if (!StringUtils.isEmpty(this.applyCode)) {
            builder.and(alimTalkTemplate.applyCode.eq(getApplyCode()));
        }

        if (!StringUtils.isEmpty(this.templateCode)) {
            builder.and(alimTalkTemplate.templateCode.eq(getTemplateCode()));
        }

        return builder;
    }

    public String getTitle() {
        return ShopUtils.unescapeHtml(title);
    }

    public String getContent() {
        return ShopUtils.unescapeHtml(content);
    }

}
