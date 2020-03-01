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
package org.rookit.storage.filter.source.method.type.collection;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.parameter.ParameterSourceFactory;
import org.rookit.convention.auto.metatype.javax.ConventionTypeElement;
import org.rookit.convention.auto.metatype.javax.visitor.ConventionTypeElementVisitor;
import org.rookit.convention.auto.metatype.source.method.ConventionTypeElementMethodSourceVisitors;
import org.rookit.storage.filter.source.guice.No;
import org.rookit.utils.adapt.Adapter;
import org.rookit.utils.guice.Collection;
import org.rookit.utils.guice.Self;
import org.rookit.utils.string.template.Template1;

final class CollectionMethodVisitor implements Provider<ConventionTypeElementVisitor<StreamEx<MethodSource>, Void>> {

    private final ParameterSourceFactory parameterFactory;
    private final ConventionTypeElementMethodSourceVisitors visitors;
    private final Adapter<ConventionTypeElement> collectionAdapter;
    private final Adapter<ConventionTypeElement> collectionUnwrapper;
    private final MethodSourceFactory methodFactory;
    private final Template1 notContainsTemplate;
    private final Template1 containsTemplate;

    @Inject
    private CollectionMethodVisitor(
            final ParameterSourceFactory parameterFactory,
            final ConventionTypeElementMethodSourceVisitors visitors,
            @Collection final Adapter<ConventionTypeElement> collectionAdapter,
            @Collection(unwrap = true) final Adapter<ConventionTypeElement> collectionUnwrapper,
            final MethodSourceFactory methodFactory,
            @No final Template1 notContainsTemplate,
            @Self final Template1 containsTemplate) {
        this.parameterFactory = parameterFactory;
        this.visitors = visitors;
        this.collectionAdapter = collectionAdapter;
        this.collectionUnwrapper = collectionUnwrapper;
        this.methodFactory = methodFactory;
        this.notContainsTemplate = notContainsTemplate;
        this.containsTemplate = containsTemplate;
    }

    @Override
    public ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> get() {

        // unwrap collections
        final ConventionTypeElementVisitor<StreamEx<MethodSource>, Void> baseVisitor = createBaseVisitor();
        return this.visitors.streamExConventionBuilder(baseVisitor)
                .withConventionTypeAdapter(this.collectionAdapter)
                .add(this.visitors.streamExConventionBuilder(baseVisitor)
                        .withConventionTypeAdapter(this.collectionUnwrapper)
                        .build())
                .build();
    }

    private <P> ConventionTypeElementVisitor<StreamEx<MethodSource>, P> createBaseVisitor() {
        return this.visitors.<P>templateMethodSourceVisitorBuilder(
                this.methodFactory,
                this.containsTemplate,
                property -> containsParam(property.type())
        ).add(
                this.visitors
                        .<P>templateMethodSourceVisitorBuilder(
                                this.methodFactory,
                                this.notContainsTemplate,
                                property -> notContainsParam(property.type())
                        ).build()
        ).build();
    }

    private java.util.Collection<ParameterSource> containsParam(final ExtendedTypeMirror typeMirror) {
        // TODO this name should be configurable
        return createParam(typeMirror, "present");
    }

    private java.util.Collection<ParameterSource> notContainsParam(final ExtendedTypeMirror typeMirror) {
        // TODO this name should be configurable
        return createParam(typeMirror, "absent");
    }

    private java.util.Collection<ParameterSource> createParam(final ExtendedTypeMirror typeMirror,
                                                              final CharSequence paramName) {
        return ImmutableList.of(
                this.parameterFactory.createMutable(paramName, typeMirror)
        );
    }

    @Override
    public String toString() {
        return "CollectionMethodVisitor{" +
                "parameterFactory=" + this.parameterFactory +
                ", visitors=" + this.visitors +
                ", collectionAdapter=" + this.collectionAdapter +
                ", collectionUnwrapper=" + this.collectionUnwrapper +
                ", methodFactory=" + this.methodFactory +
                ", notContainsTemplate=" + this.notContainsTemplate +
                ", containsTemplate=" + this.containsTemplate +
                "}";
    }

}
