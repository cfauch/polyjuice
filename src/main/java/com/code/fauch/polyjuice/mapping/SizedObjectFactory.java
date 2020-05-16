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
import java.lang.reflect.InvocationTargetException;

/**
 * Factory used to build new instance of ISizedObject.
 * 
 * @author c.fauch
 *
 */
public class SizedObjectFactory extends AbsObjectFactory<ISizedObject> {

    /**
     * The expected size of the encoded sequence.
     */
    private Integer size;

    /**
     * Returns the expected size of the encoded sequence.
     * 
     * @return the size in bytes
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Specify the expected size of the encoded sequence.
     * 
     * @param size the size to set in bytes
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Build an object of the given class with the expected size if defined.
     */
    @Override
    public <U extends ISizedObject> U build(Class<U> clss)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, IntrospectionException {
        final U obj =  super.build(clss);
        obj.setExpectedSize(this.size);
        return obj;
    }
}
