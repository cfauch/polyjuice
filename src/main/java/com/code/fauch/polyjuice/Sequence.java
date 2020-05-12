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

import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Definition of a sequence of item.
 * It is possible for client code to subscribe on changes on all items.
 * 
 * @author c.fauch
 *
 */
public class Sequence implements Iterable<IContent>, IContent {
    
    /**
     * The expected size of the encoded sequence (in bytes).
     * Null means auto-adjust
     */
    private final Integer size;
    
    /**
     * The ordered list of item of the sequence.
     */
    private final ArrayList<IContent> items;
    
    /**
     * Constructor.
     * 
     * @param size the expected size in bytes of the encoded content (may be null).
     */
    private Sequence(final Integer size) {
        this.size = size;
        this.items = new ArrayList<IContent>();
    }
    
    /**
     * Constructor.
     * 
     * @param size the expected size in bytes of the encoded content (may be null).
     * @param items the ordered list of items (list to copy, not null may be empty)
     */
    protected Sequence(final Integer size, final List<IContent> items) {
        this(size);
        this.items.addAll(Objects.requireNonNull(items, "items is mandatory"));
    }
        
    /**
     * The expected size.
     * Null means auto-adjust
     */
    public final Integer getSize() {
        return this.size;
    }

    /**
     * Register the listener to listen all items of the sequence
     */
    @Override
    public final void addPropertyChangeListener(PropertyChangeListener listener) {
        for (IContent item : this.items) {
            item.addPropertyChangeListener(listener);
        }
    }

    /**
     * Unregister the listener from all items of the sequence
     */
    @Override
    public final void removePropertyChangeListener(PropertyChangeListener listener) {
        for (IContent item : this.items) {
            item.removePropertyChangeListener(listener);
        }
    }

    @Override
    public final Iterator<IContent> iterator() {
        return this.items.iterator();
    }

    @Override
    public final byte[] getBytes() {
        return this.size == null ? encodeItems() : Arrays.copyOf(encodeItems(), this.size);
    }
    
    protected byte[] encodeItems() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (IContent item : this.items) {
            if (item != null) {
                output.writeBytes(item.getBytes());
            }
        }
        return output.toByteArray();
    }

    @Override
    public <U> Parameter<U> getParameter(String name) {
        //TODO mefiance les cycles
        for (IContent item : this.items) {
            if (item != null) {
                final Parameter<U> param = item.getParameter(name);
                if (param != null) {
                    return param;
                }
            }
        }
        return null;
    }
    
}
