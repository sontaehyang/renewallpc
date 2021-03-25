package saleson.shop.giftitem.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.enumeration.DataStatus;
import saleson.common.web.Param;
import saleson.model.QGiftItem;
import saleson.model.QGiftItemRelation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftItemRelationDto extends Param {

    private static final Logger log = LoggerFactory.getLogger(GiftItemRelationDto.class);

    private int itemId;
    private boolean isDelete = false;

    public Predicate getPredicate() {
        BooleanBuilder builder = new BooleanBuilder();
        QGiftItemRelation giftItemRelation = QGiftItemRelation.giftItemRelation;

        if (getItemId() > 0) {
            builder.and(giftItemRelation.itemId.eq(getItemId()));
        }

        return builder;
    }

}
