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
package org.rookit.auto.config;

import org.rookit.utils.object.DynamicObject;
import org.rookit.utils.object.NoSuchPropertyException;
import org.rookit.io.path.PathConfig;

final class EmptyAutoConfig implements AutoConfig {

    private final PathConfig pathConfig;

    EmptyAutoConfig(final PathConfig pathConfig) {
        this.pathConfig = pathConfig;
    }

    @Override
    public PathConfig fileConfig() {
        return this.pathConfig;
    }

    @Override
    public DynamicObject getProcessorConfig(final String name) {
        final String message = String.format("Cannot find processor config with name '%s', since this is an empty" +
                " configuration.", name);
        throw NoSuchPropertyException.missingProperty(message);
    }

    @Override
    public String toString() {
        return "EmptyAutoConfig{" +
                "pathConfig=" + this.pathConfig +
                "}";
    }
}
