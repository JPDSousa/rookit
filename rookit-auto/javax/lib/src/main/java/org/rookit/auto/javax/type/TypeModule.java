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
package org.rookit.auto.javax.type;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.type.filter.FilterModule;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.type.parameter.ParameterModule;
import org.rookit.auto.javax.type.parameter.TypeParameterExtractor;
import org.rookit.utils.guice.Base;

public final class TypeModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            FilterModule.getModule(),
            ParameterModule.getModule(),
            new TypeModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private TypeModule() {}

    @Override
    protected void configure() {
        bind(TypeParameterExtractor.class).to(TypeVisitorParameterExtractor.class).in(Singleton.class);
        bind(ExtendedTypeMirrorFactory.class).to(BaseExtendedTypeMirrorFactory.class).in(Singleton.class);

        bind(ExtendedTypeElementFactory.class).annotatedWith(Base.class)
                .to(ExtendedTypeElementFactoryImpl.class).in(Singleton.class);
        bind(ExtendedTypeElementFactory.class).to(Key.get(ExtendedTypeElementFactory.class, Base.class));
    }

}
