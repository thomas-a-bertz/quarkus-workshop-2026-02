package de.workshop.quarkus.orders;

import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.UUID;

@Path(("/orders"))
public interface OrderAPI {

    @GET
    Response getOrders();

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
    Response createOrder(@Valid OrderDTO order);

    @GET
    @Path("/{orderId}")
    Response getOrders(UUID orderId);
}
