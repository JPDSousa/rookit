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
package org.rookit.guice.auto.module;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceFactory;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.field.FieldSourceFactory;
import org.rookit.auto.source.type.reference.From;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.utils.guice.Separator;

import java.util.Collection;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.lang.String.format;
import static java.util.Arrays.fill;

final class ModuleFieldFactoryImpl implements ModuleFieldFactory {

    private final FieldSourceFactory fieldFactory;
    private final ArbitraryCodeSourceFactory codeFactory;
    private final TypeReferenceSource guiceModules;
    private final TypeReferenceSource guiceModule;
    private final String separator;

    @Inject
    private ModuleFieldFactoryImpl(
            final FieldSourceFactory fieldFactory,
            final ArbitraryCodeSourceFactory codeFactory,
            @From(Modules.class) final TypeReferenceSource guiceModules,
            @From(Module.class) final TypeReferenceSource guiceModule,
            @Separator final String separator) {
        this.fieldFactory = fieldFactory;
        this.codeFactory = codeFactory;
        this.guiceModules = guiceModules;
        this.guiceModule = guiceModule;
        this.separator = separator;
    }

    @Override
    public FieldSource combine(final Collection<TypeReferenceSource> moduleReferences) {

        return this.fieldFactory.createMutable(this.guiceModule, "MODULE")
                .makePrivate()
                .makeStatic()
                .makeFinal()
                .initializer(initializer(moduleReferences));
    }

    private ArbitraryCodeSource initializer(final Collection<TypeReferenceSource> moduleReferences) {

        // TODO should we change the design here to consider the first field separately?
        if (moduleReferences.isEmpty()) {
            return this.codeFactory.createFromFormat("$T.combine()", ImmutableList.of(this.guiceModules));
        }
        if (moduleReferences.size() == 1) {
            final TypeReferenceSource referenceName = getOnlyElement(moduleReferences);
            return this.codeFactory.createFromFormat("$T.combine(new $T())",
                                                     ImmutableList.of(this.guiceModules, referenceName));
        }

        final String initializer = format("$T.combine(new $T(),%s%s)", this.separator, combineBody(moduleReferences));

        return this.codeFactory.createFromFormat(
                initializer,
                ImmutableList.builder()
                        .addAll(moduleReferences)
                        .add(this.guiceModules)
                        .build()
        );
    }

    private String combineBody(final Collection<TypeReferenceSource> moduleReferences) {

        final String[] combines = new String[moduleReferences.size() - 1];
        fill(combines, "$T.getModule()");
        return String.join("," + this.separator, combines);
    }

}
