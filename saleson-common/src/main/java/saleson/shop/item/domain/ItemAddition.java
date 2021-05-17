package saleson.shop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemAddition {
	private int itemAdditionId;
	private int itemId;
	private int additionItemId;
	private int ordering;
	private String createdDate;

	// 연관 상품정보
	private ItemBase item;

	private int categoryId;
	private String categoryName;
}
