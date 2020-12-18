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

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import com.code.fauch.polyjuice.DynamicArray;
import com.code.fauch.polyjuice.IContent;

/**
 * @author c.fauch
 *
 */
public final class DynamicArrayFactory implements IContentFactory<DynamicArray<?>> {
 
    /**
     * name of the field
     */
    private String name;

    /**
     * The ordered items.
     */
    private List<IContentFactory<?>> items;

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
     * Specify the ordered list of items.
     * 
     * @return the items
     */
    public List<IContentFactory<?>> getItems() {
        return items;
    }

    /**
     * Returns the ordered list of items.
     * 
     * @param items the items to set
     */
    public void setItems(List<IContentFactory<?>> items) {
        this.items = items;
    }
    
    /**
     * Build and returns a new dynamic array.
     */
    @Override
    public <U extends DynamicArray<?>> U build(Class<U> clss, Type... genericTypes) throws Exception {
        //final U array = Objects.requireNonNull(clss, "clss is required").getConstructor().newInstance();
        final DynamicArray<IContent> array = from(genericTypes[0]);
        for (final IContentFactory<?> cf : Objects.requireNonNull(this.items, "missing 'items'")) {
            array.add(buildItem(cf, genericTypes[0]));
        }
        return (U) array;
    }
    
    private static final <U extends IContent> DynamicArray<U> newArray(final Class<U> cls) {
        return new DynamicArray<U>();
    }
    
    private static final <U extends IContent> DynamicArray<U> from(final Type t) {
        return newArray((Class<U>) t);
    }
    
    private <U extends IContent> U buildItem(IContentFactory<U> fact, Type cls) throws Exception {
        return fact.build((Class<U>) cls, new Type[0]);
    }

}
