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
package org.rookit.convention.module.source.type;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.config.MetatypeModuleConfig;
import org.rookit.utils.guice.Proxied;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.Collection;

import static java.lang.String.format;

final class TypeSourceFactoryImpl implements TypeSourceFactory {

    // TODO copied fromExecutableElement ModuleTypeSourceFactoryImpl
    private static final String WARN_DELEGATE = "No specification for module %s on generator '%s'. " +
            "Falling back to default implementation";

    private final TypeSourceFactory delegate;
    private final Collection<MethodSource> methods;
    private final Messager messager;
    private final MetatypeModuleConfig config;
    private final TypeReferenceSource abstractModule;

    @Inject
    private TypeSourceFactoryImpl(
            @Proxied final TypeSourceFactory delegate,
            final MetatypeModuleConfig config,
            final Messager messager,
            final TypeReferenceSourceFactory referenceFactory,
            final MethodSourceFactory methodFactory) {
        this.delegate = delegate;

        this.abstractModule = referenceFactory.fromClass(AbstractModule.class);
        this.methods = ImmutableSet.of(
                methodFactory.createMutableConstructor()
                        .makePrivate(),
                methodFactory.createMutableMethod(config.singletonMethodName())
                        .makePublic()
                        .makeStatic()
                        .returnStaticField(referenceFactory.fromClass(Module.class), "MODULE")
        );
        this.messager = messager;
        this.config = config;
    }

    @Override
    public MutableTypeSource createMutableClass(final Identifier identifier) {
        return this.delegate.createMutableClass(identifier)
                .withSuperclass(this.abstractModule)
                .addMethods(this.methods);
    }

    @Override
    public MutableTypeSource createMutableInterface(final Identifier identifier) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, format(WARN_DELEGATE, "interfaces", this.config.name()));
        return this.delegate.createMutableInterface(identifier);
    }

    @Override
    public MutableTypeSource createMutableAnnotation(final Identifier identifier) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, format(WARN_DELEGATE, "annotations", this.config.name()));
        return this.delegate.createMutableAnnotation(identifier);
    }

    @Override
    public MutableTypeSource makeMutable(final TypeSource typeSource) {
        return this.delegate.makeMutable(typeSource);
    }

}
