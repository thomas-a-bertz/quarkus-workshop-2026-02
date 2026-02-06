package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.domain.OrderService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static de.workshop.quarkus.orders.util.OrderEntityTestFactory.TEST_ENTITY1;
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
                .thenReturn(List.of(TEST_ENTITY1.create()));

        given()
                .when().get()
                .then()
                .statusCode(200);

        Mockito.verify(orderServiceMock, times(ONCE)).getOrders();
        Mockito.verifyNoMoreInteractions(orderServiceMock);
    }
}
