package saleson.shop.item.domain;

import com.onlinepowers.framework.util.ValidationUtils;
import lombok.Getter;
import lombok.Setter;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.ShopUtils;

import java.io.File;

@Getter
@Setter
public class ItemReviewImage {
    private long itemReviewImageId;
    private int itemReviewId;
    private String reviewImage;
    private int ordering;
    private String createdDate;

    public StringBuffer getDefaultSrc() {
        StringBuffer sb = new StringBuffer();

        sb.append(SalesonProperty.getUploadBaseFolder());
        sb.append("/item-review/");
        sb.append(this.itemReviewId);

        return sb;
    }

    public String getImageSrc() {
        if (ValidationUtils.isEmpty(this.reviewImage)) {
            return ShopUtils.getNoImagePath();
        }

        StringBuffer sb = getDefaultSrc();
        sb.append("/");
        sb.append(this.reviewImage);

        return sb.toString();
    }
}
