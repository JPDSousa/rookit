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
package org.rookit.guice.auto.bind;

import com.google.common.collect.Lists;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.util.Collection;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

final class SourceProviderMethodBindingImpl implements SourceProviderMethodBinding {

    private final MutableMethodSource method;
    private final Collection<ParameterSource> dependencies;
    private TypeReferenceSource implReference;

    SourceProviderMethodBindingImpl(
            final MutableMethodSource method,
            final Iterable<ParameterSource> dependencies) {
        this.method = method;
        this.dependencies = Lists.newArrayList(dependencies);
    }

    @Override
    public SourceProviderMethodBinding throughConstructor(final TypeReferenceSource targetReference) {

        this.implReference = targetReference;
        return this;
    }

    @Override
    public SourceProviderMethodBinding addBindingAnnotation(final AnnotationSource bindingAnnotation) {

        this.method.addAnnotation(bindingAnnotation);
        return this;
    }

    @Override
    public SourceProviderMethodBinding addDependencies(final Collection<ParameterSource> dependencies) {

        this.dependencies.addAll(dependencies);

        this.dependencies.forEach(this.method::addParameter);

        return this;
    }

    @Override
    public ProviderMethodBinding build() {

        if (isNull(this.implReference)) {
            throw new IllegalStateException("No implementation reference available");
        }

        return new ProviderMethodBindingImpl(
                this.dependencies.stream()
                        .map(ParameterSource::name)
                        .collect(collectingAndThen(
                                toList(),
                                parameters -> this.method.returnNewObject(this.implReference, parameters)
                        )));
    }

}
