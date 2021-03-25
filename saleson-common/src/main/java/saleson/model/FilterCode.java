package saleson.model;

import com.onlinepowers.framework.util.StringUtils;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name="OP_FILTER_CODE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class FilterCode extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1000, nullable = false)
    private String label;

    @Column(length = 1000)
    private String labelCode;

    @Column(length = 1000)
    private String labelImage;

    @Column
    private Integer ordering;

    @Transient
    private MultipartFile imageFile;

    public String getImageUploadPath() {

        StringBuffer sb = new StringBuffer();

        sb.append("categories-filter");
        sb.append(File.separator);

        return sb.toString();
    }

    public String getImageSrc() {

        if (StringUtils.isEmpty(getLabelImage())) {
            return ShopUtils.getNoImagePath();
        }

        StringBuffer sb = new StringBuffer();

        sb.append(SalesonProperty.getUploadBaseFolder());
        sb.append("/");
        sb.append("categories-filter");
        sb.append("/");
        sb.append(getLabelImage());

        return sb.toString();
    }
}
