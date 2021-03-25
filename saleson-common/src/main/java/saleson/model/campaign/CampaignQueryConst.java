package saleson.model.campaign;

public class CampaignQueryConst {

    public static final String UPDATE_CAMPAIGN_SENT = "UPDATE OP_CAMPAIGN \n" +
            "SET \n" +
            "\tSMS_SENT = :smsSent,\n" +
            "\tSMS_SUCCESS = :smsSuccess,\n" +
            "\tMMS_SENT = :mmsSent,\n" +
            "\tMMS_SUCCESS = :mmsSuccess,\n" +
            "\tKAKAO_SENT = :kakaoSent,\n" +
            "\tKAKAO_SUCCESS = :kakaoSuccess,\n" +
            "\tPUSH_SENT = :pushSent,\n" +
            "\tPUSH_SUCCESS = :pushSuccess,\n" +
            "\tPUSH_RECEIVE = :pushReceive,\n" +
            "\tSTATISTICS_DATE = :statisticsDate\n" +
            "WHERE ID = :id";

    public static final String UPDATE_CAMPAIGN_REDIRECTION = "UPDATE OP_CAMPAIGN \n" +
            "SET \n" +
            "\tREDIRECTION = :redirection,\n" +
            "\tSTATISTICS_DATE = :statisticsDate\n" +
            "WHERE ID = :id";

    public static final String UPDATE_CAMPAIGN_ORDER_INFO = "UPDATE OP_CAMPAIGN \n" +
            "SET \n" +
            "\tORDER_AMOUNT = :orderAmount,\n" +
            "\tORDER_COUNT = :orderCount,\n" +
            "\tSTATISTICS_DATE = :statisticsDate\n" +
            "WHERE ID = :id";

     // 1
    public static final String INSERT_CAMPAIGN_BATCH = "INSERT INTO OP_CAMPAIGN_BATCH \n" +
         "\t(VERSION,CREATED,\n" +
         "\tUSER_ID,\n" +
         "\tLOGIN_ID,USER_NAME,\n" +
         "\tLAST_LOGIN_DATE,\n" +
         "\tGROUP_CODE,\n" +
         "\tLEVEL_ID,\n" +
         "\tRECEIVE_SMS,\n" +
         "\tRECEIVE_PUSH,\n" +
         "\tORDER_AMOUNT1,ORDER_AMOUNT2,\n" +
         "\tCART_COUNT,CART_AMOUNT,\n" +
         "\tPOINT)\n" +
         "\tSELECT\n" +
         "\t0 AS VERSION,\n" +
         "\t:created AS CREATED,\n" +
         "\tU.USER_ID,\n" +
         "\tCOALESCE(U.LOGIN_ID, '-') AS LOGIN_ID, COALESCE(U.USER_NAME, '-'),\n" +
         "\tCOALESCE(U.LOGIN_DATE, '19900101000000') AS LAST_LOGIN_DATE,\n" +
         "\tCASE UD.GROUP_CODE WHEN '' THEN '0' ELSE COALESCE(UD.GROUP_CODE, '0') END AS GROUP_CODE,\n" +
         "\tCOALESCE(UD.LEVEL_ID, 0) AS LEVEL_ID,\n" +
         "\tCOALESCE(UD.RECEIVE_SMS, '1') AS RECEIVE_SMS,\n" +
         "\tCOALESCE(UD.RECEIVE_PUSH, '1') AS RECEIVE_PUSH,\n" +
         "\tCOALESCE(OT.AMOUNT, 0) AS ORDER_AMOUNT1,\n" +
         "\tCOALESCE(OP.AMOUNT, 0) AS ORDER_AMOUNT2,\n" +
         "\tCOALESCE(C.CART_COUNT, 0) AS CART_COUNT,\n" +
         "\tCOALESCE(C.CART_AMOUNT, 0) AS CART_AMOUNT,\n" +
         "\t0 AS POINT\n" +
         "FROM\n" +
         "\tOP_USER U\n" +
         "INNER JOIN OP_USER_DETAIL UD ON UD.USER_ID = U.USER_ID\n" +
         "\tAND U.STATUS_CODE = '9'\n" +
         "LEFT JOIN (\n" +
         "\tSELECT T.USER_ID, SUM(T.AMOUNT) AS AMOUNT\n" +
         "\tFROM (\n" +
         "\t\tSELECT\n" +
         "\t\t\tO.USER_ID,\n" +
         "\t\t\tSUM(O.ORDER_TOTAL_AMOUNT) AS AMOUNT\n" +
         "\t\tFROM OP_ORDER O\n" +
         "\t\tGROUP BY O.USER_ID\n" +
         "\t\tUNION ALL\n" +
         "\t\tSELECT\n" +
         "\t\t\tUSER_ID,\n" +
         "\t\t\tAMOUNT\n" +
         "\t\tFROM OP_ORDER_SALES\n" +
         "\t) T\n" +
         "\tGROUP BY T.USER_ID\n"+
         ") OT ON OT.USER_ID = U.USER_ID\n" +
         "LEFT JOIN (\n" +
         "\tSELECT T.USER_ID, SUM(T.AMOUNT) AS AMOUNT\n" +
         "\tFROM (\n" +
         "\t\tSELECT\n" +
         "\t\t\tO.USER_ID,\n" +
         "\t\t\tSUM(O.ORDER_TOTAL_AMOUNT) AS AMOUNT\n" +
         "\t\tFROM OP_ORDER O\n" +
         "\tWHERE O.CREATED_DATE >= :intervalDate\n" +
         "\t\tGROUP BY O.USER_ID\n" +
         "\t\tUNION ALL\n" +
         "\t\tSELECT\n" +
         "\t\t\tUSER_ID,\n" +
         "\t\t\tAMOUNT\n" +
         "\t\tFROM OP_ORDER_SALES\n" +
         "\t\tWHERE PAY_DATE >= :intervalPayDate\n" +
         "\t) T\n" +
         "\tGROUP BY T.USER_ID\n"+
         ") OP ON OP.USER_ID = U.USER_ID\n" +
         "LEFT JOIN (\n" +
         "\tSELECT\n" +
         "\tC.USER_ID,\n" +
         "\tCOUNT(C.CART_ID) AS CART_COUNT,\n" +
         "\tSUM(I.SALE_PRICE * C.QUANTITY) AS CART_AMOUNT\n" +
         "\tFROM\n" +
         "\tOP_CART C\n" +
         "\tINNER JOIN OP_ITEM I\n" +
         "\tON I.ITEM_ID = C.ITEM_ID\n" +
         "\tGROUP BY C.USER_ID\n" +
         ") C ON C.USER_ID = U.USER_ID";

