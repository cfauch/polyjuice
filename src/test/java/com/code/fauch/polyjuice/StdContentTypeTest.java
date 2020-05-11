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

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author c.fauch
 *
 */
public class StdContentTypeTest {

    @Test
    public void testEncodeParameter() {
        final byte[] buff = StdContentType.PARAMETER.encode(Parameter.newConstant("p", StdType.INT, 42));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
    }

    @Test(expected=NullPointerException.class)
    public void testEncodeNullParameter() {
        final byte[] buff = StdContentType.PARAMETER.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
    }
    
    @Test
    public void testEncodeSequence() {
        final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
        final byte[] buff = StdContentType.SEQUENCE.encode(new Sequence(Arrays.asList(p1, p2)));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(421, bb.getInt());
    }

    @Test
    public void testEncodeSequenceSize() {
        final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
        final byte[] buff = StdContentType.SEQUENCE.encode(new Sequence(12, Arrays.asList(p1, p2)));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(421, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
    }

    @Test
    public void testEncodeSequenceSizeInf() {
        final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
        final byte[] buff = StdContentType.SEQUENCE.encode(new Sequence(4, Arrays.asList(p1, p2)));
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(4, buff.length);
    }

    @Test(expected=NullPointerException.class)
    public void testEncodeNullSequence() {
        final byte[] buff = StdContentType.SEQUENCE.encode(null);
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(4, buff.length);
    }

}
