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
package org.rookit.auto.javax.visitor;

import com.google.inject.Provider;
import one.util.streamex.StreamEx;

import javax.lang.model.element.Name;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Function;

public interface ExtendedElementVisitors {

    <P> ExtendedElementVisitor<Boolean, P> isPresent(Class<? extends Annotation> annotationClass);

    <R, P> ExtendedElementVisitor<StreamEx<R>, P> emptyStreamVisitor();

    <P> ExtendedElementVisitor<Name, P> qualifiedNameVisitor();

    <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            V visitor,
            Function<ExtendedElementVisitor<R, P>, V> downcastAdapter);

    <B extends GenericBuilder<B, V, R, P>, V extends ExtendedElementVisitor<R, P>, R, P> B builder(
            Provider<V> visitor,
            Function<ExtendedElementVisitor<R, P>, V> downcastAdapter);

    <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P> streamExBuilder(
            V visitor,
            Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter);

    default <R, P> StreamExBuilder<ExtendedElementVisitor<StreamEx<R>, P>, R, P> streamExBuilder(
            final ExtendedElementVisitor<StreamEx<R>, P> visitor) {
        return streamExBuilder(visitor, element -> element);
    }

    <V extends ExtendedElementVisitor<StreamEx<R>, P>, R, P> StreamExBuilder<V, R, P>
    streamExBuilder(
            Iterable<? extends V> visitors,
            Function<ExtendedElementVisitor<StreamEx<R>, P>, V> downcastAdapter);

    default <R, P> StreamExBuilder<ExtendedElementVisitor<StreamEx<R>, P>, R, P> streamExBuilder(
            final Iterable<? extends ExtendedElementVisitor<StreamEx<R>, P>> visitors) {
        return streamExBuilder(visitors, element -> element);
    }

    <R, P> ExtendedElementVisitor<StreamEx<R>, P> streamEx(Collection<? extends ExtendedElementVisitor<R, P>> visitors);

    <R, P> ExtendedElementVisitor<StreamEx<R>, P> streamEx(ExtendedElementVisitor<R, P> visitor);


}
