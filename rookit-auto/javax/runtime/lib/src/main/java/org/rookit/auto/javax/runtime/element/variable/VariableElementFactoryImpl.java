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
package org.rookit.auto.javax.runtime.element.variable;

import com.google.inject.Inject;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.ModifierFactory;
import org.rookit.auto.javax.runtime.NameFactory;
import org.rookit.auto.javax.runtime.element.node.NodeElementFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeEnumEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeFieldEntity;
import org.rookit.auto.javax.runtime.entity.RuntimeParameterEntity;

final class VariableElementFactoryImpl implements VariableElementFactory {

    private final NodeElementFactory nodeFactory;
    private final ModifierFactory modifierFactory;
    private final NameFactory nameFactory;

    @Inject
    private VariableElementFactoryImpl(
            final NodeElementFactory nodeFactory,
            final ModifierFactory modifierFactory,
            final NameFactory nameFactory) {
        this.nodeFactory = nodeFactory;
        this.modifierFactory = modifierFactory;
        this.nameFactory = nameFactory;
    }

    @Override
    public Single<RuntimeVariableElement> createFromEnum(final RuntimeEnumEntity entity) {
        return this.nodeFactory.createMutableFromEntity(entity)
                .map(node -> new RuntimeEnumVariableElement(
                        entity.enumeration(),
                        node,
                        this.modifierFactory.create(entity.modifiers()),
                        this.nameFactory.createFromEntity(entity)));
    }

    @Override
    public Single<RuntimeVariableElement> createFromField(final RuntimeFieldEntity entity) {
        return this.nodeFactory.createMutableFromEntity(entity)
                .map(node -> new RuntimeFieldVariableElement(
                        node,
                        this.modifierFactory.create(entity.modifiers()),
                        this.nameFactory.createFromEntity(entity)
                ));
    }

    @Override
    public Single<RuntimeVariableElement> createFromParameter(final RuntimeParameterEntity entity) {
        return this.nodeFactory.createMutableFromEntity(entity)
                .map(node -> new RuntimeParameterVariableElement(
                        node,
                        this.modifierFactory.create(entity.modifiers()),
                        this.nameFactory.createFromEntity(entity)
                ));


    }

    @Override
    public String toString() {
        return "VariableElementFactoryImpl{" +
                "nodeFactory=" + this.nodeFactory +
                ", modifierFactory=" + this.modifierFactory +
                ", nameFactory=" + this.nameFactory +
                "}";
    }

}
