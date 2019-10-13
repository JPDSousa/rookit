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
package org.rookit.auto.javax.runtime.pack;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.NameFactory;
import org.rookit.auto.javax.runtime.element.node.NodeElementFactory;
import org.rookit.auto.javax.runtime.element.pack.RuntimePackageElement;
import org.rookit.auto.javax.runtime.entity.RuntimePackageEntity;
import org.rookit.utils.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Name;

final class PackageElementFactory implements Registry<RuntimePackageEntity, RuntimePackageElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PackageElementFactory.class);

    private final NameFactory nameFactory;
    private final NodeElementFactory nodeElementFactory;

    @Inject
    private PackageElementFactory(
            final NameFactory nameFactory,
            final NodeElementFactory nodeElementFactory) {
        this.nameFactory = nameFactory;
        this.nodeElementFactory = nodeElementFactory;
    }

    @Override
    public Maybe<RuntimePackageElement> get(final RuntimePackageEntity key) {
        return fetch(key).toMaybe();
    }

    @Override
    public Single<RuntimePackageElement> fetch(final RuntimePackageEntity entity) {
        // assuming simple name and fqdn are the same for packages.
        final Name name = this.nameFactory.createFromEntity(entity);
        return this.nodeElementFactory.createMutableFromEntity(entity)
                .map(node -> new NamedPackageElement(name, name, node));
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

    @Override
    public String toString() {
        return "PackageElementFactory{" +
                "nameFactory=" + this.nameFactory +
                ", nodeElementFactory=" + this.nodeElementFactory +
                "}";
    }

}
