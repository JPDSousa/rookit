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
package org.rookit.auto.javax.runtime.element;

import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.entity.kind.RuntimeElementKindFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.ElementKind;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

final class RuntimeElementKindFactoryImpl implements RuntimeElementKindFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeElementKindFactoryImpl.class);

    @Inject
    private RuntimeElementKindFactoryImpl() {}

    @Override
    public ElementKind createFromClass(final Class<?> clazz) {
        if (clazz.isEnum()) {
            logger.debug("Class is declared as enum");
            return ElementKind.ENUM;
        } else if (clazz.isAnnotation()) {
            logger.debug("Class is declared as annotation");
            return ElementKind.ANNOTATION_TYPE;
        } else if (clazz.isInterface()) {
            logger.trace("Class is declared as interface");
            return ElementKind.INTERFACE;
        }

        logger.debug("No class specification found. Assuming class kind");
        return ElementKind.CLASS;
    }

    @Override
    public ElementKind createFromExecutable(final Executable executable) {
        if (executable instanceof Method) {
            return ElementKind.METHOD;
        }
        if (executable instanceof Constructor) {
            return ElementKind.CONSTRUCTOR;
        }
        throw new IllegalArgumentException("Unknown kind of executable: " + executable);
    }

}
