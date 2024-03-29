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
import io.reactivex.Observable;
import io.reactivex.Single;
import org.apache.commons.lang3.tuple.Pair;
import org.rookit.auto.javax.mirror.annotation.node.MutableNodeAnnotation;
import org.rookit.auto.javax.runtime.element.executable.ExecutableElementFactory;
import org.rookit.auto.javax.runtime.element.executable.RuntimeExecutableElement;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeMethodEntity;
import org.rookit.auto.javax.runtime.mirror.annotation.node.AnnotationNodeFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

final class AnnotationMirrorFactoryImpl implements RuntimeAnnotationMirrorFactory {

    private final RuntimeEntityFactory entityFactory;
    private final AnnotationValueFactory valueFactory;
    private final ExecutableElementFactory executableElementFactory;
    private final AnnotationNodeFactory nodeFactory;

    @Inject
    private AnnotationMirrorFactoryImpl(
            final RuntimeEntityFactory entityFactory,
            final AnnotationValueFactory valueFactory,
            final ExecutableElementFactory executableFactory,
            final AnnotationNodeFactory nodeFactory) {
        this.entityFactory = entityFactory;
        this.valueFactory = valueFactory;
        this.executableElementFactory = executableFactory;
        this.nodeFactory = nodeFactory;
    }

    @Override
    public Single<AnnotationMirror> fromAnnotation(final Annotation annotation) {
        final RuntimeClassEntity annotationEntity = this.entityFactory.fromClass(annotation.annotationType());
        final Single<MutableNodeAnnotation> nodeSingle = this.nodeFactory.createMutableFromEntity(
                annotationEntity);

        return Single.zip(createElementValues(annotation), nodeSingle,
                (elementValues, node) -> new RuntimeAnnotationMirror(elementValues, annotation, node));
    }

    @Override
    public Observable<AnnotationMirror> createFromAnnotatedElement(final AnnotatedElement annotatedElement) {
        return Observable.fromArray(annotatedElement.getAnnotations())
                .flatMapSingle(this::fromAnnotation);
    }

    private Single<Map<RuntimeExecutableElement, AnnotationValue>> createElementValues(
            final Annotation annotation) {
        final Method[] methods = annotation.annotationType()
                .getDeclaredMethods();
        return Observable.fromArray(methods)
                .flatMapSingle(method -> createMapEntry(annotation, method))
                .reduce(new HashMap<>(methods.length), (map, entry) -> {
                    map.put(entry.getKey(), entry.getValue());
                    return map;
                });
    }

    private Single<Map.Entry<RuntimeExecutableElement, AnnotationValue>> createMapEntry(final Annotation annotation,
                                                                                        final Method rawMethod) {
        final RuntimeMethodEntity method = this.entityFactory.fromMethod(rawMethod);

        final Single<RuntimeExecutableElement> elementSingle = this.executableElementFactory.createElement(method);
        final Single<AnnotationValue> valueSingle = Single.fromCallable(() -> rawMethod.invoke(annotation))
                .flatMap(this.valueFactory::createFromObject);

        return Single.zip(elementSingle, valueSingle, Pair::of);
    }

    @Override
    public String toString() {
        return "AnnotationMirrorFactoryImpl{" +
                "entityFactory=" + this.entityFactory +
                ", valueFactory=" + this.valueFactory +
                ", executableElementFactory=" + this.executableElementFactory +
                ", nodeFactory=" + this.nodeFactory +
                "}";
    }

}
