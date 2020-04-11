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
package org.rookit.api.dm.artist.profile;

import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.dm.artist.external.ExternalIdentifiers;
import org.rookit.api.dm.artist.name.ArtistName;
import org.rookit.api.dm.artist.timeline.Timeline;
import org.rookit.convention.annotation.Property;
import org.rookit.convention.annotation.PropertyContainer;
import org.rookit.io.data.DataBucket;
import org.rookit.utils.optional.Optional;

@PropertyContainer
public interface Profile extends Comparable<Profile> {

    @Override
    default int compareTo(final Profile o) {
        return name().compareTo(o.name());
    }

    @Property(mutable = true)
    ArtistName name();

    @Property
    Timeline timeline();

    @Property(mutable = true)
    ExternalIdentifiers externalIdentifiers();

    /**
     * Returns the origin of this artist (location where the artist came from)
     *
     * @return the artist's origin
     */
    @Property
    Optional<CountryCode> origin();

    @Property
    DataBucket picture();
}
