package saleson.shop.label.query;

public class LabelQuery {
    public static final String DELETE_BY_IDS = "DELETE FROM OP_LABEL\n" +
            "WHERE ID IN (:ids)";
}
