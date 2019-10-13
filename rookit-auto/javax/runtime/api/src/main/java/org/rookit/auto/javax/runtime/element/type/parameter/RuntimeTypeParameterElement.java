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
package org.rookit.auto.javax.runtime.element.type.parameter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.rookit.auto.javax.runtime.element.RuntimeElement;
import org.rookit.auto.javax.runtime.element.node.MutableNodeElement;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;
import java.util.List;
import java.util.Set;

public interface RuntimeTypeParameterElement extends TypeParameterElement, RuntimeElement, MutableNodeElement {

    @Override
    default ElementKind getKind() {
        return ElementKind.TYPE_PARAMETER;
    }

    @Override
    default Set<Modifier> getModifiers() {
        return ImmutableSet.of();
    }

    @Override
    default List<? extends Element> getEnclosedElements() {
        return ImmutableList.of();
    }

    @Override
    default <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return v.visitTypeParameter(this, p);
    }

}
