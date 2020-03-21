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
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.source.method.PropertiesImplMethodSource;
import org.rookit.convention.auto.metatype.source.method.PropertiesMethodSourceFactory;
import org.rookit.convention.auto.property.ExtendedPropertyExtractor;
import org.rookit.auto.javax.type.ExtendedTypeElement;

final class EntityPropertiesEntityMethodFactory implements ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>,
        StreamExtendedElementVisitor<MethodSource, Void> {

    private final PropertiesMethodSourceFactory methodFactory;
    private final TypeReferenceSourceFactory referenceFactory;

    private final ExtendedPropertyExtractor extractor;
    private final ConventionTypeElementFactory elementFactory;

    @Inject
    private EntityPropertiesEntityMethodFactory(
            final PropertiesMethodSourceFactory methodFactory,
            final TypeReferenceSourceFactory referenceFactory,
            final ExtendedPropertyExtractor extractor,
            final ConventionTypeElementFactory elementFactory) {

        this.methodFactory = methodFactory;
        this.referenceFactory = referenceFactory;
        this.extractor = extractor;
        this.elementFactory = elementFactory;
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(final ConventionTypeElement conventionElement,
                                                      final Void parameter) {

        final PropertiesImplMethodSource properties = this.methodFactory.createPropertiesMethod("properties");

        // add supertypes
        if (!conventionElement.isTopLevel()) {
            conventionElement.conventionInterfaces()
                    .map(this.referenceFactory::create)
                    .forEach(properties::addFromSuperInterface);
        }

        // add each property
        this.extractor.fromType(conventionElement)
                .forEach(properties::addFromProperty);

        return StreamEx.of(properties);
    }


    @Override
    public StreamEx<MethodSource> visitType(final ExtendedTypeElement extendedType, final Void parameter) {
        return this.elementFactory.extend(extendedType).accept(this, parameter);
    }

    @Override
    public String toString() {
        return "EntityPropertiesEntityMethodFactory{" +
                "methodFactory=" + this.methodFactory +
                ", referenceFactory=" + this.referenceFactory +
                ", extractor=" + this.extractor +
                ", elementFactory=" + this.elementFactory +
                "}";
    }

}
