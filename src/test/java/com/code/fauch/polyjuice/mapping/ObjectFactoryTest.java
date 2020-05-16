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
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.code.fauch.polyjuice.Parameter;


/**
 * @author c.fauch
 *
 */
public class ObjectFactoryTest {

    @Test
    public void test() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException {
        final ArrayList<String> labels = new ArrayList<>();
        try (InputStream in = getClass().getResourceAsStream("/truc-template.yml")) {
            final ObjectFactory payload = new Yaml(new Constructor(ObjectFactory.class)).load(in);
            final Truc truc = payload.build(Truc.class);
            Assert.assertEquals(42, truc.getMagicalNumber().getValue().intValue());
            Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
            Assert.assertEquals(Duration.ofHours(-12), truc.getClock().getValue().getInformation());
            Assert.assertEquals(49, truc.getBytes().length);
            truc.addPropertyChangeListener(e -> labels.add(((Parameter<?>) e.getSource()).getLabel()));
            truc.getMsg().setValue("MERCI");
            truc.getMsgSize().setValue(5);
            Assert.assertEquals(5, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("MERCI", truc.getMsg().getValue());
            Assert.assertEquals(41, truc.getBytes().length);
            Assert.assertEquals(Arrays.asList("msg", "msgSize"), labels);
        }
    }

    @Test
    public void testImmuable() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException {
        final ArrayList<String> labels = new ArrayList<>();
        try (InputStream in = getClass().getResourceAsStream("/truc-template.yml")) {
            final ObjectFactory payload = new Yaml(new Constructor(ObjectFactory.class)).load(in);
            final ImmuableTruc truc = payload.build(ImmuableTruc.Builder.class).build();
            Assert.assertEquals(42, truc.getMagicalNumber().getValue().intValue());
            Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
            Assert.assertEquals(Duration.ofHours(-12), truc.getClock().getValue().getInformation());
            Assert.assertEquals(49, truc.getBytes().length);
            truc.addPropertyChangeListener(e -> labels.add(((Parameter<?>) e.getSource()).getLabel()));
            truc.getMsg().setValue("MERCI");
            truc.getMsgSize().setValue(5);
            Assert.assertEquals(5, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("MERCI", truc.getMsg().getValue());
            Assert.assertEquals(41, truc.getBytes().length);
            Assert.assertEquals(Arrays.asList("msg", "msgSize"), labels);
        }
    }
    
    @Test
    public void testExtenstion() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException, IOException {
        final ArrayList<String> labels = new ArrayList<>();
        try (InputStream in = getClass().getResourceAsStream("/truc-template-with-ip.yml")) {
            final TrucWithIpFactory payload = new Yaml(new Constructor(TrucWithIpFactory.class)).load(in);
            final TrucWithIp truc = payload.build();
            Assert.assertEquals(42, truc.getMagicalNumber().getValue().intValue());
            Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
            Assert.assertEquals(Duration.ofHours(-12), truc.getClock().getValue().getInformation());
            Assert.assertEquals(49, truc.getBytes().length);
            truc.addPropertyChangeListener(e -> labels.add(((Parameter<?>) e.getSource()).getLabel()));
            truc.getMsg().setValue("MERCI");
            truc.getMsgSize().setValue(5);
            Assert.assertEquals(5, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("MERCI", truc.getMsg().getValue());
            Assert.assertEquals(41, truc.getBytes().length);
            Assert.assertEquals(Arrays.asList("msg", "msgSize"), labels);
            Assert.assertEquals("192.168.56.103", truc.getIp());
        }
    }
    
    @Test
    public void testComposition() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
    NoSuchMethodException, SecurityException, IntrospectionException, IOException {
        final ArrayList<String> labels = new ArrayList<>();
        try (InputStream in = getClass().getResourceAsStream("/truc-template-composition.yml")) {
            final Frame frame = new Yaml(new Constructor(Frame.class)).load(in);
            Assert.assertEquals(100, frame.getRate().intValue());
            Assert.assertEquals("192.168.56.103", frame.getDst());
            final Truc truc = frame.getPayload().build(Truc.class);
            Assert.assertEquals(42, truc.getMagicalNumber().getValue().intValue());
            Assert.assertEquals(13, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("HELLO WORLD !", truc.getMsg().getValue());
            Assert.assertEquals(Duration.ofHours(-12), truc.getClock().getValue().getInformation());
            Assert.assertEquals(49, truc.getBytes().length);
            truc.addPropertyChangeListener(e -> labels.add(((Parameter<?>) e.getSource()).getLabel()));
            truc.getMsg().setValue("MERCI");
            truc.getMsgSize().setValue(5);
            Assert.assertEquals(5, truc.getMsgSize().getValue().intValue());
            Assert.assertEquals("MERCI", truc.getMsg().getValue());
            Assert.assertEquals(41, truc.getBytes().length);
            Assert.assertEquals(Arrays.asList("msg", "msgSize"), labels);
        }
    }

}
