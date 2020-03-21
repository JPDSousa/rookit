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
package org.rookit.auto;

import org.rookit.auto.config.ProcessorConfig;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public abstract class AbstractConfigAwareTypeProcessor implements TypeProcessor {

    private static final String DISABLED_MESSAGE = "Skipping entity processing, as this processor is " +
            "marked as disabled.";

    private final ProcessorConfig config;
    private final Messager messager;

    protected AbstractConfigAwareTypeProcessor(final ProcessorConfig config, final Messager messager) {
        this.config = config;
        this.messager = messager;
    }


    @Override
    public void process(final Iterable<TypeElement> entities) {
        if (this.config.isEnabled()) {
            TypeProcessor.super.process(entities);
        } else {
            this.messager.printMessage(Diagnostic.Kind.NOTE, DISABLED_MESSAGE);
        }
    }

    @Override
    public void process(final TypeElement element) {
        if (this.config.isEnabled()) {
            doProcessEntity(element);
        } else {
            this.messager.printMessage(Diagnostic.Kind.NOTE, DISABLED_MESSAGE);
        }
    }

    protected abstract void doProcessEntity(final TypeElement element);

}
