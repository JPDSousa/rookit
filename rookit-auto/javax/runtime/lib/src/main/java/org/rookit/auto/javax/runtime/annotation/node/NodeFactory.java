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
package org.rookit.auto.javax.runtime.annotation.node;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.mirror.annotation.dependency.AnnotationDependencyFactory;
import org.rookit.auto.javax.mirror.annotation.node.MutableNodeAnnotation;
import org.rookit.auto.javax.mirror.annotation.node.NodeAnnotation;
import org.rookit.auto.javax.runtime.entity.RuntimeEntity;
import org.rookit.auto.javax.runtime.mirror.annotation.node.AnnotationNodeFactory;
import org.rookit.utils.graph.DependencyWrapperFactory;

final class NodeFactory implements AnnotationNodeFactory {

    private final DependencyWrapperFactory wrapperFactory;
    private final AnnotationDependencyFactory dependencyFactory;

    @Inject
    private NodeFactory(
            final DependencyWrapperFactory wrapperFactory,
            final AnnotationDependencyFactory dependencyFactory) {
        this.wrapperFactory = wrapperFactory;
        this.dependencyFactory = dependencyFactory;
    }

    @Override
    public Single<NodeAnnotation> createFromEntity(final RuntimeEntity entity) {
        return createMutableFromEntity(entity)
                .cast(NodeAnnotation.class);
    }

    @Override
    public Single<MutableNodeAnnotation> createMutableFromEntity(final RuntimeEntity entity) {
        return Single.just(new MutableNodeImpl(
                this.wrapperFactory.createSingle("Annotation Type",
                                                 this.dependencyFactory::createAnnotationTypeDependency)
        ));
    }

    @Override
    public String toString() {
        return "NodeFactory{" +
                "wrapperFactory=" + this.wrapperFactory +
                ", dependencyFactory=" + this.dependencyFactory +
                "}";
    }

}
