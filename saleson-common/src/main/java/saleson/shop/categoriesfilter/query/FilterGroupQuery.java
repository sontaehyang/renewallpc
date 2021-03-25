package saleson.shop.categoriesfilter.query;

public class FilterGroupQuery {
    public static final String DELETE_BY_IDS = "DELETE FROM OP_FILTER_GROUP\n" +
            "WHERE ID IN (:ids)";
}
