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
package org.rookit.auto.javax.runtime.mirror;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeConstructorEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityVisitor;
import org.rookit.auto.javax.runtime.entity.RuntimeEnumEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeMethodEntity;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeParameterEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;
import org.rookit.auto.javax.runtime.mirror.declared.RuntimeDeclaredTypeFactory;
import org.rookit.auto.javax.runtime.mirror.executable.ExecutableTypeFactory;
import org.rookit.auto.javax.mirror.no.NoTypeFactory;
import org.rookit.auto.javax.runtime.mirror.variable.TypeVariableFactory;

import javax.lang.model.type.TypeMirror;

final class TypeMirrorVisitor implements RuntimeEntityVisitor<Single<TypeMirror>, Void> {

    private final RuntimeDeclaredTypeFactory declaredTypeFactory;
    private final ExecutableTypeFactory executableTypeFactory;
    private final NoTypeFactory noTypeFactory;
    private final TypeVariableFactory typeVariableFactory;

    @Inject
    private TypeMirrorVisitor(
            final RuntimeDeclaredTypeFactory declaredFactory,
            final ExecutableTypeFactory executableFactory,
            final NoTypeFactory noTypeFactory,
            final TypeVariableFactory typeVariableFactory) {
        this.declaredTypeFactory = declaredFactory;
        this.executableTypeFactory = executableFactory;
        this.noTypeFactory = noTypeFactory;
        this.typeVariableFactory = typeVariableFactory;
    }

    @Override
    public Single<TypeMirror> visitClass(final RuntimeClassEntity clazz, final Void parameter) {
        return this.declaredTypeFactory.createFromClass(clazz)
                .cast(TypeMirror.class);
    }

    @Override
    public Single<TypeMirror> visitMethod(final RuntimeMethodEntity method, final Void parameter) {
        return this.executableTypeFactory.createFromExecutable(method)
                .cast(TypeMirror.class);
    }

    @Override
    public Single<TypeMirror> visitConstructor(final RuntimeConstructorEntity constructor, final Void parameter) {
        return this.executableTypeFactory.createFromExecutable(constructor)
                .cast(TypeMirror.class);
    }

    @Override
    public Single<TypeMirror> visitPackage(final RuntimePackageEntity pack, final Void parameter) {
        return Single.just(this.noTypeFactory.noType());
    }

    @Override
    public Single<TypeMirror> visitTypeVariable(final RuntimeTypeVariableEntity typeVariable, final Void parameter) {
        return this.typeVariableFactory.createFromTypeVariable(typeVariable)
                .cast(TypeMirror.class);
    }

    @Override
    public Single<TypeMirror> visitParameter(final RuntimeParameterEntity reflectParameter, final Void parameter) {
        return Single.just(this.noTypeFactory.noType());
    }

    @Override
    public Single<TypeMirror> visitEnum(final RuntimeEnumEntity enumeration, final Void parameter) {
        return Single.just(this.noTypeFactory.noType());
    }

    @Override
    public Single<TypeMirror> visitField(final RuntimeFieldEntity field, final Void parameter) {
        return Single.just(this.noTypeFactory.noType());
    }

    @Override
    public String toString() {
        return "TypeMirrorVisitor{" +
                "declaredTypeFactory=" + this.declaredTypeFactory +
                ", executableTypeFactory=" + this.executableTypeFactory +
                ", noTypeFactory=" + this.noTypeFactory +
                ", typeVariableFactory=" + this.typeVariableFactory +
                "}";
    }

}
