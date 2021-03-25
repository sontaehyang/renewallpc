package saleson.shop.item.support;

import java.beans.PropertyEditorSupport;

import com.onlinepowers.framework.util.ValidationUtils;

public class ItemPricePropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if (ValidationUtils.isEmpty(text)) {
            setValue(0);
		} else {
			setValue(Integer.parseInt(text));
		}
	}
}
