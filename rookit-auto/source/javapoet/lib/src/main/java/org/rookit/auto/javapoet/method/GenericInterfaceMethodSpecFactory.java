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
package org.rookit.auto.javapoet.method;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeVariableName;
import org.rookit.auto.javax.naming.NamingFactory;
import org.rookit.utils.string.template.Template1;

import javax.lang.model.element.Modifier;
import java.util.Locale;


final class GenericInterfaceMethodSpecFactory implements MethodSpecFactory {

    private static final Modifier[] MODIFIERS = {Modifier.PUBLIC, Modifier.ABSTRACT};

    private final Locale locale;
    private final TypeVariableName typeVariableName;
    private final NamingFactory namingFactory;
    private final Template1 noopTemplate;

    @Inject
    GenericInterfaceMethodSpecFactory(final Locale locale,
                                      final TypeVariableName typeVariableName,
                                      final NamingFactory namingFactory,
                                      final Template1 noopTemplate) {
        this.locale = locale;
        this.typeVariableName = typeVariableName;
        this.namingFactory = namingFactory;
        this.noopTemplate = noopTemplate;
    }

    @Override
    public MethodSpec create(final String propertyName, final ParameterSpec... parameters) {
        return create(propertyName, this.noopTemplate, parameters);
    }

    @Override
    public MethodSpec create(final String propertyName,
                             final Template1 template1,
                             final ParameterSpec... parameters) {
        return createMethod(this.namingFactory.method(propertyName, template1), parameters);
    }

    private MethodSpec createMethod(final String methodName, final ParameterSpec... parameters) {
        return MethodSpec.methodBuilder(methodName)
                .addParameters(ImmutableList.copyOf(parameters))
                .addModifiers(MODIFIERS)
                .returns(this.typeVariableName)
                .build();
    }

    @Override
    public MethodSpec create() {
        // TODO please do something about this.
        return createMethod(this.typeVariableName.name.toLowerCase(this.locale));
    }

    @Override
    public String toString() {
        return "GenericInterfaceMethodSpecFactory{" +
                "locale=" + this.locale +
                ", typeVariableName=" + this.typeVariableName +
                ", namingFactory=" + this.namingFactory +
                ", noopTemplate=" + this.noopTemplate +
                "}";
    }
}
