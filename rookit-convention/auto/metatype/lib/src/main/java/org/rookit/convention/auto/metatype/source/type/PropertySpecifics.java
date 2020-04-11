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
package org.rookit.convention.auto.metatype.source.type;

import com.google.inject.Inject;
import org.rookit.auto.source.type.MutableTypeSource;
import org.rookit.convention.auto.property.ImmutableProperty;
import org.rookit.convention.auto.property.MutableProperty;
import org.rookit.convention.auto.property.MutablePropertyModifiers;
import org.rookit.convention.auto.property.Property;
import org.rookit.convention.auto.property.PropertyVisitor;
import org.rookit.convention.auto.property.collection.ImmutableCollectionProperty;
import org.rookit.convention.auto.property.collection.MutableCollectionProperty;
import org.rookit.convention.auto.property.map.ImmutableMapProperty;
import org.rookit.convention.auto.property.map.MutableMapProperty;
import org.rookit.convention.auto.property.optional.ImmutableOptionalProperty;
import org.rookit.convention.auto.property.optional.MutableOptionalProperty;
import org.rookit.convention.auto.property.optional.MutableOptionalPropertyModifiers;

final class PropertySpecifics implements PropertyVisitor<MutableTypeSource, VisitorData> {

    private final MutableOptionalPropertyModifiers<VisitorData> mutableOptional;
    private final MutablePropertyModifiers<VisitorData> mutable;

    @Inject
    private PropertySpecifics(
            final MutableOptionalPropertyModifiers<VisitorData> mutableOptional,
            final MutablePropertyModifiers<VisitorData> mutable) {

        this.mutableOptional = mutableOptional;
        this.mutable = mutable;
    }

    @Override
    public MutableTypeSource visitImmutableCollection(
            final ImmutableCollectionProperty collection, final VisitorData parameter) {
        
        return parameter.source();
    }

    @Override
    public MutableTypeSource visitImmutableMap(
            final ImmutableMapProperty map, final VisitorData parameter) {

        return parameter.source();
    }

    @Override
    public MutableTypeSource visitImmutableOptional(
            final ImmutableOptionalProperty optional, final VisitorData parameter) {

        return parameter.source();
    }

    @Override
    public MutableTypeSource visitMutableCollection(
            final MutableCollectionProperty collection, final VisitorData parameter) {
        
        return parameter.source();
    }

    @Override
    public MutableTypeSource visitMutableMap(
            final MutableMapProperty map, final VisitorData parameter) {
        
        return parameter.source();
    }

    @Override
    public MutableTypeSource visitMutableOptional(
            final MutableOptionalProperty optional, final VisitorData parameter) {

        return this.mutableOptional.applyAll(parameter).source();
    }

    @Override
    public MutableTypeSource visitUnknown(
            final Property property, final VisitorData parameter) {
        
        return parameter.source();
    }

    @Override
    public MutableTypeSource visitImmutable(
            final ImmutableProperty immutable, final VisitorData parameter) {

        return parameter.source();
    }

    @Override
    public MutableTypeSource visitMutable(
            final MutableProperty mutable, final VisitorData parameter) {

        return this.mutable.applyAll(parameter).source();
    }

}
