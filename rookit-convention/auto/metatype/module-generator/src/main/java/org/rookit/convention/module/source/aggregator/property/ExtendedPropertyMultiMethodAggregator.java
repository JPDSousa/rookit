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
package org.rookit.convention.module.source.aggregator.property;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.naming.PropertyIdentifierFactory;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.property.ContainerProperty;
import org.rookit.convention.auto.property.ExtendedProperty;
import org.rookit.convention.auto.property.aggregator.ExtendedPropertyAggregator;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyFactory;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.primitive.VoidUtils;

import javax.annotation.processing.Messager;
import java.util.Collection;

import static org.apache.commons.text.WordUtils.uncapitalize;

final class ExtendedPropertyMultiMethodAggregator implements ExtendedPropertyAggregator<Collection<MethodSource>> {

    private final MethodSourceFactory methodFactory;
    private final AnnotationSourceFactory annotationFactory;

    private final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterVisitor;
    private final ExtendedTypeElement element;
    private final Collection<Property> properties;
    private final PropertyFactory propertyFactory;
    private final PropertyIdentifierFactory identifierFactory;
    private final Messager messager;
    private final TypeReferenceSourceFactory apiReferenceFactory;
    private final TypeReferenceSourceFactory implReferenceFactory;
    private final TypeParameterSourceFactory parameterFactory;
    private final VoidUtils voidUtils;

    ExtendedPropertyMultiMethodAggregator(
            final MethodSourceFactory methodFactory,
            final AnnotationSourceFactory annotationFactory,
            final ConventionTypeElementVisitor<StreamEx<ParameterSource>, Void> parameterVisitor,
            final ExtendedTypeElement element,
            final Iterable<Property> properties,
            final PropertyFactory propertyFactory,
            final PropertyIdentifierFactory identifierFactory,
            final Messager messager,
            final TypeReferenceSourceFactory apiReferenceFactory,
            final TypeReferenceSourceFactory implReferenceFactory,
            final TypeParameterSourceFactory parameterFactory,
            final VoidUtils voidUtils) {
        this.methodFactory = methodFactory;
        this.annotationFactory = annotationFactory;
        this.parameterVisitor = parameterVisitor;
        this.element = element;
        this.properties = Lists.newArrayList(properties);
        this.propertyFactory = propertyFactory;
        this.identifierFactory = identifierFactory;
        this.messager = messager;
        this.apiReferenceFactory = apiReferenceFactory;
        this.implReferenceFactory = implReferenceFactory;
        this.parameterFactory = parameterFactory;
        this.voidUtils = voidUtils;
    }

    @Override
    public boolean accept(final ExtendedProperty item) {
        this.properties.add(item);
        return true;
    }

    @Override
    public ExtendedPropertyAggregator<Collection<MethodSource>> reduce(
            final ExtendedPropertyAggregator<Collection<MethodSource>> aggregator) {
        return new ReducedExtendedPropertyMultiMethodAggregator(this.messager, this, aggregator);
    }

    @Override
    public Collection<MethodSource> result() {
        return StreamEx.of(this.properties)
                .map(this.propertyFactory::toContainer)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::createPropertyBinding)
                .toImmutableList();
    }

    private MethodSource createPropertyBinding(final ContainerProperty property) {

        final String methodName = uncapitalize(this.element.getSimpleName().toString()) + property.name();

        final Identifier identifier = this.identifierFactory.create(property);

        final ExtendedTypeElement propertyType = property.typeAsElement();

        final TypeReferenceSource returnType = this.parameterFactory.create(
                this.apiReferenceFactory.create(propertyType),
                this.element
        );

        final TypeReferenceSource implReference = this.implReferenceFactory.create(propertyType);
        return this.methodFactory.createMutableMethod(methodName)
                .addAnnotations(annotations(identifier))
                .addParameters(propertyType.accept(this.parameterVisitor, this.voidUtils.returnVoid()))
                .returnMethodCall(returnType, "$T.create", ImmutableList.of(implReference));
    }

    private Collection<AnnotationSource> annotations(final Identifier identifier) {
        return ImmutableSet.of(
                this.annotationFactory.create(Provides.class),
                this.annotationFactory.create(Singleton.class),
                this.annotationFactory.create(identifier)
        );
    }

}
