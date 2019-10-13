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
package org.rookit.mongodb.update.filter;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.mongodb.update.filter.album.MongoAlbumUpdateFilterQueryModule;
import org.rookit.mongodb.update.filter.artist.MongoArtistUpdateFilterQueryModule;
import org.rookit.mongodb.update.filter.genre.MongoGenreUpdateFilterQueryModule;
import org.rookit.mongodb.update.filter.play.MongoPlaylistUpdateFilterQueryModule;
import org.rookit.mongodb.update.filter.track.MongoTrackUpdateFilterQueryModule;

public final class MongoUpdateFilterQueryModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new MongoUpdateFilterQueryModule(),
            MongoAlbumUpdateFilterQueryModule.getModule(),
            MongoPlaylistUpdateFilterQueryModule.getModule(),
            MongoArtistUpdateFilterQueryModule.getModule(),
            MongoTrackUpdateFilterQueryModule.getModule(),
            MongoGenreUpdateFilterQueryModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private MongoUpdateFilterQueryModule() {}

    @Override
    protected void configure() {

    }
}
