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
package org.rookit.storage.config;

import org.rookit.auto.javax.pack.ExtendedPackageElement;
import org.rookit.auto.source.type.variable.TypeVariableSource;
import org.rookit.auto.source.type.variable.TypeVariableSourceFactory;
import org.rookit.storage.api.config.FilterConfig;
import org.rookit.utils.object.DynamicObject;
import org.rookit.utils.string.template.Template1;
import org.rookit.utils.string.template.TemplateFactory;

final class FilterConfigImpl implements FilterConfig {

    private final DynamicObject configuration;
    private final ExtendedPackageElement basePackage;
    private final Template1 partialEntityTemplate;
    private final String name;
    private final TemplateFactory templateFactory;
    private final TypeVariableSourceFactory typeVariableFactory;

    FilterConfigImpl(
            final DynamicObject configuration,
            final ExtendedPackageElement basePackage,
            final Template1 pEntityTemplate,
            final String name,
            final TemplateFactory templateFactory,
            final TypeVariableSourceFactory typeVariableFactory) {
        this.configuration = configuration;
        this.basePackage = basePackage;
        this.partialEntityTemplate = pEntityTemplate;
        this.name = name;
        this.templateFactory = templateFactory;
        this.typeVariableFactory = typeVariableFactory;
    }

    @Override
    public TypeVariableSource parameterName() {
        return this.typeVariableFactory.createFromName(this.configuration.getString("parameterName"));
    }

    @Override
    public ExtendedPackageElement basePackage() {
        return this.basePackage.resolve(this.configuration.getString("basePackage"));
    }

    @Override
    public Template1 entityTemplate() {
        return this.templateFactory.template1(this.configuration.getString("typeTemplate"));
    }

    @Override
    public Template1 partialEntityTemplate() {
        return this.partialEntityTemplate;
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
