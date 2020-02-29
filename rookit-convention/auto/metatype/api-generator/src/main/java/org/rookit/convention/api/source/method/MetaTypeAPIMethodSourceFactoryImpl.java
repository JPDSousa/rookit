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
package org.rookit.convention.api.source.method;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import org.rookit.auto.source.method.MethodSourceFactory;
import org.rookit.auto.source.method.MutableMethodSource;
import org.rookit.auto.source.type.parameter.TypeParameterSource;
import org.rookit.auto.source.type.parameter.TypeParameterSourceFactory;
import org.rookit.auto.source.type.variable.WildcardVariableSourceFactory;
import org.rookit.convention.property.PropertyModel;

final class MetaTypeAPIMethodSourceFactoryImpl implements MetaTypeAPIMethodSourceFactory {

    private final WildcardVariableSourceFactory wildcardFactory;
    private final MethodSourceFactory methodFactory;
    private final TypeParameterSourceFactory typeParameterFactory;

    @Inject
    private MetaTypeAPIMethodSourceFactoryImpl(
            final WildcardVariableSourceFactory wildcardFactory,
            final MethodSourceFactory methodFactory,
            final TypeParameterSourceFactory typeParameterFactory) {
        this.wildcardFactory = wildcardFactory;
        this.methodFactory = methodFactory;
        this.typeParameterFactory = typeParameterFactory;
    }

    @Override
    public PropertiesMethodSource createPropertiesMethod(final CharSequence name) {

        final String varName = "allProperties";
        final TypeParameterSource varType = this.typeParameterFactory.create(
                ImmutableList.Builder.class,
                this.typeParameterFactory.create(
                        PropertyModel.class,
                        this.wildcardFactory.newWildcard()
                ));

        final MutableMethodSource method = this.methodFactory.createMutableMethod(name)
                .makePublic()
                .makeDefault()
                .addVariable(varType, varName, "$T.builder()", ImmutableList.of(ImmutableList.class));

        return new PropertiesMethodSourceImpl(name, varName, method);
    }

}
