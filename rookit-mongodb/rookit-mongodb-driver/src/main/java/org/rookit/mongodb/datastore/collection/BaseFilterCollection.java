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
package org.rookit.mongodb.datastore.collection;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoNamespace;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.CreateIndexOptions;
import com.mongodb.client.model.DeleteOptions;
import com.mongodb.client.model.DropIndexOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.RenameCollectionOptions;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.session.ClientSession;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.List;

final class BaseFilterCollection<T> implements MongoCollection<T> {
    
    private final MongoCollection<T> delegate;
    private final Bson filter;
    
    BaseFilterCollection(final MongoCollection<T> delegate, final Bson filter) {
        this.delegate = delegate;
        this.filter = filter;
    }

    @Override
    public MongoNamespace getNamespace() {
        return this.delegate.getNamespace();
    }

    @Override
    public Class<T> getDocumentClass() {
        return this.delegate.getDocumentClass();
    }

    @Override
    public CodecRegistry getCodecRegistry() {
        return this.delegate.getCodecRegistry();
    }

    @Override
    public ReadPreference getReadPreference() {
        return this.delegate.getReadPreference();
    }

    @Override
    public WriteConcern getWriteConcern() {
        return this.delegate.getWriteConcern();
    }

    @Override
    public ReadConcern getReadConcern() {
        return this.delegate.getReadConcern();
    }

    @Override
    public <NewTDocument> MongoCollection<NewTDocument> withDocumentClass(final Class<NewTDocument> clazz) {
        return new BaseFilterCollection<>(this.delegate.withDocumentClass(clazz), this.filter);
    }

    @Override
    public MongoCollection<T> withCodecRegistry(final CodecRegistry codecRegistry) {
        return new BaseFilterCollection<>(this.delegate.withCodecRegistry(codecRegistry), this.filter);
    }

    @Override
    public MongoCollection<T> withReadPreference(final ReadPreference readPreference) {
        return new BaseFilterCollection<>(this.delegate.withReadPreference(readPreference), this.filter);
    }

    @Override
    public MongoCollection<T> withWriteConcern(final WriteConcern writeConcern) {
        return new BaseFilterCollection<>(this.delegate.withWriteConcern(writeConcern), this.filter);
    }

    @Override
    public MongoCollection<T> withReadConcern(final ReadConcern readConcern) {
        return new BaseFilterCollection<>(this.delegate.withReadConcern(readConcern), this.filter);
    }

    private Bson applyFilter(final Bson filter) {
        return Filters.and(this.filter, filter);
    }

    private List<? extends Bson> applyFilterToPipeline(final Iterable<? extends Bson> pipeline) {
        return ImmutableList.<Bson>builder()
                .addAll(pipeline)
                .add(Aggregates.match(this.filter))
                .build();
    }

    @Override
    public long count() {
        return this.delegate.count(this.filter);
    }

    @Override
    public long count(final Bson filter) {
        return this.delegate.count(applyFilter(filter));
    }

    @Override
    public long count(final Bson filter, final CountOptions options) {
        return this.delegate.count(applyFilter(filter), options);
    }

    @Override
    public long count(final ClientSession clientSession) {
        return this.delegate.count(clientSession, this.filter);
    }

    @Override
    public long count(final ClientSession clientSession, final Bson filter) {
        return this.delegate.count(clientSession, applyFilter(filter));
    }

    @Override
    public long count(final ClientSession clientSession, final Bson filter, final CountOptions options) {
        return this.delegate.count(clientSession, applyFilter(filter), options);
    }

    @Override
    public <TResult> DistinctIterable<TResult> distinct(final String fieldName, final Class<TResult> tResultClass) {
        return this.delegate.distinct(fieldName, this.filter, tResultClass);
    }

    @Override
    public <TResult> DistinctIterable<TResult> distinct(final String fieldName,
                                                        final Bson filter,
                                                        final Class<TResult> tResultClass) {
        return this.delegate.distinct(fieldName, applyFilter(filter), tResultClass);
    }

