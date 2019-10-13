
package org.rookit.mongodb.test.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("javadoc")
public class DefaultTest {

    private static class TestModule extends AbstractModule {

        private final MongoClient client;

        private TestModule(final MongoClient client) {
            super();
            this.client = client;
        }

        @Override
        protected void configure() {
            when(this.client.getDatabase(any())).then(invocation -> {
                final String name = invocation.getArgument(0);

                return null; // TODO change me
            });

            bind(MongoClient.class).toInstance(this.client);
            bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);

        }

    }

    private static final MongoClient CLIENT = mock(MongoClient.class);
    private static final Injector INJECTOR = Guice.createInjector(new TestModule(CLIENT));

    @AfterAll
    public static void closeClient() {
        CLIENT.close();
    }

    private MongoDatabase database;

    @BeforeEach
    public final void setUp() {
        this.database = INJECTOR.getInstance(MongoDatabase.class);
    }

    @Test
    public final void testMongoDatabaseProvider() {
        assertThat(this.database.getName()).isEqualTo(Thread.currentThread().getName());
    }
}
