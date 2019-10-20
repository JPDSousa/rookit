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

import java.util.List;

public interface RuntimeClassEntity extends RuntimeTypeEntity {

    @Override
    Class<?> type();

    @Override
    default <R, P> R accept(final RuntimeEntityVisitor<R, P> visitor, final P parameter) {
        return visitor.visitClass(this, parameter);
    }

    List<RuntimeMethodEntity> declaredMethods();

    List<RuntimeConstructorEntity> declaredConstructors();

    List<RuntimeFieldEntity> declaredFields();

    List<RuntimeClassEntity> declaredClasses();

    Optional<RuntimeConstructorEntity> enclosingConstructor();

    Optional<RuntimeMethodEntity> enclosingMethod();

    Optional<RuntimeClassEntity> enclosingClass();

    RuntimePackageEntity packageEntity();

}
