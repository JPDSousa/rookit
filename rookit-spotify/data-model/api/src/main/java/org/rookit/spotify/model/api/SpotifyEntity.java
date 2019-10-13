package org.rookit.spotify.model.api;

import java.net.URI;
import java.net.URL;
import java.util.Map;

@SuppressWarnings("javadoc")
public interface SpotifyEntity {

    TypeSpotifyEntity type();

    String id();

    URL href();

    Map<String, String> externalUrls();

    URI uri();

}
