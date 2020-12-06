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
public abstract class AbsContent implements IContent, IObject {
    
    /**
     * The expected ordered parameters in the sequence
     */
    private final List<Parameter<?>> orderedParameters;
    
    /**
     * Constructor.
     * 
     * Without parameters.
     */
    public AbsContent() {
        this.orderedParameters = new ArrayList<>();
    }
    
    /**
     * Constructor.
     * With parameters.
     * 
     * @param orderedParameters the ordered list of parameters
     */
    public AbsContent(final List<Parameter<?>> orderedParameters) {
        this.orderedParameters = orderedParameters;
    }
    
    /**
     * Subscribes the listener on each parameters.
     */
    @Override
    public final void addPropertyChangeListener(final PropertyChangeListener listener) {
        for (Parameter<?> p : this.orderedParameters) {
            if (!p.hasListener(listener)) {
                p.addPropertyChangeListener(listener);
            }
        }
    }
    
    /**
     * Removes the listener of each parameters.
     */
    @Override
    public final void removePropertyChangeListener(final PropertyChangeListener listener) {
        for (Parameter<?> p: this.orderedParameters) {
            p.removePropertyChangeListener(listener);
        }
    }
    
    /**
     * Encode each parameters.
     */
    @Override
    public byte[] getBytes() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (Parameter<?> p : this.orderedParameters) {
            if (p != null) {
                output.writeBytes(p.getBytes());
            }
        }
        return output.toByteArray();
    }
    
    /**
     * Returns all the parameters in the same order as expected in the encoded sequence.
     * 
     * @return all the parameters.
     */
    public List<Parameter<?>> getOrderedParameters() {
        return this.orderedParameters;
    }
    
    @Override
    public void addOrderedParameters(final List<Parameter<?>> orderedParameters) {
        this.orderedParameters.addAll(orderedParameters);
    }
}
