# Shared Notes (Tag 2)
## Architektur-Refactoring
- `Map<UUID, Orders>` in `OrderService` rausziehen
- `OrderService` als Dependency in `OrderRessource` injizieren
- CDI
  - `@Inject` an Konstruktor schreiben statt Feld, so ist die `OrderRessource` in einem Kontext *mit CDI* und *ohne CDI* testbar

## Bean Validation
- Extension hinzufügen: `io.quarkus:quarkus-hibernate-validator`
- [Quarkus Guide Validation](https://quarkus.io/guides/validation)

## Spezifizieren mit OpenAPI
- [Microprofile OpenAPI Spec](https://download.eclipse.org/microprofile/microprofile-open-api-4.0.2/microprofile-openapi-spec-4.0.2.pdf)
- wir haben uns zunächst den *Code first*-Ansatz angesehen (also das Schreiben der Spezifikation im Code via Annotationen): gut geeignet für einfache Projekte oder Schulungen
- und kurz mit dem alternativen *Contract first*-Ansatz vergliche (also das Schreiben der Spezifikation als `openapi.{json|yaml}`): besser geeignet für große Projekte mit mehreren Teams und Schnittstellen

## Testing und Mocking (angefangen)
- [Welche *Test Doubles* gibt es? (Robert C. Martin)](https://blog.cleancoder.com/uncle-bob/2014/05/14/TheLittleMocker.html)
  - Fake
  - Dummy
  - Stub
  - Spy
  - Mock
### Mockito (Mocking-Framework)
- [site.mockito.org/](https://site.mockito.org/)
- Dependency (keine Extension) zu finden z.B. unter
[mvnrepository.com/.../mockito-core/5.21.0](https://mvnrepository.com/artifact/org.mockito/mockito-core/5.21.0)

### Unit Tests mit JUnit
- laufen schnell, sind klein, haben keine Abhängigkeiten bzw. Abhängigkeiten sind Test Doubles (Dummy, Stub, Spy oder Mock)
- Extension ist üblicherweise bereits im Startprojekt enthalten, falls nicht: `io.quarkus:quarkus-junit` (im `<scope>` `test`)

### Integrationstests mit RESTassured und `@QuarkusTest` 
- `@QuarkusTest` fährt die komplette Anwendung hoch mit REST, CDI, Dependencies (z. B. `OrderService`), Testdaten und ggf. Datenbank
- getestet wird via REST (Netzwerk) von außen, deshalb laufen diese Tests deutlich langsamer und sind aufwändiger, haben aber auch eine größere Aussagekraft

### Blackbox-Tests
Testet nur das Ein-/Ausgabeverhalten der UUT (Unit under Test). Es werden keine internen Aufrufe geprüft/verifiziert (das ist die Black Box). Wohl müssen aber (interne) Dependencies gemockt werden (also auch deren Verhalten). Diese liegen zwar in der Black Box, aber sie wurden injiziert und sind deshalb vorbestimmt und kontrollierbar. Nur eine Änderung der Fachlichkeit oder des Interfaces kann den Test brechen, kein Implementierungsdetail.

### Whitebox-Tests
Testet gezielt internes Verhalten/interne Logik (=Implementierungsdetails) der UUT (White Box). Auch hier können wir gezielt eingreifen, um interne Dependencies zu mocken. Das ist aber optional und wird zum Vorbelegen von internen Zuständen genutzt. Nicht nur Änderungen der Fachlichkeit und des Interfaces können den Test brechen sondern zusätzlich eine Änderung der Implementierung.