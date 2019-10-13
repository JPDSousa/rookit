package org.rookit.mongodb.gridfs;

import com.google.inject.Provider;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.rookit.api.bistream.BiStreamFactory;
import org.rookit.api.dm.GenericRookitModelMetaType;
import org.rookit.api.dm.key.Key;
import org.rookit.failsafe.Failsafe;

abstract class AbstractBiStreamFactoryProvider implements Provider<BiStreamFactory<Key>> {

    private final MongoDatabase database;
    private final String bucketName;
    private final GenericRookitModelMetaType<?> properties;
    private final Failsafe failsafe;

    AbstractBiStreamFactoryProvider(final MongoDatabase database,
                                    final String bucketName,
                                    final GenericRookitModelMetaType<?> properties,
                                    final Failsafe failsafe) {
        this.database = database;
        this.bucketName = bucketName;
        this.properties = properties;
        this.failsafe = failsafe;
    }

    @Override
    public BiStreamFactory<Key> get() {
        final GridFSBucket bucket = GridFSBuckets.create(this.database, this.bucketName);
        return new GenericGridFsBiStreamFactory(new GridFsBiStreamFactoryImpl(bucket, this.properties, this.failsafe));
    }

    @Override
    public String toString() {
        return "AbstractBiStreamFactoryProvider{" +
                "database=" + this.database +
                ", bucketName='" + this.bucketName + '\'' +
                ", properties=" + this.properties +
                ", injector=" + this.failsafe +
                "}";
    }
}
