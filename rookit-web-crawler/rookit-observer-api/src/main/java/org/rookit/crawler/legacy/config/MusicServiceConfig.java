package org.rookit.crawler.legacy.config;

@SuppressWarnings("javadoc")
public interface MusicServiceConfig {

    public LastFMConfig lastfm();

    public String cachePath();

    public SpotifyConfig spotify();

    public String formatsPath();

}
