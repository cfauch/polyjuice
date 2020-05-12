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
package com.code.fauch.polyjuice.dto;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.code.fauch.polyjuice.IContent;

/**
 * @author c.fauch
 *
 */
public class DTOPayloadTest {

    @Test
    public void testLoad() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/template.yml")) {
            DTOPayload payload = new Yaml(new Constructor(DTOPayload.class)).load(in);
            Assert.assertEquals("SEQUENCE", payload.getType());
            Assert.assertNull(payload.getSize());
            Assert.assertEquals(5, payload.getParameters().size());
            final DTOParameter p1 = payload.getParameters().get(0);
            Assert.assertEquals("magical_number", p1.getName());
            Assert.assertEquals("INT", p1.getType());
            Assert.assertEquals(42, p1.getValue());
            Assert.assertTrue(p1.isReadonly());
            final DTOParameter p2 = payload.getParameters().get(1);
            Assert.assertEquals("msg_size", p2.getName());
            Assert.assertEquals("INT", p2.getType());
            Assert.assertEquals(13, p2.getValue());
            Assert.assertFalse(p2.isReadonly());
            final DTOParameter p3 = payload.getParameters().get(2);
            Assert.assertEquals("msg", p3.getName());
            Assert.assertEquals("STRING", p3.getType());
            Assert.assertEquals("HELLO WORLD !", p3.getValue());
            Assert.assertFalse(p3.isReadonly());
            Assert.assertTrue(p1 == payload.getParameters().get(4));
        }
    }

    @Test
    public void testBuildPayload() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/template.yml")) {
            final DTOPayload payload = new Yaml(new Constructor(DTOPayload.class)).load(in);
            final IContent content = payload.build();
            Assert.assertEquals(49, content.getBytes().length);
            content.getParameter("msg").setValue("MERCI");
            content.getParameter("msg_size").setValue(5);
            Assert.assertEquals(41, content.getBytes().length);
        }
    }

    @Test
    public void testBuildPayloadWithSize() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/template-with-size.yml")) {
            final DTOPayload payload = new Yaml(new Constructor(DTOPayload.class)).load(in);
            final IContent content = payload.build();
            Assert.assertEquals(20, content.getBytes().length);
            content.getParameter("msg").setValue("MERCI");
            content.getParameter("msg_size").setValue(5);
            Assert.assertEquals(20, content.getBytes().length);
        }
    }

    @Test
    public void testBuildPayloadWithoutParameters() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/template-without-parameters.yml")) {
            final DTOPayload payload = new Yaml(new Constructor(DTOPayload.class)).load(in);
            final IContent content = payload.build();
            Assert.assertEquals(0, content.getBytes().length);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testBuildPayloadMissingParameters() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/template-missing-parameters.yml")) {
            final DTOPayload payload = new Yaml(new Constructor(DTOPayload.class)).load(in);
            payload.build();
        }
    }

    @Test
    public void testBuildParameter() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/template-parameter.yml")) {
            final DTOPayload payload = new Yaml(new Constructor(DTOPayload.class)).load(in);
            final IContent content = payload.build();
            Assert.assertEquals(4, content.getBytes().length);
            Assert.assertEquals(42, content.getParameter("magical_number").getValue());
        }
    }

}
