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
package org.rookit.auto.javax.runtime.entity;

import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

final class PackageEntity implements RuntimePackageEntity {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(PackageEntity.class);

    private final Package pack;

    PackageEntity(final Package pack) {
        this.pack = pack;
    }

    @Override
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> annotationClass) {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public String name() {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getName();
    }

    @Override
    public Package pack() {
        return this.pack;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        final PackageEntity other = (PackageEntity) o;
        return Objects.equal(this.pack, other.pack);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.pack);
    }

    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getAnnotation(annotationClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        logger.trace("Delegating to package '{}'", this.pack.getName());
        return this.pack.getDeclaredAnnotations();
    }

    @Override
    public String toString() {
        return "PackageEntity{" +
                "pack=" + this.pack +
                "}";
    }

}
