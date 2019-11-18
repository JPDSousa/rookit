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
package org.rookit.auto.javax.runtime.mirror.declared.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.annotation.AnnotatedConstructFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.auto.javax.runtime.mirror.declared.dependency.DeclaredTypeDependencyFactory;
import org.rookit.auto.javax.mirror.no.NoTypeFactory;
import org.rookit.utils.graph.DependencyWrapper;
import org.rookit.utils.graph.DependencyWrapperFactory;
import org.rookit.utils.graph.MultiDependencyWrapper;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

final class NodeDeclaredTypeFactoryImpl implements NodeDeclaredTypeFactory {

    private final AnnotatedConstructFactory annotatedFactory;
    private final DependencyWrapperFactory wrapperFactory;
    private final DeclaredTypeDependencyFactory dependencyFactory;
    private final NoTypeFactory noTypeFactory;

    @Inject
    private NodeDeclaredTypeFactoryImpl(
            final AnnotatedConstructFactory annotatedFactory,
            final DependencyWrapperFactory wrapperFactory,
            final DeclaredTypeDependencyFactory dependencyFactory,
            final NoTypeFactory noTypeFactory) {
        this.annotatedFactory = annotatedFactory;
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
        this.noTypeFactory = noTypeFactory;
    }

    @Override
    public Single<NodeDeclaredType> createFromEntity(final RuntimeEntity entity) {
        return createMutableFromEntity(entity)
                .cast(NodeDeclaredType.class);
    }

    @Override
    public Single<MutableNodeDeclaredType> createMutableFromEntity(final RuntimeEntity entity) {
        return this.annotatedFactory.createFromEntity(entity)
                .map(construct -> new MutableNodeDeclaredTypeImpl(
                        construct,
                        createEnclosedType(),
                        createTypeArgumentsDependency(),
                        createElementDependency()));
    }

    private DependencyWrapper<Element> createElementDependency() {
        return this.wrapperFactory.createSingle("Element",
                                                this.dependencyFactory::createElementDependency);
    }

    private MultiDependencyWrapper<TypeMirror> createTypeArgumentsDependency() {
        return this.wrapperFactory
                .createMulti("Type Arguments",
                             this.dependencyFactory::createTypeArgumentDependency);
    }

    private DependencyWrapper<TypeMirror> createEnclosedType() {
        final DependencyWrapper<TypeMirror> enclosedType = this.wrapperFactory
                .createSingle("Enclosed Type", this.dependencyFactory::createEnclosingTypeDependency);
        enclosedType.set(this.noTypeFactory.noType());
        return enclosedType;
    }

    @Override
    public String toString() {
        return "NodeDeclaredTypeFactoryImpl{" +
                "annotatedFactory=" + this.annotatedFactory +
                ", wrapperFactory=" + this.wrapperFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                ", noTypeFactory=" + this.noTypeFactory +
                "}";
    }

}
