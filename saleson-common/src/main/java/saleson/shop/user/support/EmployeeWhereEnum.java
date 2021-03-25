package saleson.shop.user.support;

import com.onlinepowers.framework.enumeration.PersistentEnum;

/**
 * 직원 목록의 검색 조건.
 * @author dbclose
 * @since
 */
public enum EmployeeWhereEnum implements PersistentEnum {
	NAME("USERNAME", "이름", 1), 
	LOGIN_ID("LOGIN_ID", "직원번호", 2), 
	LOCAL_HQ_NAME("LOCAL_HQ_NAME", "본부명", 3), 
	DEPT_NAME("DEPT_NAME", "부점명", 4);
	
	
	private final String value;
	private final String lable;
	private final int order;
	
	private EmployeeWhereEnum(String value, String lable, int order) {
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
