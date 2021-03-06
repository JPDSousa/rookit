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
package org.rookit.auto.source;

import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.container.TypeSourceContainer;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.WildcardVariableSource;

public interface CodeSourceVisitor<R, P> {

    R visitType(TypeSource source, P parameter);

    R visitMethod(MethodSource source, P parameter);

    R visitAnnotation(AnnotationSource source, P parameter);

    R visitTypeVariable(TypeVariableSource source, P parameter);

    R visitParameter(ParameterSource source, P parameter);

    R visitTypeContainer(TypeSourceContainer<?> container, P parameter);

    R visitWildcard(WildcardVariableSource wildcard, P parameter);

    R visitReference(TypeReferenceSource reference, P parameter);

    R visitField(FieldSource fieldSource, P parameter);

    R visitArbitraryCode(ArbitraryCodeSource codeBlock, P parameter);

    R visitTypeParameter(TypeParameterSource typeParameterSource, P parameter);

}
