package saleson.shop.display.domain;

public class DisplaySns {
    private int snsId;
    private String snsToken;
    private String snsType;
    private int ordering;
    private String updatedDate;
    private String createdDate;

    public DisplaySns() {}

    public DisplaySns(int snsId, int ordering) {
        this.snsId = snsId;
        this.ordering = ordering;
    }

    public int getSnsId() {
        return snsId;
    }

    public void setSnsId(int snsId) {
        this.snsId = snsId;
    }

    public String getSnsToken() {
        return snsToken;
    }

    public void setSnsToken(String snsToken) {
        this.snsToken = snsToken;
    }

    public String getSnsType() {
        return snsType;
    }

    public void setSnsType(String snsType) {
        this.snsType = snsType;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
