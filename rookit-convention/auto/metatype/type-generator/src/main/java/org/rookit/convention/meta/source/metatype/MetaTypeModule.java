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
package org.rookit.convention.meta.source.metatype;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.IdentifierFactories;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.naming.NamingFactories;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.config.ConventionMetatypeConfig;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.auto.property.PropertyTypeResolver;
import org.rookit.convention.auto.source.PropertyTypeReferenceSourceFactories;
import org.rookit.convention.auto.source.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.guice.MetaType;
import org.rookit.convention.meta.guice.PartialMetatype;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

@SuppressWarnings("MethodMayBeStatic")
public final class MetaTypeModule extends AbstractModule {

    private static final Module MODULE = new MetaTypeModule();

    public static Module getModule() {
        return MODULE;
    }

    private MetaTypeModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(SingleTypeSourceFactory.class).annotatedWith(PartialMetatype.class)
                .to(MetaTypePartialTypeSourceFactory.class).in(Singleton.class);
        bind(SingleTypeSourceFactory.class).annotatedWith(MetaType.class)
                .to(MetaTypeSingleTypeSourceFactory.class).in(Singleton.class);
        bind(new TypeLiteral<ConventionTypeElementVisitor<StreamEx<FieldSource>, Void>>() {})
                .annotatedWith(MetaType.class).to(MetaTypeFieldVisitor.class).in(Singleton.class);
        bind(new TypeLiteral<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>>() {})
                .annotatedWith(MetaType.class).to(MetaTypeMethodVisitor.class).in(Singleton.class);
        bind(PropertyTypeResolver.class).annotatedWith(MetaType.class)
                .to(PropertyTypeResolver.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @MetaType
    TypeVariableSource metaTypeVariableSource(final TypeVariableSourceFactory variableFactory) {
        return variableFactory.createFromName("T");
    }

    @Provides
    @Singleton
    @MetaType
    NamingFactory namingFactory(final NamingFactories factories,
                                final ConventionMetatypeConfig config,
                                @Self final Template1 noopTemplate) {
        return factories.create(config.basePackage(), config.entityTemplate(), noopTemplate);
    }

    @Provides
    @Singleton
    @MetaType
    IdentifierFactory identifierFactory(final IdentifierFactories factories,
                                        @MetaType final NamingFactory namingFactory) {
        return factories.create(namingFactory);
    }

    @Provides
    @Singleton
    @MetaType
    PropertyTypeReferenceSourceFactory propertyNamingFactory(
            final PropertyTypeReferenceSourceFactories factories,
            @MetaTypeAPI final TypeReferenceSourceFactory referenceFactory,
            @MetaType final TypeVariableSource variableSource,
            @MetaType final PropertyTypeResolver resolver) {
        return factories.createDispatcherFactory(factories.parameterWithoutVariableEntity(variableSource),
                                                 resolver,
                                                 referenceFactory);
    }

}
