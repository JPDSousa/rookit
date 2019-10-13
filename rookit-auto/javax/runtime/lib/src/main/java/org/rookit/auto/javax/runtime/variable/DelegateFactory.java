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
package org.rookit.auto.javax.runtime.variable;

import com.google.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.element.RuntimeGenericElementFactory;
import org.rookit.auto.javax.runtime.element.variable.RuntimeVariableElementFactory;
import org.rookit.auto.javax.runtime.element.variable.RuntimeVariableElement;
import org.rookit.auto.javax.runtime.entity.RuntimeEnumEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeExecutableEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeParameterEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.VariableElement;

final class DelegateFactory implements RuntimeVariableElementFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(DelegateFactory.class);

    private final RuntimeGenericElementFactory<RuntimeEnumEntity, RuntimeVariableElement> enumFactory;
    private final RuntimeGenericElementFactory<RuntimeFieldEntity, RuntimeVariableElement> fieldFactory;
    private final RuntimeGenericElementFactory<RuntimeParameterEntity, RuntimeVariableElement> parameterFactory;

    @Inject
    private DelegateFactory(
            final RuntimeGenericElementFactory<RuntimeEnumEntity, RuntimeVariableElement> enumFactory,
            final RuntimeGenericElementFactory<RuntimeFieldEntity, RuntimeVariableElement> fieldFactory,
            final RuntimeGenericElementFactory<RuntimeParameterEntity, RuntimeVariableElement> parameterFactory) {
        this.enumFactory = enumFactory;
        this.fieldFactory = fieldFactory;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public Single<RuntimeVariableElement> createEnum(final RuntimeEnumEntity entity) {
        return this.enumFactory.createElement(entity);
    }

    @Override
    public Single<RuntimeVariableElement> createFromField(final RuntimeFieldEntity entity) {
        return this.fieldFactory.createElement(entity);
    }

    @Override
    public Observable<? extends VariableElement> createFromExecutable(final RuntimeExecutableEntity executable) {
        return Observable.fromIterable(executable.parameters())
                .doOnNext(parameter -> logger.trace("Creating variable for parameter: '{}'", parameter.name()))
                .flatMapSingle(this::createFromParameter)
                .doOnNext(parameter -> logger.trace("Created variable element: '{}'", parameter.getSimpleName()));
    }

    @Override
    public Single<RuntimeVariableElement> createFromParameter(final RuntimeParameterEntity entity) {
        return this.parameterFactory.createElement(entity);
    }

    @Override
    public String toString() {
        return "DelegateFactory{" +
                "enumFactory=" + this.enumFactory +
                ", fieldFactory=" + this.fieldFactory +
                ", parameterFactory=" + this.parameterFactory +
                "}";
    }

}
