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
package org.rookit.storage.naming;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.rookit.auto.javax.naming.IdentifierTransformer;
import org.rookit.auto.javax.naming.IdentifierTransformers;
import org.rookit.auto.javax.naming.MethodNameTransformer;
import org.rookit.auto.javax.naming.MethodNameTransformers;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.storage.api.config.FilterConfig;
import org.rookit.storage.api.config.QueryConfig;
import org.rookit.storage.api.config.UpdateConfig;
import org.rookit.storage.api.config.UpdateFilterConfig;
import org.rookit.storage.guice.ElementQuery;
import org.rookit.storage.guice.PartialQuery;
import org.rookit.storage.guice.PartialUpdate;
import org.rookit.storage.guice.PartialUpdateFilter;
import org.rookit.storage.guice.Query;
import org.rookit.storage.guice.Update;
import org.rookit.storage.guice.UpdateFilter;
import org.rookit.storage.guice.filter.Filter;
import org.rookit.storage.guice.filter.PartialFilter;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

@SuppressWarnings("MethodMayBeStatic")
public final class NamingModule extends AbstractModule {

    private static final Module MODULE = new NamingModule();

    public static Module getModule() {
        return MODULE;
    }

    private NamingModule() {}

    @Provides
    @Singleton
    @PartialFilter
    IdentifierTransformer partialFilterTransformer(final IdentifierTransformers transformers,
                                                   final FilterConfig config) {
        return transformers.fromFunctions(source -> source.resolve(config.basePackage()),
                                          config.entityTemplate()::build);
    }

    @Provides
    @Singleton
    @Filter
    IdentifierTransformer filterTransformer(final IdentifierTransformers transformers,
                                            final FilterConfig config) {
        return transformers.fromFunctions(source -> source.resolve(config.basePackage()),
                                          config.entityTemplate()::build);
    }

    @Singleton
    @Provides
    @PartialQuery
    ExtendedPackageElement queryPackage(final QueryConfig config) {
        return config.basePackage();
    }

    @Provides
    @Singleton
    @PartialQuery
    TypeVariableSource typeVariableName(final QueryConfig config) {
        return config.parameterName();
    }

    @Provides
    @Singleton
    @ElementQuery
    TypeVariableSource elementTypeVariableName(final QueryConfig config) {
        return config.elementParameterName();
    }

    @Singleton
    @Provides
    @PartialQuery
    MethodNameTransformer queryNamingFactory(final MethodNameTransformers factories, final QueryConfig config) {
        return factories.fromTemplate(config.methodTemplate());
    }

    @Singleton
    @Provides
    @Query
    MethodNameTransformer queryEntityNamingFactory(final MethodNameTransformers factories, final QueryConfig config) {
        // TODO should this have the exact same binding as the one above??????
        return factories.fromTemplate(config.methodTemplate());
    }

    @Singleton
    @Provides
    @PartialUpdateFilter
    MethodNameTransformer partialUpdateFilterNamingFactory(final MethodNameTransformers factories,
                                                           @Self final Template1 noopTemplate) {
        return factories.fromTemplate(noopTemplate);
    }

    @Singleton
    @Provides
    @UpdateFilter
    MethodNameTransformer updateFilterNamingFactory(final MethodNameTransformers factories,
                                                    @Self final Template1 noopTemplate) {
        return factories.fromTemplate(noopTemplate);
    }

    @Singleton
    @Provides
    @UpdateFilter
    ExtendedPackageElement updateFilterPackage(final UpdateFilterConfig config) {
        return config.basePackage();
    }

    @Provides
    @Singleton
    @PartialUpdateFilter
    TypeVariableSource parameterName(final UpdateFilterConfig config) {
        return config.parameterName();
    }

    @Provides
    @Singleton
    @Update
    MethodNameTransformer updateTransformer(final MethodNameTransformers transformers,
                                            final UpdateConfig config) {
        return transformers.fromTemplate(config.methodTemplate());
    }

    @Provides
    @Singleton
    @PartialUpdate
    MethodNameTransformer partialUpdateTransformer(final MethodNameTransformers transformers,
                                                   final UpdateConfig config) {
        return transformers.fromTemplate(config.methodTemplate());
    }

}
