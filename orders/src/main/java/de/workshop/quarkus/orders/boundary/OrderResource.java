package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.boundary.api.ApiException;
import de.workshop.quarkus.orders.boundary.api.OrderResourceApi;
import de.workshop.quarkus.orders.boundary.model.OrderDTO;
import de.workshop.quarkus.orders.domain.OrderEntity;
import de.workshop.quarkus.orders.domain.OrderService;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

import static de.workshop.quarkus.orders.boundary.OrderMapper.toDTO;
import static de.workshop.quarkus.orders.boundary.OrderMapper.toEntity;

@Path("/orders")
@RequestScoped
public class OrderResource implements OrderResourceApi {

    private final OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get Orders
     *
     */
    @Override
    public List<OrderDTO> ordersGet() throws ApiException, ProcessingException {
        return orderService.getOrders();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response ordersOrderIdGet(@PathParam("orderId") @Pattern(regexp="[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}") UUID orderId) {
        return orderService.getOrder(orderId)
                .map(entity -> Response.ok(toDTO(entity)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @Blocking
    @Override
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    public Response ordersPost(@Valid @NotNull OrderDTO orderDTO) {
        OrderEntity orderEntity = toEntity(orderDTO);
        orderService.saveOrder(orderEntity);

        doSynchronousRESTcall(orderEntity.getCustomerLastname(), orderEntity.getAmount());

        URI location = UriBuilder
                .fromResource(OrderResource.class)
                .path(orderEntity.getOrderId().toString())
                .build();
        return Response.created(location).build();
    }

    private static void doSynchronousRESTcall(String customerName, int amount) {
        var httpClient = HttpClient.newBuilder().build();
        String invoiceRequestJson = "\n" +
                "{\n" +
                "    \"customerName\": \"" + customerName + "\",\n" +
                "    \"amount\": " + amount + "\n" +
                "}";
        var request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/invoices"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(invoiceRequestJson))
                .build();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Fehler beim Versenden des InvoiceRequests: " + e.getMessage());
            throw new RuntimeException(e);
        }
        if (response == null || response.statusCode() != 201) {
            System.err.println("Versenden des InvoiceRequests nicht erfolgreich");
        }
    }
}
