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
package org.rookit.auto.javax.type;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.rookit.auto.javax.ElementUtils;
import org.rookit.auto.javax.repetition.GenericTypeMirrorConfig;
import org.rookit.auto.javax.repetition.GenericTypeMirrorRepetitionConfig;
import org.rookit.auto.javax.repetition.JavaxRepetitionFactory;
import org.rookit.auto.javax.repetition.RepetitiveTypeMirrorFactory;
import org.rookit.auto.javax.repetition.TypeMirrorKeyedRepetitionConfig;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.type.parameter.TypeParameterExtractor;
import org.rookit.utils.optional.OptionalFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;

import static java.lang.String.format;

final class BaseExtendedTypeMirrorFactory implements ExtendedTypeMirrorFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseExtendedTypeMirrorFactory.class);

    private final Types types;
    private final ElementUtils elementUtils;
    private final Provider<TypeParameterExtractor> extractor;
    private final OptionalFactory optionalFactory;
    private final RepetitiveTypeMirrorFactory repetitiveFactory;
    private final JavaxRepetitionFactory repetitionFactory;

    @Inject
    private BaseExtendedTypeMirrorFactory(
            final Types types,
            final ElementUtils elementUtils,
            final Provider<TypeParameterExtractor> extractor,
            final OptionalFactory optionalFactory,
            final RepetitiveTypeMirrorFactory repetitiveFactory,
            final JavaxRepetitionFactory repetitionFactory) {
        this.types = types;
        this.elementUtils = elementUtils;
        this.extractor = extractor;
        this.optionalFactory = optionalFactory;
        this.repetitiveFactory = repetitiveFactory;
        this.repetitionFactory = repetitionFactory;
    }


    @Override
    public ExtendedTypeMirror createWithErasure(final Class<?> clazz) {
        return createWithErasure(this.elementUtils.fromClassErasured(clazz));
    }

    @Override
    public ExtendedTypeMirror createWithErasure(final TypeMirror typeMirror) {
        return extend(this.types.erasure(typeMirror));
    }

    @Override
    public ExtendedTypeMirror extend(final TypeMirror typeMirror) {
        if (typeMirror.getKind() == TypeKind.ERROR) {
            final String errMsg = format("Type mirror is invalid: %s", typeMirror);
            throw new IllegalArgumentException(errMsg);
        }
        if (typeMirror instanceof ExtendedTypeMirror) {
            logger.debug("{} is already a {}. Bypassing creation.", typeMirror, ExtendedTypeMirror.class.getName());
            return (ExtendedTypeMirror) typeMirror;
        }

        final GenericTypeMirrorConfig typeConfig = this.repetitionFactory.fromTypeMirror(typeMirror);
        final ExtendedTypeMirror extendedTypeMirror = new ExtendedTypeMirrorImpl(typeMirror, this.types,
                this, this.extractor.get(),
                this.optionalFactory, typeConfig.repetition());

        // TODO indeed, there's a problem here!!!
        if (typeConfig instanceof TypeMirrorKeyedRepetitionConfig) {
            final TypeMirrorKeyedRepetitionConfig keyedConfig = (TypeMirrorKeyedRepetitionConfig) typeConfig;
            final List<? extends ExtendedTypeMirror> params = extendedTypeMirror.typeParameters();
            final ExtendedTypeMirror key = extend(keyedConfig.extractKey(params));
            return this.repetitiveFactory.createKeyed(extendedTypeMirror, key, keyedConfig.valueIndex());
        }
        if (typeConfig instanceof GenericTypeMirrorRepetitionConfig) {
            final GenericTypeMirrorRepetitionConfig repetitionConfig = (GenericTypeMirrorRepetitionConfig) typeConfig;
            return this.repetitiveFactory.create(extendedTypeMirror, repetitionConfig.valueIndex());
        }
        return extendedTypeMirror;
    }

    @Override
    public ExtendedTypeMirror createPrimitive(final TypeKind typeKind) {
        return extend(this.types.getPrimitiveType(typeKind));
    }

}
