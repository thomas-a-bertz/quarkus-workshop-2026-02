package de.workshop.quarkus.orders;

import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;

import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO1;
import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO2;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit-Test
class OrderResourceTest {

    private static final int ONCE = 1;
    OrderResource cut; // class under test
    OrderService orderService;

    // black box
    @Test
    void getOrders_blackBox() {
        // arrange
        orderService = new OrderService();
        cut = new OrderResource(orderService);
        cut.createOrder(TEST_DTO1.create());
        cut.createOrder(TEST_DTO2.create());

        // act
        Response response = cut.getOrders();

        // assert
        assertEquals(OK.getStatusCode(), response.getStatus());
        assertThat(response.readEntity(new GenericType<Collection<OrderDTO>>() {}))
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .ignoringFields("orderId")
                .isEqualTo(List.of(TEST_DTO2.create(), TEST_DTO1.create()));
    }

    // white box
    @Test
    void getOrders_whiteBox() {
        orderService = Mockito.mock(OrderService.class);
        Mockito.when(orderService.getOrders()).thenReturn(List.of(TEST_DTO1.create()));
        cut = new OrderResource(orderService);

        cut.getOrders();

        Mockito.verify(orderService, Mockito.times(ONCE)).getOrders();
        Mockito.verifyNoMoreInteractions(orderService);
    }
}