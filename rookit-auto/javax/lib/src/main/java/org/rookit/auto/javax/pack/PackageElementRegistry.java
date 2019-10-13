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
package org.rookit.auto.javax.pack;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.utils.collection.ListUtils;
import org.rookit.utils.registry.Registry;
import org.rookit.utils.string.join.JointString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;

import static java.util.Objects.isNull;

final class PackageElementRegistry implements Registry<JointString, PackageElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PackageElementRegistry.class);

    private final Elements elements;
    private final ListUtils listUtils;

    @Inject
    private PackageElementRegistry(
            final Elements elements,
            final ListUtils listUtils) {
        this.elements = elements;
        this.listUtils = listUtils;
    }

    @Override
    public Maybe<PackageElement> get(final JointString key) {

        final String keyString = key.asString();

        logger.trace("Fetching package '{}' from primary registry", keyString);
        final PackageElement packageElement = this.elements.getPackageElement(keyString);

        if (isNull(packageElement)) {
            logger.debug("Package '{}' not found in primary registry. Creating absent package", keyString);
            return Maybe.just(new AbsentPackageElement(new StringToName(keyString)));
        }

        logger.trace("Package '{}' found in primary registry", keyString);
        return Maybe.just(packageElement);
    }

    @Override
    public Single<PackageElement> fetch(final JointString key) {
        return get(key)
                .toSingle();
    }

    @Override
    public void close() {
        logger.debug("Nothing to be closed");
    }

    @Override
    public String toString() {
        return "PackageElementRegistry{" +
                "elements=" + this.elements +
                ", listUtils=" + this.listUtils +
                "}";
    }

}
