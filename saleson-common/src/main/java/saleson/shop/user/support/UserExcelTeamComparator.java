package saleson.shop.user.support;

import java.util.Comparator;

import saleson.shop.user.domain.UserExcel;

public class UserExcelTeamComparator implements Comparator<UserExcel> {
    public int compare(UserExcel user1, UserExcel user2) {
    	int value1 = user1.getTeam().compareTo(user2.getTeam());
        if (value1 == 0) {
            return (int) (user1.getUserId() - user2.getUserId());
        }
        return value1;
    }
}
