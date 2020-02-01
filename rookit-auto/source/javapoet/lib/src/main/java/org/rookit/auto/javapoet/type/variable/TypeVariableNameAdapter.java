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
package org.rookit.auto.javapoet.type.variable;

import com.google.inject.Inject;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSourceAdapter;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class TypeVariableNameAdapter implements TypeVariableSourceAdapter<TypeVariableName> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeVariableNameAdapter.class);

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;

    @Inject
    private TypeVariableNameAdapter(final TypeReferenceSourceAdapter<TypeName> referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    // TODO create a JavaPoetTypeVariable interface, so that we can get rid of these supressions
    @SuppressWarnings({"InstanceofConcreteClass", "CastToConcreteClass"}) // performance improvement
    @Override
    public TypeVariableName adaptTypeVariable(final TypeVariableSource typeVariable) {

        if (typeVariable instanceof JavaPoetTypeVariable) {
            logger.trace("{} is already a javapoet implementation. Unwrapping.", typeVariable);
            return ((JavaPoetTypeVariable) typeVariable).getTypeVariable();
        }

        final TypeName[] bounds = typeVariable.bounds()
                .stream()
                .map(this.referenceAdapter::adaptTypeReference)
                .toArray(TypeName[]::new);

        return TypeVariableName.get(typeVariable.name().toString(), bounds);
    }

}
