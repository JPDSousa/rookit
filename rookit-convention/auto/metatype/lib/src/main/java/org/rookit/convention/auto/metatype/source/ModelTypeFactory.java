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
package org.rookit.convention.auto.metatype.source;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.convention.auto.javax.ConventionTypeElement;

final class ModelTypeFactory implements MetaTypeModelTypeFactory {

    private static final String ACCESSOR_NAME = "modelType";

    private final MethodSourceFactory methodFactory;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final TypeReferenceSource clazz;

    @Inject
    private ModelTypeFactory(
            final MethodSourceFactory methodFactory,
            final TypeParameterSourceFactory typeParameterFactory,
            @From(Class.class) final TypeReferenceSource clazz) {
        this.methodFactory = methodFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.clazz = clazz;
    }

    @Override
    public MethodSource methodFor(final ConventionTypeElement type) {

        final TypeParameterSource reference = this.typeParameterFactory.create(this.clazz, type);

        return this.methodFactory.createMutableMethod(ACCESSOR_NAME)
                .makePublic()
                .override()
                .withReturnType(reference)
                .addStatement("return $T.class", ImmutableList.of(type));
    }

}
