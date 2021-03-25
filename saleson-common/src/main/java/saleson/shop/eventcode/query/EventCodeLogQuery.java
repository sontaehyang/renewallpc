package saleson.shop.eventcode.query;

public class EventCodeLogQuery {

    public static final String GET_BASE_EVENT_STATISTICS = "SELECT\n" +
            "    BASE.EVENT_CODE AS EVENT_CODE,\n" +
            "    SUM(BASE.VISIT_COUNT) AS VISIT_COUNT,\n" +
            "    SUM(BASE.ORDER_COUNT) AS ORDER_COUNT,\n" +
            "    ROUND(COALESCE((SUM(BASE.ORDER_COUNT) / SUM(BASE.VISIT_COUNT)) * 100, 0), 2) AS ORDER_RATE,\n" +
            "    SUM(BASE.JOIN_COUNT) AS JOIN_COUNT,\n" +
            "ROUND(COALESCE((SUM(BASE.JOIN_COUNT) / SUM(BASE.VISIT_COUNT)) * 100, 0), 2) AS JOIN_RATE\n" +
            "FROM (\n" +
            "    SELECT\n" +
            "        L.EVENT_CODE,\n" +
            "        COUNT(L.UID) AS VISIT_COUNT,\n" +
            "        0 AS JOIN_COUNT,\n" +
            "        0 AS ORDER_COUNT\n" +
            "    FROM (\n" +
            "        SELECT\n" +
            "            DISTINCT UID, EVENT_CODE\n" +
            "        FROM OP_EVENT_CODE_LOG\n" +
            "        WHERE CREATED BETWEEN :beginDate AND :endDate\n" +
            "    ) L\n" +
            "    GROUP BY L.EVENT_CODE\n" +
            "\n" +
            "    UNION ALL\n" +
            "\n" +
            "    SELECT\n" +
            "        L.EVENT_CODE,\n" +
            "        0 AS VISIT_COUNT,\n" +
            "        0 AS JOIN_COUNT,\n" +
            "        COUNT(L.ORDER_CODE) AS ORDER_COUNT\n" +
            "    FROM (\n" +
            "        SELECT\n" +
            "            DISTINCT ORDER_CODE, EVENT_CODE\n" +
            "        FROM OP_EVENT_CODE_LOG\n" +
            "        WHERE CODE_TYPE = 'NONE'\n" +
            "        AND LOG_TYPE = 'ORDER'\n" +
            "        AND CREATED BETWEEN :beginDate AND :endDate\n" +
            "    ) L\n" +
            "    GROUP BY L.EVENT_CODE\n" +
            "\n" +
            "    UNION ALL\n" +
            "\n" +
            "    SELECT\n" +
            "        EVENT_CODE,\n" +
            "        0 AS VISIT_COUNT,\n" +
            "        COUNT(USER_ID) AS JOIN_COUNT,\n" +
            "        0 AS ORDER_COUNT\n" +
            "    FROM OP_EVENT_CODE_LOG\n" +
            "        WHERE CODE_TYPE = 'NONE'\n" +
            "        AND LOG_TYPE = 'USER'\n" +
            "        AND CREATED BETWEEN :beginDate AND :endDate\n" +
            "    GROUP BY EVENT_CODE\n" +
            ") BASE\n" +
            "GROUP BY BASE.EVENT_CODE";

    public static final String GET_BASE_EVENT_STATISTICS_COUNT = "SELECT\n" +
            "    COUNT(T.EVENT_CODE) AS CNT\n" +
            "FROM (\n"+
            EventCodeLogQuery.GET_BASE_EVENT_STATISTICS
            +") T";

    public static final String GET_TOTAL_EVENT_STATISTICS = "SELECT\n" +
            "    SUM(T.VISIT_COUNT) AS VISIT_COUNT,\n" +
            "    SUM(T.ORDER_COUNT) AS ORDER_COUNT,\n" +
            "    ROUND(COALESCE((SUM(T.ORDER_COUNT) / SUM(T.VISIT_COUNT)) * 100, 0), 2) AS ORDER_RATE,\n" +
            "    SUM(T.JOIN_COUNT) AS JOIN_COUNT,\n" +
            "    ROUND(COALESCE((SUM(T.JOIN_COUNT) / SUM(T.VISIT_COUNT)) * 100, 0), 2) AS JOIN_RATE\n" +
            "FROM (\n"+
            EventCodeLogQuery.GET_BASE_EVENT_STATISTICS
            +") T";

    public static final String GET_EVENT_CODE_LOG_CONTENTS = "SELECT EVENT_CODE, CONTENTS\n" +
            "FROM OP_EVENT_CODE\n" +
            "WHERE EVENT_CODE IN (:eventCodes)\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT ITEM_USER_CODE AS EVENT_CODE, ITEM_NAME AS CONTENTS\n" +
            "FROM OP_ITEM\n" +
            "WHERE ITEM_USER_CODE IN (:eventCodes)";

