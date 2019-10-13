
package org.rookit.parser.tag.jaudiotagger;

import com.google.common.base.MoreObjects;
import org.rookit.parser.format.result.TrackSlotParserResult;
import org.rookit.parser.tag.processor.TagProcessor;
import org.rookit.parser.tag.processor.TagProcessorFactory;
import org.rookit.parser.tag.processor.config.TagProcessorConfiguration;
import org.rookit.parser.utils.ParserValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@SuppressWarnings("javadoc")
public abstract class AbstractTagProcessorFactory implements TagProcessorFactory {

    private static final class TempFileTagProcessor implements TagProcessor {

        private final TagProcessor delegate;
        private final Path tempPath;

        TempFileTagProcessor(final TagProcessor delegate, final Path tempPath) {
            this.delegate = delegate;
            this.tempPath = tempPath;
        }

        @Override
        public void close() throws IOException {
            this.delegate.close();
            Files.deleteIfExists(this.tempPath);
        }

        @Override
        public TrackSlotParserResult process(final TrackSlotParserResult result) {
            return this.delegate.process(result);
        }

        @Override
        public TagProcessorConfiguration getConfig() {
            return this.delegate.getConfig();
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("delegate", this.delegate)
                    .add("tempPath", this.tempPath)
                    .toString();
        }
    }

    protected static final Validator VALIDATOR = ParserValidator.getDefault();

    private final Path tempFilesDirectory;

    protected AbstractTagProcessorFactory(final Path tempFilesDirectory) {
        this.tempFilesDirectory = tempFilesDirectory;
    }

    @SuppressWarnings("synthetic-access")
    @Override
    public TagProcessor create(final byte[] data) {
        try {
            final Path tempPath = Files.createTempFile(this.tempFilesDirectory, "tags_", ".tmp");
            return new TempFileTagProcessor(create(tempPath), tempPath);
        } catch (final IOException e) {
            return VALIDATOR.handleException().inputOutputException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tempFilesDirectory", this.tempFilesDirectory)
                .toString();
    }
}
