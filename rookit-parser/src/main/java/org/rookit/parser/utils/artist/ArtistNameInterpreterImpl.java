package org.rookit.parser.utils.artist;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.rookit.api.dm.artist.Artist;

import java.util.Collection;
import java.util.regex.Pattern;

class ArtistNameInterpreterImpl implements ArtistNameInterpreter {

    private static final Pattern SPLIT_REGEX = Pattern
            .compile("\\s&\\s|,|\\sx\\s|\\sX\\s|\\svs\\s|\\sVs.\\s|\\sVs\\s|\\svs.\\s");

    private final Collection<String> initialArtistNames;

    @Inject
    private ArtistNameInterpreterImpl(@InitialArtistNames final Collection<String> initialArtistNames) {
        this.initialArtistNames = initialArtistNames;
    }

    @Override
    public Collection<String> interpret(final String context) {
        final Collection<String> artistNames = Sets.newHashSet();
        String str = context;
        int index;

        for (String special : this.initialArtistNames) {
            final StringBuilder builder;

            if (StringUtils.containsIgnoreCase(str, special)
                    || StringUtils.containsIgnoreCase(str, special.replace(" & ", "&"))) {
                artistNames.add(special);
                index = StringUtils.indexOfIgnoreCase(str, special);

                if (index < 0) {
                    special = special.replace(" & ", "&");
                    index = StringUtils.indexOfIgnoreCase(str, special);
                }

                builder = new StringBuilder(32);
                builder.append(str.substring(0, index));
                builder.append(str.substring(index + special.length()));
                str = builder.toString();
            }
        }

        for (final String artist : SPLIT_REGEX.split(str)) {
            if (!artist.isEmpty() && !artist.equalsIgnoreCase(Artist.UNKNOWN_ARTISTS)) {
                artistNames.add(artist.trim());
            }
        }

        return artistNames;
    }
}
