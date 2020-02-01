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
package org.rookit.auto.javax;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Modules;
import org.rookit.auto.javax.executable.ExecutableModule;
import org.rookit.auto.javax.naming.NamingModule;
import org.rookit.auto.javax.pack.PackageModule;
import org.rookit.auto.javax.repetition.RepetitionModule;
import org.rookit.auto.javax.type.TypeModule;
import org.rookit.auto.javax.variable.VariableModule;
import org.rookit.auto.javax.visitor.VisitorModule;

import javax.lang.model.element.ElementVisitor;

public final class JavaxLibModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new JavaxLibModule(),
            ExecutableModule.getModule(),
            NamingModule.getModule(),
            PackageModule.getModule(),
            RepetitionModule.getModule(),
            TypeModule.getModule(),
            VariableModule.getModule(),
            VisitorModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private JavaxLibModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bind(ElementUtils.class).to(ElementUtilsImpl.class).in(Singleton.class);
        bind(ExtendedElementFactory.class).to(ExtendedElementFactoryImpl.class).in(Singleton.class);
        bind(new TypeLiteral<ElementVisitor<ExtendedElement, Void>>() {}).to(EffectiveExtendedElementVisitor.class)
                .in(Singleton.class);
    }

}
