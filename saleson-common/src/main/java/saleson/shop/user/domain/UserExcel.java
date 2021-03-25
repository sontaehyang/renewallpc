package saleson.shop.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.onlinepowers.framework.repository.Code;
import com.onlinepowers.framework.security.userdetails.User;
import com.onlinepowers.framework.util.CodeUtils;

@SuppressWarnings("serial")
public class UserExcel extends User {
	
	
	private String team;
	
	
	private String itemUserCode;
	private String itemName;
	
	
	
	
	private List<UserExcelOrder> userExcelOrders = new ArrayList<>();
	
	
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getItemUserCode() {
		return itemUserCode;
	}
	public void setItemUserCode(String itemUserCode) {
		this.itemUserCode = itemUserCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public List<UserExcelOrder> getUserExcelOrders() {
		return userExcelOrders;
	}
	public void setUserExcelOrders(List<UserExcelOrder> userExcelOrders) {
		this.userExcelOrders = userExcelOrders;
	}
	/*public String getPositionTypeText() {
		UserDetail userDetail = (UserDetail) getUserDetail();
		String[] positionTypes = StringUtils.tokenizeToStringArray(userDetail.getPositionType(), ",");
		
		List<Code> positionTypeCode = CodeUtils.getCodeList("POSITION_TYPE");
		
		String positionTypeTexts = "";
		
		int i = 0;
		if (positionTypes != null) {
			for (String positionType : positionTypes) {
				for (Code code : positionTypeCode) {
					if (positionType.equals(code.getValue())) {
						positionTypeTexts += i == 0 ? "" : "|";
						positionTypeTexts += code.getLabel() + " ";
						i++;
					}
				}
			}
		}
		
		return positionTypeTexts;
	}
	public String getMergedTeam() {
		UserDetail userDetail = (UserDetail) getUserDetail();
		
		if (userDetail == null || userDetail.getBusinessType() == null) {
			return "";
		}
		
		String[] estheticTeams = new String[] {
				"1", "2", "4", "9", "12", "13", "8"
		};
		String[] hairTeams = new String[] {
				"6", "7", "8"
		};
		String[] nailTeams = new String[] {
				"5", "8"
		};
		String[] matsugeExtensionTeams = new String[] {
				"11", "8"
		};

		
		String[] teamNumbers = StringUtils.tokenizeToStringArray(userDetail.getBusinessType(), ",");
		boolean isEsthetic = false;
		boolean isHair = false;
		boolean isNail = false;
		boolean isMatsugeExtension = false;
		
		if (teamNumbers != null ) {
			for (String string : teamNumbers) {
				String teamNumber = string.trim();
				
				for (String teamNum : estheticTeams) {
					if (teamNum.equals(teamNumber) && !isEsthetic) {
						isEsthetic = true;
					}
				}
				
				for (String teamNum : hairTeams) {
					if (teamNum.equals(teamNumber) && !isHair) {
						isHair = true;
					}
				}
				
				for (String teamNum : nailTeams) {
					if (teamNum.equals(teamNumber) && !isNail) {
						isNail = true;
					}
				}
				
				for (String teamNum : matsugeExtensionTeams) {
					if (teamNum.equals(teamNumber) && !isMatsugeExtension) {
						isMatsugeExtension = true;
					}
				}
			}
		}
		
		String mergedTeams = "";
		if (isEsthetic) mergedTeams += "esthetic ";
		if (isHair) mergedTeams += "hair ";
		if (isNail) mergedTeams += "nail ";
		if (isMatsugeExtension) mergedTeams += "matsuge_extension";

		return mergedTeams.trim().replace(" ", "|");
	}*/
}
