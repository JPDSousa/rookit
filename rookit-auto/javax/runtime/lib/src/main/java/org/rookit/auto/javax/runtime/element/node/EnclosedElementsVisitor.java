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
import io.reactivex.Observable;
import org.reflections.Reflections;
import org.rookit.auto.javax.runtime.element.executable.ExecutableElementFactory;
import org.rookit.auto.javax.runtime.element.type.RuntimeTypeElementFactory;
import org.rookit.auto.javax.runtime.element.variable.RuntimeVariableElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeConstructorEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeEntityFactory;
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

final class EnclosedElementsVisitor implements RuntimeEntityVisitor<Observable<? extends Element>, Void> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(EnclosedElementsVisitor.class);

    private final ExecutableElementFactory executableFactory;
    private final RuntimeVariableElementFactory variableFactory;
    private final RuntimeTypeElementFactory typeElementFactory;
    private final RuntimeEntityFactory entityFactory;

    @Inject
    private EnclosedElementsVisitor(
            final ExecutableElementFactory executableFactory,
            final RuntimeVariableElementFactory variableFactory,
            final RuntimeTypeElementFactory typeElementFactory,
            final RuntimeEntityFactory entityFactory) {
        this.executableFactory = executableFactory;
        this.variableFactory = variableFactory;
        this.typeElementFactory = typeElementFactory;
        this.entityFactory = entityFactory;
    }

    private Observable<? extends Element> createEnclosedMethods(final RuntimeClassEntity clazz) {
        return Observable.fromIterable(clazz.declaredMethods())
                .doOnNext(method -> logger.trace("Creating runtime element for method '{}'", method))
                .flatMapSingle(this.executableFactory::createElement)
                .doOnNext(method -> logger.trace("Created runtime executable element '{}'", method.getSimpleName()));
    }

    private Observable<? extends Element> createEnclosedFields(final RuntimeClassEntity clazz) {
        return Observable.fromIterable(clazz.declaredFields())
                .doOnNext(field -> logger.trace("Creating runtime element for field '{}'", field))
                .flatMapSingle(this.variableFactory::createFromField)
                .doOnNext(field -> logger.trace("Created runtime variable element '{}'", field.getSimpleName()));
    }

    private Observable<? extends Element> createEnclosedConstructors(final RuntimeClassEntity clazz) {
        return Observable.fromIterable(clazz.declaredConstructors())
                .doOnNext(constructor -> logger.trace("Creating runtime element for constructor '{}'", constructor))
                .flatMapSingle(this.executableFactory::createElement)
                .doOnNext(constructor -> logger.trace("Created runtime executable element '{}'",
                                                  constructor.getSimpleName()));
    }

    private Observable<? extends Element> createEnclosedClasses(final RuntimeClassEntity clazz) {
        return Observable.fromIterable(clazz.declaredClasses())
                .doOnNext(enclosedClass -> logger.trace("Creating runtime element for enclosed class '{}'",
                                                    enclosedClass))
                .flatMapSingle(this.typeElementFactory::createElement)
                .doOnNext(enclosedClass -> logger.trace("Created runtime type element '{}'", enclosedClass));
    }

    @Override
    public Observable<? extends Element> visitClass(final RuntimeClassEntity entity, final Void parameter) {
        return Observable.concatArray(
                createEnclosedClasses(entity),
                createEnclosedConstructors(entity),
                createEnclosedFields(entity),
                createEnclosedMethods(entity)
        );
    }

    private Observable<? extends Element> visitExecutable(final RuntimeExecutableEntity entity) {
        return Observable.fromIterable(entity.parameters())
                .doOnNext(parameter -> logger.trace("Creating runtime element for parameter '{}'", parameter))
                .flatMapSingle(this.variableFactory::createFromParameter)
                .doOnNext(parameter -> logger.trace("Created parameter type element '{}'", parameter.getSimpleName()));
    }

    @Override
    public Observable<? extends Element> visitMethod(final RuntimeMethodEntity method, final Void parameter) {
        return visitExecutable(method);
    }

    @Override
    public Observable<? extends Element> visitConstructor(final RuntimeConstructorEntity constructor,
                                                          final Void parameter) {
        return visitExecutable(constructor);
    }

    @Override
    public Observable<? extends Element> visitPackage(
            final RuntimePackageEntity pack,
            final Void parameter) {

        final Reflections reflectPackage = new Reflections(pack.pack()
                                                                   .getName());

        return Observable.fromIterable(reflectPackage.getSubTypesOf(Object.class))
                .map(this.entityFactory::fromClass)
                .doOnNext(classInPackage -> logger.trace("Creating runtime element for package in class '{}'",
                                                         classInPackage))
                .flatMapSingle(this.typeElementFactory::createElement)
                .doOnNext(classInPackage -> logger.trace("Created runtime type element '{}'",
                                                         classInPackage.getSimpleName()));
    }

    @Override
    public Observable<? extends Element> visitTypeVariable(final RuntimeTypeVariableEntity typeVariable,
                                                           final Void parameter) {
        // type variables have no enclosed elements
        return Observable.empty();
    }

    @Override
    public Observable<? extends Element> visitParameter(final RuntimeParameterEntity reflectParameter,
                                                        final Void parameter) {
        // parameters have no enclosed elements
        return Observable.empty();
    }

    @Override
    public Observable<? extends Element> visitEnum(final RuntimeEnumEntity enumeration, final Void parameter) {
        // enums have no enclosed elements
        return Observable.empty();
    }

    @Override
    public Observable<? extends Element> visitField(final RuntimeFieldEntity field, final Void parameter) {
        // fields have no enclosed elements
        return Observable.empty();
    }

}
