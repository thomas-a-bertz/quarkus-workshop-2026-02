package de.workshop.quarkus.orders;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/** Sorgt dafür, dass nur geradzahlige Werte erlaubt sind.
 *
 */
@Documented
@Constraint(validatedBy = EvenValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Even {

    String message() default "Wert muss gerade sein";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
