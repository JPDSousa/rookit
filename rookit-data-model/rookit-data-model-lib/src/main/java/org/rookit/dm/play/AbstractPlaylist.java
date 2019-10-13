
package org.rookit.dm.play;

import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.genre.able.Genreable;
import org.rookit.api.dm.play.Playlist;
import org.rookit.dm.genre.able.DelegateGenreable;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

abstract class AbstractPlaylist extends DelegateGenreable implements Playlist {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbstractPlaylist.class);

    private final Failsafe failsafe;
    private final String name;
    private final BiStream image;

    AbstractPlaylist(final Genreable genreable,
                     final String name,
                     final BiStream image,
                     final Failsafe failsafe) {
        super(genreable);
        this.name = name;
        this.image = image;
        this.failsafe = failsafe;
    }

    @Override
    public BiStream image() {
        return this.image;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void setImage(final byte[] image) {
        this.failsafe.checkArgument().isNotNull(logger, image, "image");
        try (final OutputStream output = this.image.writeTo()) {
            output.write(image);
        } catch (final IOException e) {
            this.failsafe.handleException().inputOutputException(e);
        }
    }

    @Override
    public void setImage(final BiStream image) {
        this.failsafe.checkArgument().isNotNull(logger, image, "image");
        this.image.copyFrom(image);
    }

    @Override
    public String toString() {
        return "AbstractPlaylist{" +
                "injector=" + this.failsafe +
                ", className='" + this.name + '\'' +
                ", image=" + this.image +
                "} " + super.toString();
    }
}
