package saleson.shop.order.giftitem.support;

import com.onlinepowers.framework.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.enumeration.GiftOrderStatus;
import saleson.common.web.Param;
import saleson.model.QOrderGiftItem;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderGiftItemDto extends Param {

    private static final Logger log = LoggerFactory.getLogger(OrderGiftItemDto.class);

    private String[] orderCodes;
    private String orderCode;
    private int orderSequence = -1;
    private int itemSequence = -1;
    private String giftOrderStatus;

    public OrderGiftItemDto(String[] orderCodes) {
        this.orderCodes = orderCodes;
    }

    public OrderGiftItemDto(String orderCode, int orderSequence) {
        this.orderCode = orderCode;
        this.orderSequence = orderSequence;
    }

    public OrderGiftItemDto(String orderCode, int orderSequence, int itemSequence) {
        this.orderCode = orderCode;
        this.orderSequence = orderSequence;
        this.itemSequence = itemSequence;
    }

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QOrderGiftItem orderGiftItem = QOrderGiftItem.orderGiftItem;

        if (getOrderCodes() != null && getOrderCodes().length > 0) {
            builder.and(orderGiftItem.orderCode.in(getOrderCodes()));
        }

        if (!StringUtils.isEmpty(getOrderCode())) {

            builder.and(orderGiftItem.orderCode.eq(getOrderCode()));

            if (getOrderSequence() > -1) {
                builder.and(orderGiftItem.orderSequence.eq(getOrderSequence()));

                if (getItemSequence() > 1) {
                    builder.and(orderGiftItem.itemSequence.eq(getItemSequence()));
                }
            }
        }

        GiftOrderStatus giftOrderStatus = getOrderStatus();

        if (giftOrderStatus != null) {
            builder.and(orderGiftItem.giftOrderStatus.eq(giftOrderStatus));
        }

        return builder;
    }

    private GiftOrderStatus getOrderStatus() {

        String giftOrderStatus = getGiftOrderStatus();

        if (StringUtils.isEmpty(giftOrderStatus)) {
            return null;
        }

        return Arrays.stream(GiftOrderStatus.values())
                .filter(orderStatus -> orderStatus.getCode().equals(giftOrderStatus))
                .findAny()
                .orElse(null);

    }
}
