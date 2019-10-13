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
import org.rookit.api.dm.artist.GroupArtist;
import org.rookit.api.dm.artist.Musician;
import org.rookit.api.dm.artist.TypeArtist;
import org.rookit.api.dm.artist.TypeGroup;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.dm.artist.profile.MutableProfile;
import org.rookit.failsafe.Failsafe;
import org.rookit.utils.optional.OptionalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

class GroupArtistImpl extends AbstractArtist implements GroupArtist {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GroupArtistImpl.class);

    private Set<Musician> members;
    private final TypeGroup groupType;
    private final Failsafe failsafe;

    GroupArtistImpl(final Genreable genreable,
                    final MutableProfile profile,
                    final TypeGroup groupType,
                    final OptionalUtils optionalUtils,
                    final Failsafe failsafe) {
        super(genreable, profile, optionalUtils, failsafe);
        this.groupType = groupType;
        this.failsafe = failsafe;
        this.members = Collections.synchronizedSet(Sets.newLinkedHashSet());
    }

    @Override
    public void addMember(final Musician member) {
        this.failsafe.checkArgument().isNotNull(logger, member, "member");
        this.members.add(member);
    }

    @Override
    public void addMembers(final Collection<Musician> members) {
        this.failsafe.checkArgument().isNotNull(logger, members, "members");
        this.members.addAll(members);
    }

    @Override
    public void clearMembers() {
        this.members.clear();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof GroupArtistImpl) {
            if (!super.equals(object)) {
                return false;
            }
            final GroupArtistImpl that = (GroupArtistImpl) object;
            return Objects.equals(this.groupType, that.groupType);
        }
        return false;
    }

    @Override
    public TypeGroup groupType() {
        return this.groupType;
    }

    @Override
    public Collection<Musician> members() {
        return Collections.unmodifiableCollection(this.members);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(Integer.valueOf(super.hashCode()), this.groupType);
    }

    @Override
    public void removeMember(final Musician member) {
        this.failsafe.checkArgument().isNotNull(logger, member, "member");
        this.members.remove(member);
    }

    @Override
    public void removeMembers(final Collection<Musician> members) {
        this.failsafe.checkArgument().isNotNull(logger, members, "members");
        this.members.removeAll(members);
    }

    @Override
    public void setMembers(final Collection<Musician> members) {
        this.failsafe.checkArgument().isNotNull(logger, members, "members");
        this.members = Sets.newLinkedHashSet(members);
    }

    @Override
    public TypeArtist type() {
        return TypeArtist.GROUP;
    }

    @Override
    public String toString() {
        return "GroupArtistImpl{" +
                "members=" + this.members +
                ", groupType=" + this.groupType +
                ", injector=" + this.failsafe +
                "} " + super.toString();
    }
}
