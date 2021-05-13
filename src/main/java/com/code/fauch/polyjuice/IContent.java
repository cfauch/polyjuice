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

/**
 * Describes the content of a payload.
 * 
 * @author c.fauch
 *
 */
public interface IContent {

    /**
     * Register a listener to listen all changes on this content.
     * 
     * @param listener the listener to register (not null)
     * @return the registered listener
     */
    PropertyChangeListener addPropertyChangeListener(PropertyChangeListener listener);
    
    /**
     * Unregister a listener.
     * 
     * @param listener the listener to unregister
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Encode this content into a sequence of bytes.
     * 
     * @return the encoded content
     */
    byte[] getBytes();

}
