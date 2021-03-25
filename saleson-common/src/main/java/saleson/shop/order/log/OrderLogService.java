package saleson.shop.order.log;

public interface OrderLogService {

    void put(String orderCode);

    void put(String orderCode, boolean processPgFlag);
}
