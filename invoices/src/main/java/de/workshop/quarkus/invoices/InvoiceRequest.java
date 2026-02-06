package de.workshop.quarkus.invoices;

public record InvoiceRequest(
        String customerName,
        int amount) {
}
