package saleson.model.stylebook;

import com.onlinepowers.framework.util.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.base.BaseEntity;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Table(name="OP_STYLE_BOOK")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class StyleBook extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1000)
    private String image;

    @Column(length = 200)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false)
    private Integer ordering;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="styleBookId")
    private List<StyleBookItem> items;

    @Transient
    private List<Integer> displayItemIds;

    @Transient
    private MultipartFile imageFile;

    @PrePersist
    public void prePersist() {
        setDefaultValue();
    }

    @PreUpdate
    public void preUpdate() {
        setDefaultValue();
    }

    public void setDefaultValue() {
        this.ordering = CommonUtils.intNvl(this.ordering);
    }

    public String getImageUploadPath() {

        StringBuffer sb = new StringBuffer();

        sb.append("style-book");
        sb.append(File.separator);

        return sb.toString();
    }

    public String getImageSrc() {

        if (ValidationUtils.isEmpty(getImage())) {
            return ShopUtils.getNoImagePath();
        }

        StringBuffer sb = new StringBuffer();

        sb.append(SalesonProperty.getUploadBaseFolder());
        sb.append("/");
        sb.append("style-book");
        sb.append("/");
        sb.append(getImage());

        return sb.toString();
    }
}
