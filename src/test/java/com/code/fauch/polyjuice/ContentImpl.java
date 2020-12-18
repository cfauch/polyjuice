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
package com.code.fauch.polyjuice;

import java.util.Arrays;

/**
 * Fake content implementation for test.
 * 
 * @author c.fauch
 *
 */
public final class ContentImpl extends AbsContent {

    /**
     * Item definition
     * 
     * @author c.fauch
     *
     */
    public static final class Item extends AbsContent {
        
        /** 
         * Size in bytes of the message.
         */
        private final Parameter<Short> size;
        
        /**
         * The message.
         */
        private final Parameter<String> msg;
        
        /**
         * Construct a new item without value.
         */
        private Item() {
            this.size = Parameter.newParameter("size", StdType.SHORT, null, false);
            this.msg = Parameter.newParameter("msg", StdType.STRING, null, false);
            this.addOrderedContents(Arrays.asList(this.size, this.msg));
        }
        
        /**
         * Set a new message value. The size is automatically updated.
         * @param msg the new message
         * @return this item
         */
        public Item message(final String msg) {
            this.msg.setValue(msg);
            this.size.setValue((short)msg.length());
            return this;
        }
    }
    
    /**
     * Total number of items
     */
    private final Parameter<Short> nb;
    
    /**
     * List of items
     */
    private final DynamicArray<Item> items; 
    
    /**
     * Construct a content without any items.
     */
    public ContentImpl() {
        this.nb = Parameter.newParameter("nb", StdType.SHORT, (short)0, false);
        this.items = new DynamicArray<>();
        this.addOrderedContents(Arrays.asList(this.nb, this.items));
    }
    
    /**
     * Add and returns a new empty item. The number of items is automatically updated.
     * 
     * @return the item
     */
    public Item newItem() {
        final Item item = new Item();
        this.items.add(item);
        this.nb.setValue((short) (this.nb.getValue() + 1));
        return item;
    }
}
