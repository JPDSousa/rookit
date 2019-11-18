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
package org.rookit.auto.javax.runtime.mirror;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityVisitor;
import org.rookit.auto.javax.runtime.mirror.declared.DeclaredModule;
import org.rookit.auto.javax.runtime.mirror.executable.ExecutableModule;
import org.rookit.auto.javax.runtime.mirror.no.NoModule;
import org.rookit.auto.javax.runtime.mirror.variable.VariableModule;
import org.rookit.utils.optional.Optional;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

public final class MirrorModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new MirrorModule(),
            DeclaredModule.getModule(),
            ExecutableModule.getModule(),
            NoModule.getModule(),
            VariableModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private MirrorModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(new TypeLiteral<RuntimeEntityVisitor<Single<TypeMirror>, Void>>() {}).to(TypeMirrorVisitor.class)
                .in(Singleton.class);
        bind(TypeMirrorFactory.class).to(TypeMirrorFactoryImpl.class).in(Singleton.class);
        bind(new TypeLiteral<TypeVisitor<Optional<Element>, Void>>() {}).to(AsElementVisitor.class).in(Singleton.class);
    }

}
