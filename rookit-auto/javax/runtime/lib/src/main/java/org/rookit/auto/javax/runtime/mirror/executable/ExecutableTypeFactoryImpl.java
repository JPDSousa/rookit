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
package org.rookit.auto.javax.runtime.mirror.executable;

import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.entity.RuntimeExecutableEntity;
import org.rookit.auto.javax.runtime.type.executable.ExecutableTypeFactory;
import org.rookit.auto.javax.runtime.type.executable.node.ExecutableDependencyFactory;
import org.rookit.auto.javax.runtime.type.mirror.NodeTypeMirrorFactory;
import org.rookit.auto.javax.runtime.type.no.NoTypeFactory;
import org.rookit.utils.graph.DependencyWrapperFactory;

import javax.lang.model.type.ExecutableType;

final class ExecutableTypeFactoryImpl implements ExecutableTypeFactory {

    private final NodeTypeMirrorFactory nodeFactory;
    private final DependencyWrapperFactory wrapperFactory;
    private final ExecutableDependencyFactory dependencyFactory;
    private final NoTypeFactory noTypeFactory;

    @Inject
    private ExecutableTypeFactoryImpl(
            final NodeTypeMirrorFactory nodeFactory,
            final DependencyWrapperFactory wrapperFactory,
            final ExecutableDependencyFactory dependencyFactory,
            final NoTypeFactory noTypeFactory) {
        this.nodeFactory = nodeFactory;
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
        this.noTypeFactory = noTypeFactory;
    }

    @Override
    public ExecutableType createFromExecutable(final RuntimeExecutableEntity executable) {
        return new ExecutableTypeImpl(
                this.nodeFactory.createMutableFromEntity(executable),
                this.wrapperFactory.createMulti("Type Variable",
                                                this.dependencyFactory::createTypeVariableDependency),
                this.wrapperFactory.createSingle(),
                this.wrapperFactory.createMulti("Parameter Type",
                                                this.dependencyFactory::createParameterTypeDependency),
                this.wrapperFactory.createSingle(),
                this.wrapperFactory.createMulti("Thrown Type",
                                                this.dependencyFactory::createThrownTypeDependency),
                this.noTypeFactory.noType()
        );
    }

    @Override
    public String toString() {
        return "ExecutableTypeFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                ", wrapperFactory=" + this.wrapperFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                ", noTypeFactory=" + this.noTypeFactory +
                "}";
    }

}
