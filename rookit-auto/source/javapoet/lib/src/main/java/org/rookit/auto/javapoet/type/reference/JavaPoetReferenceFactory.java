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
package org.rookit.auto.javapoet.type.reference;

import com.google.inject.Inject;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.rookit.auto.javax.naming.Identifier;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceFactory;
import org.rookit.utils.primitive.VoidUtils;

final class JavaPoetReferenceFactory implements TypeReferenceSourceFactory {

    private final VoidUtils voidUtils;

    @Inject
    private JavaPoetReferenceFactory(final VoidUtils voidUtils) {
        this.voidUtils = voidUtils;
    }

    @Override
    public TypeReferenceSource create(final ExtendedTypeMirror element) {

        return new JavaPoetReference(TypeName.get(element));
    }

    @Override
    public TypeReferenceSource fromIdentifier(final Identifier identifier) {

        final ClassName className = ClassName.get(identifier.packageElement()
                                                          .getQualifiedName()
                                                          .toString(),
                                                  identifier.name());

        return new JavaPoetReference(className);
    }

    @Override
    public TypeReferenceSource fromClass(final Class<?> clazz) {

        return new JavaPoetReference(ClassName.get(clazz));
    }

    @Override
    public TypeReferenceSource create(final ExtendedTypeElement element) {

        return new JavaPoetReference(ClassName.get(element));
    }

    @Override
    public TypeReferenceSource resolveParameters(final ExtendedTypeElement element,
                                                 final TypeReferenceSource... parameters) {

        // TODO dummy implementation, because this method is a bad design decision
        return create(element);
    }

    @Override
    public String toString() {
        return "JavaPoetReferenceFactory{" +
                "voidUtils=" + this.voidUtils +
                "}";
    }

}
