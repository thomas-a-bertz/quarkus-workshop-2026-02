package de.workshop.quarkus.invoices;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;

@Path("/invoices")
public class InvoiceResource {

    @Inject
    InvoiceService invoiceService;

    @APIResponse(
            responseCode = "200",
            description = "gefundene Rechnungen",
            content = @Content(
                    mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = Invoice.class, type = ARRAY)
            )
    )
    @GET
    @Produces(APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(invoiceService.getAll()).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response create(InvoiceRequest invoiceRequest) {
        invoiceService.save(invoiceRequest);
        return Response.status(CREATED).build();
    }
}
