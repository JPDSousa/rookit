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
package org.rookit.auto.javax.runtime.element.node;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.executable.ExecutableElementFactory;
import org.rookit.auto.javax.runtime.element.pack.PackageElementFactory;
import org.rookit.auto.javax.runtime.element.type.TypeElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeConstructorEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityVisitor;
import org.rookit.auto.javax.runtime.entity.RuntimeEnumEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeExecutableEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeMethodEntity;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeParameterEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeVariableEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;

final class EnclosingElementVisitor implements RuntimeEntityVisitor<Maybe<Element>, Void> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(EnclosingElementVisitor.class);

    private final TypeElementFactory typeElementFactory;
    private final ExecutableElementFactory executableFactory;
    private final PackageElementFactory packageFactory;

    @Inject
    private EnclosingElementVisitor(
            final TypeElementFactory typeElementFactory,
            final ExecutableElementFactory executableFactory,
            final PackageElementFactory packageFactory) {
        this.typeElementFactory = typeElementFactory;
        this.executableFactory = executableFactory;
        this.packageFactory = packageFactory;
    }

    private Maybe<Element> createFromEnclosingConstructor(final RuntimeClassEntity clazz) {
        return clazz.enclosingConstructor()
                .map(Maybe::just)
                .orElse(Maybe.empty())
                .doOnSuccess(constructor -> logger.trace("Enclosing element of '{}' is a constructor", clazz))
                .flatMapSingleElement(this.executableFactory::createElement);
    }

    private Maybe<Element> createFromEnclosingMethod(final RuntimeClassEntity clazz) {
        return clazz.enclosingMethod()
                .map(Maybe::just)
                .orElse(Maybe.empty())
                .doOnSuccess(method -> logger.trace("Enclosing element of '{}' is method '{}'", clazz, method.name()))
                .flatMapSingleElement(this.executableFactory::createElement);
    }

    private Maybe<Element> createFromEnclosingClass(final RuntimeClassEntity clazz) {
        // TODO handle security exception
        return clazz.enclosingClass()
                .map(Maybe::just)
                .orElse(Maybe.empty())
                .doOnSuccess(enclosing -> logger.trace("Enclosing element of '{}' is class '{}'", clazz, enclosing))
                .flatMapSingleElement(this.typeElementFactory::createElement);
    }

    private Single<Element> createFromPackage(final RuntimeClassEntity clazz) {

        return Single.just(clazz.packageEntity())
                .doOnSuccess(enclosing -> logger.trace("Enclosing element of '{}' is package '{}'", clazz, enclosing))
                .flatMap(this.packageFactory::createElement);
    }

    @Override
    public Maybe<Element> visitClass(final RuntimeClassEntity clazz, final Void parameter) {
        return createFromEnclosingConstructor(clazz)
                .doOnDispose(() -> logger.trace("Enclosing element is not a constructor"))
                .switchIfEmpty(createFromEnclosingMethod(clazz))
                .doOnDispose(() -> logger.trace("Enclosing element is not a method"))
                .switchIfEmpty(createFromEnclosingClass(clazz))
                .doOnDispose(() -> logger.trace("Enclosing element is not a class"))
                .switchIfEmpty(createFromPackage(clazz))
                .doOnDispose(() -> logger.trace("Enclosing element is not a package"))
                .toMaybe();
    }

    private Maybe<Element> visitExecutable(final RuntimeExecutableEntity executable) {
        return this.typeElementFactory.createElement(executable.declaringClass())
                .cast(Element.class)
                .toMaybe();
    }

    @Override
    public Maybe<Element> visitMethod(final RuntimeMethodEntity method, final Void parameter) {
        return visitExecutable(method);
    }

    @Override
    public Maybe<Element> visitConstructor(final RuntimeConstructorEntity constructor, final Void parameter) {
        return visitExecutable(constructor);
    }

    @Override
    public Maybe<Element> visitPackage(final RuntimePackageEntity pack, final Void parameter) {
        // Packages have no enclosing element
        return Maybe.empty();
    }

    @Override
    public Maybe<Element> visitTypeVariable(final RuntimeTypeVariableEntity typeVariable, final Void parameter) {
        // TODO implement me
        throw new UnsupportedOperationException("Implement me please");
    }

    @Override
    public Maybe<Element> visitParameter(final RuntimeParameterEntity reflectParameter, final Void parameter) {
        return this.executableFactory.createElement(reflectParameter.declaringExecutable())
                .cast(Element.class)
                .toMaybe();
    }

    @Override
    public Maybe<Element> visitEnum(final RuntimeEnumEntity enumeration, final Void parameter) {
        return this.typeElementFactory.createElement(enumeration.declaringClass())
                .cast(Element.class)
                .toMaybe();
    }

    @Override
    public Maybe<Element> visitField(final RuntimeFieldEntity field, final Void parameter) {
        return this.typeElementFactory.createElement(field.declaringClass())
                .cast(Element.class)
                .toMaybe();
    }

    @Override
    public String toString() {
        return "EnclosingElementVisitor{" +
                "typeElementFactory=" + this.typeElementFactory +
                ", executableFactory=" + this.executableFactory +
                ", packageFactory=" + this.packageFactory +
                "}";
    }

}
