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

import com.google.common.collect.ImmutableSet;
import com.kekstudio.musictheory.Key;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.GenericTrackMetaType;
import org.rookit.api.dm.track.Track;
import org.rookit.api.dm.track.TypeTrack;
import org.rookit.api.storage.query.GenericTrackQuery;
import org.rookit.mongodb.query.MongoQueryResultFactory;
import org.rookit.mongodb.query.genre.AbstractMongoGenreableQuery;

import java.util.Collection;

abstract class AbstractMongoTrackQuery<E extends Track, Q extends GenericTrackQuery<E, Q>,
        P extends GenericTrackMetaType<E>> extends AbstractMongoGenreableQuery<E, Q, P>
        implements GenericTrackQuery<E, Q> {

    AbstractMongoTrackQuery(final P properties,
                            final Bson initialClause,
                            final MongoQueryResultFactory<E> resultFactory) {
        super(properties, initialClause, resultFactory);
    }

    @Override
    public Q withNoArtistsMainArtists(final Artist absent) {
        final String propertyName = properties().artists().mainArtists().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAudioFeaturesLive(final boolean audioFeaturesLive) {
        final String propertyName = properties().audio().features().live().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesLive));
    }

    @Override
    public Q withNoLyricsText() {
        final String propertyName = properties().lyrics().text().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAudioFeaturesValence(final double audioFeaturesValence) {
        final String propertyName = properties().audio().features().valence().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesValence));
    }

    @Override
    public Q withArtistsProducers(final Artist present) {
        return withArtistsProducers(ImmutableSet.of(present));
    }

    @Override
    public Q withNoAudioFeaturesLive() {
        final String propertyName = properties().audio().features().live().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withLyricsExplicit(final boolean lyricsExplicit) {
        final String propertyName = properties().lyrics().explicit().propertyName();

        return addQueryClause(Filters.eq(propertyName, lyricsExplicit));
    }

    @Override
    public Q withArtistsMainArtists(final Collection<Artist> present) {
        final String propertyName = properties().artists().mainArtists().propertyName();
        return addQueryClause(Filters.all(propertyName, present));
    }

    @Override
    public Q withArtistsProducers(final Collection<Artist> present) {
        final String propertyName = properties().artists().producers().propertyName();

        return addQueryClause(Filters.all(propertyName, present));
    }

    @Override
    public Q withNoArtistsMainArtists(final Collection<Artist> absent) {
        final String propertyName = properties().artists().mainArtists().propertyName();

        return addQueryClause(Filters.nin(propertyName, absent));
    }

    @Override
    public Q withNoArtistsProducers(final Collection<Artist> absent) {
        final String propertyName = properties().artists().producers().propertyName();

        return addQueryClause(Filters.nin(propertyName, absent));
    }

    @Override
    public Q withAnyAudioFeaturesTrackKey() {
        final String propertyName = properties().audio().features().trackKey().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withNoAudioFeaturesInstrumental() {
        final String propertyName = properties().audio().features().instrumental().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withNoAudioFeaturesDanceability() {
        final String propertyName = properties().audio().features().danceability().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAnyAudioFeaturesValence() {
        final String propertyName = properties().audio().features().valence().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAudioFeaturesInstrumental(final boolean audioFeaturesInstrumental) {
        final String propertyName = properties().audio().features().instrumental().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesInstrumental));
    }

    @Override
    public Q withAudioFeaturesBpm(final short audioFeaturesBpm) {
        final String propertyName = properties().audio().features().bpm().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesBpm));
    }

    @Override
    public Q withAnyAudioFeaturesEnergy() {
        final String propertyName = properties().audio().features().energy().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAnyLyricsText() {
        final String propertyName = properties().lyrics().text().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAudioFeaturesTrackKey(final Key audioFeaturesTrackKey) {
        final String propertyName = properties().audio().features().trackKey().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesTrackKey));
    }

    @Override
    public Q withAnyAudioFeaturesBpm() {
        final String propertyName = properties().audio().features().bpm().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withNoArtistsFeatures(final Collection<Artist> absent) {
        final String propertyName = properties().artists().features().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAudioContent(final BiStream audioContent) {
        final String propertyName = properties().audio().content().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioContent));
    }

    @Override
    public Q withAnyAudioFeaturesAcoustic() {
        final String propertyName = properties().audio().features().acoustic().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAnyAudioFeaturesDanceability() {
        final String propertyName = properties().audio().features().danceability().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withTitleSingleTitleTitle(final String titleSingleTitleTitle) {
        final String propertyName = properties().title().singleTitle().title().propertyName();

        return addQueryClause(Filters.eq(propertyName, titleSingleTitleTitle));
    }

    @Override
    public Q withAnyLyricsExplicit() {
        final String propertyName = properties().lyrics().explicit().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withAudioFeaturesEnergy(final double audioFeaturesEnergy) {
        final String propertyName = properties().audio().features().energy().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesEnergy));
    }

    @Override
    public Q withNoAudioFeaturesAcoustic() {
        final String propertyName = properties().audio().features().acoustic().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withNoAudioFeaturesBpm() {
        final String propertyName = properties().audio().features().bpm().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAudioFeaturesAcoustic(final boolean audioFeaturesAcoustic) {
        final String propertyName = properties().audio().features().acoustic().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesAcoustic));
    }

    @Override
    public Q withAnyAudioFeaturesInstrumental() {
        final String propertyName = properties().audio().features().instrumental().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withArtistsFeatures(final Collection<Artist> present) {
        final String propertyName = properties().artists().features().propertyName();

        return addQueryClause(Filters.all(propertyName, present));
    }

    @Override
    public Q withNoAudioFeaturesEnergy() {
        final String propertyName = properties().audio().features().energy().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAnyAudioFeaturesLive() {
        final String propertyName = properties().audio().features().live().propertyName();

        return addQueryClause(Filters.exists(propertyName));
    }

    @Override
    public Q withNoAudioFeaturesValence() {
        final String propertyName = properties().audio().features().valence().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withArtistsFeatures(final Artist present) {
        return withArtistsFeatures(ImmutableSet.of(present));
    }

    @Override
    public Q withLyricsText(final String lyricsText) {
        final String propertyName = properties().lyrics().text().propertyName();

        return addQueryClause(Filters.eq(propertyName, lyricsText));
    }

    @Override
    public Q withArtistsMainArtists(final Artist present) {
        return withArtistsMainArtists(ImmutableSet.of(present));
    }

    @Override
    public Q withNoAudioFeaturesTrackKey() {
        final String propertyName = properties().audio().features().trackKey().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withAudioFeaturesDanceability(final double audioFeaturesDanceability) {
        final String propertyName = properties().audio().features().danceability().propertyName();

        return addQueryClause(Filters.eq(propertyName, audioFeaturesDanceability));
    }

    @Override
    public Q withNoLyricsExplicit() {
        final String propertyName = properties().lyrics().explicit().propertyName();

        return addQueryClause(Filters.exists(propertyName, false));
    }

    @Override
    public Q withNoArtistsProducers(final Artist absent) {
        return withNoArtistsProducers(ImmutableSet.of(absent));
    }

    @Override
    public Q withNoArtistsFeatures(final Artist absent) {
        return withNoArtistsFeatures(ImmutableSet.of(absent));
    }

    @Override
    public Q withType(final TypeTrack type) {
        return addQueryClause(Filters.eq(properties().type().propertyName(), type));
    }
}
