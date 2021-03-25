package saleson.api.search.domain;

public class BestKeywordInfo {

    private String keyword; // 키워드
    private String keywordType; // 검색어구분 (1:자동생성, 2:고객검색)
    private String createdDate; // 등록일/검색일
    private String keywordSeperation;  // 검색어 자소분리
    private int weight; // 가중치

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(String keywordType) {
        this.keywordType = keywordType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getKeywordSeperation() {
        return keywordSeperation;
    }

    public void setKeywordSeperation(String keywordSeperation) {
        this.keywordSeperation = keywordSeperation;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
