package de.workshop.quarkus.orders;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO1;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.times;

// hat Integrationstest-Charakter
@QuarkusTest
@TestHTTPEndpoint(OrderResource.class)
public class OrderResourceQuarkusWhiteboxTest {

    private static final int ONCE = 1;

    @InjectMock
    public OrderService orderServiceMock;

    // white box
    @Test
    void getOrder_shouldReturnListWithOneItem() {
        Mockito.when(orderServiceMock.getOrders())
                .thenReturn(List.of(TEST_DTO1.create()));

        given()
                .when().get()
                .then()
                .statusCode(200);

        Mockito.verify(orderServiceMock, times(ONCE)).getOrders();
        Mockito.verifyNoMoreInteractions(orderServiceMock);
    }
}
