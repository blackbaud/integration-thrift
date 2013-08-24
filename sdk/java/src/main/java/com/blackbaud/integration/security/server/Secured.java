package com.blackbaud.integration.security.server;

import com.blackbaud.integration.generated.types.Credential;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifies services whose public methods should be secured via a {@link Credential} argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {
}
