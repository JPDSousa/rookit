package org.rookit.dm.genre;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.key.GenreKey;
import org.rookit.dm.factory.FactoryTest;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.exception.ResourceCreationException;
import org.rookit.test.junit.categories.UnitTest;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@UnitTest
class GenreFactoryImplUnitTest extends AbstractUnitTest<GenreFactoryImpl>
        implements FactoryTest<GenreFactoryImpl, Genre, GenreKey> {

    private static final Injector INJECTOR = Guice.createInjector();

    @Override
    protected GenreFactoryImpl doCreateTestResource() throws ResourceCreationException {
        return INJECTOR.getInstance(GenreFactoryImpl.class);
    }

    @Test
    public void testConsistentCreationByName() {
        final String testName = "genre";
        final Genre expected = this.testResource.createGenre(testName);
        final Genre actual = this.testResource.createGenre(testName);

        assertThat(actual)
                .as("One of the created genres")
                .isEqualTo(expected);
    }

    @SuppressWarnings("JUnitTestMethodWithNoAssertions")
    @TestFactory
    public Collection<DynamicTest> testInvalidGenreExceptionNull() {
        return testBlankStringArgument(arg -> this.testResource.createGenre(arg));
    }

    @Test
    public void testCreationByKeyCallsKeyGetName() {
        final GenreKey key = createKey();

        this.testResource.create(key);
        verify(key, times(1)).name();
    }

    @Test
    @Override
    public void testConsistentCreationByEmpty() {
        assertThatThrownBy(() -> this.testResource.createEmpty())
            .as("Creating an empty genre is not possible")
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Override
    public GenreKey createKey() {
        final GenreKey mockedKey = mock(GenreKey.class);
        when(mockedKey.name()).thenReturn("AnAwesomeGenre");

        return mockedKey;
    }
}