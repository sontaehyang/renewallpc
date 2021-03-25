package saleson.shop.statistics.domain;

import com.onlinepowers.framework.util.StringUtils;

import java.util.List;

public class CategoryStatsSummary {

    private String teamId;
    private String teamCode;
    private String teamName;

    private String groupId;
    private String groupCode;
    private String groupName;

    private String categoryId;
    private String categoryName;
    private String categoryCode;
    private String categoryClass1;
    private String categoryClass2;
    private String categoryClass3;
    private String categoryClass4;

    private List<BaseStats> groupStats;

    public String getTitle() {

        String title = "";

        if (StringUtils.isNotEmpty(this.teamName)) {
            title += this.teamName;
        }

        if (StringUtils.isNotEmpty(this.groupName)) {
            title += " > " + this.groupName;
        }

        if (StringUtils.isNotEmpty(this.categoryName)) {
            title += " > " + this.categoryName;
        }

        if (StringUtils.isEmpty(title)) {
            title = "정보없음";
        }

        return title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTeamId() {
        return teamId;
    }
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
    public String getTeamCode() {
        return teamCode;
    }
    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getGroupId() {
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupCode() {
        return groupCode;
    }
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryCode() {
        return categoryCode;
    }
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    public String getCategoryClass1() {
        return categoryClass1;
    }
    public void setCategoryClass1(String categoryClass1) {
        this.categoryClass1 = categoryClass1;
    }
    public String getCategoryClass2() {
        return categoryClass2;
    }
    public void setCategoryClass2(String categoryClass2) {
        this.categoryClass2 = categoryClass2;
    }
    public String getCategoryClass3() {
        return categoryClass3;
    }
    public void setCategoryClass3(String categoryClass3) {
        this.categoryClass3 = categoryClass3;
    }
    public String getCategoryClass4() {
        return categoryClass4;
    }
    public void setCategoryClass4(String categoryClass4) {
        this.categoryClass4 = categoryClass4;
    }

    public List<BaseStats> getGroupStats() {
        return groupStats;
    }

    public void setGroupStats(List<BaseStats> groupStats) {
        this.groupStats = groupStats;
    }

    public long getSubSaleCount() {
        return groupStats.stream().mapToLong(s -> s.getSaleCount()).sum();
    }

    public long getSubItemPrice() {
        return groupStats.stream().mapToLong(s -> s.getItemPrice()).sum();
    }

    public long getSubDiscountAmount() {
        return groupStats.stream().mapToLong(s -> s.getDiscountAmount()).sum();
    }

    public long getSubSaleAmount() {
        return groupStats.stream().mapToLong(s -> s.getSaleAmount()).sum();
    }

    public long getSubCancelCount() {
        return groupStats.stream().mapToLong(s -> s.getCancelCount()).sum();
    }

    public long getSubCancelItemPrice() {
        return groupStats.stream().mapToLong(s -> s.getCancelItemPrice()).sum();
    }

    public long getSubCancelDiscountAmount() {
        return groupStats.stream().mapToLong(s -> s.getCancelDiscountAmount()).sum();
    }

    public long getSubCancelAmount() {
        return groupStats.stream().mapToLong(s -> s.getCancelAmount()).sum();
    }

    public long getSubTotalCount() {
        return this.getSubSaleCount() + this.getSubCancelCount();
    }

    public long getSubTotalItemPrice() {
        return this.getSubItemPrice() + this.getSubCancelItemPrice();
    }

    public long getSubTotalDiscountAmount() {
        return this.getSubDiscountAmount() + this.getSubCancelDiscountAmount();
    }

    public long getSubTotalAmount() {
        return this.getSubSaleAmount() + this.getSubCancelAmount();
    }

}
