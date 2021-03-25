package saleson.shop.config.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saleson.common.enumeration.LogType;

@Getter @Setter @NoArgsConstructor
public class ConfigLogDto {
	private Long id;
	private LogType logType;
	private String key;
	private String name;
	private String description;
	private boolean used;
}
