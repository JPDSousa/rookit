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

import com.google.inject.Injector;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

abstract class AbstractInjectorExtendedProcessor extends AbstractProcessor implements ExtendedProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final Injector injector = injector();
        final Messager messager = injector.getInstance(Messager.class);
        try {
            final TypeProcessor handler = injector.getInstance(TypeProcessor.class);
            for (final TypeElement annotation : annotations) {
                messager.printMessage(Diagnostic.Kind.NOTE, "Processing: " + annotation.getQualifiedName());
                handler.process(ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(annotation)));
            }
            handler.postProcess();
            return false;
        } catch (final RuntimeException | IOException e) {
            final String stackTrace = ExceptionUtils.getStackTrace(e);
            messager.printMessage(Diagnostic.Kind.ERROR, stackTrace);
            throw new RuntimeException(e);
        }
    }

}
