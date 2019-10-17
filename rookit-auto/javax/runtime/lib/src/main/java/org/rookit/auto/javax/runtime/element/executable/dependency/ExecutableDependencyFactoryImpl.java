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
package org.rookit.auto.javax.runtime.element.executable.dependency;

import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.ExecutableDependencyFactory;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.ParameterDependency;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.ReceiverTypeDependency;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.ReturnTypeDependency;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.ThrownTypeDependency;
import org.rookit.auto.javax.runtime.element.executable.node.dependency.TypeParameterDependency;
import org.rookit.auto.javax.runtime.element.node.dependency.DependencyFactory;
import org.rookit.auto.javax.runtime.element.node.dependency.EnclosedDependency;
import org.rookit.auto.javax.runtime.element.node.dependency.EnclosingDependency;
import org.rookit.auto.javax.runtime.element.node.dependency.TypeMirrorDependency;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

final class ExecutableDependencyFactoryImpl implements ExecutableDependencyFactory {

    private final DependencyFactory dependencyFactory;

    @Inject
    private ExecutableDependencyFactoryImpl(final DependencyFactory dependencyFactory) {
        this.dependencyFactory = dependencyFactory;
    }

    @Override
    public TypeParameterDependency createTypeParametersDependency(final TypeParameterElement dependency) {
        return () -> dependency;
    }

    @Override
    public ReturnTypeDependency createReturnTypeDependency(final TypeMirror dependency) {
        return () -> dependency;
    }

    @Override
    public ParameterDependency createParameterDependency(final VariableElement dependency) {
        return () -> dependency;
    }

    @Override
    public ReceiverTypeDependency createReceiverTypeDependency(final TypeMirror dependency) {
        return () -> dependency;
    }

    @Override
    public ThrownTypeDependency createThrownTypeDependency(final TypeMirror dependency) {
        return () -> dependency;
    }

    @Override
    public EnclosingDependency enclosingDependency(final Element element) {
        return this.dependencyFactory.enclosingDependency(element);
    }

    @Override
    public EnclosedDependency enclosedDependency(final Element element) {
        return this.dependencyFactory.enclosedDependency(element);
    }

    @Override
    public TypeMirrorDependency typeMirrorDependency(final TypeMirror typeMirror) {
        return this.dependencyFactory.typeMirrorDependency(typeMirror);
    }

    @Override
    public String toString() {
        return "ExecutableDependencyFactoryImpl{" +
                "dependencyFactory=" + this.dependencyFactory +
                "}";
    }

}
