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
package org.rookit.auto.javax.runtime.element.type;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.ModifierFactory;
import org.rookit.auto.javax.runtime.NameFactory;
import org.rookit.auto.javax.runtime.element.type.node.RuntimeTypeNodeElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeClassEntity;
import org.rookit.auto.javax.runtime.mirror.declared.RuntimeDeclaredTypeFactory;
import org.rookit.utils.collection.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class TypeElementFactoryImpl implements TypeElementFactory {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(TypeElementFactoryImpl.class);

    private final RuntimeTypeNodeElementFactory nodeFactory;
    private final NameFactory nameFactory;
    private final ModifierFactory modifierFactory;
    private final MapUtils mapUtils;
    private final RuntimeDeclaredTypeFactory typeFactory;

    @Inject
    private TypeElementFactoryImpl(
            final RuntimeTypeNodeElementFactory nodeFactory,
            final NameFactory nameFactory,
            final ModifierFactory modifierFactory,
            final MapUtils mapUtils,
            final RuntimeDeclaredTypeFactory typeFactory) {
        this.nodeFactory = nodeFactory;
        this.nameFactory = nameFactory;
        this.modifierFactory = modifierFactory;
        this.mapUtils = mapUtils;
        this.typeFactory = typeFactory;
    }

    @Override
    public Single<RuntimeTypeElement> createElement(final RuntimeClassEntity entity) {

        logger.trace("Creating mutable node");
        return this.nodeFactory.createMutableFromEntity(entity)
                .doOnSuccess(mutableNode -> logger.trace("Mutable node created"))
                .map(node -> new RuntimeTypeElementImpl(
                        node,
                        this.typeFactory,
                        this.nameFactory.createFromEntity(entity),
                        this.modifierFactory.create(entity.modifiers()),
                        this.nameFactory.createFromEntity(entity),
                        entity,
                        this.mapUtils));
    }

    @Override
    public String toString() {
        return "TypeElementFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                ", nameFactory=" + this.nameFactory +
                ", modifierFactory=" + this.modifierFactory +
                ", mapUtils=" + this.mapUtils +
                ", typeFactory=" + this.typeFactory +
                "}";
    }

}
