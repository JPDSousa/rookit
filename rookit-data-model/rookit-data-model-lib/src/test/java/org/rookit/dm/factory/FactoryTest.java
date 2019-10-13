package org.rookit.dm.factory;

import org.junit.jupiter.api.Test;
import org.rookit.api.dm.factory.RookitFactory;
import org.rookit.api.dm.key.Key;
import org.rookit.test.RookitTest;
import org.rookit.test.exception.ResourceCreationException;

import static org.assertj.core.api.Assertions.assertThat;

public interface FactoryTest<T extends RookitFactory<E, K>, E, K extends Key> extends RookitTest<T> {

    K createKey();

    @Test
    default void testConsistentCreationByKey() throws ResourceCreationException {
        final T factory = createTestResource();
        final K key = createKey();
        final E expected = factory.create(key);
        final E actual = factory.create(key);

        assertThat(actual)
                .as("One of the objects created with key %s", key)
                .isEqualTo(expected);
    }

    @Test
    default void testConsistentCreationByEmpty() throws ResourceCreationException {
        final T factory = createTestResource();
        final E expected = factory.createEmpty();
        final E actual = factory.createEmpty();

        assertThat(actual)
                .as("One of the empty objects")
                .isEqualTo(expected);
    }

}
