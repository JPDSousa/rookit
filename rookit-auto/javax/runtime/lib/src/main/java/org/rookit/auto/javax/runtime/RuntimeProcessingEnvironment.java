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
package org.rookit.auto.javax.runtime;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Locale;
import java.util.Map;

final class RuntimeProcessingEnvironment implements ProcessingEnvironment {

    private final Messager messager;
    private final Filer filer;
    private final Elements elements;
    private final Types types;
    private final SourceVersion sourceVersion;
    private final Locale locale;

    @Inject
    private RuntimeProcessingEnvironment(
            final Messager messager,
            final Filer filer,
            final Elements elements,
            final Types types,
            final SourceVersion sourceVersion,
            final Locale locale) {
        this.messager = messager;
        this.filer = filer;
        this.elements = elements;
        this.types = types;
        this.sourceVersion = sourceVersion;
        this.locale = locale;
    }

    @Override
    public Map<String, String> getOptions() {
        return ImmutableMap.of();
    }

    @Override
    public Messager getMessager() {
        return this.messager;
    }

    @Override
    public Filer getFiler() {
        return this.filer;
    }

    @Override
    public Elements getElementUtils() {
        return this.elements;
    }

    @Override
    public Types getTypeUtils() {
        return this.types;
    }

    @Override
    public SourceVersion getSourceVersion() {
        return this.sourceVersion;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public String toString() {
        return "RuntimeProcessingEnvironment{" +
                "messager=" + this.messager +
                ", filer=" + this.filer +
                ", elements=" + this.elements +
                ", types=" + this.types +
                ", sourceVersion=" + this.sourceVersion +
                ", locale=" + this.locale +
                "}";
    }

}
