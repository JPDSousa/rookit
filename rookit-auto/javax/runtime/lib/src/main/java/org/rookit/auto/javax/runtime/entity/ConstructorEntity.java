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
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

final class ConstructorEntity implements RuntimeConstructorEntity {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ConstructorEntity.class);

    private final Constructor<?> constructor;
    private final RuntimeEntityFactory entityFactory;
    private final RuntimeTypeEntity declaringClass;

    ConstructorEntity(
            final Constructor<?> constructor,
            final RuntimeEntityFactory entityFactory,
            final RuntimeTypeEntity declaringClass) {
        this.constructor = constructor;
        this.entityFactory = entityFactory;
        this.declaringClass = declaringClass;
    }

    @Override
    public int modifiers() {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getModifiers();
    }

    @Override
    public String name() {
        logger.trace("Delegating to constructor");
        return this.constructor.getName();
    }

    @Override
    public Constructor<?> constructor() {
        return this.constructor;
    }

    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getAnnotations();
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        logger.trace("Delegating to constructor '{}'", this.constructor.getName());
        return this.constructor.getDeclaredAnnotations();
    }

    @Override
    public List<RuntimeParameterEntity> parameters() {
        logger.trace("Computing parameters");
        return Arrays.stream(this.constructor.getParameters())
                .map(this.entityFactory::fromParameter)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public RuntimeTypeEntity declaringClass() {
        return this.declaringClass;
    }

    @Override
    public String toString() {
        return "ConstructorEntity{" +
                "constructor=" + this.constructor +
                ", entityFactory=" + this.entityFactory +
                ", declaringClass=" + this.declaringClass +
                "}";
    }

}
