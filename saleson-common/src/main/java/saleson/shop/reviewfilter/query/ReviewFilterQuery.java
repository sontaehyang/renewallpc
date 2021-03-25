package saleson.shop.reviewfilter.query;

public class ReviewFilterQuery {

    public static final String GET_FILTER_GROUP_ID_LIST_BY_CATEGORY_IDS = "SELECT DISTINCT\n" +
            "\tFILTER_GROUP_ID \n" +
            "FROM OP_REVIEW_FILTER\n" +
            "WHERE CATEGORY_ID IN (:ids)";

    public static final String DELETE_BY_CATEGORY_IDS = "DELETE FROM OP_REVIEW_FILTER\n" +
            "WHERE CATEGORY_ID IN(:ids)";

    public static final String DELETE_BY_FILTER_GROUP_IDS = "DELETE FROM OP_REVIEW_FILTER\n" +
            "WHERE FILTER_GROUP_ID IN(:filterGroupIds)";
}