    @Override
    public <TResult> DistinctIterable<TResult> distinct(final ClientSession clientSession,
                                                        final String fieldName,
                                                        final Class<TResult> tResultClass) {
        return this.delegate.distinct(clientSession, fieldName, this.filter, tResultClass);
    }

    @Override
    public <TResult> DistinctIterable<TResult> distinct(final ClientSession clientSession,
                                                        final String fieldName,
                                                        final Bson filter,
                                                        final Class<TResult> tResultClass) {
        return this.delegate.distinct(clientSession, fieldName, applyFilter(filter), tResultClass);
    }

    @Override
    public FindIterable<T> find() {
        return this.delegate.find(this.filter);
    }

    @Override
    public <TResult> FindIterable<TResult> find(final Class<TResult> tResultClass) {
        return this.delegate.find(this.filter, tResultClass);
    }

    @Override
    public FindIterable<T> find(final Bson filter) {
        return this.delegate.find(applyFilter(filter));
    }

    @Override
    public <TResult> FindIterable<TResult> find(final Bson filter, final Class<TResult> tResultClass) {
        return this.delegate.find(applyFilter(filter), tResultClass);
    }

    @Override
    public FindIterable<T> find(final ClientSession clientSession) {
        return this.delegate.find(clientSession, this.filter);
    }

    @Override
    public <TResult> FindIterable<TResult> find(final ClientSession clientSession, final Class<TResult> tResultClass) {
        return this.delegate.find(clientSession, this.filter, tResultClass);
    }

    @Override
    public FindIterable<T> find(final ClientSession clientSession, final Bson filter) {
        return this.delegate.find(clientSession, applyFilter(filter));
    }

    @Override
    public <TResult> FindIterable<TResult> find(final ClientSession clientSession,
                                                final Bson filter,
                                                final Class<TResult> tResultClass) {
        return this.delegate.find(clientSession, applyFilter(filter), tResultClass);
    }

    @Override
    public AggregateIterable<T> aggregate(final List<? extends Bson> pipeline) {
        return this.delegate.aggregate(applyFilterToPipeline(pipeline));
    }

    @Override
    public <TResult> AggregateIterable<TResult> aggregate(final List<? extends Bson> pipeline,
                                                          final Class<TResult> tResultClass) {
        return this.delegate.aggregate(applyFilterToPipeline(pipeline), tResultClass);
    }

    @Override
    public AggregateIterable<T> aggregate(final ClientSession clientSession, final List<? extends Bson> pipeline) {
        return this.delegate.aggregate(clientSession, applyFilterToPipeline(pipeline));
    }

    @Override
    public <TResult> AggregateIterable<TResult> aggregate(final ClientSession clientSession,
                                                          final List<? extends Bson> pipeline,
                                                          final Class<TResult> tResultClass) {
        return this.delegate.aggregate(clientSession, applyFilterToPipeline(pipeline), tResultClass);
    }

    @Override
    public ChangeStreamIterable<T> watch() {
        return this.delegate.watch(applyFilterToPipeline(ImmutableList.of()));
    }

    @Override
    public <TResult> ChangeStreamIterable<TResult> watch(final Class<TResult> tResultClass) {
        return this.delegate.watch(ImmutableList.of(), tResultClass);
    }

    @Override
    public ChangeStreamIterable<T> watch(final List<? extends Bson> pipeline) {
        return this.delegate.watch(applyFilterToPipeline(pipeline));
    }

    @Override
    public <TResult> ChangeStreamIterable<TResult> watch(final List<? extends Bson> pipeline,
                                                         final Class<TResult> tResultClass) {
        return this.delegate.watch(applyFilterToPipeline(pipeline), tResultClass);
    }

    @Override
    public ChangeStreamIterable<T> watch(final ClientSession clientSession) {
        return this.delegate.watch(clientSession, applyFilterToPipeline(ImmutableList.of()));
    }

