/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.rookit.auto;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.github.glytching.junit.extension.folder.TemporaryFolder;
import io.github.glytching.junit.extension.folder.TemporaryFolderExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rookit.auto.javax.runtime.JavaxRuntimeModule;
import org.rookit.utils.guice.Proxied;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(TemporaryFolderExtension.class)
@ExtendWith(MockitoExtension.class)
public interface ExtendedProcessorTest {

    ExtendedProcessor processor(Injector injector);

    Module sourceModule();

    @Test
    default void testInjector(@Mock final Filer filer,
                              @Mock final Messager messager) {
        final Injector injector = Guice.createInjector(
                sourceModule(),
                JavaxRuntimeModule.getModule(),
                new AbstractModule() {
                    @Override
                    protected void configure() {
                        bind(Filer.class).annotatedWith(Proxied.class).toInstance(filer);
                        bind(Messager.class).toInstance(messager);
                    }

                }
        );
        final ExtendedProcessor processor = processor(injector);
        processor.init(injector.getInstance(ProcessingEnvironment.class));


        assertThatCode(() -> processor.injector().getInstance(TypeProcessor.class))
                .as("Initializing the Entity Handler")
                .doesNotThrowAnyException();
    }

    default URI createConfigFile(final TemporaryFolder temporaryFolder) throws IOException {
        final Path tempPath = temporaryFolder.createFile("configFile.json").toPath();
        // TODO can we inject the configured charset at this point?
        Files.write(tempPath, jsonConfig().getBytes(Charset.defaultCharset()));

        return tempPath.toUri();
    }

    String jsonConfig();

}