    // 2
    public static final String INSERT_CAMPAIGN_BATCH_POINT = "INSERT INTO OP_CAMPAIGN_BATCH_POINT (USER_ID, POINT)\n" +
            "SELECT U.USER_ID, SUM(P.POINT) AS POINT\n" +
            "FROM OP_USER U\n" +
            "INNER JOIN OP_POINT P\n" +
            "    ON U.USER_ID = P.USER_ID\n" +
            "    AND P.POINT_TYPE = 'point'\n" +
            "    AND P.POINT != 0\n" +
            "    AND P.EXPIRATION_DATE >= :today\n" +
            "GROUP BY U.USER_ID\n";


    // 3
    public static final String UPDATE_CAMPAIGN_BATCH_FOR_POINT_MYSQL = "UPDATE OP_CAMPAIGN_BATCH B\n" +
            "\tINNER JOIN OP_CAMPAIGN_BATCH_POINT T ON B.USER_ID = T.USER_ID\n" +
            "\tSET B.POINT = T.POINT";

    public static final String UPDATE_CAMPAIGN_BATCH_FOR_POINT_ORACLE = "UPDATE OP_CAMPAIGN_BATCH CB\n" +
            "SET CB.POINT = (SELECT POINT FROM OP_CAMPAIGN_BATCH_POINT CBP WHERE CBP.USER_ID = CB.USER_ID)\n" +
            "WHERE EXISTS (SELECT 0 FROM OP_CAMPAIGN_BATCH_POINT CBP WHERE CBP.USER_ID = CB.USER_ID)";

    public static final String UPDATE_CAMPAIGN_BATCH_FOR_POINT_POSTGRES = "UPDATE OP_CAMPAIGN_BATCH CB\n" +
            "SET POINT = CBP.POINT\n" +
            "FROM OP_CAMPAIGN_BATCH_POINT CBP\n" +
            "WHERE CB.USER_ID = CBP.USER_ID";

    // 4
    public static final String DELETE_CAMPAIGN_BATCH_POINT = "DELETE FROM OP_CAMPAIGN_BATCH_POINT";

    public static final String DELETE_CAMPAIGN_BATCH = "DELETE FROM OP_CAMPAIGN_BATCH";

