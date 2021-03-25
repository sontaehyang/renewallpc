package saleson.shop.user.support;

import com.onlinepowers.framework.enumeration.PersistentEnum;

/**
 * 직원 목록의 검색 조건.
 * @author dbclose
 * @since
 */
public enum NgoWhereEnum implements PersistentEnum {
	ID("LOGIN_ID", "ID", 1), 
	NAME("USERNAME", "기관명", 2);
	
	
	private final String value;
	private final String lable;
	private final int order;
	
	private NgoWhereEnum(String value, String lable, int order) {
		this.value = value;
		this.lable = lable;
		this.order = order;
	}

	@Override
	public String getLabel() {
		return lable;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public int getOrder() {
		return order;
	}
}
