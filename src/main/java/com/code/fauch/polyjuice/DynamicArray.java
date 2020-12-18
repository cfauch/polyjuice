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
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author c.fauch
 *
 */
public final class DynamicArray<T extends IContent> implements IContent, Iterable<T> {

    /**
     * Name of the event raised when a new element is added.
     */
    public static final String NEW = "dynamic-array-add";

    /**
     * Name of the event raised when a old element is removed.
     */
    public static final String REMOVE = "dynamic-array-rm";

    /**
     * The dynamic ordered list of element to encode in the frame.
     */
    private final List<T> elements;
    
    /**
     * To manage listeners.
     */
    private final PropertyChangeSupport changeSupport;

    /**
     * Construct a new empty dynamic array.
     */
    public DynamicArray() {
        this.elements = new ArrayList<T>();
        this.changeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Add the listener not only to this array but also to all its elements
     */
    @Override
    public PropertyChangeListener addPropertyChangeListener(PropertyChangeListener listener) {
        if (!hasListener(listener)) {
            this.changeSupport.addPropertyChangeListener(listener);
            for (IContent content : this.elements) {
                content.addPropertyChangeListener(listener);
            }
        }
        return listener;
    }

    /**
     * Remove the listener not only to this array bus also all its elements.
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.removePropertyChangeListener(listener);
        for (IContent content: this.elements) {
            content.removePropertyChangeListener(listener);
        }
    }

    /**
     * Add an element to the list. This element obtain all the registered listeners to that array.
     * A new event it fire to listeners.
     * 
     * @param element the element to add
     */
    public void add(final T element) {
        if (this.elements.add(element)) {
            for (PropertyChangeListener l : this.changeSupport.getPropertyChangeListeners()) {
                element.addPropertyChangeListener(l);
            }
            fireEvent(NEW, this.elements.size() -1, element);
        }
    }
    
    /**
     * Remove an element from the list. All the listeners are removed from it.
     * A remove event is fire.
     * 
     * @param index index of the element to remove
     */
    public void remove(final int index) {
        final IContent removed = this.elements.remove(index);
        if (removed != null) {
            for (PropertyChangeListener l : this.changeSupport.getPropertyChangeListeners()) {
                removed.removePropertyChangeListener(l);
            }
        }
        fireEvent(REMOVE, index, removed);
    }
    
    /**
     * Check if a listener is already register.
     * 
     * @param listener the listener to check (not null)
     * @return true if listener already registered else false
     */
    private final boolean hasListener(PropertyChangeListener listener) {
        for (PropertyChangeListener l : this.changeSupport.getPropertyChangeListeners()) {
            if (l == listener) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reports array change to listeners.
     * 
     * @param event the event name
     * @param index the new/old index of the value
     * @param newValue the new/deleted value
     */
    private final void fireEvent(final String event, final int index, final Object newValue) {
        this.changeSupport.fireIndexedPropertyChange(event, index, null, newValue);
    }
    
    @Override
    public byte[] getBytes() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (IContent element : this.elements) {
            if (element != null) {
                output.writeBytes(element.getBytes());
            }
        }
        return output.toByteArray();
    }

    /**
     * To iterate over elements
     */
    @Override
    public final Iterator<T> iterator() {
        return this.elements.iterator();
    }

    /**
     * Returns the total number of elements.
     * @return total number of elements
     */
    public final int size() {
        return this.elements.size();
    }
    
}
