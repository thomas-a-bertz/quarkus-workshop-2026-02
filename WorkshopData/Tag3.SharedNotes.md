# Shared Notes (Tag 3)
## Architektur-Modifikation/-Erweiterung
### Pakete
- Trennung von außen (`boundary` package) und innen (`domain` package)
- die Domäne (mit wertvollem Business Value) bleibt unabhängig von Änderungen im Außen (= Benefit)
### `Entity` anlegen und Mapper verwenden
- Mapper sind notwendig, um zwischen DTOs (`boundary`) und Entity (`domain`) umzuwandeln (= Aufwand/Kosten)

## Continuous Testing
Beim Starten via `./mvnw quarkus:dev` kann nach dem Hochfahren mittels der Taste `r` in den *Continuous Testing Mode* geschaltet werden. Dabei werden alle notwendigen Tests ausgeführt, sobald eine Änderung am Quellcode gespeichert wird. Dadurch gibt auch der Testcode sehr schnelles Feedback beim Entwickeln. Siehe auch [Quarkus-Guide Testing]().

## Datenbank (H2) mit Hibernate-ORM anbinden
### Panache
- Es werden zwei Extensions benötigt
  - JDBC-Treiber (hier für die H2-DB) `io.quarkus:quarkus-jdbc-h2`, [Quarkus-Guide Configure Datasources in Quarkus](https://quarkus.io/guides/datasource)
  - Panache (= Quarkus-Extension mit Hibernate-ORM im Bauch und Repository-Ansatz) `io.quarkus:quarkus-hibernate-orm-panache`, [Quarkus-Guide Simplified Hibernate ORM with Panache](https://quarkus.io/guides/hibernate-orm-panache)
- es müssen Konfigurationseinstellungen in der `application.properties` gemacht werden
- Entity-Klassen müssen mit `@Entity` annotiert werden, damit sie via Hibernate persistiert werden (normales JPA ab hier)


### Transactional Test
Es gibt auch die Möglichkeit, `QuarkusTest`s in einer Transaktion laufen zu lassen, wobei nach jedem Test ein `rollback` gemacht wird und jegliche Änderungen, die aus dem Test resultierten, rückgängig gemacht werden und so die Datenbank nicht beschmutzen. Das war nicht Teil des Workshop-Inhalts, gerne selbst nachlesen: [Quarkus-Guide Testing Your Application](https://quarkus.io/guides/getting-started-testing)

## Konfiguration und Profile verwenden
- konfigurieren in der `application.properties`
- beim Aufruf verwenden mit `-Dquarkus.profile=meinprofil`
- siehe [Quarkus-Guide Configuration Reference](https://quarkus.io/guides/config-reference)
