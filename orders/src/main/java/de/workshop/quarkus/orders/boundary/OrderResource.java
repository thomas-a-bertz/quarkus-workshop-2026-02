package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.boundary.api.OrdersApi;
import de.workshop.quarkus.orders.domain.OrderEntity;
import de.workshop.quarkus.orders.domain.OrderService;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

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
public class OrderResource extends OrdersApi {

    @Inject
    JsonWebToken jwt;

    private final OrderService orderService;

    @Inject
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @GET
    @Path("/{orderId}")
    @Override
    public Response ordersOrderIdGet(@PathParam("orderId") @Pattern(regexp="[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}") UUID orderId) {
        return Response.ok(orderService.getOrders().stream()
                .map(OrderMapper::toDTO)
                .toList()).build();
    }


//    @RolesAllowed({"Praktikant", "Mitarbeiter"})
//    @Override
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getOrders(@Context SecurityContext ctx) {
//        logSecurityContext(ctx);
//
//        return Response.ok(orderService.getOrders().stream()
//                .map(OrderMapper::toDTO)
//                .toList()).build();
//    }

    private void logSecurityContext(SecurityContext ctx) {
        String name;
        if (ctx.getUserPrincipal() == null) {
            name = "anonymous";
        } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        } else {
            name = ctx.getUserPrincipal().getName();
        }
        System.out.println("GET /orders called with: " + String.format("name = %s,"
                        + " isHttps: %s,"
                        + " authScheme: %s,"
                        + " hasJWT: %s",
                name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt()));
    }

    private boolean hasJwt() {
        return jwt.getClaimNames() != null;
    }

    @RolesAllowed({"Mitarbeiter"})
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
