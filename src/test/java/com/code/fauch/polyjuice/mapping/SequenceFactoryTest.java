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

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.code.fauch.polyjuice.IContent;
import com.code.fauch.polyjuice.Sequence;

/**
 * @author c.fauch
 *
 */
public class SequenceFactoryTest {

    @Test
    public void testLoad() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/seq-template.yml")) {
            SequenceFactory sf = new Yaml(new Constructor(SequenceFactory.class)).load(in);
            Assert.assertNull(sf.getSize());
            Assert.assertEquals(5, sf.getParameters().size());
            final ParameterFactory p1 = sf.getParameters().get(0);
            Assert.assertEquals("magical_number", p1.getName());
            Assert.assertEquals("INT", p1.getType());
            Assert.assertEquals(42, p1.getValue());
            Assert.assertTrue(p1.isReadonly());
            final ParameterFactory p2 = sf.getParameters().get(1);
            Assert.assertEquals("msg_size", p2.getName());
            Assert.assertEquals("INT", p2.getType());
            Assert.assertEquals(13, p2.getValue());
            Assert.assertFalse(p2.isReadonly());
            final ParameterFactory p3 = sf.getParameters().get(2);
            Assert.assertEquals("msg", p3.getName());
            Assert.assertEquals("STRING", p3.getType());
            Assert.assertEquals("HELLO WORLD !", p3.getValue());
            Assert.assertFalse(p3.isReadonly());
            Assert.assertTrue(p1 == sf.getParameters().get(4));
        }
    }

    @Test
    public void testBuildPayload() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/seq-template.yml")) {
            final SequenceFactory sf = new Yaml(new Constructor(SequenceFactory.class)).load(in);
            final Sequence content = sf.build();
            Assert.assertEquals(49, content.getBytes().length);
            content.getParameter("msg").setValue("MERCI");
            content.getParameter("msg_size").setValue(5);
            Assert.assertEquals(41, content.getBytes().length);
        }
    }

    @Test
    public void testBuildPayloadWithSize() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/seq-template-with-size.yml")) {
            final SequenceFactory sf = new Yaml(new Constructor(SequenceFactory.class)).load(in);
            final Sequence content = sf.build();
            Assert.assertEquals(20, content.getBytes().length);
            content.getParameter("msg").setValue("MERCI");
            content.getParameter("msg_size").setValue(5);
            Assert.assertEquals(20, content.getBytes().length);
        }
    }

    @Test
    public void testBuildPayloadWithoutParameters() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/seq-template-without-parameters.yml")) {
            final SequenceFactory sf = new Yaml(new Constructor(SequenceFactory.class)).load(in);
            final IContent content = sf.build();
            Assert.assertEquals(0, content.getBytes().length);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testBuildPayloadMissingParameters() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/seq-template-missing-parameters.yml")) {
            final SequenceFactory payload = new Yaml(new Constructor(SequenceFactory.class)).load(in);
            payload.build();
        }
    }


}
