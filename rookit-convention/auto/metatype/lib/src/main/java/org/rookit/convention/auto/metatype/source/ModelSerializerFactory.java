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

import com.google.inject.Inject;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.convention.guice.MetaTypeModelSerializer;

final class ModelSerializerFactory implements MetaTypeModelSerializerFactory {

    private final FieldSourceFactory fieldFactory;
    private final MethodSourceFactory methodFactory;
    private final ExtendedExecutableElement apiMethod;
    private final String fieldName;
    private final TypeParameterSourceFactory typeParameterFactory;
    private final ParameterSourceFactory parameterFactory;

    @Inject
    private ModelSerializerFactory(
            final FieldSourceFactory fieldFactory,
            final MethodSourceFactory methodFactory,
            @MetaTypeModelSerializer final ExtendedExecutableElement apiMethod,
            final TypeParameterSourceFactory typeParameterFactory,
            final ParameterSourceFactory parameterFactory) {

        this.fieldFactory = fieldFactory;
        this.methodFactory = methodFactory;
        this.apiMethod = apiMethod;
        this.fieldName = apiMethod.getSimpleName().toString();
        this.typeParameterFactory = typeParameterFactory;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public FieldSource fieldFor(final ExtendedTypeMirror type) {

        final TypeParameterSource fieldType = referenceFor(type);

        return this.fieldFactory.createMutable(fieldType, this.fieldName)
                .makePrivate()
                .makeFinal();
    }

    private TypeParameterSource referenceFor(final ExtendedTypeMirror type) {

        return this.typeParameterFactory.create(this.apiMethod.getReturnType(), type);
    }

    @Override
    public MethodSource methodFor(final ExtendedTypeMirror type) {

        return this.methodFactory.createMutableOverride(this.apiMethod)
                .returnInstanceField(referenceFor(type), this.fieldName);
    }

    @Override
    public MethodSource delegateMethodFor(
            final ExtendedTypeMirror type,
            final FieldSource delegate) {

        return this.methodFactory.createMutableDelegateOverride(this.apiMethod, delegate)
                .withReturnType(referenceFor(type));
    }

    @Override
    public ParameterSource parameterFor(final ExtendedTypeMirror type) {

        return this.parameterFactory.createMutable(this.fieldName, referenceFor(type));
    }

}
