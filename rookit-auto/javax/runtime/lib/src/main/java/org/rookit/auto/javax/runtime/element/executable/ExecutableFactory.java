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
package org.rookit.auto.javax.runtime.element.executable;

import com.google.inject.Inject;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.rookit.auto.javax.runtime.NameFactory;
import org.rookit.auto.javax.runtime.ModifierFactory;
import org.rookit.auto.javax.runtime.annotation.AnnotationValueFactory;
import org.rookit.auto.javax.runtime.element.executable.node.ExecutableNodeElementFactory;
import org.rookit.auto.javax.runtime.element.executable.node.MutableExecutableNodeElement;
import org.rookit.auto.javax.runtime.entity.kind.RuntimeElementKindFactory;
import org.rookit.auto.javax.runtime.entity.RuntimeExecutableEntity;
import org.rookit.utils.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.AnnotationValue;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

final class ExecutableFactory implements Registry<RuntimeExecutableEntity, RuntimeExecutableElement> {

    /**
     * Logger for this class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecutableFactory.class);

    private final ExecutableNodeElementFactory nodeFactory;
    private final RuntimeElementKindFactory kindFactory;
    private final ModifierFactory modifierFactory;
    private final NameFactory nameFactory;
    private final AnnotationValueFactory annotationValueFactory;

    @Inject
    private ExecutableFactory(
            final ExecutableNodeElementFactory nodeFactory,
            final RuntimeElementKindFactory kindFactory,
            final ModifierFactory modifierFactory,
            final NameFactory nameFactory,
            final AnnotationValueFactory valueFactory) {
        this.nodeFactory = nodeFactory;
        this.kindFactory = kindFactory;
        this.modifierFactory = modifierFactory;
        this.nameFactory = nameFactory;
        this.annotationValueFactory = valueFactory;
    }

    @Override
    public Maybe<RuntimeExecutableElement> get(final RuntimeExecutableEntity key) {
        return fetch(key).toMaybe();
    }

    @Override
    public Single<RuntimeExecutableElement> fetch(final RuntimeExecutableEntity entity) {
        final Executable executable = entity.executable();

        final Single<AnnotationValue> defaultValueSingle = createDefaultValue(executable)
                .toSingle();
        final Single<MutableExecutableNodeElement> nodeSingle = this.nodeFactory.createMutableFromEntity(entity);

        return Single.zip(defaultValueSingle, nodeSingle, (defaultValue, node) -> new RuntimeExecutableElementImpl(
                node,
                this.kindFactory.createFromExecutable(executable),
                this.modifierFactory.create(entity.modifiers()),
                this.nameFactory.createFromEntity(entity),
                executable.isVarArgs(),
                defaultValue
        ));
    }

    private Maybe<AnnotationValue> createDefaultValue(final Executable executable) {
        if (executable instanceof Method) {
            return this.annotationValueFactory.createFromObject(((Method) executable).getDefaultValue())
                    .toMaybe();
        }
        return Maybe.empty();
    }

    @Override
    public void close() {
        logger.debug("Nothing to close");
    }

    @Override
    public String toString() {
        return "ExecutableFactory{" +
                "nodeFactory=" + this.nodeFactory +
                ", kindFactory=" + this.kindFactory +
                ", modifierFactory=" + this.modifierFactory +
                ", nameFactory=" + this.nameFactory +
                ", annotationValueFactory=" + this.annotationValueFactory +
                "}";
    }

}
