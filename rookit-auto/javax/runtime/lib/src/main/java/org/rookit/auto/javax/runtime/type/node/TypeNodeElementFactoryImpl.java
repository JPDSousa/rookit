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
package org.rookit.auto.javax.runtime.type.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.node.NodeElementFactory;
import org.rookit.auto.javax.runtime.element.type.node.MutableTypeNodeElement;
import org.rookit.auto.javax.runtime.element.type.node.RuntimeTypeNodeElementFactory;
import org.rookit.auto.javax.runtime.element.type.node.TypeDependencyFactory;
import org.rookit.auto.javax.runtime.element.type.node.TypeNodeElement;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.utils.graph.DependencyWrapperFactory;

final class TypeNodeElementFactoryImpl implements RuntimeTypeNodeElementFactory {

    private final NodeElementFactory nodeElementFactory;
    private final DependencyWrapperFactory wrapperFactory;
    private final TypeDependencyFactory dependencyFactory;

    @Inject
    private TypeNodeElementFactoryImpl(
            final NodeElementFactory nodeElementFactory,
            final DependencyWrapperFactory wrapperFactory,
            final TypeDependencyFactory dependencyFactory) {
        this.nodeElementFactory = nodeElementFactory;
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
    }

    @Override
    public Single<TypeNodeElement> createFromEntity(final RuntimeEntity entity) {
        return createMutableFromEntity(entity)
                .cast(TypeNodeElement.class);
    }

    @Override
    public Single<MutableTypeNodeElement> createMutableFromEntity(final RuntimeEntity entity) {
        return this.nodeElementFactory.createMutableFromEntity(entity)
               .map(node -> new MutableTypeNodeElementImpl(
                       node,
                       this.wrapperFactory.createSingle("Superclass",
                                                        this.dependencyFactory::createSuperClassDependency),
                       this.wrapperFactory.createMulti("Interfaces",
                                                       this.dependencyFactory::createInterfaceDependency),
                       this.wrapperFactory.createMulti("Type Parameters",
                                                       this.dependencyFactory::createTypeParameterDependency)
               ));
    }

    @Override
    public String toString() {
        return "TypeNodeElementFactoryImpl{" +
                "nodeElementFactory=" + this.nodeElementFactory +
                ", wrapperFactory=" + this.wrapperFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                "}";
    }

}
