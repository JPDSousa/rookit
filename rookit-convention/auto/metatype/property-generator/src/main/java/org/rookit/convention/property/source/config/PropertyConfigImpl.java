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
package org.rookit.convention.property.source.config;

import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.convention.auto.config.ConventionConfig;
import org.rookit.convention.auto.config.PropertyConfig;
import org.rookit.utils.object.DynamicObject;

final class PropertyConfigImpl implements PropertyConfig {

    private final DynamicObject configuration;
    private final ConventionConfig parent;
    private final String name;
    private final TypeVariableSourceFactory typeVariableFactory;

    PropertyConfigImpl(
            final DynamicObject configuration,
            final ConventionConfig parent,
            final String name,
            final TypeVariableSourceFactory typeVariableFactory) {
        this.configuration = configuration;
        this.parent = parent;
        this.name = name;
        this.typeVariableFactory = typeVariableFactory;
    }

    @Override
    public ExtendedPackageElement basePackage() {
        return this.parent.basePackage();
    }

    @Override
    public TypeVariableSource parameterName() {
        return this.typeVariableFactory.createFromName(this.configuration.getString("parameterName"));
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean isEnabled() {
        return this.configuration.getBoolean("enabled");
    }

}
