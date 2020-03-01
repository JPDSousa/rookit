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
package org.rookit.convention.auto.metatype.javax;

import com.google.inject.Inject;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.ExtendedTypeElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.convention.auto.metatype.property.ExtendedPropertyExtractor;
import org.rookit.convention.auto.metatype.property.Property;
import org.rookit.utils.optional.OptionalFactory;

import javax.lang.model.element.TypeElement;
import java.util.Collection;

final class ConventionTypeElementFactoryImpl implements ConventionTypeElementFactory {

    private final ExtendedTypeElementFactory delegate;
    private final OptionalFactory optionalFactory;
    private final ConventionElementUtils utils;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final ExtendedPropertyExtractor extractor;

    @Inject
    private ConventionTypeElementFactoryImpl(
            final ExtendedTypeElementFactory delegate,
            final OptionalFactory optionalFactory,
            final ConventionElementUtils utils,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final ExtendedPropertyExtractor extractor) {
        this.delegate = delegate;
        this.optionalFactory = optionalFactory;
        this.utils = utils;
        this.mirrorFactory = mirrorFactory;
        this.extractor = extractor;
    }

    @Override
    public ConventionTypeElement extend(final TypeElement baseElement) {
        final ExtendedTypeElement extended = this.delegate.extend(baseElement);
        return fromExtendedTypeElement(extended);
    }

    private ConventionTypeElement fromExtendedTypeElement(final ExtendedTypeElement extended) {
        final Collection<Property> properties = this.extractor.fromTypeAsCollection(extended);

        return new ConventionTypeElementImpl(
                extended,
                this.optionalFactory,
                this.utils,
                properties,
                this.mirrorFactory,
                this);
    }

    @Override
    public ConventionTypeElement changeElementProperties(final ConventionTypeElement original,
                                                         final Collection<Property> newProperties) {
        return new PropertyOverridingExtendedTypeElement(original, newProperties);
    }

    @Override
    public String toString() {
        return "ConventionTypeElementFactoryImpl{" +
                "delegate=" + this.delegate +
                ", optionalFactory=" + this.optionalFactory +
                ", utils=" + this.utils +
                ", mirrorFactory=" + this.mirrorFactory +
                ", extractor=" + this.extractor +
                "}";
    }

}
