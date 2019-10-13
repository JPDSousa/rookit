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
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.utils.primitive.VoidUtils;
import org.rookit.utils.string.template.Template1;

final class BaseJavaPoetNamingFactory  implements JavaPoetNamingFactory {

    private final ExtendedElementVisitor<ExtendedPackageElement, Void> packageVisitor;
    private final ExtendedElementVisitor<String, Void> typeVisitor;
    private final VoidUtils voidUtils;
    private final Template1 methodTemplate;

    BaseJavaPoetNamingFactory(
            final ExtendedElementVisitor<ExtendedPackageElement, Void> packageVisitor,
            final ExtendedElementVisitor<String, Void> typeVisitor,
            final VoidUtils voidUtils,
            final Template1 methodTemplate) {
        this.packageVisitor = packageVisitor;
        this.typeVisitor = typeVisitor;
        this.voidUtils = voidUtils;
        this.methodTemplate = methodTemplate;
    }

    @Override
    public ExtendedPackageElement packageName(final ExtendedElement element) {
        return element.accept(this.packageVisitor, this.voidUtils.returnVoid());
    }

    @Override
    public String type(final ExtendedElement element) {
        return element.accept(this.typeVisitor, this.voidUtils.returnVoid());
    }

    @Override
    public String method(final String propertyName) {
        return method(propertyName, this.methodTemplate);
    }

    @Override
    public String method(final String propertyName, final Template1 template) {
        return template.build(propertyName);
    }

    @Override
    public String toString() {
        return "BaseJavaPoetNamingFactory{" +
                "packageVisitor=" + this.packageVisitor +
                ", typeVisitor=" + this.typeVisitor +
                ", voidUtils=" + this.voidUtils +
                ", methodTemplate=" + this.methodTemplate +
                "}";
    }

}
