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

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.Collection;

import static java.lang.String.format;

public final class DependencyAwareProcessorConfig implements ProcessorConfig {

    private static final String DEPENDENCY_DISABLED = "Considering %s processor disabled, as " +
            "%s processor is disabled.";

    public static ProcessorConfig create(final ProcessorConfig delegate,
                                         final Collection<ProcessorConfig> dependencies,
                                         final Messager messager) {
        return new DependencyAwareProcessorConfig(delegate, dependencies, messager);
    }

    private final ProcessorConfig delegate;
    private final Collection<ProcessorConfig> dependencies;
    private final Messager messager;

    @Inject
    private DependencyAwareProcessorConfig(final ProcessorConfig delegate,
                                   final Collection<ProcessorConfig> dependencies,
                                   final Messager messager) {
        this.delegate = delegate;
        this.dependencies = ImmutableList.copyOf(dependencies);
        this.messager = messager;
    }

    @Override
    public String name() {
        return this.delegate.name();
    }

    @Override
    public boolean isEnabled() {
        final boolean enabled = this.delegate.isEnabled();

        if (enabled) {
            for (final ProcessorConfig dependency : this.dependencies) {
                if (!dependency.isEnabled()) {
                    this.messager.printMessage(Diagnostic.Kind.WARNING,
                            format(DEPENDENCY_DISABLED, this.delegate.name(), dependency.name()));
                    return false;
                }
            }
        }
        return enabled;
    }

    @Override
    public String toString() {
        return "DependencyAwareProcessorConfig{" +
                "delegate=" + this.delegate +
                ", dependencies=" + this.dependencies +
                ", messager=" + this.messager +
                "}";
    }
}
