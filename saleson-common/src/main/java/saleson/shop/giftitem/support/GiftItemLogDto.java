package saleson.shop.giftitem.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.web.Param;
import saleson.model.QGiftItemLog;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftItemLogDto extends Param {

    private static final Logger log = LoggerFactory.getLogger(GiftItemDto.class);

    private Long giftItemId;

    public Predicate getPredicate() {

        BooleanBuilder builder = new BooleanBuilder();
        QGiftItemLog giftItemLog = QGiftItemLog.giftItemLog;

        builder.and(giftItemLog.giftItemId.eq(getGiftItemId()));

        return builder;
    }

}
