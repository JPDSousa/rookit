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
package org.rookit.dm.track.title;

import com.google.inject.Inject;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.title.Title;
import org.rookit.api.dm.track.title.TitleFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.print.FormatType;
import org.rookit.utils.print.PrintUtils;
import org.slf4j.LoggerFactory;

final class TitleFactoryImpl implements TitleFactory {
    
    /**
     * Logger for this class.
     */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TitleFactoryImpl.class);
    
    private final Failsafe failsafe;
    private final PrintUtils printUtils;
    private final FormatType formatType;
    private final OptionalFactory optionalFactory;

    @Inject
    private TitleFactoryImpl(final Failsafe failsafe,
                             final PrintUtils printUtils,
                             final FormatType formatType,
                             final OptionalFactory optionalFactory) {
        this.failsafe = failsafe;
        this.printUtils = printUtils;
        this.formatType = formatType;
        this.optionalFactory = optionalFactory;
    }

    @Override
    public Title create(final String title) {
        return new MutableTitleImpl(this.printUtils, this.formatType, this.optionalFactory, title);
    }

    @Override
    public Title create(final Key key) {
        logger.info("Creation by key is not supported. Creating empty instead.");
        return createEmpty();
    }

    @Override
    public Title createEmpty() {
        return this.failsafe.handleException().runtimeException("Cannot create an empty title.");
    }

    @Override
    public String toString() {
        return "TitleFactoryImpl{" +
                "injector=" + this.failsafe +
                ", printUtils=" + this.printUtils +
                ", formatType=" + this.formatType +
                ", optionalFactory=" + this.optionalFactory +
                "}";
    }
}
