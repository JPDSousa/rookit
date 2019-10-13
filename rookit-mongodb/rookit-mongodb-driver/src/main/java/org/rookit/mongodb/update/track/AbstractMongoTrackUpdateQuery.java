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

import com.google.common.collect.ImmutableList;
import com.kekstudio.musictheory.Key;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.artist.Artist;
import org.rookit.api.dm.track.GenericTrackMetaType;
import org.rookit.api.dm.track.Track;
import org.rookit.api.storage.update.GenericTrackUpdateQuery;
import org.rookit.api.storage.update.filter.GenericTrackUpdateFilterQuery;
import org.rookit.storage.update.filter.UpdateFilterQueryFactory;
import org.rookit.mongodb.update.genre.able.AbstractMongoGenreableUpdateQuery;

import java.util.Collection;
import java.util.List;

abstract class AbstractMongoTrackUpdateQuery<E extends Track, Q extends GenericTrackUpdateQuery<Q, S>,
        S extends GenericTrackUpdateFilterQuery<S>, P extends GenericTrackMetaType<E>>
        extends AbstractMongoGenreableUpdateQuery<E, Q, S, P> implements GenericTrackUpdateQuery<Q, S> {


    AbstractMongoTrackUpdateQuery(final UpdateFilterQueryFactory<S> filterQueryFactory,
                                  final List<Bson> updates,
                                  final P properties) {
        super(filterQueryFactory, updates, properties);
    }

    @Override
    public Q addAllArtistsMainArtists(final Collection<Artist> artistsMainArtists) {
        final String propertyName = properties().artists().mainArtists().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(artistsMainArtists)));
    }

    @Override
    public Q removeAudioFeaturesTrackKey() {
        final String propertyName = properties().audio().features().trackKey().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q addArtistsProducers(final Artist artistsProducers) {
        return addAllArtistsProducers(ImmutableList.of(artistsProducers));
    }

    @Override
    public Q removeAllArtistsFeatures(final Collection<Artist> artistsFeatures) {
        final String propertyName = properties().artists().features().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(artistsFeatures)));
    }

    @Override
    public Q removeArtistsMainArtists(final Artist artistsMainArtists) {
        return removeAllArtistsMainArtists(ImmutableList.of(artistsMainArtists));
    }

    @Override
    public Q addAllArtistsProducers(final Collection<Artist> artistsProducers) {
        final String propertyName = properties().artists().producers().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(artistsProducers)));
    }

    @Override
    public Q setAudioFeaturesInstrumental(final boolean audioFeaturesInstrumental) {
        final String propertyName = properties().audio().features().instrumental().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesInstrumental));
    }

    @Override
    public Q removeArtistsFeatures(final Artist artistsFeatures) {
        return removeAllArtistsFeatures(ImmutableList.of(artistsFeatures));
    }

    @Override
    public Q setLyricsText(final String lyricsText) {
        final String propertyName = properties().lyrics().text().propertyName();
        return appendUpdate(Updates.set(propertyName, lyricsText));
    }

    @Override
    public Q removeArtistsProducers(final Artist artistsProducers) {
        return removeAllArtistsProducers(ImmutableList.of(artistsProducers));
    }

    @Override
    public Q setAudioFeaturesEnergy(final double audioFeaturesEnergy) {
        final String propertyName = properties().audio().features().energy().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesEnergy));
    }

    @Override
    public Q setLyricsExplicit(final boolean lyricsExplicit) {
        final String propertyName = properties().lyrics().explicit().propertyName();
        return appendUpdate(Updates.set(propertyName, lyricsExplicit));
    }

    @Override
    public Q setAudioFeaturesDanceability(final double audioFeaturesDanceability) {
        final String propertyName = properties().audio().features().danceability().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesDanceability));
    }

    @Override
    public Q addArtistsFeatures(final Artist artistsFeatures) {
        return addAllArtistsFeatures(ImmutableList.of(artistsFeatures));
    }

    @Override
    public Q setAudioContent(final BiStream audioContent) {
        final String propertyName = properties().audio().content().propertyName();
        return appendUpdate(Updates.set(propertyName, audioContent));
    }

    @Override
    public Q removeAllArtistsProducers(final Collection<Artist> artistsProducers) {
        final String propertyName = properties().artists().producers().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(artistsProducers)));
    }

    @Override
    public Q setAudioFeaturesValence(final double audioFeaturesValence) {
        final String propertyName = properties().audio().features().valence().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesValence));
    }

    @Override
    public Q setAudioFeaturesBpm(final short audioFeaturesBpm) {
        final String propertyName = properties().audio().features().bpm().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesBpm));
    }

    @Override
    public Q removeAllArtistsMainArtists(final Collection<Artist> artistsMainArtists) {
        final String propertyName = properties().artists().mainArtists().propertyName();
        return appendUpdate(Updates.pullAll(propertyName, ImmutableList.copyOf(artistsMainArtists)));
    }

    @Override
    public Q setAudioFeaturesTrackKey(final Key audioFeaturesTrackKey) {
        final String propertyName = properties().audio().features().trackKey().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesTrackKey));
    }

    @Override
    public Q addAllArtistsFeatures(final Collection<Artist> artistsFeatures) {
        final String propertyName = properties().artists().features().propertyName();
        return appendUpdate(Updates.addEachToSet(propertyName, ImmutableList.copyOf(artistsFeatures)));
    }

    @Override
    public Q setAudioFeaturesLive(final boolean audioFeaturesLive) {
        final String propertyName = properties().audio().features().live().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesLive));
    }

    @Override
    public Q removeLyricsText() {
        final String propertyName = properties().lyrics().text().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q setAudioFeaturesAcoustic(final boolean audioFeaturesAcoustic) {
        final String propertyName = properties().audio().features().acoustic().propertyName();
        return appendUpdate(Updates.set(propertyName, audioFeaturesAcoustic));
    }

    @Override
    public Q addArtistsMainArtists(final Artist artistsMainArtists) {
        return addAllArtistsMainArtists(ImmutableList.of(artistsMainArtists));
    }

    @Override
    public Q removeAudioFeaturesBpm() {
        final String propertyName = properties().audio().features().bpm().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeLyricsExplicit() {
        final String propertyName = properties().lyrics().explicit().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAudioFeaturesEnergy() {
        final String propertyName = properties().audio().features().energy().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAudioFeaturesAcoustic() {
        final String propertyName = properties().audio().features().acoustic().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAudioFeaturesLive() {
        final String propertyName = properties().audio().features().live().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAudioFeaturesInstrumental() {
        final String propertyName = properties().audio().features().instrumental().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAudioFeaturesDanceability() {
        final String propertyName = properties().audio().features().danceability().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }

    @Override
    public Q removeAudioFeaturesValence() {
        final String propertyName = properties().audio().features().valence().propertyName();
        return appendUpdate(Updates.unset(propertyName));
    }
}