    public static final String INSERT_CAMPAIGN_USER = "INSERT INTO OP_CAMPAIGN_USER \n" +
            "\t(CREATED,\n" +
            "\tCAMPAIGN_ID,\n" +
            "\tUSER_ID,\n" +
            "\tUSER_NAME,\n" +
            "\tPHONE_NUMBER,\n" +
            "\tPUSH_TOKEN,\n" +
            "\tAPPLICATION_NO,\n" +
            "\tAPPLICATION_VERSION,\n" +
            "\tUUID,\n" +
            "\tDEVICE_TYPE,\n" +
            "\tRECEIVE_PUSH,\n" +
            "\tRECEIVE_SMS,\n" +
            "\tPOINT,\n" +
            "\tBATCH_DATE)\n" +
            "\tSELECT\n" +
            "\t:created AS CREATED,\n" +
            "\t:campaignId AS CAMPAIGN_ID,\n" +
            "\tU.USER_ID,\n" +
            "\tU.USER_NAME,\n" +
            "\tUD.PHONE_NUMBER,\n" +
            "\tAI.PUSH_TOKEN,\n" +
            "\tAI.APPLICATION_NO,\n" +
            "\tAI.APPLICATION_VERSION,\n" +
            "\tAI.UUID,\n" +
            "\tAI.DEVICE_TYPE,\n" +
            "\tCB.RECEIVE_PUSH,\n" +
            "\tCB.RECEIVE_SMS,\n" +
            "\tCB.POINT,\n" +
            "\tCB.CREATED\n" +
            "FROM \n" +
            "\tOP_USER U\n" +
            "\tINNER JOIN OP_USER_DETAIL UD\n" +
            "\tON U.USER_ID = UD.USER_ID\n" +
            "\tINNER JOIN OP_CAMPAIGN_BATCH CB\n" +
            "\tON U.USER_ID = CB.USER_ID\n"+
            "\tLEFT JOIN OP_APPLICATION_INFO AI\n" +
            "\tON U.USER_ID = AI.USER_ID\n"+
            "\tWHERE U.STATUS_CODE = '9'\n" +
            "\tAND CB.GROUP_CODE IN (:groupCodes)\n" +
            "\tAND CB.LEVEL_ID IN (:levelIds)\n" +
            "\tAND CB.ORDER_AMOUNT1 >= :startOrderAmount1\n" +
            "\tAND CB.ORDER_AMOUNT1 <= :endOrderAmount1\n" +
            "\tAND CB.ORDER_AMOUNT2 >= :startOrderAmount2\n" +
            "\tAND CB.ORDER_AMOUNT2 <= :endOrderAmount2\n" +
            "\tAND CB.LAST_LOGIN_DATE <= :lastLoginDate\n" +
            "\tAND CB.CART_COUNT >= :cartCount\n" +
            "\tAND CB.CART_AMOUNT >= :startCartAmount\n" +
            "\tAND CB.CART_AMOUNT <= :endCartAmount";

    public static final String INSERT_CAMPAIGN_USER_WHERE_LOGIN_ID = "\tAND CB.LOGIN_ID LIKE :query";

    public static final String INSERT_CAMPAIGN_USER_WHERE_USER_NAME = "\tAND CB.USER_NAME LIKE :query";

    public static final String INSERT_CAMPAIGN_USER_WHERE_RECEIVE = "\tAND CB.RECEIVE_SMS IN (:receiveSms) AND CB.RECEIVE_PUSH IN (:receivePush)";

    public static final String INSERT_CAMPAIGN_USER_WHERE_RECEIVE_SMS_PUSH = "\tAND ( CB.RECEIVE_SMS IN (:receiveSms) OR CB.RECEIVE_PUSH IN (:receivePush) )";

    public static final String DELETE_CAMPAIGN_USER = "DELETE FROM OP_CAMPAIGN_USER WHERE CAMPAIGN_ID = :campaignId";

    public static final String SELECT_CAMPAIGN_USER_WHERE_CAMPAIGN_ID = "SELECT USER_ID FROM OP_CAMPAIGN_USER WHERE CAMPAIGN_ID = :campaignId";

