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
package org.rookit.convention.api.source.type;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.IdentifierFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.type.ExtendedElementTypeSourceVisitors;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.annotation.LaConvention;
import org.rookit.convention.api.guice.Container;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.auto.metatype.guice.PartialMetaTypeAPI;
import org.rookit.utils.primitive.VoidUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

@SuppressWarnings("MethodMayBeStatic")
public final class TypeModule extends AbstractModule {

    private static final Module MODULE = new TypeModule();

    public static Module getModule() {
        return MODULE;
    }

    private TypeModule() {}

    @Override
    protected void configure() {

    }

    @Singleton
    @Provides
    @Container
    SingleTypeSourceFactory baseTypeSourceFactory(
            final TypeSourceFactory typeFactory,
            final TypeVariableSourceFactory variableFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final VoidUtils voidUtils,
            final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor,
            @MetaTypeAPI final TypeVariableSource variableSource,
            final ConventionTypeElementFactory elementFactory) {
        return new BaseTypeSourceFactory(
                typeFactory,
                variableFactory,
                referenceFactory,
                typeParameterFactory,
                voidUtils,
                methodVisitor,
                variableSource,
                elementFactory
        );
    }

    @Singleton
    @Provides
    @PartialMetaTypeAPI
    SingleTypeSourceFactory partialMetaTypeProvider(
            final TypeSourceFactory typeFactory,
            final TypeVariableSourceFactory variableFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            final VoidUtils voidUtils,
            final ExtendedElementVisitor<StreamEx<MethodSource>, Void> methodVisitor,
            @MetaTypeAPI final TypeVariableSource typeVariableName,
            final ConventionTypeElementFactory elementFactory) {

        return new BaseTypeSourceFactory(
                typeFactory,
                variableFactory,
                referenceFactory,
                typeParameterFactory,
                voidUtils,
                methodVisitor,
                typeVariableName,
                elementFactory
        );
    }


    @Provides
    @Singleton
    ExtendedElementVisitor<StreamEx<TypeSource>, Void> visitor(
            @LaConvention final Set<Class<? extends Annotation>> annotations,
            final ExtendedElementTypeSourceVisitors visitors,
            final IdentifierFactory idFactory) {

        return visitors.interfaceBuilder(idFactory, Void.class)
                .withRecursiveVisiting(StreamEx::append)
                .filterIfAnyAnnotationPresent(annotations)
                .build();
    }
}
