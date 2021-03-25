package saleson.shop.giftitem.domain;

import com.onlinepowers.framework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.config.SalesonProperty;
import saleson.common.utils.CommonUtils;
import saleson.common.utils.LocalDateUtils;
import saleson.common.utils.ShopUtils;
import saleson.model.GiftItem;
import saleson.model.OrderGiftItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftItemInfo {

    private String code;
    private String name;
    private String image;
    private String validStartDate;
    private String validEndDate;

    public GiftItemInfo(OrderGiftItem giftItem) {
        this.code = CommonUtils.dataNvl(giftItem.getGiftItemCode());
        this.name = CommonUtils.dataNvl(giftItem.getGiftItemName());
        this.image = CommonUtils.dataNvl(giftItem.getImage());
        this.validStartDate = LocalDateUtils.getDateTime(giftItem.getValidStartDate());
        this.validEndDate = LocalDateUtils.getDateTime(giftItem.getValidEndDate());
    }

    public GiftItemInfo(GiftItem giftItem) {
        this.code = CommonUtils.dataNvl(giftItem.getCode());
        this.name = CommonUtils.dataNvl(giftItem.getName());
        this.image = CommonUtils.dataNvl(giftItem.getImage());
        this.validStartDate = giftItem.getValidEndDateText();
        this.validEndDate = giftItem.getValidEndDateText();
    }



    public String getImageSrc() {

        if (StringUtils.isEmpty(getImage())) {
            return ShopUtils.getNoImagePath();
        }

        StringBuffer sb = new StringBuffer();

        sb.append(SalesonProperty.getUploadBaseFolder());
        sb.append("/");
        sb.append("gift-item");
        sb.append("/");
        sb.append(getCode());
        sb.append("/");
        sb.append(getImage());

        return sb.toString();
    }

}