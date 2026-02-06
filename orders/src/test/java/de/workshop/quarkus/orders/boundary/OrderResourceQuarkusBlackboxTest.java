package de.workshop.quarkus.orders.boundary;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO1;
import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO2;
import static de.workshop.quarkus.orders.util.OrderEntityTestFactory.TEST_ENTITY1;
import static de.workshop.quarkus.orders.util.OrderEntityTestFactory.TEST_ENTITY2;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

// hat Integrationstest-Charakter
@QuarkusTest
@TestHTTPEndpoint(OrderResource.class)
class OrderResourceQuarkusBlackboxTest {

    // black box
    @Test
    void getOrder_shouldReturnAllCreatedOrders() {
        // arrange
        given()
                .header("Content-Type", "application/json")
                .body(TEST_ENTITY1.create())
                .when().post()
                        .then().statusCode(201);
        given()
                .header("Content-Type", "application/json")
                .body(TEST_ENTITY2.create())
                .when().post()
                        .then().statusCode(201);
        OrderDTO dto1 = TEST_DTO1.create();
        OrderDTO dto2 = TEST_DTO2.create();

        // act
        given()
                .when().get() // default prefix from @TestHTTPEndpoint
                .then()
                // assert
                .statusCode(200)
                .body("$.size()", equalTo(2))
                .body("customerLastname", containsInAnyOrder(
                        dto1.getCustomerLastname(),
                        dto2.getCustomerLastname()))
                // customerFirstname wird von OrderEntity nicht mehr verwaltet
                .body("itemDescription", containsInAnyOrder(
                        dto1.getItemDescription(),
                        dto2.getItemDescription()))
                .body("amount", containsInAnyOrder(
                        dto1.getAmount(),
                        dto2.getAmount()));
    }
}
