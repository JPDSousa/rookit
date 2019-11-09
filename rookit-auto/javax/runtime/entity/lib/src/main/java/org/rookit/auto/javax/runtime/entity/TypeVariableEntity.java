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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class TypeVariableEntity implements RuntimeTypeVariableEntity {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeVariableEntity.class);

    private final TypeVariable<?> typeVariable;
    private final RuntimeEntityFactory entityFactory;

    TypeVariableEntity(
            final TypeVariable<?> typeVariable,
            final RuntimeEntityFactory entityFactory) {
        this.typeVariable = typeVariable;
        this.entityFactory = entityFactory;
    }

    @Override
    public String name() {
        logger.trace("Delegating to type variable");
        return this.typeVariable.getName();
    }

    @Override
    public TypeVariable<?> typeVariable() {
        return this.typeVariable;
    }

    @Override
    public List<? extends RuntimeTypeEntity> bounds() {
        logger.trace("Computing bounds");
        return Arrays.stream(this.typeVariable.getBounds())
                .map(this.entityFactory::fromType)
                .collect(toImmutableList());
    }

    @Override
    public RuntimeEntity genericDeclaration() {
        logger.trace("Computing generic declaration.");
        return this.entityFactory.fromGenericDeclaration(this.typeVariable.getGenericDeclaration());
    }

    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.getAnnotations();
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        logger.trace("Delegating to type variable '{}'", this.typeVariable.getName());
        return this.typeVariable.getDeclaredAnnotations();
    }

    @Override
    public String toString() {
        return "TypeVariableEntity{" +
                "typeVariable=" + this.typeVariable +
                ", entityFactory=" + this.entityFactory +
                "}";
    }

}
