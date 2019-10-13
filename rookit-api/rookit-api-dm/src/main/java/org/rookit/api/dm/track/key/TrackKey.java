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
package org.rookit.api.dm.track.key;

import org.immutables.value.Value;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.key.Key;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.dm.track.TypeVersion;

import java.util.Collection;
import java.util.Optional;

@SuppressWarnings("javadoc")
@Value.Immutable
@Value.Style(canBuild = "isBuildable")
public interface TrackKey extends Key {

    Collection<Artist> mainArtists();

    TypeTrack type();

    String title();

    // TODO these 4 methods/properties are from one of Track's subtypes, which is a scalability bottleneck
    Track original();
    Optional<String> versionToken();
    Collection<Artist> getVersionArtists();
    TypeVersion getVersionType();

}
