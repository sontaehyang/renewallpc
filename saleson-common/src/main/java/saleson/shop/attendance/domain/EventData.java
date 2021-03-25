package saleson.shop.attendance.domain;

import java.util.List;

public class EventData {
	private String type; 			// update, success, reset (변경 데이터를 그룹핑 하기 위한 변수)
	private List<Long> ids;
	private String updatedDate;

	public EventData(String type, List<Long> ids, String updatedDate) {
		this.type = type;
		this.ids = ids;
		this.updatedDate = updatedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
