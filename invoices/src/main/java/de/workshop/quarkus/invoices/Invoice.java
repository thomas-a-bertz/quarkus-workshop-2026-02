package de.workshop.quarkus.invoices;

import java.math.BigDecimal;
import java.util.UUID;

public record Invoice(
        UUID invoiceId,
        String customerName,
        BigDecimal bruttoGesamtsummeEuro) {
}
