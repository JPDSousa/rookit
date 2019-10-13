package org.rookit.parser.utils.artist;

import one.util.streamex.StreamEx;

import java.util.Collection;

@FunctionalInterface
public interface ArtistNameInterpreter {

    Collection<String> interpret(final String context);

    default StreamEx<String> interpretAndStream(final String context) {
        return StreamEx.of(interpret(context).stream());
    }
}
