package org.rookit.mongodb.gridfs;

import com.google.common.base.MoreObjects;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.key.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class GenericGridFsBiStreamFactory implements BiStreamFactory<Key> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GenericGridFsBiStreamFactory.class);

    private final GridFsBiStreamFactory factory;

    GenericGridFsBiStreamFactory(final GridFsBiStreamFactory factory) {
        this.factory = factory;
    }

    @Override
    public BiStream create(final Key key) {
        logger.warn("Create by key is not supported on generic factories. Creating empty instead.");
        return this.factory.createEmpty();
    }

    @Override
    public BiStream createEmpty() {
        return this.factory.createEmpty();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("factory", this.factory)
                .toString();
    }
}
