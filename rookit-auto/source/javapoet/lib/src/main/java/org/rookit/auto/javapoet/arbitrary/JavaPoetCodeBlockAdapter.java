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
package org.rookit.auto.javapoet.arbitrary;

import com.google.inject.Inject;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;

import java.util.ArrayList;
import java.util.Collection;

final class JavaPoetCodeBlockAdapter implements ArbitraryCodeSourceAdapter<CodeBlock> {

    private static final Object[] ARGS = new Object[0];

    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;

    @Inject
    private JavaPoetCodeBlockAdapter(final TypeReferenceSourceAdapter<TypeName> referenceAdapter) {
        this.referenceAdapter = referenceAdapter;
    }

    @Override
    public CodeBlock adaptArbitraryCodeBlock(final ArbitraryCodeSource codeBlock) {
        return CodeBlock.of(codeBlock.format().toString(), adaptArguments(codeBlock.arguments()));
    }

    private Object[] adaptArguments(final Collection<Object> args) {

        final Collection<Object> adaptedArgs = new ArrayList<>(args.size());

        for (final Object arg : args) {
            if (arg instanceof TypeReferenceSource) {
                adaptedArgs.add(this.referenceAdapter.adaptTypeReference((TypeReferenceSource) arg));
            } else {
                adaptedArgs.add(arg);
            }
        }

        return adaptedArgs.toArray(ARGS);
    }

}
