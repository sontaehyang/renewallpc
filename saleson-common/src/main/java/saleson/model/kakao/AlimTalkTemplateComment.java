package saleson.model.kakao;

import com.onlinepowers.framework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.enumeration.kakao.alimtalk.TemplateCommentStatus;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlimTalkTemplateComment {

    private long id;
    private String content;
    private String userName;
    private String createdAt;
    private String status;

    public String getStatusTitle() {

        AtomicReference<String> title = new AtomicReference<>();

        Arrays.stream(TemplateCommentStatus.values()).forEach(
                t -> {
                    if (t.getCode().equals(getStatus())) {
                        title.set(t.getTitle());
                    }
                }
        );

        return StringUtils.null2void(title.get());

    }
}
