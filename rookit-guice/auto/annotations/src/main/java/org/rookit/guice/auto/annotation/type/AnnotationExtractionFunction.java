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
package org.rookit.guice.auto.annotation.type;

import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElement;
import org.rookit.auto.javax.guice.QualifiedName;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.ExtendedTypeElementFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.guice.auto.annotation.BindingAnnotationGenerator;
import org.rookit.utils.primitive.VoidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.Objects;
import java.util.function.Function;

import static com.google.auto.common.MoreTypes.asTypeElement;

final class AnnotationExtractionFunction implements Function<ExtendedElement, ExtendedTypeElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AnnotationExtractionFunction.class);

    private final Elements elements;
    private final ExtendedElementVisitor<Name, Void> qualifiedNameVisitor;
    private final VoidUtils voidUtils;
    private final ExtendedTypeElementFactory typeFactory;

    @Inject
    private AnnotationExtractionFunction(
            final Elements elements,
            @QualifiedName final ExtendedElementVisitor<Name, Void> qualifiedNameVisitor,
            final VoidUtils voidUtils,
            final ExtendedTypeElementFactory typeFactory) {
        this.elements = elements;
        this.qualifiedNameVisitor = qualifiedNameVisitor;
        this.voidUtils = voidUtils;
        this.typeFactory = typeFactory;
    }


    @Override
    public ExtendedTypeElement apply(final ExtendedElement element) {
        final BindingAnnotationGenerator annotation = element.getAnnotation(BindingAnnotationGenerator.class);
        if (Objects.isNull(annotation)) {
            final Name elementName = element.accept(this.qualifiedNameVisitor, this.voidUtils.returnVoid());
            final String errMsg = String.format("%s is not annotated with %s", elementName,
                    BindingAnnotationGenerator.class);
            throw new IllegalArgumentException(errMsg);
        }

        return this.typeFactory.extend(typeElementFrom(annotation));
    }

    private TypeElement typeElementFrom(final BindingAnnotationGenerator annotation) {
        try {
            return this.elements.getTypeElement(annotation.copyBodyFrom().getCanonicalName());
        } catch (final MirroredTypeException e) {
            logger.debug("Caught {}. Using embedded {}", e.getClass(), TypeMirror.class);
            return asTypeElement(e.getTypeMirror());
        }
    }

    @Override
    public String toString() {
        return "AnnotationExtractionFunction{" +
                "elements=" + this.elements +
                ", qualifiedNameVisitor=" + this.qualifiedNameVisitor +
                ", voidUtils=" + this.voidUtils +
                ", typeFactory=" + this.typeFactory +
                "}";
    }

}
