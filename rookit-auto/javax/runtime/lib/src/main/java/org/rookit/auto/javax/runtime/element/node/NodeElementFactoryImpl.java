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
package org.rookit.auto.javax.runtime.element.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.annotation.RuntimeAnnotatedConstructFactory;
import org.rookit.auto.javax.runtime.element.node.dependency.DependencyFactory;
import org.rookit.auto.javax.runtime.element.node.dependency.ElementDependencyVisitor;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.utils.graph.DependencyWrapperFactory;

final class NodeElementFactoryImpl implements NodeElementFactory {

    private final RuntimeAnnotatedConstructFactory annotatedFactory;
    private final DependencyWrapperFactory wrapperFactory;
    private final DependencyFactory dependencyFactory;
    private final ElementDependencyVisitor<Single<MutableNodeElement>, MutableNodeElement> visitor;

    @Inject
    private NodeElementFactoryImpl(
            final RuntimeAnnotatedConstructFactory annotatedFactory,
            final DependencyWrapperFactory wrapperFactory,
            final DependencyFactory dependencyFactory,
            final ElementDependencyVisitor<Single<MutableNodeElement>, MutableNodeElement> visitor) {
        this.annotatedFactory = annotatedFactory;
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
        this.visitor = visitor;
    }

    @Override
    public Single<NodeElement> createFromEntity(final RuntimeEntity entity) {
        return createMutableFromEntity(entity)
                .cast(NodeElement.class);
    }

    @Override
    public Single<MutableNodeElement> createMutableFromEntity(final RuntimeEntity entity) {
        return this.annotatedFactory.createFromEntity(entity)
                .map(construct -> new MutableNodeElementImpl(
                        this.wrapperFactory.createMulti("Enclosed Elements",
                                                        this.dependencyFactory::enclosedDependency),
                        this.wrapperFactory.createSingle("Enclosing Element",
                                                         this.dependencyFactory::enclosingDependency),
                        this.wrapperFactory.createSingle("Type Mirror",
                                                         this.dependencyFactory::typeMirrorDependency),
                        construct,
                        this.visitor));
    }

    @Override
    public String toString() {
        return "NodeElementFactoryImpl{" +
                "annotatedFactory=" + this.annotatedFactory +
                ", wrapperFactory=" + this.wrapperFactory +
                ", visitor=" + this.visitor +
                "}";
    }

}
