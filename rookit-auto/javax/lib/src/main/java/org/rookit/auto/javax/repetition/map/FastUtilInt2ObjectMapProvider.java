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
package org.rookit.auto.javax.repetition.map;

import com.google.inject.Inject;
import com.google.inject.Provider;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.rookit.auto.javax.repetition.ImmutableTypeMirrorKeyedRepetitionTypeConfig;
import org.rookit.auto.javax.repetition.TypeMirrorKeyedRepetitionConfig;
import org.rookit.auto.javax.ElementUtils;
import org.rookit.utils.guice.Keyed;
import org.rookit.utils.repetition.Repetition;

import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Types;

final class FastUtilInt2ObjectMapProvider implements Provider<TypeMirrorKeyedRepetitionConfig> {

    private final ElementUtils elementUtils;
    private final Types types;
    private final Repetition repetition;

    @Inject
    private FastUtilInt2ObjectMapProvider(final ElementUtils elementUtils,
                                          final Types types,
                                          @Keyed final Repetition repetition) {
        this.elementUtils = elementUtils;
        this.types = types;
        this.repetition = repetition;
    }

    @Override
    public TypeMirrorKeyedRepetitionConfig get() {
        return ImmutableTypeMirrorKeyedRepetitionTypeConfig.builder()
                .repetition(this.repetition)
                .key(this.types.getPrimitiveType(TypeKind.INT))
                .valueIndex(1)
                .typeMirror(this.elementUtils.fromClassErasured(Int2ObjectMap.class))
                .build();
    }

    @Override
    public String toString() {
        return "FastUtilInt2ObjectMapProvider{" +
                "elementUtils=" + this.elementUtils +
                ", types=" + this.types +
                ", repetition=" + this.repetition +
                "}";
    }
}
