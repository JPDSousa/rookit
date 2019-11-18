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
package org.rookit.auto.javax.runtime;

import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.element.pack.RuntimePackageElement;
import org.rookit.auto.javax.runtime.element.type.RuntimeTypeElement;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityFactory;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

final class RuntimeElements implements Elements {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeElements.class);

    private final Types types;
    private final RuntimeGenericElementFactory<RuntimePackageEntity, RuntimePackageElement> packageFactory;
    private final RuntimeGenericElementFactory<RuntimeClassEntity, RuntimeTypeElement> typeFactory;
    private final Failsafe failsafe;
    private final RuntimeEntityFactory entityFactory;
    private final NameFactory nameFactory;

    @Inject
    private RuntimeElements(
            final Types types,
            final RuntimeGenericElementFactory<RuntimePackageEntity, RuntimePackageElement> packageFactory,
            final RuntimeGenericElementFactory<RuntimeClassEntity, RuntimeTypeElement> typeFactory,
            final Failsafe failsafe,
            final RuntimeEntityFactory entityFactory,
            final NameFactory nameFactory) {
        this.types = types;
        this.packageFactory = packageFactory;
        this.typeFactory = typeFactory;
        this.failsafe = failsafe;
        this.entityFactory = entityFactory;
        this.nameFactory = nameFactory;
    }

    @Override
    public PackageElement getPackageElement(final CharSequence fqdn) {
        final RuntimePackageEntity entity = this.entityFactory.fromPackage(Package.getPackage(fqdn.toString()));
        return this.packageFactory.createElement(entity)
                .blockingGet();
    }

    @Override
    public TypeElement getTypeElement(final CharSequence name) {
        try {
            final RuntimeClassEntity entity = this.entityFactory.fromClass(Class.forName(name.toString()));
            return this.typeFactory.createElement(entity)
                    .blockingGet();
        } catch (final ClassNotFoundException e) {
            logger.warn("Could not find class.", e);
            // in order to comply with the interface contract.
            return null;
        }
    }

    @Override
    public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults(
            final AnnotationMirror annotation) {
        // TODO not sure what to do here.
        return null;
    }

    @Override
    public String getDocComment(final Element e) {
        logger.warn("Cannot access docs in runtime.");
        // returning null to comply with interface contract
        return null;
    }

    @Override
    public boolean isDeprecated(final Element e) {
        return nonNull(e.getAnnotation(Deprecated.class));
    }

    @Override
    public Name getBinaryName(final TypeElement type) {
        // TODO not sure about this
        return type.getQualifiedName();
    }

    @Override
    public PackageElement getPackageOf(final Element type) {
        this.failsafe.checkArgument().isNotNull(logger, type, "type");
        if (type instanceof PackageElement) {
            logger.trace("The provided argument is already a package.");
            return (PackageElement) type;
        }
        return getPackageOf(type.getEnclosingElement());
    }

    @Override
    public List<? extends Element> getAllMembers(final TypeElement type) {
        final StreamEx<? extends Element> elements = interfaceMembers(type);
        return StreamEx.<Element>of(type.getEnclosedElements())
                .append(elements)
                .collect(Collectors.toList());
    }

    private StreamEx<? extends Element> interfaceMembers(final TypeElement typeElement) {
        return StreamEx.of(typeElement.getInterfaces())
                .map(this.types::asElement)
                .select(TypeElement.class)
                .map(this::getAllMembers)
                .flatMap(Collection::stream);
    }

    @Override
    public List<? extends AnnotationMirror> getAllAnnotationMirrors(final Element e) {
        return null;
    }

    @Override
    public boolean hides(final Element hider, final Element hidden) {
        return false;
    }

    @Override
    public boolean overrides(final ExecutableElement overrider,
                             final ExecutableElement overridden,
                             final TypeElement type) {
        return false;
    }

    @Override
    public String getConstantExpression(final Object value) {
        return String.valueOf(value);
    }

    @Override
    public void printElements(final Writer w, final Element... elements) {
        for (final Element element : elements) {
            try {
                w.write(element.getSimpleName().toString());
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Name getName(final CharSequence cs) {
        return this.nameFactory.create(cs);
    }

    @Override
    public boolean isFunctionalInterface(final TypeElement type) {
        return nonNull(type.getAnnotation(FunctionalInterface.class));
    }

    @Override
    public String toString() {
        return "RuntimeElements{" +
                "types=" + this.types +
                ", packageFactory=" + this.packageFactory +
                ", typeFactory=" + this.typeFactory +
                ", failsafe=" + this.failsafe +
                ", entityFactory=" + this.entityFactory +
                ", nameFactory=" + this.nameFactory +
                "}";
    }

}
