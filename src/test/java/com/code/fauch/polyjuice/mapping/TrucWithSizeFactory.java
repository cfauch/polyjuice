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
 * @author c.fauch
 *
 */
public final class TrucWithSizeFactory extends ObjectFactory {

    private Integer size;

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    public TrucWithSize build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException {
        final TrucWithSize truc = super.build(TrucWithSize.class);
        truc.setSize(this.size);
        return truc;
    }
    
}
