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
import java.lang.reflect.Parameter;

final class ParameterEntity implements RuntimeParameterEntity {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ParameterEntity.class);

    private final Parameter parameter;
    private final RuntimeExecutableEntity declaringExecutable;

    ParameterEntity(
            final Parameter parameter,
            final RuntimeExecutableEntity declaringExecutable) {
        this.parameter = parameter;
        this.declaringExecutable = declaringExecutable;
    }

    @Override
    public int modifiers() {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getModifiers();
    }

    @Override
    public String name() {
        logger.trace("Delegating to parameter");
        return this.parameter.getName();
    }

    @Override
    public Parameter parameter() {
        return this.parameter;
    }

    @Override
    public RuntimeExecutableEntity declaringExecutable() {
        return this.declaringExecutable;
    }

    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getAnnotations();
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        logger.trace("Delegating to parameter '{}'", this.parameter.getName());
        return this.parameter.getDeclaredAnnotations();
    }

    @Override
    public String toString() {
        return "ParameterEntity{" +
                "parameter=" + this.parameter +
                ", declaringExecutable=" + this.declaringExecutable +
                "}";
    }

}
