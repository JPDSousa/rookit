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
package org.rookit.io.filesystem;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;
import org.rookit.io.path.filesystem.ExtendedFileSystems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.spi.FileSystemProvider;

final class ExtendedFileSystemsImpl implements ExtendedFileSystems {

    @Inject
    private ExtendedFileSystemsImpl() {}

    @Override
    public Maybe<FileSystem> fileSystemFor(final URI uri) {
        final String scheme = uri.getScheme();
        for (final FileSystemProvider provider: FileSystemProvider.installedProviders()) {
            if (scheme.equalsIgnoreCase(provider.getScheme())) {
                return Maybe.fromCallable(() -> provider.getFileSystem(uri))
                        .onErrorResumeNext(new WindowsError(provider, uri));
            }
        }

        return Maybe.empty();
    }

    private static final class WindowsError implements Function<Throwable, MaybeSource<FileSystem>> {

        /**
         * Logger for this class.
         */
        private static final Logger logger = LoggerFactory.getLogger(WindowsError.class);

        private static final String ROOT_ERR_MSG = "Path component should be '/'";

        private final FileSystemProvider provider;
        private final URI uri;

        WindowsError(final FileSystemProvider provider, final URI uri) {
            this.provider = provider;
            this.uri = uri;
        }

        @Override
        public MaybeSource<FileSystem> apply(final Throwable throwable) throws Exception {
            if (isURIError(throwable, ROOT_ERR_MSG)) {

                return Maybe.just(this.provider.getFileSystem(new URI(this.uri.getScheme(),
                                                                      this.uri.getHost(),
                                                                      "/",
                                                                      this.uri.getFragment()))
                );
            }

            logger.debug("Unknown error while accessing file system.", throwable);
            return Maybe.error(throwable);
        }

        private boolean isURIError(final Throwable throwable, final String message) {
            return (throwable instanceof IllegalArgumentException) && message.equals(throwable.getMessage());
        }

        @Override
        public String toString() {
            return "WindowsError{" +
                    "provider=" + this.provider +
                    ", uri=" + this.uri +
                    "}";
        }

    }

}
