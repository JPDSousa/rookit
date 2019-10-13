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

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import org.rookit.auto.javax.ExtendedElementFactory;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.collection.ListUtils;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.registry.Registry;
import org.rookit.utils.string.join.JointString;
import org.rookit.utils.string.join.JointStringFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.PackageElement;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class BaseExtendedPackageElementFactory implements ExtendedPackageElementFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseExtendedPackageElementFactory.class);

    private final OptionalFactory optionalFactory;
    private final JointStringFactory jointFactory;
    private final ExtendedElementFactory extendedFactory;
    private final Map<JointString, ExtendedPackageElement> packagesMap;
    private final Registry<JointString, PackageElement> packageRegistry;
    private final ListUtils listUtils;
    private final Failsafe failsafe;

    @Inject
    private BaseExtendedPackageElementFactory(final OptionalFactory optionalFactory,
                                              @Package final JointStringFactory jointFactory,
                                              final ExtendedElementFactory extendedFactory,
                                              final Registry<JointString, PackageElement> packageRegistry,
                                              final ListUtils listUtils,
                                              final Failsafe failsafe) {
        this.optionalFactory = optionalFactory;
        this.jointFactory = jointFactory;
        this.extendedFactory = extendedFactory;
        this.packageRegistry = packageRegistry;
        this.listUtils = listUtils;
        this.failsafe = failsafe;
        this.packagesMap = Maps.newHashMap();
    }

    @Override
    public ExtendedPackageElement create(final String fqdn) {
        return create(this.jointFactory.parse(fqdn));
    }

    @Override
    public ExtendedPackageElement create(final JointString fullName) {
        this.failsafe.checkArgument().isNotNull(logger, fullName, "fullName");
        if (this.packagesMap.containsKey(fullName)) {
            return this.packagesMap.get(fullName);
        }

        final int length = fullName.length();
        if (length == 1) {
            final PackageElement packageElement = fetchPackageElement(fullName);
            final ExtendedPackageElement pkg = new ImmutableExtendedPackageElement(
                    this,
                    packageElement,
                    this.extendedFactory.extend(packageElement),
                    fullName,
                    this.optionalFactory
            );
            this.packagesMap.put(fullName, pkg);
            return pkg;
        }

        final List<String> allItems = fullName.asList();
        final JointString parentFullName = this.jointFactory.create(this.listUtils.allButLast(allItems));
        final ExtendedPackageElement parent = create(parentFullName);
        final PackageElement packageElement = fetchPackageElement(fullName);
        final ExtendedPackageElement pkg = new ImmutableParentExtendedPackageElement(
                this,
                this.extendedFactory.extend(packageElement),
                this.listUtils.last(allItems),
                parent,
                packageElement,
                fullName,
                this.optionalFactory
        );
        this.packagesMap.put(fullName, pkg);
        return pkg;
    }

    @Override
    public ExtendedPackageElement create(final PackageElement element) {
        if (element instanceof ExtendedPackageElement) {
            return (ExtendedPackageElement) element;
        }

        return create(element.getQualifiedName().toString());
    }

    private PackageElement fetchPackageElement(final JointString fullName) {

        final PackageElement packageElement = this.packageRegistry.get(fullName).blockingGet();
        if (Objects.isNull(packageElement)) {
            final String errMsg = String.format("Cannot fetch package with fqdn '%s' from registry",
                    fullName.asString());
            throw new IllegalStateException(errMsg);
        }

        return packageElement;
    }

    @Override
    public String toString() {
        return "BaseExtendedPackageElementFactory{" +
                "optionalFactory=" + this.optionalFactory +
                ", jointFactory=" + this.jointFactory +
                ", extendedFactory=" + this.extendedFactory +
                ", packagesMap=" + this.packagesMap +
                ", packageRegistry=" + this.packageRegistry +
                ", listUtils=" + this.listUtils +
                ", failsafe=" + this.failsafe +
                "}";
    }
}
