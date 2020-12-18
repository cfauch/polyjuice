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
package com.code.fauch.polyjuice.mapping;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.code.fauch.polyjuice.DynamicArray;


/**
 * @author c.fauch
 *
 */
public class DynamicArrayFactoryTest {

    @Test
    public void testInitializedArray() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/array-content-template.yml")) {
            final ObjectFactory factory = new Yaml().load(in);
            final CompositeArrayContent content = factory.build(CompositeArrayContent.class);
            final DynamicArray<SimpleContent> elts = content.getElts();
            Assert.assertEquals(1, elts.size());
        }
    }

    @Test
    public void testEmptyArray() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/empty-array-content-template.yml")) {
            final ObjectFactory factory = new Yaml().load(in);
            final CompositeArrayContent content = factory.build(CompositeArrayContent.class);
            final DynamicArray<SimpleContent> elts = content.getElts();
            elts.add(new SimpleContent());
            elts.add(new SimpleContent());
            Assert.assertEquals(2, elts.size());
        }
    }

}
