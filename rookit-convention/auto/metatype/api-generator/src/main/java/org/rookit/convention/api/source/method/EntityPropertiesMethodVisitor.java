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
package org.rookit.convention.api.source.method;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.guice.MetaTypeAPI;
import org.rookit.convention.auto.source.type.reference.PropertyTypeReferenceSourceFactory;

final class EntityPropertiesMethodVisitor implements ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>,
        StreamExtendedElementVisitor<MethodSource, Void> {

    private final MethodSourceFactory methodFactory;
    private final PropertyTypeReferenceSourceFactory propertyReferenceFactory;

    @Inject
    private EntityPropertiesMethodVisitor(
            final MethodSourceFactory methodFactory,
            @MetaTypeAPI final PropertyTypeReferenceSourceFactory namingFactory) {
        this.methodFactory = methodFactory;
        this.propertyReferenceFactory = namingFactory;
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(final ConventionTypeElement element, final Void parameter) {
        return StreamEx.of(element.properties())
                .map(property -> this.methodFactory.createMutableMethod(property.name())
                        .makePublic()
                        .makeAbstract()
                        .withReturnType(this.propertyReferenceFactory.create(element, property))
                );
    }

    @Override
    public String toString() {
        return "EntityPropertiesMethodVisitor{" +
                "methodFactory=" + this.methodFactory +
                ", propertyReferenceFactory=" + this.propertyReferenceFactory +
                "}";
    }

}
