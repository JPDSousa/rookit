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
package org.rookit.convention.auto.source.method;

import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.visitor.StreamExtendedElementVisitor;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.convention.auto.javax.ConventionTypeElement;
import org.rookit.convention.auto.javax.visitor.ConventionTypeElementVisitor;

final class GetterMethodVisitor<P> implements ConventionTypeElementVisitor<StreamEx<MethodSource>, P>,
        StreamExtendedElementVisitor<MethodSource, P> {

    private final MethodSourceFactory methodSourceFactory;
    private final CharSequence name;
    private final ExtendedTypeMirror type;

    GetterMethodVisitor(
            final MethodSourceFactory methodSourceFactory,
            final CharSequence name,
            final ExtendedTypeMirror type) {
        this.methodSourceFactory = methodSourceFactory;
        this.name = name;
        this.type = type;
    }

    @Override
    public StreamEx<MethodSource> visitType(final ExtendedTypeElement extendedType, final P parameter) {
        return createMethod();
    }

    private StreamEx<MethodSource> createMethod() {
        return StreamEx.of(
                this.methodSourceFactory.createMutableMethod(this.name)
                    .override()
                    .returnInstanceField(this.type, this.name)
        );
    }

    @Override
    public StreamEx<MethodSource> visitConventionType(
            final ConventionTypeElement element, final P parameter) {
        return createMethod();
    }

    @Override
    public String toString() {
        return "GetterMethodVisitor{" +
                "name=" + this.name +
                ", type=" + this.type +
                "}";
    }

}
