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
package org.rookit.guice.auto.source.type;

import com.google.inject.Inject;
import org.rookit.auto.guice.GuiceBindAnnotation;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.TypeSourceFactory;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.guice.auto.source.reference.BindingAnnotationReferenceFactory;

import java.util.Collection;
import java.util.Set;

final class BindingAnnotationTypeFactoryImpl implements BindingAnnotationTypeFactory {

    private final TypeSourceFactory typeFactory;
    private final Collection<AnnotationSource> bindingAnnotations;
    private final BindingAnnotationReferenceFactory references;

    @Inject
    private BindingAnnotationTypeFactoryImpl(
            final TypeSourceFactory typeFactory,
            @GuiceBindAnnotation final Set<AnnotationSource> bindingAnnotations,
            final BindingAnnotationReferenceFactory references) {

        this.typeFactory = typeFactory;
        this.bindingAnnotations = bindingAnnotations;
        this.references = references;
    }

    private TypeSource fromReference(final TypeReferenceSource reference) {

        return this.typeFactory.createMutableAnnotation(reference)
                .makePublic()
                .addAnnotations(this.bindingAnnotations);
    }

    @Override
    public TypeSource fromType(final ExtendedTypeElement type) {

        return fromReference(this.references.fromType(type));
    }

    @Override
    public TypeSource fromExecutable(final ExtendedExecutableElement executable) {

        return fromReference(this.references.fromExecutable(executable));
    }

}
