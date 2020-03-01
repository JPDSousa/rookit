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
package org.rookit.convention.meta;

import com.google.auto.service.AutoService;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.convention.auto.metatype.AbstractConventionProcessor;
import org.rookit.convention.meta.source.SourceModule;

import javax.annotation.processing.Processor;

@SuppressWarnings("PublicConstructor") // needed by SPI
@AutoService(Processor.class)
public final class MetaTypeProcessor extends AbstractConventionProcessor {

    private static final Module BASE_MODULE = Modules.override(
            org.rookit.convention.guice.source.SourceModule.getModule()
    ).with(
            org.rookit.convention.api.source.SourceModule.getModule()
    );

    private static final Module MODULE = Modules.override(
            BASE_MODULE
    ).with(
            SourceModule.getModule()
    );

    MetaTypeProcessor(final Injector injector) {
        super(injector);
    }

    public MetaTypeProcessor() { }

    @Override
    protected Module sourceModule() {
        return MODULE;
    }

}
