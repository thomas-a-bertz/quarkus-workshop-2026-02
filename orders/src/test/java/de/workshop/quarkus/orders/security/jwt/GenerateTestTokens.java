package de.workshop.quarkus.orders.security.jwt;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.util.HashSet;

import static java.util.Arrays.asList;

@ApplicationScoped
public class GenerateTestTokens {

    private static final String TOKEN_EXPIRES_IN_MINUTES = "7";

    public static String createPraktikantMitarbeiterToken() {
        return Jwt.issuer("https://example.com/issuer")
                .upn("mitarbeiter17@intern.io")
                .groups(new HashSet<>(asList("Praktikant", "Mitarbeiter")))
                .claim(Claims.birthdate.name(), "1980-05-02")
                .expiresIn(Duration.ofMinutes(Long.parseLong(TOKEN_EXPIRES_IN_MINUTES)))
                .sign();
    }

    public static String createPraktikantToken() {
        return Jwt.issuer("https://example.com/issuer")
                .upn("praktikant1@extern.io")
                .groups(new HashSet<>(asList("Praktikant")))
                .claim(Claims.birthdate.name(), "2001-07-13")
                .expiresIn(Duration.ofMinutes(Long.parseLong(TOKEN_EXPIRES_IN_MINUTES)))
                .sign();
    }

    public static void main(String[] args) {
        System.out.println("Test tokens. They will expire after 5 minutes from now.");
        String praktikantMitarbeiterToken = createPraktikantMitarbeiterToken();
        System.out.println("Token of a person with roles Praktikant and Mitarbeiter\n" + praktikantMitarbeiterToken);

        System.out.println();

        String praktikantToken = createPraktikantToken();
        System.out.println("Token of a person with role Praktikant\n" + praktikantToken);

        System.exit(0);
    }
}
