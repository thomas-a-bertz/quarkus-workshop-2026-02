package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.domain.OrderEntity;
import de.workshop.quarkus.orders.domain.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
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

import java.net.URI;
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

    @PostConstruct
    public void setup() {
        System.out.println("OrderResource erstellt");
    }

    @PreDestroy
    public void raeumeAuf() {
        System.out.println("OrderResource wird beendet");
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
    @Override
    public Response createOrder(@Valid OrderDTO orderDTO) {
        OrderEntity orderEntity = toEntity(orderDTO);
        orderService.saveOrder(orderEntity);

        URI location = UriBuilder
                .fromResource(OrderResource.class)
                .path(orderEntity.getOrderId().toString())
                .build();
        return Response.created(location).build();
    }

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
