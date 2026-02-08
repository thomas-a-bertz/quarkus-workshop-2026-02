package de.workshop.quarkus.orders.boundary;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;

@Path(("/orders"))
public interface OrderAPI {

    @APIResponse(
            responseCode = "200",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = OrderDTO.class, type = ARRAY)
            )
    )
    @GET
    Response getOrders(@Context SecurityContext securityContext);

    @APIResponse(
            responseCode = "201",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = URI.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MyErrorResponse.class)
            )
    )
    @POST
    Response createOrder(@Valid OrderDTO order);

    @GET
    @Path("/{orderId}")
    Response getOrder(UUID orderId, SecurityContext ctx);
}
