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
package org.rookit.crawler.mapdb;

import com.google.common.base.MoreObjects;
import com.google.common.io.Closer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.concurrent.ConcurrentMap;

final class CacheProvider implements Provider<ConcurrentMap<String, String>> {

    private final MapDBCacheConfig config;
    private final Closer closer;

    @Inject
    private CacheProvider(final MapDBCacheConfig config, final Closer closer) {
        this.config = config;
        this.closer = closer;
    }

    @Override
    public ConcurrentMap<String, String> get() {
        final DBMaker.Maker maker = this.config.dbPath()
                .map(path -> DBMaker.fileDB(path.toString()))
                .orElseGet(DBMaker::memoryDB);
        //noinspection resource
        final DB db = this.closer.register(maker.closeOnJvmShutdown().make());
        return db
                .hashMap(this.config.databaseName(), Serializer.STRING, Serializer.STRING)
                .createOrOpen();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", this.config)
                .add("closer", this.closer)
                .toString();
    }
}
