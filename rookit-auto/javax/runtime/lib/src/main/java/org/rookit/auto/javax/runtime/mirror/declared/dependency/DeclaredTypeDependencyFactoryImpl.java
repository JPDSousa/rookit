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
package org.rookit.auto.javax.runtime.mirror.declared.dependency;

import com.google.inject.Inject;
import org.rookit.auto.javax.mirror.declared.dependency.DeclaredTypeDependencyFactory;
import org.rookit.auto.javax.mirror.declared.dependency.DirectSubTypeDependency;
import org.rookit.auto.javax.mirror.declared.dependency.ElementDependency;
import org.rookit.auto.javax.mirror.declared.dependency.EnclosingTypeDependency;
import org.rookit.auto.javax.mirror.declared.dependency.TypeArgumentDependency;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

final class DeclaredTypeDependencyFactoryImpl implements DeclaredTypeDependencyFactory {

    @Inject
    private DeclaredTypeDependencyFactoryImpl() {}

    @Override
    public EnclosingTypeDependency createEnclosingTypeDependency(final TypeMirror dependency) {
        return () -> dependency;
    }

    @Override
    public TypeArgumentDependency createTypeArgumentDependency(final TypeMirror dependency) {
        return () -> dependency;
    }

    @Override
    public ElementDependency createElementDependency(final Element dependency) {
        return () -> dependency;
    }

    @Override
    public DirectSubTypeDependency createDirectSubTypesDependency(final TypeMirror dependency) {
        return () -> dependency;
    }

}
