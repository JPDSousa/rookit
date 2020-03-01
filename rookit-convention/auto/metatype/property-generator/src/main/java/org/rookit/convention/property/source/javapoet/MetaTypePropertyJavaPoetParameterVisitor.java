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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.metatype.config.NamingConfig;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.guice.MetaType;
import org.rookit.serialization.Serializer;

import java.util.Collection;
import java.util.function.Function;

final class MetaTypePropertyJavaPoetParameterVisitor
        implements ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void>,
        StreamExtendedElementVisitor<ParameterSource, Void> {

    private final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> metaTypeFactory;
    private final TypeReferenceSourceFactory referenceFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final ParameterSourceFactory parameterFactory;
    private final TypeVariableSource variableSource;
    private final NamingConfig namingConfig;
    private final ParameterSource propertyName;
    private final TypeReferenceSource serializerName;
    private final TypeReferenceSource typeName;

    @Inject
    MetaTypePropertyJavaPoetParameterVisitor(
            final TypeReferenceSourceFactory referenceFactory,
            final ParameterSourceFactory parameterFactory,
            @MetaType final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> metaTypeFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            @MetaType final TypeVariableSource variableSource,
            final NamingConfig namingConfig) {
        this.referenceFactory = referenceFactory;
        this.metaTypeFactory = metaTypeFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.parameterFactory = parameterFactory;
        this.variableSource = variableSource;
        this.namingConfig = namingConfig;
        this.serializerName = referenceFactory.fromClass(Serializer.class);
        this.typeName = referenceFactory.fromClass(Class.class);
        this.propertyName = parameterFactory.createMutable(namingConfig.propertyName(), String.class);
    }

    private Collection<ParameterSource> entityParameters(final ConventionTypeElement element) {
        final TypeReferenceSource param = element.isPartialEntity()
                ? this.variableSource
                : this.referenceFactory.create(element);
        final TypeParameterSource type = this.typeParameterFactory.create(this.typeName, param);
        final TypeParameterSource serializer = this.typeParameterFactory.create(this.serializerName, param);
        final TypeParameterSource function = this.typeParameterFactory.create(Function.class,
                this.variableSource,
                this.referenceFactory.create(element)
        );
        final ParameterSource typeParam = createFromNaming(type, NamingConfig::type);
        final ParameterSource serializerParam = createFromNaming(serializer, NamingConfig::serializer);
        final ParameterSource functionParam = createFromNaming(function, NamingConfig::getter);

        return ImmutableList.of(
                typeParam,
                serializerParam,
                this.propertyName,
                functionParam
        );
    }

    private ParameterSource createFromNaming(final TypeReferenceSource type,
                                                      final Function<NamingConfig, String> name) {
        return this.parameterFactory.createMutable(name.apply(this.namingConfig), type);
    }

    @Override
    public StreamEx<ParameterSource> visitConventionType(final ConventionTypeElement element,
                                                                  final Void parameter) {
        return this.metaTypeFactory.visitConventionType(element, parameter)
                .map(parameterSource -> this.parameterFactory.makeMutable(parameterSource)
                        .toBeUsedInSuperclass(true))
                .select(ParameterSource.class)
                .append(entityParameters(element));
    }
}
