package saleson.common.hibenate.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanYnConverter implements AttributeConverter<Boolean, String> {

	public String convertToDatabaseColumn(Boolean attribute) {
		return (attribute != null && attribute) ? "Y" : "N";
	}

	public Boolean convertToEntityAttribute(String s) {
		return "Y".equals(s);
	}
}
