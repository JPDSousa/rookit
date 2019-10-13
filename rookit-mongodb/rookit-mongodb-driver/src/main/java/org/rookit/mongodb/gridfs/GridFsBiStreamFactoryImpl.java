
package org.rookit.mongodb.gridfs;

import com.mongodb.client.gridfs.GridFSBucket;
import org.bson.types.ObjectId;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.dm.GenericRookitModelMetaType;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("javadoc")
final class GridFsBiStreamFactoryImpl implements GridFsBiStreamFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GridFsBiStreamFactoryImpl.class);

    private final GridFSBucket bucket;
    private final String idProperty;
    private final Failsafe failsafe;

    GridFsBiStreamFactoryImpl(final GridFSBucket bucket,
                              final GenericRookitModelMetaType<?> properties,
                              final Failsafe failsafe) {
        this.idProperty = properties.id().propertyName();
        this.failsafe = failsafe;
        failsafe.checkArgument().isNotNull(logger, bucket, "bucket");
        this.bucket = bucket;
    }

    @Override
    public BiStream create(final GridFsBiStreamKey key) {
        logger.warn("BiStream creation through {}#createUniqueQuery(Key) is ignored for key {}. " +
                        "Creating empty instead.",
                GridFsBiStreamFactoryImpl.class, key);
        return createEmpty();
    }

    @Override
    public BiStream create(final ObjectId id) {
        return new GridFsBiStream(this.bucket, id, this.idProperty, this.failsafe);
    }

    @Override
    public BiStream createEmpty() {
        return create(new ObjectId());
    }

    @Override
    public String toString() {
        return "GridFsBiStreamFactoryImpl{" +
                "bucket=" + this.bucket +
                ", idProperty='" + this.idProperty + '\'' +
                ", injector=" + this.failsafe +
                "}";
    }
}
