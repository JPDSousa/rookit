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
package org.rookit.auto.javapoet.naming;

import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.parameter.ExtendedTypeParameterElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.variable.ExtendedVariableElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.utils.string.StringUtils;
import org.rookit.utils.string.template.Template1;

import javax.lang.model.element.Element;

final class TypeVisitor implements ExtendedElementVisitor<String, Void> {

    private final Template1 typeTemplate;
    private final StringUtils stringUtils;

    TypeVisitor(final Template1 typeTemplate, final StringUtils stringUtils) {
        this.typeTemplate = typeTemplate;
        this.stringUtils = stringUtils;
    }

    private String applyTemplate(final Element element) {
        return this.typeTemplate.build(this.stringUtils.capitalizeFirstChar(element.getSimpleName().toString()));
    }


    @Override
    public String visitPackage(final ExtendedPackageElement packageElement, final Void parameter) {
        return applyTemplate(packageElement);
    }

    @Override
    public String visitType(final ExtendedTypeElement extendedType, final Void parameter) {
        return applyTemplate(extendedType);
    }

    @Override
    public String visitExecutable(final ExtendedExecutableElement extendedExecutable, final Void parameter) {
        final String elementName = this.stringUtils.capitalizeFirstChar(extendedExecutable
                .getEnclosingElement().getSimpleName().toString());
        final String methodName = this.stringUtils.capitalizeFirstChar(extendedExecutable.getSimpleName().toString());
        return this.typeTemplate.build(elementName + methodName);
    }

    @Override
    public String visitTypeParameter(final ExtendedTypeParameterElement extendedParameter, final Void parameter) {
        return applyTemplate(extendedParameter);
    }

    @Override
    public String visitVariable(final ExtendedVariableElement extendedElement, final Void parameter) {
        return applyTemplate(extendedElement);
    }

    @Override
    public String visitUnknown(final ExtendedElement extendedElement, final Void parameter) {
        return applyTemplate(extendedElement);
    }

    @Override
    public String toString() {
        return "TypeVisitor{" +
                "typeTemplate=" + this.typeTemplate +
                ", stringUtils=" + this.stringUtils +
                "}";
    }
}
