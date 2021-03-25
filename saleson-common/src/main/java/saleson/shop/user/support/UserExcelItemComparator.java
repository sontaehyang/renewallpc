package saleson.shop.user.support;

import java.util.Comparator;

import saleson.shop.user.domain.UserExcel;

public class UserExcelItemComparator implements Comparator<UserExcel> {
    public int compare(UserExcel user1, UserExcel user2) {
        int value1 = user1.getItemUserCode().compareTo(user2.getItemUserCode());
        if (value1 == 0) {
            return (int)(user1.getUserId() - user2.getUserId());
        }
        return value1;
    }
}
