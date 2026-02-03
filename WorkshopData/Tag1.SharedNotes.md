# Shared Notes (Tag 1)
## Projekt erzeugen
### via Zip-Datei von Website
- https://quarkus.io/ Start Coding

### via Maven
- Pfad zu einem *JDK* setzen (je nach dem, wo dieser installiert ist)
  - `set java_home=C:\Program Files\Java\jdk21` (Windows Cmdline)
  - `export JAVA_HOME=/c/Program\ Files/Java/jdk21` (Linux/ Bash)
- `mvn io.quarkus:quarkus-maven-plugin:create -DplatformVersion=3.31.1 -DprojectGroupId=de.workshop.quarkus -DprojectArtifactId=orders -DclassName="de.workshop.quarkus.orders.GreetingResource" -Dpath="/hello"` (`platformVersion` ggf. an Eure Umgebung anpassen, je nach dem, was im Artifactory liegt)

## Bereitstellen einer REST-API
- wir verwenden die Extension `io.quarkus:quarkus-rest-jsonb`
- Extension via [quarkus.io](https://quarkus.io/)-Website unter *Browse Extensions* suchen und nach Anleitung zum Projekt hinzufügen
- Damit können wir dann
  - REST-Endpunkte (Ressource + Verb) bereitstellen und
  - Java-Klassen (DTOs) von/nach *JSON* (de-)serialisieren
- optional aber sehr nützlich: einen browser-basierten, integrierten REST-Client
  - Extension `io.quarkus:quarkus-smallrye-openapi`

## Wie man gute RESTful APIs designed
- Ein Dokument zum Nachschlagen: [Zalando RESTful API and Event Guidelines](https://opensource.zalando.com/restful-api-guidelines/)

## Swagger-UI
- ein eigener, integrierter REST-Client
- erreichbar bei laufender Anwendung im DEV-Mode (`./mvnw quarkus:dev`)
  - unter [localhost:8080/](http://localhost:8080/)
  - dort auf den blauen *DEV UI*-Button klicken und
  - dann in der *SmallRye OpenAPI*-Kachel auf *Swagger-Benutzeroberfläche* klicken
