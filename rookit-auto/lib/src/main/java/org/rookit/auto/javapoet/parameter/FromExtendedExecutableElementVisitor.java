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
package org.rookit.auto.javapoet.parameter;

import com.google.inject.Inject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.javax.type.ExtendedTypeElement;

import javax.lang.model.element.TypeElement;
import java.util.function.Predicate;

import static javax.lang.model.element.Modifier.FINAL;

// TODO I'm on the wrong module
final class FromExtendedExecutableElementVisitor<P> implements StreamExtendedElementVisitor<ParameterSpec, P> {

    private final TypeElement element;
    private final CharSequence name;
    private final TypeVariableName typeVariableName;
    private final Predicate<ExtendedTypeElement> useTypeVariable;

    @Inject
    FromExtendedExecutableElementVisitor(final TypeElement element,
                                         final CharSequence name,
                                         final TypeVariableName typeVariableName,
                                         // TODO this requires an annotation...
                                         final Predicate<ExtendedTypeElement> useTypeVariable) {
        this.element = element;
        this.name = name;
        this.typeVariableName = typeVariableName;
        this.useTypeVariable = useTypeVariable;
    }

    @Override
    public StreamEx<ParameterSpec> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        final TypeName param = this.useTypeVariable.test(extendedType) 
                ? this.typeVariableName 
                : ClassName.get(extendedType);
        final TypeName type = ParameterizedTypeName.get(ClassName.get(this.element), param);

        return StreamEx.of(
                ParameterSpec.builder(type, this.name.toString(), FINAL).build()
        );
    }

    @Override
    public String toString() {
        return "FromExtendedExecutableElementVisitor{" +
                "element=" + this.element +
                ", name=" + this.name +
                ", typeVariableName=" + this.typeVariableName +
                ", useTypeVariable=" + this.useTypeVariable +
                "}";
    }
}
