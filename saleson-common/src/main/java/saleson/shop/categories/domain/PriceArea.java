package saleson.shop.categories.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceArea {
    private int beginSalePrice;
    private int endSalePrice;
}
