package saleson.shop.display.support;

import com.onlinepowers.framework.web.domain.SearchParam;

@SuppressWarnings("serial")
public class DisplaySnsParam extends SearchParam {
    private int snsId;
    private String snsType;
    private String searchStartDate;
    private String searchEndDate;

    private String[] snsIds;
    private int startOrdering;

    public int getSnsId() {
        return snsId;
    }

    public void setSnsId(int snsId) {
        this.snsId = snsId;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public String getSearchStartDate() {
        return searchStartDate;
    }

    public void setSearchStartDate(String searchStartDate) {
        this.searchStartDate = searchStartDate;
    }

    public String getSearchEndDate() {
        return searchEndDate;
    }

    public void setSearchEndDate(String searchEndDate) {
        this.searchEndDate = searchEndDate;
    }

    public String[] getSnsIds() {
        return snsIds;
    }

    public void setSnsIds(String[] snsIds) {
        this.snsIds = snsIds;
    }

    public int getStartOrdering() {
        return startOrdering;
    }

    public void setStartOrdering(int startOrdering) {
        this.startOrdering = startOrdering;
    }
}
