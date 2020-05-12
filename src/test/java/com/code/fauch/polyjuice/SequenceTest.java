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
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author c.fauch
 *
 */
public class SequenceTest {

    @Test
    public void testNewParameterCopy() {
        final ArrayList<IContent> params = new ArrayList<IContent>();
        params.add(Parameter.newParameter("l1", StdType.INT, 42, false));
        final Sequence seq = new Sequence(4, params);
        params.add(Parameter.newParameter("l2", StdType.INT, 421, false));
        final ArrayList<IContent> seqParams = new ArrayList<IContent>();
        for (IContent p : seq) {
            seqParams.add(p);
        }
        Assert.assertEquals(params.subList(0, 1), seqParams);
    }

    @Test
    public void testParameterChanges() {
        final String[] labels = new String[]{""};
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final Parameter<Integer> p2 = Parameter.newParameter("l2", StdType.INT, 421, false); 
        final ArrayList<IContent> params = new ArrayList<IContent>();
        params.add(p1);
        params.add(p2);
        final Sequence seq = new Sequence(4, params);
        seq.addPropertyChangeListener(e -> labels[0] = ((Parameter<?>) e.getSource()).getLabel());
        Assert.assertEquals("", labels[0]);
        p2.setValue(365);
        Assert.assertEquals("l2", labels[0]);
    }

    @Test
    public void testEncodeSequence() {
        final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
        final byte[] buff = new Sequence(null, Arrays.asList(p1, p2)).getBytes();
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(421, bb.getInt());
    }

    @Test
    public void testEncodeSequenceSize() {
        final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
        final byte[] buff = new Sequence(12, Arrays.asList(p1, p2)).getBytes();
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(421, bb.getInt());
        Assert.assertEquals(0, bb.getInt());
    }

    @Test
    public void testEncodeSequenceSizeInf() {
        final Parameter<Integer> p1 = Parameter.newConstant("p1", StdType.INT, 42);
        final Parameter<Integer> p2 = Parameter.newConstant("p2", StdType.INT, 421);
        final byte[] buff = new Sequence(4, Arrays.asList(p1, p2)).getBytes();
        final ByteBuffer bb = ByteBuffer.wrap(buff);
        Assert.assertEquals(42, bb.getInt());
        Assert.assertEquals(4, buff.length);
    }

    @Test
    public void testGetValue() {
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final Parameter<Integer> p2 = Parameter.newParameter("l2", StdType.INT, 421, false); 
        final ArrayList<IContent> params = new ArrayList<IContent>();
        params.add(p1);
        params.add(p2);
        final Sequence seq = new Sequence(4, params);
        p2.setValue(365);
        Parameter<Integer> found = seq.getParameter("l1");
        Assert.assertTrue(found == p1);
        found = seq.getParameter("l2");
        Assert.assertTrue(found == p2);
    }

    @Test
    public void testGetValueNotFound() {
        final Parameter<Integer> p1 = Parameter.newParameter("l1", StdType.INT, 42, false);
        final Parameter<Integer> p2 = Parameter.newParameter("l2", StdType.INT, 421, false); 
        final ArrayList<IContent> params = new ArrayList<IContent>();
        params.add(p1);
        params.add(p2);
        final Sequence seq = new Sequence(4, params);
        p2.setValue(365);
        Assert.assertNull(seq.getParameter("l21"));
    }

}
