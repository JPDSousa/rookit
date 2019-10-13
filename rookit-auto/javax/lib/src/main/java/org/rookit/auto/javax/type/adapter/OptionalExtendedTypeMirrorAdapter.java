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
package org.rookit.auto.javax.type.adapter;

import org.apache.commons.collections4.CollectionUtils;
import org.rookit.auto.javax.type.ExtendedTypeMirror;
import org.rookit.auto.javax.type.ExtendedTypeMirrorFactory;
import org.rookit.utils.adapt.Adapter;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalBoolean;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalShort;

import javax.lang.model.type.TypeKind;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;

// TODO please improve me!!!!!!!!
final class OptionalExtendedTypeMirrorAdapter implements Adapter<ExtendedTypeMirror> {

    private final OptionalFactory optionalFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;

    OptionalExtendedTypeMirrorAdapter(final OptionalFactory optionalFactory,
                                      final ExtendedTypeMirrorFactory mirrorFactory) {
        this.optionalFactory = optionalFactory;
        this.mirrorFactory = mirrorFactory;
    }

    @Override
    public ExtendedTypeMirror adapt(final ExtendedTypeMirror source) {
        return tryAdapt(source, Optional.class, optional -> CollectionUtils.extractSingleton(optional.typeParameters()))
                .orElseMaybe(() -> tryAdapt(source, OptionalBoolean.class,
                        e -> this.mirrorFactory.createPrimitive(TypeKind.BOOLEAN)))
                .orElseMaybe(() -> tryAdapt(source, OptionalShort.class,
                        e -> this.mirrorFactory.createPrimitive(TypeKind.SHORT)))
                .orElseMaybe(() -> tryAdapt(source, OptionalInt.class,
                        e -> this.mirrorFactory.createPrimitive(TypeKind.INT)))
                .orElseMaybe(() -> tryAdapt(source, OptionalDouble.class,
                        e -> this.mirrorFactory.createPrimitive(TypeKind.DOUBLE)))
                .orElseMaybe(() -> tryAdapt(source, OptionalLong.class,
                        e -> this.mirrorFactory.createPrimitive(TypeKind.LONG)))
                .orElseThrow(() -> new IllegalArgumentException("Cannot adapt: " + source));
    }

    private Optional<ExtendedTypeMirror> tryAdapt(
            final ExtendedTypeMirror source,
            final Class<?> expected,
            final Function<ExtendedTypeMirror, ExtendedTypeMirror> adaptFunction) {
        if (source.isSameTypeErasure(this.mirrorFactory.createWithErasure(expected))) {
            return this.optionalFactory.of(adaptFunction.apply(source));
        }
        return this.optionalFactory.empty();
    }

    @Override
    public String toString() {
        return "OptionalExtendedTypeMirrorAdapter{" +
                "optionalFactory=" + this.optionalFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                "}";
    }
}