    @Override
    public <TResult> ChangeStreamIterable<TResult> watch(final ClientSession clientSession,
                                                         final Class<TResult> tResultClass) {
        return this.delegate.watch(clientSession, applyFilterToPipeline(ImmutableList.of()), tResultClass);
    }

    @Override
    public ChangeStreamIterable<T> watch(final ClientSession clientSession, final List<? extends Bson> pipeline) {
        return this.delegate.watch(clientSession, applyFilterToPipeline(pipeline));
    }

    @Override
    public <TResult> ChangeStreamIterable<TResult> watch(final ClientSession clientSession,
                                                         final List<? extends Bson> pipeline,
                                                         final Class<TResult> tResultClass) {
        return this.delegate.watch(clientSession, applyFilterToPipeline(pipeline), tResultClass);
    }

    @Override
    public MapReduceIterable<T> mapReduce(final String mapFunction, final String reduceFunction) {
        return this.delegate.mapReduce(mapFunction, reduceFunction);
    }

    @Override
    public <TResult> MapReduceIterable<TResult> mapReduce(final String mapFunction,
                                                          final String reduceFunction,
                                                          final Class<TResult> tResultClass) {
        return this.delegate.mapReduce(mapFunction, reduceFunction, tResultClass);
    }

    @Override
    public MapReduceIterable<T> mapReduce(final ClientSession clientSession,
                                          final String mapFunction,
                                          final String reduceFunction) {
        return this.delegate.mapReduce(clientSession, mapFunction, reduceFunction);
    }

    @Override
    public <TResult> MapReduceIterable<TResult> mapReduce(final ClientSession clientSession,
                                                          final String mapFunction,
                                                          final String reduceFunction,
                                                          final Class<TResult> tResultClass) {
        return this.delegate.mapReduce(clientSession, mapFunction, reduceFunction, tResultClass);
    }

    @Override
    public BulkWriteResult bulkWrite(final List<? extends WriteModel<? extends T>> requests) {
        return this.delegate.bulkWrite(requests);
    }

    @Override
    public BulkWriteResult bulkWrite(final List<? extends WriteModel<? extends T>> requests,
                                     final BulkWriteOptions options) {
        return this.delegate.bulkWrite(requests, options);
    }

    @Override
    public BulkWriteResult bulkWrite(final ClientSession clientSession,
                                     final List<? extends WriteModel<? extends T>> requests) {
        return this.delegate.bulkWrite(clientSession, requests);
    }

    @Override
    public BulkWriteResult bulkWrite(final ClientSession clientSession,
                                     final List<? extends WriteModel<? extends T>> requests,
                                     final BulkWriteOptions options) {
        return this.delegate.bulkWrite(clientSession, requests, options);
    }

    @Override
    public void insertOne(final T t) {
        this.delegate.insertOne(t);
    }

    @Override
    public void insertOne(final T t, final InsertOneOptions options) {
        this.delegate.insertOne(t, options);
    }

    @Override
    public void insertOne(final ClientSession clientSession, final T t) {
        this.delegate.insertOne(clientSession, t);
    }

    @Override
    public void insertOne(final ClientSession clientSession, final T t, final InsertOneOptions options) {
        this.delegate.insertOne(clientSession, t, options);
    }

    @Override
    public void insertMany(final List<? extends T> ts) {
        this.delegate.insertMany(ts);
    }

    @Override
    public void insertMany(final List<? extends T> ts, final InsertManyOptions options) {
        this.delegate.insertMany(ts, options);
    }

    @Override
    public void insertMany(final ClientSession clientSession, final List<? extends T> ts) {
        this.delegate.insertMany(clientSession, ts);
    }

    @Override
    public void insertMany(final ClientSession clientSession, final List<? extends T> ts, final InsertManyOptions options) {
        this.delegate.insertMany(clientSession, ts, options);
    }

    @Override
    public DeleteResult deleteOne(final Bson filter) {
        return this.delegate.deleteOne(filter);
    }

