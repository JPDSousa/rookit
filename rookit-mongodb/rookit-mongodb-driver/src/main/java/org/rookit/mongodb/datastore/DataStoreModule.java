package org.rookit.mongodb.datastore;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import org.rookit.mongodb.datastore.collection.CollectionModule;
import org.rookit.mongodb.datastore.dependency.DependencyModule;
import org.rookit.mongodb.datastore.index.MongoIndexModule;
import org.rookit.mongodb.datastore.operation.OperationModule;

@SuppressWarnings("MethodMayBeStatic")
public final class DataStoreModule extends AbstractModule {

    private static final Module MODULE = Modules.combine(
            new DataStoreModule(),
            DependencyModule.getModule(),
            CollectionModule.getModule(),
            MongoIndexModule.getModule(),
            OperationModule.getModule()
    );

    public static Module getModule() {
        return MODULE;
    }

    private DataStoreModule() {}

    @Override
    protected void configure() {
        bind(ArtistDataStore.class).to(MongoArtistDataStore.class);
        bind(AlbumDataStore.class).to(MongoAlbumDataStore.class);
        bind(GenreDataStore.class).to(MongoGenreDataStore.class);
        bind(TrackDataStore.class).to(MongoTrackDataStore.class);
        bind(PlaylistDataStore.class).to(MongoPlaylistDataStore.class);
    }

}
