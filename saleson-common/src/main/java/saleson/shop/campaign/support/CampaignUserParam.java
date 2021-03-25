package saleson.shop.campaign.support;

import com.onlinepowers.framework.util.DateUtils;
import com.onlinepowers.framework.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import saleson.common.Const;
import saleson.shop.group.domain.Group;
import saleson.shop.userlevel.domain.UserLevel;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignUserParam {

    private String loginId;
    private String userName;
    private List<String> groupCodes;
    private List<Integer> levelIds;
    // SMS 수신여부
    private List<String> receiveSmss;

    // 푸시 수신여부
    private List<String> receivePushs;

    // 가입이
    // 후 총 구매액
    private int startOrderAmount1;
    private int endOrderAmount1;

    // 최근 3개월간 총 구매액
    private int startOrderAmount2;
    private int endOrderAmount2;

    // 최종 로그인 일자
    private String lastLoginDate;

    // 장바구니 개수
    private int cartCount;

    // 장바구니 총액
    private int startCartAmount;

    // 장바구니 총액
    private int endCartAmount;

    public CampaignUserParam(List<Group> groups, CampaignBatchDto dto) {

        if (dto != null) {
            setLoginId(getQueryByWhere("loginId", dto));
            setUserName(getQueryByWhere("userName", dto));

            // groupCodes start
            List<String> groupCodes = new ArrayList<>();
            if (!StringUtils.isEmpty(dto.getGroupCode())) {
                groupCodes.add(dto.getGroupCode());
            } else {
                groupCodes.add("0");

                if (groups != null) {
                    for (Group group : groups) {
                        groupCodes.add(group.getGroupCode());
                    }
                }
            }

            setGroupCodes(groupCodes);
            // groupCodes end

            List<Integer> levelIds = new ArrayList<>();
            if (dto.getLevelId() >= 0) {
                levelIds.add(dto.getLevelId());
            } else {
                levelIds.add(0);

                if (groups != null && !groups.isEmpty()) {
                    for (Group group : groups) {

                        List<UserLevel> userLevels = group.getUserLevels();

                        if (userLevels != null && !userLevels.isEmpty()) {
                            for (UserLevel userLevel : group.getUserLevels()) {
                                levelIds.add(userLevel.getLevelId());
                            }
                        }
                    }
                }
            }

            setLevelIds(levelIds);

            setReceiveSmss(getReceiveFlags(dto.getReceiveSms()));
            setReceivePushs(getReceiveFlags(dto.getReceivePush()));

            String today = DateUtils.getToday(Const.DATE_FORMAT);
            String lastLoginDate = dto.getLastLoginDate();
            setLastLoginDate(!StringUtils.isEmpty(lastLoginDate) ? lastLoginDate : today + "235959");

            int maxValue = 999999999;
            int minValue = 0;

            setStartOrderAmount1(getInitValue(dto.getStartOrderAmount1(), minValue));
            setEndOrderAmount1(getInitValue(dto.getEndOrderAmount1(), maxValue));

            // 최근 3개월간 총 구매액
            setStartOrderAmount2(getInitValue(dto.getStartOrderAmount2(), minValue));
            setEndOrderAmount2(getInitValue(dto.getEndOrderAmount2(), maxValue));

            // 장바구니 개수
            setCartCount(getInitValue(dto.getCartCount(), minValue));

            // 장바구니 총액
            setStartCartAmount(getInitValue(dto.getStartCartAmount(), minValue));

            // 장바구니 총액
            setEndCartAmount(getInitValue(dto.getEndCartAmount(), maxValue));
        }
    }

    private int getInitValue(int value, int initValue) {
        return value > 0 ? value : initValue;
    }

    private List<String> getReceiveFlags(String value) {

        List<String> flags = new ArrayList<>();

        if (!StringUtils.isEmpty(value)) {
            flags.add(value);
        } else {
            flags.add("0");
            flags.add("1");
        }

        return flags;
    }

    private String getQueryByWhere(String where, CampaignBatchDto dto) {
        return where.equals(dto.getWhere()) ? "%" + dto.getQuery() + "%" : "";
    }

}
