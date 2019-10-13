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
package org.rookit.auto.javax.runtime.type;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.NameFactory;
import org.rookit.auto.javax.runtime.ModifierFactory;
import org.rookit.auto.javax.runtime.element.type.RuntimeTypeElement;
import org.rookit.auto.javax.runtime.element.type.node.RuntimeTypeNodeElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeTypeEntity;
import org.rookit.utils.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.NestingKind;

import static java.util.Objects.nonNull;

final class RuntimeTypeElementFactoryImpl implements Registry<RuntimeTypeEntity, RuntimeTypeElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(RuntimeTypeElementFactoryImpl.class);

    private final RuntimeTypeNodeElementFactory nodeFactory;
    private final NameFactory nameFactory;
    private final ModifierFactory modifierFactory;

    @Inject
    private RuntimeTypeElementFactoryImpl(
            final RuntimeTypeNodeElementFactory nodeFactory,
            final NameFactory nameFactory,
            final ModifierFactory modifierFactory) {
        this.nodeFactory = nodeFactory;
        this.nameFactory = nameFactory;
        this.modifierFactory = modifierFactory;
    }


    @Override
    public Maybe<RuntimeTypeElement> get(final RuntimeTypeEntity key) {
        return fetch(key).toMaybe();
    }

    @Override
    public Single<RuntimeTypeElement> fetch(final RuntimeTypeEntity entity) {
        final Class<?> entityClass = entity.clazz();

        return this.nodeFactory.createMutableFromEntity(entity)
                .map(node -> new RuntimeTypeElementImpl(
                        node,
                        this.nameFactory.create(entityClass.getTypeName()),
                        this.modifierFactory.create(entity.modifiers()),
                        this.nameFactory.create(entityClass.getName()),
                        createNestingKind(entityClass),
                        entity.kind()
                ));
    }

    private NestingKind createNestingKind(final Class<?> clazz) {
        // TODO this will never return annonymous
        if (nonNull(clazz.getEnclosingConstructor()) || nonNull(clazz.getEnclosingMethod())) {
            logger.debug("'{}' is contained either inside a constructor or a method. Returning '{}'", clazz,
                         NestingKind.LOCAL);
            return NestingKind.LOCAL;
        }
        if (nonNull(clazz.getEnclosingClass())) {
            logger.debug("'{}' is contained inside another class. Returning '{}'", clazz,
                         NestingKind.MEMBER);
            return NestingKind.MEMBER;
        }
        logger.debug("No enclosing constructor, method or class for '{}'. Returning '{}'", clazz,
                     NestingKind.TOP_LEVEL);
        return NestingKind.TOP_LEVEL;
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

    @Override
    public String toString() {
        return "RuntimeTypeElementFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                ", nameFactory=" + this.nameFactory +
                ", modifierFactory=" + this.modifierFactory +
                "}";
    }

}
