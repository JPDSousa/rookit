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
package org.rookit.mongodb.update.filter.track;

import com.kekstudio.musictheory.Key;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.storage.query.GenericTrackQuery;
import org.rookit.api.storage.update.filter.GenericTrackUpdateFilterQuery;
import org.rookit.mongodb.update.filter.genre.able.AbstractMongoGenreableUpdateFilterQuery;

import java.util.Collection;

abstract class AbstractMongoTrackUpdateFilterQuery<E extends Track, Q extends GenericTrackQuery<E, Q>,
        U extends GenericTrackUpdateFilterQuery<U>> extends AbstractMongoGenreableUpdateFilterQuery<E, Q, U> 
        implements GenericTrackUpdateFilterQuery<U> {

    AbstractMongoTrackUpdateFilterQuery(final MongoCollection<E> collection, final Bson update, final Q query) {
        super(collection, update, query);
    }

    @Override
    public U withAudioFeaturesLive(final boolean audioFeaturesLive) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesLive(audioFeaturesLive));
    }

    @Override
    public U withNoArtistsMainArtists(final Artist absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtistsMainArtists(absent));
    }

    @Override
    public U withNoLyricsText() {
        return appendClause(mongoQuery -> mongoQuery.withNoLyricsText());
    }

    @Override
    public U withAudioFeaturesValence(final double audioFeaturesValence) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesValence(audioFeaturesValence));
    }

    @Override
    public U withArtistsProducers(final Artist present) {
        return appendClause(mongoQuery -> mongoQuery.withArtistsProducers(present));
    }

    @Override
    public U withNoAudioFeaturesLive() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesLive());
    }

    @Override
    public U withLyricsExplicit(final boolean lyricsExplicit) {
        return appendClause(mongoQuery -> mongoQuery.withLyricsExplicit(lyricsExplicit));
    }

    @Override
    public U withArtistsMainArtists(final Collection<Artist> present) {
        return appendClause(mongoQuery -> mongoQuery.withArtistsMainArtists(present));
    }

    @Override
    public U withArtistsProducers(final Collection<Artist> present) {
        return appendClause(mongoQuery -> mongoQuery.withArtistsProducers(present));
    }

    @Override
    public U withNoArtistsMainArtists(final Collection<Artist> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtistsMainArtists(absent));
    }

    @Override
    public U withNoArtistsProducers(final Collection<Artist> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtistsProducers(absent));
    }

    @Override
    public U withNoAudioFeaturesInstrumental() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesInstrumental());
    }

    @Override
    public U withAnyAudioFeaturesTrackKey() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesTrackKey());
    }

    @Override
    public U withNoAudioFeaturesDanceability() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesDanceability());
    }

    @Override
    public U withAnyAudioFeaturesValence() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesValence());
    }

    @Override
    public U withAudioFeaturesInstrumental(final boolean audioFeaturesInstrumental) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesInstrumental(audioFeaturesInstrumental));
    }

    @Override
    public U withAudioFeaturesBpm(final short audioFeaturesBpm) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesBpm(audioFeaturesBpm));
    }

    @Override
    public U withAnyAudioFeaturesEnergy() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesEnergy());
    }

    @Override
    public U withAudioFeaturesTrackKey(final Key audioFeaturesTrackKey) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesTrackKey(audioFeaturesTrackKey));
    }

    @Override
    public U withAnyLyricsText() {
        return appendClause(mongoQuery -> mongoQuery.withAnyLyricsText());
    }

    @Override
    public U withAnyAudioFeaturesBpm() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesBpm());
    }

    @Override
    public U withAudioContent(final BiStream audioContent) {
        return appendClause(mongoQuery -> mongoQuery.withAudioContent(audioContent));
    }

    @Override
    public U withAnyAudioFeaturesAcoustic() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesAcoustic());
    }

    @Override
    public U withNoArtistsFeatures(final Collection<Artist> absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtistsFeatures(absent));
    }

    @Override
    public U withAnyAudioFeaturesDanceability() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesDanceability());
    }

    @Override
    public U withTitleSingleTitleTitle(final String titleSingleTitleTitle) {
        return appendClause(mongoQuery -> mongoQuery.withTitleSingleTitleTitle(titleSingleTitleTitle));
    }

    @Override
    public U withAudioFeaturesEnergy(final double audioFeaturesEnergy) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesEnergy(audioFeaturesEnergy));
    }

    @Override
    public U withNoAudioFeaturesAcoustic() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesAcoustic());
    }

    @Override
    public U withAnyLyricsExplicit() {
        return appendClause(mongoQuery -> mongoQuery.withAnyLyricsExplicit());
    }

    @Override
    public U withNoAudioFeaturesBpm() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesBpm());
    }

    @Override
    public U withAudioFeaturesAcoustic(final boolean audioFeaturesAcoustic) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesAcoustic(audioFeaturesAcoustic));
    }

    @Override
    public U withAnyAudioFeaturesInstrumental() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesInstrumental());
    }

    @Override
    public U withNoAudioFeaturesEnergy() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesEnergy());
    }

    @Override
    public U withAnyAudioFeaturesLive() {
        return appendClause(mongoQuery -> mongoQuery.withAnyAudioFeaturesLive());
    }

    @Override
    public U withArtistsFeatures(final Collection<Artist> present) {
        return appendClause(mongoQuery -> mongoQuery.withArtistsFeatures(present));
    }

    @Override
    public U withNoAudioFeaturesValence() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesValence());
    }

    @Override
    public U withLyricsText(final String lyricsText) {
        return appendClause(mongoQuery -> mongoQuery.withLyricsText(lyricsText));
    }

    @Override
    public U withArtistsFeatures(final Artist present) {
        return appendClause(mongoQuery -> mongoQuery.withArtistsFeatures(present));
    }

    @Override
    public U withNoAudioFeaturesTrackKey() {
        return appendClause(mongoQuery -> mongoQuery.withNoAudioFeaturesTrackKey());
    }

    @Override
    public U withArtistsMainArtists(final Artist present) {
        return appendClause(mongoQuery -> mongoQuery.withArtistsMainArtists(present));
    }

    @Override
    public U withAudioFeaturesDanceability(final double audioFeaturesDanceability) {
        return appendClause(mongoQuery -> mongoQuery.withAudioFeaturesDanceability(audioFeaturesDanceability));
    }

    @Override
    public U withNoLyricsExplicit() {
        return appendClause(mongoQuery -> mongoQuery.withNoLyricsExplicit());
    }

    @Override
    public U withNoArtistsProducers(final Artist absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtistsProducers(absent));
    }

    @Override
    public U withNoArtistsFeatures(final Artist absent) {
        return appendClause(mongoQuery -> mongoQuery.withNoArtistsFeatures(absent));
    }

    @Override
    public U withType(final TypeTrack type) {
        return appendClause(mongoQuery -> mongoQuery.withType(type));
    }

}
