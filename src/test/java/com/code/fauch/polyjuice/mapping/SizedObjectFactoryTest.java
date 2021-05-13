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
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 * @author c.fauch
 *
 */
public class SizedObjectFactoryTest {

    @Test
    public void testFixedSizeContent() throws Exception {
        final SizedObjectFactory factory = new SizedObjectFactory();
        final ParameterFactory pf1 = new ParameterFactory();
        final ParameterFactory pf2 = new ParameterFactory();
        factory.setContents(Arrays.asList(pf1, pf2));
        factory.setSize(20);
        pf1.setName("msgSize");
        pf1.setType("INT");
        pf1.setValue(2);
        pf2.setName("msg");
        pf2.setType("STRING");
        pf2.setValue("no");
        final FixedSizeContent sizedContent = factory.build(FixedSizeContent.class);
        Assert.assertEquals(20, sizedContent.getExpectedSize().intValue());
        Assert.assertEquals(2, sizedContent.getMsgSize().getValue().intValue());
        Assert.assertEquals("no", sizedContent.getMsg().getValue());
    }
    
    @Test
    public void testFixedSizeContentFromYaml() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/fixedsize-content-template.yml")) {
            final SizedObjectFactory factory = new Yaml(new Constructor(SizedObjectFactory.class)).load(in);
            final FixedSizeContent content = factory.build(FixedSizeContent.class);
            Assert.assertEquals(13, content.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", content.getMsg().getValue());
            Assert.assertEquals(10, content.getExpectedSize().intValue());
        }
    }
    
}
