package saleson.common.enumeration.mapper;

public class Code {

	private String code;
	private String title;
	private String description;
	private Boolean enabled;

	public Code(CodeMapperType codeMapperType) {
		code = codeMapperType.getCode();
		title = codeMapperType.getTitle();
		description = codeMapperType.getDescription();
		enabled = codeMapperType.isEnabled();
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "Code{" +
				"code='" + code + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", enabled=" + enabled +
				'}';
	}
}
