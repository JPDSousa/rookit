
package org.rookit.dm.bistream;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.rookit.failsafe.Failsafe;
import org.rookit.test.AbstractUnitTest;
import org.rookit.test.exception.ResourceCreationException;
import org.rookit.test.junit.categories.FastTest;
import org.rookit.test.junit.categories.UnitTest;
import org.rookit.test.mixin.ByteArrayMixin;
import org.rookit.test.preconditions.TestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
@FastTest
@UnitTest
public class FileBiStreamTest extends AbstractUnitTest<FileBiStream> implements ByteArrayMixin {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(FileBiStreamTest.class);

    private static final short FILENAME_LENGTH = 20;
    private static final int CAPACITY = 128;
    private static final RandomStringGenerator RANDOM_STRINGS = new RandomStringGenerator.Builder()
            .build();

    private final Failsafe failsafe = Mockito.mock(Failsafe.class);

    private Path tempFile;

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public FileBiStream doCreateTestResource() throws ResourceCreationException {
        try {
            this.tempFile = this.temporaryFolder.createFile(RANDOM_STRINGS.generate(FILENAME_LENGTH)).toPath();
            return new FileBiStream(this.failsafe, this.tempFile);
        } catch (final IOException e) {
            throw new ResourceCreationException(e);
        }
    }

    @Test
    public final void testClear() throws IOException {
        Files.write(this.tempFile, createRandomArray(CAPACITY));
        this.testResource.clear();

        assertThat(Files.notExists(this.tempFile))
                .as("The remaining size")
                .isTrue();
    }

    @Test
    public final void testRead() throws IOException {
        TestPreconditions.assure().is(logger, this.testResource.isEmpty(), "The test resource must be empty");

        final byte[] expectedContent = createRandomArray(CAPACITY / 2);
        final byte[] actualContent = new byte[CAPACITY / 2];
        Files.write(this.tempFile, expectedContent);

        try (final InputStream input = this.testResource.readFrom()) {
            final int readBytes = input.read(actualContent);
            assertThat(readBytes)
                    .as("The number of bytes read")
                    .isPositive();
            assertThat(actualContent)
                    .as("The content read")
                    .containsExactly(expectedContent);
        }
    }

    @Test
    public final void testWrite() throws IOException {
        TestPreconditions.assure().is(logger, this.testResource.isEmpty(), "The test resource must be empty");

        final byte[] expectedContent = createRandomArray(CAPACITY / 2);

        try (final OutputStream output = this.testResource.writeTo()) {
            output.write(expectedContent);
        }
        final byte[] actualContent = Files.readAllBytes(this.tempFile);
        assertThat(actualContent)
                .as("The content writen")
                .containsExactly(expectedContent);
    }

}
