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
package org.rookit.auto.javax.runtime.variable;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.NameFactory;
import org.rookit.auto.javax.runtime.ModifierFactory;
import org.rookit.auto.javax.runtime.element.node.NodeElementFactory;
import org.rookit.auto.javax.runtime.element.variable.RuntimeVariableElement;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.utils.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class FieldFactory implements Registry<RuntimeFieldEntity, RuntimeVariableElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(FieldFactory.class);

    private final NodeElementFactory nodeElementFactory;
    private final ModifierFactory modifierFactory;
    private final NameFactory nameFactory;

    @Inject
    private FieldFactory(
            final NodeElementFactory nodeElementFactory,
            final ModifierFactory modifierFactory,
            final NameFactory nameFactory) {
        this.nodeElementFactory = nodeElementFactory;
        this.modifierFactory = modifierFactory;
        this.nameFactory = nameFactory;
    }

    @Override
    public Maybe<RuntimeVariableElement> get(final RuntimeFieldEntity key) {
        return fetch(key).toMaybe();
    }

    @Override
    public Single<RuntimeVariableElement> fetch(final RuntimeFieldEntity entity) {
        return this.nodeElementFactory.createMutableFromEntity(entity)
                .map(node -> new RuntimeFieldVariableElement(
                        node,
                        this.modifierFactory.create(entity.modifiers()),
                        this.nameFactory.create(entity.name())
                ));
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

    @Override
    public String toString() {
        return "FieldFactory{" +
                "nodeElementFactory=" + this.nodeElementFactory +
                ", modifierFactory=" + this.modifierFactory +
                ", nameFactory=" + this.nameFactory +
                "}";
    }

}
