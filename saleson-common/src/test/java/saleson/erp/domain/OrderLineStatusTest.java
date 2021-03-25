package saleson.erp.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineStatusTest {

    @Test
    void ordeLineStatusTest() {

        OrderLineStatus orderLineStatus = new OrderLineStatus();

        orderLineStatus.setBundleNo("K1000000464013");
        orderLineStatus.setOrdStatus("배송중");

        assertThat(orderLineStatus.getOrderCode()).isEqualTo("K1000000464");
        assertThat(orderLineStatus.getItemSequence()).isEqualTo(13);
        assertThat(orderLineStatus.getOrderStatus()).isEqualTo("30");
    }
}