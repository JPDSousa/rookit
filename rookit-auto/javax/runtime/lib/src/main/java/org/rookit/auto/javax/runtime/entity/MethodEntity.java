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
package org.rookit.auto.javax.runtime.entity;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

final class MethodEntity implements RuntimeMethodEntity {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(MethodEntity.class);

    private final Method method;
    private final RuntimeEntityFactory entityFactory;
    private final RuntimeClassEntity declaringClass;

    MethodEntity(
            final Method method,
            final RuntimeEntityFactory entityFactory,
            final RuntimeClassEntity declaringClass) {
        this.method = method;
        this.entityFactory = entityFactory;
        this.declaringClass = declaringClass;
    }

    @Override
    public int modifiers() {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getModifiers();
    }

    @Override
    public String name() {
        logger.trace("Delegating to method");
        return this.method.getName();
    }

    @Override
    public Method method() {
        return this.method;
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getDeclaredAnnotations();
    }

    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to method '{}'", this.method.getName());
        return this.method.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public List<RuntimeParameterEntity> parameters() {
        logger.trace("Computing parameters");
        return Arrays.stream(this.method.getParameters())
                .map(this.entityFactory::fromParameter)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<RuntimeTypeVariableEntity> typeParameters() {
        logger.trace("Computing type parameters");
        return Arrays.stream(this.method.getTypeParameters())
                .map(this.entityFactory::fromTypeVariable)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public RuntimeClassEntity declaringClass() {
        return this.declaringClass;
    }

    @Override
    public String toString() {
        return "MethodEntity{" +
                "method=" + this.method +
                ", entityFactory=" + this.entityFactory +
                ", declaringClass=" + this.declaringClass +
                "}";
    }

}
