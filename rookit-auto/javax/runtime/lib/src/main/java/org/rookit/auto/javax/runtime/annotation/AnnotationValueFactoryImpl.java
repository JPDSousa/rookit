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
package org.rookit.auto.javax.runtime.annotation;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.variable.RuntimeVariableElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityFactory;
import org.rookit.auto.javax.runtime.type.declared.DeclaredTypeFactory;

import javax.lang.model.element.AnnotationValue;

final class AnnotationValueFactoryImpl implements AnnotationValueFactory {

    private final RuntimeEntityFactory entityFactory;
    private final DeclaredTypeFactory declaredFactory;
    private final RuntimeVariableElementFactory variableFactory;
    private final RuntimeAnnotationMirrorFactory annotationFactory;

    @Inject
    private AnnotationValueFactoryImpl(
            final RuntimeEntityFactory entityFactory,
            final DeclaredTypeFactory declaredFactory,
            final RuntimeVariableElementFactory variableFactory,
            final RuntimeAnnotationMirrorFactory annotationFactory) {
        this.entityFactory = entityFactory;
        this.declaredFactory = declaredFactory;
        this.variableFactory = variableFactory;
        this.annotationFactory = annotationFactory;
    }

    @Override
    public Single<AnnotationValue> createFromObject(final Object object) {
        return Single.just(
                new RuntimeAnnotationValue(this.entityFactory,
                                           this.declaredFactory,
                                           this.variableFactory, this.annotationFactory, object)
        );
    }

    @Override
    public String toString() {
        return "AnnotationValueFactoryImpl{" +
                "entityFactory=" + this.entityFactory +
                ", declaredFactory=" + this.declaredFactory +
                ", variableFactory=" + this.variableFactory +
                ", annotationFactory=" + this.annotationFactory +
                "}";
    }

}