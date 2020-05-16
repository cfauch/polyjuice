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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.code.fauch.polyjuice.Parameter;

/**
 * Factory used to build new instance of IObject.
 * 
 * @author c.fauch
 *
 */
public class ObjectFactory {

    /**
     * The ordered list of parameters.
     */
    private List<ParameterFactory> parameters;

    /**
     * Returns the ordered list of parameters.
     * 
     * @return the parameters
     */
    public final List<ParameterFactory> getParameters() {
        return parameters;
    }

    /**
     * Specify the ordered list of parameters.
     * 
     * @param parameters the parameters to set
     */
    public final void setParameters(final List<ParameterFactory> parameters) {
        this.parameters = parameters;
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
    public <U extends IObject> U build(final Class<U> clss) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException, NoSuchMethodException, SecurityException, IntrospectionException {
        final U truc = Objects.requireNonNull(clss, "clss is required").getConstructor().newInstance();
        final List<Parameter<?>> order = new ArrayList<>(Objects.requireNonNull(this.parameters, "missing 'parameters'").size());
        truc.setOrderedParameters(order);
        for (final ParameterFactory pf : this.parameters) {
            order.add(set(Objects.requireNonNull(pf.getName(), "missing 'name' in one parameter"), pf.build(), truc));
        }
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
    private final <U, V> U set(final String name, final U value, final V truc) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
            IntrospectionException {
        final PropertyDescriptor pd = new PropertyDescriptor(name, truc.getClass());
        pd.getWriteMethod().invoke(truc, value);
        return value;
    }
    
}
