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
package org.rookit.convention.auto.metatype.property;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.rookit.auto.javax.executable.ExtendedExecutableElement;
import org.rookit.auto.javax.type.ExtendedTypeElement;
import org.rookit.auto.javax.type.ExtendedTypeElementFactory;
import org.rookit.auto.javax.type.mirror.ExtendedTypeMirrorFactory;
import org.rookit.convention.auto.metatype.guice.PropertyModelModuleMixin;
import org.rookit.convention.property.guice.ImmutablePropertyModel;
import org.rookit.convention.property.guice.MutablePropertyModel;
import org.rookit.convention.property.guice.MutablePropertyModelSet;
import org.rookit.convention.property.guice.PropertyModel;
import org.rookit.convention.property.guice.PropertyModelGet;
import org.rookit.convention.property.guice.PropertyModelPropertyName;

import javax.lang.model.element.TypeElement;

import static java.lang.String.format;

public final class PropertyModule extends AbstractModule
        implements PropertyModelModuleMixin<ExtendedExecutableElement> {

    private static final String NO_META_TYPE_METHOD = "Cannot find '%s' method in meta type '%s'.";
    private static final String INVALID_META_TYPE_CLASS = "Something went wrong when using '%s' as a meta type class.";

    private static final Module MODULE = new PropertyModule();

    public static Module getModule() {
        return MODULE;
    }

    private PropertyModule() {}

    @SuppressWarnings({"AnonymousInnerClassMayBeStatic", "AnonymousInnerClass", "EmptyClass"})
    @Override
    protected void configure() {
        bindPropertyModelProperties(binder());
        bind(new TypeLiteral<Class<?>>() {}).annotatedWith(PropertyModel.class)
                .toInstance(org.rookit.convention.property.PropertyModel.class);
        bind(new TypeLiteral<Class<?>>() {}).annotatedWith(ImmutablePropertyModel.class)
                .toInstance(org.rookit.convention.property.ImmutablePropertyModel.class);
        bind(new TypeLiteral<Class<?>>() {}).annotatedWith(MutablePropertyModel.class)
                .toInstance(org.rookit.convention.property.MutablePropertyModel.class);
    }

    @Provides
    @Singleton
    @MutablePropertyModelSet
    ExtendedExecutableElement mutablePropertySetterMethod(
            @MutablePropertyModel final ExtendedTypeElement propertyElement) {

        return getMethodOrFail(propertyElement, "set");
    }

    @Provides
    @Singleton
    @PropertyModelGet
    ExtendedExecutableElement getterMethod(@PropertyModel final ExtendedTypeElement propertyElement) {
        return getMethodOrFail(propertyElement, "get");
    }

    @Provides
    @Singleton
    @PropertyModelPropertyName
    ExtendedExecutableElement propertyNameMethod(@PropertyModel final ExtendedTypeElement metaTypeElement) {
        return getMethodOrFail(metaTypeElement, "propertyName");
    }

    @Override
    public Key<ExtendedExecutableElement> key() {
        return Key.get(ExtendedExecutableElement.class, PropertyModel.class);
    }

    @Provides
    @Singleton
    @MutablePropertyModel
    ExtendedTypeElement mutablePropertyModel(@MutablePropertyModel final Class<?> clazz,
                                             final ExtendedTypeMirrorFactory mirrorFactory,
                                             final ExtendedTypeElementFactory typeFactory) {
        return mirrorFactory.createWithErasure(clazz)
                .toElement()
                .select(TypeElement.class)
                .map(typeFactory::extend)
                .orElseThrow(() -> new IllegalArgumentException(format(INVALID_META_TYPE_CLASS, clazz)));
    }

    @Provides
    @Singleton
    @ImmutablePropertyModel
    ExtendedTypeElement immutablePropertyModel(@ImmutablePropertyModel final Class<?> clazz,
                                               final ExtendedTypeMirrorFactory mirrorFactory,
                                               final ExtendedTypeElementFactory typeFactory) {
        return mirrorFactory.createWithErasure(clazz)
                .toElement()
                .select(TypeElement.class)
                .map(typeFactory::extend)
                .orElseThrow(() -> new IllegalArgumentException(format(INVALID_META_TYPE_CLASS, clazz)));
    }

    @Provides
    @Singleton
    @PropertyModel
    ExtendedTypeElement propertyModel(@PropertyModel final Class<?> propertyModelClass,
                                      final ExtendedTypeMirrorFactory mirrorFactory,
                                      final ExtendedTypeElementFactory typeFactory) {
        // TODO this can be generified with the method above
        return mirrorFactory.createWithErasure(propertyModelClass)
                .toElement()
                .select(TypeElement.class)
                .map(typeFactory::extend)
                .orElseThrow(() -> new IllegalArgumentException(format(INVALID_META_TYPE_CLASS, propertyModelClass)));
    }

    // TODO this is generic enough to not belong here.
    // TODO also, copied fromExecutableElement MetaTypeModule
    private ExtendedExecutableElement getMethodOrFail(final ExtendedTypeElement typeElement, final String name) {
        return typeElement.getMethod(name)
                .orElseThrow(() -> new IllegalArgumentException(
                        format(NO_META_TYPE_METHOD, name, typeElement.getSimpleName())));
    }
}
