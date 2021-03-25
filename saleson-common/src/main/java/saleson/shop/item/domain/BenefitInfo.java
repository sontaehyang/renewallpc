package saleson.shop.item.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.shop.cardbenefits.domain.CardBenefits;
import saleson.shop.point.domain.PointPolicy;

@Getter
@Setter
@NoArgsConstructor
public class BenefitInfo {
    private PointPolicy pointPolicy;
    private CardBenefits cardBenefits;
    private ItemEarnPoint itemEarnPoint;
}
