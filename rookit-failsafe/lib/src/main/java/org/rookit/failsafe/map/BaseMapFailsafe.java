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
package org.rookit.failsafe.map;

import org.rookit.failsafe.object.DelegateObjectFailsafe;
import org.rookit.failsafe.MapFailsafe;
import org.rookit.failsafe.ObjectFailsafe;
import org.slf4j.Logger;

import java.util.Map;

final class BaseMapFailsafe extends DelegateObjectFailsafe implements MapFailsafe {

    BaseMapFailsafe(final ObjectFailsafe delegate) {
        super(delegate);
    }

    @Override
    public <T> T checkSingleEntryMap(final Logger logger, final Map<?, ?> object, final String name) {
        isNotNull(logger, object, name);
        return is(logger, object.size() == 1, "The map %s is not a singleton", name);
    }

    @Override
    public <T, K> T isNotContainedIn(final Logger logger,
                                     final K key,
                                     final Map<K, ?> container,
                                     final String keyName,
                                     final String containerName) {
        isNotNull(logger, key, keyName);
        isNotNull(logger, container, containerName);
        return is(logger, !container.containsKey(key), "%s %s already exists in %s",
                keyName, key, containerName);
    }

}
