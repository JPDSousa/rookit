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
package org.rookit.auto.javax.repetition.collection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.commons.collections4.Bag;
import org.rookit.auto.javax.repetition.ImmutableTypeMirrorRepetitionConfig;
import org.rookit.auto.javax.repetition.TypeMirrorRepetitionConfig;
import org.rookit.auto.javax.ElementUtils;
import org.rookit.utils.guice.Multi;
import org.rookit.utils.repetition.Repetition;

final class ApacheBagProvider implements Provider<TypeMirrorRepetitionConfig> {

    private final ElementUtils elementUtils;
    private final Repetition repetition;

    @Inject
    private ApacheBagProvider(final ElementUtils elementUtils,
                              @Multi final Repetition repetition) {
        this.elementUtils = elementUtils;
        this.repetition = repetition;
    }

    @Override
    public TypeMirrorRepetitionConfig get() {
        return ImmutableTypeMirrorRepetitionConfig.builder()
                .typeMirror(this.elementUtils.fromClassErasured(Bag.class))
                .repetition(this.repetition)
                .valueIndex(0)
                .build();
    }

    @Override
    public String toString() {
        return "ApacheBagProvider{" +
                "elementUtils=" + this.elementUtils +
                ", repetition=" + this.repetition +
                "}";
    }
}
