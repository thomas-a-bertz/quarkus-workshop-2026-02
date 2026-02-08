package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.domain.OrderEntity;
import de.workshop.quarkus.orders.domain.OrderService;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static de.workshop.quarkus.orders.boundary.OrderMapper.toDTO;
import static de.workshop.quarkus.orders.boundary.OrderMapper.toEntity;

@Path("/orders")
@RequestScoped
public class OrderResource implements OrderAPI {

    private final OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @APIResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = OrderDTO.class)
            ),
            description = "Orders existieren"
    )
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        return Response.ok(orderService.getOrders().stream()
                .map(OrderMapper::toDTO)
                .toList()).build();
    }

    @APIResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = URI.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = MyErrorResponse.class)
            )
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Blocking
    @Override
    public Response createOrder(@Valid OrderDTO orderDTO) {
        OrderEntity orderEntity = toEntity(orderDTO);
        orderService.saveOrder(orderEntity);

        // nächste Erweiterung: anderen Microservices Bescheid geben,
        // dass eine Bestellung eingegangen ist
        // Möglichkeiten
        //   1. via REST: POST-Request (synchron = blockierend!)
        doSynchronousRESTcall(orderEntity.getCustomerLastname(), orderEntity.getAmount());
        //   2. via "send Message"/"publish Event" OrderCreated (asynchron, fire and forget)

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

//    @RolesAllowed({"Praktikant", "Mitarbeiter"})
    @PermitAll
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getOrder(@Parameter(
            description = "die orderId (im UUID-Format)",
            example = "daaa9f8a-1ace-46b9-aa68-598eaf6acf3f"
    )
            @PathParam("orderId") UUID orderId) {
        return orderService.getOrder(orderId)
                .map(entity -> Response.ok(toDTO(entity)).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
