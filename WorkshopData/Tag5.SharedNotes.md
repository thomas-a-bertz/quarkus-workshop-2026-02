# Shared Notes (Tag 5)
## Zweiter Microservice (Invoices)
Das Duplizieren vom Orders-Microservice und Umbenennen jeglichen Vorkommens von `Order*` nach `Invoice*` ist zu aufwendig. Stattdessen besser einfach einen neues Quarkus-Projekt erzeugen und mit minimalen fachlichen Anforderungen umsetzen. Für diesen Workshop reicht es, wenn
- es keine Datenbank-Anbindung gibt
  - Hibernate fällt weg
  - Liquibase fällt weg
  - InvoiceEntity fällt weg
- es keine Trennung zwischen `boundary` und `domain` gibt
  - `InvoiceDTO` fällt weg und wird mit `Invoice` zusammengelegt

## Inter-Microservice-Kommunikation
### Synchrone Kommunikation via *REST*

### Asynchrone Kommunikation via *Messaging*


## Security
### REST-Endpunkte absichern

### Testinstanz zum Erzeugen von JWTs

## OpenAPI Contract-First


## Health-Checks
