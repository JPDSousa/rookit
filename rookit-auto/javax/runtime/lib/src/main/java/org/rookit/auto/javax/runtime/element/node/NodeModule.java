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
package org.rookit.auto.javax.runtime.element.node;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityVisitor;

import javax.lang.model.element.Element;

public final class NodeModule extends AbstractModule {

    private static final Module MODULE = new NodeModule();

    public static Module getModule() {
        return MODULE;
    }

    private NodeModule() {}


    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(NodeElementFactory.class).to(NodeElementFactoryImpl.class).in(Singleton.class);
        bind(new TypeLiteral<RuntimeEntityVisitor<Maybe<Element>, Void>>() {}).to(EnclosingElementVisitor.class)
                .in(Singleton.class);
        bind(new TypeLiteral<RuntimeEntityVisitor<Observable<? extends Element>, Void>>() {})
                .to(EnclosedElementsVisitor.class).in(Singleton.class);
    }

}
