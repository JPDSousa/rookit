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
package org.rookit.spotify.rest.api;

import com.google.common.util.concurrent.RateLimiter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.concurrent.ThreadSafe;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ThreadSafe
//TODO this class needs a lot of refactoring
final class RateLimiterInterceptor implements Interceptor {

    private static final Logger logger = Logger.getLogger(RateLimiterInterceptor.class.getName());

    private final Object rateLimiterLock;
    private final RateLimiter limiter;
    private boolean isScheduled;
    private final int maxRetries;
    private final double maxRate;
    private final ScheduledExecutorService scheduler;

    RateLimiterInterceptor(final RateLimiter limiter,
                           final int maxRetries,
                           final ScheduledExecutorService scheduler) {
        this.maxRate = limiter.getRate();
        this.scheduler = scheduler;
        this.rateLimiterLock = new Object();
        this.limiter = limiter;
        this.maxRetries = maxRetries;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request request = chain.request();
        final double rate = this.limiter.getRate();
        this.limiter.acquire();
        return computeResponse(chain, request, rate);
    }

    private Response computeResponse(final Chain chain, final Request request, final double rate) throws IOException {
        Response response = chain.proceed(request);
        int retries = 0;
        while ((response.code() == 429) && (retries < this.maxRetries)) {
            response.close();
            decreaseLimiter(rate - 3);
            final int retryAfter = Integer.valueOf(response.header("Retry-After", "0"));
            if (retryAfter > 0) {
                try {
                    Thread.sleep(Duration.ofSeconds(retryAfter).toMillis());
                } catch (final InterruptedException e) {
                    logger.info("Thread interrupted. Returning current response");
                    return response;
                }
            }
            this.limiter.acquire();
            response = chain.proceed(request);
            retries++;
        }

        return response;
    }

    private void decreaseLimiter(final double target) {
        synchronized (this.rateLimiterLock) {
            final double rate = this.limiter.getRate();
            if ((target > 0) && (target < rate)) {
                this.limiter.setRate(target);
                logger.info("Rate limiter set to " + target);
                if (!this.isScheduled) {
                    this.isScheduled = true;
                    this.scheduler.scheduleAtFixedRate(this::increaseLimiter, 3,3, TimeUnit.SECONDS);
                }
            }
        }
    }

    private void increaseLimiter() {
        synchronized (this.rateLimiterLock) {
            final double rate = this.limiter.getRate();
            if ((rate < this.maxRate)) {
                final double newRate = rate + 1;
                this.limiter.setRate(newRate);
                logger.info("Rate limiter set to " + newRate);
            }
        }
    }

    @Override
    public String toString() {
        return "RateLimiterInterceptor{" +
                "rateLimiterLock=" + this.rateLimiterLock +
                ", limiter=" + this.limiter +
                ", isScheduled=" + this.isScheduled +
                ", maxRetries=" + this.maxRetries +
                ", maxRate=" + this.maxRate +
                ", scheduler=" + this.scheduler +
                "}";
    }
}
