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
import java.util.List;

import com.code.fauch.polyjuice.mapping.IObject;

/**
 * Partial implementation of content that manage property change listeners and encoding.
 * It is possible for client code to subscribe on changes on all parameters.
 *  
 * @author c.fauch
 *
 */
public abstract class AbsContent implements IObject {
    
    /**
     * The expected ordered content in the sequence
     */
    private final List<IContent> orderedContents;
    
    /**
     * Constructor.
     * 
     * Without parameters.
     */
    public AbsContent() {
        this.orderedContents = new ArrayList<>();
    }
    
    /**
     * Constructor with ordered contents.
     * 
     * @param orderedContents the ordered list of contents
     */
    public AbsContent(final List<IContent> orderedContents) {
        this.orderedContents = orderedContents;
    }
    
    /**
     * Subscribes the listener on each contents.
     */
    @Override
    public final PropertyChangeListener addPropertyChangeListener(final PropertyChangeListener listener) {
        for (IContent content : this.orderedContents) {
            content.addPropertyChangeListener(listener);
        }
        return listener;
    }
    
    /**
     * Removes the listener of each contents.
     */
    @Override
    public final void removePropertyChangeListener(final PropertyChangeListener listener) {
        for (IContent content: this.orderedContents) {
            content.removePropertyChangeListener(listener);
        }
    }
    
    /**
     * Encode each contents.
     */
    @Override
    public byte[] getBytes() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (IContent content : this.orderedContents) {
            if (content != null) {
                output.writeBytes(content.getBytes());
            }
        }
        return output.toByteArray();
    }
    
    /**
     * Returns all the parameters in the same order as expected in the encoded sequence.
     * 
     * @return all the parameters.
     */
    public final List<IContent> getOrderedContents() {
        return this.orderedContents;
    }
    
    @Override
    public final void addOrderedContents(final List<IContent> orderedContents) {
        this.orderedContents.addAll(orderedContents);
    }
}
