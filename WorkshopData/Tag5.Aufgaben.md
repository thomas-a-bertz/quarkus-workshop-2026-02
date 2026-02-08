# Aufgaben
Baue eine REST-Kommunikation zwischen den Microservices auf, bei der *Orders* nach dem erfolgreichen Anlegen einer Bestellung dem *Invoices* Bescheid gibt, dass er die Rechnung erstellen kann.

## RQ6: *Order erstellt* benachrichtigt den *Invoices*-Microservice darüber
- `POST /invoices` nimmt einen `InvoiceRequest` entgegen (siehe OpenAPI-Spec)
- wenn *Invoices* mit `201 (Created)` antwortet, kann *Orders* selbst seinem Aufrufer mit `201 (Created)` antworten

