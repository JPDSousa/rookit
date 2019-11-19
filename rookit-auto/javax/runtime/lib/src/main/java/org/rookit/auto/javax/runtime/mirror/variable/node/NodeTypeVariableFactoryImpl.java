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
package org.rookit.auto.javax.runtime.mirror.variable.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.mirror.variable.node.MutableNodeTypeVariable;
import org.rookit.auto.javax.mirror.variable.node.NodeTypeVariable;
import org.rookit.auto.javax.runtime.annotation.AnnotatedConstructFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.auto.javax.mirror.wildcard.dependency.TypeVariableDependencyFactory;
import org.rookit.utils.graph.DependencyWrapperFactory;

final class NodeTypeVariableFactoryImpl implements NodeTypeVariableFactory {

    private final AnnotatedConstructFactory annotatedFactory;
    private final DependencyWrapperFactory wrapperFactory;
    private final TypeVariableDependencyFactory dependencyFactory;

    @Inject
    private NodeTypeVariableFactoryImpl(
            final AnnotatedConstructFactory annotatedFactory,
            final DependencyWrapperFactory wrapperFactory,
            final TypeVariableDependencyFactory dependencyFactory) {
        this.annotatedFactory = annotatedFactory;
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
    }

    @Override
    public Single<NodeTypeVariable> createFromEntity(final RuntimeEntity entity) {
        return createMutableFromEntity(entity)
                .cast(NodeTypeVariable.class);
    }

    @Override
    public Single<MutableNodeTypeVariable> createMutableFromEntity(final RuntimeEntity entity) {
        return this.annotatedFactory.createFromEntity(entity)
                .map(construct -> new NodeTypeVariableImpl(
                        construct,
                        this.wrapperFactory.createSingle("Element",
                                                         this.dependencyFactory::createElementDependency),
                        this.wrapperFactory.createSingle("Upper Bound",
                                                         this.dependencyFactory::createUpperBoundDependency),
                        this.wrapperFactory.createSingle("Lower Bound",
                                                         this.dependencyFactory::createLowerBoundDependency)
                ));
    }

    @Override
    public String toString() {
        return "NodeTypeVariableFactoryImpl{" +
                "annotatedFactory=" + this.annotatedFactory +
                ", wrapperFactory=" + this.wrapperFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                "}";
    }

}
