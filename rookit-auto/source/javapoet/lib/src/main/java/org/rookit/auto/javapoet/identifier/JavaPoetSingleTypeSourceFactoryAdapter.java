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
package org.rookit.auto.javapoet.identifier;

import com.google.inject.Inject;
import org.rookit.auto.javapoet.type.JavaPoetSingleTypeSourceFactory;
import org.rookit.auto.javapoet.type.JavaPoetTypeSourceFactory;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.source.type.SingleTypeSourceFactory;
import org.rookit.auto.source.type.TypeSource;

final class JavaPoetSingleTypeSourceFactoryAdapter implements SingleTypeSourceFactory {

    private final JavaPoetSingleTypeSourceFactory delegate;
    private final JavaPoetIdentifierFactory identifierFactory;
    private final JavaPoetTypeSourceFactory adapter;

    @Inject
    private JavaPoetSingleTypeSourceFactoryAdapter(final JavaPoetSingleTypeSourceFactory delegate,
                                                   final JavaPoetIdentifierFactory identifierFactory,
                                                   final JavaPoetTypeSourceFactory adapter) {
        this.delegate = delegate;
        this.identifierFactory = identifierFactory;
        this.adapter = adapter;
    }

    @Override
    public TypeSource create(final Identifier identifier, final ExtendedTypeElement element) {
        final JavaPoetIdentifier javaPoetIdentifier = this.identifierFactory.create(identifier);
        return this.adapter.fromTypeSpec(identifier, this.delegate.create(javaPoetIdentifier, element));
    }

    @Override
    public String toString() {
        return "JavaPoetSingleTypeSourceFactoryAdapter{" +
                "delegate=" + this.delegate +
                ", identifierFactory=" + this.identifierFactory +
                ", adapter=" + this.adapter +
                "}";
    }
}
