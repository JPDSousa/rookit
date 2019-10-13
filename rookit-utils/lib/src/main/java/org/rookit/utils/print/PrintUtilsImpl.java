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
package org.rookit.utils.print;

import com.google.inject.Inject;
import org.apache.commons.collections4.IterableUtils;

import java.time.Duration;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SuppressWarnings("javadoc")
public final class PrintUtilsImpl implements PrintUtils {

    private static final Duration ONE_HOUR = Duration.ofHours(1);

    public static PrintUtils create() {
        return new PrintUtilsImpl();
    }

    @Inject
    private PrintUtilsImpl() {
    }

    @Override
    public String duration(final Duration duration) {
        if (duration.minus(ONE_HOUR).isNegative()) {
            return new StringBuilder(5).append(normalizeUnit(duration.toMinutes()))
                    .append(':')
                    .append(normalizeUnit(duration.getSeconds() % 60))
                    .toString();
        }
        return duration3layer(duration);
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements, final char separator) {
        return getIterableAsString(elements, Character.toString(separator));
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements,
                                          final char separator,
                                          final String emptyMessage) {
        return getIterableAsString(elements, Character.toString(separator), emptyMessage);
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements,
                                          final Function<T, String> toString,
                                          final String separator) {
        return StreamSupport.stream(elements.spliterator(), false)
                .map(toString)
                .collect(Collectors.joining(separator));
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements,
                                          final Function<T, String> toString,
                                          final FormatType format) {
        return getIterableAsString(elements, toString, format.getSeparator());
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements, final String separator) {
        return StreamSupport.stream(elements.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining(separator));
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements,
                                          final String separator,
                                          final String emptyMessage) {
        if (IterableUtils.isEmpty(elements)) {
            return emptyMessage;
        }
        return getIterableAsString(elements, Object::toString, separator);
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements, final FormatType format) {
        return getIterableAsString(elements, format.getSeparator());
    }

    @Override
    public <T> String getIterableAsString(final Iterable<T> elements,
                                          final FormatType format,
                                          final String emptyMessage) {
        return getIterableAsString(elements, format.getSeparator(), emptyMessage);
    }

    private String duration3layer(final Duration duration) {
        return new StringBuilder(10).append(normalizeUnit(duration.toHours()))
                .append(':')
                .append(normalizeUnit(duration.toMinutes() % 60))
                .append(':')
                .append(normalizeUnit(duration.getSeconds() % 60))
                .toString();
    }

    private String normalizeUnit(final long unit) {
        if (unit < 10) {
            return '0' + Long.toString(unit);
        }
        return String.valueOf(unit);
    }

}
