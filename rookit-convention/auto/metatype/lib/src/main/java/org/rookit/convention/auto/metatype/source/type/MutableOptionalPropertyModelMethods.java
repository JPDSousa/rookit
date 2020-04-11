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
package org.rookit.convention.auto.metatype.source.type;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.parameter.MutableParameterSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.convention.auto.metatype.source.type.reference.MetaTypeReferenceFactory;
import org.rookit.convention.auto.property.PropertyModifiers;
import org.rookit.convention.auto.property.optional.MutableOptionalPropertyModifiers;
import org.rookit.convention.property.guice.MutableOptionalPropertyModelClear;
import org.rookit.convention.property.guice.MutableOptionalPropertyModelSet;

import javax.lang.model.element.Name;
import java.util.List;

final class MutableOptionalPropertyModelMethods implements MutableOptionalPropertyModifiers<VisitorData> {

    private static final CharSequence CLEAR_FIELD = "clear";
    private static final CharSequence SETTER_FIELD = "setter";

    private final PropertyModifiers<VisitorData> delegate;
    private final ParameterSourceFactory parameterFactory;
    private final MethodSourceFactory methodFactory;
    private final MetaTypeReferenceFactory metaTypeReferences;
    private final TypeReferenceSourceFactory referenceFactory;

    private final ExtendedExecutableElement optionalClear;
    private final ExtendedExecutableElement optionalSetter;

    @Inject
    private MutableOptionalPropertyModelMethods(
            final PropertyModifiers<VisitorData> delegate,
            final ParameterSourceFactory parameterFactory,
            final MethodSourceFactory methodFactory,
            final MetaTypeReferenceFactory metaTypeReferences,
            final TypeReferenceSourceFactory referenceFactory,
            @MutableOptionalPropertyModelClear final ExtendedExecutableElement optionalClear,
            @MutableOptionalPropertyModelSet final ExtendedExecutableElement optionalSetter) {
        this.delegate = delegate;
        this.parameterFactory = parameterFactory;
        this.methodFactory = methodFactory;
        this.metaTypeReferences = metaTypeReferences;
        this.referenceFactory = referenceFactory;
        this.optionalClear = optionalClear;
        this.optionalSetter = optionalSetter;
    }

    @Override
    public VisitorData set(final VisitorData original) {

        final TypeReferenceSource typeType = this.metaTypeReferences.parentParameterTypeFor(original.type());

        final TypeReferenceSource propertyType = this.referenceFactory.create(
                original.property().type()
        );

        final MutableTypeSource targetSource = original.source()
                .addMethod(setMethod(typeType, propertyType));

        return ImmutableVisitorData.builder()
                .from(original)
                .source(targetSource)
                .build();
    }

    private MethodSource setMethod(final TypeReferenceSource typeType,
                                   final TypeReferenceSource propertyType) {

        final List<? extends ExtendedVariableElement> params = this.optionalSetter.getParameters();

        final Name typeParamName = params.get(0)
                .getSimpleName();

        final Name propParamName = params.get(1)
                .getSimpleName();

        final ParameterSource typeParam = this.parameterFactory.createMutable(typeParamName, typeType);
        final MutableParameterSource propParam = this.parameterFactory.createMutable(propParamName, propertyType);

        return this.methodFactory.createMutableMethod(this.optionalSetter.getSimpleName())
                .makePublic()
                .override()
                .addParameter(typeParam)
                .addParameter(propParam)
                .addStatement("this.$L.accept($L, $L)",
                              ImmutableList.of(SETTER_FIELD, typeParamName, propParamName));
    }

    @Override
    public VisitorData clear(final VisitorData original) {

        final TypeReferenceSource typeType = this.metaTypeReferences.parentParameterTypeFor(original.type());

        final MutableTypeSource targetSource = original.source()
                .addMethod(clearMethod(typeType));

        return ImmutableVisitorData.builder()
                .from(original)
                .source(targetSource)
                .build();
    }

    private MutableMethodSource clearMethod(final TypeReferenceSource typeType) {

        final Name typeParamName = this.optionalClear.getParameters()
                .get(0)
                .getSimpleName();

        final MutableParameterSource typeParam = this.parameterFactory.createMutable(typeParamName, typeType);
        final Name methodName = this.optionalClear.getSimpleName();

        return this.methodFactory.createMutableMethod(methodName)
                .makePublic()
                .override()
                .addParameter(typeParam)
                .addStatement("this.$L.accept($L)", ImmutableList.of(CLEAR_FIELD, typeParamName));
    }

    @Override
    public VisitorData get(final VisitorData original) {

        return this.delegate.get(original);
    }

}
