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
package org.rookit.mongodb.client;

import com.google.common.base.MoreObjects;
import com.mongodb.ClientSessionOptions;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.session.ClientSession;
import org.bson.Document;

import java.io.Closeable;

final class MongoClientCloser implements MongoClient, Closeable {

    private final MongoClient client;

    MongoClientCloser(final MongoClient client) {
        this.client = client;
    }

    @Override
    public MongoDatabase getDatabase(final String databaseName) {
        return this.client.getDatabase(databaseName);
    }

    @Override
    public ClientSession startSession(final ClientSessionOptions options) {
        return this.client.startSession(options);
    }

    @Override
    public void close() {
        this.client.close();
    }

    @Override
    public MongoIterable<String> listDatabaseNames() {
        return this.client.listDatabaseNames();
    }

    @Override
    public MongoIterable<String> listDatabaseNames(final ClientSession clientSession) {
        return this.client.listDatabaseNames(clientSession);
    }

    @Override
    public ListDatabasesIterable<Document> listDatabases() {
        return this.client.listDatabases();
    }

    @Override
    public ListDatabasesIterable<Document> listDatabases(final ClientSession clientSession) {
        return this.client.listDatabases(clientSession);
    }

    @Override
    public <TResult> ListDatabasesIterable<TResult> listDatabases(final Class<TResult> tResultClass) {
        return this.client.listDatabases(tResultClass);
    }

    @Override
    public <TResult> ListDatabasesIterable<TResult> listDatabases(final ClientSession clientSession,
                                                                  final Class<TResult> tResultClass) {
        return this.client.listDatabases(clientSession, tResultClass);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("client", this.client)
                .toString();
    }
}
