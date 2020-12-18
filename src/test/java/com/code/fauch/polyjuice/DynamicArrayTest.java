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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author c.fauch
 *
 */
public class DynamicArrayTest {

    @Test
    public void testEncode() {
        final DynamicArray<Parameter<?>> array = new DynamicArray<>();
        array.add(Parameter.newParameter("size", StdType.SHORT, (short)5, false));
        array.add(Parameter.newParameter("msg", StdType.STRING, "Hello", false));
        byte[] msg = "Hello".getBytes(StandardCharsets.UTF_8);
        Assert.assertArrayEquals(new byte[] {0, 5, msg[0], msg[1], msg[2], msg[3], msg[4]}, array.getBytes());
    }

    @Test
    public void testAddElement() {
        final List<String> labels = new ArrayList<>();
        final DynamicArray<Parameter<?>> array = new DynamicArray<>();
        array.addPropertyChangeListener(e -> labels.add(e.getPropertyName()));
        final Parameter<Short> size = Parameter.newParameter("size", StdType.SHORT, (short)5, false);
        final Parameter<String> msg = Parameter.newParameter("msg", StdType.STRING, "Hello", false);
        array.add(size);
        array.add(msg);
        size.setValue((short)6);
        Assert.assertArrayEquals(new String[] {DynamicArray.NEW, DynamicArray.NEW, Parameter.VALUE}, labels.toArray());
    }
    
    @Test
    public void testRemoveElement() {
        final List<String> labels = new ArrayList<>();
        final DynamicArray<Parameter<?>> array = new DynamicArray<>();
        array.addPropertyChangeListener(e -> labels.add(e.getPropertyName()));
        final Parameter<Short> size = Parameter.newParameter("size", StdType.SHORT, (short)5, false);
        final Parameter<String> msg = Parameter.newParameter("msg", StdType.STRING, "Hello", false);
        array.add(size);
        array.add(msg);
        array.remove(0);
        size.setValue((short)6);
        Assert.assertArrayEquals(new String[] {DynamicArray.NEW, DynamicArray.NEW, DynamicArray.REMOVE}, labels.toArray());
        byte[] msgb = "Hello".getBytes(StandardCharsets.UTF_8);
        Assert.assertArrayEquals(new byte[] {msgb[0], msgb[1], msgb[2], msgb[3], msgb[4]}, array.getBytes());
    }
    
    @Test
    public void testAddListener() {
        final List<String> labels = new ArrayList<>();
        final DynamicArray<Parameter<?>> array = new DynamicArray<>();
        final Parameter<Short> size = Parameter.newParameter("size", StdType.SHORT, (short)5, false);
        final Parameter<String> msg = Parameter.newParameter("msg", StdType.STRING, "Hello", false);
        array.add(size);
        array.add(msg);
        array.addPropertyChangeListener(e -> labels.add(e.getPropertyName()));
        size.setValue((short)6);
        Assert.assertArrayEquals(new String[] {Parameter.VALUE}, labels.toArray());
    }
    
    @Test
    public void testRemoveListener() {
        final List<String> labels = new ArrayList<>();
        final DynamicArray<Parameter<?>> array = new DynamicArray<>();
        final Parameter<Short> size = Parameter.newParameter("size", StdType.SHORT, (short)5, false);
        final Parameter<String> msg = Parameter.newParameter("msg", StdType.STRING, "Hello", false);
        array.add(size);
        array.add(msg);
        final PropertyChangeListener l = array.addPropertyChangeListener(e -> labels.add(e.getPropertyName()));
        array.removePropertyChangeListener(l);
        size.setValue((short)6);
        Assert.assertArrayEquals(new String[] {}, labels.toArray());
    }
}
