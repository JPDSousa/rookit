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
package org.rookit.guice.auto.source.annotation;

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.annotation.AnnotationSourceFactory;
import org.rookit.guice.auto.source.reference.BindingAnnotationReferenceFactory;

final class BindingAnnotationFactoryImpl implements BindingAnnotationFactory {

    private final AnnotationSourceFactory annotationFactory;
    private final BindingAnnotationReferenceFactory references;

    @Inject
    private BindingAnnotationFactoryImpl(
            final AnnotationSourceFactory annotationFactory,
            final BindingAnnotationReferenceFactory references) {
        this.annotationFactory = annotationFactory;
        this.references = references;
    }

    @Override
    public AnnotationSource annotationFromType(final ExtendedTypeElement type) {

        return this.annotationFactory.fromReference(this.references.fromType(type));
    }

    @Override
    public AnnotationSource annotationFromExecutable(final ExtendedExecutableElement executable) {

        return this.annotationFactory.fromReference(this.references.fromExecutable(executable));
    }

    @Override
    public AnnotationSource annotationFromExecutable(final ExtendedElement enclosing,
                                                     final CharSequence executableName) {

        return this.annotationFactory.fromReference(this.references.fromExecutable(enclosing, executableName));
    }

}
