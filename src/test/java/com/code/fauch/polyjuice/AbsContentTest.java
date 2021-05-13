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

import org.junit.Assert;
import org.junit.Test;

import com.code.fauch.polyjuice.ContentImpl.Item;

/**
 * @author c.fauch
 *
 */
public class AbsContentTest {

    /**
     * EXPECTED:
     * +---2bytes---+---2bytes---+---3bytes---+---2bytes---+---5bytes---+---2bytes---+---4bytes---+
     * |     3      |     3      |    red     |     5      |    green   |     4      |    blue    |
     * +------------+------------+------------+------------+------------+------------+------------+
     */
    @Test
    public void testEncode() {
        final ContentImpl content = new ContentImpl();
        content.newItem().message("red");
        content.newItem().message("green");
        content.newItem().message("blue");
        byte[] red = "red".getBytes(StandardCharsets.UTF_8);
        byte[] green = "green".getBytes(StandardCharsets.UTF_8);
        byte[] blue = "blue".getBytes(StandardCharsets.UTF_8);
        byte[] expecteds = new byte[] {0, 3, 0, 3, red[0], red[1], red[2], 
                0, 5, green[0], green[1], green[2], green[3], green[4], 
                0, 4, blue[0], blue[1], blue[2], blue[3]};
        Assert.assertArrayEquals(expecteds, content.getBytes());
    }
    
    @Test
    public void testAddListener() {
        final ArrayList<String> events = new ArrayList<>();
        final ContentImpl content = new ContentImpl();
        final Item red = content.newItem().message("red");
        content.newItem().message("green");
        content.newItem().message("blue");
        content.addPropertyChangeListener(e -> events.add(e.getPropertyName()));
        red.message("yello");
        Assert.assertArrayEquals(new String[] {Parameter.VALUE,  Parameter.VALUE}, events.toArray());
    }
    
    @Test
    public void testRemoveListener() {
        final ArrayList<String> events = new ArrayList<>();
        final ContentImpl content = new ContentImpl();
        final Item red = content.newItem().message("red");
        content.newItem().message("green");
        content.newItem().message("blue");
        final PropertyChangeListener l = content.addPropertyChangeListener(e -> events.add(e.getPropertyName()));
        content.removePropertyChangeListener(l);
        red.message("yello");
        Assert.assertArrayEquals(new String[] {}, events.toArray());
    }

    @Test
    public void testAddItem() {
        final ArrayList<String> events = new ArrayList<>();
        final ContentImpl content = new ContentImpl();
        final Item red = content.newItem().message("red");
        content.addPropertyChangeListener(e -> events.add(e.getPropertyName()));
        content.newItem().message("green");
        content.newItem().message("blue");
        red.message("yello");
        Assert.assertArrayEquals(new String[] {
                DynamicArray.NEW, Parameter.VALUE, Parameter.VALUE, Parameter.VALUE, 
                DynamicArray.NEW, Parameter.VALUE, Parameter.VALUE, Parameter.VALUE,
                Parameter.VALUE,  Parameter.VALUE
            }, events.toArray());
    }
}
