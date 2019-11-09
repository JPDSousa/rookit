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

import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.entity.kind.RuntimeElementKindFactory;
import org.rookit.utils.optional.OptionalFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class RuntimeEntityFactoryImpl implements RuntimeEntityFactory {

    private final RuntimeElementKindFactory elementKindFactory;
    private final OptionalFactory optionalFactory;

    @Inject
    private RuntimeEntityFactoryImpl(
            final RuntimeElementKindFactory elementKindFactory,
            final OptionalFactory optionalFactory) {
        this.elementKindFactory = elementKindFactory;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public RuntimeEntity fromGenericDeclaration(final GenericDeclaration declaration) {
        if (declaration instanceof Class<?>) {
            return fromClass((Class<?>) declaration);
        } else if (declaration instanceof Method) {
            return fromMethod((Method) declaration);
        } else if (declaration instanceof Constructor<?>) {
            return fromConstructor((Constructor<?>) declaration);
        }
        throw new IllegalArgumentException("Unsupported declaration: " + declaration);
    }

    @Override
    public RuntimeClassEntity fromClass(final Class<?> clazz) {
        return new ClassEntity(clazz, this.elementKindFactory.createFromClass(clazz), this, this.optionalFactory);
    }

    @Override
    public RuntimeTypeEntity fromType(final Type type) {
        if (type instanceof Class<?>) {
            return fromClass((Class<?>) type);
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }

    @Override
    public RuntimeMethodEntity fromMethod(final Method method) {
        return new MethodEntity(method, this, fromClass(method.getDeclaringClass()));
    }

    @Override
    public RuntimeConstructorEntity fromConstructor(final Constructor<?> constructor) {
        return new ConstructorEntity(constructor, this, fromClass(constructor.getDeclaringClass()));
    }

    @Override
    public RuntimeParameterEntity fromParameter(final Parameter parameter) {
        final Executable executable = parameter.getDeclaringExecutable();
        final RuntimeExecutableEntity declaringExecutable = (executable instanceof Method)
                ? fromMethod((Method) executable)
                : fromConstructor((Constructor<?>) executable);

        return new ParameterEntity(parameter, declaringExecutable);
    }

    @Override
    public RuntimePackageEntity fromPackage(final Package pack) {
        return new PackageEntity(pack);
    }

    @Override
    public RuntimeTypeVariableEntity fromTypeVariable(final TypeVariable<?> typeVariable) {
        return new TypeVariableEntity(typeVariable, this);
    }

    @Override
    public RuntimeEnumEntity fromEnum(final Enum<?> value) {
        return new EnumEntityImpl(value, fromClass(value.getDeclaringClass()));
    }

    @Override
    public RuntimeFieldEntity fromField(final Field field) {
        return new FieldEntityImpl(field, fromClass(field.getDeclaringClass()));
    }

    @Override
    public String toString() {
        return "RuntimeEntityFactoryImpl{" +
                "elementKindFactory=" + this.elementKindFactory +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }

}
