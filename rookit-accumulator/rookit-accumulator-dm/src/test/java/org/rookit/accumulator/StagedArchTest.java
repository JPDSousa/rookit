
package org.rookit.accumulator;

import accumulator.Accumulator;
import org.junit.jupiter.api.Test;
import org.rookit.test.RookitTest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
public interface StagedArchTest<T extends Accumulator<?, T, ?>> extends RookitTest<T> {
    
    @Test
    default void testAllOverridenMethods() {
        final Class<?> clazz = getTestResource().getClass();
        final Set<String> accumulatorMethods = Arrays.stream(clazz.getMethods())
                .map(Method::getName)
                .filter(methodName -> methodName.endsWith("Accumulator"))
                .collect(Collectors.toSet());
        final Set<String> accumulatorOverriddenMethods = Arrays.stream(clazz.getDeclaredMethods())
                .map(Method::getName)
                .filter(methodName -> methodName.endsWith("Accumulator"))
                .collect(Collectors.toSet());
        
        assertThat(accumulatorMethods)
            .as("The methods that should be overriden by the staged accumulator")
            .containsExactlyInAnyOrderElementsOf(accumulatorOverriddenMethods);
    }

}
