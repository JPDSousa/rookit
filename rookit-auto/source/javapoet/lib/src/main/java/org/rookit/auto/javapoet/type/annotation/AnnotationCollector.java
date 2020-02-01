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
package org.rookit.auto.javapoet.type.annotation;

import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import one.util.streamex.EntryStream;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

final class AnnotationCollector implements Collector<Map.Entry<String, CodeBlock>, AnnotationSpec.Builder, AnnotationSpec> {

    private final ClassName annotationClass;

    AnnotationCollector(final ClassName annotationClass) {
        this.annotationClass = annotationClass;
    }

    @Override
    public Supplier<AnnotationSpec.Builder> supplier() {
        return () -> AnnotationSpec.builder(this.annotationClass);
    }

    @Override
    public BiConsumer<AnnotationSpec.Builder, Map.Entry<String, CodeBlock>> accumulator() {
        return (builder, member) -> builder.addMember(member.getKey(), member.getValue());
    }

    @Override
    public BinaryOperator<AnnotationSpec.Builder> combiner() {
        return AnnotationCollector::combine;
    }

    private static AnnotationSpec.Builder combine(
            final AnnotationSpec.Builder builder1,
            final AnnotationSpec.Builder builder2) {

        EntryStream.of(builder2.build().members)
                .flatMapValues(Collection::stream)
                .forKeyValue(builder1::addMember);

        return builder1;
    }

    @Override
    public Function<AnnotationSpec.Builder, AnnotationSpec> finisher() {
        return AnnotationSpec.Builder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of(CONCURRENT, UNORDERED);
    }

    @Override
    public String toString() {
        return "AnnotationCollector{" +
                "annotationClass=" + this.annotationClass +
                "}";
    }

}
