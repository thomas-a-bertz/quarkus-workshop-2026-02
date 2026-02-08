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
Nun haben wir einen zweiten Microservice *Invoices*, mit dem wir (als Anwender) ebenfalls via REST-API kommunizieren können.
Damit auch der *Orders*-Microservice mit *Invoices* auf diese Art kommunizieren kann muss *Orders* selbst REST-Requests absetzen können, er muss Client werden. 

### Synchrone Kommunikation via *REST*
- siehe `Tag5.Aufgaben.md` *RQ6*
- Nach Umsetzung dieser Aufgabe haben wir eine microservice-übergreifende Kommunikation. Diese ist allerdings synchron mit allen Vor- und Nachteilen.
- Vorteile
  - leicht zu implementieren
  - sehr anschaulich
  - relativ einfach zu testen
- Nachteile
  - mit einem Request kann nur genau ein Service erreicht werden, nicht mehrere
  - wenn der Zielservice nicht läuft, haben wir ein Problem: wohin mit der Notifikation, dass wir fertig sind?
  - Wenn der Zielservice lange Berechnungen macht oder spät antwortet blockiert unser Service
  - es besteht eine starke Kopplung zwischen den Services

### Asynchrone Kommunikation via *Messaging*
- Den obengenannten Nachteilen begegnet man mit Messaging (z. B. Messages über Queues oder Events)-
- Statt eines synchronen Aufrufs (z. B. REST-Request) erzeugt man ein Event oder sendet eine Message und delegiert die weitere Verarbeitung so an eine externe Instanz (fire and forget). Der Producer dieses Events/der Message kann sofort weiterarbeiten.
- Die Lösung wurde aus Zeitgründen und Gründen des Aufsetzens einer nicht trivialen Infrastruktur (z. B. Apache Artemis Service als Message Broker) nicht gezeigt.
- In diesem Zusammenhang sei auf die folgenden Quarkus-Quellen verwiesen
  - Guides
    - [Quarkus Messaging Extensions](https://quarkus.io/guides/messaging)
    - [Getting Started to Quarkus Messaging with AMQP 1.0](https://quarkus.io/guides/amqp)
  - [AMQP Quickstart Demo-Solution](https://github.com/quarkusio/quarkus-quickstarts/tree/main/amqp-quickstart)

## Konfiguration der App
- Es gibt verschiedene Möglichkeiten, eine Quarkus-App zur Laufzeit mit Werten zu versorgen, zu konfigurieren, siehe dazu
  - Quarkus [Configuration Reference Guide](https://quarkus.io/guides/config-reference)
  - Zugriff auf selbstdefinierte Werte in der `application.properties`, siehe Quarkus Guide [Configuring Your Application](https://quarkus.io/guides/config)

## Security
### REST-Endpunkte absichern

### Testinstanz zum Erzeugen von JWTs

## OpenAPI Contract-First


## Health-Checks
