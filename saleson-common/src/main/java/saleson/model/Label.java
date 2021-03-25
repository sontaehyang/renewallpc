package saleson.model;

import com.onlinepowers.framework.util.StringUtils;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.enumeration.LabelType;
import saleson.common.utils.ShopUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name="OP_LABEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Label extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 라벨 이미지
    @Column(length = 255, nullable = false)
    private String image;

    // 라벨 설명
    @Column(length = 100, nullable = false)
    private String description;

    // 라벨 타입
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabelType labelType;

    // form 데이터 영역

    // 이미지 파일
    @Transient
    private MultipartFile imageFile;

    public String getImageUploadPath() {

        StringBuffer sb = new StringBuffer();

        sb.append("label");
        sb.append(File.separator);
        sb.append(getLabelType().getCode().toLowerCase());
        sb.append(File.separator);

        return sb.toString();
    }

    public String getImageSrc() {

        if (StringUtils.isEmpty(getImage())) {
            return ShopUtils.getNoImagePath();
        }

        StringBuffer sb = new StringBuffer();

        sb.append(SalesonProperty.getUploadBaseFolder());
        sb.append("/");
        sb.append("label");
        sb.append("/");
        sb.append(getLabelType().getCode().toLowerCase());
        sb.append("/");
        sb.append(getImage());

        return sb.toString();
    }
}
