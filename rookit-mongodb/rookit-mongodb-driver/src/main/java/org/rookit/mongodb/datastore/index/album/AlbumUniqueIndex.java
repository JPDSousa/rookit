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
package org.rookit.mongodb.datastore.index.album;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;
import org.rookit.api.dm.album.Album;
import org.rookit.api.dm.album.AlbumMetaType;
import org.rookit.mongodb.datastore.MongoIndex;
import org.rookit.mongodb.guice.Mongo;

import java.util.List;

final class AlbumUniqueIndex implements MongoIndex<Album> {

    private final IndexModel index;
    private final String releaseType;
    private final String title;
    private final String artists;

    @Inject
    private AlbumUniqueIndex(@Mongo final AlbumMetaType properties) {
        this.releaseType = properties.release().type().propertyName();
        this.title = properties.title().propertyName();
        this.artists = properties.artists().propertyName();
        final Bson titleAsc = Indexes.ascending(this.title);
        final Bson release = Indexes.descending(this.releaseType);
        final Bson artists = Indexes.ascending(this.artists);

        final IndexOptions uniqueOptions = new IndexOptions();
        uniqueOptions.unique(true);
        this.index = new IndexModel(Indexes.compoundIndex(titleAsc, release, artists), uniqueOptions);
    }


    @Override
    public IndexModel indexModel() {
        return this.index;
    }

    @Override
    public Bson queryFrom(final Album album) {
        return Filters.and(
                Filters.eq(this.title, album.title()),
                Filters.eq(this.releaseType, album.release().type()),
                Filters.all(this.artists, album.artists())
        );
    }

    @Override
    public List<String> fields() {
        return ImmutableList.of(this.title, this.releaseType, this.artists);
    }

    @Override
    public String toString() {
        return "AlbumUniqueIndex{" +
                "index=" + this.index +
                ", releaseType='" + this.releaseType + '\'' +
                ", title='" + this.title + '\'' +
                ", artists='" + this.artists + '\'' +
                "}";
    }
}