    public static final String INSERT_COUPON_USER = "INSERT INTO OP_COUPON_USER (" +
            "COUPON_ID, \n" +
            "USER_ID, \n" +
            "COUPON_TYPE, \n" +
            "COUPON_NAME, \n" +
            "COUPON_COMMENT, \n" +
            "COUPON_APPLY_TYPE,\n" +
            "COUPON_APPLY_START_DATE, \n" +
            "COUPON_APPLY_END_DATE, \n" +
            "COUPON_PAY_RESTRICTION, \n" +
            "COUPON_CONCURRENTLY, \n" +
            "COUPON_PAY_TYPE, \n" +
            "COUPON_PAY, \n" +
            "COUPON_DISCOUNT_LIMIT_PRICE, \n" +
            "COUPON_TARGET_ITEM_TYPE, \n" +
            "DATA_STATUS_CODE,\n" +
            "COUPON_DOWNLOAD_DATE, \n" +
            "COUPON_USED_DATE, \n" +
            "ORDER_CODE, \n" +
            "ORDER_SEQUENCE, \n" +
            "ITEM_SEQUENCE, \n" +
            "DISCOUNT_AMOUNT, CREATED_DATE" +
            ")\n" +
            "SELECT \n" +
            ":couponId AS COUPON_ID, \n" +
            "USER_ID, \n" +
            ":couponType AS COUPON_TYPE, \n" +
            ":couponName AS COUPON_NAME, \n" +
            ":couponComment AS COUPON_COMMENT, \n" +
            ":couponApplyType AS COUPON_APPLY_TYPE, \n" +
            ":couponApplyStartDate AS COUPON_APPLY_START_DATE, \n" +
            ":couponApplyEndDate AS COUPON_APPLY_END_DATE, \n" +
            ":couponPayRestriction AS COUPON_PAY_RESTRICTION, \n" +
            ":couponConcurrently AS COUPON_CONCURRENTLY, \n" +
            ":couponPayType AS COUPON_PAY_TYPE, \n" +
            ":couponPay AS COUPON_PAY, \n" +
            ":couponDiscountLimitPrice AS COUPON_DISCOUNT_LIMIT_PRICE, \n" +
            ":couponTargetItemType AS COUPON_TARGET_ITEM_TYPE, \n" +
            "'0' AS DATA_STATUS_CODE, \n" +
            ":couponDownloadDate AS COUPON_DOWNLOAD_DATE, \n" +
            "NULL AS COUPON_USED_DATE, \n" +
            "NULL AS ORDER_CODE, \n" +
            "NULL AS ORDER_SEQUENCE, \n" +
            "NULL AS ITEM_SEQUENCE, \n" +
            "NULL AS DISCOUNT_AMOUNT, \n" +
            ":createdDate AS CREATED_DATE \n" +
            "FROM OP_CAMPAIGN_USER \n" +
            "WHERE CAMPAIGN_ID = :campaignId";


    public static final String SELECT_COUPON_USER_WHERE_DATA_STATUS_CODE =
            "\tSELECT CU.USER_ID FROM OP_CAMPAIGN_USER CAU \n" +
                    "INNER JOIN OP_COUPON_USER CU \n" +
                    "ON CAU.USER_ID = CU.USER_ID \n" +
                    "WHERE CU.COUPON_ID = :couponId \n" +
                    "AND CAU.CAMPAIGN_ID = :campaignId \n" +
                    "AND CU.DATA_STATUS_CODE = '0' ";

    public static final String DELETE_COUPON_USER_WHERE_MULTIPLE_DOWNLOAD_FLAG = "\tDELETE FROM OP_COUPON_USER WHERE USER_ID IN (:userIds)";

    public static final String SELECT_APPLICATION_INFO = "SELECT \n" +
            "\tU.USER_ID,\n" +
            "\tU.LOGIN_ID,\n" +
            "\tU.USER_NAME,\n" +
            "\tUD.PHONE_NUMBER,\n" +
            "\tAI.APPLICATION_NO,\n" +
            "\tAI.APPLICATION_VERSION,\n" +
            "\tAI.DEVICE_TYPE,\n" +
            "\tAI.PUSH_TOKEN,\n" +
            "\tAI.ID,\n" +
            "\tAI.UUID,\n" +
            "\tAI.CREATED,\n" +
            "\tAI.CREATED_BY,\n" +
            "\tAI.UPDATED,\n" +
            "\tAI.UPDATED_BY,\n" +
            "\tAI.VERSION\n" +
            "FROM \n" +
            "\tOP_USER U\n" +
            "\tINNER JOIN OP_USER_DETAIL UD ON U.USER_ID = UD.USER_ID\n" +
            "\tINNER JOIN OP_APPLICATION_INFO AI ON U.USER_ID = AI.USER_ID \n" +
            "\tWHERE U.STATUS_CODE = '9'";

