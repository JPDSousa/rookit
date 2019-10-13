/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.system.mongodb;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.genre.factory.GenreFactory;
import org.rookit.convention.ConventionModule;
import org.rookit.dm.convention.RookitDataModelConventionModule;
import org.rookit.dm.guice.RookitDataModelModule;
import org.rookit.dm.serializer.RookitDataModelSerializerModule;
import org.rookit.mongodb.guice.MongoDbModule;
import org.rookit.utils.guice.UtilsModule;

public final class GenreDatatoreTest {

    private static final Module MODULE = Modules
            .override(
                    // Covnention basics
                    ConventionModule.getModule(),
                    // Convention data model bindings
                    RookitDataModelConventionModule.getModule(),
                    // A data model implementation, required by the persistence layer
                    RookitDataModelModule.getModule(),
                    // Data Model Seriailzers
                    RookitDataModelSerializerModule.getModule(),
                    // The module with the base utils implementations
                    UtilsModule.getModule())
            .with(
                    // The module with the logic under test
                    MongoDbModule.getModule()
            );

    private static Injector injector;

    @BeforeAll
    public static void createInjector() {
        injector = Guice.createInjector(MODULE);
    }

    private GenreDataStore dataStore;
    private GenreFactory genreFactory;

    @BeforeEach
    public void setup() {
        this.dataStore = injector.getInstance(GenreDataStore.class);
        this.genreFactory = injector.getInstance(GenreFactory.class);
    }

    @Test
    public void testInsertion() {
        this.dataStore.insert(this.genreFactory.createGenre("SomeRandomGenre"));
    }

    @Test
    public void testUpdate() {
        this.dataStore.update()
                .setDescription("Add Some Description")
                .where()
                .withAnyId()
                .execute();
    }
}
