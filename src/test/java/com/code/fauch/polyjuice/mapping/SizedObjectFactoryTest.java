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

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;

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
    public void test() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException {
        try (InputStream in = getClass().getResourceAsStream("/truc-template-with-size.yml")) {
            final SizedObjectFactory payload = new Yaml(new Constructor(SizedObjectFactory.class)).load(in);
            final TrucWithSize truc = payload.build(TrucWithSize.class);
            Assert.assertEquals(42, truc.getMagicalNumber().getValue().intValue());
            Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
            Assert.assertEquals(Duration.ofHours(-12), truc.getClock().getValue().getInformation());
            Assert.assertEquals(50, truc.getExpectedSize().intValue());
            Assert.assertEquals(50, truc.getBytes().length);
        }
    }
    
}
