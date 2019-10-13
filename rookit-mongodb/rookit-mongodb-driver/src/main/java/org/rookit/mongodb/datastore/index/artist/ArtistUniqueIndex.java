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
package org.rookit.mongodb.datastore.index.artist;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.conversions.Bson;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.artist.ArtistMetaType;
import org.rookit.mongodb.datastore.MongoIndex;
import org.rookit.mongodb.guice.Mongo;

import java.util.List;

final class ArtistUniqueIndex implements MongoIndex<Artist> {

    private final IndexModel index;
    private final String official;
    private final String isni;
    private final String type;

    @Inject
    private ArtistUniqueIndex(@Mongo final ArtistMetaType properties) {
        this.official = properties.profile().name().official().propertyName();
        this.isni = properties.profile().externalIdentifiers().isni().propertyName();
        this.type = properties.type().propertyName();

        final Bson keys = Indexes.ascending(this.official, this.type, this.isni);
        final IndexOptions options = new IndexOptions();
        options.unique(true);

        this.index = new IndexModel(keys, options);
    }

    @Override
    public IndexModel indexModel() {
        return this.index;
    }

    @Override
    public Bson queryFrom(final Artist model) {
        return Filters.and(
                Filters.eq(this.official, model.profile().name().official()),
                Filters.eq(this.type, model.type()),
                Filters.eq(this.isni, model.profile().externalIdentifiers().isni())
        );
    }

    @Override
    public List<String> fields() {
        return ImmutableList.of(this.official, this.type, this.isni);
    }

    @Override
    public String toString() {
        return "ArtistUniqueIndex{" +
                "index=" + this.index +
                ", official='" + this.official + '\'' +
                ", isni='" + this.isni + '\'' +
                ", type='" + this.type + '\'' +
                "}";
    }
}
