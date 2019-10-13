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
import org.rookit.auto.javax.ExtendedElementFactory;
import org.rookit.auto.source.CodeSource;
import org.rookit.auto.source.CodeSourceFactory;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import java.util.concurrent.ExecutionException;

public abstract class AbstractTypeProcessor extends AbstractConfigAwareTypeProcessor {

    private final CodeSourceFactory codeSourceFactory;
    private final Filer filer;
    private final ExtendedElementFactory elementFactory;

    protected AbstractTypeProcessor(final CodeSourceFactory codeSourceFactory,
                                    final Filer filer,
                                    final ExtendedElementFactory elementFactory,
                                    final ProcessorConfig config,
                                    final Messager messager) {
        super(config, messager);
        this.codeSourceFactory = codeSourceFactory;
        this.filer = filer;
        this.elementFactory = elementFactory;
    }

    @Override
    protected void doProcessEntity(final TypeElement element) {
        try {
            final CodeSource codeSource = this.codeSourceFactory.create(this.elementFactory.extendType(element));
            codeSource.writeTo(this.filer).get();
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "AbstractTypeProcessor{" +
                "codeSourceFactory=" + this.codeSourceFactory +
                ", filer=" + this.filer +
                ", elementFactory=" + this.elementFactory +
                "}";
    }
}
