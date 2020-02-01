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
package org.rookit.auto.javax.executable;

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElementFactory;
import org.rookit.auto.javax.variable.ExtendedVariableElementFactory;
import org.rookit.utils.optional.OptionalFactory;

import javax.lang.model.element.ExecutableElement;

final class ExtendedExecutableElementFactoryImpl implements ExtendedExecutableElementFactory {

    private final OptionalFactory optionalFactory;
    private final ExtendedElementFactory elementFactory;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final ExtendedTypeParameterElementFactory typeParameterFactory;
    private final ExtendedVariableElementFactory variableFactory;

    @Inject
    private ExtendedExecutableElementFactoryImpl(
            final OptionalFactory optionalFactory,
            final ExtendedElementFactory elementFactory,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final ExtendedTypeParameterElementFactory typeParameterFactory,
            final ExtendedVariableElementFactory variableFactory) {
        this.optionalFactory = optionalFactory;
        this.elementFactory = elementFactory;
        this.mirrorFactory = mirrorFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.variableFactory = variableFactory;
    }

    @Override
    public ExtendedExecutableElement extend(final ExecutableElement javaxElement) {
        return this.optionalFactory.of(javaxElement)
                .select(ExtendedExecutableElement.class)
                .orElseGet(() -> new ExtendedExecutableElementImpl(
                        this.elementFactory.extend(javaxElement),
                        javaxElement,
                        this.mirrorFactory,
                        this.typeParameterFactory,
                        this.variableFactory)
                );
    }

    @Override
    public String toString() {
        return "ExtendedExecutableElementFactoryImpl{" +
                "optionalFactory=" + this.optionalFactory +
                ", elementFactory=" + this.elementFactory +
                ", mirrorFactory=" + this.mirrorFactory +
                ", typeParameterFactory=" + this.typeParameterFactory +
                ", variableFactory=" + this.variableFactory +
                "}";
    }

}
