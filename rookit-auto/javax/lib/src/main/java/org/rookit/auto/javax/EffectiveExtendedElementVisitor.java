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
package org.rookit.auto.javax;

import com.google.inject.Inject;
import org.rookit.auto.javax.executable.ExtendedExecutableElementFactory;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.auto.javax.type.ExtendedTypeElementFactory;
import org.rookit.auto.javax.type.parameter.ExtendedTypeParameterElementFactory;
import org.rookit.auto.javax.variable.ExtendedVariableElementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

final class EffectiveExtendedElementVisitor implements ElementVisitor<ExtendedElement, Void> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(EffectiveExtendedElementVisitor.class);

    private final ExtendedElementFactory elementFactory;
    private final ExtendedPackageElementFactory packageFactory;
    private final ExtendedTypeElementFactory typeFactory;
    private final ExtendedTypeParameterElementFactory typeParameterFactory;
    private final ExtendedExecutableElementFactory executableFactory;
    private final ExtendedVariableElementFactory variableFactory;

    @Inject
    private EffectiveExtendedElementVisitor(
            final ExtendedElementFactory elementFactory,
            final ExtendedPackageElementFactory packageFactory,
            final ExtendedTypeElementFactory typeFactory,
            final ExtendedTypeParameterElementFactory typeParameterFactory,
            final ExtendedExecutableElementFactory executableFactory,
            final ExtendedVariableElementFactory variableFactory) {
        this.elementFactory = elementFactory;
        this.packageFactory = packageFactory;
        this.typeFactory = typeFactory;
        this.typeParameterFactory = typeParameterFactory;
        this.executableFactory = executableFactory;
        this.variableFactory = variableFactory;
    }

    @Override
    public ExtendedElement visit(final Element e, final Void aVoid) {
        return visit(e);
    }

    @Override
    public ExtendedElement visit(final Element e) {
        return this.elementFactory.extend(e);
    }

    @Override
    public ExtendedElement visitPackage(final PackageElement e, final Void aVoid) {
        return this.packageFactory.extend(e);
    }

    @Override
    public ExtendedElement visitType(final TypeElement e, final Void aVoid) {
        return this.typeFactory.extend(e);
    }

    @Override
    public ExtendedElement visitVariable(final VariableElement e, final Void aVoid) {
        return this.variableFactory.extend(e);
    }

    @Override
    public ExtendedElement visitExecutable(final ExecutableElement e, final Void aVoid) {
        return this.executableFactory.extend(e);
    }

    @Override
    public ExtendedElement visitTypeParameter(final TypeParameterElement e, final Void aVoid) {
        return this.typeParameterFactory.extend(e);
    }

    @Override
    public ExtendedElement visitUnknown(final Element e, final Void aVoid) {

        logger.warn("Visiting unknown for {}", e.getClass());
        return this.elementFactory.extend(e);
    }

    @Override
    public String toString() {
        return "EffectiveExtendedElementVisitor{" +
                "elementFactory=" + this.elementFactory +
                ", packageFactory=" + this.packageFactory +
                ", typeFactory=" + this.typeFactory +
                ", typeParameterFactory=" + this.typeParameterFactory +
                ", executableFactory=" + this.executableFactory +
                ", variableFactory=" + this.variableFactory +
                "}";
    }

}
