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
import java.util.Arrays;

/**
 * Partial implementation of content that manage property change listeners and encoding based on the ability of iterate
 * over a sequence of parameters.
 * Real implementations just have to implement the parameters iteration.
 * 
 * @author c.fauch
 *
 */
public abstract class AbsContent implements IContent, Iterable<Parameter<?>> {
    
    /**
     * Subscribes the listener on each parameters.
     */
    @Override
    public final void addPropertyChangeListener(final PropertyChangeListener listener) {
        for (Parameter<?> p : this) {
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
        for (Parameter<?> p: this) {
            p.removePropertyChangeListener(listener);
        }
    }
    
    /**
     * Adapt the result if necessary to match the expected size if it is defined.
     */
    @Override
    public final byte[] getBytes() {
        return getExpectedSize() == null ? encode() : Arrays.copyOf(encode(), getExpectedSize());
    }

    /**
     * Returns the expected size of the encoded content.
     * If not null the result is truncated and padded with zeros to match the expected size.
     * 
     * @return null by default.
     */
    protected Integer getExpectedSize() {
        return null;
    }
    
    /**
     * Encode each parameters.
     * 
     * @return the encoded content.
     */
    private final byte[] encode() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (Parameter<?> p : this) {
            if (p != null) {
                output.writeBytes(p.getBytes());
            }
        }
        return output.toByteArray();
    }
    
}
