package org.rookit.test.categories;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("javadoc")
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Tag(TestCategories.ARCH)
public @interface ArchTest {
}
