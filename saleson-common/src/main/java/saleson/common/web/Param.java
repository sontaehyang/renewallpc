package saleson.common.web;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Param {
	private int size = 10;
	private int page = 1;
	private String where;
	private String query;
	private String sort;
}