    public static final String SELECT_APPLICATION_INFO_WHERE_LOGIN_ID = "\tAND U.LOGIN_ID LIKE :query";

    public static final String SELECT_APPLICATION_INFO_WHERE_USER_NAME = "\tAND U.USER_NAME LIKE :query";

    public static final String DELETE_CAMPAIGN_URL = "DELETE FROM OP_EVENT_CODE WHERE CAMPAIGN_ID = :campaignId";

    public static final String DELETE_CAMPAIGN_REGULAR_URL = "DELETE FROM OP_CAMPAIGN_REGULAR_URL WHERE CAMPAIGN_REGULAR_ID = :campaignRegularId";

    public static final String SUMMARY_EVENT_REDIRECTION = "SELECT \n" +
            "\tCAMPAIGN_ID,\n" +
            "\tCOALESCE(SUM(REDIRECTION),0) AS REDIRECTION \n" +
            "FROM OP_EVENT_CODE\n" +
            "WHERE CAMPAIGN_ID NOT NULL\n"+
            "GROUP BY CAMPAIGN_ID";

    public static final String UPDATE_CAMPAIGN_ORDER_INFO_FOR_USER = "UPDATE OP_CAMPAIGN_USER \n" +
            "SET \n" +
            "\tORDER_AMOUNT = :orderAmount,\n" +
            "\tORDER_COUNT = :orderCount,\n" +
            "\tSTATISTICS_DATE = :statisticsDate\n" +
            "WHERE CAMPAIGN_ID = :id\n" +
            "\tAND USER_ID = :userId";

    public static final String UPDATE_CAMPAIGN_USER_REDIRECTION = "UPDATE OP_CAMPAIGN_USER \n" +
            "SET REDIRECTION = :redirection\n" +
            "WHERE CAMPAIGN_ID = :campaignId\n" +
            "\tAND USER_ID = :userId";

    public static final String UPDATE_CAMPAIGN_USER_SMS = "UPDATE OP_CAMPAIGN_USER U \n"+
            "INNER JOIN OP_SMS_LOG_TEMP T\n"+
            "ON U.USER_ID = T.USER_ID AND U.CAMPAIGN_ID = T.CAMPAIGN_ID\n"+
            "SET U.SMS_SENT = T.SMS_SENT,\n"+
            "U.SMS_SUCCESS = T.SMS_SUCCESS,\n"+
            "U.STATISTICS_DATE = :statisticsDate";

    public static final String UPDATE_CAMPAIGN_USER_MMS = "UPDATE OP_CAMPAIGN_USER U\n"+
            "INNER JOIN OP_MMS_LOG_TEMP T\n"+
            "ON U.USER_ID = T.USER_ID AND U.CAMPAIGN_ID = T.CAMPAIGN_ID \n"+
            "SET U.MMS_SENT = T.MMS_SENT,\n"+
            "U.MMS_SUCCESS = T.MMS_SUCCESS,\n"+
            "U.STATISTICS_DATE = :statisticsDate";

    public static final String UPDATE_CAMPAIGN_USER_KAKAO = "UPDATE OP_CAMPAIGN_USER U \n"+
            "INNER JOIN OP_KAKAO_LOG_TEMP T\n"+
            "ON U.USER_ID = T.USER_ID AND U.CAMPAIGN_ID = T.CAMPAIGN_ID\n"+
            "SET U.KAKAO_SENT = T.KAKAO_SENT,\n"+
            "U.KAKAO_SUCCESS = T.KAKAO_SUCCESS,\n"+
            "U.STATISTICS_DATE = :statisticsDate";

    public static final String UPDATE_CAMPAIGN_USER_PUSH = "UPDATE OP_CAMPAIGN_USER U \n"+
            "INNER JOIN OP_PUSH_LOG_TEMP T \n"+
            "ON U.USER_ID = T.USER_ID AND U.CAMPAIGN_ID = T.CAMPAIGN_ID \n"+
            "SET U.PUSH_SENT = T.PUSH_SENT,\n"+
            "U.PUSH_SUCCESS = T.PUSH_SUCCESS,\n"+
            "U.PUSH_RECEIVE = T.PUSH_RECEIVE,\n"+
            "U.STATISTICS_DATE = :statisticsDate";

    public static final String SELECT_CAMPAIGN_ID_BY_ID = "SELECT \n" +
            "\tCAMPAIGN_ID\n" +
            "FROM OP_EVENT_CODE\n" +
            "WHERE ID = :id";
}
