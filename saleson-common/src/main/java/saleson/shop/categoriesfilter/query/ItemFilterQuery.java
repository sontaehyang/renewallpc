package saleson.shop.categoriesfilter.query;

public class ItemFilterQuery {

    public static final String DELETE_BY_CATEGORY_ID = "DELETE FROM OP_ITEM_FILTER\n" +
            "WHERE ITEM_ID IN (\n" +
            "\tSELECT ITEM_ID FROM OP_ITEM_CATEGORY IC\n" +
            "\tWHERE IC.CATEGORY_ID IN (:categoryIds)\n" +
            ")\n";

    public static final String WHERE_ADD_IN_FILTER_GROUP_ID ="AND FILTER_GROUP_ID IN (:filterGroupIds)";

    public static final String WHERE_ADD_NOT_IN_FILTER_GROUP_ID = "AND FILTER_GROUP_ID NOT IN (:filterGroupIds)";

    public static final String DELETE_BY_ITEM_ID = "DELETE FROM OP_ITEM_FILTER\n" +
            "WHERE ITEM_ID IN (:itemId)";

    public static final String DELETE_BY_FILTER_GROUP_IDS = "DELETE FROM OP_ITEM_FILTER\n" +
            "WHERE FILTER_GROUP_ID IN (:filterGroupIds)";

    public static final String DELETE_BY_FILTER_CODE_IDS = "DELETE FROM OP_ITEM_FILTER\n" +
            "WHERE FILTER_CODE_ID IN (:filterCodeIds)";
}
