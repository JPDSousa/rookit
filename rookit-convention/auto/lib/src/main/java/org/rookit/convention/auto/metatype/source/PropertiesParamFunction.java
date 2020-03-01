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
package org.rookit.convention.auto.metatype.source;

import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;

import java.util.function.Function;

final class PropertiesParamFunction implements Function<ConventionTypeElement, TypeReferenceSource> {

    private final boolean varEntity;
    private final TypeVariableSource variableName;
    private final TypeReferenceSourceFactory referenceFactory;

    PropertiesParamFunction(
            final boolean varEntity,
            final TypeVariableSource variableName,
            final TypeReferenceSourceFactory referenceFactory) {
        this.varEntity = varEntity;
        this.variableName = variableName;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public TypeReferenceSource apply(final ConventionTypeElement element) {
        if (!element.isPropertyContainer() && (this.varEntity || !element.isEntity())) {
            return this.variableName;
        }
        return this.referenceFactory.create(element);
    }

    @Override
    public String toString() {
        return "PropertiesParamFunction{" +
                "varEntity=" + this.varEntity +
                ", variableName=" + this.variableName +
                ", referenceFactory=" + this.referenceFactory +
                "}";
    }

}
