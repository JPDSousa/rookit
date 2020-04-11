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
package org.rookit.convention.auto.property;

import org.immutables.value.Value;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.utils.optional.Optional;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.util.Objects;

import static java.util.Objects.isNull;

@Value.Immutable
@Value.Style(
        typeImmutable = "Base*"
)
abstract class AbstractProperty implements Property {

    static Builder builder() {
        return BaseProperty.builder();
    }

    interface Builder {

        Builder elementFactory(ConventionTypeElementFactory elementFactory);

        default Builder fromExecutable(final ExtendedExecutableElement executable) {

            final ExtendedTypeMirror typeMirror = executable.getReturnType();
            final Name name = executable.getSimpleName();

            final org.rookit.convention.annotation.Property annotation
                    = executable.getAnnotation(org.rookit.convention.annotation.Property.class);

            final boolean isFinal = isNull(annotation) || !annotation.mutable();

            return this.isFinal(isFinal)
                    .name(name)
                    .type(typeMirror);
        }

        Builder isFinal(boolean isFinal);

        Builder name(CharSequence name);

        Builder type(ExtendedTypeMirror typeMirror);

        Property build();

    }

    protected abstract ConventionTypeElementFactory elementFactory();

    @Value.Derived
    @Override
    public <R, P> R accept(final PropertyVisitor<R, P> visitor, final P param) {

        return visitor.visitUnknown(this, param);
    }

    @Value.Derived
    @Override
    public Optional<ConventionTypeElement> typeAsElement() {
        return type().toElement()
                .map(element -> (TypeElement) element)
                .map(elementFactory()::extend);
    }

    @Override
    public boolean equals(final Object o) {
        // TODO this might need to include additional fields
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        final Property otherProperty = (Property) o;
        return Objects.equals(name(), otherProperty.name());
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(name());
    }

}
