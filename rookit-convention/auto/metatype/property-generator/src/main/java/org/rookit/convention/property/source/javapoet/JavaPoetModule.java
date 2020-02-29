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
package org.rookit.convention.property.source.javapoet;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import one.util.streamex.StreamEx;
import org.rookit.auto.javapoet.method.MethodModule;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.config.NamingConfig;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.guice.MetaType;
import org.rookit.convention.property.guice.PropertyModel;

@SuppressWarnings("MethodMayBeStatic")
public final class JavaPoetModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new JavaPoetModule(),
            MethodModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private JavaPoetModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(new TypeLiteral<ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void>>() {})
                .annotatedWith(PropertyModel.class)
                .to(MetaTypePropertyJavaPoetParameterVisitor.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    @PropertyModel(includeAnnotations = true)
    ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterFactoryWithAnnotations(
            final TypeReferenceSourceFactory referenceFactory,
            final ParameterSourceFactory parameterFactory,
            @MetaType(includeAnnotations = true)
            final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> baseVisitor,
            final TypeParameterSourceFactory typeParameterFactory,
            @MetaType final TypeVariableSource variableSource,
            final NamingConfig namingConfig) {

        return new MetaTypePropertyJavaPoetParameterVisitor(
                referenceFactory,
                parameterFactory,
                baseVisitor,
                typeParameterFactory,
                variableSource,
                namingConfig
        );
    }

}
