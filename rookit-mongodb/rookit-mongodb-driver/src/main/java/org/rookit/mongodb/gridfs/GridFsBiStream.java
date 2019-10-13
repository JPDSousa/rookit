
package org.rookit.mongodb.gridfs;

import com.mongodb.client.gridfs.GridFSBucket;
import org.apache.commons.io.IOUtils;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.rookit.api.bistream.BiStream;
import org.rookit.failsafe.Failsafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

@SuppressWarnings("javadoc")
final class GridFsBiStream implements BiStream {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(GridFsBiStream.class);

    private final Failsafe failsafe;
    private final GridFSBucket bucket;
    private final ObjectId id;

    GridFsBiStream(final GridFSBucket bucket,
                   final ObjectId id,
                   final String idProperty,
                   final Failsafe failsafe) {
        failsafe.checkArgument().isNotNull(logger, bucket, "bucket");
        failsafe.checkArgument().isNotNull(logger, id, idProperty);
        this.failsafe = failsafe;
        this.bucket = bucket;
        this.id = id;
    }

    @Override
    public void clear() {
        this.bucket.delete(this.id);
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(this.id);
    }

    @Override
    public InputStream readFrom() {
        return this.bucket.openDownloadStream(this.id);
    }

    @Override
    public OutputStream writeTo() {
        logger.trace("Creating output stream over bucket {} and id {}", this.bucket, this.id);
        return this.bucket.openUploadStream(new BsonObjectId(this.id), this.id.toHexString());
    }

    @Override
    public void copyFrom(final BiStream biStream) {
        if (!equals(biStream)) {
            try {
                IOUtils.copy(biStream.readFrom(), writeTo());
            } catch (final IOException e) {
                this.failsafe.handleException().inputOutputException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "GridFsBiStream{" +
                "injector=" + this.failsafe +
                ", bucket=" + this.bucket +
                ", id=" + this.id +
                "}";
    }
}
