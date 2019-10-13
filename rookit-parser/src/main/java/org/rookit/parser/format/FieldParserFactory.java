package org.rookit.parser.format;

import java.util.Collection;

public interface FieldParserFactory {

    FieldParser create(String rawFormat);

    FieldParser create(Collection<String> rawFormats);

}
