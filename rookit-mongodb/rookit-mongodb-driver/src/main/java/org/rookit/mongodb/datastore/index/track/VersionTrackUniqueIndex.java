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
package org.rookit.mongodb.datastore.index.track;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.VersionTrackMetaType;
import org.rookit.mongodb.datastore.MongoIndex;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.mongodb.guice.Unique;

import java.util.List;


final class VersionTrackUniqueIndex implements MongoIndex<VersionTrack> {

    private final MongoIndex<Track> trackIndex;
    private final IndexModel index;
    private final String versionTypeProperty;
    private final String versionArtistsProperty;

    @Inject
    private VersionTrackUniqueIndex(@Mongo final VersionTrackMetaType properties,
                                    @Unique final MongoIndex<Track> trackIndex) {
        this.trackIndex = trackIndex;
        this.versionTypeProperty = properties.versionType().propertyName();
        this.versionArtistsProperty = properties.versionArtists().propertyName();

        final Bson bsonIndex = Indexes.ascending(ImmutableList.<String>builder()
                .addAll(trackIndex.fields())
                .add(this.versionArtistsProperty)
                .add(this.versionTypeProperty)
                .build());

        final IndexOptions options = new IndexOptions();
        options.unique(true);
        this.index = new IndexModel(bsonIndex, options);
    }

    @Override
    public IndexModel indexModel() {
        return this.index;
    }

    @Override
    public Bson queryFrom(final VersionTrack model) {
        return Filters.and(ImmutableList.of(
                this.trackIndex.queryFrom(model),
                Filters.eq(this.versionTypeProperty, model.versionType()),
                Filters.all(this.versionArtistsProperty, model.versionArtists())
        ));
    }

    @Override
    public List<String> fields() {
        return ImmutableList.<String>builder()
                .addAll(this.trackIndex.fields())
                .add(this.versionArtistsProperty)
                .add(this.versionTypeProperty)
                .build();
    }

    @Override
    public String toString() {
        return "VersionTrackUniqueIndex{" +
                "trackIndex=" + this.trackIndex +
                ", index=" + this.index +
                ", versionTypeProperty='" + this.versionTypeProperty + '\'' +
                ", versionArtistsProperty='" + this.versionArtistsProperty + '\'' +
                "}";
    }
}
