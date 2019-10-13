package org.rookit.parser.format.field;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.ProvidesIntoMap;
import com.google.inject.multibindings.StringMapKey;
import org.rookit.parser.format.SuspiciousTokens;

@SuppressWarnings("MethodMayBeStatic")
public final class FieldModule extends AbstractModule {

    private static final String[] SUSPICIOUS_TITLE_CHARSEQS = {"- ", " -",  "[", "]", "_", "{", "}", "~", "|", "�",
            " vs ", " vs. "};

    private static final Module MODULE = new FieldModule();
    private static final char SEPARATOR = '-';

    public static Module getModule() {
        return MODULE;
    }

    private FieldModule() {}

    @Override
    protected void configure() {
        bind(Joiner.class).annotatedWith(org.rookit.parser.format.Field.class).toInstance(Joiner.on(SEPARATOR));
        final MapBinder<String, Field> fieldMap = MapBinder.newMapBinder(binder(), String.class, Field.class);

        bind(String[].class).annotatedWith(SuspiciousTokens.class).toInstance(SUSPICIOUS_TITLE_CHARSEQS);
        fieldMap.addBinding(AlbumField.NAME).to(AlbumField.class).in(Singleton.class);
        fieldMap.addBinding(TrackTitleField.NAME).to(TrackTitleField.class).in(Singleton.class);

        bindArtistFields(fieldMap);

        fieldMap.addBinding(NumberField.NAME).to(NumberField.class).in(Singleton.class);
        fieldMap.addBinding(GenreField.NAME).to(GenreField.class).in(Singleton.class);

        bindVersionTrackFields(fieldMap);

        fieldMap.addBinding(HiddenTrackTitleField.NAME).to(HiddenTrackTitleField.class).in(Singleton.class);
        fieldMap.addBinding(IgnoreField.NAME).to(IgnoreField.class).in(Singleton.class);
    }

    private static void bindVersionTrackFields(final MapBinder<String, Field> fieldMap) {
        fieldMap.addBinding(VersionTypeField.NAME).to(VersionTypeField.class).in(Singleton.class);
        fieldMap.addBinding(VersionTokenField.NAME).to(VersionTokenField.class).in(Singleton.class);
        fieldMap.addBinding(VersionArtistField.NAME).to(VersionArtistField.class).in(Singleton.class);
    }

    private static void bindArtistFields(final MapBinder<String, Field> fieldMap) {
        fieldMap.addBinding(ArtistField.NAME).to(ArtistField.class).in(Singleton.class);
        fieldMap.addBinding(FeatureField.NAME).to(FeatureField.class).in(Singleton.class);
        fieldMap.addBinding(ProducerField.NAME).to(ProducerField.class).in(Singleton.class);
    }

    @SuppressWarnings({"MethodParameterOfConcreteClass", "TypeMayBeWeakened", "BindingAnnotationWithoutInject"})
    @ProvidesIntoMap
    @StringMapKey("TITLES")
    @Singleton
    Field getTitlesField(final AlbumField albumField,
                         final TrackTitleField titleField,
                         @org.rookit.parser.format.Field final Joiner joiner) {
        return new CompositeField(InitialScore.L2_VALUE, ImmutableList.of(albumField, titleField), joiner);
    }

    @SuppressWarnings({"MethodParameterOfConcreteClass", "TypeMayBeWeakened", "BindingAnnotationWithoutInject"})
    @ProvidesIntoMap
    @StringMapKey("ARTIST_EXTRA")
    @Singleton
    Field getArtistAndVersionArtistField(final ArtistField artistField,
                                         final VersionArtistField versionArtistField,
                                         @org.rookit.parser.format.Field final Joiner joiner) {
        return new CompositeField(InitialScore.L2_VALUE, ImmutableList.of(artistField, versionArtistField), joiner);
    }

}