    @Override
    public DeleteResult deleteOne(final Bson filter, final DeleteOptions options) {
        return this.delegate.deleteOne(filter, options);
    }

    @Override
    public DeleteResult deleteOne(final ClientSession clientSession, final Bson filter) {
        return this.delegate.deleteOne(clientSession, filter);
    }

    @Override
    public DeleteResult deleteOne(final ClientSession clientSession, final Bson filter, final DeleteOptions options) {
        return this.delegate.deleteOne(clientSession, filter, options);
    }

    @Override
    public DeleteResult deleteMany(final Bson filter) {
        return this.delegate.deleteMany(filter);
    }

    @Override
    public DeleteResult deleteMany(final Bson filter, final DeleteOptions options) {
        return this.delegate.deleteMany(filter, options);
    }

    @Override
    public DeleteResult deleteMany(final ClientSession clientSession, final Bson filter) {
        return this.delegate.deleteMany(clientSession, filter);
    }

    @Override
    public DeleteResult deleteMany(final ClientSession clientSession, final Bson filter, final DeleteOptions options) {
        return this.delegate.deleteMany(clientSession, filter, options);
    }

    @Override
    public UpdateResult replaceOne(final Bson filter, final T replacement) {
        return this.delegate.replaceOne(applyFilter(filter), replacement);
    }

    @Override
    public UpdateResult replaceOne(final Bson filter, final T replacement, final UpdateOptions updateOptions) {
        return this.delegate.replaceOne(applyFilter(filter), replacement, updateOptions);
    }

    @Override
    public UpdateResult replaceOne(final Bson filter, final T replacement, final ReplaceOptions replaceOptions) {
        return this.delegate.replaceOne(applyFilter(filter), replacement, replaceOptions);
    }

    @Override
    public UpdateResult replaceOne(final ClientSession clientSession, final Bson filter, final T replacement) {
        return this.delegate.replaceOne(clientSession, applyFilter(filter), replacement);
    }

    @Override
    public UpdateResult replaceOne(final ClientSession clientSession,
                                   final Bson filter,
                                   final T replacement,
                                   final UpdateOptions updateOptions) {
        return this.delegate.replaceOne(clientSession, applyFilter(filter), replacement, updateOptions);
    }

    @Override
    public UpdateResult replaceOne(final ClientSession clientSession,
                                   final Bson filter,
                                   final T replacement,
                                   final ReplaceOptions replaceOptions) {
        return this.delegate.replaceOne(clientSession, applyFilter(filter), replacement, replaceOptions);
    }

    @Override
    public UpdateResult updateOne(final Bson filter, final Bson update) {
        return this.delegate.updateOne(applyFilter(filter), update);
    }

    @Override
    public UpdateResult updateOne(final Bson filter, final Bson update, final UpdateOptions updateOptions) {
        return this.delegate.updateOne(applyFilter(filter), update, updateOptions);
    }

    @Override
    public UpdateResult updateOne(final ClientSession clientSession, final Bson filter, final Bson update) {
        return this.delegate.updateOne(clientSession, applyFilter(filter), update);
    }

    @Override
    public UpdateResult updateOne(final ClientSession clientSession,
                                  final Bson filter,
                                  final Bson update,
                                  final UpdateOptions updateOptions) {
        return this.delegate.updateOne(clientSession, applyFilter(filter), update, updateOptions);
    }

    @Override
    public UpdateResult updateMany(final Bson filter, final Bson update) {
        return this.delegate.updateMany(applyFilter(filter), update);
    }

    @Override
    public UpdateResult updateMany(final Bson filter, final Bson update, final UpdateOptions updateOptions) {
        return this.delegate.updateMany(applyFilter(filter), update, updateOptions);
    }

    @Override
    public UpdateResult updateMany(final ClientSession clientSession, final Bson filter, final Bson update) {
        return this.delegate.updateMany(clientSession, applyFilter(filter), update);
    }

