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
package org.rookit.auto.javax.runtime.pack;

import io.reactivex.Observable;
import org.rookit.auto.javax.runtime.annotation.RuntimeAnnotationMirrorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

final class PackageTypeMirror implements TypeMirror {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PackageTypeMirror.class);

    private final Package packageReference;
    private final RuntimeAnnotationMirrorFactory annotationFactory;

    PackageTypeMirror(
            final Package packageReference,
            final RuntimeAnnotationMirrorFactory annotationFactory) {
        this.packageReference = packageReference;
        this.annotationFactory = annotationFactory;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.PACKAGE;
    }

    @Override
    public <R, P> R accept(final TypeVisitor<R, P> v, final P p) {
        logger.debug("Visiting regular type mirror");
        return v.visit(this, p);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        logger.trace("Delegating to reflect package: {}", this.packageReference);
        return Observable.fromArray(this.packageReference.getAnnotations())
                .flatMapSingle(this.annotationFactory::fromAnnotation)
                .toList()
                .blockingGet();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        logger.trace("Delegating to reflect package: {}", this.packageReference);
        return this.packageReference.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        logger.trace("Delegating to reflect package: {}", this.packageReference);
        return this.packageReference.getAnnotationsByType(annotationType);
    }

    @Override
    public String toString() {
        return "PackageTypeMirror{" +
                "packageReference=" + this.packageReference +
                ", annotationFactory=" + this.annotationFactory +
                "}";
    }

}