    public static final String GET_BASE_EVENT_ITEM_STATISTICS = "SELECT\n" +
            "    I.ITEM_NAME,\n" +
            "    T.*\n" +
            "FROM OP_ITEM I\n" +
            "INNER JOIN (\n" +
            "    SELECT BASE.ITEM_USER_CODE,\n" +
            "           SUM(BASE.LIST_VIEW_COUNT)                                                            AS LIST_VIEW_COUNT,\n" +
            "           SUM(BASE.ITEM_VIEW_COUNT)                                                            AS ITEM_VIEW_COUNT,\n" +
            "           ROUND(COALESCE((SUM(BASE.ITEM_VIEW_COUNT) / SUM(BASE.LIST_VIEW_COUNT)) * 100, 0), 2) AS CLICKED_RATE,\n" +
            "           SUM(BASE.ORDER_COUNT)                                                                AS ORDER_COUNT,\n" +
            "           ROUND(COALESCE((SUM(BASE.ORDER_COUNT) / SUM(BASE.ITEM_VIEW_COUNT)) * 100, 0), 2)     AS ORDER_RATE\n" +
            "    FROM (\n" +
            "             SELECT ITEM_USER_CODE,\n" +
            "                    COUNT(ITEM_USER_CODE) AS LIST_VIEW_COUNT,\n" +
            "                    0                     AS ITEM_VIEW_COUNT,\n" +
            "                    0                     AS ORDER_COUNT\n" +
            "             FROM OP_EVENT_CODE_LOG\n" +
            "             WHERE CODE_TYPE = 'NONE'\n" +
            "               AND LOG_TYPE = 'FEATURED'\n" +
            "               AND CREATED BETWEEN :beginDate AND :endDate\n" +
            "             GROUP BY ITEM_USER_CODE\n" +
            "\n" +
            "             UNION ALL\n" +
            "\n" +
            "             SELECT EVENT_CODE,\n" +
            "                    COUNT(EVENT_CODE) AS LIST_VIEW_COUNT,\n" +
            "                    0                 AS ITEM_VIEW_COUNT,\n" +
            "                    0                 AS ORDER_COUNT\n" +
            "             FROM OP_EVENT_CODE_LOG\n" +
            "             WHERE CODE_TYPE IN ('SHARE', 'EP_ITEM')\n" +
            "               AND LOG_TYPE = 'VIEW'\n" +
            "               AND CREATED BETWEEN :beginDate AND :endDate\n" +
            "             GROUP BY EVENT_CODE\n" +
            "\n" +
            "             UNION ALL\n" +
            "\n" +
            "             SELECT ITEM_USER_CODE,\n" +
            "                    0                     AS LIST_VIEW_COUNT,\n" +
            "                    COUNT(ITEM_USER_CODE) AS ITEM_VIEW_COUNT,\n" +
            "                    0                     AS ORDER_COUNT\n" +
            "             FROM OP_EVENT_CODE_LOG\n" +
            "             WHERE CODE_TYPE = 'NONE'\n" +
            "               AND LOG_TYPE = 'ITEM'\n" +
            "               AND CREATED BETWEEN :beginDate AND :endDate\n" +
            "             GROUP BY ITEM_USER_CODE\n" +
            "\n" +
            "             UNION ALL\n" +
            "\n" +
            "             SELECT L.ITEM_USER_CODE,\n" +
            "                    0                   AS LIST_VIEW_COUNT,\n" +
            "                    0                   AS ITEM_VIEW_COUNT,\n" +
            "                    COUNT(L.ORDER_CODE) AS ORDER_COUNT\n" +
            "             FROM (\n" +
            "                      SELECT DISTINCT ORDER_CODE, ITEM_USER_CODE\n" +
            "                      FROM OP_EVENT_CODE_LOG\n" +
            "                      WHERE CODE_TYPE = 'NONE'\n" +
            "                        AND LOG_TYPE = 'ORDER'\n" +
            "                        AND CREATED BETWEEN :beginDate AND :endDate\n" +
            "                  ) L\n" +
            "             GROUP BY L.ITEM_USER_CODE\n" +
            "         ) BASE\n" +
            "    GROUP BY BASE.ITEM_USER_CODE\n" +
            ") T ON T.ITEM_USER_CODE = I.ITEM_USER_CODE";

    public static final String GET_BASE_EVENT_ITEM_STATISTICS_COUNT = "SELECT\n" +
            "    COUNT(T.ITEM_USER_CODE) AS CNT\n" +
            "FROM (\n"+
            EventCodeLogQuery.GET_BASE_EVENT_ITEM_STATISTICS
            +") T";

    public static final String GET_TOTAL_EVENT_ITEM_STATISTICS = "SELECT\n" +
            "       SUM(T.LIST_VIEW_COUNT)                                                            AS LIST_VIEW_COUNT,\n" +
            "       SUM(T.ITEM_VIEW_COUNT)                                                            AS ITEM_VIEW_COUNT,\n" +
            "       ROUND(COALESCE((SUM(T.ITEM_VIEW_COUNT) / SUM(T.LIST_VIEW_COUNT)) * 100, 0), 2) AS CLICKED_RATE,\n" +
            "       SUM(T.ORDER_COUNT)                                                                AS ORDER_COUNT,\n" +
            "       ROUND(COALESCE((SUM(T.ORDER_COUNT) / SUM(T.ITEM_VIEW_COUNT)) * 100, 0), 2)     AS ORDER_RATE\n" +
            "FROM (\n"+
            EventCodeLogQuery.GET_BASE_EVENT_ITEM_STATISTICS
            +") T";
}
