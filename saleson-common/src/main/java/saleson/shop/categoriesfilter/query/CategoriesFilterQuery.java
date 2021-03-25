package saleson.shop.categoriesfilter.query;

public class CategoriesFilterQuery {

    public static final String GET_FILTER_GROUP_ID_LIST_BY_CATEGORY_IDS = "SELECT DISTINCT\n" +
            "\tFILTER_GROUP_ID \n" +
            "FROM OP_CATEGORY_FILTER\n" +
            "WHERE CATEGORY_ID IN (:ids)";

    public static final String DELETE_BY_CATEGORY_IDS = "DELETE FROM OP_CATEGORY_FILTER\n" +
            "WHERE CATEGORY_ID IN(:ids)";

    public static final String DELETE_BY_FILTER_GROUP_IDS = "DELETE FROM OP_CATEGORY_FILTER\n" +
            "WHERE FILTER_GROUP_ID IN(:filterGroupIds)";
}
