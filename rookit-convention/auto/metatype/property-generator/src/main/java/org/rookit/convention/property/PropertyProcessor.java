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
package org.rookit.convention.property;

import com.google.auto.service.AutoService;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.auto.AbstractExtendedProcessor;
import org.rookit.convention.property.source.SourceModule;

import javax.annotation.processing.Processor;

@SuppressWarnings("PublicConstructor")
@AutoService(Processor.class)
public final class PropertyProcessor extends AbstractExtendedProcessor {

    private static final Module BASE_BASE_MODULE = Modules.override(
            org.rookit.convention.guice.source.SourceModule.getModule()
    ).with(
            org.rookit.convention.api.source.SourceModule.getModule()
    );

    private static final Module BASE_MODULE = Modules.override(
            BASE_BASE_MODULE
    ).with(
            org.rookit.convention.meta.source.SourceModule.getModule()
    );

    private static final Module MODULE = Modules.override(
            BASE_MODULE
    ).with(
            SourceModule.getModule()
    );

    PropertyProcessor(final Injector injector) {
        super(injector);
    }

    public PropertyProcessor() { }

    @Override
    protected Module sourceModule() {
        return MODULE;
    }

}
