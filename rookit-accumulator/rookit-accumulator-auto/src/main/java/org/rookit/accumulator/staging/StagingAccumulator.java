package org.rookit.accumulator.staging;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@SuppressWarnings("javadoc")
@Retention(SOURCE)
@Target(TYPE)
public @interface StagingAccumulator {
    
    // empty on purpose

}
