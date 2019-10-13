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
package org.rookit.utils.collection;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.Bag;
import org.rookit.failsafe.Failsafe;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

final class UnmodifiableBag<T> implements Bag<T> {

    private static final String IMMUTABLE_ERROR_MESSAGE = "Cannot modify this object";

    private final Failsafe failsafe;
    private final Bag<T> delegate;

    UnmodifiableBag(final Failsafe failsafe, final Bag<T> delegate) {
        this.failsafe = failsafe;
        //noinspection AssignmentOrReturnOfFieldWithMutableType
        this.delegate = delegate;
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean add(final T object) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean add(final T object, final int copies) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean addAll(final Collection<? extends T> collection) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @Override
    public void clear() {
        this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @Override
    public boolean contains(final Object o) {
        return this.delegate.contains(o);
    }

    @Override
    public boolean containsAll(final Collection<?> coll) {
        return this.delegate.containsAll(coll);
    }

    @Override
    public int getCount(final Object object) {
        return this.delegate.getCount(object);
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.delegate.iterator();
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean remove(final Object object) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean remove(final Object object, final int copies) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean removeAll(final Collection<?> coll) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @SuppressWarnings("boxing")
    @Override
    public boolean retainAll(final Collection<?> coll) {
        //noinspection AutoUnboxing
        return this.failsafe.handleException().unsupportedOperation(IMMUTABLE_ERROR_MESSAGE);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public Object[] toArray() {
        return this.delegate.toArray();
    }

    @Override
    public <E> E[] toArray(final E[] a) {
        return this.delegate.toArray(a);
    }

    @Override
    public Set<T> uniqueSet() {
        return ImmutableSet.copyOf(this.delegate.uniqueSet());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("injector", this.failsafe)
                .add("delegate", this.delegate)
                .toString();
    }
}
