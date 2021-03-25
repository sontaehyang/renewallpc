package saleson.shop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalePriceInfo {

    private int minPrice;
    private int maxPrice;
    private int averagePrice;

}
