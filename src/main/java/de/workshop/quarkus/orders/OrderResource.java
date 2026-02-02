package de.workshop.quarkus.orders;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path(("/orders"))
public class OrderResource {

    private final Map<UUID, OrderDTO> orders = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder() {
        return Response.ok(orders.values()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderDTO order) {
        order.setOrderId(UUID.randomUUID());
        orders.put(order.getOrderId(), order);

        URI location = UriBuilder
                .fromResource(OrderResource.class)
                .path(order.getOrderId().toString())
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId") UUID orderId) {
        if (orders.containsKey(orderId)) {
            return Response.ok(orders.get(orderId)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
