# Shared Notes (Tag 4)
## Liquibase
- zum versionierten Verwalten von Datenbank-Schema-Migrationen
- Quarkus-Extension `io.quarkus:quarkus-liquibase` hinzufügen
- `application.properties` anpassen
  - Datasource(s)
  - `quarkus.hibernate-orm.schema-management.strategy=validate`
  - `quarkus.liquibase.migrate-at-start=true`
- [Quarkus-Guide Using Liquibase](https://quarkus.io/guides/liquibase)
- [Liquibase Original-Dokumentationen](https://www.liquibase.org/)
  - [Implementation Guide](https://docs.liquibase.com/community/implementation-guide-5-0)
  - [User Guide](https://docs.liquibase.com/community/user-guide-5-0)
  - [Reference Guide](https://docs.liquibase.com/reference-guide)
## Test-{Dimensionen | Konzepte}
### Scope, Umfang
Was ist die *Unit Under Test* (UUT)?
- **Unit-Test** (isoliert)
    - Systemgrenze: klein, eine Unit
    - Laufzeit: kurz = schnell
- **Integrationstest** (integriert)
  - Systemgrenze: größer, mehrere Units
  - Laufzeit: länger = langsam

### Exposure, Offenlegung
Was weiß der Test über Interna der UUT?
- **Black-Box-Test**: nichts (nur öffentliche Schnittstelle ist bekannt)
- **White-Box-Test**: alles (interne Felder sind bekannt)

### Focus, Fokus
Worauf richtet der Test seine Beobachtung?
- **State-based**
  - auf den *Zustand* des Ergebnisses
  - andere Namen: Chicago School, Classicist Approach
- **Interaction-based**
  - auf die *Interaktionen* (der Interna)
  - andere Namen: London School, Mockist Approach

## Zweiter Microservice (Invoices)
Ganzes Projektverzeichnis duplizieren und alles, was namentlich *Order* betrifft in *Invoice* umbenennen.
