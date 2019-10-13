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
package org.rookit.utils.object;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import one.util.streamex.StreamEx;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.string.StringUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

final class BaseDynamicObjectFactory implements DynamicObjectFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = getLogger(BaseDynamicObjectFactory.class);

    private final Collection<DynamicObjectFactory> factories;
    private final Collection<String> supportedTypes;
    private final StringUtils stringUtils;
    private final Failsafe failsafe;

    @Inject
    private BaseDynamicObjectFactory(final Set<DynamicObjectFactory> factories,
                                     final StringUtils stringUtils,
                                     final Failsafe failsafe) {
        this.factories = ImmutableSet.copyOf(factories);
        this.stringUtils = stringUtils;
        this.failsafe = failsafe;
        this.supportedTypes = StreamEx.of(this.factories.stream())
                .map(DynamicObjectFactory::supportedTypes)
                .flatMap(Collection::stream)
                .toImmutableSet();
    }

    @Override
    public Collection<String> supportedTypes() {
        //noinspection AssignmentOrReturnOfFieldWithMutableType already immutable (see initialization)
        return this.supportedTypes;
    }

    @Override
    public DynamicObject fromRawContent(final String rawContent) throws MalformedObjectException {
        for (final DynamicObjectFactory factory : this.factories) {
            try {
                return factory.fromRawContent(rawContent);
            } catch (final MalformedObjectException e) {
                final String errMsg = String.format("Cannot create from content: '%s'",
                        this.stringUtils.preview(rawContent, 10));
                logger.info(errMsg, e);
            } catch (final IOException e) {
                this.failsafe.handleException().inputOutputException(e);
            }
        }
        throw new MalformedObjectException(supportedTypes());
    }

    @Override
    public String toString() {
        return "BaseDynamicObjectFactory{" +
                "factories=" + this.factories +
                ", supportedTypes=" + this.supportedTypes +
                ", stringUtils=" + this.stringUtils +
                ", failsafe=" + this.failsafe +
                "}";
    }
}
