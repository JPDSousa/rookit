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
package org.rookit.utils.guice;

import com.google.gson.JsonParser;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.util.Modules;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BoundedCollection;
import org.rookit.utils.collection.CollectionsModule;
import org.rookit.utils.io.DummyInputStream;
import org.rookit.utils.io.DummyOutputStream;
import org.rookit.utils.io.DummyReader;
import org.rookit.utils.io.DummyWriter;
import org.rookit.utils.object.ObjectModule;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalFactoryImpl;
import org.rookit.utils.optional.OptionalShort;
import org.rookit.utils.optional.OptionalUtils;
import org.rookit.utils.optional.OptionalUtilsImpl;
import org.rookit.utils.primitive.PrimitiveModule;
import org.rookit.utils.reflect.BaseExtendedClassFactory;
import org.rookit.utils.reflect.ExtendedClassFactory;
import org.rookit.utils.reflect.ReflectModule;
import org.rookit.utils.registry.RegistryModule;
import org.rookit.utils.repetition.RepetitionModule;
import org.rookit.utils.string.StringModule;
import org.rookit.utils.string.template.Template1;
import org.rookit.utils.string.template.TemplateFactory;
import org.rookit.utils.supplier.SupplierUtils;
import org.rookit.utils.supplier.SupplierUtilsImpl;
import org.rookit.utils.system.SystemModule;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableSet;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

@SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
public final class UtilsModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new UtilsModule(),
            CollectionsModule.getModule(),
            ObjectModule.getModule(),
            PrimitiveModule.getModule(),
            ReflectModule.getModule(),
            RegistryModule.getModule(),
            RepetitionModule.getModule(),
            StringModule.getModule(),
            SystemModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private UtilsModule() {}

    @Override
    protected void configure() {
        bindDummy();
        bindOptionals();
        bindMulti();
        bindKeyed();
        bindGson();

        bind(OptionalUtils.class).toInstance(OptionalUtilsImpl.create());
        bind(SupplierUtils.class).toInstance(SupplierUtilsImpl.create());
        //bind(PrintUtils.class).to(PrintUtilsImpl.class).in(Singleton.class);
        bind(OptionalFactory.class).to(OptionalFactoryImpl.class).in(Singleton.class);
        bind(ExtendedClassFactory.class).to(BaseExtendedClassFactory.class).in(Singleton.class);
        // TODO might be configurable
        bind(Locale.class).toInstance(Locale.getDefault());
        bind(Charset.class).toInstance(Charset.forName("UTF-8"));
    }

    private void bindGson() {
        bind(JsonParser.class).toInstance(new JsonParser());
    }

    private void bindKeyed() {
        final Key<Class<?>> key = Key.get(new TypeLiteral<Class<?>>() {}, Keyed.class);
        final Multibinder<Class<?>> multibinder = Multibinder.newSetBinder(binder(), key);

        multibinder.addBinding().toInstance(Map.class);
        // TODO missing other library's map structures
    }

    private void bindMulti() {
        final Key<Class<?>> key = Key.get(new TypeLiteral<Class<?>>() {}, Multi.class);
        final Multibinder<Class<?>> multibinder = Multibinder.newSetBinder(binder(), key);

        multibinder.addBinding().toInstance(Collection.class);
        multibinder.addBinding().toInstance(Set.class);
        multibinder.addBinding().toInstance(List.class);
        multibinder.addBinding().toInstance(Queue.class);
        multibinder.addBinding().toInstance(Deque.class);
        multibinder.addBinding().toInstance(NavigableSet.class);
        multibinder.addBinding().toInstance(SortedSet.class);
        multibinder.addBinding().toInstance(Bag.class);
        multibinder.addBinding().toInstance(BoundedCollection.class);
    }

    private void bindOptionals() {
        final Key<Class<?>> key = Key.get(new TypeLiteral<Class<?>>() {}, Optional.class);
        final Multibinder<Class<?>> multibinder = Multibinder.newSetBinder(binder(), key);

        multibinder.addBinding().toInstance(org.rookit.utils.optional.Optional.class);
        multibinder.addBinding().toInstance(OptionalInt.class);
        multibinder.addBinding().toInstance(OptionalShort.class);
        multibinder.addBinding().toInstance(OptionalLong.class);
        multibinder.addBinding().toInstance(OptionalBoolean.class);
        multibinder.addBinding().toInstance(OptionalDouble.class);
        multibinder.addBinding().toInstance(java.util.Optional.class);
        multibinder.addBinding().toInstance(com.google.common.base.Optional.class);
    }

    private void bindDummy() {
        bind(InputStream.class).annotatedWith(Dummy.class).toInstance(DummyInputStream.get());
        bind(OutputStream.class).annotatedWith(Dummy.class).toInstance(DummyOutputStream.get());
        bind(Reader.class).annotatedWith(Dummy.class).toInstance(DummyReader.get());
        bind(Writer.class).annotatedWith(Dummy.class).toInstance(DummyWriter.get());
    }

    @Provides
    @Singleton
    @Self
    Template1 self(final TemplateFactory templateFactory) {
        return templateFactory.template1();
    }
}
