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
package org.rookit.dm;

import com.google.inject.Inject;
import org.rookit.api.dm.RookitModel;
import org.rookit.api.dm.RookitModelFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;

final class RookitModelFactoryImpl implements RookitModelFactory {

    private final OptionalFactory optionalFactory;
    private final Failsafe failsafe;

    @Inject
    private RookitModelFactoryImpl(final OptionalFactory optionalFactory, final Failsafe failsafe) {
        this.optionalFactory = optionalFactory;
        this.failsafe = failsafe;
    }

    @Override
    public RookitModel create() {
        return new RookitModelImpl(this.failsafe, this.optionalFactory);
    }

    @Override
    public String toString() {
        return "RookitModelFactoryImpl{" +
                "optionalFactory=" + this.optionalFactory +
                ", injector=" + this.failsafe +
                "}";
    }
}
