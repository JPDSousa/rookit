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
package org.rookit.auto.javax.runtime.element.executable.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.ExecutableDependencyFactory;
import org.rookit.auto.javax.runtime.element.node.NodeElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.auto.javax.runtime.mirror.no.NoTypeFactory;
import org.rookit.utils.graph.DependencyWrapperFactory;

final class ExecutableNodeElementFactoryImpl implements ExecutableNodeElementFactory {

    private final NodeElementFactory nodeFactory;
    private final DependencyWrapperFactory wrapperFactory;
    private final ExecutableDependencyFactory dependencyFactory;
    private final NoTypeFactory noTypeFactory;

    @Inject
    private ExecutableNodeElementFactoryImpl(
            final NodeElementFactory nodeFactory,
            final DependencyWrapperFactory wrapperFactory,
            final ExecutableDependencyFactory dependencyFactory,
            final NoTypeFactory noTypeFactory) {
        this.nodeFactory = nodeFactory;
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
        this.noTypeFactory = noTypeFactory;
    }

    @Override
    public Single<ExecutableNodeElement> createFromEntity(final RuntimeEntity entity) {
        return createMutableFromEntity(entity)
                .cast(ExecutableNodeElement.class);
    }

    @Override
    public Single<MutableExecutableNodeElement> createMutableFromEntity(final RuntimeEntity entity) {
        return this.nodeFactory.createMutableFromEntity(entity)
                .map(node -> new MutableExecutableNodeElementImpl(
                        node,
                        this.wrapperFactory.createMulti("Type Parameters",
                                                        this.dependencyFactory::createTypeParametersDependency),
                        this.wrapperFactory.createSingle("Return type",
                                                         this.dependencyFactory::createReturnTypeDependency),
                        this.wrapperFactory.createMulti("Parameters",
                                                        this.dependencyFactory::createParameterDependency),
                        this.wrapperFactory.createSingle("Receiver type",
                                                         this.dependencyFactory::createReceiverTypeDependency),
                        this.wrapperFactory.createMulti("Thrown Type",
                                                        this.dependencyFactory::createThrownTypeDependency),
                        this.noTypeFactory.noType())
                );
    }

    @Override
    public String toString() {
        return "ExecutableNodeElementFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                ", wrapperFactory=" + this.wrapperFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                ", noTypeFactory=" + this.noTypeFactory +
                "}";
    }

}
