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
package org.rookit.mongodb.update.track;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.rookit.api.storage.update.TrackUpdateQuery;
import org.rookit.storage.update.UpdateQueryFactory;
import org.rookit.api.storage.update.VersionTrackUpdateQuery;
import org.rookit.api.storage.update.filter.TrackUpdateFilterQuery;
import org.rookit.api.storage.update.filter.VersionTrackUpdateFilterQuery;
import org.rookit.mongodb.update.MongoUpdateQueryFactory;

public final class MongoTrackUpdateQueryModule extends AbstractModule {

    private static final Module MODULE = new MongoTrackUpdateQueryModule();

    public static Module getModule() {
        return MODULE;
    }

    private MongoTrackUpdateQueryModule() {}

    @Override
    protected void configure() {
        bind(new TypeLiteral<UpdateQueryFactory<TrackUpdateQuery, TrackUpdateFilterQuery>>() {})
                .to(MongoTrackUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<MongoUpdateQueryFactory<TrackUpdateQuery, TrackUpdateFilterQuery>>() {})
                .to(MongoTrackUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<UpdateQueryFactory<VersionTrackUpdateQuery, VersionTrackUpdateFilterQuery>>() {})
                .to(MongoVersionTrackUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<MongoUpdateQueryFactory<VersionTrackUpdateQuery, VersionTrackUpdateFilterQuery>>() {})
                .to(MongoVersionTrackUpdateQueryFactory.class).in(Singleton.class);
    }
}
