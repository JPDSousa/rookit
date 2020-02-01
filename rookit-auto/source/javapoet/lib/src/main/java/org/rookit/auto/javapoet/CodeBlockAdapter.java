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
package org.rookit.auto.javapoet;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import one.util.streamex.EntryStream;
import org.rookit.auto.source.CodeSource;
import org.rookit.auto.source.TypeSourceContainer;
import org.rookit.auto.source.CodeSourceVisitor;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSource;
import org.rookit.auto.source.arbitrary.ArbitraryCodeSourceAdapter;
import org.rookit.auto.source.field.FieldSource;
import org.rookit.auto.source.method.MethodSource;
import org.rookit.auto.source.parameter.ParameterSource;
import org.rookit.auto.source.type.TypeSourceAdapter;
import org.rookit.auto.source.type.TypeSource;
import org.rookit.auto.source.type.annotation.AnnotationSource;
import org.rookit.auto.source.type.reference.TypeReferenceSourceAdapter;
import org.rookit.auto.source.type.reference.TypeReferenceSource;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.WildcardVariableSource;
import org.rookit.utils.guice.Separator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.Collector;

import static com.squareup.javapoet.CodeBlock.join;
import static com.squareup.javapoet.CodeBlock.joining;
import static java.util.stream.Collectors.collectingAndThen;
import static org.apache.commons.lang3.StringUtils.EMPTY;

final class CodeBlockAdapter implements CodeSourceVisitor<CodeBlock, Void> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CodeBlockAdapter.class);

    private final TypeSourceAdapter<TypeSpec> typeAdapter;
    private final TypeReferenceSourceAdapter<TypeName> referenceAdapter;
    private final ArbitraryCodeSourceAdapter<CodeBlock> codeSourceAdapter;
    @SuppressWarnings("FieldNotUsedInToString")
    private final String separator;

    @Inject
    private CodeBlockAdapter(
            final TypeSourceAdapter<TypeSpec> typeAdapter,
            final TypeReferenceSourceAdapter<TypeName> referenceAdapter,
            final ArbitraryCodeSourceAdapter<CodeBlock> codeSourceAdapter,
            @Separator final String separator) {
        this.typeAdapter = typeAdapter;
        this.referenceAdapter = referenceAdapter;
        this.codeSourceAdapter = codeSourceAdapter;
        this.separator = separator;
    }

    @Override
    public CodeBlock visitType(final TypeSource source, final Void parameter) {

        return CodeBlock.of("$L", this.typeAdapter.adaptTypeSource(source));
    }

    @Override
    public CodeBlock visitMethod(final MethodSource source, final Void parameter) {

        logger.warn("Cannot create code blocks from methods");
        return CodeBlock.builder().build();
    }

    @Override
    public CodeBlock visitAnnotation(final AnnotationSource source, final Void parameter) {

        return EntryStream.of(source.members())
                .map(member -> createMemberBlock(member, parameter))
                .collect(createAnnoration(source));
    }

    private Collector<CodeBlock, ?, CodeBlock> createAnnoration(final AnnotationSource annotation) {
        return collectingAndThen(
                joining("," + this.separator),
                members -> createAnnotationBlock(annotation, members)
        );
    }

    private CodeBlock createAnnotationBlock(final AnnotationSource annotation, final CodeBlock members) {
        final TypeName annotationReference = this.referenceAdapter.adaptTypeReference(annotation.reference());
        return join(ImmutableList.of(
                CodeBlock.of("@$T(", annotationReference),
                members,
                CodeBlock.of(")")
        ), EMPTY);
    }

    private CodeBlock createMemberBlock(final Map.Entry<String, CodeSource> member, final Void parameter) {
        return join(ImmutableList.of(
                CodeBlock.of("$L", member.getKey()),
                member.getValue().accept(this, parameter)
        ), "=");
    }

    @Override
    public CodeBlock visitTypeVariable(final TypeVariableSource source, final Void parameter) {

        return CodeBlock.of("&L", source.name());
    }

    @Override
    public CodeBlock visitParameter(final ParameterSource source, final Void parameter) {

        return CodeBlock.of("&L", source.name());
    }

    @Override
    public CodeBlock visitTypeContainer(final TypeSourceContainer<?> container, final Void parameter) {

        return container.stream()
                .map(contained -> contained.accept(this, parameter))
                .collect(joining(";"));
    }

    @Override
    public CodeBlock visitWildcard(
            final WildcardVariableSource wildcard, final Void parameter) {

        return CodeBlock.of("*");
    }

    @Override
    public CodeBlock visitReference(
            final TypeReferenceSource reference, final Void parameter) {

        return CodeBlock.of("&T", this.referenceAdapter.adaptTypeReference(reference));
    }

    @Override
    public CodeBlock visitField(final FieldSource fieldSource, final Void parameter) {

        return CodeBlock.of("this.&L", fieldSource.name());
    }

    @Override
    public CodeBlock visitArbitraryCode(
            final ArbitraryCodeSource codeBlock, final Void parameter) {
        return this.codeSourceAdapter.adaptArbitraryCodeBlock(codeBlock);
    }

    @Override
    public String toString() {
        return "CodeBlockAdapter{" +
                "typeAdapter=" + this.typeAdapter +
                ", referenceAdapter=" + this.referenceAdapter +
                "}";
    }

}
