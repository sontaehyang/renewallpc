package saleson.shop.item.domain;

import com.onlinepowers.framework.util.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saleson.common.utils.ShopUtils;
import saleson.common.utils.UserUtils;
import saleson.shop.point.domain.PointPolicy;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEarnPoint {

    private static final Logger log = LoggerFactory.getLogger(ItemEarnPoint.class);

    private int point;
    private float pointRate;
    private int levelPoint;
    private float levelPointRate;
    private String levelName;

    public ItemEarnPoint(PointPolicy pointPolicy, Item item) {

        int point = 0;
        float pointRate = 0;
        int levelPoint = 0;
        float levelPointRate = 0;

        if (pointPolicy != null && item != null) {

            try {

                // 포인트 기준금액 - 쿠폰 할인가를 적용?
                int basePointPrice = item.getPresentPrice();

                if (ValidationUtils.isNotNull(pointPolicy)) {
                    point = ShopUtils.getEarnPoint(basePointPrice, pointPolicy);

                    if ("1".equals(pointPolicy.getPointType())) { // 비율
                        pointRate = pointPolicy.getPoint();
                    }
                }

                levelPointRate = UserUtils.getUserDetail().getUserLevelPointRate();

                if (levelPointRate != 0) {
                    levelPoint = new BigDecimal(basePointPrice)
                            .multiply(BigDecimal.valueOf(levelPointRate).divide(new BigDecimal("100")))
                            .setScale(0, BigDecimal.ROUND_FLOOR).intValue();
                }

            } catch (Exception ignore) {
                log.error("ItemEarnPoint error {}", ignore.getMessage() ,ignore);
            }
        }

        setPoint(point);
        setPointRate(pointRate);
        setLevelPoint(levelPoint);
        setLevelPointRate(levelPointRate);
        setLevelName(UserUtils.getUserDetail().getLevelName());
    }

    public int getTotalPoint() {
        return getPoint() + getLevelPoint();
    }
}
