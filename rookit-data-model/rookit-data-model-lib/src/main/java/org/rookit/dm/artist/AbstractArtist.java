/*******************************************************************************
 * Copyright (C) 2017 Joao Sousa
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

package org.rookit.dm.artist;

import com.google.common.collect.Sets;
import com.neovisionaries.i18n.CountryCode;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.profile.Profile;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.dm.genre.able.DelegateGenreable;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.NotThreadSafe;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Abstract implementation of the {@link Artist} interface. Extend this class in
 * order to create a custom artist.
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
@NotThreadSafe
abstract class AbstractArtist extends DelegateGenreable implements Artist {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractArtist.class);
    
    /**
     * Set of Related artists
     */
    private final Set<Artist> related;

    private final MutableProfile profile;

    private final OptionalUtils optionalUtils;
    
    private final Failsafe failsafe;

    /**
     * Abstract constructor. Use this constructor to Initialize all the class
     * fields.
     *
     */
    AbstractArtist(final Genreable genreable,
                   final MutableProfile profile,
                   final OptionalUtils optionalUtils, 
                   final Failsafe failsafe) {
        super(genreable);
        this.profile = profile;
        this.optionalUtils = optionalUtils;
        this.failsafe = failsafe;
        this.related = Sets.newLinkedHashSet();
    }

    @Override
    public void addAlias(final String alias) {
        this.failsafe.checkArgument().string().isNotBlank(logger, alias, "alias");
        this.profile.addAlias(alias);
    }

    @Override
    public void addRelatedArtist(final Artist artist) {
        this.failsafe.checkArgument().isNotNull(logger, artist, "related artist");
        this.related.add(artist);
    }

    @Override
    public void clearAliases() {
        this.profile.clearAliases();
    }

    @Override
    public Profile profile() {
        return this.profile;
    }

    @Override
    public Collection<Artist> relatedArtists() {
        return Collections.unmodifiableCollection(this.related);
    }

    @Override
    public void removeAlias(final String alias) {
        this.failsafe.checkArgument().string().isNotBlank(logger, alias, "alias");
        this.profile.removeAlias(alias);
    }

    @Override
    public void setAliases(final Collection<String> aliases) {
        this.failsafe.checkArgument().isNotNull(logger, aliases, "aliases");
        clearAliases();
        aliases.forEach(this::addAlias);
    }

    @Override
    public void setBeginDate(final LocalDate beginDate) {
        this.failsafe.checkArgument().isNotNull(logger, beginDate, "beginDate");
        this.profile.setBeginDate(beginDate);
    }

    @Override
    public void setEndDate(final LocalDate endDate) {
        this.failsafe.checkArgument().isNotNull(logger, endDate, "endDate");
        this.profile.setEndDate(endDate);
    }

    @Override
    public void setIPI(final String ipi) {
        this.failsafe.checkArgument().isNotNull(logger, ipi, "ipi");
        this.profile.setIpi(ipi);
    }

    @Override
    public void setOrigin(final CountryCode origin) {
        this.failsafe.checkArgument().isNotNull(logger, origin, "origin");
        this.profile.setOrigin(origin);
    }

    @Override
    public void setPicture(final byte[] picture) {
        this.failsafe.checkArgument().isNotNull(logger, picture, "picture");
        try (final OutputStream output = this.profile.picture().writeTo()) {
            output.write(picture);
        } catch (final IOException e) {
            this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public int compareTo(final Artist o) {
        final int profile = profile().compareTo(o.profile());
        return (profile == 0) ? this.optionalUtils.compare(id(), o.id()) : profile;
    }

    @Override
    public String toString() {
        return "AbstractArtist{" +
                "related=" + this.related +
                ", profile=" + this.profile +
                ", optionalUtils=" + this.optionalUtils +
                ", injector=" + this.failsafe +
                "} " + super.toString();
    }
}
