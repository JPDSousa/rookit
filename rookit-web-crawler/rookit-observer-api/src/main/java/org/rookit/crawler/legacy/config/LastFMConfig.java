package org.rookit.crawler.legacy.config;

@SuppressWarnings("javadoc")
public interface LastFMConfig {

    String apiKey();

    String user();

    int levenshteinThreshold();

}
