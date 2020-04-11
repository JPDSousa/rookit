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

import com.google.inject.Inject;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.convention.auto.javax.ConventionTypeElementFactory;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.repetition.Repetition;

import static java.util.Objects.isNull;

final class BasePropertyFactory implements PropertyFactory {

    private final ConventionTypeElementFactory elementFactory;
    private final OptionalFactory optionalFactory;

    @Inject
    private BasePropertyFactory(
            final ConventionTypeElementFactory elementFactory,
            final OptionalFactory optionalFactory) {
        this.elementFactory = elementFactory;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Property create(final ExtendedExecutableElement executable) {

        final org.rookit.convention.annotation.Property annotation
                = executable.getAnnotation(org.rookit.convention.annotation.Property.class);

        final Property baseProperty = AbstractProperty.builder()
                .elementFactory(this.elementFactory)
                .fromExecutable(executable)
                .build();

        if (isNull(annotation)) {

            return baseProperty;
        }

        return isCollection(baseProperty)
                .orElseMaybe(() -> isMap(baseProperty))
                .orElseMaybe(() -> isOptional(baseProperty))
                .orElseMaybe(() -> isImmutable(baseProperty))
                .orElseGet(() -> mutableProperty(baseProperty));
    }

    private Property mutableProperty(final Property baseProperty) {

        return ImmutableMutableProperty.builder()
                .from(baseProperty)
                .isFinal(false)
                .build();
    }

    private Optional<Property> isImmutable(final Property baseProperty) {

        if (baseProperty.isFinal()) {
            return this.optionalFactory.empty();
        }

        return this.optionalFactory.of(
                ImmutableImmutableProperty.builder()
                        .from(baseProperty)
                        .isFinal(true)
                        .build()
        );
    }

    private Optional<Property> isMap(final Property baseProperty) {

        final Repetition repetition = baseProperty.type().repetition();

        if (repetition.isKeyed() && repetition.isMulti()) {

            if (baseProperty.isFinal()) {

                return this.optionalFactory.of(
                        ImmutableImmutableMapProperty.builder()
                                .from(baseProperty)
                                .isFinal(true)
                                .build()
                );
            }

            return this.optionalFactory.of(
                    ImmutableMutableMapProperty.builder()
                            .from(baseProperty)
                            .isFinal(false)
                            .build()
            );
        }
        return this.optionalFactory.empty();
    }

    private Optional<Property> isOptional(final Property baseProperty) {

        final Repetition repetition = baseProperty.type().repetition();

        if (repetition.isOptional() && !repetition.isMulti() && !repetition.isKeyed()) {

            if (baseProperty.isFinal()) {

                return this.optionalFactory.of(
                        ImmutableImmutableOptionalProperty.builder()
                                .from(baseProperty)
                                .isFinal(true)
                                .build()
                );
            }

            return this.optionalFactory.of(
                    ImmutableMutableOptionalProperty.builder()
                            .from(baseProperty)
                            .isFinal(false)
                            .build()
            );
        }
        return this.optionalFactory.empty();
    }

    private Optional<Property> isCollection(final Property baseProperty) {

        final Repetition repetition = baseProperty.type().repetition();
        if (repetition.isMulti() && !repetition.isKeyed()) {

            if (baseProperty.isFinal()) {

                return this.optionalFactory.of(
                        ImmutableImmutableCollectionProperty.builder()
                                .from(baseProperty)
                                .isFinal(true)
                                .build()
                );
            }

            return this.optionalFactory.of(
                    ImmutableMutableCollectionProperty.builder()
                            .from(baseProperty)
                            .isFinal(false)
                            .build()
            );
        }
        return this.optionalFactory.empty();
    }

}
