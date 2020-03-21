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
package org.rookit.auto.source.type;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.auto.javax.visitor.GenericStreamExBuilder;
import org.rookit.auto.source.doc.JavadocTemplate1;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.extension.ExtensionStrategy;
import org.rookit.auto.source.type.reference.TypeReferenceSource;

import java.util.function.Function;

public interface GenericTypeSourceBuilder<B extends GenericTypeSourceBuilder<B, V, P>,
        V extends ExtendedElementVisitor<StreamEx<TypeSource>, P>, P>
        extends GenericStreamExBuilder<B, V, TypeSource, P> {

    B withAnnotations(Iterable<AnnotationSource> annotations);

    B copyBodyFrom(ExtendedTypeElement source);

    B copyBodyFrom(Function<ExtendedElement, ExtendedTypeElement> extractionFunction);

    B withClassJavadoc(JavadocTemplate1 template);

}
