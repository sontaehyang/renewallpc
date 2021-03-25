package saleson.shop.coupon.support;

import java.beans.PropertyEditorSupport;

import com.onlinepowers.framework.util.ValidationUtils;

public class CouponPayRestrictionPropertyEditor extends PropertyEditorSupport {
	
	@Override
	public void setAsText(String text) {
		if (ValidationUtils.isEmpty(text)) {
            setValue(-1);
		} else {
			setValue(Integer.parseInt(text));
		}
	}
}
