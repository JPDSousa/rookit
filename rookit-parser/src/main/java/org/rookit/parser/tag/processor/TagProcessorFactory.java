package org.rookit.parser.tag.processor;

import java.nio.file.Path;

@SuppressWarnings("javadoc")
public interface TagProcessorFactory {

    TagProcessor create(Path path);

    TagProcessor create(byte[] data);

}
