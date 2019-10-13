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

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import org.rookit.utils.guice.Dummy;
import org.rookit.utils.guice.Proxied;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Set;

final class IdempotentFiler implements Filer {

    private final Filer filer;
    private final Set<CharSequence> writtenFiles;
    private final JavaFileObject dummyFileObject;

    @Inject
    private IdempotentFiler(@Proxied final Filer filer,
                            @Dummy final JavaFileObject dummyFileObject) {
        this.filer = filer;
        this.dummyFileObject = dummyFileObject;
        this.writtenFiles = Sets.newHashSet();
    }

    @Override
    public JavaFileObject createSourceFile(final CharSequence name,
                                           final Element... originatingElements) throws IOException {

        if (this.writtenFiles.contains(name)) {
            return this.dummyFileObject;
        }
        final JavaFileObject sourceFile = this.filer.createSourceFile(name, originatingElements);
        this.writtenFiles.add(name);
        return sourceFile;
    }

    @Override
    public JavaFileObject createClassFile(final CharSequence name,
                                          final Element... originatingElements) throws IOException {
        if (this.writtenFiles.contains(name)) {
            return this.dummyFileObject;
        }
        final JavaFileObject classFile = this.filer.createClassFile(name, originatingElements);
        this.writtenFiles.add(name);
        return classFile;
    }

    @Override
    public FileObject createResource(final JavaFileManager.Location location,
                                     final CharSequence pkg,
                                     final CharSequence relativeName,
                                     final Element... originatingElements) throws IOException {
        final String id = pkg.toString() + relativeName;
        if (this.writtenFiles.contains(id)) {
            return this.dummyFileObject;
        }
        final FileObject resource = this.filer.createResource(location, pkg, relativeName, originatingElements);
        this.writtenFiles.add(id);
        return resource;
    }

    @Override
    public FileObject getResource(final JavaFileManager.Location location,
                                  final CharSequence pkg,
                                  final CharSequence relativeName) throws IOException {
        return this.filer.getResource(location, pkg, relativeName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("filer", this.filer)
                .add("writtenFiles", this.writtenFiles)
                .add("dummyFileObject", this.dummyFileObject)
                .toString();
    }
}