    @Override
    public UpdateResult updateMany(final ClientSession clientSession,
                                   final Bson filter,
                                   final Bson update,
                                   final UpdateOptions updateOptions) {
        return this.delegate.updateMany(clientSession, applyFilter(filter), update, updateOptions);
    }

    @Override
    public T findOneAndDelete(final Bson filter) {
        return this.delegate.findOneAndDelete(applyFilter(filter));
    }

    @Override
    public T findOneAndDelete(final Bson filter, final FindOneAndDeleteOptions options) {
        return this.delegate.findOneAndDelete(applyFilter(filter), options);
    }

    @Override
    public T findOneAndDelete(final ClientSession clientSession, final Bson filter) {
        return this.delegate.findOneAndDelete(clientSession, applyFilter(filter));
    }

    @Override
    public T findOneAndDelete(final ClientSession clientSession,
                              final Bson filter,
                              final FindOneAndDeleteOptions options) {
        return this.delegate.findOneAndDelete(clientSession, applyFilter(filter), options);
    }

    @Override
    public T findOneAndReplace(final Bson filter, final T replacement) {
        return this.delegate.findOneAndReplace(applyFilter(filter), replacement);
    }

    @Override
    public T findOneAndReplace(final Bson filter, final T replacement, final FindOneAndReplaceOptions options) {
        return this.delegate.findOneAndReplace(applyFilter(filter), replacement, options);
    }

    @Override
    public T findOneAndReplace(final ClientSession clientSession, final Bson filter, final T replacement) {
        return this.delegate.findOneAndReplace(clientSession, applyFilter(filter), replacement);
    }

    @Override
    public T findOneAndReplace(final ClientSession clientSession,
                               final Bson filter,
                               final T replacement,
                               final FindOneAndReplaceOptions options) {
        return this.delegate.findOneAndReplace(clientSession, applyFilter(filter), replacement, options);
    }

    @Override
    public T findOneAndUpdate(final Bson filter, final Bson update) {
        return this.delegate.findOneAndUpdate(applyFilter(filter), update);
    }

    @Override
    public T findOneAndUpdate(final Bson filter, final Bson update, final FindOneAndUpdateOptions options) {
        return this.delegate.findOneAndUpdate(applyFilter(filter), update, options);
    }

    @Override
    public T findOneAndUpdate(final ClientSession clientSession, final Bson filter, final Bson update) {
        return this.delegate.findOneAndUpdate(clientSession, applyFilter(filter), update);
    }

    @Override
    public T findOneAndUpdate(final ClientSession clientSession,
                              final Bson filter,
                              final Bson update,
                              final FindOneAndUpdateOptions options) {
        return this.delegate.findOneAndUpdate(clientSession, applyFilter(filter), update, options);
    }

    @Override
    public void drop() {
        this.delegate.drop();
    }

    @Override
    public void drop(final ClientSession clientSession) {
        this.delegate.drop(clientSession);
    }

    @Override
    public String createIndex(final Bson keys) {
        return this.delegate.createIndex(keys);
    }

    @Override
    public String createIndex(final Bson keys, final IndexOptions indexOptions) {
        return this.delegate.createIndex(keys, indexOptions);
    }

    @Override
    public String createIndex(final ClientSession clientSession, final Bson keys) {
        return this.delegate.createIndex(clientSession, keys);
    }

    @Override
    public String createIndex(final ClientSession clientSession, final Bson keys, final IndexOptions indexOptions) {
        return this.delegate.createIndex(clientSession, keys, indexOptions);
    }

    @Override
    public List<String> createIndexes(final List<IndexModel> indexes) {
        return this.delegate.createIndexes(indexes);
    }

    @Override
    public List<String> createIndexes(final List<IndexModel> indexes, final CreateIndexOptions createIndexOptions) {
        return this.delegate.createIndexes(indexes, createIndexOptions);
    }

    @Override
    public List<String> createIndexes(final ClientSession clientSession, final List<IndexModel> indexes) {
        return this.delegate.createIndexes(clientSession, indexes);
    }

