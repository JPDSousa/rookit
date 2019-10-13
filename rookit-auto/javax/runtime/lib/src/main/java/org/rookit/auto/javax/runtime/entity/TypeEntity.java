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

import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.ElementKind;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class TypeEntity implements RuntimeTypeEntity {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeEntity.class);

    private final Class<?> clazz;
    private final ElementKind kind;
    private final RuntimeEntityFactory entityFactory;
    private final OptionalFactory optionalFactory;

    TypeEntity(
            final Class<?> clazz,
            final ElementKind kind,
            final RuntimeEntityFactory entityFactory,
            final OptionalFactory optionalFactory) {
        this.clazz = clazz;
        this.kind = kind;
        this.entityFactory = entityFactory;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public int modifiers() {
        logger.trace("Delegating to class '{}'", this.clazz);
        return this.clazz.getModifiers();
    }

    @Override
    public String name() {
        logger.trace("Delegating to class '{}'", this.clazz);
        return this.clazz.getName();
    }

    @Override
    public ElementKind kind() {
        return this.kind;
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to class '{}'", this.clazz);
        return this.clazz.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        logger.trace("Delegating to class '{}'", this.clazz);
        return this.clazz.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        logger.trace("Delegating to class '{}'", this.clazz);
        return this.clazz.getDeclaredAnnotations();
    }

    @Override
    public Class<?> clazz() {
        return this.clazz;
    }

    @Override
    public List<RuntimeMethodEntity> declaredMethods() {
        logger.trace("Computing declared methods");
        return Arrays.stream(this.clazz.getDeclaredMethods())
                .map(this.entityFactory::fromMethod)
                .collect(toImmutableList());
    }

    @Override
    public List<RuntimeConstructorEntity> declaredConstructors() {
        logger.trace("Computing declared constructors");
        return Arrays.stream(this.clazz.getDeclaredConstructors())
                .map(this.entityFactory::fromConstructor)
                .collect(toImmutableList());
    }

    @Override
    public List<RuntimeFieldEntity> declaredFields() {
        logger.trace("Computing declared fields");
        return Arrays.stream(this.clazz.getDeclaredFields())
                .map(this.entityFactory::fromField)
                .collect(toImmutableList());
    }

    @Override
    public List<RuntimeTypeEntity> declaredClasses() {
        logger.trace("Computing declared classes");
        return Arrays.stream(this.clazz.getDeclaredClasses())
                .map(this.entityFactory::fromClass)
                .collect(toImmutableList());
    }

    @Override
    public Optional<RuntimeConstructorEntity> enclosingConstructor() {
        return this.optionalFactory.ofNullable(this.clazz.getEnclosingConstructor())
                .map(this.entityFactory::fromConstructor);
    }

    @Override
    public Optional<RuntimeMethodEntity> enclosingMethod() {
        return this.optionalFactory.ofNullable(this.clazz.getEnclosingMethod())
                .map(this.entityFactory::fromMethod);
    }

    @Override
    public Optional<RuntimeTypeEntity> enclosingClass() {
        return this.optionalFactory.ofNullable(this.clazz.getEnclosingClass())
                .map(this.entityFactory::fromClass);
    }

    @Override
    public RuntimePackageEntity packageEntity() {
        return this.entityFactory.fromPackage(this.clazz.getPackage());
    }

    @Override
    public String toString() {
        return "TypeEntity{" +
                "clazz=" + this.clazz +
                ", kind=" + this.kind +
                ", entityFactory=" + this.entityFactory +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }

}
