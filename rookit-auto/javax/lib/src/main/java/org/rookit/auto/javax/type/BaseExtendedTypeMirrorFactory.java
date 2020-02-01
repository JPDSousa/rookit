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

import javax.annotation.processing.Messager;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.List;

import static java.lang.String.format;

public final class BaseExtendedTypeMirrorFactory implements ExtendedTypeMirrorFactory {

    public static ExtendedTypeMirrorFactory create(final Types types,
                                                   final Messager messager,
                                                   final ElementUtils elementUtils,
                                                   final Provider<TypeParameterExtractor> extractor,
                                                   final OptionalFactory optionalFactory,
                                                   final RepetitiveTypeMirrorFactory repetitiveFactory,
                                                   final JavaxRepetitionFactory repetitionFactory) {
        return new BaseExtendedTypeMirrorFactory(types, elementUtils, messager, extractor,
                optionalFactory, repetitiveFactory, repetitionFactory);
    }

    private final Types types;
    private final ElementUtils elementUtils;
    private final Messager messager;
    private final Provider<TypeParameterExtractor> extractor;
    private final OptionalFactory optionalFactory;
    private final RepetitiveTypeMirrorFactory repetitiveFactory;
    private final JavaxRepetitionFactory repetitionFactory;

    @Inject
    private BaseExtendedTypeMirrorFactory(final Types types,
                                          final ElementUtils elementUtils,
                                          final Messager messager,
                                          final Provider<TypeParameterExtractor> extractor,
                                          final OptionalFactory optionalFactory,
                                          final RepetitiveTypeMirrorFactory repetitiveFactory,
                                          final JavaxRepetitionFactory repetitionFactory) {
        this.types = types;
        this.elementUtils = elementUtils;
        this.messager = messager;
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
            this.messager.printMessage(Diagnostic.Kind.ERROR, errMsg);
            throw new IllegalArgumentException(errMsg);
        }
        if (typeMirror instanceof ExtendedTypeMirror) {
            final String errMsg = format("%s is already a %s. Bypassing creation.", typeMirror,
                    ExtendedTypeMirror.class.getName());
            this.messager.printMessage(Diagnostic.Kind.NOTE, errMsg);
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

    @Override
    public String toString() {
        return "BaseExtendedTypeMirrorFactory{" +
                "types=" + this.types +
                ", elementUtils=" + this.elementUtils +
                ", messager=" + this.messager +
                ", extractor=" + this.extractor +
                ", optionalFactory=" + this.optionalFactory +
                ", repetitiveFactory=" + this.repetitiveFactory +
                ", repetitionFactory=" + this.repetitionFactory +
                "}";
    }
}