    @Override
    public List<String> createIndexes(final ClientSession clientSession,
                                      final List<IndexModel> indexes,
                                      final CreateIndexOptions createIndexOptions) {
        return this.delegate.createIndexes(clientSession, indexes, createIndexOptions);
    }

    @Override
    public ListIndexesIterable<Document> listIndexes() {
        return this.delegate.listIndexes();
    }

    @Override
    public <TResult> ListIndexesIterable<TResult> listIndexes(final Class<TResult> tResultClass) {
        return this.delegate.listIndexes(tResultClass);
    }

    @Override
    public ListIndexesIterable<Document> listIndexes(final ClientSession clientSession) {
        return this.delegate.listIndexes(clientSession);
    }

    @Override
    public <TResult> ListIndexesIterable<TResult> listIndexes(final ClientSession clientSession,
                                                              final Class<TResult> tResultClass) {
        return this.delegate.listIndexes(clientSession, tResultClass);
    }

    @Override
    public void dropIndex(final String indexName) {
        this.delegate.dropIndex(indexName);
    }

    @Override
    public void dropIndex(final String indexName, final DropIndexOptions dropIndexOptions) {
        this.delegate.dropIndex(indexName, dropIndexOptions);
    }

    @Override
    public void dropIndex(final Bson keys) {
        this.delegate.dropIndex(keys);
    }

    @Override
    public void dropIndex(final Bson keys, final DropIndexOptions dropIndexOptions) {
        this.delegate.dropIndex(keys, dropIndexOptions);
    }

    @Override
    public void dropIndex(final ClientSession clientSession, final String indexName) {
        this.delegate.dropIndex(clientSession, indexName);
    }

    @Override
    public void dropIndex(final ClientSession clientSession, final Bson keys) {
        this.delegate.dropIndex(clientSession, keys);
    }

    @Override
    public void dropIndex(final ClientSession clientSession,
                          final String indexName,
                          final DropIndexOptions dropIndexOptions) {
        this.delegate.dropIndex(clientSession, indexName, dropIndexOptions);
    }

    @Override
    public void dropIndex(final ClientSession clientSession, final Bson keys, final DropIndexOptions dropIndexOptions) {
        this.delegate.dropIndex(clientSession, keys, dropIndexOptions);
    }

    @Override
    public void dropIndexes() {
        this.delegate.dropIndexes();
    }

    @Override
    public void dropIndexes(final ClientSession clientSession) {
        this.delegate.dropIndexes(clientSession);
    }

    @Override
    public void dropIndexes(final DropIndexOptions dropIndexOptions) {
        this.delegate.dropIndexes(dropIndexOptions);
    }

    @Override
    public void dropIndexes(final ClientSession clientSession, final DropIndexOptions dropIndexOptions) {
        this.delegate.dropIndexes(clientSession, dropIndexOptions);
    }

    @Override
    public void renameCollection(final MongoNamespace newCollectionNamespace) {
        this.delegate.renameCollection(newCollectionNamespace);
    }

    @Override
    public void renameCollection(final MongoNamespace newCollectionNamespace,
                                 final RenameCollectionOptions renameCollectionOptions) {
        this.delegate.renameCollection(newCollectionNamespace, renameCollectionOptions);
    }

    @Override
    public void renameCollection(final ClientSession clientSession, final MongoNamespace newCollectionNamespace) {
        this.delegate.renameCollection(clientSession, newCollectionNamespace);
    }

    @Override
    public void renameCollection(final ClientSession clientSession,
                                 final MongoNamespace newCollectionNamespace,
                                 final RenameCollectionOptions renameCollectionOptions) {
        this.delegate.renameCollection(clientSession, newCollectionNamespace, renameCollectionOptions);
    }

    @Override
    public String toString() {
        return "BaseFilterCollection{" +
                "delegate=" + this.delegate +
                ", filter=" + this.filter +
                "}";
    }
}
