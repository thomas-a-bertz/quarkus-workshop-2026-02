package de.workshop.quarkus.invoices;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;

@ApplicationScoped
public class InvoiceService {

    private static final BigDecimal HUNDRED = new BigDecimal("100");
    private static final BigDecimal ALL_ITEMS_PRICE_EURO = new BigDecimal("14.97");
    private static final BigDecimal VAT_RATE_PERCENT = new BigDecimal("19");

    private final Map<UUID, Invoice> invoices = new ConcurrentHashMap<>();

    public Collection<Invoice> getAll() {
        return invoices.values();
    }

    public void save(InvoiceRequest request) {
        UUID invoiceId = UUID.randomUUID();
        var bruttoGesamtsummeEuro = valueOf(request.amount())
                .multiply(ALL_ITEMS_PRICE_EURO)
                .multiply(ONE.add(VAT_RATE_PERCENT
                             .divide(HUNDRED, RoundingMode.HALF_UP)));
        var invoice = new Invoice(invoiceId, request.customerName(),
                bruttoGesamtsummeEuro.setScale(2, RoundingMode.HALF_EVEN));
        invoices.put(invoiceId, invoice);
        System.out.println("Invoice " + invoice + " has been saved.");
    }
}
