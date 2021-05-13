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

import com.code.fauch.polyjuice.Parameter;
import com.code.fauch.polyjuice.StdType;

/**
 * @author c.fauch
 *
 */
public class ParameterFactoryTest {

    @Test
    public void testBuild() {
        final ParameterFactory factory = new ParameterFactory();
        factory.setName("msg");
        factory.setType("STRING");
        factory.setValue("hello");
        factory.setReadonly(true);
        Parameter<String> param = factory.build();
        param.setValue("test");
        Assert.assertEquals("msg", param.getLabel());
        Assert.assertEquals(StdType.STRING, param.getType());
        Assert.assertEquals("hello", param.getValue());
        Assert.assertTrue(param.isReadOnly());
    }
    
    @Test
    public void testFromYaml() throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/simple-parameter-template.yml")) {
            final ParameterFactory payload = new Yaml().load(in);
            final Parameter<?> param = payload.build(Parameter.class);
            Assert.assertEquals(13, param.getValue());
        }
    }

}
