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
package org.rookit.auto.javax.runtime.element;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.executable.ExecutableElementFactory;
import org.rookit.auto.javax.runtime.element.pack.PackageElementFactory;
import org.rookit.auto.javax.runtime.element.type.TypeElementFactory;
import org.rookit.auto.javax.runtime.element.type.parameter.TypeParameterElementFactory;
import org.rookit.auto.javax.runtime.element.variable.VariableElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeConstructorEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityVisitor;
import org.rookit.auto.javax.runtime.entity.RuntimeEnumEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeMethodEntity;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeParameterEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;
import org.rookit.utils.primitive.VoidUtils;

final class RuntimeElementFactoryImpl implements RuntimeElementFactory {

    private final VoidUtils voidUtils;
    private final TypeElementFactory typeFactory;
    private final ExecutableElementFactory executableFactory;
    private final PackageElementFactory packageFactory;
    private final TypeParameterElementFactory typeParameterFactory;
    private final VariableElementFactory variableFactory;

    @Inject
    private RuntimeElementFactoryImpl(
            final VoidUtils voidUtils,
            final TypeElementFactory typeFactory,
            final ExecutableElementFactory executableFactory,
            final PackageElementFactory packageFactory,
            final TypeParameterElementFactory typeParameterFactory,
            final VariableElementFactory variableFactory) {
        this.voidUtils = voidUtils;
        this.typeFactory = typeFactory;
        this.executableFactory = executableFactory;
        this.packageFactory = packageFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.variableFactory = variableFactory;
    }

    @Override
    public Single<RuntimeElement> createElement(final RuntimeEntity entity) {
        return entity.accept(new Visitor(), this.voidUtils.returnVoid());
    }

    private class Visitor implements RuntimeEntityVisitor<Single<RuntimeElement>, Void> {

        Visitor() {
        }

        @Override
        public Single<RuntimeElement> visitClass(
                final RuntimeClassEntity clazz, final Void parameter) {
            return RuntimeElementFactoryImpl.this.typeFactory.createElement(clazz)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitMethod(
                final RuntimeMethodEntity method, final Void parameter) {
            return RuntimeElementFactoryImpl.this.executableFactory.createElement(method)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitConstructor(
                final RuntimeConstructorEntity constructor, final Void parameter) {
            return RuntimeElementFactoryImpl.this.executableFactory.createElement(constructor)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitPackage(
                final RuntimePackageEntity pack, final Void parameter) {
            return RuntimeElementFactoryImpl.this.packageFactory.createElement(pack)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitTypeVariable(
                final RuntimeTypeVariableEntity typeVariable, final Void parameter) {
            return RuntimeElementFactoryImpl.this.typeParameterFactory.createElement(typeVariable)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitParameter(
                final RuntimeParameterEntity reflectParameter, final Void parameter) {
            return RuntimeElementFactoryImpl.this.variableFactory.createFromParameter(reflectParameter)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitEnum(
                final RuntimeEnumEntity enumeration, final Void parameter) {
            return RuntimeElementFactoryImpl.this.variableFactory.createFromEnum(enumeration)
                    .cast(RuntimeElement.class);
        }

        @Override
        public Single<RuntimeElement> visitField(
                final RuntimeFieldEntity field, final Void parameter) {
            return RuntimeElementFactoryImpl.this.variableFactory.createFromField(field)
                    .cast(RuntimeElement.class);
        }

    }

    @Override
    public String toString() {
        return "RuntimeElementFactoryImpl{" +
                "voidUtils=" + this.voidUtils +
                ", typeFactory=" + this.typeFactory +
                ", executableFactory=" + this.executableFactory +
                ", packageFactory=" + this.packageFactory +
                ", typeParameterFactory=" + this.typeParameterFactory +
                ", variableFactory=" + this.variableFactory +
                "}";
    }

}
