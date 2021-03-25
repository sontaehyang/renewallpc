package saleson.shop.categoriesfilter.query;

public class FilterCodeQuery {
    public static final String DELETE_BY_FILTER_GROUP_ID = "DELETE FROM OP_FILTER_CODE\n" +
            "WHERE FILTER_GROUP_ID = :filterGroupId";

    public static final String DELETE_BY_FILTER_GROUP_IDS = "DELETE FROM OP_FILTER_CODE\n" +
            "WHERE FILTER_GROUP_ID IN (:filterGroupIds)";

    public static final String DELETE_BY_FILTER_CODE_IDS = "DELETE FROM OP_FILTER_CODE\n" +
            "WHERE ID IN (:filterCodeIds)";
}
