package saleson.shop.config.support;

import java.beans.PropertyEditorSupport;

import com.onlinepowers.framework.util.ValidationUtils;

public class PointUsePropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if (ValidationUtils.isEmpty(text)) {
            setValue(-1);
		} else {
			setValue(Integer.parseInt(text));
		}
	}
}
