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
import java.time.Duration;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


/**
 * @author c.fauch
 *
 */
public class ObjectFactoryTest {

    @Test
    public void testSimpleContent() throws Exception {
        final ObjectFactory factory = new ObjectFactory();
        final ParameterFactory pf1 = new ParameterFactory();
        final ParameterFactory pf2 = new ParameterFactory();
        factory.setContents(Arrays.asList(pf1, pf2));
        pf1.setName("msgSize");
        pf1.setType("INT");
        pf1.setValue(2);
        pf2.setName("msg");
        pf2.setType("STRING");
        pf2.setValue("no");
        final SimpleContent simple = factory.build(SimpleContent.class);
        Assert.assertEquals(2, simple.getMsgSize().getValue().intValue());
        Assert.assertEquals("no", simple.getMsg().getValue());
    }
    
    @Test
    public void testSimpleContentFromYaml() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/simple-content-template.yml")) {
            final ObjectFactory factory = new Yaml().load(in);
            final SimpleContent simple = factory.build(SimpleContent.class);
            Assert.assertEquals(13, simple.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", simple.getMsg().getValue());
        }
    }

    @Test
    public void testComposiyeContentFromYaml() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/composite-content-template.yml")) {
            final ObjectFactory factory = new Yaml().load(in);
            final CompositeContent composite = factory.build(CompositeContent.class);
            Assert.assertNull(composite.getSize().getValue());
            Assert.assertEquals(Duration.ofHours(-12), composite.getClock().getValue().getInformation());
            final SimpleContent simple = composite.getSubContent();
            Assert.assertEquals(13, simple.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", simple.getMsg().getValue());
        }
    }

    @Test
    public void testImmutableContentFromYaml() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/simple-content-template.yml")) {
            final ObjectFactory factory = new Yaml(new Constructor(ObjectFactory.class)).load(in);
            final ImmutableContent truc = factory.build(ImmutableContent.Builder.class).build();
            Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
       }
    }
    
}
