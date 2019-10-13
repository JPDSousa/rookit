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
package org.rookit.auto.javax.runtime.modifier;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import org.rookit.auto.javax.runtime.ModifierFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.util.Set;

final class ModifierFactoryImpl implements ModifierFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ModifierFactoryImpl.class);

    @Inject
    private ModifierFactoryImpl() {}

    @Override
    public Set<Modifier> create(final int modifier) {
        final ImmutableSet.Builder<Modifier> modifiersBuilder = ImmutableSet.builder();

        for (final Modifier value : Modifier.values()) {
            if (containsModifier(value, modifier)) {
                modifiersBuilder.add(value);
            }
        }

        return Sets.newEnumSet(modifiersBuilder.build(), Modifier.class);
    }

    private boolean containsModifier(final Modifier modifier, final int reflectModifier) {
        //noinspection AutoBoxing
        logger.trace("Checking if '{}' is a modifier in {}", modifier, reflectModifier);

        boolean containsModifier = false;
        switch (modifier) {
            case PUBLIC:
                containsModifier = java.lang.reflect.Modifier.isPublic(reflectModifier);
                break;
            case PROTECTED:
                containsModifier = java.lang.reflect.Modifier.isProtected(reflectModifier);
                break;
            case PRIVATE:
                containsModifier = java.lang.reflect.Modifier.isPrivate(reflectModifier);
                break;
            case ABSTRACT:
                containsModifier = java.lang.reflect.Modifier.isAbstract(reflectModifier);
                break;
            case DEFAULT:
                logger.info("Cannot infer default modifier from reflection modifier");
                break;
            case STATIC:
                containsModifier = java.lang.reflect.Modifier.isStatic(reflectModifier);
                break;
            case FINAL:
                containsModifier = java.lang.reflect.Modifier.isFinal(reflectModifier);
                break;
            case TRANSIENT:
                containsModifier = java.lang.reflect.Modifier.isTransient(reflectModifier);
                break;
            case VOLATILE:
                containsModifier = java.lang.reflect.Modifier.isVolatile(reflectModifier);
                break;
            case SYNCHRONIZED:
                containsModifier = java.lang.reflect.Modifier.isSynchronized(reflectModifier);
                break;
            case NATIVE:
                containsModifier = java.lang.reflect.Modifier.isNative(reflectModifier);
                break;
            case STRICTFP:
                containsModifier = java.lang.reflect.Modifier.isStrict(reflectModifier);
                break;
        }
        //noinspection AutoBoxing
        logger.trace("{} contains modifier '{}'? {}", reflectModifier, modifier, containsModifier);
        return containsModifier;
    }

}
