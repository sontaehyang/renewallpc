package saleson.shop.featured.support;

import com.onlinepowers.framework.web.domain.SearchParam;

public class FeaturedReplyParam extends SearchParam {

    private long id;
    private int featuredId;
    private String userName;
    private String replyContent;
    private String dataStatus;  // (0: 정상, 1: 삭제)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFeaturedId() {
        return featuredId;
    }

    public void setFeaturedId(int featuredId) {
        this.featuredId = featuredId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
}
