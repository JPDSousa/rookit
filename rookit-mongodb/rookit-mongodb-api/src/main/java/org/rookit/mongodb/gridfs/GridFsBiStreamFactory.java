package org.rookit.mongodb.gridfs;

import org.bson.types.ObjectId;
import org.rookit.api.bistream.BiStream;
import org.rookit.api.bistream.BiStreamFactory;

public interface GridFsBiStreamFactory extends BiStreamFactory<GridFsBiStreamKey> {

    BiStream create(ObjectId id);
}
