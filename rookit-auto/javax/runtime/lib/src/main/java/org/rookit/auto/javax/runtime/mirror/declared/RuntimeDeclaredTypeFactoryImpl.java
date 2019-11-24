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
package org.rookit.auto.javax.runtime.mirror.declared;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.mirror.declared.DeclaredTypeFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.mirror.declared.node.NodeDeclaredTypeFactory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;

final class RuntimeDeclaredTypeFactoryImpl implements RuntimeDeclaredTypeFactory {

    private final NodeDeclaredTypeFactory nodeFactory;
    private final DeclaredTypeFactory delegate;

    @Inject
    private RuntimeDeclaredTypeFactoryImpl(
            final NodeDeclaredTypeFactory nodeFactory,
            final DeclaredTypeFactory delegate) {
        this.nodeFactory = nodeFactory;
        this.delegate = delegate;
    }

    @Override
    public Single<RuntimeDeclaredType> createFromClass(final RuntimeClassEntity typeEntity) {
        return this.nodeFactory.createMutableFromEntity(typeEntity)
                .map(node -> new DeclaredTypeImpl(
                        new ErasuredDeclaredType(
                                typeEntity,
                                node
                        ),
                        node
                     )
                );
    }

    @Override
    public Single<DeclaredType> createFromElement(
            final TypeElement element, final Collection<TypeMirror> args) {
        return this.delegate.createFromElement(element, args);
    }

    @Override
    public Single<DeclaredType> createFromElement(
            final DeclaredType containing, final TypeElement element, final Collection<TypeMirror> args) {
        return this.delegate.createFromElement(containing, element, args);
    }

    @Override
    public String toString() {
        return "RuntimeDeclaredTypeFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                ", delegate=" + this.delegate +
                "}";
    }

}
