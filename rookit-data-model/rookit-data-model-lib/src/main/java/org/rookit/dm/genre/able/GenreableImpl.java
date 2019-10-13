
package org.rookit.dm.genre.able;

import com.google.common.collect.Sets;
import org.rookit.api.dm.genre.Genre;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.play.able.Playable;
import org.rookit.dm.play.able.DelegatePlayable;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("javadoc")
@NotThreadSafe
final class GenreableImpl extends DelegatePlayable implements Genreable {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GenreableImpl.class);
    
    /**
     * Genres of the album
     */
    private Collection<Genre> genres;
    
    private final Failsafe failsafe;

    protected GenreableImpl(final Playable playable,
                            final Failsafe failsafe) {
        super(playable);
        this.failsafe = failsafe;
        this.genres = Sets.newLinkedHashSet();
    }

    @Override
    public void addGenre(final Genre genre) {
        this.failsafe.checkArgument().isNotNull(logger, genre, "genre");
        this.genres.add(genre);
    }

    @Override
    public void addGenres(final Collection<Genre> genres) {
        this.failsafe.checkArgument().isNotNull(logger, genres, "genres");
        this.genres.addAll(genres);
    }

    @Override
    public void clearGenres() {
        this.genres.clear();
    }

    @Override
    public Collection<Genre> allGenres() {
        return genres();
    }

    @Override
    public Collection<Genre> genres() {
        return Collections.unmodifiableCollection(this.genres);
    }

    @Override
    public void removeGenre(final Genre genre) {
        this.failsafe.checkArgument().isNotNull(logger, genre, "genre");
        this.genres.remove(genre);
    }

    @Override
    public void removeGenres(final Collection<Genre> genres) {
        this.failsafe.checkArgument().isNotNull(logger, genres, "genres");
        this.genres.removeAll(genres);
    }

    @Override
    public void setGenres(final Collection<Genre> genres) {
        this.failsafe.checkArgument().isNotNull(logger, genres, "genres");
        this.genres = Sets.newLinkedHashSet(genres);
    }

    @Override
    public String toString() {
        return "GenreableImpl{" +
                "genres=" + this.genres +
                ", injector=" + this.failsafe +
                "} " + super.toString();
    }
}
