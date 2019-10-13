package org.rookit.parser.tag.processor.config;

import org.immutables.value.Value;

import java.util.Collection;

@Value.Immutable
@Value.Style(canBuild = "isBuildable")
public interface TagProcessorConfiguration {

    /**
     * Standard date format, used when for parsing and formatting release dates
     * related to albums
     */
    // TODO this should be defined through configuration
    String[] SUPPORTED_FORMATS = {"dd-MM-yyyy", "yyyy"};

    boolean extractDate();

    Collection<String> supportedDateFormats();
}
