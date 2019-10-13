package org.rookit.mongodb.gridfs;

import org.bson.types.ObjectId;
import org.immutables.value.Value;
import org.rookit.api.dm.key.Key;

@FunctionalInterface
@Value.Immutable
@Value.Style(canBuild = "isBuildable")
public interface GridFsBiStreamKey extends Key {

    ObjectId id();
}
