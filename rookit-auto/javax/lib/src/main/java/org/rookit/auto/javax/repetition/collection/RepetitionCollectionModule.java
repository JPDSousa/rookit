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
package org.rookit.auto.javax.repetition.collection;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import org.rookit.auto.javax.repetition.TypeMirrorRepetitionConfig;
import org.rookit.utils.guice.Multi;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

public final class RepetitionCollectionModule extends AbstractModule {

    private static final Module MODULE = new RepetitionCollectionModule();

    public static Module getModule() {
        return MODULE;
    }

    private RepetitionCollectionModule() {}

    @Override
    protected void configure() {
        final Multibinder<TypeMirrorRepetitionConfig> collection = newSetBinder(binder(),
                TypeMirrorRepetitionConfig.class, Multi.class);
        collection.addBinding().toProvider(JavaCollectionProvider.class).in(Singleton.class);
        collection.addBinding().toProvider(JavaListProvider.class).in(Singleton.class);
        collection.addBinding().toProvider(JavaSetProvider.class).in(Singleton.class);
        collection.addBinding().toProvider(JavaQueueProvider.class).in(Singleton.class);
        collection.addBinding().toProvider(ApacheBagProvider.class).in(Singleton.class);
        // TODO missing other types of collections
    }
}
