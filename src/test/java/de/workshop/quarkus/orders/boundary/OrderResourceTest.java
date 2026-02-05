package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.domain.InMemoryOrderRepository;
import de.workshop.quarkus.orders.domain.OrderService;
import de.workshop.quarkus.orders.domain.OrderRepository;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;

import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO1;
import static de.workshop.quarkus.orders.util.OrderDTOTestFactory.TEST_DTO2;
import static de.workshop.quarkus.orders.util.OrderEntityTestFactory.TEST_ENTITY1;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderResourceTest {

    private static final int ONCE = 1;
    OrderResource cut; // class under test
    OrderService service;

    // black box --> man kann kein 'new OrderRepository()' schreiben und
    // dies in den OrderService injizieren, weil OrderRepository ein
    // PanacheRepository implementiert/ist und Panache braucht CDI.
    // Unsere Möglichkeiten:
    //   1. Integrationstest schreiben und CDI verwenden (@QuarkusTest) oder
    //   2. OrderRepository in ein eigenes Interface wandeln und zwei Implementationen
    //      anbieten: - InMemoryOrderRepository (für den Test verwenden)
    //                - PanacheOrderRepository (für Produktivcode verwenden). Nur diese
    //                  Implementation implementiert zusätzlich PanacheRepository.
    //      Es bleibt ein Unit-Test.
    //      Es bleibt ein BlackBox-Test.
    //      Wir nutzen weiterhin State-based Testing (Chicago/Classicist Style/School).
    //      Wir kennen keine Interna (injizierte Klassen
    //      sind Teil der Infrastruktur, in der die BlackBox lebt/läuft.
    @Test
    void getOrders_UnitBlackBoxStatebasedTest() {
        // arrange
        OrderRepository repo = new InMemoryOrderRepository();
        service = new OrderService(repo);
        cut = new OrderResource(service);
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
                .ignoringActualNullFields() // customerFirstname wird von Entity nicht mehr verwaltet
                .isEqualTo(List.of(TEST_DTO2.create(), TEST_DTO1.create()));
    }

    // White Box Unit-Test
    @Test
    void getOrders_whiteBox() {
        service = Mockito.mock(OrderService.class);
        Mockito.when(service.getOrders()).thenReturn(List.of(TEST_ENTITY1.create()));
        cut = new OrderResource(service);

        cut.getOrders();

        Mockito.verify(service, Mockito.times(ONCE)).getOrders();
        Mockito.verifyNoMoreInteractions(service);
    }
}