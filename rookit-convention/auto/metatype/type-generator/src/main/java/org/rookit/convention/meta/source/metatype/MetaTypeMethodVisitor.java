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

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.source.type.reference.PropertyTypeReferenceSourceFactory;
import org.rookit.convention.guice.MetaType;

final class MetaTypeMethodVisitor implements ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>,
        StreamExtendedElementVisitor<MethodSource, Void> {

    private final MethodSourceFactory methodFactory;
    private final PropertyTypeReferenceSourceFactory propertyReferenceFactory;

    @Inject
    private MetaTypeMethodVisitor(
            final MethodSourceFactory methodFactory,
            @MetaType final PropertyTypeReferenceSourceFactory referenceFactory) {
        this.methodFactory = methodFactory;
        this.propertyReferenceFactory = referenceFactory;
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(final ConventionTypeElement element, final Void parameter) {
        return StreamEx.of(element.properties())
                .map(property -> createMethod(element, property));
    }

    private MutableMethodSource createMethod(final ConventionTypeElement element, final Property property) {

        final TypeReferenceSource returnType = this.propertyReferenceFactory.create(element, property);

        return this.methodFactory.createMutableMethod(property.name())
                .makePublic()
                .addAnnotationByClass(Override.class)
                .returnInstanceField(returnType, property.name());
    }

    @Override
    public String toString() {
        return "MetaTypeMethodVisitor{" +
                "methodFactory=" + this.methodFactory +
                ", propertyReferenceFactory=" + this.propertyReferenceFactory +
                "}";
    }

}
