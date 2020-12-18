/*
 * Copyright 2020 Claire Fauch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.code.fauch.polyjuice.mapping;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.code.fauch.polyjuice.IContent;

/**
 * Abstract object factory.
 * This class can be extended to build other object.
 * 
 * @author c.fauch
 *
 */
public abstract class AbsObjectFactory<T extends IObject> implements IContentFactory<T> {

    /**
     * name of the field
     */
    private String name;

    /**
     * Returns the name of the field (may be null)
     * 
     * @return the name
     */
    @Override
    public final String getName() {
        return name;
    }

    /**
     * Specify the name of the field (may  be null)
     * 
     * @param name the name to set
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * The ordered list of contents.
     */
    private List<IContentFactory<?>> contents;

    /**
     * Returns the ordered list of contents.
     * 
     * @return the contents
     */
    public final List<IContentFactory<?>> getContents() {
        return contents;
    }

    /**
     * Specify the ordered list of contents.
     * 
     * @param contents the ordered list of contents to set
     */
    public final void setContents(final List<IContentFactory<?>> contents) {
        this.contents = contents;
    }
    
    /**
     * Build an object of a given class.
     * The ordered list of parameters must not be null.
     * @param <U>
     * @param clss the class of the object to build (not null).
     * @return the new instance
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IntrospectionException
     */
    public <U extends T> U build(final Class<U> clss, Type... genericTypes) 
            throws Exception {
        final U truc = Objects.requireNonNull(clss, "clss is required").getConstructor().newInstance();
        final List<IContent> order = new ArrayList<>(Objects.requireNonNull(this.contents, "missing 'contents'").size());
        for (final IContentFactory<?> cf : this.contents) {
            order.add(set(cf, truc));
        }
        truc.addOrderedContents(order);
        return truc;
    }
    
    /**
     * Invoke a setter to set the given value into the field of the given name.
     * 
     * @param <U>
     * @param <V>
     * @param name the name of the field (not null)
     * @param value the value to set
     * @param truc the object on which to invoke setter (not null)
     * @return the value
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     */
    private final <U extends IContent> U set(final IContentFactory<U> cf, final IContent truc) 
            throws Exception {
        final PropertyDescriptor pd = new PropertyDescriptor(Objects.requireNonNull(cf.getName(), "missing 'name' in one content"), truc.getClass());
        final Method setter = pd.getWriteMethod();
        final ArrayList<Type> mappingGenericTypes = new ArrayList<>();
         for(Type genericParameterType : setter.getGenericParameterTypes()){
            if(genericParameterType instanceof ParameterizedType){
                for(Type parameterArgType : ((ParameterizedType) genericParameterType).getActualTypeArguments()){
                    mappingGenericTypes.add(parameterArgType);
                }
            }
        }
        @SuppressWarnings("unchecked")
        final U value = cf.build((Class<U>) pd.getPropertyType(), mappingGenericTypes.toArray(new Type[0]));
        setter.invoke(truc, value);
        return value;
    }

}
