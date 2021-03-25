package saleson.shop.stats.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Visit {
	private int visitId;
	private String remoteAddr;
	private String visitDate;
	private String visitTime;
	private String language;
	private String referer;
	private String agent;
	private String domain;
	private String domainName;
	private String browser;
	private String os;
	private String weekday;

	private int visitCount;
}
