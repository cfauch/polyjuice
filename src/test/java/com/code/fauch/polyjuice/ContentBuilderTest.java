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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author c.fauch
 *
 */
public class ContentBuilderTest {

    @Test
    public void buildParameter() {
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final IContent content = new ContentBuilder(StdContentFactory.PARAMETER).parameter(p1).build();
        Assert.assertTrue(p1 == content);
    }

    @Test
    public void buildSequence() {
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final Parameter<String> p2 = Parameter.newParameter("l2", StdType.STRING, "Hello World !", false);
        final IContent content = new ContentBuilder(StdContentFactory.SEQUENCE).addItem(p1).addItem(p2).build();
        Parameter<?> found = content.getParameter("l1");
        Assert.assertTrue(found == p1);
        found = content.getParameter("l2");
        Assert.assertTrue(found == p2);
        Assert.assertEquals(17, content.getBytes().length);
    }

    @Test
    public void buildSequenceWithNullItem() {
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final Parameter<String> p2 = Parameter.newParameter("l2", StdType.STRING, "Hello World !", false);
        final IContent content = new ContentBuilder(StdContentFactory.SEQUENCE).addItem(p1).addItem(null).addItem(p2).build();
        Parameter<?> found = content.getParameter("l1");
        Assert.assertTrue(found == p1);
        found = content.getParameter("l2");
        Assert.assertTrue(found == p2);
        Assert.assertEquals(17, content.getBytes().length);
    }

    @Test
    public void buildSequenceWithSize() {
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final Parameter<String> p2 = Parameter.newParameter("l2", StdType.STRING, "Hello World !", false);
        final IContent content = new ContentBuilder(StdContentFactory.SEQUENCE).addItem(p1).addItem(p2).size(2).build();
        Assert.assertEquals(2, content.getBytes().length);
    }

}
