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
package org.rookit.mongodb.query.track;

import com.google.inject.Inject;
import org.bson.conversions.Bson;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.VersionTrack;
import org.rookit.api.dm.track.VersionTrackMetaType;
import org.rookit.api.storage.query.TrackQuery;
import org.rookit.api.storage.query.VersionTrackQuery;
import org.rookit.mongodb.guice.Mongo;
import org.rookit.mongodb.query.MongoQueryFactory;
import org.rookit.mongodb.query.MongoQueryResultFactory;

final class MongoVersionTrackQueryFactory implements MongoQueryFactory<VersionTrack, VersionTrackQuery> {

    private final VersionTrackMetaType properties;
    private final MongoQueryResultFactory<VersionTrack> resultFactory;
    private final MongoQueryFactory<Track, TrackQuery> queryFactory;

    @Inject
    private MongoVersionTrackQueryFactory(@Mongo final VersionTrackMetaType properties,
                                          final MongoQueryResultFactory<VersionTrack> resultFactory,
                                          final MongoQueryFactory<Track, TrackQuery> queryFactory) {
        this.properties = properties;
        this.resultFactory = resultFactory;
        this.queryFactory = queryFactory;
    }

    @Override
    public VersionTrackQuery create(final Bson query) {
        return new MongoVersionTrackQuery(this.properties, query, this.resultFactory, this.queryFactory);
    }

    @Override
    public String toString() {
        return "MongoVersionTrackQueryFactory{" +
                "properties=" + this.properties +
                ", resultFactory=" + this.resultFactory +
                ", queryFactory=" + this.queryFactory +
                "}";
    }
}
