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

package org.rookit.dm.genre;

import org.apache.commons.lang3.StringUtils;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.play.able.DelegatePlayable;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.Optional;
import org.rookit.utils.optional.OptionalFactory;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;

final class GenreImpl extends DelegatePlayable implements Genre {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GenreImpl.class);
    private static final String NO_DESCRIPTION = EMPTY;

    private final String name;
    private String description;
    private final OptionalUtils optionalUtils;
    private final Failsafe failsafe;
    private final OptionalFactory optionalFactory;

    GenreImpl(final String name,
              final Playable playable,
              final OptionalUtils optionalUtils,
              final Failsafe failsafe,
              final OptionalFactory optionalFactory) {
        super(playable);
        this.name = name;
        this.optionalUtils = optionalUtils;
        this.failsafe = failsafe;
        this.optionalFactory = optionalFactory;
        this.description = NO_DESCRIPTION;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof GenreImpl) {
            if (!super.equals(object)) {
                return false;
            }
            final GenreImpl that = (GenreImpl) object;
            return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description);
        }
        return false;
    }

    @Override
    public Optional<String> description() {
        return this.optionalFactory.ofNullable(this.description)
                .filter(desc -> !StringUtils.equals(desc, NO_DESCRIPTION));
    }

    @Override
    public String name() {
        return this.name;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.name, this.description);
    }

    @Override
    public void resetDescription() {
        this.description = NO_DESCRIPTION;
    }

    @Override
    public void setDescription(final String description) {
        this.failsafe.checkArgument().string().isNotBlank(logger, description, "description");
        this.description = description;
    }

    //TODO there could be some reuse here, since this is also being equally implemented in AbstractArtist and so on
    @Override
    public int compareTo(final Genre o) {
        final int name = name().compareTo(o.name());
        return (name == 0) ? this.optionalUtils.compare(id(), o.id()) : name;
    }

    @Override
    public String toString() {
        return "GenreImpl{" +
                "className='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", optionalUtils=" + this.optionalUtils +
                ", injector=" + this.failsafe +
                ", optionalFactory=" + this.optionalFactory +
                "} " + super.toString();
    }
}
