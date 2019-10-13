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
package org.rookit.mongodb.update.artist;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.rookit.api.storage.update.ArtistUpdateQuery;
import org.rookit.api.storage.update.GroupArtistUpdateQuery;
import org.rookit.api.storage.update.MusicianUpdateQuery;
import org.rookit.storage.update.UpdateQueryFactory;
import org.rookit.api.storage.update.filter.ArtistUpdateFilterQuery;
import org.rookit.api.storage.update.filter.GroupArtistUpdateFilterQuery;
import org.rookit.api.storage.update.filter.MusicianUpdateFilterQuery;
import org.rookit.mongodb.update.MongoUpdateQueryFactory;

public final class MongoArtistUpdateQueryModule extends AbstractModule {
    
    private static final Module MODULE = new MongoArtistUpdateQueryModule();
    
    public static Module getModule() {
        return MODULE;
    }
    
    private MongoArtistUpdateQueryModule() {}
    
    @Override
    protected void configure() {
        bind(new TypeLiteral<UpdateQueryFactory<ArtistUpdateQuery, ArtistUpdateFilterQuery>>() {})
                .to(MongoArtistUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<MongoUpdateQueryFactory<ArtistUpdateQuery, ArtistUpdateFilterQuery>>() {})
                .to(MongoArtistUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<UpdateQueryFactory<GroupArtistUpdateQuery, GroupArtistUpdateFilterQuery>>() {})
                .to(MongoGroupArtistUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<MongoUpdateQueryFactory<GroupArtistUpdateQuery, GroupArtistUpdateFilterQuery>>() {})
                .to(MongoGroupArtistUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<UpdateQueryFactory<MusicianUpdateQuery, MusicianUpdateFilterQuery>>() {})
                .to(MongoMusicianUpdateQueryFactory.class).in(Singleton.class);
        bind(new TypeLiteral<MongoUpdateQueryFactory<MusicianUpdateQuery, MusicianUpdateFilterQuery>>() {})
                .to(MongoMusicianUpdateQueryFactory.class).in(Singleton.class);
    }
}
