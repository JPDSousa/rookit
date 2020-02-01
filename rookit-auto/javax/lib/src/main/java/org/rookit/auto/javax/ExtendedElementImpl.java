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
package org.rookit.auto.javax;

import com.google.inject.Provider;
import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.javax.pack.ExtendedPackageElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirror;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.auto.javax.visitor.ExtendedElementVisitor;
import org.rookit.utils.primitive.VoidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class ExtendedElementImpl implements ExtendedElement {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExtendedElementImpl.class);

    private final Elements elements;
    private final Element element;
    private final ExtendedTypeMirrorFactory mirrorFactory;
    private final Provider<ElementVisitor<ExtendedElement, Void>> effectiveTypeVisitor;
    private final VoidUtils voidUtils;
    private final Provider<ExtendedPackageElementFactory> packageFactory;

    ExtendedElementImpl(
            final Elements elements,
            final Element element,
            final ExtendedTypeMirrorFactory mirrorFactory,
            final Provider<ElementVisitor<ExtendedElement, Void>> effectiveTypeVisitor,
            final VoidUtils voidUtils,
            final Provider<ExtendedPackageElementFactory> packageFactory) {
        this.elements = elements;
        this.element = element;
        this.mirrorFactory = mirrorFactory;
        this.effectiveTypeVisitor = effectiveTypeVisitor;
        this.voidUtils = voidUtils;
        this.packageFactory = packageFactory;
    }

    @Override
    public ExtendedPackageElement packageInfo() {
        return this.packageFactory.get().extend(this.elements.getPackageOf(this.element));
    }

    @Override
    public ExtendedTypeMirror asType() {
        return this.mirrorFactory.extend(this.element.asType());
    }

    @Override
    public ElementKind getKind() {
        return this.element.getKind();
    }

    @Override
    public Set<Modifier> getModifiers() {
        return this.element.getModifiers();
    }

    @Override
    public Name getSimpleName() {
        return this.element.getSimpleName();
    }

    private ExtendedElement effectiveType(final Element element) {
        return element.accept(this.effectiveTypeVisitor.get(), this.voidUtils.returnVoid());
    }

    @Override
    public ExtendedElement getEnclosingElement() {
        return effectiveType(this.element.getEnclosingElement());
    }

    @Override
    public List<? extends ExtendedElement> getEnclosedElements() {
        return this.element.getEnclosedElements().stream()
                .map(this::effectiveType)
                .collect(toImmutableList());
    }

    @Override
    public <R, P> R accept(final ExtendedElementVisitor<R, P> visitor, final P parameter) {
        logger.warn("Visiting unknown for {}", this);
        return visitor.visitUnknown(this, parameter);
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return this.element.getAnnotationMirrors();
    }

    @Override
    public <A extends Annotation> A getAnnotation(final Class<A> annotationType) {
        return this.element.getAnnotation(annotationType);
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(final Class<A> annotationType) {
        return this.element.getAnnotationsByType(annotationType);
    }

    @Override
    public <R, P> R accept(final ElementVisitor<R, P> v, final P p) {
        return this.element.accept(v, p);
    }

    @Override
    public String toString() {
        return "ExtendedElementImpl{" +
                "elements=" + this.elements +
                ", element=" + this.element +
                ", mirrorFactory=" + this.mirrorFactory +
                ", effectiveTypeVisitor=" + this.effectiveTypeVisitor +
                ", voidUtils=" + this.voidUtils +
                ", packageFactory=" + this.packageFactory +
                "}";
    }

}
