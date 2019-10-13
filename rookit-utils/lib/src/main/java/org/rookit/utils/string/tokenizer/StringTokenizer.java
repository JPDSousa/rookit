package org.rookit.utils.string.tokenizer;

import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
public final class StringTokenizer implements Iterator<Token> {

    public static Iterator<Token> create(final String context, final String delimiter) {
        return new StringTokenizer(context, delimiter);
    }

    private final Object contextLock;
    private String context;
    private final String delimiter;

    private StringTokenizer(final String context, final String delimiter) {
        this.contextLock = new Object();
        this.context = context;
        this.delimiter = delimiter;
    }

    @Override
    public boolean hasNext() {
        synchronized (this.contextLock) {
            return this.context.contains(this.delimiter);
        }
    }

    @SuppressWarnings("FeatureEnvy")
    @Override
    public Token next() {
        synchronized (this.contextLock) {
            final int delimiterIndex = this.context.indexOf(this.delimiter);
            if (delimiterIndex >= 0) {
                final Token next = ImmutableToken.builder()
                        .token(this.context.substring(0, delimiterIndex))
                        .payload(this.context.substring(delimiterIndex + this.delimiter.length()))
                        .build();

                final int nextIndex = this.context.indexOf(this.delimiter, delimiterIndex + 1);
                this.context = this.context.substring(nextIndex);

                return next;
            }
            final String errorMessage = String.format("String '%s' does not contain any more tokens", this.context);
            throw new NoSuchElementException(errorMessage);
        }
    }

    @Override
    public String toString() {
        synchronized (this.contextLock) {
            return MoreObjects.toStringHelper(this)
                    .add("contextLock", this.contextLock)
                    .add("context", this.context)
                    .add("delimiter", this.delimiter)
                    .toString();
        }
    }
}
